package chapter1

import org.apache.spark.ml.feature.{ StringIndexer, StringIndexerModel}
import org.apache.spark.ml.feature.VectorAssembler


import commons.SparkSessionCreate


object Part_2 {
    
    println("Preprocessing Data")

    var trainSample = 1.0
    var testSample = 1.0
    val trainPath = "../data/claims/train.csv"
    val testPath = "../data/claims/test.csv"
    val spark = SparkSessionCreate.createSession()
    import spark.implicits._

    val trainInput = spark.read
        .option("header", "true")
        .option("inferSchema", "true")
        .format("com.databricks.spark.csv")
        .load(trainPath)
        .cache
    val testInput = spark.read
        .option("header", "true")
        .option("inferSchema", "true")
        .format("com.databricks.spark.csv")
        .load(testPath)
        .cache

    println("Preparing data for training model")
    var dataTrain = trainInput.withColumnRenamed("loss", "label").sample(false,
        trainSample)
    // Eliminando datos faltantes
    dataTrain = dataTrain.na.drop()
    val seed = 12345L
    val splits = dataTrain.randomSplit(Array(0.75, 0.25), seed)
    val (trainingData, validationData) = (splits(0), splits(1))
    trainingData.cache
    validationData.cache

    // Parecido con el testing set.
    val testData = testInput.withColumnRenamed("loss", "label").sample(false, testSample).cache

    // Seguir...
    

}

