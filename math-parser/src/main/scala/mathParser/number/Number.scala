package mathParser.number

trait Number[S]:
  def zero: S
  def one: S
  def two: S
  def oneHalf: S

object Number:
  def zero[S](using number: Number[S]): S    = number.zero
  def one[S](using number: Number[S]): S     = number.one
  def two[S](using number: Number[S]): S     = number.two
  def oneHalf[S](using number: Number[S]): S = number.oneHalf

  def contraMap[A, B](f: A => B)(using number: Number[A]): Number[B] =
    new Number[B]:
      def zero    = f(number.zero)
      def one     = f(number.one)
      def two     = f(number.two)
      def oneHalf = f(number.oneHalf)

  given Number[Double] = new Number[Double]:
    def zero    = 0.0
    def one     = 1.0
    def two     = 2.0
    def oneHalf = 0.5
