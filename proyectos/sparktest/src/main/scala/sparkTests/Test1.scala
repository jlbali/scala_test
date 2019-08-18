package sparkTests


object Test1 {
    println("Corriendo Test1")
    def test1 = {
        import org.apache.spark.{SparkConf, SparkContext}
        import org.apache.spark.SparkContext._
        println("Spark importado")
    }
    test1
}
