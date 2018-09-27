import sbt._
import Keys._

object Dependencies {
  def spire = libraryDependencies += "org.spire-math" %% "spire" % "0.13.0"

  def scalaCompiler = libraryDependencies += "org.scala-lang" % "scala-compiler" % scalaVersion.value

  def specs2AndScalaCheck = Seq(
    "org.scalacheck" %% "scalacheck" % "1.14.0",
    "org.specs2" %% "specs2-core" % "4.3.4",
    "org.specs2" %% "specs2-scalacheck" % "4.3.4")
    .map(libraryDependencies += _ % Test)

  def scalaChart = libraryDependencies += "com.github.wookietreiber" %% "scala-chart" %  "0.5.1"

  def scopt = libraryDependencies += "com.github.scopt" %% "scopt" % "3.7.0"
}
