package mathParser.algebra

import mathParser.{Derive, Evaluate, LiteralParser, Optimizer}
import spire.algebra.{Field, NRoot, Trig}

trait SpireImplicits extends spire.std.DoubleInstances {
  given spireLiteralParser[A: Field]: LiteralParser[A] =
    SpireLanguage.spireLiteralParser

  given spireEvaluate[A: Field: NRoot: Trig, V]: Evaluate[SpireUnitaryOperator, SpireBinaryOperator, A, V] =
    SpireLanguage.spireEvaluate

  given spireOptimizer[A: Field: NRoot: Trig, V]: Optimizer[SpireUnitaryOperator, SpireBinaryOperator, A, V] =
    SpireLanguage.spireOptimizer

  given spireDerive[A: Field: Trig: NRoot, V]: Derive[SpireUnitaryOperator, SpireBinaryOperator, A, V] =
    SpireLanguage.spireDerive
}
