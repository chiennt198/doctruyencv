/**
 * ファイル名: Constants.java
 * バージョン: 1.0.0
 * 作成日付: Jan 11, 2017
 * 最終更新日付: Jan 11, 2017
 * 作成者: KCS
 * 更新履歴: Jan 11, 2017 : 新規作成
 */

package jp.co.kcs_grp.common;

public class Constants {

    /**.
     * MAIL_PROPERTIES
     */
    public static final String MAIL_PROPERTIES     = "mailpath";

    /**.
     * URL_NOT_AUTH_PROPERTIES
     */
    public static final String URL_NOT_AUTH_PROPERTIES     = "urlnotauthpath";

    /**.
     * PARAMETER_PROPERTIES
     */
    public static final String PARAMETER_PROPERTIES     = "parameterpath";

    /*JDBC information*/
    /**.
     * JDBC_USER
     */
    public static final String JDBC_USER           = "user";

    /**.
     * JDBC_PW
     */
    public static final String JDBC_PW             = "password";

    /**.
     * JDBC_URI
     */
    public static final String JDBC_URI            = "uri";

    /**
     *
     */
    public static final String APPLICATION_JSON    = "application/json";

    //--------------STATUS CODE-------------------

    /**.
     *0:正常処理
     */
    public static final String RESPONSE_STATUS_NORMAL = "0";
    /**.
     *1:該当データゼロ件
     */
    public static final String RESPONSE_STATUS_NO_DATA = "1";
    /**.
     *2:URI・パラメータ不正
     */
    public static final String RESPONSE_STATUS_URI_PARAMS_ERROR = "2";
    /**.
     *9:ＴＢＬアクセスエラー
     */
    public static final String RESPONSE_STATUS_DB_ERROR = "9";
    
    /**.
     *0:NON MESSAGE
     */
    public static final String ERROR_MESSAGE_NORMAL = "";
    /**.
     *1:「対象データがありません」
     */
    public static final String ERROR_MESSAGE_NO_DATA = "対象データがありません";
    
    public static final String SYSTEM_ERROR_MESSAGE = "問題が発生しました。続けて表示される場合は京葉コンピューターサービス株式会社までお問い合わせください。<br>お問い合わせは<a href=\"/WebMemberSys/html/inquiry.html\">こちら</a>";
   

}
