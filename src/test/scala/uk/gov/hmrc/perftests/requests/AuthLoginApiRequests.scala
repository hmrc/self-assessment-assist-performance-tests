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
import uk.gov.hmrc.perftests.Common.{validMtdItId, validNino}
import uk.gov.hmrc.perftests.RandomDataGenerator._

object AuthLoginApiRequests extends ServicesConfiguration {

  private val baseUrlAuthLoginApi: String = baseUrlFor("auth-login-api")
  private val authLoginApiUrl: String     = s"$baseUrlAuthLoginApi/government-gateway/session/login"

  val insertAuthRecordAgent: HttpRequestBuilder =
    http("Login Auth Agent")
      .post(authLoginApiUrl)
      .body(StringBody(agentAuthPayload))
      .headers(Map(HttpHeaderNames.ContentType -> HttpHeaderValues.ApplicationJson))
      .check(status is 201)
      .check(header(HttpHeaderNames.Authorization).saveAs("agentBearerToken"))

  val insertAuthRecordIndividual: HttpRequestBuilder =
    http("Login Auth Individual")
      .post(authLoginApiUrl)
      .body(StringBody(individualAuthPayload))
      .headers(Map(HttpHeaderNames.ContentType -> HttpHeaderValues.ApplicationJson))
      .check(status is 201)
      .check(header(HttpHeaderNames.Authorization).saveAs("bearerToken"))

  private def individualAuthPayload: String = {
    s"""
       |{
       |	 "credId": "${randomAlphanumeric(16)}",
       |   "affinityGroup": "Individual",
       |   "confidenceLevel": 200,
       |   "credentialStrength": "strong",
       |   "nino": "$validNino",
       |	 "enrolments": ${mtdSaEnrolment(validMtdItId)}
       |}
  """.stripMargin
  }

  private def agentAuthPayload: String = {
    s"""
       | {
       |  "confidenceLevel": 200,
       |  "nino": "$validNino",
       |  "credentialRole": "User",
       |  "affinityGroup": "Agent",
       |  "credentialStrength": "strong",
       |  "credId": "${randomAlphanumeric(16)}",
       |  "enrolments": $agentServicesEnrolment,
       |  "delegatedEnrolments": ${mtdSaDelegatedEnrolment(validMtdItId)}
       |}
     """.stripMargin
  }

  private def mtdSaEnrolment(mtditid: String): String =
    s"""
       |[
       |  {
       |    "key": "HMRC-MTD-IT",
       |    "identifiers": [
       |      {
       |        "key": "MTDITID",
       |        "value": "$mtditid"
       |      }
       |    ],
       |    "state": "Activated"
       |  }
       |]
     """.stripMargin

  private def agentServicesEnrolment: String =
    s"""
       |[
       |  {
       |    "key": "HMRC-AS-AGENT",
       |    "identifiers": [
       |      {
       |        "key": "AgentReferenceNumber",
       |        "value": "1234567"
       |      }
       |    ],
       |    "state": "Activated"
       |  }
       |]
       |""".stripMargin

  private def mtdSaDelegatedEnrolment(mtditid: String): String =
    s""" [
       |    {
       |      "key": "HMRC-MTD-IT",
       |      "identifiers": [
       |        {
       |          "key": "MTDITID",
       |           "value": "${mtditid}"
       |        }
       |      ],
       |      "delegatedAuthRule": "mtd-it-auth"
       |    }
       |  ] """.stripMargin

}
