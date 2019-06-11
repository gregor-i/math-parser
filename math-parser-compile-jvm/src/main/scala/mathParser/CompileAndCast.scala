package mathParser

import scala.util.Try

object CompileAndCast{
  def apply[A](scalaCode: String): Try[A] =
    Try {
      import reflect.runtime.currentMirror
      import tools.reflect.ToolBox
      currentMirror.mkToolBox().compile(currentMirror.mkToolBox().parse(scalaCode))().asInstanceOf[A]
    }
}
