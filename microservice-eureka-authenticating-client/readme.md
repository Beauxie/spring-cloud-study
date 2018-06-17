# mircoservice-eureka-authenticating-client

> 简单的微服务示例,将服务注册到需要登录认证的Eureka Server中。 

* 集成了Eureka服务，可以自动注册到服务中心，需要登录验证服务，此时`defalutZone`的配置为:
   
  ```yml
    http://user:password@EUREKA_HOST:EUREKA_PORT/eureka/
  ```
  比如对于该例的配置为:

    ```yml
	eureka:
	  client:
	    service-url:
	      #default-zone: http://localhost:8761/eureka/
	      defaultZone: http://user:password123@localhost:8761/eureka/     #需要用户名密码认证才能注册
  ```
  用户名为`user`，密码为`password123`