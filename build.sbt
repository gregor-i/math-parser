scalaVersion in ThisBuild := "2.12.3"
scalacOptions in ThisBuild ++= Seq("-feature", "-deprecation")

val mathParser = project.in(file("."))
  .settings(
    name := "mathParser",
    version := "0.0.1",
    FolderStructure.folderStructre,
    Dependancies.spire,
    Dependancies.specs2AndScalaCheck,
    Dependancies.scalaCompiler)

val examples = project.in(file("examples"))
  .dependsOn(mathParser)
  .settings(
    name := "mathParser-examples",
    version := "0.0.1",
    FolderStructure.folderStructre)
