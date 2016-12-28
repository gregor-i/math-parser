package deriving

import operators.{ComplexOperators, DoubleOperators, Operators}
import spire.algebra.{Field, IsReal, NRoot, Trig}
import spire.math.Complex

trait Derive[A, Ops <: Operators[A]] {
  type Term = Ops#Term
  type Variable = Ops#Variable

  def apply(term: Term)(variable: Variable)(implicit ops: Ops): Term
}

object Derive{
  def apply[A, Ops <: Operators[A]](term:Ops#Term)(variable:Ops#Variable)(implicit d:Derive[A, Ops], ops:Ops):Ops#Term =
    d.apply(term)(variable)(ops)
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


class DeriveSpireComplex[A: Field : Trig : NRoot : IsReal] extends Derive[Complex[A], ComplexOperators[A]] {
  val field: Field[A] = implicitly



  override def apply(term: Term)(variable: Variable)(implicit ops: ComplexOperators[A]): Term =
    derive(term)(variable)(ops)

  def derive(term: Term)(variable: Variable)(implicit ops: ComplexOperators[A]): Term = {
    implicit def kackDat1(t:Term):ops.Term = t.asInstanceOf
    implicit def kackDat2(t:ops.Term):Term = t.asInstanceOf

    def neg(t: Term):Term = new ops.UnitaryNode(ops.neg, t)
    def cos(t: Term):Term = new ops.UnitaryNode(ops.cos, t)
    def sin(t: Term):Term = new ops.UnitaryNode(ops.sin, t)
    def log(t: Term):Term = new ops.UnitaryNode(ops.log, t)
    def times(a: Term, b:Term):Term = new ops.BinaryNode(ops.times, a, b)
    def plus(a: Term, b:Term):Term = new ops.BinaryNode(ops.plus, a, b)
    def minus(a: Term, b:Term):Term = new ops.BinaryNode(ops.minus, a, b)
    def divided(a: Term, b:Term):Term = new ops.BinaryNode(ops.divided, a, b)
    def power(a: Term, b:Term):Term = new ops.BinaryNode(ops.power, a, b)
    def literal(d: Double): Term = new ops.Literal(Complex(field.fromDouble(d), field.fromDouble(0)))

    println("Deriving: " + term)

    term match {
      case `variable` => literal(1d)
      case ops.Variable(_) => literal(0d)
      case ops.Literal(_) => literal(0d)
      case ops.Constant(_, _) => literal(0d)
      case ops.UnitaryNode(op, t) =>
        val abl = derive(t)(variable)(ops)
        op match {
          case ops.neg => neg(abl)
          case ops.sin => times(abl, cos(t))
          case ops.cos => times(abl, neg(sin(t)))
          case _ => ??? // todo
        }
      case ops.BinaryNode(op, t1, t2) =>
        val abl1 = derive(t1)(variable)
        val abl2 = derive(t2)(variable)
        op match {
          case ops.plus => plus(abl1, abl2)
          case ops.minus => minus(abl1, abl2)
          case ops.times => plus(times(abl1, t2), times(abl2, t1))
          case ops.divided => divided(minus(times(t1, abl2), times(t2, abl1)), times(t2, t2))
          case ops.power =>
            // d/dx(f(x)^(g(x))) = f(x)^(g(x) - 1) (g(x) f'(x) + f(x) log(f(x)) g'(x))
            val one = power(t1, minus(t2, literal(1))) // f(x)^(g(x) - 1)
            val two = times(t2, abl1) // g(x) f'(x)
            val three = times(times(t1, log(t1)), abl2) // f(x) log(f(x)) g'(x))
            times(one, plus(two, three))
        }
    }
  }
}