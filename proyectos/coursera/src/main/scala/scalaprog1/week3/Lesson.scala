package scalaprog1.week3

//import scalaprog1.week3.extras.Rational
import scalaprog1.week3.extras._
import scala.annotation.tailrec

object Lesson extends App{
  
  abstract class IntSet{
    def incl(x:Int):IntSet
    def contains(x:Int):Boolean
    
    def union(other: IntSet):IntSet
  }
  
  //val set = new IntSet; // No es posible.

  // Binary Search Tree implementation.

  //class Empty extends IntSet{
  object Empty extends IntSet{
    def contains(x:Int):Boolean = false
    def incl(x:Int):IntSet = new NonEmpty(x, Empty, Empty)
    override def toString = "."
    def union(other:IntSet):IntSet = other
  }
  // En vez de class Empty, se hace Object así es Singleton.
  
  class NonEmpty(elem:Int, left: IntSet, right: IntSet) extends IntSet {
    
    def contains(x:Int): Boolean = {
      //println("x: " + x) 
      //println("elem: " + elem)
      if (x < elem) left contains x
      else if (x > elem) right contains x
      else true      
    }
    
    def incl(x:Int):IntSet =
      if (x < elem) new NonEmpty(elem, left incl x, right)
      else if (x > elem) new NonEmpty(elem, left, right incl x)
      else this
      
    override def toString = "{"+left + elem + right + "}"
    
    def union(other:IntSet):IntSet =
      ((left union right) union other) incl elem 
  }
  // override cuando se redefine un método no abstracto.
  
  
  val set = new NonEmpty(3, Empty, Empty)
  val set2 = set incl 4
  println("set2 contiene 4 " + (set2 contains 4)) 
  println("set2 contiene 6 " + (set2 contains 6))
  println("set2 contiene 3 " + (set2 contains 3))
  println("set2: " + set2)

  val set3 = (Empty incl 10) incl 1
  println("Union: " + (set2 union set3))
  
  val r = new Rational(4,10)  
  
  trait Planar {
    def height: Int
    def width: Int
    def surface = height*width // Can have concrete methods.
  }
  // cannot have values parameters.

  // Exceptions and types Nothing.
  def error(msg: String) = throw new Error(msg)
  
  val x = null
  val y:String = null
  val z:String = x
  //val w:Int = null // Wrong!!
  val w = if(true) 1 else false
  // w is AnyVal.
  
  // Polymorphism.
  
  // Immutable linked list.
  
  trait List[T] {
    def isEmpty: Boolean
    def head: T
    def tail: List[T]
  }
  
  // val en parameters de Clases los hace
  // directamente field parameters
  class Cons[T](val head:T, val tail: List[T]) extends List[T] {
    def isEmpty = false
  }
  class Nil[T] extends List[T] {
    def isEmpty = true
    def head =  throw new NoSuchElementException("Nil.head")
    def tail = throw new NoSuchElementException("Nil.tail")
  }
  // head and tail tienen type Nothing en este caso, que es subtipo de toda clase.
  
  def singleton[T](elem:T) = new Cons[T](elem, new Nil[T])
  
  val s = singleton[Int](4)
  val s2 = singleton(5) // Scala can deduce the parameter.

  @tailrec
  def nth[T](n: Int, xs: List[T]):T = 
    if (n == 0) xs.head
    else nth(n-1, xs.tail)
    
  val list = new Cons(0, new Cons(1, new Cons(2, new Nil)))
  println("elemento 1 es " + nth(1,list))

  // With special range.
  @tailrec
  def nthE[T](n: Int, xs: List[T]):T = 
    if (xs.isEmpty) throw new IndexOutOfBoundsException
    else if (n == 0) xs.head
    else nthE(n-1, xs.tail)

  println("elemento 1 es " + nthE(1,list))

  
}
