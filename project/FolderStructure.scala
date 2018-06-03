import sbt._
import Keys._

object FolderStructure {
  def settings: Seq[Setting[_]] = Seq(
    scalaSource in Compile := baseDirectory.value / "src",
    scalaSource in Test := baseDirectory.value / "test"
  )
}
