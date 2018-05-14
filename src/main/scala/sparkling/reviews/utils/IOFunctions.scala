package sparkling.reviews.utils

import org.apache.spark.sql.{DataFrame, SaveMode}
import sparkling.reviews.core.SessionFactory

private[sparkling] object IOFunctions {

  /**
    * Load the raw data from the DFS or local FS,
    * which is in parquet format.
    *
    * @param path Absolute path of the data file or
    *             folder where the part files are present (many parquet files)
    * @return [[DataFrame]] The given data loaded into Apache Spark.
    */
  def loadData(path: String): DataFrame = {
    SessionFactory.getSparkSessionInstance.read.parquet(path)
  }

  /**
    * Method to write a DataFrame to a DFS or local FS,
    * in parquet format.
    *
    * @param df        To be written to the secondary memory.
    * @param path      Absolute folder path where the data will be written.
    * @param writeMode Mode in which the data has to be written. (overwrite, append, etc.)
    */
  def writeData(df: DataFrame, path: String, writeMode: SaveMode = SaveMode.Overwrite): Unit = {
    df.write.mode(writeMode).parquet(path)
  }

}
