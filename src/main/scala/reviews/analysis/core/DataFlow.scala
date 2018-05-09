package reviews.analysis.core

import org.apache.spark.sql.DataFrame
import reviews.analysis.constants.DataConstants._

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
    val sentimentDF = TextProcessing.tagStringWithSentimentsJSL(cleanDF, CombinedText)
    val keyWordsSentimentDF = TextProcessing.getKeyWordsJSL(sentimentDF, CleanText)
    val sentimentFactorDF = DataProcessing.calSentimentFactor(keyWordsSentimentDF)
    sentimentFactorDF.show(1, truncate = false)
  }

  /**
    * Load the raw data from the DFS
    *
    * @return [[DataFrame]]
    */
  private def loadData: DataFrame = {
    SessionFactory.getSparkSession.read.parquet(dataPath)
  }

}
