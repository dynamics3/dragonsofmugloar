# Dragons of Mugloar
Solution for task http://www.dragonsofmugloar.com/

# Tools used
* Project is written in Kotlin language which is a very progressing and convenient JVM language.

* Maven was selected as build automation tool.

* For logging purposes, slf4j and logback frameworks are used.

* For testing purposes, PowerMock, Mockito, Kotlin-Mockito and AssertJ were chosen.

* Extra libraries include: khttp - is a simple library for HTTP requests in Kotlin; Gson - library for converting Objects into their JSON representation.

# Usage
Build:
```$xslt
mvn clean package
```
Run (default game limit is 10):

```$xslt
java -jar dragonsofmugloar-1.0.jar
```

Run with setting game limit:
```$xslt
java -jar dragonsofmugloar-1.0.jar -limit 100
```

# Logging:
Logs can be found at:
```$xslt
/tmp/dragonsofmugloar/dragonsofmugloar.log
```
Logging messages are shown in console as well.

# Thanks and have fun!


