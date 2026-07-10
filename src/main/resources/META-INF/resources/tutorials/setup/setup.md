This guide will help you quickly set up JQuery (JavaQuery) and start writing queries in a simple and efficient way.

1. Add JQuery Dependency

To use JQuery in your project, add the following dependency to your `pom.xml`.

You can find the latest version here:<br>

https://mvnrepository.com/artifact/io.github.oneteme/jquery

```java
// pom.xml

<dependency>
	<groupId>io.github.oneteme</groupId>
	<artifactId>jquery</artifactId>
	<version>5.0.0 /* (JQuery version) */</version>
</dependency>

// pom.xml
```

In order to start `JQuery` in your project you also need to add  the `JQuery Springboot Starter`

```java
// pom.xml

<dependency>
	<groupId>io.github.oneteme</groupId>
	<artifactId>jquery-spring-boot-starter</artifactId>
	<version>1.0.0 /*JQuery Starter version*/</version>
</dependency>

// pom.xml
```

Once the dependency is added, you're ready to start configuring your datasets and store.