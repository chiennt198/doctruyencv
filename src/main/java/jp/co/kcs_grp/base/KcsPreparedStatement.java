/*
 * KcsPreparedStatement.java    $
 * $Id: KcsPreparedStatement.java,v 1.1 2012/03/05 04:45:09 nagashima Exp $
 * $Revision: 1.1 $
 * $Date: 2012/03/05 04:45:09 $
 *
 * KcsPreparedStatementクラス.
 *
 * 修正履歴     修正者氏名　  修正内容
 * 2012/03/02   nagashima           新規
 *
 * Copyright (C) KCS
 */
package jp.co.kcs_grp.base;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

import jp.co.kcs_grp.utils.CommonUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class KcsPreparedStatement implements PreparedStatement {
    private Logger            log = Logger.getLogger(KcsPreparedStatement.class);

    private PreparedStatement ps  = null;

    public KcsPreparedStatement(PreparedStatement ps, Logger log) {
        this.log = log;
        this.ps = ps;
    }

    public void addBatch() throws SQLException {
        log.debug("addBatch()");
        ps.addBatch();
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#execute()
     */
    public void clearParameters() throws SQLException {
        log.debug("clearParameters()");
        ps.clearParameters();
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#execute()
     */
    public boolean execute() throws SQLException {
        log.debug("execute()");
        return ps.execute();
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#executeQuery()
     */
    public ResultSet executeQuery() throws SQLException {
        log.debug("executeQuery()");
        return ps.executeQuery();
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#executeUpdate()
     */
    public int executeUpdate() throws SQLException {
        log.debug("executeUpdate()");
        return ps.executeUpdate();
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#getMetaData()
     */
    public ResultSetMetaData getMetaData() throws SQLException {
        log.debug("getMetaData()");
        return ps.getMetaData();
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#getParameterMetaData()
     */
    public ParameterMetaData getParameterMetaData() throws SQLException {
        log.debug("getParameterMetaData()");
        return ps.getParameterMetaData();
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setArray(int, java.sql.Array)
     */
    public void setArray(int i, Array x) throws SQLException {
        log.debug("setArray(" + i + "," + x + ")");
        ps.setArray(i, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setAsciiStream(int, java.io.InputStream, int)
     */
    public void setAsciiStream(int parameterIndex, InputStream x, int length)
            throws SQLException {
        log.debug("setAsciiStream(" + parameterIndex + "," + x + "," + length
                + ")");
        ps.setAsciiStream(parameterIndex, x, length);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setBigDecimal(int, java.math.BigDecimal)
     */
    public void setBigDecimal(int parameterIndex, BigDecimal x)
            throws SQLException {
        log.debug("setBigDecimal(" + parameterIndex + "," + x + ")");
        ps.setBigDecimal(parameterIndex, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setBinaryStream(int, java.io.InputStream, int)
     */
    public void setBinaryStream(int parameterIndex, InputStream x, int length)
            throws SQLException {
        log.debug("setBinaryStream(" + parameterIndex + "," + x + "," + length
                + ")");
        ps.setBinaryStream(parameterIndex, x, length);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setBlob(int, java.sql.Blob)
     */
    public void setBlob(int i, Blob x) throws SQLException {
        log.debug("setBlob(" + i + "," + x + ")");
        ps.setBlob(i, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setBoolean(int, boolean)
     */
    public void setBoolean(int parameterIndex, boolean x) throws SQLException {
        log.debug("setBoolean(" + parameterIndex + "," + x + ")");
        ps.setBoolean(parameterIndex, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setByte(int, byte)
     */
    public void setByte(int parameterIndex, byte x) throws SQLException {
        log.debug("setByte(" + parameterIndex + "," + x + ")");
        ps.setByte(parameterIndex, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setBytes(int, byte[])
     */
    public void setBytes(int parameterIndex, byte[] x) throws SQLException {
        log.debug("setBytes(" + parameterIndex + "," + x + ")");
        ps.setBytes(parameterIndex, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setCharacterStream(int, java.io.Reader, int)
     */
    public void setCharacterStream(int parameterIndex, Reader reader,
            int length) throws SQLException {
        log.debug("setCharacterStream(" + parameterIndex + "," + reader + ","
                + length + ")");
        ps.setCharacterStream(parameterIndex, reader, length);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setClob(int, java.sql.Clob)
     */
    public void setClob(int i, Clob x) throws SQLException {
        log.debug("setClob(" + i + "," + x + ")");
        ps.setClob(i, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setDate(int, java.sql.Date)
     */
    public void setDate(int parameterIndex, Date x) throws SQLException {
        log.debug("setDate(" + parameterIndex + "," + x + ")");
        ps.setDate(parameterIndex, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setDate(int, java.sql.Date, java.util.Calendar)
     */
    public void setDate(int parameterIndex, Date x, Calendar cal)
            throws SQLException {
        log.debug("setDate(" + parameterIndex + "," + x + "," + cal + ")");
        ps.setDate(parameterIndex, x, cal);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setDouble(int, double)
     */
    public void setDouble(int parameterIndex, double x) throws SQLException {
        log.debug("setDouble(" + parameterIndex + "," + x + ")");
        ps.setDouble(parameterIndex, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setFloat(int, float)
     */
    public void setFloat(int parameterIndex, float x) throws SQLException {
        log.debug("setFloat(" + parameterIndex + "," + x + ")");
        ps.setFloat(parameterIndex, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setInt(int, int)
     */
    public void setInt(int parameterIndex, int x) throws SQLException {
        log.debug("setInt(" + parameterIndex + "," + x + ")");
        ps.setInt(parameterIndex, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setLong(int, long)
     */
    public void setLong(int parameterIndex, long x) throws SQLException {
        log.debug("setLong(" + parameterIndex + "," + x + ")");
        ps.setLong(parameterIndex, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setNull(int, int)
     */
    public void setNull(int parameterIndex, int sqlType) throws SQLException {
        log.debug("setNull(" + parameterIndex + "," + sqlType + ")");
        ps.setNull(parameterIndex, sqlType);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setNull(int, int, java.lang.String)
     */
    public void setNull(int paramIndex, int sqlType, String typeName)
            throws SQLException {
        log.debug(
                "setNull(" + paramIndex + "," + sqlType + "," + typeName + ")");
        ps.setNull(paramIndex, sqlType, typeName);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setObject(int, java.lang.Object)
     */
    public void setObject(int parameterIndex, Object x) throws SQLException {
        log.debug("setObject(" + parameterIndex + "," + x + ")");
        ps.setObject(parameterIndex, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setObject(int, java.lang.Object, int)
     */
    public void setObject(int parameterIndex, Object x, int targetSqlType)
            throws SQLException {
        log.debug("setObject(" + parameterIndex + "," + x + "," + targetSqlType
                + ")");
        ps.setObject(parameterIndex, x, targetSqlType);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setObject(int, java.lang.Object, int, int)
     */
    public void setObject(int parameterIndex, Object x, int targetSqlType,
            int scale) throws SQLException {
        log.debug("setObject(" + parameterIndex + "," + x + "," + targetSqlType
                + "," + scale + ")");
        ps.setObject(parameterIndex, x, targetSqlType, scale);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setRef(int, java.sql.Ref)
     */
    public void setRef(int i, Ref x) throws SQLException {
        log.debug("setRef(" + i + "," + x + ")");
        ps.setRef(i, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setShort(int, short)
     */
    public void setShort(int parameterIndex, short x) throws SQLException {
        log.debug("setShort(" + parameterIndex + "," + x + ")");
        ps.setShort(parameterIndex, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setString(int, java.lang.String)
     */
    public void setString(int parameterIndex, String x) throws SQLException {
        x = CommonUtils.SqlLiteral(x);
        if (StringUtils.isEmpty(x))
        {
            x = null;
        }
        log.debug("setString(" + parameterIndex + "," + x + ")");
        ps.setString(parameterIndex, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setString(int, java.lang.String)
     */
    public void setString(int parameterIndex, String x, boolean isEmpty) throws SQLException {
        x = CommonUtils.SqlLiteral(x);
        if (!isEmpty && StringUtils.isEmpty(x))
        {
            x = null;
        }
        log.debug("setString(" + parameterIndex + "," + x + ")");
        ps.setString(parameterIndex, x);
    }

    //2018/03/31 yamamoto
    public void setStringNoneSqlLiteral(int parameterIndex, String x) throws SQLException {
        if (StringUtils.isEmpty(x))
        {
            x = null;
        }
        log.debug("setString(" + parameterIndex + "," + x + ")");
        ps.setString(parameterIndex, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setTime(int, java.sql.Time)
     */
    public void setTime(int parameterIndex, Time x) throws SQLException {
        log.debug("setTime(" + parameterIndex + "," + x + ")");
        ps.setTime(parameterIndex, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setTime(int, java.sql.Time, java.util.Calendar)
     */
    public void setTime(int parameterIndex, Time x, Calendar cal)
            throws SQLException {
        log.debug("setTime(" + parameterIndex + "," + x + "," + cal + ")");
        ps.setTime(parameterIndex, x, cal);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setTimestamp(int, java.sql.Timestamp)
     */
    public void setTimestamp(int parameterIndex, Timestamp x)
            throws SQLException {
        log.debug("setTimestamp(" + parameterIndex + "," + x + ")");
        ps.setTimestamp(parameterIndex, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setTimestamp(int, java.sql.Timestamp, java.util.Calendar)
     */
    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal)
            throws SQLException {
        log.debug("setTimestamp(" + parameterIndex + "," + x + "," + cal + ")");
        ps.setTimestamp(parameterIndex, x, cal);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setURL(int, java.net.URL)
     */
    public void setURL(int parameterIndex, URL x) throws SQLException {
        log.debug("setURL(" + parameterIndex + "," + x + ")");
        ps.setURL(parameterIndex, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setUnicodeStream(int, java.io.InputStream, int)
     */
    public void setUnicodeStream(int parameterIndex, InputStream x, int length)
            throws SQLException {
        log.debug("setUnicodeStream(" + parameterIndex + "," + x + "," + length
                + ")");
        ps.setUnicodeStream(parameterIndex, x, length);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#addBatch(java.lang.String)
     */
    public void addBatch(String sql) throws SQLException {
        log.debug("addBatch(" + sql + ")");
        ps.addBatch(sql);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#cancel()
     */
    public void cancel() throws SQLException {
        log.debug("cancel()");
        ps.cancel();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#clearBatch()
     */
    public void clearBatch() throws SQLException {
        log.debug("clearBatch()");
        ps.clearBatch();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#clearWarnings()
     */
    public void clearWarnings() throws SQLException {
        log.debug("clearWarnings()");
        ps.clearWarnings();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#close()
     */
    public void close() throws SQLException {
        log.debug("close()");
        ps.close();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#execute(java.lang.String)
     */
    public boolean execute(String sql) throws SQLException {
        log.debug("execute(" + sql + ")");
        return ps.execute(sql);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#execute(java.lang.String, int)
     */
    public boolean execute(String sql, int autoGeneratedKeys)
            throws SQLException {
        log.debug("execute(" + sql + "," + autoGeneratedKeys + ")");
        return ps.execute(sql, autoGeneratedKeys);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#execute(java.lang.String, int[])
     */
    public boolean execute(String sql, int[] columnIndexes)
            throws SQLException {
        log.debug("execute(" + sql + "," + columnIndexes + ")");
        return ps.execute(sql, columnIndexes);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#execute(java.lang.String, java.lang.String[])
     */
    public boolean execute(String sql, String[] columnNames)
            throws SQLException {
        log.debug("execute(" + sql + "," + columnNames + ")");
        return ps.execute(sql, columnNames);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#executeBatch()
     */
    public int[] executeBatch() throws SQLException {
        log.debug("executeBatch()");
        return ps.executeBatch();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#executeQuery(java.lang.String)
     */
    public ResultSet executeQuery(String sql) throws SQLException {
        log.debug("executeQuery(" + sql + ")");
        return ps.executeQuery(sql);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#executeUpdate(java.lang.String)
     */
    public int executeUpdate(String sql) throws SQLException {
        log.debug("executeUpdate(" + sql + ")");
        return ps.executeUpdate(sql);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#executeUpdate(java.lang.String, int)
     */
    public int executeUpdate(String sql, int autoGeneratedKeys)
            throws SQLException {
        log.debug("executeUpdate(" + sql + "," + autoGeneratedKeys + ")");
        return ps.executeUpdate(sql, autoGeneratedKeys);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#executeUpdate(java.lang.String, int[])
     */
    public int executeUpdate(String sql, int[] columnIndexes)
            throws SQLException {
        log.debug("executeUpdate(" + sql + "," + columnIndexes + ")");
        return ps.executeUpdate(sql, columnIndexes);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#executeUpdate(java.lang.String, java.lang.String[])
     */
    public int executeUpdate(String sql, String[] columnNames)
            throws SQLException {
        log.debug("executeUpdate(" + sql + "," + columnNames + ")");
        return ps.executeUpdate(sql, columnNames);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getConnection()
     */
    public Connection getConnection() throws SQLException {
        log.debug("getConnection()");
        return ps.getConnection();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getFetchDirection()
     */
    public int getFetchDirection() throws SQLException {
        log.debug("getFetchDirection()");
        return ps.getFetchDirection();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getFetchSize()
     */
    public int getFetchSize() throws SQLException {
        log.debug("getFetchSize()");
        return ps.getFetchSize();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getGeneratedKeys()
     */
    public ResultSet getGeneratedKeys() throws SQLException {
        log.debug("getGeneratedKeys()");
        return ps.getGeneratedKeys();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getMaxFieldSize()
     */
    public int getMaxFieldSize() throws SQLException {
        log.debug("getMaxFieldSize()");
        return ps.getMaxFieldSize();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getMaxRows()
     */
    public int getMaxRows() throws SQLException {
        log.debug("getMaxRows()");
        return ps.getMaxRows();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getMoreResults()
     */
    public boolean getMoreResults() throws SQLException {
        log.debug("getMoreResults()");
        return ps.getMoreResults();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getMoreResults(int)
     */
    public boolean getMoreResults(int current) throws SQLException {
        log.debug("getMoreResults(" + current + ")");
        return ps.getMoreResults(current);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getQueryTimeout()
     */
    public int getQueryTimeout() throws SQLException {
        log.debug("getQueryTimeout()");
        return ps.getQueryTimeout();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getResultSet()
     */
    public ResultSet getResultSet() throws SQLException {
        log.debug("getResultSet()");
        return ps.getResultSet();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getResultSetConcurrency()
     */
    public int getResultSetConcurrency() throws SQLException {
        log.debug("getResultSetConcurrency()");
        return ps.getResultSetConcurrency();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getResultSetHoldability()
     */
    public int getResultSetHoldability() throws SQLException {
        log.debug("getResultSetHoldability()");
        return ps.getResultSetHoldability();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getResultSetType()
     */
    public int getResultSetType() throws SQLException {
        log.debug("getResultSetType()");
        return ps.getResultSetType();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getUpdateCount()
     */
    public int getUpdateCount() throws SQLException {
        log.debug("getUpdateCount()");
        return ps.getUpdateCount();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getWarnings()
     */
    public SQLWarning getWarnings() throws SQLException {
        log.debug("getWarnings()");
        return ps.getWarnings();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#setCursorName(java.lang.String)
     */
    public void setCursorName(String name) throws SQLException {
        log.debug("setCursorName(" + name + ")");
        ps.setCursorName(name);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#setEscapeProcessing(boolean)
     */
    public void setEscapeProcessing(boolean enable) throws SQLException {
        log.debug("setEscapeProcessing(" + enable + ")");
        ps.setEscapeProcessing(enable);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#setFetchDirection(int)
     */
    public void setFetchDirection(int direction) throws SQLException {
        log.debug("setFetchDirection(" + direction + ")");
        ps.setFetchDirection(direction);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#setFetchSize(int)
     */
    public void setFetchSize(int rows) throws SQLException {
        log.debug("setFetchSize(" + rows + ")");
        ps.setFetchSize(rows);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#setMaxFieldSize(int)
     */
    public void setMaxFieldSize(int max) throws SQLException {
        log.debug("setMaxFieldSize(" + max + ")");
        ps.setMaxFieldSize(max);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#setMaxRows(int)
     */
    public void setMaxRows(int max) throws SQLException {
        log.debug("setMaxRows(" + max + ")");
        ps.setMaxRows(max);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#setQueryTimeout(int)
     */
    public void setQueryTimeout(int seconds) throws SQLException {
        log.debug("setQueryTimeout(" + seconds + ")");
        ps.setQueryTimeout(seconds);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#isClosed()
     */
    public boolean isClosed() throws SQLException {
        log.debug("isClosed()");
        return ps.isClosed();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#isPoolable()
     */
    public boolean isPoolable() throws SQLException {
        log.debug("isPoolable()");
        return ps.isPoolable();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#setPoolable(boolean)
     */
    public void setPoolable(boolean poolable) throws SQLException {
        log.debug("setPoolable(" + poolable + ")");
        ps.setPoolable(poolable);
    }

    /* (non-Javadoc)
     * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
     */
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        log.debug("isWrapperFor(" + iface + ")");
        return ps.isWrapperFor(iface);
    }

    /* (non-Javadoc)
     * @see java.sql.Wrapper#unwrap(java.lang.Class)
     */
    public <T> T unwrap(Class<T> iface) throws SQLException {
        log.debug("unwrap(" + iface + ")");
        return ps.unwrap(iface);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setAsciiStream(int, java.io.InputStream)
     */
    public void setAsciiStream(int parameterIndex, InputStream x)
            throws SQLException {
        log.debug("setAsciiStream(" + parameterIndex + "," + x + ")");
        ps.setAsciiStream(parameterIndex, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setAsciiStream(int, java.io.InputStream, long)
     */
    public void setAsciiStream(int parameterIndex, InputStream x, long length)
            throws SQLException {
        log.debug("setAsciiStream(" + parameterIndex + "," + x + "," + length
                + ")");
        ps.setAsciiStream(parameterIndex, x, length);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setBinaryStream(int, java.io.InputStream)
     */
    public void setBinaryStream(int parameterIndex, InputStream x)
            throws SQLException {
        log.debug("setBinaryStream(" + parameterIndex + "," + x + ")");
        ps.setBinaryStream(parameterIndex, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setBinaryStream(int, java.io.InputStream, long)
     */
    public void setBinaryStream(int parameterIndex, InputStream x, long length)
            throws SQLException {
        log.debug("setBinaryStream(" + parameterIndex + "," + x + "," + length
                + ")");
        ps.setBinaryStream(parameterIndex, x, length);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setBlob(int, java.io.InputStream)
     */
    public void setBlob(int parameterIndex, InputStream inputStream)
            throws SQLException {
        log.debug("setBlob(" + parameterIndex + "," + inputStream + ")");
        ps.setBlob(parameterIndex, inputStream);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setBlob(int, java.io.InputStream, long)
     */
    public void setBlob(int parameterIndex, InputStream inputStream,
            long length) throws SQLException {
        log.debug("setBlob(" + parameterIndex + "," + inputStream + "," + length
                + ")");
        ps.setBlob(parameterIndex, inputStream, length);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setCharacterStream(int, java.io.Reader)
     */
    public void setCharacterStream(int parameterIndex, Reader reader)
            throws SQLException {
        log.debug("setCharacterStream(" + parameterIndex + "," + reader + ")");
        ps.setCharacterStream(parameterIndex, reader);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setCharacterStream(int, java.io.Reader, long)
     */
    public void setCharacterStream(int parameterIndex, Reader reader,
            long length) throws SQLException {
        log.debug("setCharacterStream(" + parameterIndex + "," + reader + ","
                + length + ")");
        ps.setCharacterStream(parameterIndex, reader, length);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setClob(int, java.io.Reader)
     */
    public void setClob(int parameterIndex, Reader reader) throws SQLException {
        log.debug("setClob(" + parameterIndex + "," + reader + ")");
        ps.setClob(parameterIndex, reader);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setClob(int, java.io.Reader, long)
     */
    public void setClob(int parameterIndex, Reader reader, long length)
            throws SQLException {
        log.debug("setClob(" + parameterIndex + "," + reader + "," + length
                + ")");
        ps.setClob(parameterIndex, reader, length);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setNCharacterStream(int, java.io.Reader)
     */
    public void setNCharacterStream(int parameterIndex, Reader value)
            throws SQLException {
        log.debug("setNCharacterStream(" + parameterIndex + "," + value + ")");
        ps.setNCharacterStream(parameterIndex, value);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setNCharacterStream(int, java.io.Reader, long)
     */
    public void setNCharacterStream(int parameterIndex, Reader value,
            long length) throws SQLException {
        log.debug("setNCharacterStream(" + parameterIndex + "," + value + ","
                + length + ")");
        ps.setNCharacterStream(parameterIndex, value, length);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setNClob(int, java.sql.NClob)
     */
    public void setNClob(int parameterIndex, NClob value) throws SQLException {
        log.debug("setNClob(" + parameterIndex + "," + value + ")");
        ps.setNClob(parameterIndex, value);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setNClob(int, java.io.Reader)
     */
    public void setNClob(int parameterIndex, Reader reader)
            throws SQLException {
        log.debug("setNClob(" + parameterIndex + "," + reader + ")");
        ps.setNClob(parameterIndex, reader);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setNClob(int, java.io.Reader, long)
     */
    public void setNClob(int parameterIndex, Reader reader, long length)
            throws SQLException {
        log.debug("setNClob(" + parameterIndex + "," + reader + "," + length
                + ")");
        ps.setNClob(parameterIndex, reader, length);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setNString(int, java.lang.String)
     */
    public void setNString(int parameterIndex, String value)
            throws SQLException {
        log.debug("setNString(" + parameterIndex + "," + value + ")");
        ps.setNString(parameterIndex, value);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setRowId(int, java.sql.RowId)
     */
    public void setRowId(int parameterIndex, RowId x) throws SQLException {
        log.debug("setRowId(" + parameterIndex + "," + x + ")");
        ps.setRowId(parameterIndex, x);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setSQLXML(int, java.sql.SQLXML)
     */
    public void setSQLXML(int parameterIndex, SQLXML xmlObject)
            throws SQLException {
        log.debug("setSQLXML(" + parameterIndex + "," + xmlObject + ")");
        ps.setSQLXML(parameterIndex, xmlObject);
    }

    @Override
    public void closeOnCompletion() throws SQLException {
        log.debug("close()");
        ps.close();
    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        return ps.isClosed();
    }

}
