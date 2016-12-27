package operators

trait Variables[A] {
  def variables(implicit operators: Operators[A]): Seq[operators.Variable] = Seq.empty
}
