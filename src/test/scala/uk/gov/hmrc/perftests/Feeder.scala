/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.perftests

import uk.gov.hmrc.perftests.RandomDataGenerator._

object Feeder {

  def generateScenarioData: IndexedSeq[Map[String, String]] =
    for {
      i <- 1 to 30000
    } yield Map(
      "arn"       -> generateArn,
      "vrn"       -> generateVrn,
      "periodKey" -> generatePeriodKey
    )
}
