import bintray.BintrayPlugin.autoImport.bintrayRepository
import sbt.Keys.{licenses, publishMavenStyle}
import sbt.url

object BintrayRelease {
  def settings = Seq(
    publishMavenStyle := false,
    bintrayRepository := "releases",
    licenses += ("MIT", url("http://opensource.org/licenses/MIT"))
  )
}
