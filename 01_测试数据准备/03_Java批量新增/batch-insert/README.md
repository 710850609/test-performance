# Java 版本批量insert 关系型数据库 使用步骤
<pre>此版本支持MySQL数据库，如需要其他支持JDBC数据库新增的，需要自行添加对应JDBC驱动</pre>
## 一、修改数据库连接方式
- 1、找到```01_测试数据准备/03_Java批量新增/batch-insert/src/main/resources/application.properties```配置文件
- 2、修改```spring.datasource.url```、```spring.datasource.username```、```spring.datasource.password```参数

## 二、声明数据库表字段名和对应字段值
- 1、创建一个类，继承[```core.table.AbstractInsert```](src/main/java/core/table/AbstractInsert.java)。
- 2、添加spring的```@Component```注解。
- 3、重写```String getTableName()```方法，该方法返回数据库表名。
- 4、重写```List<String> getColumns()```方法，该方法返回表的字段集合。
- 5、重写```List<Object> getValues()```方法，该方法返回表的字段对应的取值。

| 常用字段值写法 | 说明 |
| ----| ---- |
| ```null``` | null |
| ```[String]``` | 常量字符串 |
| ```[number]``` | 常量数值 |
| ```new String[]{SubassemblyManager.RANDOM_STRING_CHINESENAME}``` | 随机中国姓名 |
| ```new String[]{SubassemblyManager.RANDOM_STRING_MOBILE}``` | 随机手机号 |
| ```new String[]{SubassemblyManager.RANDOM_STRING_EMAIL}``` | 随机电子邮箱 |
| ```new String[]{SubassemblyManager.RANDOM_STRING_ID_CARD_NO}``` | 随机身份证件号 |
| ```new String[]{SubassemblyManager.RANDOM_STRING_UUID}``` | 随机UUID |
| ```new String[]{SubassemblyManager.RANDOM_STRING_IP}``` | 随机IPv4地址 |
| ```new String[]{SubassemblyManager.RANDOM_STRING_RANGE, "6", "12"}  ``` | 随机IPv4地址 |


参考[```core.table.AccountInsert```](01_测试数据准备/03_Java批量新增/batch-insert/src/main/java/core/table/AccountInsert.java)
![AccountInsert](https://upload.cc/i1/2019/09/29/CpsWqO.png)

## 三、创建批量任务
- 1、在[```core.task.TaskManager```](src/main/java/core/task/TaskManager.java)类中增加上一步创建的映射类

- 2、定义批量新增的记录数

![TaskManager](https://upload.cc/i1/2019/09/29/PgfSJy.png)

## 三、执行批量新增
执行[```core.MainApplication```](src/main/java/core/MainApplication.java)类

![执行结果](https://upload.cc/i1/2019/09/29/kCoznu.png)