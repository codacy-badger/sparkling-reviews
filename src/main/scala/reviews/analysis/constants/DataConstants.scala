package reviews.analysis.constants

object DataConstants {

  /**
    * Column names in the original dataset.
    */
  final val UserID: String = "reviewerID"
  final val ProductID: String = "asin"
  final val Rating: String = "overall"
  final val Review: String = "reviewText"
  final val ReviewSummary: String = "summary"
  final val TimeStamp: String = "timeStamp"

  /**
    * Column names which will be generated while processing
    */
  final val CombinedText: String = "summary_plus_review"
  final val CleanText: String = "clean_text"
  final val SentimentValue: String = "sentiment_value"
  final val SentimentFactor: String = "sentiment_factor"
  final val KeyWords: String = "key_words"

  /**
    * Columns names of JohnSnowLabs outputs
    */
  final val SentimentsJSL: String = "sentiment"
  final val TextColumnJSL: String = "text"
  final val StemJSL: String = "stem"
  final val PosJSL: String = "pos"

  /**
    * Values which are fixed to the given data
    * e.g. Rating is out of 5, it could have been out of 10 too.
    */
  final val MaxRating: Double = 5.0

}
