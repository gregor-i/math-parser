import sbt._
import Keys._

object Dependancies {
  def spire = libraryDependencies += "org.spire-math" %% "spire" % "0.13.0"

  def scalaCompiler = libraryDependencies += "org.scala-lang" % "scala-compiler" % scalaVersion.value

  def specs2AndScalaCheck = Seq(
    "org.scalacheck" %% "scalacheck" % "1.13.4",
    "org.specs2" %% "specs2-core" % "3.8.6",
    "org.specs2" %% "specs2-scalacheck" % "3.8.6")
    .map(libraryDependencies += _ % Test)
}