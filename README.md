# mathParser

### Introduction and Motivation

`mathParser` is a small Library created for a single purpose: It parses strings into abstract syntax trees (AST) of math-like languages. There are some example languages already defined, but the library is designed to be extended to suite your needs.

### Features

* Modularity: The library is design to let you design your language as you like. Using a bunch of traits and the famous cake pattern, it is possible to define a language for exactly your use case.

* Analytic Function Derivative: For the example languages it is possible to calculate analytical derivatives for arbitrarily functions.

* Runtime compilation: For the example languages it is possible to use the scala compiler at runtime to compile your AST into a high-performance function-object.


### Getting started

Currently there is no published jar. To use the library extend your sbt build with:
```scala
def mathParser = RootProject(uri("https://github.com/gregor-i/mathParser.git#<hash>"))

lazy val `your-project` = project.dependsOn(mathParser)
```

Be aware that this project has the scala compiler and spire as runtime dependencies.

I highly encourage you to use the `<hash>` although it's optional. The library is still experimental and the api is subject to change.

### Examples and Usage

To get stated take a look into the `examples` folder. I hope that most of the code is self-explaining.
