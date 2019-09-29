package core.task;

import core.table.AccountInsert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author LinBo
 * @date 2019-09-26 13:45
 */
@Component
public class TaskManager {

    @Autowired
    private AccountInsert accountInsert;

    @PostConstruct
    public void start() throws Exception {
        accountInsert.insert(10000);
    }
}
