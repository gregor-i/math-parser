package mathParser.slices

trait Derive {
  _: AbstractSyntaxTree =>
  def derive(term: Node)(variable: Symbol): Node
}
