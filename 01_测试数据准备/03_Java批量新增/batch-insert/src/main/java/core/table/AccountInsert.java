package core.table;

import core.subassembly.SubassemblyManager;
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
        return Arrays.asList("id", "name", "pwd", "salt", "gender", "mobile", "state", "email", "id_card_no",
                "create_time", "update_time", "uid", "last_login_time", "last_login_ip", "error_login_count", "login_count");
    }

    @Override
    public List<Object> getValues() {
        return Arrays.asList(
            null,
            new String[]{SubassemblyManager.RANDOM_STRING_CHINESENAME},
            new String[]{SubassemblyManager.RANDOM_STRING_RANGE, "6", "12"},
            new String[]{SubassemblyManager.RANDOM_STRING_FIXED, "6"},
            new String[]{SubassemblyManager.RANDOM_STRING_RANGE, "0,1,2", "1"},
            new String[]{SubassemblyManager.RANDOM_STRING_MOBILE},
            null,
            new String[]{SubassemblyManager.RANDOM_STRING_EMAIL},
            new String[]{SubassemblyManager.RANDOM_STRING_ID_CARD_NO},
            new String[]{SubassemblyManager.RANDOM_DATE_RANGE, "2010-01-01 00:00:00", "2019-01-01 00:00:00"},
            null,
            new String[]{SubassemblyManager.RANDOM_STRING_UUID},
            new String[]{SubassemblyManager.RANDOM_DATE_RANGE, "2010-01-01 00:00:00", "2019-01-01 00:00:00"},
            new String[]{SubassemblyManager.RANDOM_STRING_IP},
            new String[]{SubassemblyManager.RANDOM_NUM_RANGE, "0", "3"},
            new String[]{SubassemblyManager.RANDOM_NUM_RANGE, "0", "1000"}
        );
    }

}
