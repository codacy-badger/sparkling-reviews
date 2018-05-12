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

import org.apache.spark.sql.SparkSession

private[sparkling] object SessionFactory {

  /**
    * Initialize the spark session.
    * This static class helps in ensuring there is
    * only one instance of [[SparkSession]].
    */
  private final lazy val sparkSession: SparkSession = SparkSession.builder()
    .appName("ProductReviewAnalysis")
    .getOrCreate()

  /**
    * Getter to maintain single spark session
    *
    * @return [[SparkSession]]
    */
  def getSparkSessionInstance: SparkSession = sparkSession

}
