# TODO

Fritidslagret

Vecka 5

* Bygga tester för diverse, hade varit enklare om man hade utgått från TDD när man börja skrev
  - [ ] Services
    - [ ] LoanServiceTest
      - Normalfall
        - [x] Försenat Lån
        - [x] Lån kan skapas
        - [x] Kontrollerar Regler och Policy innan lån skapas(Dock ligger den här och (Utrustningen är redan utlånad, Medlemmen har för många aktiva lån) under samma test, splitta upp dom?
      - Gränsfall
        - [x] Försenat lån (Samma dag som Återlämningsdag)
      - [ ] Felkontroll
        - [x] Utrustningen är redan utlånad
        - [x] Medlemmen har för många aktiva lån.
        - [ ] Ogiltiga argument skickas in.
    - [ ] EquipmentServiceTest
      - Normalfall
        - [x] Equipment kan skapas
      - Gränsfall
      - Felkontroll
      
Vecka 6

