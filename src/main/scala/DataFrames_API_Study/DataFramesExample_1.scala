package org.saboor.scalapractice
package DataFrames_API_Study



import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql._
import org.apache.spark.sql.types.StructField

import java.sql.Struct

object DataFramesExample_1 extends App {
  Logger.getLogger("org").setLevel(Level.ERROR)
  val spark = SparkSession.builder.master("local[*]").appName("DataFramesExample_1").getOrCreate()
    val sc = spark.sparkContext
    import spark.implicits._
    import spark.sql
    var orders_df = spark.read
                    .format("csv")
                    .option("header",true)
                    .option("inferSchema",true)
                    .option("path","C:/Users/sabbi/OneDrive/Desktop/Shared Folder//orders-201025-223502.csv")
                    .load()
  println("orders_df has "+orders_df.rdd.getNumPartitions+" partitions")
  var orders_df_repartitioned = orders_df.repartition(5)
  println("orders_df_repartitioned has "+orders_df_repartitioned.rdd.getNumPartitions+" partitions")

  orders_df.printSchema()

  orders_df_repartitioned.write
    .format("csv")
    .partitionBy("order_status")
    .mode(SaveMode.Overwrite)
    .option("path","C:/Users/sabbi/OneDrive/Desktop/Shared Folder_1//")
    .save()

  scala.io.StdIn.readLine()
    spark.stop()

}