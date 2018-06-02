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

trait Constant[S] {
  val symbol: Symbol
  val value: S
}

trait Literal[S] {
  val value: S
}

trait UnitaryOperator[S] {
  val symbol: Symbol
  val apply: S => S
}

trait BinaryOperator[S] {
  val symbol: Symbol
  val apply: (S, S) => S
}