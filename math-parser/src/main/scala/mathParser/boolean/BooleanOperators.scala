package mathParser.boolean

import mathParser.{BinaryOperator, UnitaryOperator}

enum BooleanOperator(val name: String):
  case Not extends BooleanOperator("!") with UnitaryOperator
  case And extends BooleanOperator("&") with BinaryOperator
  case Or extends BooleanOperator("|") with BinaryOperator
  case Equals extends BooleanOperator("=") with BinaryOperator
  case Unequals extends BooleanOperator("!=") with BinaryOperator
