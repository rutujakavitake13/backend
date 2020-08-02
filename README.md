# Sanction Screening for Payment Transaction

#### This application allows you to upload desired file, check ita validation and then screen them against pre-defined list of key words
Sanctions screening is the verification of names, or alias of those, on Sanction lists involved in financial transactions. Such transactions are filtered to prevent, detect and report suspicious money laundering transactions. 

## Prerequisites

Project uses Spring Boot and MySql Workbench. Installation links for same are as follows :

* [Spring Boot](https://www.javatpoint.com/spring-boot-download-and-install-sts-ide)
* [MySql Workbench](https://www.guru99.com/introduction-to-mysql-workbench.html)

## Dependencies used

```
1. MySql Connector
2. Spring Data Jpa
3. Apache poi
4. Apache poi-xml
5. Log4j2
5.Sprinng Web
6. Freemaker
```

## Application flow

![image](https://user-images.githubusercontent.com/63504802/89121847-4eeae080-d4e0-11ea-9084-362cd8cde05c.png)

## MySql and Log4j2 connection

application.properties:-

```
spring.datasource.driver-class-name: com.mysql.cj.jdbc.Driver
spring.datasource.url: jdbc:mysql://localhost:3306/sanction
spring.datasource.username: root
spring.datasource.password: rutuja13

spring.jpa.properties.hibernate.dialect= org.hibernate.dialect.MySQL5Dialect

spring.jpa.hibernate.ddl-auto= update
spring.jpa.show-sql= true
```

Log4j2 file:-

```
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN" monitorInterval="30">
    <Properties>
        <Property name="LOG_PATTERN">
            %d{YYYY-MM-DD HH:mm:ss.SSS} %5p ${hostName} --- [%15.15t] %-40.40c{1.} : %m%n%EX
        </Property>
    </Properties>
    <Appenders>
        <Console name="ConsoleAppender" target="SYSTEM_OUT" follow="true">
            <PatternLayout pattern="${LOG_PATTERN}"/>
        </Console>
    </Appenders>
    <Loggers>
        <Logger name="com.example.log4j2demo" level="debug" additivity="false">
            <AppenderRef ref="ConsoleAppender" />
        </Logger>

        <Root level="info">
            <AppenderRef ref="ConsoleAppender" />
        </Root>
    </Loggers>
</Configuration>
```
# Frontend Link

[Angular](https://github.com/Swati-K29/CitiBridgeProject.git)


