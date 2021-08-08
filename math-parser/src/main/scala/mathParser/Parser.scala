package mathParser

import scala.annotation.tailrec
import atto.*
import Atto.*
import atto.ParseResult.Done
import cats.implicits.*

object Parser {
  def parse[O, S, V](lang: Language[O, S, V], literalParser: LiteralParser[S])(input: String): Option[AbstractSyntaxTree[O, S, V]] = {

    val constants : atto.Parser[AbstractSyntaxTree[O, S, V]] =
      Atto.choice {
        lang.constants
          .map((name, value) => Atto.string(name).map(_ => ConstantNode[O, S, V](value)))
      }.named(s"constants(${lang.constants.map(_._1).mkString(", ")})")

    val variables: atto.Parser[AbstractSyntaxTree[O, S, V]] =
      Atto.choice {
        lang.variables
          .map((name, value) => Atto.string(name).map(_ => VariableNode[O, S, V](value)))
      }.named(s"variables(${lang.variables.map(_._1).mkString(", ")})")

    def unitaryPrefixOperation: atto.Parser[AbstractSyntaxTree[O, S, V]] =
      Atto.choice{
        lang.unitaryOperators.map{
          case (name, op) if name.length == 1 && !name.head.isLetterOrDigit =>
            (Atto.string(name).token ~> expr())
              .map(child => UnitaryNode(op, child))
          case (name, op) =>
            Atto.string(name).token ~> Atto.parens(expr())
              .map(child => UnitaryNode(op, child))
        }
      }.named(s"unitaryPrefixOperation(${lang.unitaryOperators.map(_._1).mkString(", ")})")

    def binaryInfixOperation(ops: List[(String, O & BinaryOperator)]):
      atto.Parser[AbstractSyntaxTree[O, S, V]] =
      ops match {
        case (name, op) :: tail =>
          (for {
            leftChild <- expr(tail)
            _ <- Atto.string(name).token
            rightChild <- expr(ops)
          } yield BinaryNode(op, leftChild, rightChild)) | binaryInfixOperation(tail)
        case Nil => Atto.err("no match")
      }

    def expr(binaryInfixOperators: List[(String, O & BinaryOperator)] = lang.binaryInfixOperators.reverse): atto.Parser[AbstractSyntaxTree[O, S, V]] =
      Atto.delay {
        Atto.choice(
          constants,
          literalParser.map(ConstantNode[O, S, V].apply),
          binaryInfixOperation(binaryInfixOperators),
          unitaryPrefixOperation,
          variables,
          Atto.parens(expr())
        ).token
      }.named("expr")

    val parsed = Atto.phrase(expr(lang.binaryInfixOperators)).parse(input).done
    parsed.option
  }
}
