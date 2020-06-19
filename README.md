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

import mathParser.SpireImplicits._

// define your language:
object X
val language = mathParser.SpireLanguages.doubleLanguage
.withVariables(List("x" -> X))

// parsing: string => Option[AST]
// .get only to demonstrate.
val parsed = language.parse(string).get

// evaluating:
language.evaluate(parsed){
case X => 5 // assign values to your variables
} // == 5*5*2 + 1 == 51

// deriving:
val derived = language.derive(parsed)(X) // d/dx (2*x*x + 1) == 4*x
language.evaluate(derived){
case X => 5
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
libraryDependencies += "com.github.gregor-i" %% "math-parser" % {current-version}
```

There is a subproject to work with `spire`. 
```sbt
libraryDependencies += "com.github.gregor-i" %% "math-parser-spire" % {current-version}
```

If you want to use runtime compilation (only availible on the jvm), use the following library dependency.
Be aware that this has the scala compiler as runtime dependencies.
```sbt
libraryDependencies += "com.github.gregor-i" %% "math-parser-compile-jvm" % {current-version}
```

### Examples and Usage

To get started take a look into the `examples` folder.


### Changelog
#### 1.6:
- updated `scalajs` to 1.1.
- `spire` was extracted into its own submodule. This will not be build for scalajs, as `spire` is not compatible with `scalajs` 1.1.
- Core now contains an implementation for complex numbers. Some imports have to be changed.  
- Dropped support for `scala` 2.12.

#### 1.5.3:
- performance optimization
- enrichments for `Node`

#### 1.5.2:
- dependency updates
- jar size reduced

#### 1.5.1:
- performance optimization

#### 1.5:
- upgrade to scala 2.13
- replaced Symbols with String

#### 1.4:
- reworked the whole library. It no longer uses the cake pattern, but instead uses typeclasses.
- It now easier to construct a language and to work with it. No more path dependant typing.

#### 1.3:
- Cross compilation for scala-js
- runtime compilation has been extracted from `math-parser` and moved into its own module `math-parser-compile-jvm`.

#### 1.2:
- Fixed missing constant `i` for `ComplexLanguage`
