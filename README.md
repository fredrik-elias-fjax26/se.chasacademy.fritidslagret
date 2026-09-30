# Fritidslagret

Konsolprogram i Java för utlåning av fritidsutrustning. Grupprojekt på Chas Academy.

## Projektets syfte

Fritidslagret håller ordning på ett lager med fritidsutrustning (cyklar, tält och skidset), på föreningens medlemmar och på lånen mellan dem. Allt sköts via menyer i konsolen:

| Meny | Vad man kan göra |
|---|---|
| 1. Utrustning | Lägga till, lista, söka på namn och radera. Radering kräver att man skriver artikelns exakta namn, och utlånad utrustning kan inte raderas. |
| 2. Medlemmar | Lista medlemmar och ändra status (Aktiv, Inaktiv, Återkallat). |
| 3. Lån | Låna, lista aktiva och försenade lån, söka lån på låne-id, medlemsnamn eller medlems-id, och återlämna. |
| 4. Statistik | Antal aktiva lån, antal medlemmar, mest lånade utrustning och tillgänglig utrustning. |
| 5. Väntelistor | Visa vilka artiklar som har kö. |
| 6. Rapporter | En rapport med aktiva lån (med försenad ja/nej), mest utlånad utrustning, utrustning som aldrig lånats ut, de tre medlemmar som har flest lån totalt och tillgänglig utrustning. |

Reglerna för lån:

- En medlem får ha högst 3 aktiva lån.
- Bara aktiva medlemmar får låna. Återkallade medlemmar och medlemmar med försenade lån nekas.
- Är utrustningen redan utlånad ställs medlemmen i kö. När utrustningen lämnas tillbaka försöker programmet låna ut den till nästa medlem i kön (först in, först ut).

All data finns bara i minnet. Vid start skapar `Startup` demodata (utrustning, medlemmar, några lån och en återlämning), och inget sparas mellan körningar.

## Hur programmet startas

Krav: **JDK 25**, till exempel Eclipse Temurin 25. `JAVA_HOME` ska peka på en JDK, inte bara en JRE. Gradle behöver inte installeras, eftersom wrappern (Gradle 9.7.1) följer med.

```bash
./gradlew run        # Windows: gradlew.bat run
```

`run` kör först alla tester (`test` och `ui-test`). När de gått igenom skrivs "Tester Godkända, Startar Main" ut och menyn visas. Välj med siffror, och `7` avslutar.

**IntelliJ:** öppna mappen som Gradle-projekt, välj SDK 25 och kör `se.chasacademy.fritidslagret.Main`.

**Jar:**

```bash
./gradlew jar        # kör också alla tester först
java -jar build/libs/se.chasacademy.fritidslagret-1.0-SNAPSHOT.jar
```

## Hur tester körs

```bash
./gradlew test             # enhetstester
./gradlew ui-test          # UI-tester som går igenom menyerna
./gradlew test ui-test     # båda
```

I IntelliJ finns samma tasks i Gradle-fönstret under *Tasks → verification*. Resultaten hamnar i `build/reports/tests/test/index.html` och `build/reports/tests/ui-test/index.html`.

- Testerna använder **JUnit 6** och **Mockito 5**. Mockito laddas som Java-agent via `build.gradle`.
- Testerna följer **Arrange / Act / Assert**, och beroenden till tjänsterna mockas med Mockito.
- **Tester vars namn innehåller `UI` körs bara i `ui-test`.** De byter ut `System.in` och `System.out` och startar hela programmet med `Main.main()`, och därför körs de i en egen task.
- Att tänka på när man skriver ett UI-test:
  - Den inmatning testet skickar in måste sluta med `7` (Avsluta). Annars fortsätter menyn när inmatningen tar slut, och testet dör av minnesbrist.
  - Skapa inga `Member`- eller `Equipment`-objekt före `Main.main()`. Id-räknarna är statiska, och `Startup` räknar med att den första medlemmen får id 1000.

## Viktiga designbeslut

- **Lager:** `model` (Equipment, Loan, Member samt reglerna LoanRules och LoanPolicy), `repository` (lagring i minnet), `service` (affärslogik), `ui` (menyer) och `exception`. `Main` skapar alla objekt och skickar in beroendena via konstruktorerna.
- **Repositories** är singletons (`getInstance()`) med en `HashMap`. Equipment-, Loan- och MemberRepository lämnar ut kopior (`Map.copyOf`), så ingen utanför repot kan ändra lagret direkt.
- **Låneregler i egna klasser:** `LoanPolicy` (återkallad medlem, försenade lån) och `LoanRules` (max 3 lån, tillgänglig utrustning, aktiv medlem) kontrolleras innan `LoanService` skapar ett lån.
- **Klockan injiceras:** `LoanService` får en `Clock`, så att förseningar kan testas med ett fast datum.
- **Utrustningstyper:** `Equipment` är abstrakt med underklasserna `Bike`, `SkiSet` och `Tent`. Enumen `EquipmentTypes` kopplar varje typ till en `EquipmentCreator` (`Bike::new` och så vidare). En ny typ blir en ny underklass och en rad i enumen.
- **Egna undantag** för varje regelbrott. De ärver `RuntimeException` och har svenska meddelanden som menyerna skriver ut.
- **Menyer:** en klass per meny och en klass per menyval, till exempel `EquipmentMenuAdd` och `LoanMenuSearch`. Texterna i gränssnittet är på svenska och koden på engelska.
- **Väntelistor:** en kö (`Queue`) per utrustning, först in, först ut.
- **Rapporter:** `ReportService` räknar fram datan men skriver aldrig ut något. Varje kriterium är en egen klass som implementerar `ReportMenuSection` (`getTitle()`, `getRows()`), och `ReportMenu` skriver ut alla sektioner i en loop. Ett nytt kriterium blir en ny klass och en rad i listan. Återlämnade lån räknas med i "mest utlånad" och "flest lån". Hur rapportdelen togs fram med AI-stöd, och vad gruppen behöll, ändrade och tog bort, står i `Fritidslagret_rapporter_AI-dokumentation.pdf`.
- **Java 25:** kompakt `main()`, `IO.println` och `IO.readln`, `import module java.base`, och satser före `super(...)` i undantagens konstruktorer.
