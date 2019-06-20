object Readme extends App {
  // your input, any string represesenting a function:
  val string = "2*x*x + 1"

  import mathParser.implicits._

  // define your language:
  object X
  val language = mathParser.MathParser.doubleLanguage
    .withVariables(List('x -> X))

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
}
