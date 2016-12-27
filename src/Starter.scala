import operators.{Operators, Variables}

object VariablesX extends Variables[Double] {
  override def variables(implicit operators: Operators[Double]): Seq[operators.Variable] = Seq(new operators.Variable("x"))
}

object Starter {
  def exampleDoubles() = {
    import operators.DoubleOperators.ops
    val p = parser.parser[Double](operators.DoubleOperators.ops)
    val t = p.apply("2^-x", VariablesX)
    t.foreach { term =>
      val d = term.apply {
        case "x" => 25
      }
      println(d)
    }
    println(t)
  }

  def exampleBooleans() = {
    import operators.BooleanOperators.ops
    val p = parser.parser[Boolean]
    println(p("!false", Variables.emptyVariables))
  }

  def exampleComplex() = {
    import spire.implicits._
    import spire.math.Complex
    implicit val ops:Operators[Complex[Double]] = operators.ComplexOperators.ops[Double].ops
    val p = parser.parser[Complex[Double]]
    println(p("i", Variables.emptyVariables))
  }

  def main(args: Array[String]): Unit = {
    exampleDoubles()
    exampleBooleans()
    exampleComplex()
  }
}
