# microservice-provider-user-with-auth

> 简单的服务提供者示例

* 集成了`spring security`，api需要登录验证
* 一共有两个用户:`user/password1`、`admin/password2`
* 只有一个自定义的接口:根据id获取用户信息,首次访问时需要登录；
* 使用H2作为内存数据库
* 集成了actuator
* 集成了Eureka服务，可以自动注册到服务中心