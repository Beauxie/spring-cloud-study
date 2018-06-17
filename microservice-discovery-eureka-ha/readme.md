# microservice-discovery-eureka-ha

> 简单的Eureka服务注册中心集群使用示例,作为Eureka Server集群配置

* 1.需要在hosts中配置

 ```
  127.0.0.1   peer1
  127.0.0.1   peer2
 ```
* 2.打包项目(``)，并使用以下命令启动两个Eureka Server节点。
  
  ```
  java -jar microservice-discovery-eureka-ha-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer1

  java -jar microservice-discovery-eureka-ha-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer2
  ```
  通过spring.profiles.active指定使用哪个profile启动，启动过程中如果报`unknow server`是正常的，等两个jar包都正常启动以后就不会报错了。
* 3.访问[http://peer1:8761](http://peer1:8761)会发现"registered-replicas"中已有peer2节点，同理访问[http://peer2:8762](http://peer2:8762)，也能发现其中的"registered-replicas"中有peer1节点。