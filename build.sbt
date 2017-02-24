import de.heikoseeberger.sbtheader.license.GPLv3
import sbt.Keys._

// settings and libs
def commonSettings = Seq(
  version := "0.0.1",
  scalaVersion := "2.12.0",
  scalaSource in Compile := baseDirectory.value / "src",
  scalaSource in Test := baseDirectory.value / "test",
  headers := Map("scala" -> GPLv3("2017", "Gregor Ihmor")),
  scalacOptions in ThisBuild ++= Seq("-feature", "-deprecation")
) ++ specs2AndScalaCheck ++ spire

def spire = Seq(
  libraryDependencies += "org.spire-math" %% "spire" % "0.13.0"
)

def javaRuntimeCompiler =
  libraryDependencies += "net.openhft" % "compiler" % "2.3.0"

def scalaCompiler =
  libraryDependencies += "org.scala-lang" % "scala-compiler" % scalaVersion.value

def specs2AndScalaCheck = Seq(
  "org.scalacheck" %% "scalacheck" % "1.13.4",
  "org.specs2" %% "specs2-core" % "3.8.6",
  "org.specs2" %% "specs2-scalacheck" % "3.8.6")
  .map(libraryDependencies += _ % "test")

// projects
name := "mathParser"
commonSettings
spire
specs2AndScalaCheck
javaRuntimeCompiler
scalaCompiler