package core.table;

import core.sql.SqlBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author LinBo
 * @date 2019-09-25 17:18
 */
@Slf4j
public abstract class AbstractInsert {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SqlBuilder sqlBuilder;

    public abstract String getTableName();

    public abstract List<String> getColumns();

    public abstract List<Object> getValues();

    public AbstractInsert() {
         sqlBuilder = new SqlBuilder(getTableName(), getColumns(), getValues());
    }

    public void insert(int num) {
        String sqlTemplate = sqlBuilder.getInsertSqlTemplate(num);
        log.info("insert SQL: {}", sqlTemplate);
        System.out.println(jdbcTemplate);
    }
}
