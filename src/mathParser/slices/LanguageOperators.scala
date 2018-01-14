package mathParser.slices

import mathParser.slices

trait LanguageOperators {
  type S
  type Constant <: slices.Constant[S]
  type UnitaryOperator <: slices.UnitaryOperator[S]
  type BinaryOperator <: slices.BinaryOperator[S]

  def unitaryOperators: Seq[UnitaryOperator]
  def binaryOperators: Seq[BinaryOperator]
  def binaryInfixOperators: Seq[BinaryOperator]
  def constants(): Seq[Constant]
}


trait Named {
  val name: Symbol
}

trait Constant[S] extends Named {
  val apply: S
}

trait UnitaryOperator[S] extends Named {
  val apply: S => S
}

trait BinaryOperator[S] extends Named {
  val apply: (S, S) => S
}

trait Literal[S] {
  val apply: S
}