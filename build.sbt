import sbtcrossproject.CrossPlugin.autoImport.{CrossType, crossProject}
import scala.sys.process._

ThisBuild / version := {
  Option("git tag -l --points-at HEAD".!!.trim)
    .filter(_.nonEmpty)
    .getOrElse("SNAPSHOT")
}
ThisBuild / organization := "com.github.gregor-i"
ThisBuild / scalaVersion := "2.13.5"

val `math-parser` =
  crossProject(JSPlatform, JVMPlatform)
    .crossType(CrossType.Pure)
    .settings(testSettings)

val `math-parser-spire` =
  project
    .dependsOn(`math-parser`.jvm)
    .settings(
      libraryDependencies += "org.typelevel" %%% "spire" % "0.17.0"
    )
    .settings(testSettings)

val `math-parser-compile-jvm` = project
  .dependsOn(`math-parser-spire`  % "compile -> compile; test -> test")
  .settings(libraryDependencies += "org.scala-lang" % "scala-compiler" % scalaVersion.value)

val `examples` = project
  .dependsOn(`math-parser-spire`, `math-parser-compile-jvm`)
  .settings(skip in publish := true)
  .settings(packagedArtifacts := Map.empty)
  .settings(
    libraryDependencies += "de.sciss" %% "scala-chart" % "0.7.1",
    libraryDependencies += "com.github.scopt" %% "scopt" % "3.7.1",
  )


def testSettings = Seq(
    libraryDependencies += "org.scalatest" %%% "scalatest" % "3.2.8" % Test,
    Test / testOptions += Tests.Argument(TestFrameworks.ScalaTest, "-oD")
)
