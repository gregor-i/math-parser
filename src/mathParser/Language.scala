package mathParser

trait Language[Skalar] {
  type Constant <: mathParser.Constant[Skalar]
  type UnitaryOperator <: mathParser.UnitaryOperator[Skalar]
  type BinaryOperator <: mathParser.BinaryOperator[Skalar]

  def unitaryOperators: Seq[UnitaryOperator]
  def binaryOperators: Seq[BinaryOperator]
  def binaryInfixOperators: Seq[BinaryOperator]
  def constants(): Seq[Constant]
}
