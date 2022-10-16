

# Installatiehandleiding
https://github.com/wpeckelsen/juice



## Benodigdheden
Om dit project te installeren en te draaien is een werkende en bij voorkeur stabiele internetverbinding nodig.  
 
#### IDE
Dit project is geschreven in een IDE (Integrated Development Environment) die kan omgaan met Java. De IDE voor dit project is IntelliJ Idea.  

#### JDK
Om Java te draaien is een JDK vereist. De Java Development Kit is de software die een programmeur nodig heeft om programma's in Java te kunnen ontwikkelen. De Java Development Kit bestaat uit de Java Runtime Environment met daarbij een reeks hulpmiddelen die voor de programmeur van belang zijn.


Dit kan via de IDE ingesteld worden. Dit project gebruikt versie 17.0.2


Om het aan te passen, klik in de bovenste balk op de projectnaam met het SpringBoot logo ervoor en klik: Edit configurations > Build and Run > openjdk 17 - Oracle open JDK version 17.0.2



#### Database
Dit project maakt gebruik van een relationele database. Hiervoor is PostgreSQL gebruikt. Om de database in te zien/te manipuleren is een user interface PgAdmin gebruikt. Deze applicatie is kosteloos te downloaden en de laatste versie is eenvoudig via Google te vinden via Download  PgAdmin.




##### Eindpunten
Om API-requests te sturen naar de verschillende eindpunten is gebruikt gemaakt van Postman. Dit is een user interface. Gebruik van deze applicatie is niet vereist.

Een request kan ook via de IDE verstuurd worden door een nieuw .http bestand aan te maken.


Postman kan lokaal geïnstalleerd worden, maar het kan ook als web applicatie gerund worden. De laatste versie van deze applicatie is eenvoudig via Google te vinden, bijvoorbeeld via Download Postman.






## Stappenplan

Deze handleiding gaat uit van IntelliJ. Mocht je een andere IDE gebruiken dan gaat deze handleiding ervan uit dat je weet waar nodig andere acties vereist zijn. De applicatie staat zoals je door hebt op GitHub.


Clone het programma naar je lokale omgeving en open het in je IDE.


IntelliJ zal je vragen of Maven dit project kan vertrouwen, kies Ja.


Ook zal IntelliJ om een databron vragen, of aangeven dat “Datasources have been detected”. Negeer deze melding(en).


Maak dan een database aan. Hiervoor open je PgAdmin. Bij het eerste gebruik is naam en wachtwoord vereist. Bedenk dit en bewaar dit goed.


Vervolgens, links in het scherm, rechtsklik op Databases > Create > Database. Geef het de naam Juice. Verder hoef je niets in te stellen. Klik op Save.


Om de applicatie toegang te geven tot je nieuwe database dien je deze naam en wachtwoord in te voeren in je IDE. Zoek naar het volgende bestand: application.properties en in dit bestand pas je deze regels aan:

spring.datasource.username=postgres  
spring.datasource.password=postgresql

Je verandert dus postgres aan naar jouw gebruikersnaam, en postgresql pas je aan naar jouw wachtwoord die je zojuist in PgAdmin hebt ingevoerd. De regels hoeven niet afgesloten te worden met een puntkomma ; symbool. Ook hoeft dit niet tussen haakjes geschreven te worden.


Dan de REST API eindpunten. Hiervoor importeer je juice.postman_collection.json in Postman. Dit bestand is te vinden in src/main/resources van het Juice project.


Je kunt nu de applicatie draaien. Navigeer in je IDE naar het JuiceApplication bestand in het Juice project om dit te doen.


Je kunt nu terug naar Postman om de applicatie in z’n werk te zien gaan. Zoek het request authenticate admin op. Verstuur dit verzoek. Kopieer de lange reeks tekens. Navigeer naar Environment quick look, en pas de JWT token aan met de reeks tekens die je zojuist hebt gekopieerd. Je bent nu ingelogd als Admin gebruiker met zowel Customer rol als Publisher rol.


Ga dan naar de Customer map en kies de requests om een nieuwe Customer en Bid aan te maken. Voor alle json- data onder het kopje body geldt het volgende: voor de dubbele punt mag niets worden aangepast. De data na de de dubbele punt mag aangepast worden naar keus.


Ter verduidelijking hier een voorbeeld body voor een Domain.  
{  
"name": "broodjes-en-worsten.nl",  
"tld": "NL",  
"category": "foods",  
"price": 420  
}  

Dit mag dus niet:  

{  
"domainName": "broodjes-en-worsten.nl",  
"topLevelDomain": "nl",  
"niche": "foods",  
“what is the price in euros?”: 420   
}  

Dit mag wel:  
{  
"name": "kidneybeans-flavoured-icecream.co.uk",  
"tld": "co.uk",  
"category": "British Food",  
"price": 13  
}






# REST API eindpunten

Dit is een overzicht van alle requests die je kunt versturen in deze applicatie.


admin  
update deal  
delete deal



Common
New Deal  
Customer List  
Bid List  
Publisher list  
Domain List  
Deal List  
Customer by ID  
Bid by ID  
Publisher by ID  
Domain by ID  
Deal by ID  

Customer  
new customer  
new bid  
update bid  
update customer  
delete bid  
delete customer  


publisher  
new publisher  
new domain  
update publisher  
update domain  
delete publisher  
delete domain  

