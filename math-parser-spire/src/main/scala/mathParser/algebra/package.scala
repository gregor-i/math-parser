package mathParser

package object algebra {
  type SpireLanguage[A, V] = Language[SpireUnitaryOperator, SpireBinaryOperator, A, V]
  type SpireNode[A, V]     = AbstractSyntaxTree[SpireUnitaryOperator, SpireBinaryOperator, A, V]
}
