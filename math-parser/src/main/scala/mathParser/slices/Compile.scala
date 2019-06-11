package mathParser.slices

trait Compile[Lang <: AbstractSyntaxTree with LanguageOperators with FreeVariables] {

  def compile1(language: Lang)(term: language.Node): Option[language.S => language.S]

  def compile2(language: Lang)(term: language.Node): Option[(language.S, language.S) => language.S]

  private[mathParser] def preconditions1(language: Lang): Unit =
    require(language.freeVariables.size == 1, "you can only call compile1 if you provided exactly 1 free variables in the language defintion")

  private[mathParser] def preconditions2(language: Lang): Unit =
    require(language.freeVariables.size == 2, "you can only call compile2 if you provided exactly 2 free variables in the language defintion")
}
