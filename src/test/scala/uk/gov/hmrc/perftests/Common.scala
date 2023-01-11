/*
 * Copyright 2023 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.perftests

import uk.gov.hmrc.performance.conf.ServicesConfiguration

object Common extends ServicesConfiguration {

  val baseUrlAuthLoginApi: String = baseUrlFor("auth-login-api")
  val baseUrlSaSelfAssessment: String = baseUrlFor("self-assessment-assist")

  val Individual: String = "Individual"
  val Organisation: String = "Organisation"
  val Agent: String = "Agent"

  val commonHeaders: Map[String, String] =
    Map("Accept" -> s"application/vnd.hmrc.1.0+json",
      "Authorization" -> s"$${bearerToken}",
      "Content-Type" -> "application/json")

  val headersInsolvent: Map[String, String] =
    Map("Accept" -> s"application/vnd.hmrc.1.0+json",
      "Content-Type" -> "application/json")

}