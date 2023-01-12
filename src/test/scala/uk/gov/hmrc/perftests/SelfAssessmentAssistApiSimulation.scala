/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.perftests

import io.gatling.core.Predef.{Simulation, atOnceUsers, constantUsersPerSec, rampUsersPerSec}
import io.gatling.core.Predef._
import io.gatling.core.assertion.AssertionSupport
import io.gatling.core.structure.{PopulationBuilder, ScenarioBuilder}
import uk.gov.hmrc.performance.conf.PerftestConfiguration

import scala.concurrent.duration._

class vatApiSimulation extends Simulation with PerftestConfiguration with AssertionSupport {

  private def withInjectedLoad(scenarioDefinitions: Seq[ScenarioDefinition]): Seq[PopulationBuilder] = scenarioDefinitions.map(scenarioDefinition => {
    val load = loadPercentage * scenarioDefinition.load
    println(s"Running scenario: ${scenarioDefinition.builder.name} at ${loadPercentage*100}% of default ${scenarioDefinition.load} load = $load")

    val injectionSteps = List(
      rampUsersPerSec(noLoad).to(load).during(rampUpTime),
      constantUsersPerSec(load).during(constantRateTime),
      rampUsersPerSec(load).to(noLoad).during(rampDownTime)

    )
    scenarioDefinition.builder.inject(injectionSteps)
  })

  val scenarioDefinitions: Seq[ScenarioDefinition] = Option(System.getProperty("journeyType")) match {

    case Some("SaAssessmentAssistApi") => Seq(
     // selfAssessmentAssist.Scenarios.selfAssessmentAssistApiJourney(runSingleUserJourney)
    )
  }

  println(s"Setting up simulation")

  if (runSingleUserJourney) {
    println(s"'perftest.runSmokeTest' is set to true, ignoring all loads and running with only one user per journey!")
    val injectedBuilders = scenarioDefinitions.map(scenarioDefinition => {
      scenarioDefinition.builder.inject(atOnceUsers(1))
    })

    setUp(injectedBuilders: _*)
      .assertions(global.failedRequests.count.is(0))
  }
  else {
    setUp(withInjectedLoad(scenarioDefinitions): _*)
      .assertions(global.failedRequests.percent.lt(1)).maxDuration(29 minutes)
  }
}

case class ScenarioConfig(fileName: String, load: Double)

case class ScenarioDefinition(builder: ScenarioBuilder, load: Double) {
  def this(scenarioBuilder: ScenarioBuilder) {
    this(scenarioBuilder, 1.0)
  }
}