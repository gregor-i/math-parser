package mathParser

import mathParser.AbstractSyntaxTree.Node

import scala.util.Try

trait Compile[S, Lang <: Language[S]] {
  def apply(v1: Variable)
           (term: Node[S, Lang]): Option[S => S]

  def apply(v1: Variable, v2: Variable)
           (term: Node[S, Lang]): Option[(S, S) => S]


  private[mathParser] def compileAndCast[A](scalaCode:String):Try[A] =
    Try{
      import reflect.runtime.currentMirror
      import tools.reflect.ToolBox
      currentMirror.mkToolBox().compile(currentMirror.mkToolBox().parse(scalaCode))().asInstanceOf[A]
    }
}
