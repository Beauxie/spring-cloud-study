# microservice-consumer-movie-ribbon

> 简单的微服务示例,作为服务消费者，整合Ribbon，实现请求负载均衡

## 说明

* Ribbon是Netflix发布的负载均衡器，有助于控制HTTP和TCP客户端的行为。有多种负载均衡算法，例如轮询、随机等，支持自定义。
* 由于spring-cloud-starter-eureka已经包含spring-cloud-starter-ribbon依赖，因此不需要再引入Ribbon
* 需要将`user.serviceUrl`改为`http://microservice-provider-user/`,其中microservice-provider-user是用户微服务的虚拟主机名,默认情况下，虚拟机主机名和服务名称是一致的，可以使用配置属性eureka.instance.virtual-host-name指定虚拟机主机名
* 为RestTemplate添加@LoadBalanced注解，开启负载均衡:
  
   ```java
         /**
     * 加上@LoadBalanced，就可为RestTemplate整合Ribbon，使其具备负载均衡的能力
     *
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        //解决中文乱码
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }
   ```
* 集成了Eureka服务，可以自动注册到服务中心
* 访问[http://localhost:8010/user/1](http://localhost:8010/user/1)

## 测试
 
1.启动microservice-discovery-eureka。
2.启动2个或多个microservice-provider-user示例。
3.启动microservice-consumer-movie-ribbon。
4.访问[http://localhost:8761](http://localhost:8761),可以看到:
   > MICROSERVICE-CONSUMER-MOVIE 对应一个服务
   > MICROSERVICE-PROVIDER_USER 对应多个服务
   
5.多次访问[http://localhost:8010/user/1](http://localhost:8010/user/1),返回以下结果；

```json
{
  "id": 1,
  "username": "account1",
  "name": "张三",
  "age": 20,
  "balance": 100.00
}

```
同时，两个用户微服务示例都会打印查询如下日志:

```
Hibernate: select user0_.id as id1_0_0_, user0_.age as age2_0_0_, user0_.balance as balance3_0_0_, user0_.name as name4_0_0_, user0_.username as username5_0_0_ from user user0_ where user0_.id=?
```

6.多次访问[http://localhost:8010/log-user-instance](http://localhost:8010/log-user-instance),控制台会打印如下语句:

```
2018-06-18 12:57:11.513  INFO 16764 --- [nio-8010-exec-1] c.b.c.study.controller.UserController    : microservice-provider-user:192.168.229.1:8001
2018-06-18 12:57:12.339  INFO 16764 --- [erListUpdater-0] c.netflix.config.ChainedDynamicProperty  : Flipping property: microservice-provider-user.ribbon.ActiveConnectionsLimit to use NEXT property: niws.loadbalancer.availabilityFilteringRule.activeConnectionsLimit = 2147483647
2018-06-18 12:57:13.800  INFO 16764 --- [nio-8010-exec-3] c.b.c.study.controller.UserController    : microservice-provider-user:192.168.229.1:8000
2018-06-18 12:57:15.987  INFO 16764 --- [nio-8010-exec-5] c.b.c.study.controller.UserController    : microservice-provider-user:192.168.229.1:8001
2018-06-18 12:57:16.752  INFO 16764 --- [nio-8010-exec-7] c.b.c.study.controller.UserController    : microservice-provider-user:192.168.229.1:8000
2018-06-18 12:57:17.439  INFO 16764 --- [nio-8010-exec-9] c.b.c.study.controller.UserController    : microservice-provider-user:192.168.229.1:8001
2018-06-18 12:57:18.104  INFO 16764 --- [nio-8010-exec-1] c.b.c.study.controller.UserController    : microservice-provider-user:192.168.229.1:8000
```