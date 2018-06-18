# microservice-consumer-movie-understanding-metadata

> 简单的微服务示例,作为服务消费者，可以获取自定义的数据。
> 通过DiscoveryClient.getInstances(serviceId)，可查询指定微服务在Eureka上的示例列表

* 一共两个接口:1.根据id获取用户信息，调用微服务api获取用户信息，2.获取示例列表信息
* 集成了Eureka服务，可以自动注册到服务中心
* 访问[http://localhost:8010/1](http://localhost:8010/1),返回:
  
  ``` json
  {
  "id": 1,
  "username": "account1",
  "name": "张三",
  "age": 20,
  "balance": 100.00
}
  ```
* 访问[http://localhost:8010/user-instance](http://localhost:8010/user-instance) ,返回:
  
  ``` json
  [
  {
    "host": "192.168.229.1",
    "port": 8000,
    "metadata": {
      "my-metadata": "我自定义的元数据"
    },
    "uri": "http://192.168.229.1:8000",
    "instanceInfo": {
      "instanceId": "localhost:microservice-provider-user:8000",
      "app": "MICROSERVICE-PROVIDER-USER",
      "appGroupName": null,
      "ipAddr": "192.168.229.1",
      "sid": "na",
      "homePageUrl": "http://192.168.229.1:8000/",
      "statusPageUrl": "http://192.168.229.1:8000/info",
      "healthCheckUrl": "http://192.168.229.1:8000/health",
      "secureHealthCheckUrl": null,
      "vipAddress": "microservice-provider-user",
      "secureVipAddress": "microservice-provider-user",
      "countryId": 1,
      "dataCenterInfo": {
        "@class": "com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo",
        "name": "MyOwn"
      },
      "hostName": "192.168.229.1",
      "status": "UP",
      "leaseInfo": {
        "renewalIntervalInSecs": 30,
        "durationInSecs": 90,
        "registrationTimestamp": 1529286432086,
        "lastRenewalTimestamp": 1529286432086,
        "evictionTimestamp": 0,
        "serviceUpTimestamp": 1529286432086
      },
      "isCoordinatingDiscoveryServer": false,
      "metadata": {
        "my-metadata": "我自定义的元数据"
      },
      "lastUpdatedTimestamp": 1529286432086,
      "lastDirtyTimestamp": 1529286431958,
      "actionType": "ADDED",
      "asgName": null,
      "overriddenStatus": "UNKNOWN"
    },
    "secure": false,
    "serviceId": "MICROSERVICE-PROVIDER-USER"
  }
]
  
  ```