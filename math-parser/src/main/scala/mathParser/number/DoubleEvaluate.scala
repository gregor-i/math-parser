package mathParser.number

import mathParser.Evaluate
import mathParser.number.*
import mathParser.number.NumberOperator.*

final class DoubleEvaluate[V] extends Evaluate[NumberUnitaryOperator, NumberBinaryOperator, Double, V] {

  import Math._

   def executeUnitary(uo: NumberUnitaryOperator, a: Double): Double = (uo: Any) match {
    case Neg  => -a
    case Sin  => sin(a)
    case Cos  => cos(a)
    case Tan  => tan(a)
    case Asin => asin(a)
    case Acos => acos(a)
    case Atan => atan(a)
    case Sinh => sinh(a)
    case Cosh => cosh(a)
    case Tanh => tanh(a)
    case Exp  => exp(a)
    case Log  => log(a)
    case _ => ???

   }

   def executeBinaryOperator(bo: NumberBinaryOperator, a: Double, b: Double): Double =
     (bo: Any) match {
      case Plus    => a+b
      case Minus   => a - b
      case Times   => a * b
      case Divided => a / b
      case Power   => pow(a, b)
      case _ => ???

    }
}