# enskildUppgift

## Beskrivning
Projektet är en SpringBoot aplikation webservice med en workflowfilför GitHub Actions (Build-steget).
Webservicen kan ta emot olika typer av förfrågningar och skicka tillbaka ett svar, både via Postman och AWS.
Vid push till GitHub hostas den nya versionen automatiskt på AWS efter att den gått igenom pipelinen på AWS.
För att gå igenom pipelinen innehåller koden ett antal jUnit tester som måste bli godkända.

### AWS Pipeline Process
1. För att skicka koden till AWS, gör en Git Commit & Push
2. En Build skapas i GitHub Actions via workflowfilen "superlinter.yml"
3. När Build-steget blivit godkänt i GitHub skickas koden vidare till AWS Pipeline:
   - Steg 1, Source: Hämtar koden från repositoryt på GitHub. Vilket repository den ska hämta ifrån ställdes in vid skapandet av pipelinen i AWS.
   - Steg 2, Build: Här körs bland annat tester för att kontrollera att koden fungerar korrekt. Här används AWS CodeBuild.
   - Steg 3, Deploy: Distribuerar koden till miljön (Environment i AWS Elastic Beanstalk)