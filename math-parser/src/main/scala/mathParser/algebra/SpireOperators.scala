package mathParser
package algebra

sealed abstract class SpireUnitaryOperator(val name: String)
case object Neg extends SpireUnitaryOperator("-")
case object Sin extends SpireUnitaryOperator("sin")
case object Cos extends SpireUnitaryOperator("cos")
case object Tan extends SpireUnitaryOperator("tan")
case object Asin extends SpireUnitaryOperator("asin")
case object Acos extends SpireUnitaryOperator("acos")
case object Atan extends SpireUnitaryOperator("atan")
case object Sinh extends SpireUnitaryOperator("sinh")
case object Cosh extends SpireUnitaryOperator("cosh")
case object Tanh extends SpireUnitaryOperator("tanh")
case object Exp extends SpireUnitaryOperator("exp")
case object Log extends SpireUnitaryOperator("log")

sealed abstract class SpireBinaryOperator(val name: String)
case object Plus extends SpireBinaryOperator("+")
case object Minus extends SpireBinaryOperator("-")
case object Times extends SpireBinaryOperator("*")
case object Divided extends SpireBinaryOperator("/")
case object Power extends SpireBinaryOperator("^")

