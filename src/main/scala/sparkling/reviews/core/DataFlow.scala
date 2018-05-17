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

import sparkling.reviews.constants.DataConstants._
import sparkling.reviews.utils.IOFunctions._
import sparkling.reviews.utils.Logs

/**
  * Class to manage the data processes in sequence
  *
  * It has a solid class implementation, as a flow should
  * have its own identity.
  *
  * @param dataPath Path of the data
  */
private[core] case class DataFlow(dataPath: String,
                                  resultPath: String) extends Logs {

  /**
    * This is the data flow of the application.
    * The steps after loading the data are:-
    * 1. Clean the text of some unwanted chars and string formats.
    * 2. Use JohnSnowLabs pre-trained sentiment prediction pipeline to get the sentiments.
    * 3. Use JohnSnowLabs pre-trained `AdvancedPipeline` to get the key words.
    * 4. Calculate the sentiment factor using the rating given by the user.
    * 5. Get all to computed aggregated to a product level.
    * 6. Write the computed result to a given location on to a secondary memory.
    *
    *
    * Reason for doing `count` operation on `sentimentFactorDF`:-
    * Caching the computed data up to "sentimentFactorDF" will speed up the
    * further process as the DAG for the same will be computed only once.
    * And the data is not cached until an action come in picture.
    */
  def execute(): Unit = {

    log.info(s"Data processing started.")
    val rawDF = loadData(dataPath)
    val cleanDF = DataProcessing.preProcessing(rawDF)
    val sentimentDF = TextProcessing.tagStringWithSentiments(cleanDF, CombinedText)
    val keyWordsSentimentDF = TextProcessing.getKeyWords(sentimentDF, CleanText)
    val sentimentFactorDF = DataProcessing.calSentimentFactor(keyWordsSentimentDF).cache
    log.info(s"Caching (save) intermediate data, to avoid multiple execution of a DAG.")
    val totalReviewCount: Long = sentimentFactorDF.count
    log.info(s"Per review, computation is done.")
    log.info(s"Total review counts is $totalReviewCount")
    log.info(s"Product level aggregations started.")
    val productSentimentInsights = DataProcessing.aggregateToProductLevel(sentimentFactorDF)
    writeData(productSentimentInsights, resultPath)
    log.info(s"All processes are done.")
  }

}
