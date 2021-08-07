package mathParser

package object number {
  type ComplexLanguage[V] = Language[NumberOperator, Complex, V]
  type ComplexNode[V]     = AbstractSyntaxTree[NumberOperator, Complex, V]
}
