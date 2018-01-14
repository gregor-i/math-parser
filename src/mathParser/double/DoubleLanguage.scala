package mathParser.double

import mathParser.slices._

class DoubleLanguage(val freeVariables: Seq[Symbol],
                     val literalParser: LiteralParser[Double] = DoubleLiteralParser)
  extends DoubleOperators
    with AbstractSyntaxTree
    with DoubleSyntaxSugar
    with FreeVariables
    with Parser
    with DoubleDerive
    with DoubleCompile
    with Evaluate
