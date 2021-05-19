package mathParser.boolean

enum BooleanUnitaryOperator(val name: String):
  case Not extends BooleanUnitaryOperator("!")

enum BooleanBinaryOperator(val name: String):
  case And extends BooleanBinaryOperator("&")
  case Or extends BooleanBinaryOperator("|")
  case Equals extends BooleanBinaryOperator("=")
  case Unequals extends BooleanBinaryOperator("!=")
