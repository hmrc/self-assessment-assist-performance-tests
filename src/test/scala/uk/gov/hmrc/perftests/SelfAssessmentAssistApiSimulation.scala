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

import uk.gov.hmrc.performance.simulation.PerformanceTestRunner
import uk.gov.hmrc.perftests.requests.{AuthLoginApiRequests, ReportRequests}

class SelfAssessmentAssistApiSimulation extends PerformanceTestRunner {

  setup("login-individual", "auth-login-api-individual").withRequests(AuthLoginApiRequests.insertAuthRecordIndividual)
  setup("login-agent", "auth-login-api-agent").withRequests(AuthLoginApiRequests.insertAuthRecordAgent)

  setup("generate-report", "generate-report-individual").withRequests(ReportRequests.generateReport("1.0", AffinityGroup.Individual))
  setup("generate-report-as-agent", "generate-report-agent").withRequests(ReportRequests.generateReport("1.0", AffinityGroup.Agent))

  setup("acknowledge-report", "acknowledge-report-individual").withRequests(ReportRequests.acknowledgeReport("1.0", AffinityGroup.Individual))
  setup("acknowledge-report-as-agent", "acknowledge-report-agent").withRequests(ReportRequests.acknowledgeReport("1.0", AffinityGroup.Agent))

  runSimulation()

}