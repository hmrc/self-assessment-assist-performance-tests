import sbt._

object Dependencies {

  val test: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"          %% "performance-test-runner"   % "6.2.0",
    "org.apache.commons"    % "commons-lang3"             % "3.19.0",
    "com.typesafe"          % "config"                    % "1.4.5"
  ).map(_ % Test)

}
