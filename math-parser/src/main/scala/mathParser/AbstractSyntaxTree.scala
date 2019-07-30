package mathParser

sealed trait Node[UO, BO, S, V] {

  // todo: make tailrec
  def fold[A](ifConstant: S => A,
              ifUnitary: (UO, A) => A,
              ifBinary: (BO, A, A) => A,
              ifVariable: V => A): A = {
    def rec(node: Node[UO, BO, S, V]): A = node match {
      case ConstantNode(value) => ifConstant(value)
      case UnitaryNode(op, child) => ifUnitary(op, rec(child))
      case BinaryNode(op, left, right) => ifBinary(op, rec(left), rec(right))
      case VariableNode(symbol) => ifVariable(symbol)
    }

    rec(this)
  }
}

case class ConstantNode[UO, BO, S, V](value: S) extends Node[UO, BO, S, V]
case class UnitaryNode[UO, BO, S, V](op: UO, child: Node[UO, BO, S, V]) extends Node[UO, BO, S, V]
case class BinaryNode[UO, BO, S, V](op: BO, childLeft: Node[UO, BO, S, V], childRight: Node[UO, BO, S, V]) extends Node[UO, BO, S, V]
case class VariableNode[UO, BO, S, V](v: V) extends Node[UO, BO, S, V]
