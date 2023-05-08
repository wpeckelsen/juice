

# Installatiehandleiding
https://github.com/wpeckelsen/juice





## Benodigdheden
Om dit project te installeren en te draaien is een werkende en bij voorkeur stabiele internetverbinding nodig.
 
#### IDE
Dit project is geschreven in een IDE (Integrated Development Environment) die kan omgaan met Java. De IDE voor dit project is IntelliJ Idea.

### JDK
Om Java te draaien is een JDK vereist. Dit kan via de IDE  ingesteld worden.  Dit project gebruikt versie 17.0.2

Om het aan te passen, klik in de bovenste balk op de projectnaam met het SpringBoot logo ervoor en klik: Edit configurations > Build and Run > openjdk 17 - Oracle open JDK version 17.0.2



Dit kan via de IDE ingesteld worden. Dit project gebruikt versie 17.0.2


Om het aan te passen, klik in de bovenste balk op de projectnaam met het SpringBoot logo ervoor en klik: Edit configurations > Build and Run > openjdk 17 - Oracle open JDK version 17.0.2



### Database
Dit project maakt gebruik van een relationele database. Hiervoor is PostgreSQL gebruikt. Om de database in te zien/te manipuleren is een user interface PgAdmin gebruikt. Deze applicatie is kosteloos te downloaden en de laatste versie is eenvoudig via Google te vinden via Download  PgAdmin.





### Postman eindpunten
Om API-requests te sturen naar de verschillende eindpunten is gebruikt gemaakt van Postman. Dit is een user interface waarmee de verschillende requests verstuurd kunnen worden en de return waardes kunnen worden ingezien.

Postman kan lokaal geïnstalleerd worden, maar het kan ook als web applicatie gerund worden. De laatste versie van deze applicatie is eenvoudig via Google te vinden. Deze handleiding raadt aan om van de geïnstalleerde versie van Postman gebruik te maken onder andere omdat in deze versie foto’s opgeslagen kunnen worden.

Dit project levert namelijk een JSON bestand aan dat in Postman geplakt kan worden. In dit bestand zitten foto’s, en die foto’s kunnen niet ingeladen worden als het via de webversie van Postman wordt gedaan.

Mocht je toch je eigen foto’s willen uploaden, of gebruik je toch liever de webversie, dat kan. Hierover meer in het stappenplan.






## Stappenplan

Deze handleiding gaat uit van IntelliJ. Mocht je een andere IDE gebruiken dan gaat deze handleiding ervan uit dat je weet waar nodig andere acties vereist zijn. De applicatie is te vinden op GitHub via: github.com/wpeckelsen/juice


### Cloning
Clone het programma naar je lokale omgeving en open het in je IDE.


IntelliJ zal je vragen of Maven dit project kan vertrouwen, kies Ja.


Ook zal IntelliJ om een databron vragen, of aangeven dat “Datasources have been detected”. Negeer deze melding(en).

### Database
Maak dan een database aan. Hiervoor open je PgAdmin. Bij het eerste gebruik is naam en wachtwoord vereist. Bedenk dit en bewaar dit goed.

Vervolgens, links in het scherm, rechtsklik op Databases > Create > Database. Geef het de naam Juice. Verder hoef je niets in te stellen. Klik op Save.


Om de applicatie toegang te geven tot je nieuwe database dien je deze naam en wachtwoord in te voeren in je IDE. Zoek in het Juice project in de IDE naar het volgende bestand: application.properties en in dit bestand pas je deze regels aan:

spring.datasource.username=postgres
spring.datasource.password=postgresql

Je past dus “postgres” aan naar jouw gebruikersnaam, en “postgresql” pas je aan naar jouw wachtwoord die je zojuist in PgAdmin hebt ingevoerd. De regels hoeven niet afgesloten te worden met een puntkomma ; symbool. Ook hoeft dit niet tussen haakjes geschreven te worden.


### Endpoints
Dan de REST API eindpunten. Je kunt in theorie alles zelf overtypen vanuit de applicatie in de IDE. Dit project komt met een bestand dat je kunt in Postman kunt invoegen zodat je alle eindpoints automatisch krijgt.

Hiervoor importeer je juice.postman_collection.json in Postman. Dit bestand is te vinden in src/main/resources van het Juice project.

In Postman voeg je het bestand in. Linksboven in beeld vind je hiervoor de knop.

### Foto's
De applicatie kan zowel foto’s uploaden als downloaden. In het meegeleverde json bestand dat je in Postman hebt geplakt zit een foto. Die foto kun je vinden in Postman onder het endpoint “localhost:8080/juice/customer/photo “ met de naam deng-cool-hat.jpeg.

Je kunt zelf ook een foto toevoegen. In deze endpoint, ga naar Body>key>file>select files.

### Applicatie draaien
Je kunt nu de applicatie draaien. Navigeer in je IDE naar het JuiceApplication bestand in het Juice project om dit te doen. Klik op het groene driehoekje / play button links in het gootje.

Je kunt nu terug naar Postman om de applicatie in z’n werk te zien gaan. Zoek het request “authenticate admin” op, of navigeer direct naar “localhost:8080/authenticate”. Verstuur dit verzoek om een zogenaamde JWT token terug te krijgen. Dit is een lange reeks tekens waarmee de applicatie kan verifiëren dat een gebruiker de juiste rechten heeft en dat een gebruiker daadwerkelijk is wie het zegt te zijn tegen de applicatie.

Kopieer de lange reeks tekens die je terug krijgt.
Navigeer naar “Environment quick look” rechtsboven in beeld, en pas de JWT token aan met de reeks tekens die je zojuist hebt gekopieerd. Hiermee laat je aan de applicatie weten dat je de juiste rechten hebt om verzoeken via de endpunten te versturen.


En doordat je het hier rechts bovenin invoegt, wordt het globaal toegepast. Dit is handig, want zo hoef je niet bij ieder verzoek de JWT token opnieuw in te vullen. De token is geldig voor 10 dagen. Dit zou genoeg tijd moeten zijn.


Je bent nu ingelogd als Admin gebruiker met zowel Customer rol als Publisher rol. Je kunt nu alle requests sturen naar de applicatie en de response data uitlezen in Postman. Dit kan handmatig, of met een druk op de knop.

Rechtsklik, op de folder JWT. “Run Folder” om alle requests direct na elkaar uit te voeren. Alle requests zouden je een statuscode terug moeten geven in de 200. Dit geeft aan dat het request succesvol is uitgevoerd.



## Gebruikers
Dit zijn alle gebruikers met hun rollen die aanwezig zijn in de applicatie. Meer Customers en Publishers kunnen naar wens aangemaakt worden.

Admin
Gebruikersnaam: 	ADMIN
Wachtwoord: 		ADMIN
Rol: 			Admin

Customer
Gebruikersnaam:	CUSTOMER
Wachtwoord: 		CUSTOMER
Rol: 			Customer
Publisher
Gebruikersnaam:	PUBLISHER
Wachtwoord:		PUBLISHER
Rol:			Publisher

## REST API eindpunten

Hier eerst een een overzicht van alle endpoints. Daarna worden ze stuk voor stuk uitgeschreven met de request body en de response body als die aanwezig zijn. Alle endpoints met een * gemarkeerd zijn beveiligd met een JWT token.

Een JWT kan worden opgevraagd door de endpoint “localhost:8080/authenticate” te bezoeken. Meer informatie kun je vinden in het Stappenplan, stap 5.


### Login
Authentication Admin
Authentication Customer
### ADMIN*
update deal (PUT)
delete deal (DEL)
Photos (GET)
### Common*
New Deal (POST)
Customers (GET)
Bids (GET)
Publishers (GET)
Domains (GET)
Domains TLD NL (GET)
Domains TLD DE (GET)
Deals (GET)
Customer (GET)
Bid (GET)
Publisher (GET)
Domain (GET)
Deal (GET)
### Customer*
New Bid (POST)
Update Bid (PUT)
Update Customer (PUT)
Delete Customer (DEL)
Delete Bid (DEL)
Upload Photo (POST)
### Publisher*
New Domain (POST)
Update Publisher (PUT)
Update Domain (PUT)
Delete Publisher (DEL)
Delete Domain (DEL)
Download Photo (GET)
### Miscellaneous*
Authenticated (GET)
