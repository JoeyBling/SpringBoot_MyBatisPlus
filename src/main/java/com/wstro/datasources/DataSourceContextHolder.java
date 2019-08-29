package com.wstro.datasources;

/**
 * 数据源切换的工具类
 *
 * @author Joey
 * @Email 2434387555@qq.com
 */
public class DataSourceContextHolder {

    // 这里加了数据源名称记得containsDataSource方法里也要多加判断
    public static final String PRIMARY_DATA_SOURCE = "PRIMARY_DATA_SOURCE";
    public static final String DATA_SOURCE_B = "DATA_SOURCE_B";

    /**
     * 数据源类型
     */
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    /**
     * 设置数据源类型
     *
     * @param dbType 数据源类型
     */
    public static void setDbType(String dbType) {
        contextHolder.set(dbType);
    }

    /**
     * 获取数据源类型
     *
     * @return String
     */
    public static String getDbType() {
        return ((String) contextHolder.get());
    }

    /**
     * 清除数据源类型
     */
    public static void clearDbType() {
        contextHolder.remove();
    }

    /**
     * 判断指定DataSrouce当前是否存在
     *
     * @param dsId DataSrouce
     * @return boolean
     */
    public static boolean containsDataSource(String dsId) {
        if (null == dsId) {
            return false;
        } else if (!PRIMARY_DATA_SOURCE.equals(dsId) && !DATA_SOURCE_B.equals(dsId)) {
            return false;
        } else {
            return true;
        }
    }

}
