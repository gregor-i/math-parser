package mathParser.slices

import mathParser.slices

trait LanguageOperators {
  type Skalar
  type Constant <: slices.Constant[Skalar]
  type UnitaryOperator <: slices.UnitaryOperator[Skalar]
  type BinaryOperator <: slices.BinaryOperator[Skalar]

  def unitaryOperators: Seq[UnitaryOperator]
  def binaryOperators: Seq[BinaryOperator]
  def binaryInfixOperators: Seq[BinaryOperator]
  def constants(): Seq[Constant]
}


trait Named {
  val name: Symbol
}

trait Constant[Skalar] extends Named {
  val apply: Skalar
}

trait UnitaryOperator[Skalar] extends Named {
  val apply: Skalar => Skalar
}

trait BinaryOperator[Skalar] extends Named {
  val apply: (Skalar, Skalar) => Skalar
}

trait Literal[Skalar] {
  val apply: Skalar
}