import sbt._

object Dependencies {

  private val gatlingVersion = "3.4.2"

  val test = Seq(
    "com.typesafe"          % "config"                    % "1.3.1"        % Test,
    "uk.gov.hmrc"          %% "performance-test-runner"   % "5.4.0"        % Test,
    "io.gatling"            % "gatling-test-framework"    % gatlingVersion % Test,
    "io.gatling.highcharts" % "gatling-charts-highcharts" % gatlingVersion % Test,
    "org.apache.commons"    % "commons-lang3"             % "3.12.0"       % Test
  )
}
