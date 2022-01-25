import sbtcrossproject.CrossPlugin.autoImport.{CrossType, crossProject}
import scala.sys.process._

ThisBuild / version := {
  Option("git tag -l --points-at HEAD".!!.trim)
    .filter(_.nonEmpty)
    .getOrElse("SNAPSHOT")
}
ThisBuild / organization := "com.github.gregor-i"
ThisBuild / scalaVersion := "3.1.1"

val `math-parser` =
  crossProject(JSPlatform, JVMPlatform)
    .crossType(CrossType.Pure)
    .settings(testSettings)

val `math-parser-spire` =
  project
    .dependsOn(`math-parser`.jvm % "compile->compile;test->test")
    .settings(libraryDependencies += "org.typelevel" % "spire_2.13" % "0.17.0")
    .settings(testSettings)

val `examples` = project
  .dependsOn(`math-parser-spire`)
  .settings(publish / skip := true)
  .settings(packagedArtifacts := Map.empty)
  .settings(
    libraryDependencies += "de.sciss"         %% "scala-chart" % "0.8.0",
    libraryDependencies += "com.github.scopt" %% "scopt"       % "4.0.1"
  )

def testSettings = Seq(
  libraryDependencies += "org.scalatest" %%% "scalatest" % "3.2.11" % Test,
  Test / testOptions += Tests.Argument(TestFrameworks.ScalaTest, "-oD")
)
