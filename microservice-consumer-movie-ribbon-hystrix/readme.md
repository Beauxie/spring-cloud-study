# microservice-consumer-movie-ribbon-hystrix

> 简单的微服务示例,作为服务消费者，整合Ribbon、hystrix，实现请求负载均衡

## 说明
*使用Hystrix可以对应用进行容错，该项目主要测试Hystrix的回退机制，并集成了Actuator
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
3.启动microservice-consumer-movie-ribbon-hystrix。
4.访问[http://localhost:8761](http://localhost:8761),可以看到:
   > MICROSERVICE-CONSUMER-MOVIE 对应一个服务
   > MICROSERVICE-PROVIDER_USER 对应一个服务
   
5.访问[http://localhost:8010/user/1](http://localhost:8010/user/1),返回以下结果；

```json
{
  "id": 1,
  "username": "account1",
  "name": "张三",
  "age": 20,
  "balance": 100.00
}

```
此时，访问[http://localhost:8010/health](http://localhost:8010/health)：

``` json
{
  "description": "Spring Cloud Eureka Discovery Client",
  "status": "UP",
  "discoveryComposite": {
    "description": "Spring Cloud Eureka Discovery Client",
    "status": "UP",
    "discoveryClient": {
      "description": "Spring Cloud Eureka Discovery Client",
      "status": "UP",
      "services": [
        "microservice-consumer-movie",
        "microservice-provider-user"
      ]
    },
    "eureka": {
      "description": "Remote status from Eureka server",
      "status": "UP",
      "applications": {
        "MICROSERVICE-CONSUMER-MOVIE": 1,
        "MICROSERVICE-PROVIDER-USER": 1
      }
    }
  },
  "diskSpace": {
    "status": "UP",
    "total": 212689256448,
    "free": 199898849280,
    "threshold": 10485760
  },
  "refreshScope": {
    "status": "UP"
  },
  "hystrix": {
    "status": "UP"
  }
}
```

此时，hystrix的状态为`UP`

6.停掉microservice-provider-user,再次访问,[http://localhost:8010/user/1](http://localhost:8010/user/1)，获得如下结果:

``` json
{
  "id": -1,
  "username": null,
  "name": "默认用户",
  "age": null,
  "balance": null
}

```

说明当用户微服务不可用时，进入了回退方法。

然后，多次访问访问[http://localhost:8010/user/1](http://localhost:8010/user/1)以后(默认5秒内失败20次才会打开断路器)，再访问[http://localhost:8010/health](http://localhost:8010/health):

```json
{
  "description": "Spring Cloud Eureka Discovery Client",
  "status": "UP",
  "discoveryComposite": {
    "description": "Spring Cloud Eureka Discovery Client",
    "status": "UP",
    "discoveryClient": {
      "description": "Spring Cloud Eureka Discovery Client",
      "status": "UP",
      "services": [
        "microservice-consumer-movie",
        "microservice-provider-user"
      ]
    },
    "eureka": {
      "description": "Remote status from Eureka server",
      "status": "UP",
      "applications": {
        "MICROSERVICE-CONSUMER-MOVIE": 1,
        "MICROSERVICE-PROVIDER-USER": 1
      }
    }
  },
  "diskSpace": {
    "status": "UP",
    "total": 212689256448,
    "free": 199898849280,
    "threshold": 10485760
  },
  "refreshScope": {
    "status": "UP"
  },
  "hystrix": {
    "status": "CIRCUIT_OPEN",
    "openCircuitBreakers": [
      "MovieController::findById"
    ]
  }
}

```
此时，hystrix的状态为`CIRCUIT_OPEN`,表示已打开



## Hsytrix线程隔离策略与传播上下文

### 隔离策略

* THREAD（线程隔离）:使用该方式，HystrixCommand将会在单独的线程上执行，并发请求受线程池中的线程数量限制
* SEMAPHORE(信号量隔离)：使用该方式，HystrixCommand将会调用线程上执行，开销相对较小，并发请求受到信号量个数的限制
* 如果发生找不到上下文的运行时异常时，可考虑将隔离策略设置为SEMAPHORE

Hystrix默认并推荐使用**线程隔离**，因为这种方式有一个除网络超时以外有一个额外保护层。




