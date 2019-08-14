package scalaprog2.week1

object Lesson extends App {
  
  case class Book(title: String, authors: List[String])
  
  val books: List[Book] = List(
    Book(title="Bla Bla",
         authors=List("Abelson, Harald", "Sussman, Gerald")),
    Book(title="Blas Blas",
         authors=List("Bird, Richard", "Wadler, Phil")),
    Book(title="Effective Java",
         authors=List("Bloch, Joshua")),
    Book(title="Bom Bom",
         authors=List("Bloch, Joshua", "Gafter, Neal")),
    Book(title="Scala",
         authors=List("Odersky, Martin", "Spoon, Lex", "Benners, Bill")),
  )
  // Con case classes no hace falta el new.
  // Titulos que tienen a "Bird" como autor.
  val titles = for (
      b <- books;
      a <- b.authors
      if a startsWith "Bird,")
    yield b.title
  println("Titles: " + titles)
  // Titulos de libros con "Bla"
  val titles2 = for (
      b <- books
      if (b.title indexOf "Bla") >= 0)
      yield b.title
  println("Titles2: " + titles2)
  // Autores que escribieron al menos dos libros.
  val titles3 = for {
    b1 <- books
    b2 <- books
    if b1 != b2
    a1 <- b1.authors
    a2 <- b2.authors
    if a1 == a2
  } yield a1
  println("Titles3: " + titles3)
  // Aparecen repetidos los autores...
  val titles4 = for {
    b1 <- books
    b2 <- books
    if b1.title < b2.title
    a1 <- b1.authors
    a2 <- b2.authors
    if a1 == a2
  } yield a1
  println("Titles4: " + titles4)
  // Si un autor aparece en tres libros, aparece en tres veces.
  val titles5 = { for {
    b1 <- books
    b2 <- books
    if b1.title < b2.title
    a1 <- b1.authors
    a2 <- b2.authors
    if a1 == a2
  } yield a1 }.distinct
  println("Titles5: " + titles5)
  // Otra alternativa, usar Set en vez de List.
  val booksSet = books toSet
  val titles6 = for {
    b1 <- booksSet
    b2 <- booksSet
    if b1.title < b2.title
    a1 <- b1.authors
    a2 <- b2.authors
    if a1 == a2
  } yield a1
  println("Titles6: " + titles6)
  
  // Functional Random Generators.
  
  trait Generator[+T] {
    def generate: T
  }
  
  val integers = new Generator[Int]{
    val rand = new java.util.Random
    def generate = rand.nextInt()
  }
  
  val booleans = new Generator[Boolean]{
    def generate = integers.generate > 0
  }
  
  val resBooleans = for (i <- 1 to 10) yield booleans.generate
  println("Booleans " + (resBooleans mkString ","))

  val pairs = new Generator[(Int,Int)]{
    def generate = (integers.generate, integers.generate)
  }
  
  // More "convenient" version.
  trait Generator2[+T] {
    self =>   // Alias for "this".
    // val self = this // is that the same?
    def generate: T
    def map[S](f: T => S): Generator2[S] = new Generator2[S] {
      def generate = f(self.generate)
    }
    def flatMap[S](f: T => Generator[S]): Generator[S] = new Generator[S] {
      def generate = f(self.generate).generate
    }
    
  }
  val integersGenerator = new Generator2[Int]{
    val rand = new java.util.Random
    def generate = rand.nextInt()
  }

  // Another boolean generator, using the previous.
  val booleansGenerator = for (x <- integersGenerator) yield x > 0

  val resBooleans2 = for (i <- 1 to 10) yield booleansGenerator.generate
  println("Booleans " + (resBooleans2 mkString ","))
  
  // Constant "random" number generator.
  def single[T](x:T): Generator2[T] = new Generator2[T]{
    def generate = x
  }
  
  def choose(low: Int, high: Int): Generator2[Int] =
    for (x <- integersGenerator) yield (low + x % (high-low))
  
  def oneOf[T](xs: T*): Generator2[T] =
    for (idx <- choose(0, xs.length)) yield xs(idx)
  
  val numbGen = oneOf(1,5,10)
  // Estos tiran una excepcion...
  //val numbers = for (i <- 1 to 10)
  //  yield numbGen.generate
  //println("Numbers elegidos "+numbers)
  // Medio confuso este modelo...
  
  trait Tree
  case class Inner(left: Tree, right:Tree) extends Tree
  case class Leaf(x:Int) extends Tree
  // Medio lío lo que sigue con tree...
  
  
  // Random Test Function
  def test[T](g: Generator[T], numTimes: Int=100)
    (testF: T => Boolean): Unit ={
    for (i <- 0 until numTimes){
      val value = g.generate
      assert(testF(value), "test failed for " + value)
    }
    println("passed " + numTimes + " tests.")
  }
  
  // Monads
  
  
}
