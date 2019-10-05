# [ApacheBench](https://httpd.apache.org/)

## 一、工具介绍
### 1、简介
[Apache httpd](https://httpd.apache.org/)本身是一个提供http服务工具.其内含一个 [ApacheBench(ab)](http://httpd.apache.org/docs/2.4/programs/ab.html) 命令,可用于模拟并发HTTP请求.
ab命令使用说明,见官网

### 2、适用场景
- 1、只适用 http / https 协议
- 2、请求参数只能写死，不能随机产生,合适像查询这种幂等接口压测
- 3、产出简单报告（txt/html），不支持图


## 二、下载安装
### 1、Window下载安装
国内推荐[下载地址](https://www.apachelounge.com/download/)

下载后解压即可使用，推荐配置解压路径到系统环境变量，这样以后命令窗口就能识别到ab命令.

懒人一键下载: 
[httpd-2.4.41-win64-VS16.zip](https://www.apachelounge.com/download/VS16/binaries/httpd-2.4.41-win64-VS16.zip)、[httpd-2.4.41-win32-VS16.zip](https://www.apachelounge.com/download/VS16/binaries/httpd-2.4.41-win32-VS16.zip)


### 2、Linux安装
安装命令
 ```bash
 sudo apt  install apache2
 ```

 如果需要最小安装,不需要apache提供的http功能,则使用下面命令安装
```bash
sudo apt install apache2-utils
```

### 3、验证
在命令窗口输入``` ab -h ```命令后，能显示ab命令使用提示，即安装完成，如下。
```bash
~ » ab                                                                                                      linbo@LinBo
ab: wrong number of arguments
Usage: ab [options] [http[s]://]hostname[:port]/path
Options are:
    -n requests     Number of requests to perform
    -c concurrency  Number of multiple requests to make at a time
    -t timelimit    Seconds to max. to spend on benchmarking
                    This implies -n 50000
    -s timeout      Seconds to max. wait for each response
                    Default is 30 seconds
    -b windowsize   Size of TCP send/receive buffer, in bytes
    -B address      Address to bind to when making outgoing connections
    -p postfile     File containing data to POST. Remember also to set -T
    -u putfile      File containing data to PUT. Remember also to set -T
    -T content-type Content-type header to use for POST/PUT data, eg.
                    'application/x-www-form-urlencoded'
                    Default is 'text/plain'
    -v verbosity    How much troubleshooting info to print
    -w              Print out results in HTML tables
    -i              Use HEAD instead of GET
    -x attributes   String to insert as table attributes
    -y attributes   String to insert as tr attributes
    -z attributes   String to insert as td or th attributes
    -C attribute    Add cookie, eg. 'Apache=1234'. (repeatable)
    -H attribute    Add Arbitrary header line, eg. 'Accept-Encoding: gzip'
                    Inserted after all normal header lines. (repeatable)
    -A attribute    Add Basic WWW Authentication, the attributes
                    are a colon separated username and password.
    -P attribute    Add Basic Proxy Authentication, the attributes
                    are a colon separated username and password.
    -X proxy:port   Proxyserver and port number to use
    -V              Print version number and exit
    -k              Use HTTP KeepAlive feature
    -d              Do not show percentiles served table.
    -S              Do not show confidence estimators and warnings.
    -q              Do not show progress when doing more than 150 requests
    -l              Accept variable document length (use this for dynamic pages)
    -g filename     Output collected data to gnuplot format file.
    -e filename     Output CSV file with percentages served
    -r              Don't exit on socket receive errors.
    -m method       Method name
    -h              Display usage information (this message)
    -I              Disable TLS Server Name Indication (SNI) extension
    -Z ciphersuite  Specify SSL/TLS cipher suite (See openssl ciphers)
    -f protocol     Specify SSL/TLS protocol
                    (SSL2, TLS1, TLS1.1, TLS1.2 or ALL)
    -E certfile     Specify optional client certificate chain and private key
```

## 三、测试使用
### 1、命令格式
```bash
ab [选项/参数] [URL地址]
```

### 2、常用选项
| 参数 | 说明 |
| ---- | ---- |
| -c | 并发请求数量,默认1个 |
| -n | 总请求数量 |
| -T | 设置POST/PUT请求方式的Content-type请求头参数,如:<p>-T "application/x-www-form-urlencoded"</p> |
| -p | POST请求发送的请求数据,需要和-T参数一起配合使用 |
| -u | PUT请求发送的请求数据,需要和-T参数一起配合使用 |
| -m | 自定义HTTP请求方法,如DELETE/HEAD |
| -H | 添加自定义请求头参数,使用冒号分离请求头的key和value,格式如:<p>-H "Accept-Encoding: zip/zop;8bit"</p> |
|  |  |

### 3、使用例子

可以使用网上一些开发的服务.这里使用到一个[Java Web](https://github.com/710850609/api-demo)服务，只需要本地安装JDK8或JDK8以上版本，即可直接运行.

这里已经打包好程序,只要命令窗口切换到 
[api-demo-v1.0.jar](api-demo-v1.0.jar) 所在目录,执行命令``` java -jar api-demo-v1.0.jar --server.port=80 ```,80表示使用80端口启动,如果80端口被占用,需要修改成其他端口,相应下面的请求端口也要跟着修改.

#### 3.1、GET请求
![模拟请求](https://upload.cc/i1/2019/10/05/me4sPi.png)
模拟发起HTTP GET请求,并发10个,总共1000个请求,地址: http://10.10.67.16:80/users

##### 3.1.1、执行命令
```bash
ab -n 1000 -c 10 http://10.10.67.16:80/users
```

##### 3.1.2、输出结果
```bash
~ » ab -n 1000 -c 10 http://10.10.67.16:80/users                                                            linbo@LinBo
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking 10.10.67.16 (be patient)
Completed 100 requests
Completed 200 requests
Completed 300 requests
Completed 400 requests
Completed 500 requests
Completed 600 requests
Completed 700 requests
Completed 800 requests
Completed 900 requests
Completed 1000 requests
Finished 1000 requests

# 服务器信息
Server Software:
Server Hostname:        10.10.67.16
Server Port:            80

Document Path:          /users  # 请求路径
Document Length:        676 bytes   # 第一个成功返回的响应数据大小

Concurrency Level:      10  # 并发请求数量
Time taken for tests:   1.260 seconds   # 总耗时
Complete requests:      1000    # 总请求数量
Failed requests:        0   # 请求失败数量
Total transferred:      795000 bytes    # 从服务器接收数据大小
HTML transferred:       676000 bytes    # 接收HTML大小
Requests per second:    793.48 [#/sec] (mean)   # 平均每秒请求数(总请求数量/总耗时)
Time per request:       12.603 [ms] (mean)  # 平均每批请求耗时（一批=总请求数量/并发请求数量）
Time per request:       1.260 [ms] (mean, across all concurrent requests)   # 平均每个请求耗时
Transfer rate:          616.03 [Kbytes/sec] received # 从服务器接收数据流量

# 连接耗时详情
Connection Times (ms)   
#            最小值     均值     中值    最大值
              min  mean[+/-sd] median   max
Connect:        0    4  58.1      0    1063     # 与服务连接耗时
Processing:     2    4   5.2      3      72     # 服务器处理请求耗时
Waiting:        2    4   5.0      3      72     # 响应数据传输耗时
Total:          2    8  58.2      4    1066     # 总耗时

# 整体响应时间分布比
Percentage of the requests served within a certain time (ms)    
  50%      4
  66%      4
  75%      5
  80%      5
  90%      7
  95%      9
  98%     20
  99%     41
 100%   1066 (longest request)
```

#### 3.2、POST请求
![模拟请求](https://upload.cc/i1/2019/10/05/GMyiVH.png)
##### 3.2.1、构造请求参数
ab命令发起post请求，需要将参数放置到外部文件，用 -p 选项引入外部文件。文件中数据格式为

[key1]=[value1]&[key2]=[value2]&[key3]=[value3]

这里模拟post请求,新增用户
数据保存到[ab-addUser.txt](ab-addUser.txt)

##### 3.2.2、执行命令
```bash
ab -n 100 -c 10 -p "ab-addUser.txt" -T "application/json;charset=UTF-8" http://10.10.67.16:80/users
```
##### 3.2.3、输出结果
```bash
» ab -n 100 -c 10 -p "ab-addUser.txt" -T "application/json;charset=UTF-8" http://10.10.67.16:80/users
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking 10.10.67.16 (be patient).....done


Server Software:
Server Hostname:        10.10.67.16
Server Port:            80

Document Path:          /users
Document Length:        0 bytes

Concurrency Level:      10
Time taken for tests:   1.118 seconds
Complete requests:      100
Failed requests:        0
Total transferred:      9200 bytes
Total body sent:        20000
HTML transferred:       0 bytes
Requests per second:    89.48 [#/sec] (mean)
Time per request:       111.761 [ms] (mean)
Time per request:       11.176 [ms] (mean, across all concurrent requests)
Transfer rate:          8.04 [Kbytes/sec] received
                        17.48 kb/s sent
                        25.51 kb/s total

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0   11 101.7      1    1018
Processing:     3    8   6.3      6      35
Waiting:        3    8   6.3      6      35
Total:          4   19 101.6      7    1024

Percentage of the requests served within a certain time (ms)
  50%      7
  66%      8
  75%     11
  80%     12
  90%     17
  95%     25
  98%     36
  99%   1024
 100%   1024 (longest request)
 ```

##### 3.3、PUT请求

![模拟请求](https://upload.cc/i1/2019/10/05/2h5DoM.png)

##### 3.3.1、构造请求参数
跟POST请求差不多，需要进行请求参数准备，这里数据保存到[ab-updateUser.txt](ab-updateUser.txt)

##### 3.3.2、执行命令

```bash
ab -n 100 -c 10 -u "ab-updateUser.txt" -T "application/json;charset=UTF-8" http://10.10.67.16:80/users/1
```

##### 3.2.3、输出结果
```bash
» ab -n 100 -c 10 -u "ab-updateUser.txt" -T "application/json;charset=UTF-8" http://10.10.67.16:80/users/1
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking 10.10.67.16 (be patient).....done


Server Software:
Server Hostname:        10.10.67.16
Server Port:            80

Document Path:          /users/1
Document Length:        0 bytes

Concurrency Level:      10
Time taken for tests:   0.101 seconds
Complete requests:      100
Failed requests:        0
Total transferred:      9200 bytes
Total body sent:        21100
HTML transferred:       0 bytes
Requests per second:    994.81 [#/sec] (mean)
Time per request:       10.052 [ms] (mean)
Time per request:       1.005 [ms] (mean, across all concurrent requests)
Transfer rate:          89.38 [Kbytes/sec] received
                        204.98 kb/s sent
                        294.36 kb/s total

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    1   0.3      1       1
Processing:     3    9   7.4      6      46
Waiting:        3    8   6.8      5      46
Total:          4    9   7.5      6      47

Percentage of the requests served within a certain time (ms)
  50%      6
  66%      8
  75%     11
  80%     12
  90%     18
  95%     27
  98%     39
  99%     47
 100%     47 (longest request)
```

#### 3.4、DELETE请求
DELETE请求一般用于资源的删除.这里使用到删除用户接口,一次请求成功后,该用户会被删除,所以多次请求没实际意义,这里只是列举例子参考,一般用不到.
##### 3.4.1、执行命令
```bash
ab -m DELETE http://10.10.67.16/users/1
```
最后的路径参数1代表id=1的用户

##### 3.4.2、输出结果
```bash
» ab -m DELETE http://10.10.67.16/users/1
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking 10.10.67.16 (be patient).....done


Server Software:
Server Hostname:        10.10.67.16
Server Port:            80

Document Path:          /users/2
Document Length:        0 bytes

Concurrency Level:      1
Time taken for tests:   0.005 seconds
Complete requests:      1
Failed requests:        0
Total transferred:      92 bytes
HTML transferred:       0 bytes
Requests per second:    186.57 [#/sec] (mean)
Time per request:       5.360 [ms] (mean)
Time per request:       5.360 [ms] (mean, across all concurrent requests)
Transfer rate:          16.76 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        1    1   0.0      1       1
Processing:     5    5   0.0      5       5
Waiting:        5    5   0.0      5       5
Total:          5    5   0.0      5       5
```
