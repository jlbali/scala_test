package scalaprog1.week6

import scala.collection.Map.WithDefault

object Lesson extends App {
  
  val nums = Vector(1,2,5,-88)
  println("Nums: " + nums)
  
  val names:Seq[String] = Vector("fred", "bob", "john")
  val names2:Iterable[String] = Vector("fred", "bob", "john")
  
  val xs = Array(1,2,3,44)
  println("Doubled: " + (xs map (x => 2*x))) // Se ve feo...
  
  var r:Range = 1 to 5
  val s:Range = 1 until 5
  
  def scalarProduct(xs: Vector[Double], ys:Vector[Double]):Double =
    (xs zip ys).map(xy => xy._1 * xy._2).sum
  
  val x = Vector(1.0,5.0,10.0)
  val y = Vector(0.5, -2.0, 3.0)
  println("Scalar product x e y: " + scalarProduct(x, y))

  def scalarProduct2(xs: Vector[Double], ys:Vector[Double]):Double =
    (xs zip ys).map{case (x,y) => x*y}.sum
  // Pattern matching function value.
    
  println("Scalar product2 x e y: " + scalarProduct2(x, y))
  
  def isPrime(n: Int): Boolean =
    (2 until n) forall(d => n % d != 0)
    
  println("5 es primo: " + isPrime(5))
  println("8 es primo: " + isPrime(8))
  
  // Combinatorial Search and For-Expressions.
  val n = 7
  val _pairs = (1 until n) map (i =>
    (1 until i) map (j => (i,j)))
  println("Pares que van con cada i: " + _pairs)
  val pairs = _pairs.flatten
  println("Pares aplanados: " + pairs)
  // xs flatMap f = (xs map f).flatten
  val pairs2 = (1 until n) flatMap (i =>
    (1 until i) map (j => (i,j)))
  println("Pares nuevos: " + pairs2)
  // Es igual que pairs.
  val primePairs = pairs2 filter {case (i,j) => isPrime(i+j)}
  println("Pares con suma primo: " + primePairs)
  
  case class Person(name: String, age: Int)
  val john = new Person("John", 34)
  val audrey = new Person("Audrey", 43)
  val stephen = new Person("Stephen", 12)
  val persons = List(john, audrey, stephen)
  val names30 = for (p <- persons if p.age >20) yield p.name
  println("Persons older than 20: " + names30)
  // Equivalent to:
  val names30b = persons filter (p => p.age > 20) map (p => p.name)
  println("Persons older than 20b: " + names30b)
  // In the for, {} may be used for multiple lines generator.
  
  var primePairs2 = for {
    i <- 1 until n
    j <- 1 until i
    if isPrime(i+j)
  } yield(i,j)
  println("Pares con suma primo: " + primePairs2)
  
  def scalarProduct3(xs: List[Double], ys: List[Double]): Double = 
    (for ( (x,y) <- xs zip ys) yield x*y).sum
 
  println("Scalar product3 x e y: " + scalarProduct2(x, y)) 
  
  // Combinatorial Search Example.
  // Set
  val fruits = Set("apple", "banana", "pear")
  val s2 = (1 to 6).toSet
  println("Sumados 2 : " + (s2 map (_ + 2)))
  println("3 está en s2: " + (s2 contains 3))

  // N-Queens.
  
  def queens(n:Int): Set[List[Int]] = {
    def isSafe(col: Int, queens: List[Int]): Boolean = {
      val row = queens.length
      val queensWithRow = (row - 1 to 0 by -1) zip queens
      queensWithRow forall {
        case (r,c) => col != c && math.abs(col - c) != row-r
      }
    }
    def placeQueens(k: Int) : Set[List[Int]] = 
      if (k == 0) Set(List())
      else 
        for {
          queens <- placeQueens(k-1)
          col <- 0 until n
          if isSafe(col, queens)
        } yield col :: queens
    placeQueens(n)
  }
  
  println("Soluciones para n=4: " + queens(4))
  
  def show(queens: List[Int]) = {
    val lines = 
      for (col <- queens.reverse)
        yield Vector.fill(queens.length)("* ").updated(col, "X ").mkString
    "\n" + (lines mkString "\n")
  }
  
  // Mi despliegue imperativo.
  for (qs <- queens(4)){
    println(show(qs))
  }
  
  for (qs <- queens(8) take 3){
    println(show(qs))
  }
  
  // Maps
  val romanNumerals = Map("I" -> 1, "V" -> 5, "X" -> 10)
  println("Map: " + romanNumerals)
  println("I vale: " + romanNumerals("I"))
  val iVal = romanNumerals get "I"
  val lVal = romanNumerals get "L"
  println("iVal: " + iVal)
  println("lVal: " + lVal)
  
  println("iVal vale " + (iVal match  {
    case None => "Nada"
    case Some(value) => value
  }))
  
  val fruits2 = List("apple", "banana", "pear", "peach", "pineapple")
  println("Frutas ordenadas por longitud: " + (fruits2 sortWith((x,y) => x.length < y.length))) 
  println("Frutas ordenadas lexicograficamente " + (fruits2 sorted))
  val frutasHeader = fruits2 groupBy { x => x head }
  println("Frutas Header: " + frutasHeader)
  
  class Poly(val terms: Map[Int, Double]){
    override def toString = 
       //(for ((exp,coeff) <- terms) yield coeff+"x^"+ exp) mkString " + "
     (for ((exp,coeff) <- terms.toList.sorted.reverse) yield coeff+"x^"+ exp) mkString " + "
     //def + (other: Poly) = new Poly(terms ++ other.terms) // No es lo correcto.
     def adjust(term: (Int,Double)):(Int,Double) = {
       val (exp, coeff) = term
       terms get exp match {
         case Some(coeff1) => exp -> (coeff + coeff1)
         case None => exp -> coeff
       }
     }
     def + (other: Poly) = new Poly(terms ++ (other.terms map adjust))
  }
  
  def p1 = new Poly(Map(0 -> 4.0, 1->2.0, 2->0.0, 3-> (-2.0)))
  def p2 = new Poly(Map(0 -> -5.0, 1->3.0, 2->1.0))
  println("Poly: " + p1)
  println("p1 + p2: " + (p1+p2))

  class Poly2(terms0: Map[Int, Double]){
    val terms = terms0 withDefaultValue 0.0
    override def toString = 
      (for ((exp,coeff) <- terms.toList.sorted.reverse) yield coeff+"x^"+ exp) mkString " + "
    def adjust(term: (Int, Double)): (Int, Double) = {
      val (exp,coeff) = term
      exp -> (coeff + terms(exp))
    }
    def + (other: Poly2) = new Poly2(terms ++ (other.terms map adjust))
  }

  def p3 = new Poly2(Map(0 -> 4.0, 1->2.0, 2->0.0, 3-> (-2.0)))
  def p4 = new Poly2(Map(0 -> -5.0, 1->3.0, 2->1.0))
  println("Poly: " + p3)
  println("p3 + p4: " + (p3+p4))
  
  class Poly3(terms0: Map[Int, Double]){
    def this(bindings: (Int, Double)*) = this(bindings toMap)
    val terms = terms0 withDefaultValue 0.0
    override def toString = 
      (for ((exp,coeff) <- terms.toList.sorted.reverse) yield coeff+"x^"+ exp) mkString " + "
    def adjust(term: (Int, Double)): (Int, Double) = {
      val (exp,coeff) = term
      exp -> (coeff + terms(exp))
    }
    def + (other: Poly3) = new Poly3(terms ++ (other.terms map adjust))
  }

  // No need to use the Map.
  def p5 = new Poly3(0 -> 4.0, 1->2.0, 2->0.0, 3-> (-2.0))
  def p6 = new Poly3(0 -> -5.0, 1->3.0, 2->1.0)
  println("Poly: " + p5)
  println("p5 + p6: " + (p5+p6))
  
  class Poly4(terms0: Map[Int, Double]){
    def this(bindings: (Int, Double)*) = this(bindings toMap)
    val terms = terms0 withDefaultValue 0.0
    override def toString = 
      (for ((exp,coeff) <- terms.toList.sorted.reverse) yield coeff+"x^"+ exp) mkString " + "
    def + (other: Poly4) = new Poly4((other.terms foldLeft terms)(addTerm))
    def addTerm(terms: Map[Int, Double], term: (Int, Double)): Map[Int, Double] = {
      val (exp, coeff) = term
      terms + (exp -> (coeff + terms(exp)))
    }
  }
  def p7 = new Poly4(0 -> 4.0, 1->2.0, 2->0.0, 3-> (-2.0))
  def p8 = new Poly4(0 -> -5.0, 1->3.0, 2->1.0)
  println("Poly: " + p7)
  println("p7 + p8: " + (p7+p8))
  

}