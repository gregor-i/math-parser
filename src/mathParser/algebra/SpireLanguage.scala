package mathParser.algebra

import mathParser.slices._
import spire.algebra.{Field, NRoot, Trig}

import scala.util.Try

class SpireLanguage[A](val freeVariables: Seq[Symbol])
                      (implicit val field: Field[A], val trig: Trig[A], val nRoot: NRoot[A])
  extends SpireAlgebra[A]
    with SpireOperators[A]
    with AbstractSyntaxTree
    with SpireSyntaxSugar[A]
    with SpireDerive[A]
    with SpireOptimize[A]
    with FreeVariables
    with Parser
    with Evaluate {

  val literalParser: LiteralParser[A] = s => Try(s.toDouble).map(field.fromDouble).toOption
}

trait SpireAlgebra[A] {
  val field: Field[A]
  val trig: Trig[A]
  val nRoot: NRoot[A]
}