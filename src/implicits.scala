import operators.{BooleanOperators, ComplexOperators, DoubleOperators, Operators}
import spire.algebra.{Field, IsReal, NRoot, Trig}

object implicits {
  implicit val doubleHasOperators:Operators[Double] = DoubleOperators
  implicit val booleanHasOperators:Operators[Boolean] = BooleanOperators
  implicit def spireComplexHasOperators[A: Field : Trig : NRoot : IsReal]: ComplexOperators[A] = new ComplexOperators()
}
