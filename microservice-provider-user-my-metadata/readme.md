# microservice-provider-user-my-metadata

> 简单的Eureka自定义元数据示例。Eureka的元数据分为两种，分别是标准元数据和自定义元数据。
> 标准元数据指的是主机名、IP地址、端口号、状态页和健康检查等信息，这些信息都会被发布在服务注册表中，用于服务之间的调用。自定义元数据可以使用eureka.instance.metadata-map配置。

* 只有一个自定义的接口:根据id获取用户信息
* 使用H2作为内存数据库
* 集成了actuator
* 集成了Eureka服务，可以自动注册到服务中心
* 在配置文件中定义了一个名为`my-metadata`的Eureka元数据