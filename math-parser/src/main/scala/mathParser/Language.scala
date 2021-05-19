package mathParser

import scala.util.Try

final case class Language[UO, BO, S, V](
    unitaryOperators: List[(String, UO)],
    binaryPrefixOperators: List[(String, BO)],
    binaryInfixOperators: List[(String, BO)],
    constants: List[(String, S)],
    variables: List[(String, V)]
) {
  require(declaredNames.distinct == declaredNames)

  def declaredNames: List[String] = constants.map(_._1) ++ variables.map(_._1) ++ unitaryOperators.map(_._1) ++ binaryPrefixOperators.map(_._1)

  def withUnitaryOperators[UO2](ops: List[(String, UO2)]): Language[UO2, BO, S, V] =
    copy(unitaryOperators = ops)

  def withBinaryOperators[BO2](prefix: List[(String, BO2)], infix: List[(String, BO2)]): Language[UO, BO2, S, V] =
    copy(binaryPrefixOperators = prefix, binaryInfixOperators = infix)

  def addConstant[S2](name: String, constant: S2): Language[UO, BO, S | S2, V] =
    copy(constants = (name -> constant) :: constants)

  def withConstants[S2](constants: List[(String, S2)]): Language[UO, BO, S2, V] =
    copy(constants = constants)

  def withVariables[V2](variables: List[(String, V2)]): Language[UO, BO, S, V2] =
    copy(variables = variables)

  def withUnitaryOperator[UO2 >: UO](name: String, op: UO2): Language[UO2, BO, S, V] =
    copy(unitaryOperators = (name -> op) :: unitaryOperators)

  def withBinaryInfixOperator[BO2 >: BO](name: String, op: BO2): Language[UO, BO2, S, V] =
    copy(binaryInfixOperators = (name -> op) :: binaryInfixOperators)

  def withBinaryPrefixOperator[BO2 >: BO](name: String, op: BO2): Language[UO, BO2, S, V] =
    copy(binaryPrefixOperators = (name -> op) :: binaryPrefixOperators)

  // todo: remove
  def variable(variable: V): VariableNode[UO, BO, S, V] = VariableNode(variable)
  def constantNode(value: S): ConstantNode[UO, BO, S, V] = ConstantNode(value)

  def parse(term: String)(using literalParser: LiteralParser[S]): Option[AbstractSyntaxTree[UO, BO, S, V]] =
    Parser.parse(this, literalParser)(term)

  def evaluate(node: AbstractSyntaxTree[UO, BO, S, V])(variableAssignment: V => S)(using evaluate: Evaluate[UO, BO, S, V]): S =
    evaluate.evaluate(node)(variableAssignment)

  def derive(node: AbstractSyntaxTree[UO, BO, S, V])(variable: V)(using derive: Derive[UO, BO, S, V]): AbstractSyntaxTree[UO, BO, S, V] =
    derive.derive(node)(variable)

  def optimize(node: AbstractSyntaxTree[UO, BO, S, V])(using optimizer: Optimizer[UO, BO, S, V]): AbstractSyntaxTree[UO, BO, S, V] =
    optimizer.optimize(node)

  def compile[F](node: AbstractSyntaxTree[UO, BO, S, V])(using compiler: Compiler[UO, BO, S, V, F]): Try[F] =
    compiler.compile(node)
}

object Language {
  val emptyLanguage = Language[Nothing, Nothing, Nothing, Nothing](Nil, Nil, Nil, Nil, Nil)
}
