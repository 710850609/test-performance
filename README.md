# test-performance

## 介绍
性能测试使用到的一些东东，这里以MySQL数据库为例讲解，其他关系型数据库可以借鉴参考。

## 01 测试数据准备
<pre>在性能测试之前，如果还没大量数据沉淀，需要人为进行测试数据生成</pre>
[00_创建测速库表脚本.sql](01_测试数据准备/00_创建测速库表脚本.sql)

### 1、数据库脚本方式

#### 1.1、数据库随机类函数
| 数据库脚本名称 | 说明 |
| ------------ | ------------ |
| [01_rand_str.sql](01_测试数据准备/01_数据库随机类函数/01_rand_str.sql) | 生成固定长度随机字符串 |
| [02_rand_fixed_num.sql](01_测试数据准备/01_数据库随机类函数/02_rand_fixed_num.sql) | 生成固定长度的随机整数 |
| [03_rand_range_num.sql](01_测试数据准备/01_数据库随机类函数/03_rand_range_num.sql) | 生成指定范围的随机正整数 |
| [04_rand_date.sql](01_测试数据准备/01_数据库随机类函数/04_rand_date.sql) | 生成指定时间范围的随机时间 |
| [05_rand_chinese_name.sql](01_测试数据准备/01_数据库随机类函数/05_rand_chinese_name.sql) | 生成随机中文姓名，使用穷举法，单姓，名字1-2个字 |
| [06_rand_mobile.sql](01_测试数据准备/01_数据库随机类函数/06_rand_mobile.sql) | 生成随机手机号 |
| [07_rand_idcard_no.sql](01_测试数据准备/01_数据库随机类函数/07_rand_idcard_no.sql) | 生成随机手机号 |
| [08_rand_dict.sql](01_测试数据准备/01_数据库随机类函数/08_rand_dict.sql) | 生成随机字典数据 |
| [09_rand_ip.sql](01_测试数据准备/01_数据库随机类函数/09_rand_ip.sql) | 生成随机IP |

#### 1.2、数据库批量新增
[01_批量新增存储过程.sql](01_测试数据准备/02_数据库批量新增/01_批量新增存储过程.sql)

#### 1.3、编写程序方式新增
[batch-insert](01_测试数据准备/03_Java批量新增/batch-insert/README.md)

#### 1.4、Jmeter工具批量操作
[Jmeter批量新增](01_测试数据准备/04_jmeter批量新增/README.md)


## 02 性能测试
### 0201 [ApacheBench (ab压测)](02_性能测试\01_ApacheBench\README.md)
