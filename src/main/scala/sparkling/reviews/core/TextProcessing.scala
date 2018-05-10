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

import com.johnsnowlabs.nlp.pretrained.pipelines.en.{AdvancedPipeline, SentimentPipeline}
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.col
import sparkling.reviews.constants.DataConstants._
import sparkling.reviews.utils.DataFrameUtils.{getImportantWords, getSentimentValue, getSingleSentimentStr}

private[core] object TextProcessing {

  /**
    * This method uses the pre-trained model from John Snow Labs,
    * for sentiment tagging (analysis) of the string in the input column.
    *
    * Select columns which are required - [[ProductID]], [[Rating]], [[CleanText]],
    * and [[SentimentsJSL]]. [[UserID]] is dropped as it is of no use, but each row
    * is a user's reviews and ratings whose identity is not of any importance for
    * the use case.
    *
    * @param df           DataFrame for which we want to do sentiment analysis
    * @param inputColName Column in which the string under observation is present
    * @return [[DataFrame]] With additional column to tell us about the sentiments
    */
  def tagStringWithSentiments(df: DataFrame, inputColName: String): DataFrame = {
    SentimentPipeline().annotate(df, inputColName)
      .select(ProductID, CleanText, Rating, SentimentsJSL)
      .withColumn(SentimentsJSL, getSingleSentimentStr(col(SentimentsJSL)))
      .withColumn(SentimentValue, getSentimentValue(col(SentimentsJSL)))
  }

  /**
    * This method uses the pre-trained model from John Snow Labs,
    * for tagging the words with POS (Part of Speech) and also their Lemma form.
    *
    * @param df           DataFrame for which we want to basic NLP tags of each word
    * @param inputColName Column in which the string under observation is present
    * @return
    */
  def getKeyWords(df: DataFrame, inputColName: String): DataFrame = {
    AdvancedPipeline().annotate(df, inputColName)
      .withColumnRenamed(TextColumnJSL, CleanText)
      .withColumn(KeyWords, getImportantWords(col(StemJSL), col(PosJSL)))
      .select(ProductID, Rating, SentimentsJSL, SentimentValue, KeyWords)
  }

}
