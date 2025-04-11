lazy val root = (project in file("."))
  .enablePlugins(GatlingPlugin)
  .settings(
      name         := "self-assessment-assist-performance-tests",
      version      := "0.1.0-SNAPSHOT",
      scalaVersion := "2.13.14",
      // -feature surfaces warning when advanced features are used without being enabled.
      // -language:implicitConversions", "-language:postfixOps are recommended by Gatling
      scalacOptions ++= Seq("-Xfatal-warnings", "-feature", "-language:implicitConversions", "-language:postfixOps"),
      libraryDependencies ++= Dependencies.test,
      // Enabling sbt-auto-build plugin provides DefaultBuildSettings with default `testOptions` from `sbt-settings` plugin.
      // These testOptions are not compatible with `sbt Gatling/test`. So we have to override testOptions here.
      Test / testOptions := Seq.empty
  )