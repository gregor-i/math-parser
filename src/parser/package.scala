package object parser {
  implicit val a = operators.BooleanOperators.ops
  implicit val b = operators.DoubleOperators.ops

  val doubleParser = new Parser[Double]
  val booleanParser = new Parser[Boolean]
}
