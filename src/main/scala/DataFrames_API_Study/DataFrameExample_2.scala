package org.saboor.scalapractice
package DataFrames_API_Study

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.DateType

object DataFrameExample_2 extends App {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val spark = SparkSession.builder.master("local[*]").appName("DataFrameExample_2").getOrCreate()
    val sc = spark.sparkContext
    import spark.implicits._
    import spark.sql
  val myList = List((1,"2013-07-25",11599,"CLOSED"),
    (2,"2014-07-25",256,"PENDING_PAYMENT"),
    (3,"2015-07-25",11599,"COMPLETE"),
    (4,"2016-07-25",8827,"CLOSED"))

  var df1 = spark.createDataFrame(myList) .toDF("order_id","order_date","customer_id","order_status")
  //spark.sparkContext.parallelize(myList).toDF() - Method Involving RDD
  var df_new = df1
    .withColumn("order_date",unix_timestamp(col("order_date").cast(DateType)))
    .withColumn("new_id",monotonically_increasing_id)
    .dropDuplicates("order_date","customer_id")
    .drop("order_id")
    .sort("order_date")
  df_new.printSchema()
  df_new.show(false)

  scala.io.StdIn.readLine()
  spark.stop()

}