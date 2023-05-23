# youcai-open-sdk

![main workflow](https://github.com/pinheng-inc/youcai-open-sdk/actions/workflows/main.yaml/badge.svg)

[优采开放平台](https://b2b.you.163.com/distribution/openapi#/home) 推荐 Java 用户的sdk。

## 引入临时版本

**正式版本尚未发布。**

```xml

<repository>
    <id>pinheng-release</id>
    <name>pinheng-release</name>
    <url>http://nexus.weitun.com.cn/repository/maven-public/</url>
</repository>
```

maven 版本高于`3.0`的用户需要在通过编辑 `settings.xml` 文件允许`pinheng-release`使用http协议。

```xml

<mirrors>
    <mirror>
        <id>pinheng-release</id>
        <name>pinheng-release</name>
        <mirrorOf>pinheng-release</mirrorOf>
        <url>http://nexus.weitun.com.cn/repository/maven-public/</url>
    </mirror>
</mirrors>
```

添加依赖

```xml

<dependency>
    <groupId>com.pinheng.youcai</groupId>
    <artifactId>youcai-open-sdk</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

## 引入正式版本

***TBC***

## 使用

在代码中构造`OpenClient`
实例即可，目标仓库中源码，javadoc都已齐备，通过开发环境获取即可获取帮助；也可以通过参考[测试用例](https://github.com/pinheng-inc/youcai-open-sdk/blob/main/src/test/java/com/pinheng/youcai/OpenClientTest.java)
了解其基本用法。