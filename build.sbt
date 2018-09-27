version in ThisBuild := "1.0"
organization in ThisBuild := "com.github.gregor-i"
scalaVersion in ThisBuild := "2.12.6"

val `math-parser` = project.in(file("."))
  .settings(
      name := "math-parser",
      FolderStructure.settings,
      BintrayRelease.settings,
      Dependencies.spire,
      Dependencies.specs2AndScalaCheck,
      Dependencies.scalaCompiler
  )

val `examples` = project.in(file("examples"))
  .dependsOn(`math-parser`)
  .settings(
    name := "math-parser-examples",
    FolderStructure.settings,
    Dependencies.scalaChart,
    Dependencies.scopt
  )
