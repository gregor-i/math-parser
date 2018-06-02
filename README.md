# math-parser

### Introduction and Motivation

`mathParser` is a small Library created for a single purpose: 
It parses strings into abstract syntax trees (AST) of math-like languages. 
There are some example languages already defined, but the library is designed to be extended to suite your needs.

### Features

* Modularity: 
The library is design to let you design your language as you like. 
Using a bunch of traits and the famous cake pattern, it is possible to define a language for exactly your use case.

* Analytic Function Derivative: 
For the predefined languages it is possible to calculate analytical derivatives for arbitrarily functions.

* Runtime compilation: 
For the predefined languages it is possible to use the scala compiler at runtime to compile your AST into a high-performance function-object.


### Getting started

To use the library extend your sbt build with:
```sbt
resolvers += Resolver.url("gregor-i @ bintray", url("https://dl.bintray.com/gregor-i/releases/"))(Resolver.ivyStylePatterns)
libraryDependencies += "math-parser" %% "math-parser" % "1.0"
```

Be aware that this project has the scala compiler and spire as runtime dependencies.

### Examples and Usage

To get stated take a look into the `examples` folder. I hope that most of the code is self-explaining.
