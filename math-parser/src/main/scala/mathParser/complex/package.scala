package mathParser

package object complex {
  type ComplexLanguage[V] = Language[ComplexUnitaryOperator, ComplexBinaryOperator, Complex, V]
  type ComplexNode[V]     = Node[ComplexUnitaryOperator, ComplexBinaryOperator, Complex, V]
}
