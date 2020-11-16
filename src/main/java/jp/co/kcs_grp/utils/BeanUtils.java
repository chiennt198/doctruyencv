/**
 * ファイル名: BeanUtils.java
 * バージョン: 1.0.0
 * 作成日付: Jan 11, 2017
 * 最終更新日付: Jan 11, 2017
 * 作成者: KCS
 * 更新履歴: Jan 11, 2017 : 新規作成
 */

package jp.co.kcs_grp.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

/**
 * ファイル名: BeanUtils.java
 * バージョン: 1.0.0
 * 作成日付: 2016/02/03
 * 最終更新日付: 2016/02/03
 * 作成者: KCS
 * 更新履歴: 2016/02/03 : 新規作成
 */
public class BeanUtils
{
    private static final Logger logger = Logger.getLogger(BeanUtils.class);

    /**
     * Set data from Map to Object
     * @param map
     * @param obj
     * @return
     */
    public static Object mapBeanObject(Map<String, String[]> map, Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        fields = (Field[]) ArrayUtils.addAll(fields, getSupperClassField(obj.getClass()));
        String fieldName = "";
        String[] fieldValue = null;

        for (Field field : fields) {
            try {
                fieldName = field.getName();
                if (map.containsKey(fieldName)) {
                    fieldValue = map.get(fieldName);
                    org.apache.commons.beanutils.BeanUtils.setProperty(obj,
                            fieldName, fieldValue[0]);
                } else {
                    org.apache.commons.beanutils.BeanUtils.setProperty(obj,
                            fieldName, "");
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                logger.fatal(e.getMessage());
                e.printStackTrace();
            }
        }

        return obj;
    }
    
    /**
     * Set data from Map to Map
     * @param map
     * @param obj
     * @return
     */
    public static Map<String, Object> mapBeanObject(Map<String, String[]> map, Map<String, Object> object) {
        logger.info("start");
        
        for (String key : map.keySet()) {
        	object.put(key, map.get(key)[0]);
        }

        logger.info("end");
        return object;
    }

    /**
     * Set data from resultSet to Object
     * @param rs result set
     * @param obj Object data
     * @return
     */
    public static Object mapBeanObject(ResultSet rs, Object obj) {
        logger.info("start");
        Field[] currClassfields = obj.getClass().getDeclaredFields();
        Field[] superClassFields = getSupperClassField(obj.getClass());
        Field[] fields = (Field[]) ArrayUtils.addAll(currClassfields,
                superClassFields);
        String fieldName = "";
        String fieldValue = "";
        ResultSetMetaData rsmd;
        ArrayList<String> lstColName = null;

        // Get list column name of ResultSet
        try {
            rsmd = rs.getMetaData();
            int numOfCols = rsmd.getColumnCount();
            lstColName = new ArrayList<>();
            for (int i = 1; i < numOfCols + 1; i++) {
                lstColName.add(rsmd.getColumnLabel(i));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        // Set value of resultset to Object
        for (Field field : fields) {
            fieldName = field.getName();
            try {
                if (lstColName.contains(fieldName)
                        || lstColName.contains(fieldName.toUpperCase())) {
                    fieldValue = rs.getString(fieldName);
                    if (fieldValue == null) {
                        fieldValue = "";
                    }
                    org.apache.commons.beanutils.BeanUtils.setProperty(obj,
                            fieldName, fieldValue);
                } else {
                    org.apache.commons.beanutils.BeanUtils.setProperty(obj,
                            fieldName, "");
                }
            } catch (SQLException | IllegalAccessException
                    | InvocationTargetException e) {
                logger.fatal(e.getMessage());
                e.printStackTrace();
            }
        }

        logger.info("end");
        return obj;
    }

    /**
     * copy data from source object to another object
     * @param dest
     * @param orig
     */
    public static void copyProperties(Object dest, Object orig) {
        logger.info("start");

        try {
            org.apache.commons.beanutils.BeanUtils.copyProperties(dest, orig);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.fatal(e.getMessage());
            e.printStackTrace();
        }

        logger.info("end");
    }

    /**
     * get field of supper class
     * @param current
     * @return
     */
    private static Field[] getSupperClassField(Class<?> current)
    {
        Field[] retVal = null;
        Class<?> superClass = current.getSuperclass();
        if (superClass != null) {
            retVal = superClass.getDeclaredFields();
        }

        return retVal;
    }
    
    /**
     * Set data from Map<String, String[]> to Map<String, String>
     * @param Map<String, String[]>
     * @return
     */
    public static Map<String, String> mapBeanData(Map<String, String[]> map) {
        logger.info("start");
        Map<String, String> rtnMap = new HashMap<>();
        
        if (map == null ||  (map != null && map.isEmpty()) ) {
        	return rtnMap;
        }

        for (Entry<String, String[]> entry : map.entrySet()) {
        	 try {
        		 rtnMap.put(entry.getKey(), map.get(entry.getKey())[0]);
             } catch (Exception e) {
            	 StringWriter stack = new StringWriter();
                 e.printStackTrace(new PrintWriter(stack));
                 logger.error(stack.toString());
 			} 
        }
        logger.info("end");
        return rtnMap;

    }
}
