package jp.co.kcs_grp.base.json;

import java.util.ArrayList;
import java.util.List;

import jp.co.kcs_grp.common.Constants;

/**
 * @author kcs
 *
 */
public class ObjectResponse {
    
    /**.
     * ステータス
     * 0:正常処理
     * 1:該当データゼロ件
     * 2:URI・パラメータ不正
     * 9:ＴＢＬアクセスエラー
     */
    private String status;
    
    /**.
     * エラーメッセージ
     * 0:NON MESSAGE
     * 1:「対象データがありません」
     * 2:「INPUT-URI ERROR」
     * 9:「DATA BASE ACCESS ERROR」
     */
    private String errorMessage;
    
    /**.
     * マスター情報
     */
    private Object dataInfo;
    
    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
        if (Constants.RESPONSE_STATUS_NORMAL.equals(status)) {
            this.errorMessage = Constants.ERROR_MESSAGE_NORMAL;
        } else if (Constants.RESPONSE_STATUS_NO_DATA.equals(status)) {
            this.errorMessage = Constants.ERROR_MESSAGE_NO_DATA;
        } else {
            this.errorMessage = Constants.SYSTEM_ERROR_MESSAGE;
        }
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return the dataInfo
     */
    public Object getDataInfo()
    {
        return dataInfo;
    }

    /**
     * @param dataInfo the dataInfo to set
     */
    public void setDataInfo(Object dataInfo)
    {
        this.dataInfo = dataInfo;
        if (dataInfo == null)
        {
            setStatus(Constants.RESPONSE_STATUS_NO_DATA);
        } else {
            if (dataInfo instanceof List && ((List<?>)dataInfo).isEmpty())
            {
                setStatus(Constants.RESPONSE_STATUS_NO_DATA);
            } else if (dataInfo instanceof ArrayList && ((ArrayList<?>)dataInfo).isEmpty()) {
                setStatus(Constants.RESPONSE_STATUS_NO_DATA);
            } else {
                setStatus(Constants.RESPONSE_STATUS_NORMAL);
            }
        }
    }
    
}