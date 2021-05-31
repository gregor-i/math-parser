package mathParser.number

import mathParser.AbstractSyntaxTree
import mathParser.AbstractSyntaxTree.*
import mathParser.number.*
import mathParser.number.NumberSyntax.*
import mathParser.OptimizationRule
import mathParser.number.NumberOperator.*

class NumberOptimizer[S : Number, V](using evaluate: mathParser.Evaluate[NumberUnitaryOperator, NumberBinaryOperator, S, V])
  extends mathParser.Optimizer[NumberUnitaryOperator, NumberBinaryOperator, S, V] {
 def rules: List[OptimizationRule[NumberUnitaryOperator, NumberBinaryOperator, S, V]] =
   List(mathParser.Optimize.replaceConstantsRule(using evaluate), identities)

 private def identities: OptimizationRule[NumberUnitaryOperator, NumberBinaryOperator, S, V] = {
   val zero = ConstantNode[NumberUnitaryOperator, NumberBinaryOperator, S, V](Number.zero[S])
   val one = ConstantNode[NumberUnitaryOperator, NumberBinaryOperator, S, V](Number.one[S])

   {
     case UnitaryNode(Neg, UnitaryNode(Neg, child))               => child
     case BinaryNode(Plus, left, `zero`)   => left
     case BinaryNode(Plus, `zero`, right)  => right
     case BinaryNode(Times, `zero`, _)     => zero
     case BinaryNode(Times, _, `zero`)     => zero
     case BinaryNode(Times, left, `one`)  => left
     case BinaryNode(Times, `one`, right) => right
     case BinaryNode(Power, left, `one`)  => left
     case BinaryNode(Power, _, `zero`)     => one
     case BinaryNode(Power, `one`, _)     => one
     case BinaryNode(Power, `zero`, _)     => zero
     case UnitaryNode(Log, UnitaryNode(Exp, child))               => child
     case BinaryNode(Plus, left, UnitaryNode(Neg, child))         => left - child
     case BinaryNode(Minus, left, UnitaryNode(Neg, child))        => left + child
     case BinaryNode(Minus, left, right) if left == right         => zero
     case BinaryNode(Divided, left, right) if left == right       => one
   }
 }

}
