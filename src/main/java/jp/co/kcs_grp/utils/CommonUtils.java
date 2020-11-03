/**
 * ファイル名: CommonUtils.java
 * バージョン: 1.0.0
 * 作成日付: Jan 11, 2017
 * 最終更新日付: Jan 11, 2017
 * 作成者: KCS
 * 更新履歴: Jan 11, 2017 : 新規作成
 */

package jp.co.kcs_grp.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class CommonUtils {
    
    /**
     * @param str
     * @return
     */
    public static String SqlLiteral(String str) {

        if (str == null || str.length() < 1) {
            return "";
        }

        StringBuffer result = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {

            switch (str.charAt(i)) {
                case '\\':
                    result.append("\\");
                    result.append("\\");
                    break;
                case '"':
                    result.append("\\");
                    result.append("\"");
                    break;
                case '\'':
                    result.append("\\");
                    result.append("\'");
                    break;
                case ',':
                    result.append("\\");
                    result.append(",");
                    break;
                default:
                    result.append(str.charAt(i));
                    break;
            }

        }

        return result.toString();

    }

    /**
    *
    * FromToの日付の検索条件を作る。FYとTYがNULLまたは空白のときはNULLを返す。
    * 年がNULLや空白だと入力なしとみなす。
    *
    * @param field　DBフィールド名
    * @param fy　Fromの年
    * @param fm　Fromの月
    * @param fd　Fromの日
    * @param ty　Toの年
    * @param tm　Toの月
    * @param td　Toの日
    * @return
    */
    public static String SqlDateFromTo(String field, String fy, String fm,
            String fd, String ty, String tm, String td) {
        StringBuffer ret = new StringBuffer(field);
        //入力なしとみなす
        if ((fy == null || fy.trim().length() == 0)
                && (ty == null || ty.trim().length() == 0))
            return null;
        //Fromだけ入力あり
        if ((fy != null && fy.trim().length() > 0)
                && (ty == null || ty.trim().length() == 0)) {
            ret.append(" >= '" + fy.trim() + "-");
            ret.append(getFromMonthDay(fm, fd));
        }
        //Toだけ入力あり
        else if ((ty != null && ty.trim().length() > 0)
                && (fy == null || fy.trim().length() == 0)) {
            ret.append(" <= '" + ty.trim() + "-");
            ret.append(getToMonthDay(ty, tm, td));
        }
        //どちらも入力あり
        else {
            ret.append(" between '" + fy.trim() + "-");
            ret.append(getFromMonthDay(fm, fd));

            ret.append(" and '" + ty.trim() + "-");
            ret.append(getToMonthDay(ty, tm, td));
        }
        return ret.toString();
    }

    public static String getFromMonthDay(String mon, String day) {
        StringBuffer ret = new StringBuffer();

        if (mon == null || mon.trim().length() == 0)
            ret.append("01-01'");
        else if (day == null || day.trim().length() == 0)
            ret.append(mon.trim() + "-01'");
        else ret.append(mon.trim() + "-" + day.trim() + "'");

        return ret.toString();
    }

    /**
     * @param year
     * @param mon
     * @param day
     * @return
     */
    public static String getToMonthDay(String year, String mon, String day) {
        StringBuffer ret = new StringBuffer();

        if (mon == null || mon.trim().length() == 0)
            ret.append("12-31'");
        else if (day == null || day.trim().length() == 0) {
            ret.append(mon.trim() + "-"
                    + getMonthLastday(Integer.parseInt(year.trim()),
                            Integer.parseInt(mon.trim()))
                    + "'");
        } else ret.append(mon.trim() + "-" + day.trim() + "'");

        return ret.toString();
    }

    /**
     * @param year
     * @param mon
     * @return
     */
    public static String getMonthLastday(int year, int mon) {
        String last_day = null;
        if (mon == 0)
            return null;
        else {
            switch (mon) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    last_day = "31";
                    break;
                case 2:
                    if ((year % 400 == 0) || (year % 100 > 0 && year % 4 == 0))
                        last_day = "29";
                    else last_day = "28";
                    break;
                default:
                    last_day = "30";
            }
        }
        return last_day;
    }

    /**
     * @param org
     * @return
     */
    public static String WTrim(String org) {
        int from, to;
        String buf = org;

        if (org == null) {
            return "";
        }

        for (from = 0; from < buf.length(); from++) {
            if (buf.charAt(from) != ' ' && buf.charAt(from) != '　') {
                buf = buf.substring(from);
                break;
            }
            if (from == buf.length() - 1
                    && (buf.charAt(from) == ' ' || buf.charAt(from) == '　'))
                buf = "";
        }
        if (buf.equals(""))
            return "";
        String buf2 = buf;
        for (to = buf2.length(); to > 0; to--) {
            if (buf2.charAt(to - 1) != ' ' && buf2.charAt(to - 1) != '　') {
                buf2 = buf2.substring(0, to);
                break;
            }
        }

        return buf2;
    }
    /**Convert String to List
     * convertStringArrayToList
     * @param stringArr
     * @return
     */
    public static List<String> convertStringArrayToList(String stringArr){
        String[]a= stringArr.split(",");
        List<String> wordList = new ArrayList<String>(Arrays.asList(a));
        return wordList;
    }
    
    public static String convertStringArrayToString(String stringArr){
        StringBuilder str = new StringBuilder();
        char[] c_arr = stringArr.toCharArray(); 
        
        for (char c : c_arr)
        {
            boolean flag =  StringUtils.isNotBlank(c + "");
            if(!flag){
                stringArr = stringArr.replaceAll(c+"", ",");
            }
        }
       
        stringArr = stringArr.replaceAll("\\s+",",");
        stringArr = stringArr.replaceAll("\\n+",",");
        stringArr = stringArr.replaceAll(",+",",");

        if(StringUtils.isNotEmpty(stringArr)){
            String[] arr = stringArr.split(",");
           
            if(arr.length > 1){
                for (int i = 0; i < arr.length; i++)
                {
                    str.append("'");
                    str.append(arr[i]);
                    str.append("',");
                }
            }else{
                str.append("'"+stringArr+"'");
            }
            
            if(str.toString().endsWith(",")){
                int numLength = str.toString().length() - 1;
                String strdata = str.toString();
                strdata =  strdata.substring(0, numLength);
                str = new StringBuilder();
                str.append(strdata);
            }
        }else{
           return "";
        }
        
        return str.toString();
    }
    
}
