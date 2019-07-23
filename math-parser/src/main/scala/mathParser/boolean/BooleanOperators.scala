package mathParser.boolean

sealed abstract class BooleanUnitaryOperator(val name: String, val apply: Boolean => Boolean)
case object Not extends BooleanUnitaryOperator("!", !_)

sealed abstract class BooleanBinaryOperator(val name: String, val apply: (Boolean, Boolean) => Boolean)
case object And extends BooleanBinaryOperator("&", _ && _)
case object Or extends BooleanBinaryOperator("|", _ || _)
case object Equals extends BooleanBinaryOperator("=", _ == _)
case object Unequals extends BooleanBinaryOperator("!=", _ != _)
