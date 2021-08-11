# Parser Combinators
Presentation and workshop about parser combinators.

If you are looking to participate, make sure to download the [workshop material][material].

## Parts
This repository is split in the following parts.

1. [Workshop][workshop]
2. [Presentation][presentation]
3. [Resources][resources]

### Workshop
The `workshop` folder contains the material for, and instructions on how to run, the workshop.

### Presentation
The `presentation` folder contains various presentations on parser combinators.

### Resources
Material that is beneficial for the understanding of parser combinators, but is not necessarily needed to read while working on the guide are gathered in the `resources` material.

## Development
Development for the Parser Combinator workshop is focused on creating the guide and example code. This work can be found in the [`workshop`][workshop] directory. When commited to the `main` branch, it is packaged and released automatically.

### Packaging
The packaging is described via a [`Makefile`][makefile].

### Releasing
A release is made by the GitHub action [Publish Workshop Material][action:publish].

[material]: https://github.com/fifth-postulate/parser-combinators/releases/download/latest/workshop-material.tar.gz
[workshop]: https://github.com/fifth-postulate/parser-combinators/tree/main/workshop
[presentation]: https://github.com/fifth-postulate/parser-combinators/tree/main/presentation
[resources]: httsp://github.com/fifth-postulate/parser-combinators/tree/main/resources
[makefile]: https://github.com/fifth-postulate/parser-combinators/tree/main/Makefile
[action:publish]: https://github.com/fifth-postulate/parser-combinators/actions/workflows/publish-material.yaml