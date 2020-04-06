## 数据表, 如何创建
1. 可视化管理工具(navicat, mysql workbench)
2. 手写SQL语句进行数据表创建
3. Model模型类

## 如何查询数据库

## 常用SpringBoot依赖
```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>
```

```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
```
```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-jdbc</artifactId>
    </dependency>
```
```xml
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <scope>runtime</scope>
    </dependency>
```

## 数据库表与表之间的关系
1. 一对一
    * 查询效率, 避免一个表太多字段
    * 业务角度
2. 一对多
3. 多对多
    > 中间表
    > teacher student teacher_student

## 数据库设计
    1. 业务对象
    2. 对象与对象之间的关系
        > 通过外键联系
    3. 细化
        > 字段 限制 长度 小数点 唯一索引