package operators

import scala.util.Try

object BooleanOperators {
  implicit val ops:Operators[Boolean] = new Operators[Boolean] {
    val not = new UnitaryOperator("!", !_)

    val and = new BinaryOperator("&", _ && _)
    val or = new BinaryOperator("|", _ || _)
    val equals = new BinaryOperator("|", _ == _)
    val unequals = new BinaryOperator("|", _ != _)

    val `true` = new Constant("true", true)
    val `false` = new Constant("false", false)

    override def parseLiteral(input: String): Option[Literal] = Try{input.toBoolean}.map(Literal).toOption

    override def unitaryOperators: Seq[UnitaryOperator] = Seq(not)

    override def constants(): Seq[Constant] = Seq(`true`, `false`)

    override def binaryOperators: Seq[BinaryOperator] = Seq.empty

    override def binaryInfixOperators: Seq[BinaryOperator] = Seq(and, or, equals, unequals)
  }
}
