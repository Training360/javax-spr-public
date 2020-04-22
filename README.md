# JAVAX-SPR - Alkalmazásfejlesztés Spring keretrendszerrel

## Javasolt haladás

* Először nézd meg a videót! A videóban szereplő forráskódot a [demos](demos) könyvtárban találod.
* Minden videóhoz van gyakorlati feladat, melynek a leírását itt találod: [spring-gyak.md](spring-gyak.md).
  A gyakorlati feladatok egymásra épülnek.

## Tematika

* Bevezetés
    * Bevezetés a Spring Framework használatába
* Spring Framework IoC container
    * Inversion of Control és Dependency Injection
    * Unit- és integrációs tesztelés Spring környezetben
    * Beanek személyre szabása
    * Konfiguráció XML-lel és annotációval
    * Injektálás konfigurálása
    * Eseménykezelés
    * Konfigurációs állományok
    * Profile
    * Conditional beans
    * Naplózás (`core`)
    * Aspektusorientált programozás (`aop`)
* Spring Framework adatbáziskezelés
    * Spring Framework repository réteg
    * Séma inicializálás Flyway eszközzel
    * Spring JdbcTemplate
    * JPA használata Spring Frameworkkel
    * Spring Data JPA (`spring-data-jpa`)
    * Deklaratív tranzakciókezelés
* Spring Framework webalkalmazás fejlesztés
    * Bevezetés a Spring MVC használatába (`spring-webmvc-intro`)
    * Thymeleaf view (`spring-webmvc-thymeleaf`)
    * Erőforrások kezelése (`spring-webmvc-resources`)
    * Thymeleaf oldalstruktúra
    * Controllerek használata (`spring-webmvc-controllers`)
    * Hozzáadás és szerkesztés (`spring-webmvc-crud`)
    * Tesztelés (`spring-webmvc-testing`)
    * Handlerek
    * I18N (`spring-webmvc-i18n`)
    * Validáció (`spring-webmvc-validation`)
    * Fájlkezelés (`spring-webmvc-file`)
    * Témák használata (`spring-webmvc-themes`)
    * Hiba- és kivételkezelés (`spring-webmvc-exceptions`)
    * Backend integráció
    * Restful webszolgáltatások (`spring-webmvc-rest`)

## Naplózás

Egy példa `logback.xml` állomány:

```xml
<configuration>

    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <!-- encoders are assigned the type
             ch.qos.logback.classic.encoder.PatternLayoutEncoder by default -->
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <root level="info">
        <appender-ref ref="STDOUT" />
    </root>

    <logger name="spring" level="debug"/>

</configuration>
```

## Aspektusorientált programozás

A _Join point_ slide-on a tetszőleges paraméterezést két ponttal (`..`) tudjuk megadni, és nem hárommal,
ahogy a videóban szerepel.

https://www.eclipse.org/aspectj/doc/released/progguide/language-joinPoints.html#pointcut-parameters

Tehát helyesen:

```java
execution(* spring.di.EmployeeService.saveEmployee(..))
```

## Spring Framework repository réteg

### MariaDB

Ha MySQL helyett MariaDB-t használunk, akkor választhatjuk a MariaDB
JDBC drivert is, melynek neve MariaDB Connector/J.

Ekkor a függőség:

```xml
<dependency>
    <groupId>org.mariadb.jdbc</groupId>
    <artifactId>mariadb-java-client</artifactId>
    <version>2.3.0</version>
</dependency>
```

Érdemes ekkor a megfelelő JDBC url-t is használni:

```
jdbc:mariadb://localhost:3306/employees?useUnicode=true
```

FIGYELEM! A `2.3.0` verzió azt mondja az adatbázisról, hogy MySQL, azonban a `2.4.0` verzió
már azt mondja, hogy MariaDB. Ezt viszont nem ismeri fel a Flyway `5.2.4` verziója, a következő hiba keletkezik:

```
org.flywaydb.core.api.FlywayException: Unsupported Database: MariaDB 10.1
```

Ekkor térjünk vissza a `2.3.0` verzióra.

A MariaDB JCBC driver esetén `MysqlDataSource` helyett `org.mariadb.jdbc.MariaDbDataSource` osztályt kell használnunk,
azonban ennek metódusai `SQLException` kivételt dobnak.

```java
try {
    MariaDbDataSource dataSource = new MariaDbDataSource();
    dataSource.setUrl(url);
    dataSource.setUser(username);
    dataSource.setPassword(password);
}
catch (SQLException se) {
    throw new IllegalStateException("Can not create data source", se);
}
```

### JDBC url

Abban az esetben, ha a legfrissebb MySQL JDBC drivert (connector) alkalmazzuk,
azaz szerepel a `pom.xml` állományban (pl. `mysql:mysql-connector-java:8.0.15`),
akkor a következőhöz hasonló hiba keletkezhet:

```
Caused by: com.mysql.cj.exceptions.InvalidConnectionAttributeException: The server time zone value 'KÃ¶zÃ©p-eurÃ³pai tÃ©li idÃµ' is unrecognized or represents more than one time zone. You must configure either the server or JDBC driver (via the serverTimezone configuration property) to use a more specifc time zone value if you want to utilize time zone support.
	at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
	at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)
	at java.base/jdk.internal.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
	at java.base/java.lang.reflect.Constructor.newInstance(Constructor.java:490)
	at com.mysql.cj.exceptions.ExceptionFactory.createException(ExceptionFactory.java:61)
	at com.mysql.cj.exceptions.ExceptionFactory.createException(ExceptionFactory.java:85)
	at com.mysql.cj.util.TimeUtil.getCanonicalTimezone(TimeUtil.java:132)
	at com.mysql.cj.protocol.a.NativeProtocol.configureTimezone(NativeProtocol.java:2241)
	at com.mysql.cj.protocol.a.NativeProtocol.initServerSession(NativeProtocol.java:2265)
	at com.mysql.cj.jdbc.ConnectionImpl.initializePropsFromServer(ConnectionImpl.java:1319)
	at com.mysql.cj.jdbc.ConnectionImpl.connectOneTryOnly(ConnectionImpl.java:966)
	at com.mysql.cj.jdbc.ConnectionImpl.createNewIO(ConnectionImpl.java:825)
	... 33 more
```

Ekkor használjuk a következő JDBC url-t:

```
jdbc:mysql://localhost/employees?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
```

Vagy térjünk vissza egy régebbi JDBC driverre:

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.47</version>
</dependency>
```

Ekkor azonban a `MysqlDataSource` csomagja más, az osztály neve minősítve
`com.mysql.jdbc.jdbc2.optional.MysqlDataSource`.

## Séma inicializálás Flyway eszközzel

A Flyway videóban szereplő létrehozási módja az újabb verziókban deprecated, helyette használjuk ezt:

```java
Flyway flyway = Flyway.configure().dataSource(dataSource()).load();
flyway.migrate();
```

## Spring Data JPA

A _Teszt eset CrudRepository-ra_ slide-on kimaradt a névre alakítás, a kódrészlet helyesen:

```java
@Test
public void testSaveThenFindAll() {
    employeeRepository.save(new Employee("John Doe"));

    Iterable<Employee> employees = employeeRepository.findAll();
    assertEquals(List.of("John Doe"), StreamSupport.stream(employees.spliterator(), false)
      .map(Employee::getName)
      .collect(Collectors.toList()));
}
```

A _PagingAndSortingRepository_ slide-on a `PageRequest.of(1, 20)` metódus első paramétere az oldal sorszáma 0-tól kezdve a sorszámozást, a második paramétere az oldal mérete.

## Deklaratív tranzakciókezelés

A videóban a `@Transactional(Transactional.TxType.REQUIRES_NEW)` használatához a `javax.transaction.Transactional` importálása szükséges. Helyette a Spring saját `Transactional` annotációja is használható, de ekkor a `propagation` paramétert kell a megfelelő `Propagation` enum értékkel ellátni. Például `@Transactional(propagation = Propagation.REQUIRES_NEW)`.