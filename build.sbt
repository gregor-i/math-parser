import sbtcrossproject.CrossPlugin.autoImport.{CrossType, crossProject}

version in ThisBuild := "1.4"
organization in ThisBuild := "com.github.gregor-i"
scalaVersion in ThisBuild := "2.12.8"

val `math-parser` =
  crossProject(JSPlatform, JVMPlatform)
    .crossType(CrossType.Pure)
    .settings(BintrayRelease.settings)
    .settings(
      libraryDependencies ++= Seq(
        "org.typelevel" %%% "spire" % "0.16.0",
        "org.scalatest" %%% "scalatest" % "3.0.7" % Test,
        "org.scalacheck" %%% "scalacheck" % "1.14.0" % Test,
      )
    )

val `math-parser-compile-jvm` = project
  .dependsOn(`math-parser`.jvm % "compile -> compile; test -> test")
  .settings(libraryDependencies += "org.scala-lang" % "scala-compiler" % scalaVersion.value)
  .settings(BintrayRelease.settings)

val `examples` = project
  .dependsOn(`math-parser`.jvm, `math-parser-compile-jvm`)
  .settings(
    libraryDependencies += "com.github.wookietreiber" %% "scala-chart" % "0.5.1",
    libraryDependencies += "com.github.scopt" %% "scopt" % "3.7.0",
  )
