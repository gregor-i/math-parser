import operators.Operators

package object parser {
  def parser[A : Operators] = new Parser[A]
}
