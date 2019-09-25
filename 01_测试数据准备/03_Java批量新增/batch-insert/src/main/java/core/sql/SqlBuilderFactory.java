package core.sql;

import core.sql.dialect.Dialect;
import core.sql.dialect.MySQLSqlBuilder;
import core.sql.dialect.OracleSqlBuilder;

/**
 * @author LinBo
 * @date 2019-09-25 16:20
 */
public class SqlBuilderFactory {

    public static SqlBuilder getInstance(int dialect) {
        if (dialect == Dialect.MYSQL) {
            return new MySQLSqlBuilder();
        } else if (dialect == Dialect.ORACLE) {
            return new OracleSqlBuilder();
        } else {
            return new SqlBuilder();
        }
    }
}
