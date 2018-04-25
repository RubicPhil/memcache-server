##                           **How to run the cache server**

环境要求：Linux2.6及以上内核，jdk1.8（保证环境变量JAVA_HOME、JRE_HOME全局有效）

监听端口：8083

启动方式：

1. 把jar包和shell脚本放到同一目录下;
2. shell脚本需要可执行权限（chmod 777 start.sh）;
3. $ sh start.sh（或./start.sh）.

##                                   **Running Example**

（1） 连接服务器（可支持多个客户端同时连接）

​	$ telnet  你的ip地址  8083

（2）命令支持：

1. <set> <key> <flags> <exptime> <bytes> [noreply]\r\n 

   发送命令行和数据区块以后，客户端等待回复，可能的回复如下：

   - "STORED\r\n" 	表明成功
   - "CLIENT_ERROR bad data chunk\r\n "  表明数据块长度超过bytes

2. get <key>*\r\n

   发送命令行（<key>* 表示一个或多个key）以后，客户端的等待0个或多个项目，每项都会收到一行文本，然后跟着数据区块。所有项目传送完毕后，服务器发送以下字串：
   "END\r\n"来指示回应完毕。

   服务器用以下形式发送每项内容

   - "VALUE <key> <flags> <bytes>\r\n"  返回一行或多行数据行

3. delete <key> [noreply]\r\n

   此命令有一行回应：

   - "DELETED\r\n"  表示执行成功
   - "NOT_FOUND\r\n" 表示没有找到这项内容


（3）错误

每一个由客户端发送的命令，都可能收到来自服务器的错误应答。这些错误有3种形式：

- "ERROR\r\n"
  意味着客户端发送了不存在的命令名称。
- "CLIENT_ERROR <error>\r\n"
  意味着输入的命令行里存在一些客户端错误，例如输入未遵循协议。<error>部分是易于理解的错误说明（如bad data chunk）……
- "SERVER_ERROR <error>\r\n"
  意味着一些服务器错误，导致命令无法执行。<error>部分是易于理解的错误说明。在一些服务器发生严重错误的情形下（通常应该不会遇到，RuntimeException），服务器将在发送这行错误后关闭连接。这是服务器主动关闭连接的唯一情况。

## 设计思路

（1）协议分析

![memcache协议](E:\我的项目\攀登微服务\Demo项目\自己实现memcache\memcache协议.png)

（2）项目结构

```
memcache-server
├── memcache-common -- memcache服务器公共模块
├── memcache-config -- 配置模块
├── memcache-remoting -- memcache服务器通讯模块
|    ├── memcache-remoting-server -- 服务器socket通信，基于线程池实现多客户端连接
|    ├── memcache-remoting-work -- 服务器处理请求的任务
|    ├── ServerBootstrap -- 服务器启动类
├── memcache-cache -- 缓存处理模块
|    ├── memcache-cache-impl -- 缓存命令的处理实现类
|    ├── CommandHandler -- 缓存命令的接口
|    ├── CommandHandlerFactory -- 负责生成缓存命令处理类实例的工厂
|    ├── Cache -- 缓存空间的提供者，采用ConcurrentHashMap类 

```

（3）技术点

​	1、通讯模块

​		采用BIO + 线程池来实现多客户端连接的要求。线程池采用FixedThreadPool，线程数默认为2 * Cpu核数。

​	2、缓存实现

​		采用的是ConcurrentHashMap，由于缓存存储类在多线程环境工作，所以需要使用线程安全且效率高的集合实现。

​	3、命令交互

​		抽象了set、get、delete命令交互操作的方法，根据单一职责原则有不同的实现类；并且操作类的实例的生成由工厂模式来完成，方便扩展。

 





