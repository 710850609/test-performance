
# 基于springboot、junit5/testng、extentreports接口测试框架
## 一、框架解决的问题
### 1 编码接口自动化测试面临的问题
- 提高代码复用率
- 支持多种环境切换测试
- 简化测试用例相关编码

### 2 解决问题办法
- 分离用例逻辑和用例数据，提高用例的复用率
- 分离接口调用和用例，提高接口调用代码复用率

### 3 项目使用必要技术前提
- 会testng基础使用
- 会springboot基础使用
- 会mybatis-plus基础使用
- 会数据库SQL基础使用

## 二、项目结构说明
***(\*)是使用本项目基础编写测试用例编写的目录***
``` text
java/com/bob/test -----------------> 代码目录
| cases ---------------------------> 具体测试用例，主要是组织依赖参数、调用service、断言、传递变量
| core ----------------------------> 辅助框架实现
|--| Tests ------------------------> 自动化测试编码辅助工具使用入口
|--log ----------------------------> 打印并输出到报告相关实现
|--report -------------------------> 打印并输出到报告相关实现
|----| TestNgExtentReport ---------> testng报告监听实现
|----| JunitExtentReport ----------> junit5报告监听实现
| dao -----------------------------> 数据访问层（数据库操作）(*)
| service -------------------------> 接口调用实现(*)
| util ----------------------------> 辅助工具类(*)
| Application ---------------------> springboot启动类

resource --------------------------> 配置目录
| testng.xml ----------------------> 测试套件定义(*)
| application.properties ----------> springboot默认配置
| application-dev.properties ------> springboot dev环境配置
| application-dev.properties ------> springboot test环境配置
| loback-spring.xml ---------------> springboot logback日志框架配置
```

## 三、项目依赖
### 1 必要依赖
| 依赖库             | 说明                                                                                       |
|:----------------|:-----------------------------------------------------------------------------------------|
| testng          | 测试框架                                                                                     |
| extentreports相关 | 测试报告生成工具                                                                                 |
| lombok          | Java编译期自动生成基础代码工具                                                                        |
| springboot      | spring体系定义的一套java开发规范，减少开发配置、代码                                                          |

### 2 非必要依赖
| 依赖库           | 说明                                                                                       |
|:--------------|:-----------------------------------------------------------------------------------------|
| hutool-all    | 中国个人开源的一些开发高频使用基础代码封装，如CSV、Excel操作，HTTP客户端，对象、集合工具，编码、加解密工具等。在线文档：https://hutool.cn/docs |
| common-random | 随机生成工具，支持数字、日期、 地理、互联网、个人信息、金融、体育等随机数据。地址：https://github.com/shaoxiongdu/common-random   |
| fastjson      | JSON处理工具                                                                                 |
| httpclient    | HTTP客户端                                                                                  |
| mybatis.plus  | 数据库ORM工具                                                                                 |
| mockito             | mock工具                                                                                   |

## 四、其他
- 此项目运行前，需要在对应数据库执行 ```sql/db.sql```
- 如果配置了```com.bob.test.core.report.ExtentTestNgIReporterListener```作为testng监听器生成报告，报告文件在当前运行目录中的```report-output```目录。
- 如果需要执行```com.bob.test.cases.Db```相关用例，先在本地数据库执行```sql/db.sql```，修改对应环境的数据库链接配置，再执行。