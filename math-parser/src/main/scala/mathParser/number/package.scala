package mathParser

package object number {
  type ComplexLanguage[V] = Language[NumberUnitaryOperator, NumberBinaryOperator, Complex, V]
  type ComplexNode[V]     = AbstractSyntaxTree[NumberUnitaryOperator, NumberBinaryOperator, Complex, V]
}
