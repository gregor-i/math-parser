import sbtcrossproject.CrossPlugin.autoImport.{CrossType, crossProject}
import scala.sys.process._

version in ThisBuild := {
  Option("git tag -l --points-at HEAD".!!.trim)
    .filter(_.nonEmpty)
    .getOrElse("SNAPSHOT")
}
organization in ThisBuild := "com.github.gregor-i"
scalaVersion in ThisBuild := "2.13.2"

val `math-parser` =
  crossProject(JSPlatform, JVMPlatform)
    .crossType(CrossType.Pure)
    .settings(BintrayRelease.settings)
    .settings(testSettings)

val `math-parser-spire` =
  project
    .dependsOn(`math-parser`.jvm)
    .settings(
      libraryDependencies += "org.typelevel" %%% "spire" % "0.17.0-M1"
    )
    .settings(testSettings)
    .settings(BintrayRelease.settings)

val `math-parser-compile-jvm` = project
  .dependsOn(`math-parser-spire`  % "compile -> compile; test -> test")
  .settings(libraryDependencies += "org.scala-lang" % "scala-compiler" % scalaVersion.value)
  .settings(BintrayRelease.settings)

val `examples` = project
  .dependsOn(`math-parser-spire`, `math-parser-compile-jvm`)
  .settings(skip in publish := true)
  .settings(packagedArtifacts := Map.empty)
  .settings(
    libraryDependencies += "de.sciss" %% "scala-chart" % "0.7.1",
    libraryDependencies += "com.github.scopt" %% "scopt" % "3.7.1",
  )


def testSettings = Seq(
    libraryDependencies += "org.scalatest" %%% "scalatest" % "3.1.2" % Test,
    testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-oD")
)
