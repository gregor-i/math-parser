package mathParser.slices

import scala.util.Try

trait Compile {
  _: AbstractSyntaxTree with LanguageOperators with FreeVariables =>

  def compile1(term: Node): Option[S => S]

  def compile2(term: Node): Option[(S, S) => S]

  private[mathParser] def preconditions1(): Unit =
    require(freeVariables.size == 1, "you can only call compile1 if you provided exactly 1 free variables in the language defintion")

  private[mathParser] def preconditions2(): Unit =
    require(freeVariables.size == 2, "you can only call compile2 if you provided exactly 2 free variables in the language defintion")

  private[mathParser] def compileAndCast[A](scalaCode: String): Try[A] =
    Try {
      import reflect.runtime.currentMirror
      import tools.reflect.ToolBox
      currentMirror.mkToolBox().compile(currentMirror.mkToolBox().parse(scalaCode))().asInstanceOf[A]
    }
}
