#!/usr/bin/env bash
sbt -DrunLocal=true -Dperftest.runSmokeTest=true Gatling/test
#sbt -DrunLocal=true -DloadPercentage=1 Gatling/test
#sbt -DrunLocal=true Gatling/test
