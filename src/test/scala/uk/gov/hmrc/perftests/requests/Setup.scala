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
import play.api.libs.json.Json
import uk.gov.hmrc.performance.conf.ServicesConfiguration
import uk.gov.hmrc.perftests.Common

object Setup extends ServicesConfiguration {

  private val desStubBaseUrl = baseUrlFor("des-auth-stub")
  private def insertNinoUrl(nino: String) = s"$desStubBaseUrl/pay-as-you-earn/02.00.00/individuals/$nino"

  private def insertNinoPayload(nino: String): String =
    s"""
       |{
       |  "nino": "$nino",
       |  "ninoSuffix": "A",
       |  "names": {
       |    "1": {
       |      "sequenceNumber": 12345,
       |      "firstForenameOrInitial": "Jane",
       |      "secondForenameOrInitial": "A",
       |      "surname": "Doe",
       |      "startDate": "2001-02-02"
       |    } },
       |  "sex": "F",
       |  "dateOfBirth": "1987-02-20",
       |  "deceased": false,
       |  "addresses": {
       |    "1": {
       |      "line1": "1 Oxford Road",
       |      "line2": "Town Centre",
       |      "line3": "London",
       |      "line4": "London",
       |      "line5": "England",
       |      "postcode": "NW2 3CD",
       |      "countryCode": 1,
       |      "sequenceNumber": 1,
       |      "startDate": "2001-02-02"
       |    }
       |  },
       |  "phoneNumbers": {
       |    "1": {
       |      "telephoneNumber": "01999123459",
       |      "telephoneType": 1
       |    }
       |  },
       |  "accountStatus": 0,
       |  "manualCorrespondenceInd": false,
       |  "dateOfEntry": "2001-02-02",
       |  "dateOfRegistration": "2001-02-02",
       |  "registrationType": 0,
       |  "hasSelfAssessmentAccount": false,
       |  "audioOutputRequired": false,
       |  "brailleOutputRequired": false,
       |  "largePrintOutputRequired": false,
       |  "welshOutputRequired": false
       |}
      """.stripMargin

  val insertNino: HttpRequestBuilder = http("Insert nino")
    .post(insertNinoUrl(Common.validNinoWithoutSuffix))
    .body(StringBody(insertNinoPayload(Common.validNinoWithoutSuffix)))
    .headers(
      Map("Content-Type" -> "application/json")
    )

  def curlInsertNino: Unit = {
    import sys.process._

    val command: Seq[String] = Seq("curl", "-f", "-X", "POST",
      "-H", "Content-Type: application/json",
      insertNinoUrl(Common.validNinoWithoutSuffix),
      "-d", Json.stringify(Json.toJson(insertNinoPayload(Common.validNinoWithoutSuffix)))
    )

    try {
      command.!!
      ()
    } catch {
      case e: Throwable => println(s"Error inserting nino with command '${command.mkString(" ")}': ${e.getMessage}")
    }
  }

}
