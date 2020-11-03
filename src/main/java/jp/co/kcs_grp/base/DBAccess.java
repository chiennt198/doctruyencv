package jp.co.kcs_grp.base;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;


public class DBAccess {
    private Logger log  = Logger.getLogger(DBAccess.class);

    Connection     conn = null;

    Statement      stmt = null;

    ResultSet      rs   = null;
    InitialContext ic;
    DataSource ds;
    /**
     * DBに接続する
     * @throws Exception 例外
     */
    public boolean dbConnection() {
        log.debug("start");
        boolean flg = false;
        try {
        	ic = new InitialContext();
            ds = (DataSource)ic.lookup("java:comp/env/jdbc/MySQL");
            conn = ds.getConnection();
            stmt = (Statement) conn.createStatement();
            flg = true;

        }
        catch (Exception e) {
            e.printStackTrace();
            DBClose();
        }
        log.debug("end");
        return flg;

    }

    /**
     * DB切断処理を行う
     * @throws Exception 例外
     */
    public boolean DBClose() {

        log.debug("start");
        boolean flg = false;

        try {
            if (stmt != null) {
                stmt.close();
                stmt = null;
            }

            if (rs != null) {
                rs.close();
                rs = null;
            }

            if (conn != null) {
                if (!conn.isClosed()) {
                    conn.close();
                    conn = null;
                }
            }

            flg = true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        log.debug("end");
        return flg;

    }

    /*トランザクションの開始（connectionのオートコミットをoffにする）
     * @return  true    成功
     *                  false   失敗（トランザクション機能を持っていない場合もこっち）
     */
    public boolean DBTranStart() {

        log.debug("start");
        boolean flg = false;

        try {

            if (conn.isClosed()) {
                //コネクションがない
                log.fatal("connection isClosed");
                return flg;
            }

            /* コミットをきらないようにする */
            conn.setAutoCommit(false);
            flg = true;

        }
        catch (SQLException e) {
            e.printStackTrace();
            log.fatal(e.toString());
        }
        log.debug("end");
        return flg;
    }

    /*トランザクションの終了（connectionのオートコミットをoffにする）
     * @param       b
     *                      true    コミットをきる
     *                      false   ロールバックをする
     * @return  true    成功
     *                  false   失敗
     */
    public boolean DBTranEnd(boolean b) {
        log.debug("start");
        boolean flg = false;

        try {
            if (conn.isClosed()) {
                /* コネクションが閉じられている場合は終了してよし */
                flg = true;
                return flg;
            }

            if (conn.getAutoCommit() == true) {
                /* オートコミットがOFFの場合は終了 */
                flg = true;
                return flg;
            }
            if (b) {
                /* コミットをきる */
                conn.commit();
            }
            else {
                /* ロールバックをする */
                conn.rollback();
            }

            /* オートコミットをONに */
            conn.setAutoCommit(true);
            flg = true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            log.fatal(e.toString());
        }
        log.debug("end");

        return flg;
    }

    /**
     * PreparedStatementを使用する
     * @param sql SQL
     * @return PreparedStatement
     * @throws SQLException 例外
     */
    public KcsPreparedStatement getPreparedStatement(String sql)
            throws SQLException {
        log.debug(sql);
        return new KcsPreparedStatement(conn.prepareStatement(sql), log);
    }

}
