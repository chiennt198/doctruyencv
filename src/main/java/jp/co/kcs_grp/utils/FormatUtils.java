/**
 * ファイル名: FormatUtils.java
 * バージョン: 1.0.0
 * 作成日付: Jan 11, 2017
 * 最終更新日付: Jan 11, 2017
 * 作成者: KCS
 * 更新履歴: Jan 11, 2017 : 新規作成
 */

package jp.co.kcs_grp.utils;

import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;

/**
 * ファイル名: FormatUtils.java
 * バージョン: 1.0.0
 * 作成日付: 2016/03/09
 * 最終更新日付: 2016/03/09
 * 作成者: KCS
 * 更新履歴: 2016/03/09 : 新規作成
 */
public class FormatUtils
{
    public static String addComma(String str)
    {
        String retVal = "";
        if (!StringUtils.isEmpty(str))
        {
            DecimalFormat formatter = new DecimalFormat("#,###");
            retVal = formatter.format(Double.parseDouble(str));    
        }
        return retVal;
    }
}
