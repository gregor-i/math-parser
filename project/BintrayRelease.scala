import bintray.BintrayPlugin.autoImport.bintrayRepository
import sbt.Keys.{licenses, publishMavenStyle}
import sbt.url

object BintrayRelease {
  def settings = Seq(
    bintrayRepository := "maven",
    licenses += ("MIT", url("http://opensource.org/licenses/MIT"))
  )
}
