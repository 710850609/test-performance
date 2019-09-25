package core.sql.dialect;

import core.sql.SqlBuilder;

/**
 * @author LinBo
 * @date 2019-09-25 16:34
 */
public class OracleSqlBuilder extends SqlBuilder {

    @Override
    public String getColumnSeparator() {
        return "'";
    }
}
