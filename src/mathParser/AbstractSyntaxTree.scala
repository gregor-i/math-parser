package mathParser

object AbstractSyntaxTree {
  sealed trait Node[S, Lang <: Language[S]]
  case class Constant[S, Lang <: Language[S]](value:S) extends Node[S, Lang]
  case class UnitaryNode[S, Lang <: Language[S]](op:UnitaryOperator[S], child:Node[S, Lang]) extends Node[S, Lang]
  case class BinaryNode[S, Lang <: Language[S]](op:BinaryOperator[S], childLeft:Node[S, Lang], childRight:Node[S, Lang]) extends Node[S, Lang]
  case class Variable[S, Lang <: Language[S]](name: Symbol) extends Node[S, Lang]
}
