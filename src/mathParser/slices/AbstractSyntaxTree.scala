package mathParser.slices

trait AbstractSyntaxTree {
  _: LanguageOperators =>

  sealed trait Node
  case class ConstantNode(value: S) extends Node
  case class UnitaryNode(op: UnitaryOperator, child: Node) extends Node
  case class BinaryNode(op: BinaryOperator, childLeft: Node, childRight: Node) extends Node
  case class Variable(name: Symbol) extends Node
}
