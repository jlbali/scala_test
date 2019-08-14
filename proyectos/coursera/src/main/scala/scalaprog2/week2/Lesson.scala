package scalaprog2.week2

object Lesson extends App {
  
  // Streams
  
  def isPrime(n: Int): Boolean =
    (2 until n) forall(d => n % d != 0)
  
  // Second Prime from 1000 to 10000
  val secondPrime = ((1000 to 10000) filter isPrime)(1)
  println("Second prime is: " + secondPrime)
  // More efficient, with streams.
  val xs = Stream.cons(1, Stream.cons(2, Stream.cons(3, Stream.empty)))
  val ys = Stream(1,2,3)
  val zs = (1 to 1000).toStream
  println("Stream: " + ys)
  println("Stream: " + zs)
  
  def streamRange(low: Int, high: Int):Stream[Int] =
    if (low >= high) Stream.empty
    else Stream.cons(low, streamRange(low+1,high))
  
  println("Stream Range: " + streamRange(400,900))
  
  val secondPrimeEff = ((1000 to 10000).toStream filter isPrime)(1)
  println("Second Prime: " + secondPrimeEff)
  
  // x::xs siempre devuelve una lista.
  // x #:: xs es un Stream.
  
  // Lazy Evaluation.

  def expr = {
    val x = {print("x"); 1}
    lazy val y = {print("y"); 2}
    def z = {print("z"); 3}
    z + y + x + z + y + x
  }
  expr
  println("")
  // Infinite Sequences.
  
  def from(n:Int):Stream[Int] = Stream.cons(n, from(n+1))
  
  println("Desde 5 al infinito " + from(5))
  
  def pares = from(0) filter ((x:Int) => x % 2 == 0)

  println("Pares: " + pares)
  println("Septimo par: " + pares(6))
  println("20 primeros pares: " + (pares take 20).toList)
  
  def primos = from(2) filter isPrime
  println("20 primeros primos: " + (primos take 20).toList)
  
  // Sieve of Erathostenes.
  def sieve(s: Stream[Int]):Stream[Int] =
    s.head #:: sieve(s.tail filter (_ % s.head != 0))
  
  def primos2 = sieve(from(2))
  println("20 primeros primos: " + (primos2 take 20).toList)
  
  // Stream para aproximar raices.
  def sqrtStream(x: Double): Stream[Double]={
    def improve(guess: Double) = (guess + x/guess)/2
    lazy val guesses: Stream[Double] = 1 #:: (guesses map improve)
    guesses
  }
  
  println("sucesion de raiz de 2: " + (sqrtStream(2.0) take 5).toList)
  
  def isGoodEnough(guess: Double, x:Double) =
    math.abs((guess*guess-x)/x) < 0.0001
  
  println("Sucesion de raiz de 2: " + (sqrtStream(4) filter (isGoodEnough(_,2)))) 
  // Alguien está convirtiéndolo en una lista...
  
  // 2 formas de obtener multiplos de 4.
  val multiples4 = from(0) filter ((x:Int) => x % 4 == 0)
  val multiples4b = from(0) map ((x:Int) => 4*x) // Es más rápido...
  
  
  
  
}