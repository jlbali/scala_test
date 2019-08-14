package scalaprog2.week1

object Recap extends App {
  
  // Functions and Pattern Matching
  
  abstract class JSON
  case class JSeq(elems: List[JSON]) extends JSON
  case class JObj(bindings: Map[String, JSON]) extends JSON
  case class JNum(num: Double) extends JSON
  case class JStr(str: String) extends JSON
  case class JBool(b: Boolean) extends JSON
  case object JNull extends JSON
  
  val john = JObj(Map(
    "firstName" -> JStr("John"),
    "lastName" -> JStr("Smith"),
    "address" -> JObj(Map(
      "state" -> JStr("NY"),
      "postalCode" -> JNum(10021)
    )),
    "phoneNumbers" -> JSeq(List(
       JObj(Map("number" -> JStr("433453"))),
       JObj(Map("number" -> JStr("43343783")))
    ))
  ))
  println("Json john: " + john)
  
  def show(json: JSON):String = json match {
    case JSeq(elems) =>
      "[" + (elems map show mkString ", ")+ "]"
    case JObj(bindings) =>
      val assocs = bindings map {
        case (key, value) => "\"" + key + "\": " + show(value)  
      }
      "{" + (assocs mkString ", ") + "}"
    case JNum(num) => num.toString
    case JStr(str) => "\"" + str + "\""
    case JBool(b)  => b.toString
    case JNull     => "null"
  }
  
  println("John: " + show(john))
  val f: String => String = {case "ping" => "pong"}
  println("f evaluada: " + f("ping"))
  //println("f evaluada: " + f("abc"))
  val f2: PartialFunction[String, String] = {case "ping" => "pong"}
  println("Defined at 'abc' " + (f2 isDefinedAt("abc"))) 
  
}