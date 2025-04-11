#!/usr/bin/env bash
sbt -Dperftest.runSmokeTest=true -DrunLocal=true Gatling/test
#sbt -DrunLocal=true -DloadPercentage=1 Gatling/test
#sbt -DrunLocal=true Gatling/test
