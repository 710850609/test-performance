package core.table;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author LinBo
 * @date 2019-09-25 17:10
 */
@Component
public class AccountInsert extends AbstractInsert {

    @Override
    public String getTableName() {
        return "account";
    }

    @Override
    public List<String> getColumns() {
        return Arrays.asList("id", "name", "pwd", "salt", "gender", "mobile", "state", "email", "id_card_no", "create_time", "update_time", "uid", "last_login_time", "last_login_ip", "error_login_count", "login_count");
    }

    @Override
    public List<Object> getValues() {
        return Arrays.asList("id", "name", "pwd", "salt", "gender", "mobile", "state", "email", "id_card_no", "create_time", "update_time", "uid", "last_login_time", "last_login_ip", "error_login_count", "login_count");
    }
}
