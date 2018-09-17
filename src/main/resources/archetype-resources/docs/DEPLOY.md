项目说明
springboot服务化打包大致要解决两点问题：

1.让springboot能够加载jar外的配置文件

2.提供一个服务化的启动脚本，这个脚本一般是shell或者windows下的bat ，有了springboot的应用服务脚本后，就可以很容易的去启动和停止springboot的应用了。


# 环境要求

jdk版本：jdk 1.8

系统：linux /windows

推荐部署在linux

# 项目部署

打包后的项目部署比较简单，开箱即可启动
```
//执行package打包,在target目录下会生成.gz的包
mvn clean package -P test 或者在ide中选择profile后执行package
//解压tar.gz包
tar -zxvf xxx-1.0-{dev}.tar.gz
//解压后启动脚本在项目的bin目录中，项目配置文件在config中，日志文件在logs目录中
```
解压后的项目大致结构
```
├─bin
│      dump.sh
│      server.sh
│      setenv.sh
│      start.bat
│      start.sh
│      stop.sh
│      yaml.sh   
├─config
│      application.yml
       application-test.yml
       log4j2.xml
├─docs
│      DEPLOY.md
├─lib
│      xxx.jar
       xxx.sources.jar  
└─logs
```

# 启动应用

第一种通过start.sh来启动
```
# 简单启动应用
./start.sh

# 使用目录隔离的多配置环境，可以用--env启动时指定环境，如用开发环境启动。
# 开发中可能会有其他配置文件，因此可以采用文件夹来隔离多环境，
./start.sh --env dev
# 以debug方式启动，此处debug为jvm环境的debug
./start.sh debug

# 启动并开启jmx监控

./start.sh jmx
# 获取应用当前的运行状态
./start.sh status
```
第二种通过server.sh来启动

```
# 启动应用

./server.sh

# 启动时指定环境，如用开发环境启动
./start.sh --env dev
# 以debug方式启动，此处debug为jvm环境的debug
./server.sh debug

# 启动任务并开启jmx监控

./server.sh jmx
# 获取当前的运行状态
./server.sh status

```
# 停止应用
```
./stop.sh
或
./server.sh stop
```
**注意：** 以上脚本如果不能正常执行请先检查脚本的用户权限
# 启动日志

启动日志在应用的logs下

# jvm参数调整

服务启动的jvm参数设置是在setenv.sh中，目前设置比较小，但是如果setenv.sh不存在，应用使用start.sh中默认的
jvm参数，强力推荐不要在start.sh中去修改jvm，设置也相对麻烦，因此推荐在setenv.sh中去设置jvm参数.
```
# set jvm params

export JAVA_OPTS="$JAVA_OPTS -Xms512m"
export JAVA_OPTS="$JAVA_OPTS -Xmx512m"
export JAVA_OPTS="$JAVA_OPTS -Xss256K"

# The hotspot server JVM has specific code-path optimizations
# which yield an approximate 10% gain over the client version.
export JAVA_OPTS="$JAVA_OPTS -server"

# Basically tell the JVM not to load awt libraries
export JAVA_OPTS="$JAVA_OPTS -Djava.awt.headless=true"

# set encoding
export JAVA_OPTS="$JAVA_OPTS -Dfile.encoding=utf-8"

# set garbage collector
# export java_OPTS="$JAVA_OPTS -XX:+UseConcMarkSweepGC"

```
