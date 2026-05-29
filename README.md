# springboot 4.0微服务框架搭建

## 前置条件

* JDK 25
* postgresql
* redis
* nacos
* skywalking

## 数据库

* attendance_db
* employee_db
* auth_db

## 解决启动日志中的2个WARNING

添加VM参数

````
--sun-misc-unsafe-memory-access=allow
--enable-native-access=ALL-UNNAMED
````

(1) 第一个WARNING

````
WARNING: A restricted method in java.lang.System has been called
WARNING: java.lang.System::loadLibrary has been called by io.netty.util.internal.NativeLibraryUtil in an unnamed module (file:/Users/bob/.gradle/caches/modules-2/files-2.1/io.netty/netty-common/4.2.12.Final/d35ffb9bf5cc0e05ae7408cf6a682b62dceceafc/netty-common-4.2.12.Final.jar)
WARNING: Use --enable-native-access=ALL-UNNAMED to avoid a warning for callers in this module
WARNING: Restricted methods will be blocked in a future release unless native access is enabled
````

(2) 第二个WARNING

````
WARNING: A terminally deprecated method in sun.misc.Unsafe has been called
WARNING: sun.misc.Unsafe::allocateMemory has been called by com.alibaba.nacos.shaded.io.grpc.netty.shaded.io.netty.util.internal.PlatformDependent0$2 (file:/Users/bob/.gradle/caches/modules-2/files-2.1/com.alibaba.nacos/nacos-client/3.1.1/5ff01c59bf5efcab267fee8dbc5579c84306996a/nacos-client-3.1.1.jar)
WARNING: Please consider reporting this to the maintainers of class com.alibaba.nacos.shaded.io.grpc.netty.shaded.io.netty.util.internal.PlatformDependent0$2
WARNING: sun.misc.Unsafe::allocateMemory will be removed in a future release
````

## 集成SkyWalking

````
-javaagent:/Your path/skywalking-agent.jar
-Dskywalking.agent.service_name=your-springboot-app
-Dskywalking.collector.backend_service=localhost:11800
````