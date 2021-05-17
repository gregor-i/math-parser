package mathParser

package object algebra {
  type SpireLanguage[A, V] = Language[SpireUnitaryOperator, SpireBinaryOperator, A, V]
  type SpireNode[A, V]     = Node[SpireUnitaryOperator, SpireBinaryOperator, A, V]
}
