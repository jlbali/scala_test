
package chapter1

import commons.SparkSessionCreate



object Part_1 {
    
    println("Exploratory Data Analysis")
    
    val spark = SparkSessionCreate.createSession()
    import spark.implicits._

    // Carga de datos de entrenamiento.
    val trainPath = "../data/claims/train.csv"
    val trainInput = spark.read
        .option("header", "true")
        .option("inferSchema", "true")
        .format("com.databricks.spark.csv")
        .load(trainPath)
        .cache

    trainInput.printSchema()
    //trainInput.show()
    println("Registros: " + trainInput.count())
    val dfTrain = trainInput
    dfTrain.select("id","cat1", "cont1", "loss").show(5)
    val dfTrain2 = dfTrain.withColumnRenamed("loss", "label")
    //dfTrain2.sqlContext.sql("SELECT avg(label) as AVG_LOSS").show()
    dfTrain2.createOrReplaceTempView("insurance")
    spark.sql("SELECT avg(insurance.label) as AVG_LOSS from insurance").show()


    spark.close()

}
