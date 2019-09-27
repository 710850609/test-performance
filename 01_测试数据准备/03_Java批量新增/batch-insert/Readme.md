# Java 版本批量insert 关系型数据库 使用步骤
<pre>此版本支持MySQL数据库，如需要其他支持JDBC数据库新增的，需要自行添加对应JDBC驱动</pre>

## 一、修改数据库连接方式
- 1、找到```01_测试数据准备\03_Java批量新增\batch-insert\src\main\resources\application.properties```配置文件
- 2、修改```spring.datasource.url```、```spring.datasource.username```、```spring.datasource.password```参数

## 二、声明数据库表字段名和对应字段值
模仿(```core.table.AccountInsert```)[01_测试数据准备\03_Java批量新增\batch-insert\src\main\java\core\table\AccountInsert.java]类，创建一个库表对应的类

## 三、创建批量任务
- 1、在(```core.task.TaskManager```)[01_测试数据准备\03_Java批量新增\batch-insert\src\main\java\core\task\TaskManager.java]类中增加上一步创建的映射类
- 2、定义批量新增的记录数

## 三、执行批量新增
执行(```core.MainApplication```)[01_测试数据准备\03_Java批量新增\batch-insert\src\main\java\core\MainApplication.java]类

