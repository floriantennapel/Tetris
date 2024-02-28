## Hva har du lært om Java og objekt-orientert programmering under arbeidet med denne oppgaven? Beskriv hvilke deloppgave(r) du jobbet med i læringsøyeblikket.

Jeg har lært mye om hensikten av Interfaces i tilknytning til pakker og public metoder. Jeg har tidligere ikke helt skjønt hva meningen med er Interfaces i tilfeller der man kun har som hensikt å skrive én klasse som implementerer grensesnittet.
Det jeg nå har forstått er at en Interface egentlig er en pakkes ansikt utad, og at alle public metoder burde være definert i et Interface. Metoder som brukes av andre klasser i samme pakke burde ikke være public, men pakke-privat.
I dette programmet er det veldig tydelig hvordan Interfaces kan brukes på en god måte med måten pakkene model, view og controller kun kommuniserer gjennom grensesnittene ControllableTetrisModel og ViewableTetrisModel.

Et eksempel på en metode som er pakke-privat er getTetrisBoardWithContents som defineres i TestTetrisBoard, denne metoden er veldig grei å bruke for alle test klassene, men ikke i andre pakker. 
Frem til nå har vi stort sett sett på programmer som er fullstendig inneholdt i en pakke, i slike tilfeller har vi skrevet public foran metoder som brukes i flere metoder, men egentlig er dette en for åpen modifikator når det kun er en intensjon om at metodene skal brukes i samme pakke. God innkapsling har klare krav til at tilgangsmodifikatorer alltid skal være så strenge som mulig.

Videre om Interfaces har jeg lært nytten av å la en klasse implementere flere grensesnitt slik som TetrisModel implementerer både ControllableTetrisModel og ViewableTetrisModel, på denne måten er klassene mye lettere å bruke ettersom vi kun får tilgang til metodene som trengs til et gitt formål og vi får mye bedre innkapsling slik at vi ikke med uhell endrer ting vi ikke burde kunne endre.

## Hva er det neste du ønsker å lære om Java og programmering?

Jeg ønsker å lære mer om hvordan man bør skrive tester og hva som er gode tester. 
Videre ønsker jeg å vite hvordan grafiske programmer som dette kan optimaliseres for å kjøre bedre, allerede nå merker jeg at programmet noen gang henger seg litt fast, Tetris er et veldig enkelt spill og burde kunne kjøre helt jevnt på en moderne maskin.


## Hvilke grep gjør vi for å øke modulariteten i koden? Gi noen eksempeler.

Der vi bruker objekter er typen til objektet i stor grad grensesnittet klassen til objektet implementerer og ikke klassen selv. På denne måten kan vi enkelt bytte ut klassen med en annen klasse uten å endre noe kode i programmet som bruker denne klassen. Et eksempel på dette er de to implementasjonene av TetrominoFactory i tetromino pakken.
Her var det veldig enkelt for meg å skrive en ny klasse der implementasjonsdetaljene var annerledes. I TetrisModel trengte jeg kun å endre en linje der jeg kalte på konstruktøren til BagTetrominoFactory istedenfor RandomTetrominoFactory.

Jeg brukte i klassen ShadowColorTheme arv for å unngå å skrive en helt ny implementasjon av ColorTheme, her trengte jeg bare å bruke DefaultColorTheme sin getCellColor og overkjørte den for å gjøre fargen gjennomsiktig. På denne måten kan jeg endre fargene i DefaultColorTheme og alltid være sikker på at skyggen til brikken har samme farge, dette gjør også at vi unngår repetisjon av kode.

Pakken grid er egentlig helt uavhengig av at den brukes til å lage tetris. Fordi den kun bruker generics vil vi kunne bruke hele pakken til alle slags programmer der vi ønsker å ha en rutenett av objekter med informasjon om posisjon og verdi i hver celle. 
Alt som gjør Grid mer brukbart til tetris defineres utfor pakken i TetrisBoard klassen. Her brukes arv for å legge til funksjonalitet i Grid, slik at TetrisBoard <strong>er</strong> en Grid, men også har flere metoder vi trenger uten at vi endrer på Grid.
