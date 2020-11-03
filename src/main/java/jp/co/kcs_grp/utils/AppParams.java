/**
 * ファイル名: AppParams.java
 * バージョン: 1.0.0
 * 作成日付: Jan 11, 2017
 * 最終更新日付: Jan 11, 2017
 * 作成者: KCS
 * 更新履歴: Jan 11, 2017 : 新規作成
 */

package jp.co.kcs_grp.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import jp.co.kcs_grp.common.Constants;


public class AppParams {
    private final static Logger logger = Logger.getLogger(AppParams.class);

	public static final String rootParamName = "parameter";	// properties file name
	public static int cycleMin = 1;	// パラメタファイル再読み込み周期(間隔を分単位で指定)
	public static String environmentKey = null;	// dev, publish, staging
	public static String keyPrefix = "";		// dev., publish., staging. ""
	private static Map<String, ConfigDataListsObject> parameterFiles = null;
	private static long timeOfLastBuild = 0;
	private static boolean cacheMapReady = false;

	/**
	 * every parameters into child map (and return it)
	 */
	static class ConfigDataListsObject {
		public String configFileId = null;	// identifier (= Key)
		public String conffilepath = null;	// path
		private Map<String, String> configLines = null;	// params
		private boolean built = false;
		private long timeOfLastRead = 0;		// last time

		public Map<String, String> getConfigLines() {
			return configLines;
		}

		public void setConfigLines(Map<String, String> configLines) {
			if(configLines != null){
				this.configLines = configLines;
				this.setBuilt(true);
			} else this.setBuilt(false);
		}

		public boolean isBuilt() {
			return built;
		}

		public void setBuilt(boolean built) {
			this.built = built;
		}

		public long getTimeOfLastRead() {
			return timeOfLastRead;
		}

		public void setTimeOfLastRead(long timeOfLastRead) {
			this.timeOfLastRead = timeOfLastRead;
		}
	}

	public static void useEnvParams(String app_instance) {
		// 本番(publish)、仮環境(staging)、テストサーバ環境(dev)には必ず設定を行なう
		// マッチするprefix が用意されていない項目はprefix なしが使用される
		// app_instance による初期化
		environmentKey = app_instance;
        logger.info("appInstanceName:" + environmentKey);
		// key-prefix の決定
		if (AppParams.keyPrefix.length() == 0)
			AppParams.keyPrefix = AppParams.environmentKey + ".";	// 各環境prefix

		Date d = new Date();
		long systime = d.getTime();

		// static Map 生成 ← キャッシュルート
		if (AppParams.parameterFiles == null) {
			AppParams.parameterFiles = new HashMap<String, ConfigDataListsObject>();
		} else {
			if (timeOfLastBuild != 0) {
				if ((systime - timeOfLastBuild) < (60000 * AppParams.cycleMin)) return;
			}
		}
		AppParams.parameterFiles.clear();
		cacheMapReady = false;

		// コンフィグマップルートの構築
		// 最初に全体を読み込み、Mapに積む (読み込むべき各Configを決定する)

        ResourceBundle resource = ResourceBundle.getBundle(rootParamName);
        Map<String, String> map1 = convertResourceBundleToMap(resource);

        // environmentKey により map を現在の環境用の内容に編集して再構築する
        // キー文字先頭が keyPrefix に一致するものがあれば、それを除去したキー名でputするだけ
        // ----------------------------------------------------------------------------
        // したがってkeyPrefix なしの項目は本番でも使われる可能性があることに注意する
        // local の設定には常にlocal prefix を明記するのが良い(そうすれば間違っても本番で使われない)
        // 旧方式(PropertiesUtils)と混在する時は本番用にpublish を付けたものを用意すれば確実
        // ----------------------------------------------------------------------------
        Map<String, String> map2 = new HashMap<String, String>(map1);	// コピー

        int prefixsize = keyPrefix.length();
        for (Entry<String, String> entry : map2.entrySet()) {
        	String key = entry.getKey();
        	if(key.startsWith(keyPrefix)) {
            	String value = entry.getValue().toString();
        		// 除去したキー名を得る
            	String overrideKey = key.substring(prefixsize);
            	// オリジナル map 側の内容を上書きする
            	map1.put(overrideKey, value);
            	// 効果は小さいがkeyPrefix付きのエントリは削除する
            	map1.remove(key);
            	logger.info("@Bundle(" + rootParamName + ") -override:" + key + " -value:" + value);
        	}
        }

        // parameterFiles を構築(ファイル読み込み。エラー時はNullをセット)
        for (Entry<String, String> entry : map1.entrySet()) {
        	ConfigDataListsObject obj = new ConfigDataListsObject();
        	obj.configFileId = entry.getKey();
        	obj.conffilepath = entry.getValue();
        	obj.setConfigLines(null);
        	AppParams.parameterFiles.put(entry.getKey(), obj);
        }

        cacheMapReady = true;
        timeOfLastBuild = systime;
	}

	static Map<String, String> convertResourceBundleToMap(ResourceBundle resource) {
		  Map<String, String> map = new HashMap<String, String>();

		  Enumeration<String> keys = resource.getKeys();
		  while (keys.hasMoreElements()) {
		    String key = keys.nextElement();
		    map.put(key, resource.getString(key));
		  }

		  return map;
		}

	/**
     * create Map which contains key and value in properties file
     * @param fileName
     * @return
     * @throws IOException
     */
    public static Map<String, String> getPropertyFromCache(String fileName)
            throws IOException {
    	Map<String, String> retobj = null;

		Date d = new Date();
		long systime = d.getTime();

    	if (AppParams.cacheMapReady) {
            ConfigDataListsObject cdo = parameterFiles.get(fileName);
            if (cdo != null) {
                if (cdo.isBuilt() && ((systime - cdo.getTimeOfLastRead()) < (60000 * AppParams.cycleMin))){
                	retobj = cdo.getConfigLines();
        		} else {
        			retobj = readSpecifiedParamFiles(fileName);
        			if (retobj == null){
        				cdo.setBuilt(false);
        			} else {
        				cdo.setConfigLines(retobj);
        				cdo.setTimeOfLastRead(systime);
        				cdo.setBuilt(true);
        			}
        		}
            }
    	}

    	return retobj;
    }

    /**
     * create Map which contains key and value in properties file
     * @param fileName
     * @return
     * @throws IOException
     */
    private static Map<String, String> readSpecifiedParamFiles(String fileName) {

        // 返却値
        Map<String, String> originalMap = new HashMap<String, String>();

        BufferedReader br = null;

        try {
            // confファイルの取得
            String confpath = null;
            ConfigDataListsObject cdo = parameterFiles.get(fileName);
            if (cdo != null) confpath = cdo.conffilepath;

            // confファイルの読み込み（Java SE 7～）
            //// 最初にPathインターフェースを定義する
            Path confpath2 = Paths.get(confpath);
            if(! Files.isReadable(confpath2)) {
            	logger.info("paramfile: " + fileName + " - not found.");
            	return null;
            }
            //// Files.readAllLinesメソッドでテキストファイルの全行を読み込む
            List<String> conflist = Files.readAllLines(confpath2, Charset.forName("UTF-8"));
            String newLine = System.getProperty("line.separator");
            for (String confstr : conflist) {
                //// 後は、必要な行だけ取得する（不要な行をふるい落とす）
                if (StringUtils.isEmpty(confstr)) {
                    // 空欄の場合はskip
                    continue;
                }
                if (confstr.charAt(0) == '#') {
                    // 「#」で始まる場合はskip（コメント行とみなす）
                    continue;
                }
                // 「=」で分割する（ただし、最大で2項目）
                String[] keyval = StringUtils.splitPreserveAllTokens(confstr, "=", 2);
                if (keyval == null || keyval.length < 2) {
                    // 「=」がない場合
                    continue;
                }
                String key = keyval[0];
                String val = keyval[1];
                val = val.replaceAll("%newLine%", newLine);
                originalMap.put(key, val);
            }

        } catch (Exception e) {
            // 予期しないエラー
            e.printStackTrace();
            return null;
        } finally {
            if (br != null) {
                try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        }
        Map<String, String> returnMap = new HashMap<String, String>(originalMap);	// コピー

        int prefixsize = keyPrefix.length();
        for (Entry<String, String> entry : originalMap.entrySet()) {
        	String key = entry.getKey();
        	if(key.startsWith(keyPrefix)) {
        		// 除去したキー名を得る
            	String overrideKey = key.substring(prefixsize);
            	String value = entry.getValue().toString();
            	// オリジナル map 側の内容を上書きする
            	returnMap.put(overrideKey, value);
            	// 効果は小さいがkeyPrefix付きのエントリは削除する(あれば)
            	returnMap.remove(key);
            	logger.info("@Paramfile:" + fileName + " -override key:" + key + " -value:" + value);
        	}
        }
        logger.info("Paramfile:" + fileName + " - build done.");
        return returnMap;
    }
    /**
     * return cached value of property - most brief
     *
     * @return
     * @throws IOException
     */
    public static String getValue(String fileId, String key){

    	String retValue = null;
        Map<String, String> map;

        try {
			map = getPropertyFromCache(fileId);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
    	if (AppParams.cacheMapReady) {
            ConfigDataListsObject cdo = parameterFiles.get(fileId);
            if (cdo != null) {
            	map = cdo.getConfigLines();
            	if (map != null) {
            		retValue = map.get(key);
            	}
            }
    	}
		return retValue;
    }

    /**
     * return value of mail.properties file
     *
     * @return
     * @throws IOException
     */
    public static Map<String, String> getMailProp() throws IOException {

        String propFileName = Constants.MAIL_PROPERTIES;
        Map<String, String> values = getPropertyFromCache(propFileName);
        return values;
    }

    /**
     * return value of url_not_auth.properties file
     *
     * @return
     * @throws IOException
     */
    public static Map<String, String> getUrlNotAuthProp() throws IOException {

        String propFileName = Constants.URL_NOT_AUTH_PROPERTIES;
        Map<String, String> values = getPropertyFromCache(propFileName);
        return values;
    }
    
    /**
     * return value of parameter.properties file
     *
     * @return
     * @throws IOException
     */
    public static Map<String, String> getParameterProp() throws IOException {

        String propFileName = Constants.PARAMETER_PROPERTIES;
        Map<String, String> values = getPropertyFromCache(propFileName);
        return values;
    }
}
