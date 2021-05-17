package mathParser.algebra

import mathParser.{Derive, Evaluate, LiteralParser, Optimizer}
import spire.algebra.{Field, NRoot, Trig}

trait SpireImplicits extends spire.std.DoubleInstances {
  implicit final def spireLiteralParser[A: Field]: LiteralParser[A] =
    SpireLanguage.spireLiteralParser

  implicit final def spireEvaluate[A: Field: NRoot: Trig, V]: Evaluate[SpireUnitaryOperator, SpireBinaryOperator, A, V] =
    SpireLanguage.spireEvaluate

  implicit final def spireOptimizer[A: Field: NRoot: Trig, V]: Optimizer[SpireUnitaryOperator, SpireBinaryOperator, A, V] =
    SpireLanguage.spireOptimizer

  implicit final def spireDerive[A: Field: Trig: NRoot, V]: Derive[SpireUnitaryOperator, SpireBinaryOperator, A, V] =
    SpireLanguage.spireDerive
}
