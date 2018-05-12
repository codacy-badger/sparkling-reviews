package sparkling.reviews.utils

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.expressions.{Window, WindowSpec}
import org.apache.spark.sql.functions.col
import sparkling.reviews.constants.DataConstants.{ProductID, ProductWordCount, ProductWordExploded}

/**
  * A static class to hold some conditions to be used
  * in [[DataFrame]] operations.
  */
private[sparkling] object SQLOperations {

  /**
    * Window specification to count number of occurrences
    * of words per product.
    *
    * @return [[WindowSpec]]
    */
  def getProductWordCountWindow: WindowSpec =
    Window.partitionBy(ProductID, ProductWordExploded)
      .rangeBetween(Window.unboundedPreceding, Window.unboundedFollowing)

  /**
    * Window specification to get the word in descending order
    * by word count per product.
    *
    * @return [[WindowSpec]]
    */
  def getProductWordRankWindow: WindowSpec =
    Window.partitionBy(ProductID).orderBy(col(ProductWordCount).desc)
      .rangeBetween(Window.unboundedPreceding, Window.currentRow)

}
