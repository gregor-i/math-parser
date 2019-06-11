object Readme {
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
}
