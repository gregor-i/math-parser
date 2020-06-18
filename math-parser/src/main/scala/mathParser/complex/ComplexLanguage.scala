package mathParser.complex

import mathParser._

object ComplexLanguage {

  import syntax._

  def apply: ComplexLanguage[Nothing] =
    Language.emptyLanguage
        .withConstants[Complex](List("e" -> Complex.e, "pi" -> Complex.pi, "i" -> Complex.i))
        .withBinaryOperators[ComplexBinaryOperator](
          prefix = List.empty,
          infix = List(Plus, Minus, Times, Divided, Power).map(op => (op.name, op)))
        .withUnitaryOperators(List(Neg, Sin, Cos, Tan, Asin, Acos, Atan, Sinh, Cosh, Tanh, Exp, Log).map(op => (op.name, op)))

  def complexLiteralParser: LiteralParser[Complex] = s => s.toDoubleOption.map(Complex(_, 0.0))

  def complexEvaluate[V]: Evaluate[ComplexUnitaryOperator, ComplexBinaryOperator, Complex, V] = ComplexEvaluate[V]

  def complexOptimizer[V]: Optimizer[ComplexUnitaryOperator, ComplexBinaryOperator, Complex, V] =
    new Optimizer[ComplexUnitaryOperator, ComplexBinaryOperator, Complex, V] {
      override def rules: List[PartialFunction[ComplexNode[V], ComplexNode[V]]] = List(
        Optimize.replaceConstantsRule[ComplexUnitaryOperator, ComplexBinaryOperator, Complex, V](complexEvaluate[V]),
        {
          case UnitaryNode(Neg, UnitaryNode(Neg, child)) => child
          case BinaryNode(Plus, left, ConstantNode(Complex(0d, 0d))) => left
          case BinaryNode(Plus, ConstantNode(Complex(0d, 0d)), right) => right
          case BinaryNode(Times, ConstantNode(Complex(0d, 0d)), _) => zero
          case BinaryNode(Times, _, ConstantNode(Complex(0d, 0d))) => zero
          case BinaryNode(Times, left, ConstantNode(Complex(1d, 0d))) => left
          case BinaryNode(Times, ConstantNode(Complex(1d, 0d)), right) => right
          case BinaryNode(Power, left, ConstantNode(Complex(1d, 0d))) => left
          case BinaryNode(Power, _, ConstantNode(Complex(0d, 0d))) => one[V]
          case BinaryNode(Power, ConstantNode(Complex(1d, 0d)), _) => one[V]
          case BinaryNode(Power, ConstantNode(Complex(0d, 0d)), _) => zero[V]
          case UnitaryNode(Log, UnitaryNode(Exp, child)) => child
          case BinaryNode(Plus, left, UnitaryNode(Neg, child)) => left - child
          case BinaryNode(Minus, left, UnitaryNode(Neg, child)) => left + child
          case BinaryNode(Minus, left, right) if left == right => zero
          case BinaryNode(Divided, left, right) if left == right => one
        }
      )
    }


  def spireDerive[V]: Derive[ComplexUnitaryOperator, ComplexBinaryOperator, Complex, V] =
    new Derive[ComplexUnitaryOperator, ComplexBinaryOperator, Complex, V] {
      def derive(term: ComplexNode[V])
                (variable: V): ComplexNode[V] = {
        def derive(term: ComplexNode[V]): ComplexNode[V] = term match {
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
    implicit def doubleAsComplex(d: Double): Complex = Complex(d, 0.0)

    def neg[V](t: ComplexNode[V]): ComplexNode[V] = UnitaryNode(Neg, t)
    def sin[V](t: ComplexNode[V]): ComplexNode[V] = UnitaryNode(Sin, t)
    def cos[V](t: ComplexNode[V]): ComplexNode[V] = UnitaryNode(Cos, t)
    def tan[V](t: ComplexNode[V]): ComplexNode[V] = UnitaryNode(Tan, t)
    def asin[V](t: ComplexNode[V]): ComplexNode[V] = UnitaryNode(Asin, t)
    def acos[V](t: ComplexNode[V]): ComplexNode[V] = UnitaryNode(Acos, t)
    def atan[V](t: ComplexNode[V]): ComplexNode[V] = UnitaryNode(Atan, t)
    def sinh[V](t: ComplexNode[V]): ComplexNode[V] = UnitaryNode(Sinh, t)
    def cosh[V](t: ComplexNode[V]): ComplexNode[V] = UnitaryNode(Cosh, t)
    def tanh[V](t: ComplexNode[V]): ComplexNode[V] = UnitaryNode(Tanh, t)
    def exp[V](t: ComplexNode[V]): ComplexNode[V] = UnitaryNode(Exp, t)
    def log[V](t: ComplexNode[V]): ComplexNode[V] = UnitaryNode(Log, t)

    def sqrt[V](t: ComplexNode[V]): ComplexNode[V] = BinaryNode(Power, t, ConstantNode(0.5))

    implicit class EnrichNode[V](t1: ComplexNode[V]) {
      def +(t2: ComplexNode[V]): ComplexNode[V] = BinaryNode(Plus, t1, t2)
      def -(t2: ComplexNode[V]): ComplexNode[V] = BinaryNode(Minus, t1, t2)
      def *(t2: ComplexNode[V]): ComplexNode[V] = BinaryNode(Times, t1, t2)
      def /(t2: ComplexNode[V]): ComplexNode[V] = BinaryNode(Divided, t1, t2)
      def ^(t2: ComplexNode[V]): ComplexNode[V] = BinaryNode(Power, t1, t2)
    }

    def zero[V]: ComplexNode[V] = ConstantNode(0.0)
    def one[V]: ComplexNode[V] = ConstantNode(1.0)
    def two[V]: ComplexNode[V] = ConstantNode(2.0)
  }
}
