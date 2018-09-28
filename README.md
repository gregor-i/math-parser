# math-parser

### Introduction and Motivation

`mathParser` is a small Library created for a single purpose: 
It parses strings into abstract syntax trees (AST) of math-like languages. 
There are some predefined example languages, but the library is intended to be extended by your language to suite your needs.

### Quick Overview

```scala
// your input:
val string = "2*x*x + 1"

val language = mathParser.MathParser.doubleLanguage('x)
val parsed = language.parse(string)

language.evaluate(parsed.get){
  case 'x => 5
} // == 5*5*2 + 1

val derived = language.derive(parsed.get)('x) // d/dx (2*x*x + 1) == 4*x
language.evaluate(derived){
  case 'x => 5
} // == 4*5


val compiled: Option[Double => Double] = parsed.flatMap(language.compile1)
```

### Features

* Modularity: 
The library is design to let you design your language as you like. 
Using a bunch of traits and the famous cake pattern, it is possible to define a language for exactly your use case.

* Analytic Function Derivative: 
For the predefined languages it is possible to calculate analytical derivatives for arbitrary functions.

* Runtime compilation: 
For the predefined languages it is possible to use the scala compiler at runtime to compile your AST into a high-performance function-object.


### Setup

To use the library extend your sbt build with:
```sbt
resolvers += Resolver.bintrayRepo("gregor-i", "maven"),
libraryDependencies += "com.github.gregor-i" %% "math-parser" % "1.0"
```

Be aware that this project has the scala compiler and spire as runtime dependencies.

### Examples and Usage

To get started take a look into the `examples` folder.
