/*
 * Copyright 2023 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.perftests

import org.apache.commons.lang3.RandomStringUtils

import scala.collection.immutable.Stream
import scala.util.Random.self

object RandomDataGenerator {

  /**
    * Returns random string
    * @param length number of characters required
    * @return random string of a specific length
    */
  def randomAlphanumeric(length: Int): String = scala.util.Random.alphanumeric.take(length).mkString

  /**
    * Returns random string
    * @param length number of characters required
    * @return random string of a specific length
    */
  def randomNumeric(length: Int): String = numeric.take(length).mkString

  def numeric: Stream[Char] = {
    def nextNumeric: Char = {
      val chars = "0123456789"
      chars charAt (self nextInt chars.length)
    }
    Stream continually nextNumeric
  }

  def randomNumber(min: Int, max: Int): Int = scala.util.Random.nextInt(max-min)+min

  def randomDate: String = "20%02d-%02d-%02d" format (randomNumber(0,16), randomNumber(1,12), randomNumber(1,28))

  def randomTime: String = "T%02d:%02d:%02d.123Z" format (randomNumber(0,23), randomNumber(0,59), randomNumber(0,59))

  def generateVrn: String = RandomStringUtils.randomNumeric(9)

  def generatePeriodKey: String = randomNumeric(4)

  def generateArn: String = "TARN" + randomNumeric(7)

  def randomValue: BigDecimal = s"${randomNumber(0,9999999)}.${randomNumber(0,99)}".toDouble

}