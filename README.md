# test-performance

## 介绍
性能测试使用到的一些东东

## 一、测试数据准备
<pre>在性能测试之前，如果还没大量数据沉淀，需要人为进行测试数据生成</pre>
这里以MySQL数据库为例讲解，其他关系型数据库可以借鉴参考。
使用到表的创建语言[00_创建测速库表脚本.sql](01_测试数据准备/00_创建测速库表脚本.sql)

### 1、[数据库随机类函数](01_测试数据准备/01_数据库随机类函数/README.md)

### 2、数据库批量新增
[01_批量新增存储过程.sql](01_测试数据准备/02_数据库批量新增/01_批量新增存储过程.sql)

### 3、[Java批量新增](01_测试数据准备/03_Java批量新增/batch-insert/README.md)
[batch-insert](01_测试数据准备/03_Java批量新增/batch-insert/README.md)

### 4、[Jmeter工具批量新增](01_测试数据准备/04_jmeter批量新增/README.md)


## 二、性能测试
### 1、[ApacheBench (ab压测)](02_性能测试/01_ApacheBench/README.md)
