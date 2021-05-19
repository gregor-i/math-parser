package mathParser.number

enum NumberUnitaryOperator(val name: String):
  case Neg extends NumberUnitaryOperator("-")
  case Sin extends NumberUnitaryOperator("sin")
  case Cos extends NumberUnitaryOperator("cos")
  case Tan extends NumberUnitaryOperator("tan")
  case Asin extends NumberUnitaryOperator("asin")
  case Acos extends NumberUnitaryOperator("acos")
  case Atan extends NumberUnitaryOperator("atan")
  case Sinh extends NumberUnitaryOperator("sinh")
  case Cosh extends NumberUnitaryOperator("cosh")
  case Tanh extends NumberUnitaryOperator("tanh")
  case Exp extends NumberUnitaryOperator("exp")
  case Log extends NumberUnitaryOperator("log")

enum NumberBinaryOperator(val name: String):
  case Plus extends NumberBinaryOperator("+")
  case Minus extends NumberBinaryOperator("-")
  case Times extends NumberBinaryOperator("*")
  case Divided extends NumberBinaryOperator("/")
  case Power extends NumberBinaryOperator("^")

export NumberBinaryOperator.*
export NumberUnitaryOperator.*
