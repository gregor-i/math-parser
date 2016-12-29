package deriving

import operators.{ComplexOperators, DoubleOperators, Operators}
import spire.algebra.{Field, IsReal, NRoot, Trig}
import spire.math.Complex

trait Derive[A] {
  _: Operators[A] =>

  def derive(term: Term)(variable: Variable): Term
}


/*object DeriveDoubles extends Derive[DoubleOperators.type] {
  override def apply(term: Term)(variable: Variable)(implicit ops: DoubleOperators.type): Term =
    derive(term)(variable)(ops)

  import operators.{DoubleOperators => ops}

  def neg(t: Term) = ops.UnitaryNode(ops.neg, t)

  def times(t1: Term, t2: Term) = ops.BinaryNode(ops.times, t1, t2)

  def derive(term: Term)(variable: Variable)(implicit ops: DoubleOperators.type): Term =
    term match {
      case `variable` => ops.Literal(1d)
      case ops.Variable(_) => ops.Literal(0d)
      case ops.Literal(_) => ops.Literal(0d)
      case ops.Constant(_, _) => ops.Literal(0d)
      case ops.UnitaryNode(op, t) =>
        import ops._
        val abl = apply(t)(variable)
        op match {
          case `neg` => neg(abl)
          case `sin` => times(abl, UnitaryNode(cos, t))
          case `cos` => times(abl, neg(UnitaryNode(sin, t)))
          case _ => ???
        }
      case ops.BinaryNode(op, t1, t2) =>
        lazy val abl1 = apply(t1)(variable)
        lazy val abl2 = apply(t2)(variable)
        op match {
          case ops.plus => BinaryNode(plus, abl1, abl2)
          case ops.minus => BinaryNode(minus, abl1, abl2)
          case ops.times => BinaryNode(plus, BinaryNode(times, abl1, t2), BinaryNode(times, abl2, t1))
          case ops.divided => BinaryNode(divided, BinaryNode(minus, BinaryNode(times, t1, abl2), BinaryNode(times, t2, abl1)), BinaryNode(times, t2, t2))
          case ops.power => ??? //            g(x) f(x)^(g(x) - 1) f'(x) + f(x)^(g(x)) log(f(x)) g'(x)
        }
    }
}*/