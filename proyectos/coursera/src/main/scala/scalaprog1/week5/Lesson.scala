package scalaprog1.week5

import scala.annotation.tailrec

object Lesson extends App {
 
  // More on Lists.
  
  @tailrec
  def last[T](xs:List[T]):T = xs match {
    case List() => throw new Error("last of empty list")
    case List(x) => x
    case y::ys => last(ys)
  }
  
  val l = 1::4::60::(-4)::90::Nil
  println("Last: " + last(l))
  
  def init[T](xs: List[T]): List[T] = xs match {
    case List() => throw new Error("init of empty list")
    case List(x) => List()  // Nil
    case y::ys => y::init(ys)
  }
  
  println("Init: " + init(l))
  
  def concat[T](xs:List[T], ys:List[T]):List[T] = xs match {
    case List() => ys
    case z::zs => z::concat(zs,ys)
  }
  
  val l2 = 100::40::Nil
  println("Concat: " + concat(l,l2))
  
  def reverse[T](xs: List[T]): List[T] = xs match {
    case List() => List()
    case y::ys => reverse(ys) ++ List(y) // concat
  }
  println("Reverse: " + reverse(l))
  // We could do better in performance.
  
  def removeAt[T](n:Int, xs: List[T]):List[T] = xs match {
    case List() => throw new Error("bad index")
    case y::ys => if (n==0) ys else y::removeAt(n-1, ys)
  }
  
  println("Remove second: " + removeAt(1,l))
  
  def flatten[T](xss: List[List[T]]):List[T] = xss match {
    case List() => List()
    case ys::yss => ys ++ flatten(yss)
  }
  
  println("Flatten " + flatten(List(List(4,1,5), List(6,10,11))))
  
  // Pairs and Tuples.
  
  def msort(xs:List[Int]):List[Int] = {
    //def merge(xs: List[Int], ys:List[Int]):List[Int] = ??? // Es valido el ???
    def merge(xs: List[Int], ys:List[Int]):List[Int] = xs match {
      case List() => ys
      case x::xs1 => ys match {
        case List() => xs
        case y::ys1 => if (x < y) x::merge(xs1, ys)
          else y::merge(xs, ys1)
      }
    }
    // Versión más prolija
    def merge2(xs:List[Int], ys:List[Int]):List[Int] = (xs,ys) match {
      case (Nil, ys) => ys
      case (xs, Nil) => xs
      case (x::xs1, y::ys1) => 
        if (x < y) x::merge2(xs1,ys) else y::merge2(xs,ys1)
    }
    val n = xs.length / 2
    if (n == 0) xs
    else {
      val (first, second) = xs splitAt n
      merge2(msort(first), msort(second))
    }
  }
  
  println("Sorted list: " + msort(l))
  
  // Implicit Parameters and Parameterization Techniques.
  
  // Podria haber sido un parámetro extra en vez de currificarlo.
  def mergeSort[T](xs:List[T])(lt: (T,T)=>Boolean):List[T] = {
    def merge2(xs:List[T], ys:List[T]):List[T] = (xs,ys) match {
      case (Nil, ys) => ys
      case (xs, Nil) => xs
      case (x::xs1, y::ys1) => 
        if (lt(x,y)) x::merge2(xs1,ys) else y::merge2(xs,ys1)
    }
    val n = xs.length / 2
    if (n == 0) xs
    else {
      val (first, second) = xs splitAt n
      merge2(mergeSort(first)(lt), mergeSort(second)(lt))
    }
  }

  val l3 = List(4.5, 0.2, -3.0, 50.6, 23.3)
  println("Sorted: " + mergeSort(l3)((x, y) => x<y)) 
  // No hace falta especificar el tipo en la función anónima,
  // lo puede inferir el Scala.

  def mergeSort2[T](xs:List[T])(ord: Ordering[T]):List[T] = {
    def merge2(xs:List[T], ys:List[T]):List[T] = (xs,ys) match {
      case (Nil, ys) => ys
      case (xs, Nil) => xs
      case (x::xs1, y::ys1) => 
        if (ord.lt(x,y)) x::merge2(xs1,ys) else y::merge2(xs,ys1)
    }
    val n = xs.length / 2
    if (n == 0) xs
    else {
      val (first, second) = xs splitAt n
      merge2(mergeSort2(first)(ord), mergeSort2(second)(ord))
    }
  }
  
  println("Sorted2: " + mergeSort2(l3)(Ordering.Double))

  // implicits
  def mergeSort3[T](xs:List[T])(implicit ord: Ordering[T]):List[T] = {
    def merge2(xs:List[T], ys:List[T]):List[T] = (xs,ys) match {
      case (Nil, ys) => ys
      case (xs, Nil) => xs
      case (x::xs1, y::ys1) => 
        if (ord.lt(x,y)) x::merge2(xs1,ys) else y::merge2(xs,ys1)
    }
    val n = xs.length / 2
    if (n == 0) xs
    else {
      val (first, second) = xs splitAt n
      merge2(mergeSort3(first), mergeSort3(second))
    }
  }
  
  println("Sorted3: " + mergeSort3(l3))
  
  // Higher-Order List Functions
  
  // Map
  def map[T,U](xs:List[T], f:T => U): List[U] = xs match {
    case Nil => Nil
    case y::ys => f(y)::map(ys,f)
  }
  println("L3 duplicado " + map(l3, (x:Double) => 2*x))
  println("L3 cuadrado " + map(l3, (x:Double) => x*x))
  println("L3 cuadrado con el map de Scala " + (l3 map (x => x*x)))
  
  // Filter
  def filter[T](xs: List[T], p:T => Boolean):List[T] = xs match {
    case Nil => Nil
    case y::ys => if (p(y)) y::filter(ys,p) else filter(ys,p)
  }
  
  println("L3 positivos " + filter(l3, (x:Double) => x>0))
  println("L3 positivos (Scala) " + (l3 filter ((x:Double) => x>0)))

  def pack[T](xs:List[T]):List[List[T]] = xs match {
    case Nil => Nil
    case x :: xs1 =>
      val (first, rest) = xs span(y => y == x)
      first :: pack(rest)
  }
  
  val l4 = List("a", "a", "a", "b", "c", "c")
  println("Pack de l4: " + pack(l4))

  def encode[T](xs:List[T]):List[(T,Int)] = 
    pack(xs) map (ys => (ys.head, ys.length))
  
  println("Encode de l4: " + encode(l4))
  
  // Reduction of Lists.
  // foldLeft
  def sum(xs: List[Double]) = (xs foldLeft 0.0)(_ + _)
  def product(xs: List[Double]) = (xs foldLeft 1.0)(_ * _)
  println("Suma de l3: " + sum(l3))
  println("Product de l3: " + product(l3))
  
  // Implementación local.
  def foldLeft[T,U](xs: List[T])(z: U)(op: (U,T) => U): U = xs match {
    case Nil => z
    case y::ys => op(foldLeft(ys)(z)(op), y)
  }
  
  def sum2(xs: List[Double]) = foldLeft(xs)(0.0)(_ + _)
  println("Suma2 de l3: " + sum2(l3))

  //def concat2[T](xs:List[T], ys:List[T]):List[T] =
  //  foldLeft(ys)(xs)((zs,y) =>
  
  // Parecido con reduceRight y foldRight.
 
  def concat2[T](xs:List[T], ys:List[T]):List[T] =
    (xs foldRight ys)(_ :: _)


}

