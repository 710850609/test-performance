package com.bob.test.cases.junit;

import com.bob.test.core.report.JunitExtentReport;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@ExtendWith(JunitExtentReport.class)
@SelectClasses({
        Db.class,
        Pay.class,
//        Refund.class
})
public class JunitSuite {
}
