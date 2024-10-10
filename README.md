# enskildUppgift

## Beskrivning
Projektet är en SpringBoot applikation webservice med en workflowfil för GitHub Actions (Build-steget).
Webservicen kan ta emot olika typer av förfrågningar och skicka tillbaka ett svar, både via Postman och AWS.
Movies är kopplad till Author med en @ManyToOne vilket innebär att en författare kan vara kopplad till flera filmer.
Vid push till GitHub hostas den nya versionen automatiskt på AWS efter att den gått igenom pipelinen på AWS.
För att gå igenom pipelinen innehåller koden ett antal jUnit tester som måste bli godkända.

## URL
API:et är tillgängligt på AWS via följande url:
http://enskilduppgift3-env.eba-dep3smvq.eu-north-1.elasticbeanstalk.com/movies

## Endpoints

### Movies

#### 1. Hämta alla filmer
- **Endpoint:** `GET /movies`
- **Beskrivning:** Hämtar en lista med alla filmer.
- **Svar:** En lista med `Movies` objekt
- **Exempel på svar:**
 ```json
    [
  {
    "id": 1,
    "title": "Movie 1",
    "year": 1990,
    "author": {
      "id": 1,
      "name": "Author 1",
      "age": 45
    }
  }
    ]
   ```
#### 2. Hämta en film
- **Endpoint:** `GET /movies/{id}`
- **Beskrivning:** Hämtar en film baserat på dess id.
- - **Parameter:**
  - `id` : Filmens id (long)
- **Svar:** En `Movies` objekt om den finns, annars en 404-status.
- **Exempel på svar:**
 ```json
    [
  {
    "id": 1,
    "title": "Movie 1",
    "year": 1990,
    "author": {
      "id": 1,
      "name": "Author 1",
      "age": 45
    }
  }
    ]
   ```  

#### 3. Skapa en ny film
- **Endpoint:** `POST /movies`
- **Beskrivning:** Skapar en ny film.
- **Begäran:** Ett `Movies` objekt.
- **Exempel på begäran:**
```json
{
   "title": "New Movie",
   "year": 2023,
    "author": {
      "id": 2
  }
}
```
- **Svar:** Det skapade `Movies` objektet.
- **Exempel på svar:**
 ```json
    [
  {
    "id": 3,
    "title": "New Movie",
    "year": 2023,
    "author": {
      "id": 2,
      "name": "Author 2",
      "age": 30
    }
  }
    ]
   ``` 

#### 4. Uppdatera en film
- **Endpoint:** `PATCH /movies/{id}`
- **Beskrivning:** Uppdaterar en befintlig film.
- **Parameter:**
  - `id` : Filmens id (long)
- **Begäran:** En delvis uppdateras `Movies` objekt.
- **Exempel på begäran:**
```json
{
   "title": "Updated Title",
   "year": 2020,
   "author": {
     "id": 3
  }
}
```
- **Svar:** Det uppdaterade `Movies` objektet.
- **Exempel på svar:**
 ```json
    [
        {
            "id": 1,
            "title": "Updated title",
            "year": 2020,
            "author": {
              "id": 3,
              "name": "Author 3",
              "age": 30
          }
        }
    ]
   ```

#### 5. Ta bort en film
- **Endpoint:** `DELETE /movies{id}`
- **Beskrivning:** Tar bort en film baserat på filmens id.
- **Parameter:**
  - `id` : Filmens id (long)
- **Svar:** Meddelande om att filmen har tagits bort.
- **Exempel på svar:**
 ```json
      "Removed Successfully"
   ```

### Author

#### 1. Hämta alla författare
- **Endpoint:** `GET /author`
- **Beskrivning:** Hämtar en lista med alla författare.
- **Svar:** En lista med `Author` objekt
- **Exempel på svar:**
 ```json
    [
        {
            "id": 1,
            "name": "Author 1",
            "age": 30
        }
    ]
   ```
#### 2. Hämta en författare
- **Endpoint:** `GET /author/{id}`
- **Beskrivning:** Hämtar en författare baserat på dess id.
- - **Parameter:**
  - `id` : författarens id (long)
- **Svar:** En `Author` objekt om den finns, annars en 404-status.
- **Exempel på svar:**
 ```json
    [
        {
            "id": 1,
            "name": "Author 1",
            "age": 30
        }
    ]
   ```  

#### 3. Skapa en ny författare
- **Endpoint:** `POST /author`
- **Beskrivning:** Skapar en ny författare.
- **Begäran:** Ett `Author` objekt.
- **Exempel på begäran:**
```json
{
   "name": "New author",
   "age": 40
}
```
- **Svar:** Det skapade `Author` objektet.
- **Exempel på svar:**
 ```json
    [
        {
            "id": 3,
            "name": "New Author",
            "age": 40
        }
    ]
   ``` 

#### 4. Uppdatera en författare
- **Endpoint:** `PATCH /author/{id}`
- **Beskrivning:** Uppdaterar en befintlig film.
- **Parameter:**
    - `id` : Författarens id (long)
- **Begäran:** En delvis uppdaterad `Author` objekt.
- **Exempel på begäran:**
```json
{
   "name": "Updated Name",
   "age": 40
}
```
- **Svar:** Det uppdaterade `Author` objektet.
- **Exempel på svar:**
 ```json
    [
        {
            "id": 1,
            "name": "Updated name",
            "age": 40
        }
    ]
   ```

#### 5. Ta bort en författare
- **Endpoint:** `DELETE /author{id}`
- **Beskrivning:** Tar bort en författare baserat på filmens id.
- **Parameter:**
    - `id` : Författarens id (long)
- **Svar:** Meddelande om att författaren har tagits bort.
- **Exempel på svar:**
 ```json
      "Removed Successfully"
   ```

## AWS Pipeline Process
1. För att skicka koden till AWS, gör en Git Commit & Push
2. En Build skapas i GitHub Actions via workflowfilen "superlinter.yml". Under Build - Build with Maven kan man längst ner under "TESTS" se att testerna kördes.
3. När Build-steget blivit godkänt i GitHub skickas koden vidare till AWS Pipeline:
   - Steg 1, Source: Hämtar koden från repositoryt på GitHub. Vilket repository den ska hämta ifrån ställdes in vid skapandet av pipelinen i AWS.
   - Steg 2, Build: Här körs bland annat tester för att kontrollera att koden fungerar korrekt. Här används AWS CodeBuild.
   - Steg 3, Deploy: Distribuerar koden till miljön (Environment i AWS Elastic Beanstalk)