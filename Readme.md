# freestyle-persistence-service

## Descrizione

Il `freestyle-persistence-service` è un microservizio Java Spring Boot che si occupa di leggere i dati da un database MongoDB. Questo servizio è parte di un'architettura di microservizi più ampia e fornisce un'astrazione per l'accesso ai dati, consentendo ad altri servizi di interagire con il database in modo semplice e uniforme.

## Funzionalità

*   **Lettura dati da MongoDB:** Il servizio espone endpoint REST che permettono di recuperare dati specifici dal database MongoDB. Le query possono essere personalizzate per filtrare e ordinare i risultati.
*   **Astrazione del database:** Il servizio nasconde la complessità dell'interazione con MongoDB, fornendo un'interfaccia semplice e coerente per gli altri microservizi.
*   **Configurazione flessibile:** Il servizio può essere configurato tramite variabili d'ambiente o file di configurazione per adattarsi a diversi ambienti (sviluppo, test, produzione).
*   **Monitoraggio e logging:** Il servizio include funzionalità di monitoraggio e logging per facilitare la diagnosi e la risoluzione dei problemi.

## Tecnologie

*   **Java:** Linguaggio di programmazione principale.
*   **Spring Boot:** Framework per lo sviluppo di applicazioni microservizi.
*   **MongoDB:** Database NoSQL utilizzato per la persistenza dei dati.
*   **Spring Data MongoDB:** Libreria per semplificare l'accesso a MongoDB da applicazioni Spring.
*   **Maven o Gradle:** Tool di build per la gestione delle dipendenze e la creazione del pacchetto del servizio.
*   **Docker (opzionale):** Per контейнериizzare il servizio e facilitarne la distribuzione.

## Installazione

1.  **Prerequisiti:** Assicurarsi di avere installato Java e Maven o Gradle.
2.  **Clonare il repository:** `git clone https://[URL_repository]`
3.  **Configurare il database:**
    *   Assicurarsi che un'istanza di MongoDB sia in esecuzione e accessibile.
    *   Configurare la stringa di connessione al database nel file `application.properties` o tramite variabili d'ambiente.
4.  **Build del progetto:** `mvn clean install` (o `gradlew build`)
5.  **Esecuzione del servizio:** `java -jar target/freestyle-persistence-service.jar` (o eseguire il servizio tramite l'IDE)

## Utilizzo

Il servizio espone endpoint REST che possono essere utilizzati per interagire con il database. La documentazione dettagliata degli endpoint è disponibile tramite Swagger o un altro strumento di documentazione API.

## Esempio di utilizzo

Per recuperare tutti gli utenti dal database, è possibile effettuare una richiesta GET all'endpoint `/users`:
