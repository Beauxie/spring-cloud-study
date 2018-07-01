# microservice-consumer-movie-feign-manual

> 简单的微服务示例,作为服务消费者，使用feign与服务提供者通信
* 调用需要登录认证的`microservice-provider-user-with-auth`服务接口;
* 集成了Eureka服务，可以自动注册到服务中心
* 访问[http://localhost:8010/user-user/1](http://localhost:8010/user-user/1)或[http://localhost:8010/user-admin/1](http://localhost:8010/user-admin/1)