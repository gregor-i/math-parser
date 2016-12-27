package operators

trait Operators[Skalar] {
  class UnitaryOperator(val name: String, val apply: Skalar => Skalar)
  class BinaryOperator (val name: String, val apply: (Skalar, Skalar) => Skalar)

  type Term = PublicTerm[Skalar]

  case class Variable(name:String) extends Term{
    def apply(variableValues: PartialFunction[String, Skalar]): Skalar = variableValues(name)
    def isVariable(variableName:String): Boolean = name == variableName
  }

  case class Literal(value: Skalar) extends Term{
    def apply(variableValues: PartialFunction[String, Skalar]): Skalar = value
    def isVariable(variableName:String): Boolean = false
  }

  case class Constant(name: String, value: Skalar) extends Term{
    def apply(variableValues: PartialFunction[String, Skalar]): Skalar = value
    def isVariable(variableName:String): Boolean = false
  }

  case class UnitaryNode(name:String, op: UnitaryOperator, para1: Term) extends Term {
    def apply(variableValues: PartialFunction[String, Skalar]): Skalar = op.apply(para1.apply(variableValues))
    def isVariable(variableName:String): Boolean = para1.isVariable(variableName)
    override def toString: String = "%s(%s)".format(name, para1)
  }

  case class BinaryNode(name:String, op: BinaryOperator, para1: Term, para2: Term) extends Term {
    def apply(variableValues: PartialFunction[String, Skalar]): Skalar = op.apply(para1.apply(variableValues), para2.apply(variableValues))
    def isVariable(variableName:String): Boolean = para1.isVariable(variableName) || para2.isVariable(variableName)
    override def toString: String = "%s(%s, %s)".format(name, para1, para2)
  }

  def unitaryOperators: Seq[UnitaryOperator]
  def binaryOperators: Seq[BinaryOperator]
  def binaryInfixOperators: Seq[BinaryOperator]
  def constants(): Seq[Constant]
  def parseLiteral(input: String): Option[Literal]
}