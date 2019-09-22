# test-performance

## 介绍
性能测试使用到的一些东东，这里以MySQL数据库为例讲解，其他关系型数据库可以借鉴参考。

## 01 测试数据准备
<pre>在性能测试之前，如果还没大量数据沉淀，需要人为进行测试数据生成</pre>
### 1、数据库脚本方式

#### 1.1、常用数据库函数
| 数据库脚本名称 | 说明 |
| ------------ | ------------ |
| [01_rand_str.sql](01_测试数据准备/01_rand_str.sql) | 生成固定长度随机字符串 |
| [02_rand_fixed_num.sql](01_测试数据准备/02_rand_fixed_num.sql) | 生成固定长度的随机整数 |
| [03_rand_range_num.sql](01_测试数据准备/03_rand_range_num.sql) | 生成指定范围的随机正整数 |
| [04_rand_range_date.sql](01_测试数据准备/04_rand_range_date.sql) | 生成指定时间范围的随机时间 |
| [05_rand_chinese_name.sql](01_测试数据准备/05_rand_chinese_name.sql) | 生成随机中文姓名，使用穷举法，单姓，名字1-2个字 |

### 2、编写程序方式
