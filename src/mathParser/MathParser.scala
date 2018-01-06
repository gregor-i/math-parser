package mathParser

import mathParser.boolean.BooleanLanguage
import mathParser.complex.ComplexLanguage
import mathParser.double.DoubleLanguage

object MathParser {
  def doubleLanguage(variables: Symbol*) = new DoubleLanguage(freeVariables = variables)
  def complexLanguage(variables: Symbol*) = new ComplexLanguage(freeVariables = variables)
  def booleanLanguage(variables: Symbol*) = new BooleanLanguage(freeVariables = variables)
}
