package mathParser.slices

trait AbstractSyntaxTree {
  _: LanguageOperators =>

  sealed trait Node {
    def fold[A](ifConstant: S => A,
                ifUnitary: (UnitaryOperator, A) => A,
                ifBinary: (BinaryOperator, A, A) => A,
                ifVariable: Symbol => A): A = {
      def rec(node: Node): A = node match {
        case ConstantNode(value) => ifConstant(value)
        case UnitaryNode(op, child) => ifUnitary(op, rec(child))
        case BinaryNode(op, left, right) => ifBinary(op, rec(left), rec(right))
        case Variable(symbol) => ifVariable(symbol)
      }

      rec(this)
    }
  }

  case class ConstantNode(value: S) extends Node
  case class UnitaryNode(op: UnitaryOperator, child: Node) extends Node
  case class BinaryNode(op: BinaryOperator, childLeft: Node, childRight: Node) extends Node
  case class Variable(name: Symbol) extends Node
}
