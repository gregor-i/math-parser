import sbt._
import Keys._

object Dependencies {
  def spire = libraryDependencies += "org.typelevel" %% "spire" % "0.16.0"

  def scalaCompiler = libraryDependencies += "org.scala-lang" % "scala-compiler" % scalaVersion.value

  def scalaTestAndScalaCheck = Seq(
    "org.scalatest" %% "scalatest" % "3.0.5",
    "org.scalacheck" %% "scalacheck" % "1.14.0"
  ).map(libraryDependencies += _ % Test)

  def scalaChart = libraryDependencies += "com.github.wookietreiber" %% "scala-chart" % "0.5.1"

  def scopt = libraryDependencies += "com.github.scopt" %% "scopt" % "3.7.0"
}
