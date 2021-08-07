package mathParser

import scala.util.Try

final case class Language[O, S, V](
    unitaryOperators: List[(String, O & UnitaryOperator)],
    binaryPrefixOperators: List[(String, O & BinaryOperator)],
    binaryInfixOperators: List[(String, O & BinaryOperator)],
    constants: List[(String, S)],
    variables: List[(String, V)]
) {
  require(declaredNames.distinct == declaredNames)

  def declaredNames: List[String] = constants.map(_._1) ++ variables.map(_._1) ++ unitaryOperators.map(_._1) ++ binaryPrefixOperators.map(_._1)

  def withUnitaryOperators[O2](ops: List[(String, O2 & UnitaryOperator)]): Language[O | O2, S, V] =
    copy(unitaryOperators = ops)

  def withBinaryOperators[O2](prefix: List[(String, O2 & BinaryOperator)], infix: List[(String, O2 & BinaryOperator)]): Language[O | O2, S, V] =
    copy(binaryPrefixOperators = prefix, binaryInfixOperators = infix)

  def addConstant[S2](name: String, constant: S2): Language[O, S | S2, V] =
    copy(constants = (name -> constant) :: constants)

  def withConstants[S2](constants: List[(String, S2)]): Language[O, S2, V] =
    copy(constants = constants)

  def withVariables[V2](variables: List[(String, V2)]): Language[O, S, V2] =
    copy(variables = variables)

  def withUnitaryOperator[O2](name: String, op: O2 & UnitaryOperator): Language[O | O2, S, V] =
    copy(unitaryOperators = (name -> op) :: unitaryOperators)

  def withBinaryInfixOperator[O2](name: String, op: O2 & BinaryOperator): Language[O | O2, S, V] =
    copy(binaryInfixOperators = (name -> op) :: binaryInfixOperators)

  def withBinaryPrefixOperator[O2](name: String, op: O2 & BinaryOperator): Language[O | O2, S, V] =
    copy(binaryPrefixOperators = (name -> op) :: binaryPrefixOperators)

  // todo: remove
  def variable(variable: V): VariableNode[O, S, V] = VariableNode(variable)
  def constantNode(value: S): ConstantNode[O, S, V] = ConstantNode(value)

  def parse(term: String)(using literalParser: LiteralParser[S]): Option[AbstractSyntaxTree[O, S, V]] =
    Parser.parse(this, literalParser)(term)

  def evaluate(node: AbstractSyntaxTree[O, S, V])(variableAssignment: V => S)(using evaluate: Evaluate[O, S, V]): S =
    evaluate.evaluate(node)(variableAssignment)

  def derive(node: AbstractSyntaxTree[O, S, V])(variable: V)(using derive: Derive[O, S, V]): AbstractSyntaxTree[O, S, V] =
    derive.derive(node)(variable)

  def optimize(node: AbstractSyntaxTree[O, S, V])(using optimizer: Optimizer[O, S, V]): AbstractSyntaxTree[O, S, V] =
    optimizer.optimize(node)

  def compile[F](node: AbstractSyntaxTree[O, S, V])(using compiler: Compiler[O, S, V, F]): Try[F] =
    compiler.compile(node)
}

object Language {
  val emptyLanguage = Language[Nothing, Nothing, Nothing](Nil, Nil, Nil, Nil, Nil)
}
