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

package uk.gov.hmrc.perftests

object Common {

  val validMtdItId: String = "123456789012345"
  val validNino: String = "NJ070957A"
  val validNinoWithoutSuffix: String = "NJ070957"

  val validReportId: String =  "a365c0b4-06e3-4fef-a555-16fd0877dc7c"
  val correlationId: String =  "a5fht738957jfjf845jgjf855"

  val calculationId: String = "111190b4-06e3-4fef-a555-6fd0877dc7ca"

  val commonHeaders: Map[String, String] =
    Map("Accept" -> s"application/vnd.hmrc.1.0+json",
      "Authorization" -> s"$${bearerToken}",
      "Content-Type" -> "application/json")

  val headersInsolvent: Map[String, String] =
    Map("Accept" -> s"application/vnd.hmrc.1.0+json",
      "Content-Type" -> "application/json")

}