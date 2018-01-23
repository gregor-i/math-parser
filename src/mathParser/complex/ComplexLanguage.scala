
package mathParser.complex

import mathParser._
import mathParser.slices._

class ComplexLanguage(val freeVariables: Seq[Symbol],
                      val literalParser: LiteralParser[C] = ComplexLiteralParser)
  extends ComplexOperators
    with AbstractSyntaxTree
    with FreeVariables
    with Parser
    with ComplexSyntaxSugar
    with ComplexCompile
    with Evaluate
    with ComplexDerive
