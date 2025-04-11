import sbt._

object Dependencies {

  val test: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"          %% "performance-test-runner"   % "6.1.0",
    "org.apache.commons"    % "commons-lang3"             % "3.17.0",
    "com.typesafe"          % "config"                    % "1.4.3"
  ).map(_ % Test)

}
