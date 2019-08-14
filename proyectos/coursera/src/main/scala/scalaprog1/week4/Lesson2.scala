package scalaprog1.week4

object Lesson2 extends App {
  
  val fruits = List("apples", "oranges", "pears")
  val empty = List()
  val fruits2 = "apples"::"oranges"::"pears"::Nil 
  println("Frutas: " + fruits2)  
  println("Primera fruta " + fruits2.head)
  println("Primera fruta " + (fruits2 head))

  def insertionSort(xs: List[Int]):List[Int] = {
    def insert(x: Int, xs: List[Int]):List[Int] = xs match {
      case Nil => x::Nil
      case y::ys => if (x<=y) x::y::ys else y::(insert(x,ys))
    }
    xs match {
      case List() => List()
      case y::ys => insert(y, insertionSort(ys))
    }
  }
  // Two ways of doing pattern matching, by List() or by Nil.
  
  
  val l = 10::2::30::(-1)::4::Nil
  println("Ordenando " + l )
  println("Ordenado: " + insertionSort(l))
  
  
}