package mathParser.complex

enum ComplexUnitaryOperator(val name: String) {
  case Neg extends ComplexUnitaryOperator("-")
  case Sin extends ComplexUnitaryOperator("sin")
  case Cos extends ComplexUnitaryOperator("cos")
  case Tan extends ComplexUnitaryOperator("tan")
  case Asin extends ComplexUnitaryOperator("asin")
  case Acos extends ComplexUnitaryOperator("acos")
  case Atan extends ComplexUnitaryOperator("atan")
  case Sinh extends ComplexUnitaryOperator("sinh")
  case Cosh extends ComplexUnitaryOperator("cosh")
  case Tanh extends ComplexUnitaryOperator("tanh")
  case Exp extends ComplexUnitaryOperator("exp")
  case Log extends ComplexUnitaryOperator("log")
}

enum ComplexBinaryOperator(val name: String) {
  case Plus extends ComplexBinaryOperator("+")
  case Minus extends ComplexBinaryOperator("-")
  case Times extends ComplexBinaryOperator("*")
  case Divided extends ComplexBinaryOperator("/")
  case Power extends ComplexBinaryOperator("^")
}
