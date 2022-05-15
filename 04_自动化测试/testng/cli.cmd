@REM 命令行模式启动，参考命令。命令格式： java -Dxxx1=xxx1 -Dxxx2=xxx2 -jar testng-1.0-SNAPSHOT.jar
@REM -Dxxx=xxx 表示传入参数key=参数value
@REM spring.profiles.active： 运行环境
@REM testngCli： testng 运行的xml配置文件
@REM file.encoding： jvm执行字符编码，需要是UTF8，避免报告乱码
java -jar -Dspring.profiles.active=test -DtestngCli=./classes/testng.xml -Dfile.encoding=UTF-8 testng-1.0-SNAPSHOT.jar