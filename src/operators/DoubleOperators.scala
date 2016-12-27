package operators

import scala.util.Try

object DoubleOperators extends Operators[Double] {
  val neg = new UnitaryOperator("-", -_)
  val sin = new UnitaryOperator("sin", Math.sin)
  val cos = new UnitaryOperator("cos", Math.cos)
  val tan = new UnitaryOperator("tan", Math.tan)
  val asin = new UnitaryOperator("asin", Math.asin)
  val acos = new UnitaryOperator("acos", Math.acos)
  val atan = new UnitaryOperator("atan", Math.atan)
  val sinh = new UnitaryOperator("sinh", Math.sinh)
  val cosh = new UnitaryOperator("cosh", Math.cosh)
  val tanh = new UnitaryOperator("tanh", Math.tanh)
  val exp = new UnitaryOperator("exp", Math.exp)
  val log = new UnitaryOperator("log", Math.log)

  val plus = new BinaryOperator("+", _ + _)
  val minus = new BinaryOperator("-", _ - _)
  val times = new BinaryOperator("*", _ * _)
  val divided = new BinaryOperator("/", _ / _)
  val power = new BinaryOperator("^", Math.pow)

  val e = new Constant("e", Math.E)
  val pi = new Constant("pi", Math.PI)

  override def parseLiteral(input: String): Option[Literal] = Try(input.toDouble).map(Literal).toOption

  override def constants(): Seq[Constant] = Seq(e, pi)

  override def binaryOperators: Seq[BinaryOperator] = Seq()

  override def binaryInfixOperators: Seq[BinaryOperator] = Seq(plus, minus, times, divided, power)

  override def unitaryOperators: Seq[UnitaryOperator] = Seq(neg, sin, cos, tan, asin, acos, atan, sinh, cosh, tanh, exp, log)
}