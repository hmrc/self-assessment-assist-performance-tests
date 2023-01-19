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

package uk.gov.hmrc.perftests.auth

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import org.joda.time.DateTime
import uk.gov.hmrc.performance.conf.ServicesConfiguration
import uk.gov.hmrc.perftests.Common._
import uk.gov.hmrc.perftests.RandomDataGenerator._

object AuthLoginApiRequests extends ServicesConfiguration {

  val baseUrlAuthLoginApi: String = baseUrlFor("auth-login-api")
  val authLoginApiUrl: String     = s"$baseUrlAuthLoginApi/government-gateway/session/login"

  val insertAuthRecordOrganisation: ChainBuilder = exec(
    http("Insert Auth Record Organisation")
      .post(authLoginApiUrl)
      .body(StringBody(authPayload(Organisation)))
      .headers(Map("Content-Type" -> "application/json"))
      .check(status is 201)
      .check(header("Authorization").saveAs("bearerToken"))
  )

  val insertAuthRecordAgent: ChainBuilder = exec(
    http("Insert Auth Record Agent")
      .post(authLoginApiUrl)
      .body(StringBody(authPayload(Agent)))
      .headers(Map("Content-Type" -> "application/json"))
      .check(status is 201)
      .check(header("Authorization").saveAs("bearerToken"))
  )

  val insertAuthRecordIndividual: ChainBuilder = exec(
    http("Insert Auth Record Agent")
      .post(authLoginApiUrl)
      .body(StringBody(authPayload(Individual)))
      .headers(Map("Content-Type" -> "application/json"))
      .check(status is 201)
      .check(header("Authorization").saveAs("bearerToken"))
  )

  def authPayload(affinityGroup: String): String = {
    val credentialRole      = if (affinityGroup == Agent) "Admin" else "User"
    val enrolments          = if (affinityGroup == Agent) "[]" else mtdSaEnrolment
    val delegatedEnrolments = if (affinityGroup == Agent) mtdSaDelegatedEnrolment else "[]"

    def createAuthToken = {
      val stringBody =
        s"""
         | {
         |  "credId": "123456789",
         |  "affinityGroup": "Individual",
         |  "confidenceLevel": 200,
         |  "credentialStrength": "strong",
         |  "nino": "AA000000B",
         |  "enrolments": []
         | }""".stripMargin

      http("Create auth token")
        .post(authLoginApiUrl)
        .body(StringBody(stringBody))
        .asJson
        .check(status.is(201))
    }

    val authPayload =
      s"""
         | {
         |  "internalId": "Int-a7688cda-d983-472d-9971-ddca5f124641",
         |  "externalId": "Ext-c4ebc935-ac7a-4cc2-950a-19e6fac91f2a",
         |  "agentCode" : "",
         |  "credentials": {
         |    "providerId": "8124873381064832",
         |    "providerType": "GovernmentGateway"
         |  },
         |  "confidenceLevel": 200,
         |  "nino": "AZ127598B",
         |  "usersName": "test",
         |  "agentInformation": {},
         |  "credentialRole": "$credentialRole",
         |  "itmpData": $itmpData,
         |  "affinityGroup": "$affinityGroup",
         |  "credentialStrength": "strong",
         |  "loginTimes": {
         |    "currentLogin": "${DateTime.parse("2018-04-22T09:00:00.000Z")}",
         |    "previousLogin": "${DateTime.parse("2018-03-01T12:00:00.000Z")}"
         |  },
         |  "credId": "${randomAlphanumeric(16)}",
         |  "enrolments": $enrolments,
         |  "delegatedEnrolments": $delegatedEnrolments
         | }
     """.stripMargin

    println("\n" + affinityGroup + ":\n" + authPayload + "\n")
    authPayload
  }

  lazy val mtdSaEnrolment: String =
    s""" [
       |   {
       |     "key": "HMRC-MTD-IT",
       |     "identifiers": [
       |       {
       |         "key": "MTDITID",
       |         "value": "$${mtditid}"
       |       }
       |     ],
       |     "state": "Activated"
       |   }
       |  ] """.stripMargin

  lazy val mtdSaDelegatedEnrolment: String =
    s""" [
       |    {
       |      "key": "HMRC-MTD-IT",
       |      "identifiers": [
       |        {
       |          "key": "MTDITID",
       |           "value": "$${mtditid}"
       |        }
       |      ],
       |      "delegatedAuthRule": "mtd-it-auth"
       |    }
       |  ] """.stripMargin

  lazy val itmpData: String =
    s""" {
       |    "givenName": "test",
       |    "middleName": "test",
       |    "familyName": "test",
       |    "birthdate": "1985-01-01",
       |    "address": {
       |      "line1": "Line 1",
       |      "line2": "line 2",
       |      "line3": "line 3",
       |      "line4": "line 4",
       |      "line5": "line 5",
       |      "postCode": "TF3 4ER",
       |      "countryName": "United Kingdom",
       |      "countryCode": "UK"
       |    }
       |  } """.stripMargin
}
