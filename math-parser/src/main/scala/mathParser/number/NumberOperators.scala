package mathParser.number

import mathParser.BinaryOperator
import mathParser.UnitaryOperator

enum NumberOperator(val name: String):
  case Neg extends NumberOperator("-") with UnitaryOperator
  case Sin extends NumberOperator("sin") with UnitaryOperator
  case Cos extends NumberOperator("cos") with UnitaryOperator
  case Tan extends NumberOperator("tan") with UnitaryOperator
  case Asin extends NumberOperator("asin") with UnitaryOperator
  case Acos extends NumberOperator("acos") with UnitaryOperator
  case Atan extends NumberOperator("atan") with UnitaryOperator
  case Sinh extends NumberOperator("sinh") with UnitaryOperator
  case Cosh extends NumberOperator("cosh") with UnitaryOperator
  case Tanh extends NumberOperator("tanh") with UnitaryOperator
  case Exp extends NumberOperator("exp") with UnitaryOperator
  case Log extends NumberOperator("log") with UnitaryOperator
  case Plus extends NumberOperator("+") with BinaryOperator
  case Minus extends NumberOperator("-") with BinaryOperator
  case Times extends NumberOperator("*") with BinaryOperator
  case Divided extends NumberOperator("/") with BinaryOperator
  case Power extends NumberOperator("^") with BinaryOperator


type NumberBinaryOperator = NumberOperator & BinaryOperator
type NumberUnitaryOperator = NumberOperator & UnitaryOperator
