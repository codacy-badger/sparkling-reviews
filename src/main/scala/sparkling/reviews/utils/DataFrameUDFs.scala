package sparkling.reviews.utils

/**
  * Copyright 2018 Pratik Barhate
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  * http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing,
  * software distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  */

import org.apache.spark.sql.Row
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions.udf
import sparkling.reviews.constants.RegexExpressions._
import sparkling.reviews.constants.StringConstants._

/**
  * A static class to hold all the DataFrame UDFs.
  */
private[sparkling] object DataFrameUDFs {

  /**
    * Custom UDF for parsing raw text data and extract required data.
    *
    * i) Remove end line chars
    * ii) Remove all the web hyperlinks
    * iii) Remove all the non-alphanumeric characters
    * iv) Remove all the extra white spaces
    *
    * @return [[String]] clean reviews
    */
  def cleanReviews: UserDefinedFunction =
    udf((inputStr: String) => {
      extraWhiteSpaceRegex.replaceAllIn(nonAlphaNumericWithSpaceRegex.replaceAllIn(
        webLinksRegex.replaceAllIn(endLineCharRegex.replaceAllIn(inputStr, SingleWhiteSpace), EmptyString),
        EmptyString), SingleWhiteSpace)
    })

  /**
    * Takes the output from the JohnSnowLabs pre-trained sentiment
    * model and returns the main sentiment, by checking which sub-sequence
    * of the comment has the maximum length.
    *
    * @return [[String]] - sentiment (positive or negative)
    */
  def getSingleSentimentStr: UserDefinedFunction =
    udf((input: Seq[Row]) => {
      input
        .map(x => (x.getInt(2) - x.getInt(1), x.getString(3)))
        .maxBy(_._1)._2
    })

  /**
    * Returns a numeric value for the corresponding sentiments.
    *
    * @return [[Double]] 1.0 for positive sentiment and -1.0 for a negative one
    */
  def getSentimentValue: UserDefinedFunction =
    udf((input: String) => {
      input match {
        case "positive" => 1.0
        case "negative" => -1.0
      }
    })

  /**
    * Takes in the POS (Part of Speech) tags and stem form of the word.
    * And returns distinct stem form of all the Noun words.
    *
    * @return [[List]] of [[String]] which represents distinct key words of the review.
    */
  def getImportantWords: UserDefinedFunction =
    udf((stemList: Seq[Row], posList: Seq[Row]) => {
      val nounWordList = posList.filter(x => x.getString(3).matches(NounFormsStrExpr))
      val indices = nounWordList.map(posList.indexOf(_))
      val requiredLemmaList = stemList.filter(x => indices.contains(stemList.indexOf(x)))
      requiredLemmaList
        .map(x => x.getString(3))
        .distinct
    })

  /**
    * Calculate the sentiment factor (f), or we can say the depth to which
    * user felt about the product while providing the comment.
    *
    * If its a negative sentiment the multiplication factor is proportional
    * to {MAX - RATING} how much the rating points are less than the max value (how far the value
    * is from the max) and if the sentiment is positive, then multiplication factor
    * is proportional directly to {RATING} the rating (how close the value is towards the max value).
    *
    * @return [[Double]] Sentiment Factor
    */
  def getSentimentFactor: UserDefinedFunction =
    udf((value: Double, userRating: Double, maxRating: Double) => {
      value match {
        case x if x < 0 =>
          val multiplicationFactor = (maxRating - userRating) / maxRating
          multiplicationFactor * value
        case _ =>
          val multiplicationFactor = userRating / maxRating
          multiplicationFactor * value
      }
    })

  /**
    * Finds which sentiment has the highest count.
    *
    * @return [[String]] sentiment with the highest count.
    */
  def getProductSentiment: UserDefinedFunction =
    udf((input: Seq[Seq[String]]) => {
      input.map(x => (x.head, x.last.toLong)).maxBy(_._2)._1
    })

}