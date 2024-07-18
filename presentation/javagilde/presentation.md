Presentatie
===========

Het doel van vandaag is een simpele expressie te kunnen parsen zoals 5*2+1. De output van een parser is normaal
gesproken een Abstract Syntax Tree. Dit is een boomstructuur die bestaat uit een set van nodes. Voor ons mini expressie
taaltje -- we parsen alleen constanten, keer en plus -- hebben we de volgende typen nodes nodig (Expression,
Multiplication, Addition en Constant) ...

Voor de expressie 5*2+1 willen we daarom een boom opbouwen die er als volgt uitziet ...

Deze boom kunnen we vervolgens "evalueren", dit levert zoals verwacht ...

## Maar hoe parse je eigenlijk/ hoe schrijf je een parser?
Laten we simpel beginnen, we maken eerst een parser die een 'A' parst. Dit is een functie die een String als input
neemt en de geparste A teruggeeft. `fun parserA(input: String): Char`

Hier missen we meteen iets; want hoe geef je een foutsituatie aan? Dus wanneer de input niet begint met een 'A'?
`fun parserA(input: String): List<Char>`

Dit is ook niet handig, want we willen verder kunnen parsen met de output van deze parser.
`fun parserA(input: String): List<Pair<Char, String>>`

Een parser is daarom een functie `(String) -> List<Pair<T, String>>`, hiervoor kunnen een type definieren.

## Generiekere parser
We kunnen de parser die alleen een 'A' kan parsen nu ook generiek maken: `fun character(s: Char): Parser<Char>`

Nu kunnen we schrijven: `val parserA = symbol('A')`

Of nog generieker: `fun satisfy(predicate: (Char) -> Boolean): Parser<Char>` bijvoorbeeld: 
`val parserA = satisfy { it == 'A' }`

Als we nu een ingewikkeldere parser willen maken, dan kunnen we bijvoorbeeld de predicate uitbreiden:
`val parserAorB = satisfy { it == 'A' || it == 'B'}`

## Combinators
Het zou mooier zijn als we een losse parser hebben die de 'A' kan parsen en een losse parser die de 'B' kan parsen en
deze twee vervolgens met elkaar combineren met een 'or' operator. Daarnaast willen we straks ook twee parsers kunnen
combineren die verschillend van type zijn; bijvoorbeeld 'char' en 'int'. 

Daarom gaan we nu een combinator introduceren. Dit is de 'or' combinator:
`fun <T> or(p: Parser<T>, q: Parser<T>): Parser<T>`.

In Kotlin kunnen we hiervoor een prachtige infix operator definieren:
`infix fun <T> Parser<T>.`|||`(p: Parser<T>): Parser<T>`

We willen echter niet alleen 'or' parsers maken, maar ook parsers achter elkaar kunnen chainen, bijv. eerst `A` en dan
`B`. Deze combinator noemen we 'and': `fun <S, T> and(p: Parser<S>, q: Parser<T>): Parser<Pair<S,T>>`.

Ook hiervoor kunnen we in kotlin een infix operator introduceren: `infix fun <S, T> Parser<S>.`|&|`(p: Parser<T>)`

De 'and' parser geeft een `Pair` terug, maar vaak ben je in iets anders geinteresseerd; om de pair te vertalen naar
iets anders introduceren we geheel in de traditie van het functioneel programmeren de map:
`fun <S, T> map(p: Parser<S>, transform: (S) -> T): Parser<T>`

Een voorbeeld waarbij we de 'map' nodig hebben is bij een variabele naam gevolgd door een =-teken; we zijn hier alleen
geinteresseerd in de variabelenaam. In een ander geval zouden we alleen geinteresseerd kunnen zijn in het tweede deel.
Hier voor maken we de volgende parsers: `infix fun <S, T> Parser<S>.`&|`(p: Parser<T>): Parser<T>` en
`infix fun <S, T> Parser<S>.`|&`(p: Parser<T>): Parser<S>`.

Merk op dat we nu een combinatie van twee andere parsers maken!!!

## Uitbreiden van de collectie van combinators
In een ander geval willen we bijvoorbeeld functie argumenten parsen; dit is een herhaling van steeds dezelfde parser;
we willen eigenlijk een 'many' parser maken (dit noemen we vaak ook de kleene star; bijv. bij reguliere expressies):
`fun <T> many(p: Parser<T>): Parser<List<T>>`

Hierin hebben we de `succeed` parser nodig. Deze parser slurpt geen input en slaagt altijd, hierbij geeft deze het
gegeven resultaat terug: `fun <T> succeed(result: T): Parser<T>`

Deze 'many' parser gaat echter stuk... Wat gaat er mis?

Bij het aanroepen van many moet de functie zichzelf aanroepen. We moeten deze recursie op een of andere manier
doorbreken, we gebruiken hiervoor de 'lazy' parser: `fun <T> lazy(p: () -> Parser<T>): Parser<T>` Dit is een parser die
pas bij de aanroep de gegeven parser teruggeeft.

We zetten de aanroep van 'many' in een 'lazy'.

Met deze many kunnen we bijvoorbeeld de volgende many1 parser definieren; deze parser parst tenminste 1 herhaling
(vergelijkbaar met de plus operator in reguliere expressies): `fun <T> many1(p: Parser<T>): Parser<List<T>>`

## Expressies parsen
We hebben nu alle instrumenten om een expressieparser te maken: kijk maar!

Om een getal (digit) parsen maken we de volgende parser: `val digit: Parser<Int>`

Hiermee kunnen we ook een nummer maken: `val number: Parser<Int>`

We kunnen een number vertalen naar een constant: `val constant: Parser<Expression>`

Voor een vermenigvuldiging kunnen we schrijven: `fun multiplication(): Parser<Expression>`
Addition: `fun addition(): Parser<Expression>`
Expression: `fun expression() : Parser<Expression>`

# Testen
We kunnen dit nu aanroepen; dit levert een lijst op met resultaten waar we ook niet in geinteresseerd zijn.

We zijn echter alleen geinteresseerd in de resultaten (het resultaat) dat de hele input parst; dit doen we met de just
parser: `fun <T> just(p: Parser<T>): Parser<T>`

Dit levert echter nog steeds een lijst op. We maken nu een convenience methode om makkelijk te parsen en het eerste
resultaat te pakken: `fun <T> parse(parser: Parser<T>, input: String): T?`
