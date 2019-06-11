package mathParser

import spire.algebra.{Field, NRoot, Trig}
import mathParser.algebra.SpireLanguage
import mathParser.boolean.BooleanLanguage
import mathParser.complex.ComplexLanguage
import mathParser.double.DoubleLanguage

object MathParser {
  def doubleLanguage(variables: Symbol*) = new DoubleLanguage(freeVariables = variables)
  def complexLanguage(variables: Symbol*) = new ComplexLanguage(freeVariables = variables)
  def booleanLanguage(variables: Symbol*) = new BooleanLanguage(freeVariables = variables)
  def realLanguage(variables: Symbol*) = new SpireLanguage[spire.math.Real](freeVariables = variables)

  def spireLang[A : Field : Trig: NRoot](variables: Symbol*) = new SpireLanguage[A](freeVariables = variables)
}