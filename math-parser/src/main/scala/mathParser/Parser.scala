package mathParser

object Parser {

  def parse[UO, BO, S, V](lang: Language[UO, BO, S, V], literalParser: LiteralParser[S])(input: String): Option[Node[UO, BO, S, V]] = {

    def splitByRegardingParenthesis(input: String, splitter: Char): Option[(String, String)] = {
      var k = -1
      var c = 0
      input.zipWithIndex.foreach {
        case (')', _) => c -= 1
        case ('(', _) => c += 1
        case (`splitter`, i) if c == 0 => k = i
        case _ =>
      }
      if (c != 0 || k == -1) None
      else Some((input.take(k), input.drop(k + 1)))
    }

    def binaryNode(input: String, splitter: Char,
                   f: (Node[UO, BO, S, V], Node[UO, BO, S, V]) => Node[UO, BO, S, V]): Option[Node[UO, BO, S, V]] =
      for {
        (sub1, sub2) <- splitByRegardingParenthesis(input.trim, splitter)
        p1 <- loop(sub1)
        p2 <- loop(sub2)
      } yield f(p1, p2)

    def constant(input: String): Option[Node[UO, BO, S, V]] =
      lang.constants.find(_._1 == input).map(constant => ConstantNode[UO, BO, S, V](constant._2))

    def variable(input: String): Option[Node[UO, BO, S, V]] = lang.variables.find(_._1 == input).map(v => VariableNode(v._2))

    def literal(input: String): Option[Node[UO, BO, S, V]] = literalParser.tryToParse(input).map(literal => ConstantNode[UO, BO, S, V](literal))

    def parenthesis(input: String): Option[Node[UO, BO, S, V]] =
      if (input.startsWith("(") && input.endsWith(")"))
        loop(input.tail.init)
      else
        None

    def binaryInfixOperation(input: String): Option[Node[UO, BO, S, V]] = lang.binaryInfixOperators
      .flatMap(op => binaryNode(input, op._1.head, BinaryNode[UO, BO, S, V](op._2, _, _)))
      .headOption

    def binaryPrefixOperation(input: String): Option[Node[UO, BO, S, V]] = lang.binaryPrefixOperators
      .find(op => input.matches(s"${op._1}\\(.*\\)"))
      .flatMap(op => binaryNode(input.substring(op._1.length + 1, input.length - 1), ',', BinaryNode[UO, BO, S, V](op._2, _, _)))

    def unitaryPrefixOperation(input: String): Option[Node[UO, BO, S, V]] = lang.unitaryOperators
      .find {
        case op if !op._1.exists(_.isLetterOrDigit) => input.startsWith(op._1)
        case op => input.startsWith(op._1 + "(") || input.startsWith(op._1 + " ")
      }
      .flatMap(op => loop(input.stripPrefix(op._1)).map(UnitaryNode[UO, BO, S, V](op._2, _)))

    def loop(input: String) = {
      val trimmedInput = input.trim()
      constant(trimmedInput) orElse
        variable(trimmedInput) orElse
        literal(trimmedInput) orElse
        parenthesis(trimmedInput) orElse
        binaryPrefixOperation(trimmedInput) orElse
        binaryInfixOperation(trimmedInput) orElse
        unitaryPrefixOperation(trimmedInput)
    }

    loop(input)
  }
}
