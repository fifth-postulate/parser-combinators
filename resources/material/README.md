# Workshop Material
This archive contains all the material needed for the _Practical Parsing for Pragmatic Programmers_ workshop.

It contains

1. A Guide
2. Example Code
3. Presentations
4. Resources

## Guide
The guide is a collections of webpages explaining the topic by providing theory, examples, exercises and projects. You can serve the guide by creating a small webserver, e.g. by running the following code from the workshop material directory

```shell
python3 -m http.server --directory guide 26624 
```

You will find the guide at [`http://localhost:26624`](http://localhost:26624).

## Examples
Example code can be found in the `example` directory. In this directory examples are subdivided per language. I.e. if you are interested in seeing an example in Python, take a look at `example/python`.

## Presentations
Even though the material is written to be self contained, it is good to have a common understanding of the goal and expectations for this workshop. The presentation used to kick off the workshop can be found in this directory.

If you want to serve the presentation use the command below

```shell
python3 -m http.server --directory presentation 78278
```

## Resources
Throughout the material you will find other files that could be beneficial for the understanding of parser combinators. Some are referenced from the guide, others are bonus material.