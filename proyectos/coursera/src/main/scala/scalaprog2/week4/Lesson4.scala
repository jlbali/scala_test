package scalaprog2.week4

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global;

object Lesson4 extends App {
  
  // Seguir con los videos de ScalaProg2 Week 4.
  
  trait Socket {
    def readFromMemory(): Future[Array[Byte]]
    def sendToEurope(packet: Array[Byte]): Future[Array[Byte]]
    
  }

  class SocketString{
    def readFromMemory(): Future[String] =
      Future {
        Thread.sleep(2000)
        println("Esperado...")
        "Terminado de leer de memoria"
      }
  }

}