import sbtcrossproject.CrossPlugin.autoImport.{CrossType, crossProject}
import scala.sys.process._

version in ThisBuild := {
  Option("git tag -l --points-at HEAD".!!.trim)
    .filter(_.nonEmpty)
    .getOrElse("SNAPSHOT")
}
organization in ThisBuild := "com.github.gregor-i"
scalaVersion in ThisBuild := "2.13.0"
crossScalaVersions in ThisBuild := Seq("2.12.8", "2.13.0")
crossScalaVersions := Nil

val `math-parser` =
  crossProject(JSPlatform, JVMPlatform)
    .crossType(CrossType.Pure)
    .settings(BintrayRelease.settings)
    .settings(
      libraryDependencies += "org.typelevel" %%% "spire" % "0.17.0-M1"
    )
    .settings(
      libraryDependencies ++= Seq(
        "org.scalatest" %%% "scalatest" % "3.0.8" % Test,
        "org.scalacheck" %%% "scalacheck" % "1.14.0" % Test,
      ),
      testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-oD")
    )

val `math-parser-compile-jvm` = project
  .dependsOn(`math-parser`.jvm % "compile -> compile; test -> test")
  .settings(libraryDependencies += "org.scala-lang" % "scala-compiler" % scalaVersion.value)
  .settings(BintrayRelease.settings)

val `examples` = project
  .dependsOn(`math-parser`.jvm, `math-parser-compile-jvm`)
  .settings(skip in publish := true)
  .settings(
    libraryDependencies += "de.sciss" %% "scala-chart" % "0.7.1",
    libraryDependencies += "com.github.scopt" %% "scopt" % "3.7.1",
  )
