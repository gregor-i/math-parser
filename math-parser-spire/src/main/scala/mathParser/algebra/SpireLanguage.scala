package mathParser
package algebra

import spire.algebra.{Field, NRoot, Trig}

import scala.util.Try

object SpireLanguage {

  import syntax._

  def apply[A: Field : NRoot : Trig]: SpireLanguage[A, Nothing] =
    Language.emptyLanguage
        .withConstants[A](List("e" -> Trig[A].e, "pi" -> Trig[A].pi))
        .withBinaryOperators[SpireBinaryOperator](
          prefix = List.empty,
          infix = List(Plus, Minus, Times, Divided, Power).map(op => (op.name, op)))
        .withUnitaryOperators(List(Neg, Sin, Cos, Tan, Asin, Acos, Atan, Sinh, Cosh, Tanh, Exp, Log).map(op => (op.name, op)))

  def spireLiteralParser[A: Field]: LiteralParser[A] = s => Try(Field[A].fromDouble(s.toDouble)).toOption

  def spireEvaluate[A: Field : NRoot : Trig, V]: Evaluate[SpireUnitaryOperator, SpireBinaryOperator, A, V] =
    new Evaluate[SpireUnitaryOperator, SpireBinaryOperator, A, V] {
      def executeUnitary(uo: SpireUnitaryOperator, s: A): A = uo match {
        case Neg => Field[A].negate(s)
        case Sin => Trig[A].sin(s)
        case Cos => Trig[A].cos(s)
        case Tan => Trig[A].tan(s)
        case Asin => Trig[A].asin(s)
        case Acos => Trig[A].acos(s)
        case Atan => Trig[A].atan(s)
        case Sinh => Trig[A].sinh(s)
        case Cosh => Trig[A].cosh(s)
        case Tanh => Trig[A].tanh(s)
        case Exp => Trig[A].exp(s)
        case Log => Trig[A].log(s)
      }

      def executeBinaryOperator(bo: SpireBinaryOperator, left: A, right: A): A = bo match {
        case Plus => Field[A].plus(left, right)
        case Minus => Field[A].minus(left, right)
        case Times => Field[A].times(left, right)
        case Divided => Field[A].div(left, right)
        case Power => NRoot[A].fpow(left, right)
      }
    }

  def spireOptimizer[A: Field : NRoot : Trig, V]: Optimizer[SpireUnitaryOperator, SpireBinaryOperator, A, V] =
    new Optimizer[SpireUnitaryOperator, SpireBinaryOperator, A, V] {
      override def rules: List[PartialFunction[SpireNode[A, V], SpireNode[A, V]]] = List(
        Optimize.replaceConstantsRule[SpireUnitaryOperator, SpireBinaryOperator, A, V](spireEvaluate),
        {
          case UnitaryNode(Neg, UnitaryNode(Neg, child)) => child
          case BinaryNode(Plus, left, ConstantNode(0d)) => left
          case BinaryNode(Plus, ConstantNode(0d), right) => right
          case BinaryNode(Times, ConstantNode(0d), _) => zero
          case BinaryNode(Times, _, ConstantNode(0d)) => zero
          case BinaryNode(Times, left, ConstantNode(1d)) => left
          case BinaryNode(Times, ConstantNode(1d), right) => right
          case BinaryNode(Power, left, ConstantNode(1d)) => left
          case BinaryNode(Power, _, ConstantNode(0d)) => one[A, V]
          case BinaryNode(Power, ConstantNode(1d), _) => one[A, V]
          case BinaryNode(Power, ConstantNode(0d), _) => zero[A, V]
          case UnitaryNode(Log, UnitaryNode(Exp, child)) => child
          case BinaryNode(Plus, left, UnitaryNode(Neg, child)) => left - child
          case BinaryNode(Minus, left, UnitaryNode(Neg, child)) => left + child
          case BinaryNode(Minus, left, right) if left == right => zero
          case BinaryNode(Divided, left, right) if left == right => one
        }
      )
    }


  def spireDerive[A: Field : Trig : NRoot, V]: Derive[SpireUnitaryOperator, SpireBinaryOperator, A, V] =
    new Derive[SpireUnitaryOperator, SpireBinaryOperator, A, V] {
      def derive(term: SpireNode[A, V])
                (variable: V): SpireNode[A, V] = {
        def derive(term: SpireNode[A, V]): SpireNode[A, V] = term match {
          case VariableNode(`variable`) => one
          case VariableNode(_) | ConstantNode(_) => zero
          case UnitaryNode(op, f) => op match {
            case Neg => neg(derive(f))
            case Sin => derive(f) * cos(f)
            case Cos => neg(derive(f) * sin(f))
            case Tan => derive(f) / (cos(f) * cos(f))
            case Asin => derive(f) / sqrt(one - (f * f))
            case Acos => neg(derive(f)) / sqrt(one - (f * f))
            case Atan => derive(f) / (one + (f * f))
            case Sinh => derive(f) * cosh(f)
            case Cosh => derive(f) * sinh(f)
            case Tanh => derive(f) / (cosh(f) * cosh(f))
            case Exp => exp(f) * derive(f)
            case Log => derive(f) / f
          }
          case BinaryNode(op, f, g) => op match {
            case Plus => derive(f) + derive(g)
            case Minus => derive(f) - derive(g)
            case Times => (derive(f) * g) + (derive(g) * f)
            case Divided => ((f * derive(g)) - (g * derive(f))) / (g * g)
            case Power => (f ^ (g - one)) * ((g * derive(f)) + (f * log(f) * derive(g)))
          }
        }

        derive(term)
      }
    }

  object syntax {
    def neg[A: Field : Trig : NRoot, V](t: SpireNode[A, V]): SpireNode[A, V] = UnitaryNode(Neg, t)
    def sin[A: Field : Trig : NRoot, V](t: SpireNode[A, V]): SpireNode[A, V] = UnitaryNode(Sin, t)
    def cos[A: Field : Trig : NRoot, V](t: SpireNode[A, V]): SpireNode[A, V] = UnitaryNode(Cos, t)
    def tan[A: Field : Trig : NRoot, V](t: SpireNode[A, V]): SpireNode[A, V] = UnitaryNode(Tan, t)
    def asin[A: Field : Trig : NRoot, V](t: SpireNode[A, V]): SpireNode[A, V] = UnitaryNode(Asin, t)
    def acos[A: Field : Trig : NRoot, V](t: SpireNode[A, V]): SpireNode[A, V] = UnitaryNode(Acos, t)
    def atan[A: Field : Trig : NRoot, V](t: SpireNode[A, V]): SpireNode[A, V] = UnitaryNode(Atan, t)
    def sinh[A: Field : Trig : NRoot, V](t: SpireNode[A, V]): SpireNode[A, V] = UnitaryNode(Sinh, t)
    def cosh[A: Field : Trig : NRoot, V](t: SpireNode[A, V]): SpireNode[A, V] = UnitaryNode(Cosh, t)
    def tanh[A: Field : Trig : NRoot, V](t: SpireNode[A, V]): SpireNode[A, V] = UnitaryNode(Tanh, t)
    def exp[A: Field : Trig : NRoot, V](t: SpireNode[A, V]): SpireNode[A, V] = UnitaryNode(Exp, t)
    def log[A: Field : Trig : NRoot, V](t: SpireNode[A, V]): SpireNode[A, V] = UnitaryNode(Log, t)

    def sqrt[A: Field : Trig : NRoot, V](t: SpireNode[A, V]): SpireNode[A, V] = BinaryNode(Power, t, ConstantNode(Field[A].fromDouble(0.5)))

    implicit class EnrichNode[A: Field : Trig : NRoot, V](t1: SpireNode[A, V]) {
      def +(t2: SpireNode[A, V]): SpireNode[A, V] = BinaryNode(Plus, t1, t2)
      def -(t2: SpireNode[A, V]): SpireNode[A, V] = BinaryNode(Minus, t1, t2)
      def *(t2: SpireNode[A, V]): SpireNode[A, V] = BinaryNode(Times, t1, t2)
      def /(t2: SpireNode[A, V]): SpireNode[A, V] = BinaryNode(Divided, t1, t2)
      def ^(t2: SpireNode[A, V]): SpireNode[A, V] = BinaryNode(Power, t1, t2)
    }

    def zero[A: Field : Trig : NRoot, V]: SpireNode[A, V] = ConstantNode(Field[A].zero)
    def one[A: Field : Trig : NRoot, V]: SpireNode[A, V] = ConstantNode(Field[A].one)
    def two[A: Field : Trig : NRoot, V]: SpireNode[A, V] = ConstantNode(Field[A].fromInt(2))
  }
}
