# microservice-consumer-movie-feign-hystrix-fallback

> 简单的微服务示例,作为服务消费者，使用feign与服务提供者通信，测试Hystrix

* Feign的fallback，使用`@FeignClient`的fallback属性指定回退类
* 只有一个自定义的接口:根据id获取用户信息，调用微服务api获取用户信息
* 集成了Eureka服务，可以自动注册到服务中心
* 启动microservice-discovery-eureka。
* 启动microservice-provider-user。
  启动microservice-consumer-movie-feign-hystrix-fallback。
* 访问[http://localhost:8010/user/1](http://localhost:8010/user/1)，可正常获得结果
* 停止microservice-provider-user
* 再次访问[http://localhost:8010/user/1](http://localhost:8010/user/1),可获得如下结果。说明当用户微服务不可用时，进入了回退的逻辑.
 
``` json
{
  "id": -1,
  "username": null,
  "name": "默认用户",
  "age": null,
  "balance": null
}

```