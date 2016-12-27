package operators

trait Variables[A] {
  def variables(implicit operators: Operators[A]): Seq[operators.Variable] = Seq.empty
}

object Variables{
  def emptyVariables[A] = new Variables[A] {}
}