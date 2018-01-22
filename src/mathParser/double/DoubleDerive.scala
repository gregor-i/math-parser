package mathParser.double

import mathParser.slices.{AbstractSyntaxTree, Derive}

trait DoubleDerive extends Derive {
  _: AbstractSyntaxTree with DoubleOperators with DoubleSyntaxSugar =>

  override def derive(term: Node)
                     (variable: Symbol): Node = {
    def derive(term: Node): Node = term match {
      case Variable(name) if name == variable => one
      case Variable(_) | ConstantNode(_) => zero
      case UnitaryNode(op: UnitaryOperator, f) => op match {
        case Neg => neg(derive(f))
        case Sin => derive(f) *  cos(f)
        case Cos => neg(derive(f) * sin(f))
        case Tan => derive(f)/(cos(f) * cos(f))
        case Asin => derive(f) / ((one - (f * f)) ^ ConstantNode(0.5))
        case Acos => neg(derive(f))/((one - (f * f)) ^ ConstantNode(0.5))
        case Atan => derive(f) / (one + (f * f))
        case Sinh => derive(f) * cosh(f)
        case Cosh => derive(f) * sinh(f)
        case Tanh => derive(f)/(cosh(f) * cosh(f))
        case Exp => exp(f) * derive(f)
        case Log => derive(f) / f
      }
      case BinaryNode(op: BinaryOperator, f, g) => op match {
        case Plus => derive(f) + derive(g)
        case Minus => derive(f) - derive(g)
        case Times => (derive(f) * g) + (derive(g) * f)
        case Divided => ((f * derive(g)) - (g * derive(f))) / (g * g)
        case Power if g.isInstanceOf[ConstantNode] => g * (f ^ (g - one)) * derive(f)
        case Power => (f ^ (g - one)) * ((g * derive(f)) + (f * log(f) * derive(g)))
      }
    }

    derive(term)
  }
}
