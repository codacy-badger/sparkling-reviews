package reviews.analysis.core

import org.apache.spark.sql.SparkSession

private[analysis] object SessionFactory {

  /**
    * Initialize the spark session
    */
  private final lazy val sparkSession: SparkSession = SparkSession.builder()
    .appName("ProductReviewAnalysis")
    .enableHiveSupport()
    .getOrCreate()

  /**
    * Getter to maintain single spark session
    *
    * @return [[SparkSession]]
    */
  def getSparkSession: SparkSession = sparkSession

}
