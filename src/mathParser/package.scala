package object mathParser {
  type Variable = Symbol
  type VariableSet = Set[Variable]


  trait Named {
    val name: Symbol
  }

  trait Constant[Skalar] extends Named {
    val apply: Skalar
  }

  trait UnitaryOperator[Skalar] extends Named {
    val apply: Skalar => Skalar
  }

  trait BinaryOperator[Skalar] extends Named {
    val apply: (Skalar, Skalar) => Skalar
  }

  trait Literal[Skalar] {
    val apply: Skalar
  }
}
