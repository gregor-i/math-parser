import operators.{Operators, Variables}

object VariablesX extends Variables[Double] {
  override def variables(implicit operators: Operators[Double]): Seq[operators.Variable] = Seq(new operators.Variable("x"))
}

object Starter {
  def exampleDoubles() = {
    import implicits.doubleHasOperators
    val p = parser.parser[Double]
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
    import implicits.booleanHasOperators
    val p = parser.parser[Boolean]
    println(p("!false", Variables.emptyVariables))
  }

  def exampleComplex() = {
    import implicits.spireComplexHasOperators
    import spire.implicits._
    import spire.math.Complex
    val p = parser.parser[Complex[Double]]
    println(p("i", Variables.emptyVariables))
  }

  def main(args: Array[String]): Unit = {
    exampleDoubles()
    exampleBooleans()
    exampleComplex()
  }
}
