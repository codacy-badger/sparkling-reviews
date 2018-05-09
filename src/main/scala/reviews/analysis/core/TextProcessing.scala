package reviews.analysis.core

import com.johnsnowlabs.nlp.pretrained.pipelines.en.{AdvancedPipeline, SentimentPipeline}
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.col
import reviews.analysis.constants.DataConstants._
import reviews.analysis.utils.DataFrameUtils.{getImportantWordsJSL, getSentimentValueJSL, getSingleSentimentStrJSL}

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
  def tagStringWithSentimentsJSL(df: DataFrame, inputColName: String): DataFrame = {
    SentimentPipeline().annotate(df, inputColName)
      .select(ProductID, CleanText, Rating, SentimentsJSL)
      .withColumn(SentimentsJSL, getSingleSentimentStrJSL(col(SentimentsJSL)))
      .withColumn(SentimentValue, getSentimentValueJSL(col(SentimentsJSL)))
  }

  /**
    * This method uses the pre-trained model from John Snow Labs,
    * for tagging the words with POS (Part of Speech) and also their Lemma form.
    *
    * @param df           DataFrame for which we want to basic NLP tags of each word
    * @param inputColName Column in which the string under observation is present
    * @return
    */
  def getKeyWordsJSL(df: DataFrame, inputColName: String): DataFrame = {
    AdvancedPipeline().annotate(df, inputColName)
      .withColumnRenamed(TextColumnJSL, CleanText)
      .withColumn(KeyWords, getImportantWordsJSL(col(StemJSL), col(PosJSL)))
      .select(ProductID, Rating, SentimentsJSL, SentimentValue, KeyWords)
  }

}
