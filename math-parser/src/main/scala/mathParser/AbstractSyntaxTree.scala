package mathParser

enum AbstractSyntaxTree[UO, BO, S, V]:
  case ConstantNode[UO, BO, S, V](value: S)                                                            extends AbstractSyntaxTree[UO, BO, S, V]
  case UnitaryNode[UO, BO, S, V](op: UO, child: AbstractSyntaxTree[UO, BO, S, V])                                    extends AbstractSyntaxTree[UO, BO, S, V]
  case BinaryNode[UO, BO, S, V](op: BO, childLeft: AbstractSyntaxTree[UO, BO, S, V], childRight: AbstractSyntaxTree[UO, BO, S, V]) extends AbstractSyntaxTree[UO, BO, S, V]
  case VariableNode[UO, BO, S, V](v: V)                                                                extends AbstractSyntaxTree[UO, BO, S, V]

  // todo: make tailrec
  def fold[A](ifConstant: S => A, ifUnitary: (UO, A) => A, ifBinary: (BO, A, A) => A, ifVariable: V => A): A = {
    def rec(node: AbstractSyntaxTree[UO, BO, S, V]): A = node match {
      case ConstantNode(value)         => ifConstant(value)
      case UnitaryNode(op, child)      => ifUnitary(op, rec(child))
      case BinaryNode(op, left, right) => ifBinary(op, rec(left), rec(right))
      case VariableNode(symbol)        => ifVariable(symbol)
    }

    rec(this)
  }

export AbstractSyntaxTree.*
