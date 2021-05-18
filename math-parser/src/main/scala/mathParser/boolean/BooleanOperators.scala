package mathParser.boolean


enum BooleanUnitaryOperator(val name: String, val apply: Boolean => Boolean):
  case Not extends BooleanUnitaryOperator("!", !_)

enum BooleanBinaryOperator(val name: String, val apply: (Boolean, Boolean) => Boolean):
  case And extends BooleanBinaryOperator("&", _ && _)
  case Or extends BooleanBinaryOperator("|", _ || _)
  case Equals extends BooleanBinaryOperator("=", _ == _)
  case Unequals extends BooleanBinaryOperator("!=", _ != _)
