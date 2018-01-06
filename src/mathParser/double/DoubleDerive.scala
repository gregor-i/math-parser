package mathParser.double

import mathParser.slices.{AbstractSyntaxTree, Derive}

trait DoubleDerive extends Derive {
  _: AbstractSyntaxTree with DoubleOperators with DoubleSyntaxSugar =>

  override def derive(term: Node)
                     (variable: Symbol): Node = {
    def derive(term: Node): Node = term match {
      case Variable(name) if name == variable => ConstantNode(1d)
      case Variable(_) | ConstantNode(_) => ConstantNode(0d)
      case UnitaryNode(Neg, child) => neg(derive(child))
      case UnitaryNode(Sin, child) => times(derive(child), cos(child))
      case UnitaryNode(Atan, child) => divided(derive(child), plus(ConstantNode(1d), times(child, child)))
      case in@UnitaryNode(Exp, child) => times(in, derive(child))
      case BinaryNode(Plus, t1, t2) => plus(derive(t1), derive(t2))
      case BinaryNode(Minus, t1, t2) => minus(derive(t1), derive(t2))
      case BinaryNode(Times, t1, t2) => plus(times(derive(t1), t2), times(derive(t2), t1))
      case BinaryNode(Divided, t1, t2) => divided(minus(times(t1, derive(t2)), times(t2, derive(t1))), times(t2, t2))
      case BinaryNode(Power, t1, t2) =>
        // d/dx(f(x)^(g(x))) = f(x)^(g(x) - 1) (g(x) f'(x) + f(x) log(f(x)) g'(x))
        val one = power(t1, minus(t2, ConstantNode(1d)))
        // f(x)^(g(x) - 1)
        val two = times(t2, derive(t1))
        // g(x) f'(x)
        val three = times(times(t1, log(t1)), derive(t2)) // f(x) log(f(x)) g'(x))
        times(one, plus(two, three))
      case x => throw new UnsupportedOperationException("no match for " + x)
    }

    derive(term)
  }
}
