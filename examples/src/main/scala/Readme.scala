object Readme extends App {
  // your input, any string represesenting a function:
  val string = "2*x*x + 1"

  import mathParser.number.DoubleLanguage.given

  // define your language:
  object X
  val language = mathParser.BuildIn.doubleLanguage.addVariable("x", X)

  // parsing: string => Option[AST]
  // Option.get only to demonstrate.
  val parsed = language.parse(string).get

  // evaluating:
  parsed.evaluate { case X =>
    5 // assign values to your variables
  }   // == 2*5*5 + 1 == 51

  // deriving:
  val derived = parsed.derive(X) // d/dx (2*x*x + 1) == 4*x
  derived.evaluate { case X =>
    5
  } // == 4*5 == 20
}
