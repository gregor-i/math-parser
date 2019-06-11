package mathParser.boolean

import mathParser.slices._

class BooleanLanguage(val freeVariables: Seq[Symbol])
  extends BooleanOperators
    with AbstractSyntaxTree
    with Evaluate
    with FreeVariables
    with Parser {
  val literalParser: LiteralParser[Boolean] = NoLiterals
}
