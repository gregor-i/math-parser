# math-parser

### Introduction and Motivation

`mathParser` is a small Library created for a single purpose: 
It parses strings into abstract syntax trees (AST) of math-like languages.
The AST can be used to easily reason over it, modify it, optimize it or derive it.

There are some predefined example languages, but the library is intended to be extended by your language to suite your needs.

### Quick Overview

```scala
// your input, any string represesenting a function:
val string = "2*x*x + 1"

val language = mathParser.MathParser.doubleLanguage('x)
val parsed = language.parse(string)

language.evaluate(parsed.get){
    case 'x => 5
} // == 2*5*5 + 1 == 51

val derived = language.derive(parsed.get)('x) // d/dx (2*x*x + 1) == 4*x
language.evaluate(derived){
    case 'x => 5
} // == 4*5 == 20
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
libraryDependencies += "com.github.gregor-i" %% "math-parser" % "1.2"
```

If you want to use runtime compilation (only availible on the jvm), use the following library dependency.
Be aware that this has the scala compiler as runtime dependencies.
```sbt
resolvers += Resolver.bintrayRepo("gregor-i", "maven"),
libraryDependencies += "com.github.gregor-i" %% "math-parser-compile-jvm" % "1.2"
```

### Examples and Usage

To get started take a look into the `examples` folder.


### Changelog
#### 1.3:
- Cross compilation for scala-js
- runtime compilation has been extracted from `math-parser` and moved into its own module `math-parser-compile-jvm`.

#### 1.2:
- Fixed missing constant `i` for `ComplexLanguage`