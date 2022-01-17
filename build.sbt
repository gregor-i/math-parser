import sbtcrossproject.CrossPlugin.autoImport.{CrossType, crossProject}
import scala.sys.process._

ThisBuild / version := {
  Option("git tag -l --points-at HEAD".!!.trim)
    .filter(_.nonEmpty)
    .getOrElse("SNAPSHOT")
}
ThisBuild / organization := "com.github.gregor-i"
ThisBuild / scalaVersion := "3.1.0"

val `math-parser` =
  crossProject(JSPlatform, JVMPlatform)
    .crossType(CrossType.Pure)
    .settings(
      libraryDependencies += "org.typelevel" %%% "cats-parse" % "0.3.6"
    )
    .settings(testSettings)

val `math-parser-spire` =
  project
    .dependsOn(`math-parser`.jvm % "compile->compile;test->test")
    .settings(libraryDependencies += "org.typelevel" %%% "spire" % "0.18.0-M2")
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
  libraryDependencies += "org.scalatest" %%% "scalatest" % "3.2.10" % Test,
  Test / testOptions += Tests.Argument(TestFrameworks.ScalaTest, "-oD")
)
