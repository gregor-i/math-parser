package mathParser.slices

trait Parser {
  _: AbstractSyntaxTree with LanguageOperators with FreeVariables =>

  def literalParser: LiteralParser[S]

  def parse(input: String): Option[Node] = {

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
      else Some(input.take(k), input.drop(k + 1))
    }

    def binaryNode(input: String, splitter: Char,
                   f: (Node, Node) => Node): Option[Node] =
      for {
        (sub1, sub2) <- splitByRegardingParenthesis(input.trim, splitter)
        p1 <- parse(sub1)
        p2 <- parse(sub2)
      } yield f(p1, p2)

    def constant(input: String): Option[Node] =
      constants().find(_.symbol.name == input).map(constant => ConstantNode(constant.value))

    def variable(input: String): Option[Node] = freeVariables.find(_.name == input).map(Variable.apply)

    def literal(input: String): Option[Node] = literalParser.tryToParse(input).map(literal => ConstantNode(literal))

    def parenthesis(input: String): Option[Node] =
      if (input.startsWith("(") && input.endsWith(")"))
        parse(input.tail.init)
      else
        None

    def binaryInfixOperation(input: String): Option[Node] = binaryInfixOperators
      .flatMap(op => binaryNode(input, op.symbol.name.head, BinaryNode(op, _, _)))
      .headOption

    def binaryPrefixOperation(input: String): Option[Node] = binaryOperators
      .find(op => input.matches(s"${op.symbol}\\(.*\\)"))
      .flatMap(op => binaryNode(input.substring(op.symbol.name.length + 1, input.length - 1), ',', BinaryNode(op, _, _)))

    def unitaryPrefixOperation(input: String): Option[Node] = unitaryOperators
      .find {
        case op if !op.symbol.name.exists(_.isLetterOrDigit) => input.startsWith(op.symbol.name)
        case op => input.startsWith(op.symbol.name + "(") || input.startsWith(op.symbol.name + " ")
      }
      .flatMap(op => parse(input.stripPrefix(op.symbol.name)).map(UnitaryNode(op, _)))
      .headOption

    val trimmedInput = input.trim()
    constant(trimmedInput) orElse
      variable(trimmedInput) orElse
      literal(trimmedInput) orElse
      parenthesis(trimmedInput) orElse
      binaryPrefixOperation(trimmedInput) orElse
      binaryInfixOperation(trimmedInput) orElse
      unitaryPrefixOperation(trimmedInput)
  }
}
