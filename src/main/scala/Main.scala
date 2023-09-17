import org.apache.spark.sql.{DataFrame, SparkSession}

object Main extends App {
  // docker run --name habr-pg-13.3 -p 5432:5432 -e POSTGRES_USER=fedor -e POSTGRES_PASSWORD=anime -e POSTGRES_DB=db -d postgres:13.3

  implicit val spark: SparkSession = SparkSession
    .builder()
    .config("spark.jars.packages", "postgresql-42.6.0.jar")
    .master("local[*]")
    .getOrCreate()

  val path = "crime.csv"
  val user = "fedor"
  val password = "anime"
  val url = "jdbc:postgresql://localhost:5432/db"
  val table = "test"

  val loadData = new LoadDataToPostgres(path, user, password, url, table)

  val data: DataFrame = loadData.readData(path)

  val new_data: DataFrame = loadData.transformData(data)

  new_data.show()

  loadData.writeData(new_data)

}
