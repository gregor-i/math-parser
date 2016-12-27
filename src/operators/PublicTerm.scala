package operators

/**
  * Created by GREGOR on 30.07.2016.
  */
trait PublicTerm[Skalar] {
  def apply(variableValues: PartialFunction[String, Skalar]): Skalar
  def isVariable(variableName:String): Boolean
}
