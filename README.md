# Parser Combinators
Presentation and workshop about parser combinators.

If you are looking to participate, make sure to download the [workshop material][material] or follow the [workshop online][guide].

## Parts
This repository is split in the following parts.

1. [Workshop][workshop]
2. [Presentations][presentations]
3. [Articles][articles]
3. [Resources][resources]

### Workshop
The `workshop` folder contains the material for, and instructions on how to run, the workshop.

### Presentations
The `presentations` folder contain various presentations on parser combinators.

### Articles
The `articles` folder contain various articles to be published in magazines.

### Resources
Material that is beneficial for the understanding of parser combinators, but is not necessarily needed to read while working on the guide are gathered in the `resources` material.

## Development
Development for the Parser Combinator workshop is focused on creating the guide and example code. This work can be found in the [`workshop`][workshop] directory. When commited to the `main` branch, it is packaged and released automatically.

### Packaging
The packaging is described via a [`Makefile`][makefile].

### Releasing
A release is made by the GitHub action [Publish Workshop Material][action:publish].

[action:publish]: https://github.com/fifth-postulate/parser-combinators/actions/workflows/publish-material.yaml[guide]: https://fifth-postulate.nl/parser-combinators/guide/
[articles]: https://github.com/fifth-postulate/parser-combinators/tree/main/articles
[makefile]: https://github.com/fifth-postulate/parser-combinators/tree/main/Makefile
[material]: https://github.com/fifth-postulate/parser-combinators/releases/download/latest/workshop-material.tar.gz
[presentations]: https://github.com/fifth-postulate/parser-combinators/tree/main/presentations
[resources]: https://github.com/fifth-postulate/parser-combinators/tree/main/resources
[workshop]: https://github.com/fifth-postulate/parser-combinators/tree/main/workshop
