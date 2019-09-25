package core.sql.dialect;

/**
 * @author LinBo
 * @date 2019-09-25 16:17
 */
public interface Dialect {

    int MYSQL = 1;
    int ORACLE = 2;

    /** 获取字段分隔符 */
    String getColumnSeparator();

}
