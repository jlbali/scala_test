package sparkTests


object Test1 {
    println("Corriendo Test1")
    
    def test1 = {
        import org.apache.spark.{SparkConf, SparkContext}
        import org.apache.spark.SparkContext._
        println("Spark importado")
    }
    
    def test2 = {
        import org.apache.spark.{SparkConf, SparkContext}
        import org.apache.spark.SparkContext._
        println("Creando context")
        val conf = new SparkConf().setMaster("local").setAppName("Prueba")
        val sc = new SparkContext(conf)
    }
    
    
    //test1
    test2
}
