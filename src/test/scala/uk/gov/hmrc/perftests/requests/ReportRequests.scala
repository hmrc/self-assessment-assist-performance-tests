/*
 * Copyright 2023 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.perftests.requests

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import uk.gov.hmrc.performance.conf.ServicesConfiguration
import uk.gov.hmrc.perftests.AffinityGroup
import uk.gov.hmrc.perftests.Common.{calculationId, correlationId, validNino, validReportId}

object ReportRequests extends ServicesConfiguration {

  private val selfAssessmentBaseUrl: String = baseUrlFor("self-assessment-assist")
  private val generateReportUrl: String = s"$selfAssessmentBaseUrl/reports/$validNino/2021-22/$calculationId"
  private val acknowledgeReportUrl: String = s"$selfAssessmentBaseUrl/reports/acknowledge/$validNino/$validReportId/$correlationId"

  private def bearerToken(bearerTokenFor: AffinityGroup.Value): String = bearerTokenFor match {
    case uk.gov.hmrc.perftests.AffinityGroup.Individual => s"$${bearerToken}"
    case uk.gov.hmrc.perftests.AffinityGroup.Agent => s"$${agentBearerToken}"
  }

  def generateReport(apiVersion: String = "1.0", affinityGroup: AffinityGroup.Value): HttpRequestBuilder =
    http("Generate report")
      .post(generateReportUrl)
      .headers(Map("Content-Type" -> "application/json"))
      .headers(Map("Authorization" -> bearerToken(affinityGroup)))
      .headers(Map("Accept" -> s"application/vnd.hmrc.$apiVersion+json"))
      .check(status is 200)
      .check(substring("reportId"))


  def acknowledgeReport(apiVersion: String = "1.0", affinityGroup: AffinityGroup.Value): HttpRequestBuilder =
    http("Acknowledge report")
      .post(acknowledgeReportUrl)
      .headers(Map("Content-Type" -> "application/json"))
      .headers(Map("Authorization" -> bearerToken(affinityGroup)))
      .headers(Map("Accept" -> s"application/vnd.hmrc.$apiVersion+json"))
      .check(status is 204)

}
