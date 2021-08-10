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

  def addConstant[S2](name: String, constant: S2): Language[O, S | S2, V] =
    copy(constants = (name -> constant) :: constants)

  def addVariable[V2](name: String, variable: V2): Language[O, S, V | V2] =
    copy(variables = (name -> variable) :: variables)

  def addUnitaryOperator[O2](name: String, op: O2 & UnitaryOperator): Language[O | O2, S, V] =
    copy(unitaryOperators = (name -> op) :: unitaryOperators)

  def addBinaryInfixOperator[O2](name: String, op: O2 & BinaryOperator): Language[O | O2, S, V] =
    copy(binaryInfixOperators = (name -> op) :: binaryInfixOperators)

  def addBinaryPrefixOperator[O2](name: String, op: O2 & BinaryOperator): Language[O | O2, S, V] =
    copy(binaryPrefixOperators = (name -> op) :: binaryPrefixOperators)

  def withUnitaryOperators[O2](ops: List[(String, O2 & UnitaryOperator)]): Language[(O & BinaryOperator ) | O2, S, V] =
    copy(unitaryOperators = ops)

  def withBinaryOperators[O2](prefix: List[(String, O2 & BinaryOperator)], infix: List[(String, O2 & BinaryOperator)]): Language[(O & UnitaryOperator) | O2, S, V] =
    copy(binaryPrefixOperators = prefix, binaryInfixOperators = infix)

  def withConstants[S2](constants: List[(String, S2)]): Language[O, S2, V] =
    copy(constants = constants)

  def withVariables[V2](variables: List[(String, V2)]): Language[O, S, V2] =
    copy(variables = variables)

  def mapScalar[S2](f: S => S2): Language[O, S2, V] =
    copy(constants = constants.map((name, s) => (name, f(s))))

  def parse(term: String)(using literalParser: LiteralParser[S]): Option[AbstractSyntaxTree[O, S, V]] =
    Parser.parse(this, literalParser)(term)
}

object Language {
  val emptyLanguage = Language[Nothing, Nothing, Nothing](Nil, Nil, Nil, Nil, Nil)
}
