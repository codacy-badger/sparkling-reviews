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
import sparkling.reviews.constants.DataConstants._

/**
  * Class to manage the data processes in sequence
  *
  * It has a solid class implementation, as a flow should
  * have its own identity.
  *
  * @param dataPath Path of the data
  */
private[core] case class DataFlow(dataPath: String) {

  /**
    * This is the data flow of the application.
    * The steps after loading the data are:-
    * 1. Clean the text of some unwanted chars and string formats.
    * 2. Use JohnSnowLabs pre-trained sentiment prediction model to get the sentiments.
    * 3. Use JohnSnowLabs pre-trained `AdvancedPipeline` to get the key words.
    * 4. Calculate the sentiment factor using the rating given by the user.
    */
  def execute(): Unit = {

    val rawDF = loadData
    val cleanDF = DataProcessing.preProcessing(rawDF)
    val sentimentDF = TextProcessing.tagStringWithSentiments(cleanDF, CombinedText)
    val keyWordsSentimentDF = TextProcessing.getKeyWords(sentimentDF, CleanText)
    /**
      * If you have main memory (RAM) more than the data size.
      *
      * Comment the line just after this comment which holds `sentimentFactorDF` and
      * un-comment the similar lines below which caches the DataFrame and perform count on it,
      * this will speed up the further processes.
      */
    val sentimentFactorDF = DataProcessing.calSentimentFactor(keyWordsSentimentDF)
//    val sentimentFactorDF = DataProcessing.calSentimentFactor(keyWordsSentimentDF).cache
//    sentimentFactorDF.count
    sentimentFactorDF.printSchema()
  }

  /**
    * Load the raw data from the DFS or local FS
    *
    * @return [[DataFrame]]
    */
  private def loadData: DataFrame = {
    SessionFactory.getSparkSession.read.parquet(dataPath)
  }

}
