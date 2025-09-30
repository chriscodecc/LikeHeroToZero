# LikeHeroToZero

Dieses Projekt ist eine Java-Webanwendung (Jakarta EE / JSF) mit Hibernate und MySQL-Datenbank.  
Sie läuft unter **Tomcat 10.0** und verwendet **Maven** als Build-Tool.  

---

## Projektstruktur

```
src/main/java/
  charts/           # JSF-Backing Beans für Diagramme
  country/          # Länderverwaltung (Model + Manager)
  filter/           # Servlet-Filter für Login/Access
  hibernate/        # DB-Verbindung (DBConnectionManager)
  usermanagement/   # User + UserManager

src/main/resources/ # Hibernate-Konfigurationen (hibernate.cfg.xml, hibernate_user.cfg.xml)
src/main/webapp/    # JSF-Seiten (index.xhtml, login.xhtml, etc.)
db/                 # Datenbank-Dump (all_databases_dump.sql)
pom.xml             # Maven Build-Datei
```

---

## Voraussetzungen

- **Java**: JDK 23 (getestet mit OpenJDK 23.0.1), empfohlen mindestens JDK 17 (LTS)  
- **Maven**: Version 3.6 oder höher  
- **Server**: Apache Tomcat 10.0.x  
- **Datenbank**: MySQL (z. B. via XAMPP mit phpMyAdmin)  

---

## Setup

### 1. Datenbank einrichten

1. MySQL starten (über XAMPP).  
2. Neue Datenbank anlegen, z. B. `likeherotozero`:  
   - In phpMyAdmin → „Neue Datenbank“ → `likeherotozero` → *Anlegen*.  
3. Dump importieren:  
   - phpMyAdmin → *Importieren* → Datei `db/all_databases_dump.sql` hochladen → *OK*.)
     **WICHTIG!!!** Es darf **keine bestehende DB ausgewählt sein**, sonst werden die `CREATE DATABASE`-Befehle im Dump ignoriert!

Oder über die Konsole:  

```bash
mysql -u root -p < db/all_databases_dump.sql
```

---

## Hibernate konfigurieren

1. In `src/main/resources/hibernate.cfg.xml` sicherstellen, dass die DB-Daten stimmen  
2. Ebenso in `src/main/resources/hibernate_user.cfg.xml` anpassen  
3. Standard-Setup bei XAMPP: Benutzername = `root`, Passwort = leer  

Beispielkonfiguration:  

```xml
<property name="hibernate.connection.driver_class">com.mysql.cj.jdbc.Driver</property>
<property name="hibernate.connection.url">jdbc:mysql://localhost:3306/likeherotozero</property>
<property name="hibernate.connection.username">root</property>
<property name="hibernate.connection.password"></property>
<property name="hibernate.dialect">org.hibernate.dialect.MySQL8Dialect</property>
```

---

## Build & Deployment

1. Projekt mit Maven bauen:  

```bash
mvn clean package
```

2. Kopiere die erzeugte Datei `target/LikeHeroToZero.war` nach `TOMCAT_HOME/webapps/`  

3. Tomcat starten:  

```bash
cd TOMCAT_HOME/bin
startup.bat    # Windows
./startup.sh   # Linux/Mac
```

---

## Anwendung im Browser öffnen

[http://localhost:8080/LikeHeroToZero](http://localhost:8080/LikeHeroToZero)
