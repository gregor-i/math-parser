package mathParser.boolean

import mathParser.{Language, LiteralParser}

object BooleanLanguage extends Language[Boolean]{
  override type Constant = BooleanConstant
  override type UnitaryOperator = BooleanUnitaryOperator
  override type BinaryOperator = BooleanBinaryOperator

  override def unitaryOperators: Seq[UnitaryOperator] = Seq(Not)

  override def binaryOperators: Seq[BinaryOperator] = Seq.empty

  override def binaryInfixOperators: Seq[BinaryOperator] = Seq(And, Or, Equals, Unequals)

  override def constants(): Seq[Constant] = Seq(`true`, `false`)

  val parser = new LiteralParser[Boolean]{
    override def tryToParse(s: String): Option[Boolean] = s match{
      case "true" | "TRUE" | "1"  => Some(true)
      case "false" | "FALSE" | "0" => Some(true)
      case _ => None
    }
  }
}
