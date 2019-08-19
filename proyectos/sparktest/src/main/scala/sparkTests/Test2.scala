
package sparkTests

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.SparkContext._



object Test2 {

    def test1 = {
        println("Creating Context")
        val conf = new SparkConf().setMaster("local").setAppName("Test")
        val sc = new SparkContext(conf)
        val rdd = sc.parallelize(List("Spark", "is", "an", "amazing", "piece", "of", "technology"))
        val pairRDD = rdd.map(w => (w.length,w))
        pairRDD.collect().foreach(println)
        sc.stop()
    }

    def test2 = {
        println("Creating Context")
        val conf = new SparkConf().setMaster("local").setAppName("Test")
        val sc = new SparkContext(conf)
        val rdd = sc.parallelize(List("Spark", "is", "an", "amazing", "piece", "of", "technology"))
        val pairRDD = rdd.map(w => (w.length,w))
        val wordByLenRDD = pairRDD.groupByKey() // Hay un groupBy a secas, quizás ese no requiera key.
        wordByLenRDD.collect().foreach(println)
        sc.stop()
    }

    def test3 = {
        println("Creating Context")
        val conf = new SparkConf().setMaster("local").setAppName("Test")
        val sc = new SparkContext(conf)
        val candiesRdd = sc.parallelize(List(("caramel", 24.5), ("caramel", 20.1), ("chocolate", 50.6), ("chocolate", 87.5), ("maple", 200.4)))
        val candiesSum = candiesRdd.reduceByKey((total,value) => total + value)
        println("Summary")
        candiesSum.collect().foreach(println)
        // Lo mismo, pero ordenado por precio.
        val candiesSort = candiesSum.sortBy( tup => tup._2)
        println("Ordered Summary")
        candiesSort.collect().foreach(println)
        sc.stop()
    }
    // Ver el Join

    def test4 = {
        println("Creating Context")
        val conf = new SparkConf().setMaster("local").setAppName("Test")
        val sc = new SparkContext(conf)
        val candiesRdd = sc.parallelize(List(("caramel", 24.5), ("caramel", 20.1), ("chocolate", 50.6), ("chocolate", 87.5), ("maple", 200.4)))
        val contador = candiesRdd.countByKey()
        println("Contador: " + contador)
        sc.stop()

    }

    //test1
    //test2
    //test3
    test4
}


