package core.table;

import core.sql.SqlBuilder;
import core.subassembly.SubassemblyManager;
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

    private static int batchInsertSize = 10000;

    public abstract String getTableName();

    public abstract List<String> getColumns();

    public abstract List<Object> getValues();

    public AbstractInsert() {
         sqlBuilder = new SqlBuilder(getTableName(), getColumns(), getValues());
    }

    public void insert(int num) throws Exception {
        long startTime = System.currentTimeMillis();
        int rowIndex = 0;
        while (rowIndex < num) {
            int curBatchSize = (num - rowIndex > batchInsertSize) ? batchInsertSize : num - rowIndex;
            String sqlTemplate = sqlBuilder.getInsertSqlTemplate(curBatchSize);
            List<Object> values = getValues();
            Object[] params = new Object[values.size() * curBatchSize];
            int paramIndex = 0;
            for (int i = 0; i < curBatchSize; i++) {
                for (int j = 0; j < values.size(); j++) {
                    Object paramMeta = values.get(j);
                    if (paramMeta != null && paramMeta.getClass().isArray()) {
                        // 数组结构，使用组件生成
                        String[] meta = (String[]) paramMeta;
                        params[paramIndex] = SubassemblyManager.getData(meta);
                    } else {
                        // 非数组结构，认为是常量
                        params[paramIndex] = paramMeta;
                    }
                    paramIndex++;
                }
                rowIndex++;
            }
//            log.info("SQL: {}", sqlTemplate);
//            log.info("SQL params: {}", Arrays.toString(params));
            int update = jdbcTemplate.update(sqlTemplate, params);
            log.info("影响记录数: {}", update);
        }
        log.info("批量新增 {} 条记录，耗时: {} s", num, (System.currentTimeMillis() - startTime) / 1000);
    }
}
