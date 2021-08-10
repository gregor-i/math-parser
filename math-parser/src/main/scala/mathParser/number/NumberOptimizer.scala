package mathParser.number

import mathParser.AbstractSyntaxTree
import mathParser.AbstractSyntaxTree.*
import mathParser.number.*
import mathParser.number.NumberSyntax.*
import mathParser.OptimizationRule
import mathParser.number.NumberOperator.*

class NumberOptimizer[S : Number](using evaluate: mathParser.Evaluate[NumberOperator, S])
  extends mathParser.Optimizer[NumberOperator, S] {
 def rules[V]: List[OptimizationRule[NumberOperator, S, V]] =
   List(mathParser.Optimize.replaceConstantsRule(using evaluate), identities[V])

 private def identities[V]: OptimizationRule[NumberOperator, S, V] = {
   val zero = ConstantNode[NumberOperator, S, V](Number.zero[S])
   val one = ConstantNode[NumberOperator, S, V](Number.one[S])

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
