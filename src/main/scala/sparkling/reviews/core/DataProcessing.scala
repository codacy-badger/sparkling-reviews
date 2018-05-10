package sparkling.reviews.core

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

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._
import sparkling.reviews.constants.DataConstants._
import sparkling.reviews.constants.StringConstants.SingleWhiteSpace
import sparkling.reviews.utils.DataFrameUtils.{cleanReviews, sentimentFactor}

private[core] object DataProcessing {

  /**
    * This method takes in the raw data follows pre-processing steps :-
    * 1. Drop the timestamp column as it won't be used.
    * 2. Combine the summary and review text, as it is a part of the review analysis.
    * 3. Drop the review and summary individual columns.
    * 4. Clean all the text data of - hyperlinks, extra spaces, and end line chars.
    *
    * @param df Initial data load.
    * @return [[DataFrame]] With data as required and in clean form.
    */
  def preProcessing(df: DataFrame): DataFrame = {

    df.drop(TimeStamp)
      .withColumn(CombinedText, concat_ws(SingleWhiteSpace, col(ReviewSummary), col(Review)))
      .drop(ReviewSummary, Review)
      .withColumn(CleanText, cleanReviews(col(CombinedText)))
  }

  /**
    * This method calculate the sentiment factor (f), or we can say the
    * depth to which user felt about the product while providing the reviews.
    *
    * Let's say there are two users with positive reviews. But user 1 have given a
    * rating of 5.0 whereas the user 2 have given a rating of 4.2, for user 1 `f=1`
    * and for user 2 `f=0.84`. From this we can infer the overall depth of the user's
    * sentiments, which shows user 2 was happy with the product but not as happy as user 1.
    *
    * @param df Data with sentiment value and ratings of the user
    * @return [[DataFrame]] With sentiment factor calculated for each entry.
    */
  def calSentimentFactor(df: DataFrame): DataFrame = {

    df.withColumn(SentimentFactor, sentimentFactor(col(SentimentValue), col(Rating), lit(MaxRating)))
      .drop(SentimentValue, Rating)
  }

}