import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}
import org.spark_project.jetty.util.security.Password

import java.util.Properties


class LoadDataToPostgres(path: String, user: String, password: String, url: String, table: String) {

  def getConnectionProperties(user: String, password: String): Properties = {
    val connectionProperties = new Properties()
    connectionProperties.put("user", user)
    connectionProperties.setProperty("Driver", "org.postgresql.Driver")
    connectionProperties.put("password", password)
    connectionProperties
  }

  def readData(path: String)(implicit spark: SparkSession): DataFrame = {
    spark.read.format("csv").option("header", "true").csv(path)
  }

  def transformData(df: DataFrame): DataFrame = {
    df.distinct()
  }

  def writeData(df: DataFrame): Unit = {
    df.repartition(1)
      .write.format("jdbc")
      .mode(SaveMode.Overwrite)
      .jdbc(url, table, getConnectionProperties(user, password))
  }


}