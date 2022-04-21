# JAVAX-SPR - Alkalmazásfejlesztés Spring keretrendszerrel tanfolyam gyakorlati feladatok

## Inversion of Control és Dependency Injection

Feladatként egy kedvenc helyeket nyilvántartó alkalmazást kell fejleszteni.
Egy kedvenc helyet a `Location` osztály reprezentál. Rendelkezik egy azonosítóval, névvel
és két koordinátával (rendre `Long id`, `String name`, `double lat`, `double lon`).

Hozz létre egy új projektet `locations` néven.

Létre kell hozni egy `LocationService` és `LocationDao` osztályt.
A  `LocationDao` osztály a `Location` példányokat egy belső
kollekcióban tárolja. Az azonosítókat egy belső számlálóból adja ki.

A kollekció lehet egy `List<Location>`, de akár lehet egy `Map<Long, Location>`
is, ez utóbbi esetben a kulcs az id, az érték meg maga a `Location` példány.

A `LocationService` metódusai:

* `List<Location> listLocations()` - összes kedvenc hely listázása
* `void createLocation(String name, double lat, double lon)` - kedvenc hely létrehozása
* `getLocationById(long id)` - kedvenc hely keresése id alapján
* `void updateLocation(long id, String name, double lat, double lon)` - kedvenc hely módosítása id alapján
* `deleteLocation(long id)` - kedvenc hely törlése

A `LocationDao` metódusai rendre:

* `List<Location> findAll()` - összes kedvenc hely listázása
* `Location save(String name, double lat, double lon)` - kedvenc hely létrehozása, visszaadja a teljes Locationt
* `Optional<Location> findById(long id)` - kedvenc hely keresése id alapján
* `Optional<Location> update(long id, String name, double lat, double lon)` - kedvenc hely módosítása id alapján, visszaadja az új Locationt, ha volt mit módosítani
* `Optional<Location> delete(long id)` - kedvenc hely törlése, visszaadja a törölt Locationt, ha az létezett

A `LocationService` delegálja a kéréseket a `LocationDao` osztálynak. Legyen konstruktor injection.

Írj egy `LocationMain` osztályt, mely példányosít egy `ApplicationContext` példány, lekéri a
`LocationService` beant, majd meghívja rajta a fenti metódusokat.

## Unit és integrációs tesztelés Spring környezetben

Írj egy unit tesztet a `LocationDaoTest` osztályba a  `LocationDao` `save()` metódusára, hívd meg kétszer, majd ellenőrizd, hogy a `listLocations()` metódus jó értékeket ad vissza!

Mivel a visszatérési érték típusa
`List<Location>`, nincs JUnit assert, mellyel egy utasítással le lehetne tesztelni a lista tartalmát.
Ezért érdemes átalakítani pl. a nevek listájává, és erre már lehet `assertEquals()` metódussal összehasonlítani.

```java
assertEquals(Arrays.asList("Budapest", "Győr"),
      locations.stream().map(Location::getName)
        .collect(Collectors.toList()));
```

Írj egy unit tesztet a `LocationServiceTest` osztályba a `LocationService` `createLocation()` metódusára! 

Írj integrációs teszteket a `LocationServiceIntegrationTest` osztályba, 
melyek a `LocationService` metódusait letesztelik! Írj egy `deleteAll()` metódust a `LocationDao`-ba,
és hívd meg minden teszt metódus előtt, ehhez a teszt osztályba a
`LocationDao`-t is injektálni kell! Ez kitörli a lista tartalmát, ezzel inicializálva a tesztek lefutása előtti
állapotot. Tedd egy `@Before` annotációval ellátott metódusba!

A következőképp működjenek a tesztesetek:

* `listLocations()` metódus esetén hívd meg a `createLocation()` metódust párszor, majd olvasd vissza a `listLocations()` metódussal az
  elmentett objektumokat. Használd az előző assert megoldást! Ez már a `createLocation()` metódust is teszteli
* `findById()` metódus esetén hívd meg a `createLocation()` metódust! A visszakapott objektum `id` mezőjével már meg lehet hívni a 
  `findById()` metódust
* `updateLocation()` metódus hasonló az előzőhöz, le kell menteni, utána `updateLocation()` a visszakapott objektumban
  lévő `id` mező alapján, majd
  olvasás a `getLocationById()` metódussal, és assert, hogy változott-e az érték
* `deleteLocation()` metódus esetén le kell menteni, majd `deleteLocation()` a visszakapott objektumban
  lévő `id` alapján, és újra listázás, és üres
  kollekciót kell kapni

Mivel a `getLocationById()` visszatérési típusa `Optional`, ezért használható meghívásakor a következő:

```java
locationService.getLocationById(id).orElseThrow(() -> new IllegalStateException("Not found"));
```

## Beanek személyre szabása

Hozz létre egy prototype scope-pal rendelkező `templateLocation` nevű `Location` beant az Application Contextben!
A kedvenc hely azonosítója legyen `0`, neve `Choose name`, koordinátája `47,50, 19,05`!

A `LocationService`-ben hozz létre egy `createLocationTemplate()` metódust, mely
létrehoz mindig egy új példányt az előbbi prototype beanből, és visszaadja azt! Ehhez az `ApplicationContext`-et
kell a service-be injektálni, majd a `getBean` metódusát hívni. Használj constructor injectiont,
azaz az `ApplicationContext`-et vedd fel paraméterként a `LocationDao` mellé!

Ekkor azonban a `AppConfig` osztály nem fog lefordulni, hiszen már két paramétert vár a konstruktor, és nem egyet.

```java
@Bean
public LocationService locationService() {
    return new LocationService(locationDao());
}
```

A megoldás, hogy itt paraméterben injektálni lehet az `ApplicationContext` példányt.

```java
@Bean
public LocationService locationService(ApplicationContext context) {
    return new LocationService(locationDao(), context);
}
```

Ellenőrizd integrációs tesztesetben, hogy két egymás után létrehozott példány tényleg nem ugyanaz, az `==` operátorral!

## Konfiguráció XML-lel és annotációval

Módosítsd úgy az alkalmazást, hogy a `LocationService` és `LocationDao` osztályokon sterotype annotáció legyen,
valamint component scannel legyenek felolvasva!

## Injektálás konfigurálása

Emeld ki a `LocationDao` metódusait egy külön interfészbe, neve legyen `LocationDao`. Ennek legyen
az eredeti osztály az implementációja, neve legyen `ListLocationDao`. Írj egy `DummyLocationDao`
osztályt is, mely implementálja a `LocationDao` interfészt. Milyen hibaüzenet jön indításkor?
Milyen annotációval kell ellátni a
`ListLocationDao` osztályt, hogy mindig az kerüljön injektálásra?

## Eseménykezelés

Hozz létre egy `LocationMemento` osztályt, mely ugyanazokat az attribútumokat tartalmazza, mint a `Location`. A konstruktorban egy `Location` objektumot kap, melynek aktuális állapotát átmásolja saját magába.

Amennyiben módosítás történik, a `LocationService` dobjon egy `LocationHasChangedEvent` objektumot,
melyben legyen benne egy régi értékeket tartalmazó `LocationMemento` objektum, valamint egy új értékeket tartalmazó `LocationMemento` objektum.

Legyen egy `NameChangeListener` osztály, mely iratkozzon fel erre az eseményre, és amennyiben a név
vátozott, egy listában tárolja el ezeket a változásokat `régi név -> új név` formátumban. Ha csak
a koordináták változnak, ne történjen semmi.

Írj rá tesztesetet. Ehhez injektálni kell a `NameChangeListener` beant, valamint legyen egy `deleteAll()`
metódusa, mely a listát üríti, valamint egy `List<String> getChanges()` metódusa, mellyel a módosítások
lekérdezhetőek.

Mivel az `ApplicationContext` már injektált, és implementálja az `ApplicationEventPublisher` interfészt,
ezért nincs szükség más injektálásra, közvetlenül meghívható annak a `publishEvent()` metódusa.

## Konfigurációs állományok

A `templateLocation` bean `name`, `lat`, `lon` attribútumainak értékét (, ami most a kódban beégetve `Choose name`, stb.) töltsd
fel egy `application.properties` állományból, ami az értékeket a következő kulcsokkal tartalmazza: `template.location.name`,
`template.location.lat`, `template.location.lon`.


## Profile használata

Jelenleg a `DummyLocationDao` és a `ListLocationDao` `LocationDao` implementáció létezik az application contextben.
Módosítsd úgy az alkalmazást, hogy a `DummyLocationDao` csak a `dummy` profile esetén, a `ListLocationDao` pedig
a `normal` profile esetén legyen aktív! A teszteket módosítsd, hogy a `normal` profile-lal fussanak! A main-t is módosítsd,
hogy `normal` profile-lal fusson! Írj egy integrációs tesztet a `dummy` profile-lal is.

Vigyázz, minden tesz esetbe bele kell írni, hogy `normal` profile-lal fusson.

## Conditional beans

Jelenleg a `DummyLocationDao` és a `ListLocationDao` `LocationDao` implementáció létezik az application contextben.
Különböző profile-ok esetén kerülnek aktiválásra. Módosítsd úgy az alkalmazást, hogy a `DummyLocationDao` csak
akkor legyen érvényben, ha a `mode=dummy` környezeti változó deklarálva van, ellenkező esetben mindig
a `ListLocationDao` legyen aktív.

Módosítsd az előző tesztesetet, hogy ne csak a profile legyen `dummy`, hanem a `mode` propery értéke is legyen
`dummy`! Ehhez használd a következő annotációt!

```java
@TestPropertySource(properties = "mode=dummy")
```

## Naplózás

A `LocationService`-ben minden módosító műveletnél legyen SLF4J naplózás `debug` szinten, és ez jelenjen is meg a
konzolon a `LocationMain` és a tesztesetek futtatásakor.

## Aspektusorientált programozás

Írj egy advice-t, mely azt számolja, hogy milyen kezdőbetűvel kezdődő kedvenc hely hányszor került létrehozásra.
Ezt tárolja egy `Map`-ben, a kulcs a betű (mindig kisbetűsítve), az érték pedig egy
számláló. A `createLocation()` hívásokat kell elkapni, és nézni a paramétereket.

Írj rá egy tesztesetet. Ehhez az advice is legyen egy `@Component`, legyen getter a `Map`-hez, és injektálni kell
a tesztesetbe.

## Spring Framework repository réteg

Hozz létre egy új projektet!

Hozz létre egy `DataSource` beant, mely a saját számítógépre telepített MariaDB adatbázishoz kapcsolódik.
A kapcsolódási paramétereket az `application.properties` állományból olvassa be.

Írj egy `LocationDao` interfészt, és annak egy `LocationDaoJdbc` implementációját! A következő
metódusokat implementáld!

```java
public interface LocationDao {
    List<Location> findAll();

    long save(String name, double lat, double lon);

    Location findLocationById(long id);
}
```

Ebben esetben a generált azonosító visszakérése egy kicsit
bonyolult, használd a következő példát!

```java
public long getGeneratedKeys(){
    try(Connection conn=dataSource.getConnection();
        PreparedStatement stmt=conn.prepareStatement("insert into employees(emp_name) values (?)",
        Statement.RETURN_GENERATED_KEYS)
    ) {
        stmt.setString(1,name);
        stmt.executeUpdate();
        return executeAndGetGeneratedKey(stmt);
    } catch (SQLException sqle) {
        throw new IllegalArgumentException("Error by insert",sqle);
    }
}

private long executeAndGetGeneratedKey(PreparedStatement stmt) {
    try (
        ResultSet rs = stmt.getGeneratedKeys()
    ) {
        if (rs.next()) {
            return rs.getLong(1);
        } else {
            throw new SQLException("No key has generated");
        }
    } catch (SQLException sqle) {
        throw new IllegalArgumentException("Error by insert", sqle);
    }
}
```

Megjegyzés: mivel a `ResultSet` ebben az esetben egészen biztosan legfeljebb egy elemet fog tartalmazni, elég
egy `if` feltételben vizsgálni ennek a jelenlétét. Ha mégsem kaptunk volna vissza eredményt, abban az esetben
viszont érdemes kivételt dobni, mert az kifejezetten hibát jelez, ha az adatbázis az elvárttal szemben mégsem
generált az új rekord számára egyedi azonosítót.

Service osztályra most nincs szükség!

Implementálj integrációs teszteket!

## Séma inicializálás Flyway eszközzel

Hozz létre egy `Flyway` beant, mely inicializálja az adatbázist! A következő SQL utasítást adja ki
migrációs szkriptben:

```sql
create table locations(id int auto_increment primary key, name varchar(255), lat double, lon double);

insert into locations(name, lat, lon) values ('Budapest', 47.4979, 19.0402);
```

## Spring JdbcTemplate

Implementáld a `LocationDao` interfészt `LocationDaoJdbcTemplate` osztállyal, a `JdbcTemplate` használatával!

Az eddigi `LocationDao` implementációkon tedd megjegyzésbe a `@Repository` annotációkat, és akkor nem fognak
beanként megjelenni. Vagy ha ráteszed a `@Primary` annotációt az új osztályodra, akkor csak azt fogja a Spring betölteni.

Futtasd az integrációs teszteket!

## JPA használata Spring Frameworkkel

Implementáld a `LocationDao` interfészt `JpaLocationDao` osztállyal, JPA használatával!

Futtasd az integrációs teszteket!

## Spring Data JPA

Hozz létre egy `LocationRepository` interfészt (`extends JpaRepository`), és egy
`LocationService` osztályt, mely hívja ezt a repository-t!

## Deklaratív tranzakciókezelés

Implementálj adatbázisba audit naplózást!

Írj egy `AuditLog` osztályt, melynek van egy `Long id` és egy `String message`
attribútuma! Írj egy `AuditLoggerRepository` interfészt (`extends JpaRepository`)!
Írj hozzá egy `AuditLoggerService` service-t! A `LocationService` írási műveletek
esetén hívja meg az `AuditLoggerService` `saveAuditLog()` metódusát! A `saveAuditLog`
metódus indítson saját tranzakciót! Legyen
lekérdezési lehetőség is a `listAuditLogs()` metódussal! (Ne felejtsd el létrehozni a
táblát is Flyway migrációs szkripttel!)

Írj egy integrációs tesztet, amelyben ellenőrzöd, hogy mentés esetén jelenik meg audit log.  

## Bevezetés a Spring MVC használatába

Hozz létre egy új projektet `locations-mvc` néven. Készíts egy oldalt, mely kiírja a következő
tartalmat:

```html
<html><body><h1>Locations</h1></body></html>
```

## Thymeleaf view

Az előző szöveget ne Java String objektum alapján adja vissza, hanem egy Thymeleaf
template alapján. Az oldalon jelenjen meg az aktuális idő is.

A projektbe hozz létre egy `Location` osztályt (előző projektből átemelhető).

Az oldalon jelenjen meg egy táblázat, ami kiír pár kedvenc helyet!

## Erőforrások kezelése

Az alkalmazásban helyezd el a Bootstrap CSS állományát!
Kösd is be a fejlécbe!

## Thymeleaf oldalstruktúra

Hozz létre egy oldaltöredéket, mely a fejlécet tartalmazza!

## Controllerek használata

Hozz létre egy részletek oldalt, mely elérhető a `/location/{id}` címen!
Jelenítse meg a kedvenc hely adatait!

## Hozzáadás és szerkesztés (@ModelAttribute használata)

A főoldalon hozz létre egy űrlapot, amin új kedvenc helyet lehet felvenni.
Csak két beviteli mező legyen, az egyik első a név, a második a koordináták.
A koordinátákat a következő formátumban kell megadni: `49.12, 13.45`.
Az űrlap mögött egy `LocationForm` álljon (két `String` attribútummal)! A controller
a `LocationForm` alapján hozzon létre egy `Location` példányt, amit aztán
adjon tovább a service-nek, ami egy belső listába elmenti! A koordinátákat
`String` műveletekkel kell feldolgozni.

## Tesztelés

Hozz létre egy unit tesztet a controllerre és egy integrációs tesztet a mentésre és
betöltésre!

## Handlerek (opcionális)

Hozz létre egy handlert, mely az vizsgálja, hogy a böngésző milyen nyelvre van
állítva! Ezt kiírja a naplóba. Ehhez a `Accept-Language` headert kell vizsgálni.

## I18N

A felületen megjelenő összes feliratot properties állományból töltsd be!

Opcionális feladat: Tedd lehetővé a nyelvváltást!

## Validáció

Ellenőrizd, hogy a név nem üres-e, valamint azt, hogy a koordináták a megfelelő
formátumban vannak-e beírva. Erre több lehetőséged is van:

* `@Pattern` annotáció használatával reguláris kifejezést használhatsz
* Saját Bean Validation annotációt implementálsz
* A controllerben implementálod az ellenőrzést, és `BindingResult`-nak meghívod
az `addError(ObjectError)` metódusát, és explicit módon töltöd fel hibával.

## Fájlkezelés (opcionális)

A kedvenc helyhez lehessen képet is feltölteni! Egészítsd ki az űrlapot úgy,
hogy fájlt is lehessen megadni! Egészítsd ki a `Location` osztályt egy
`Image` típusú mezővel, mely `@Embedded` annotációval van ellátva! Az `Image`
osztály a következő. Erre azért van szükség, hogy adatbázisban el lehessen
tárolni a képet blob-ban, és szükség van annak Mime-Type-jára is.

```java
@Embeddable
public class Image {

    @Lob
    @Column(name = "image_content")
    private byte[] content;

    @Column(name = "file_name")
    private String contentType;

    public Image() {
    }

    public Image(byte[] content, String contentType) {
        this.content = content;
        this.contentType = contentType;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
```

Készíts egy `ImagesController` osztályt is, ahol le lehet tölteni a képet!
Legyen elérhető a `/images/{id}` címen. Ez adjon vissza egy `ResponseEntity<Resource>`
típusú objektumot! A `Resource` legyen `ByteArrayResource`, és a header legyen beállítva a
kövekezőre:

```java
.header("Content-Type", image.getContentType())
```

## Témák használata (opcionális)

Tedd lehetővé, hogy az alkalmazásban témát lehessen váltani!

## Hiba és kivételkezelés

Konfigurálj egy 404-es és 500-as oldalt, hogy az alkalmazás designjába illeszkedjen!

## Backend integráció

Az előző projektből emeld át a Spring Data JPA megvalósítást, és integráld a
webes projektbe!

Ha megvalósítottad a képfeltöltést, azt is mentsd le adatbázisba. Ehhez blobot
kell használnod, az annotáció a JPA entitás megfelelő attribútumán (`Location.image`)
`@Lob`.

## RESTful webszolgáltatások

Implementáld a CRUD műveleteket a `Location` entitásra! Legyen RESTful művelet
kedvenc helyek listázására, egyedi azonosító alapján lekérésre, létrehozásra,
módosításra és törlésre!  
