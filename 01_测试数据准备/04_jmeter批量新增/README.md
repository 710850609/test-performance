# Jmeter数据库批量新增

这里使用Jmeter5.1.1版本，需要对Jmeter会继续基础操作。

如果需要进行体验，导入 [jmx文件](01_测试数据准备/04_jmeter批量新增/JDBC-Request.jmx) 到Jmeter中。

使用到的 [mysql-connector-java-5.1.48.jar](01_测试数据准备/04_jmeter批量新增/mysql-connector-java-5.1.48.jar)、[jmeter-function-plugins](01_测试数据准备/04_jmeter批量新增/jmeter-function-plugins-1.0-SNAPSHOT.jar)。


## 一、适用情况
- 1、直接对数据表进行重复性操作
- 2、数据有严格的创建函数或存储过程限制，不能通过编写数据库存储过程实现
- 3、需要一些生成随机数据

## 二、Jmeter数据库操作计划
### 1、创建线程组
![创建线程组](https://upload.cc/i1/2019/09/29/MWuo7z.png)
![导入数据库驱动jar](https://upload.cc/i1/2019/09/29/6Efs2x.png)

### 2、创建JDBC连接配置
- 1、创建JDBC连接配置
- 2、设置数据库连接池名称，后面使用
- 3、设置JDBC连接参数，包括url、驱动类名、用户、密码

![创建JDBC连接配置](https://upload.cc/i1/2019/09/29/aNxV8k.png)

### 3、创建数据库操作请求
- 1、创建JDBC请求
- 2、指定JDBC请求使用到的数据连接池名称，上一步已经定义的。
- 3、编写数据库操作SQL，字段值可以是常量，也可以是变量。变量写法符合Jmeter变量写法，使用```${}```包括起来。

![创建数据库操作请求](https://upload.cc/i1/2019/09/29/vcP1gm.png)

### 4、创建字段随机值
- 1、创建用户变量
- 2、如果需要每次获取用户变量值时，需要进行重新计算，请勾选“每次迭代更细一次”
- 3、定义变量名和变量的取值。如果需要使用到Jmeter函数，可以使用Jemter函数助手进行帮助。

![创建字段随机值](https://upload.cc/i1/2019/09/29/SHzfmp.png)

### 5、执行计划

## 三、Jmeter函数
<code>Jmeter函数返回的都是字符串类型的数据，如果需要时间格式，可能需要额外的转时间处理</code>

<code>Jmeter函数助手目的是为了快速写出正确的Jmeter函数表达式</code>

<code>这里使用到自定义Jmeter函数，需要复制 [jmeter-function-plugins-1.0-SNAPSHOT.jar](01_测试数据准备/04_jmeter批量新增/jmeter-function-plugins-1.0-SNAPSHOT.jar) 到 %JMETER_HOME%/lib/ext下，并重启Jmeter</code>


- 1、Jmeter函数助手入口
![Jmeter函数助手入口](https://upload.cc/i1/2019/09/29/LIKrxq.png)
![Jmeter函数助手](https://upload.cc/i1/2019/09/29/Xl1oYw.png)
- 2、Jmeter函数助手使用
![Jmeter函数助手使用](https://upload.cc/i1/2019/09/29/ygjXpa.png)
- 3、函数说明

<code>这里只列举例子中使用到的一些函数，更多函数使用参考[Jmeter官网函数说明](https://jmeter.apache.org/usermanual/functions.html)</code>


| 函数名 | 使用例子 | 解释说明 | 
| ---- | ---- | ---- |
| __RandomString | ```${__RandomString(6,0123456789abcdefghijklmnopgrstuvwxyz,)}```| 从“0123456789abcdefghijklmnopgrstuvwxyz”中生成随机的6个字符串，可重复 |
| __time | ```${__time(yyyy-MM-dd HH:mm:sss,)}``` | 按指定时间格式，生成随机时间字符串 |
| __UUID |  ```${__UUID}``` | 生成UUID，包含“-” |
| __Random | ```${__Random(10,99,)}``` | 生成10到99的范围的一个数字，包含10和99 |
| __RandomChineseName | ```${__RandomChineseName}```| 生成随机中国姓名(非内置函数) |
| __RandomMobile | ```${__RandomMobile}```| 生成随机手机号码(非内置函数) |
| __RandomIdCardNo | ```${__RandomIdCardNo}```| 生成随机身份证件号(非内置函数) |
| __RandomEmail | ```${__RandomEmail}```| 生成随机电子邮箱(非内置函数) |
| __RandomIP | ```${__RandomIP}```| 生成随机IPv4地址(非内置函数) |

## 四、自定义Jmeter函数
上一步使用到的Jmeter自定义函数，需要进行Java编码。
参考项目 [jmeter-function-plugins](https://gitee.com/lin_bo/jmeter-function-plugins)