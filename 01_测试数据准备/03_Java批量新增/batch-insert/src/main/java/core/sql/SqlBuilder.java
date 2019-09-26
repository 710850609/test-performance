package core.sql;

import core.sql.dialect.Dialect;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * SqlBuilder
 */
@Data
public class SqlBuilder implements Dialect {

    /** 表名 */
    private String tableName;
    /** 字段类型 */
    private List<Class<?>> meta;
    /** 字段名 */
    private List<String> columns;
    /** 字段值 */
    private List<Object> values;

    public SqlBuilder() {
    }

    public SqlBuilder(String tableName, List<String> columns, List<Object> values) {
        this.tableName = tableName;
        this.columns = columns;
        this.values = values;
	}

    /**
     * 获取insert sql模板
     * @return
     */
    public String getInsertSqlTemplate(int num) {
        checkForInsert();
        StringBuilder sqlBuf = new StringBuilder("insert into ");
        sqlBuf.append(this.tableName).append(" (");
        int columnSize = columns.size();
        for (int i = 0; i < columnSize; i++) {
            sqlBuf.append(getColumnSeparator()).append(columns.get(i)).append(getColumnSeparator()).append(",");
        }
        sqlBuf.deleteCharAt(sqlBuf.length() - 1);
        sqlBuf.append(") values ");
        for (int i = 0; i < num; i++) {
            sqlBuf.append("(");
            for (int j = 0; j < columnSize; j++) {
                sqlBuf.append("?,");
            }
            sqlBuf.deleteCharAt(sqlBuf.length() - 1);
            sqlBuf.append("),");
        }
        sqlBuf.deleteCharAt(sqlBuf.length() - 1);
        return sqlBuf.toString();
    }

    /**
     * 检查insert参数
     */
    private void checkForInsert() {
        if (Strings.isBlank(this.tableName)) {
            throw new RuntimeException("表名为空");
        }
        if (CollectionUtils.isEmpty(columns)) {
            throw new RuntimeException("字段为空");
        }
    }

    @Override
    public String getColumnSeparator() {
        return "";
    }
}