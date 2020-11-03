package jp.co.kcs_grp.model;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import jp.co.kcs_grp.base.json.ObjectResponse;
import jp.co.kcs_grp.common.Constants;
import jp.co.kcs_grp.utils.AppParams;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class TokenGenerator {
	// ELS01 ** 暗号化アルゴリズム定義
	private static final String ELS01_ALGORITHM = "DESede";
	// ELS01 ** 暗号化モード定義
	private static final String ELS01_MODE = "ECB";
	// ELS01 ** パディング方式定義
	private static final String ELS01_PADDING = "PKCS5Padding";

	public ObjectResponse genToken(String tokenId, String societyCd, String memberId2) {
		// return generated token

		// Supported TokenId
		// els01: elsevier member access
		ObjectResponse objectResponse = new ObjectResponse();
		Map<String, String> dataInfo = new HashMap<String, String>();
		// セット順に注意(setDataInfo はstatus を上書きするので)
		objectResponse.setStatus(Constants.RESPONSE_STATUS_URI_PARAMS_ERROR);	// 初期値 (error)

		String tokenstring ="";

		if (tokenId != null && tokenId.equals("els01")) {
			// -------------------------------------------------------------------------------
			// Elsevier Journal link token for member mode (encrypt with 3DES/ECB/PKCS#5)
			// -------------------------------------------------------------------------------
			// sample: paramString = "www.society.com|1084470043000|memberaccess";
			// sample KeyString: 92f2f81930b5afb056630afb02f2f8b1
			// sample encrypted string: uQlYYov+XlVEHL0JBpvt15FeJxt+fXgjs9KLJHH5XsUd80emx5Nn5kSaCXxhQId/

			// ELS01 エルゼビア TPSSOCIETYID
			String serverName   = AppParams.getValue("parameterpath", "ELS01_TPSHOSTNAME");		//For token
			String token_prefix = AppParams.getValue("parameterpath", "ELS01_TPSSOCIETYID");	//For token
			String encryptKey   = AppParams.getValue("parameterpath", "ELS01_TPSENCRYPTKEY");	//For crypt
			String jounalUrl    = AppParams.getValue("parameterpath", "ELS01_JOURNAL_URL");		//Journal url

			BASE64Decoder decode64 = new BASE64Decoder();
			BASE64Encoder encode64 = new BASE64Encoder();

			String paramString = "";

			long ct = System.currentTimeMillis();
			String currentTime = Long.toString(ct).trim();

			paramString = serverName  + "|" + currentTime + "|" + "memberaccess";

			byte[] keyByte = null;
			try {
				keyByte = decode64.decodeBuffer(encryptKey);
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			try {
				byte[] original = paramString.getBytes(StandardCharsets.UTF_8);

				byte[] encrypted = tripleDesEncrypt(original, keyByte, ELS01_ALGORITHM, ELS01_MODE, ELS01_PADDING);

				tokenstring = URLEncoder.encode(token_prefix + "." + encode64.encode(encrypted).toString(), "UTF-8");;

				dataInfo.put("token", tokenstring);
				dataInfo.put("jnlurl", jounalUrl);
				objectResponse.setDataInfo(dataInfo);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return objectResponse;
	}
	/**
	 * 3DES暗号化
	 */
	public static byte[] tripleDesEncrypt(byte[] bytes, byte[] keyByte, String algorithm, String mode, String padding) throws Exception {
		Cipher cipher = Cipher.getInstance(String.join("/", algorithm, mode, padding));
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyByte, algorithm));
		return cipher.doFinal(bytes);
	}

	/**
	 * 3DES復号化
	 */
	public static byte[] tripleDesDecrypt(byte[] bytes, byte[] keyByte, String algorithm, String mode, String padding) throws Exception {
		Cipher cipher = Cipher.getInstance(String.join("/", algorithm, mode, padding));
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyByte, algorithm));
		return cipher.doFinal(bytes);
	}
}
