package mathParser.algebra

import mathParser.Evaluate
import mathParser.number.{NumberBinaryOperator, NumberOperator, NumberUnitaryOperator}
import spire.algebra.{Field, NRoot, Trig}
import mathParser.number.NumberOperator.*

class SpireEvaluate[A: Field: NRoot: Trig] extends Evaluate[NumberOperator, A] {
  def executeUnitary(uo: NumberUnitaryOperator, s: A): A = uo match {
    case Neg  => Field[A].negate(s)
    case Sin  => Trig[A].sin(s)
    case Cos  => Trig[A].cos(s)
    case Tan  => Trig[A].tan(s)
    case Asin => Trig[A].asin(s)
    case Acos => Trig[A].acos(s)
    case Atan => Trig[A].atan(s)
    case Sinh => Trig[A].sinh(s)
    case Cosh => Trig[A].cosh(s)
    case Tanh => Trig[A].tanh(s)
    case Exp  => Trig[A].exp(s)
    case Log  => Trig[A].log(s)
  }

  def executeBinaryOperator(bo: NumberBinaryOperator, left: A, right: A): A = bo match {
    case Plus    => Field[A].plus(left, right)
    case Minus   => Field[A].minus(left, right)
    case Times   => Field[A].times(left, right)
    case Divided => Field[A].div(left, right)
    case Power   => NRoot[A].fpow(left, right)
  }
}
