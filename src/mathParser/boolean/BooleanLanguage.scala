package mathParser.boolean

import mathParser._
import mathParser.slices._

class BooleanLanguage(val freeVariables: Seq[Symbol])
  extends BooleanOperators
    with AbstractSyntaxTree
    with Evaluate
    with ReplaceConstants
    with FreeVariables
    with Parser {
  val literalParser: LiteralParser[Skalar] = new NoLiterals
}