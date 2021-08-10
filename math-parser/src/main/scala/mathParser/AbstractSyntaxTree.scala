package mathParser

import scala.util.Try

enum AbstractSyntaxTree[O, S, V]:
  case ConstantNode[O, S, V](value: S) extends AbstractSyntaxTree[O, S, V]
  case UnitaryNode[O, S, V](op: O & UnitaryOperator,
                            child: AbstractSyntaxTree[O, S, V]) extends AbstractSyntaxTree[O, S, V]
  case BinaryNode[O, S, V](op: O & BinaryOperator,
                           childLeft: AbstractSyntaxTree[O, S, V],
                           childRight: AbstractSyntaxTree[O, S, V]) extends AbstractSyntaxTree[O, S, V]
  case VariableNode[O, S, V](v: V) extends AbstractSyntaxTree[O, S, V]

  // todo: make tailrec
  def fold[A](ifConstant: S => A, ifUnitary: (O & UnitaryOperator, A) => A, ifBinary: (O & BinaryOperator, A, A) => A, ifVariable: V => A): A = {
    def rec(node: AbstractSyntaxTree[O, S, V]): A = node match {
      case ConstantNode(value)         => ifConstant(value)
      case UnitaryNode(op, child)      => ifUnitary(op, rec(child))
      case BinaryNode(op, left, right) => ifBinary(op, rec(left), rec(right))
      case VariableNode(symbol)        => ifVariable(symbol)
    }

    rec(this)
  }

  def evaluate(variableAssignment: V => S)(using evaluate: Evaluate[O, S]): S =
    evaluate.evaluate(this)(variableAssignment)

  def optimize(using optimizer: Optimizer[O, S]): AbstractSyntaxTree[O, S, V] =
    optimizer.optimize(this)

  def derive(variable: V)(using derive: Derive[O, S]): AbstractSyntaxTree[O, S, V] =
    derive.derive(this)(variable)

  def compile[F](using compiler: Compiler[O, S, V, F]): Try[F] =
    compiler.compile(this)

export AbstractSyntaxTree.*
