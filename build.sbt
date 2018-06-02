version in ThisBuild := "1.0"
organization in ThisBuild := "com.github.gregor-i"

scalaVersion in ThisBuild := "2.12.6"
scalacOptions in ThisBuild ++= Seq("-feature", "-deprecation")

val `math-parser` = project.in(file("."))
  .settings(
    name := "math-parser",
    FolderStructure.folderStructre,
    Dependancies.spire,
    Dependancies.specs2AndScalaCheck,
    Dependancies.scalaCompiler,
    BintrayRelease.settings
  )

val `examples` = project.in(file("examples"))
  .dependsOn(`math-parser`)
  .settings(
    name := "mathParser-examples",
    FolderStructure.folderStructre,
    Dependancies.scalaChart,
    Dependancies.scopt)
