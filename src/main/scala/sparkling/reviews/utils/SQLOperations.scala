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

}
