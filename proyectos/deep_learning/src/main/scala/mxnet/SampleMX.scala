package mxnet

import org.apache.mxnet._

object SampleMX {

    println("MXNet samples")

    def test1 = {


        println("Tensor examples")
        val arr = NDArray.ones(2, 3)
        // arr: org.apache.mxnet.NDArray = org.apache.mxnet.NDArray@f5e74790

        println(arr.shape)
        // org.apache.mxnet.Shape = (2,3)

        println((arr * 2).toArray.mkString(","))
        // Array[Float] = Array(2.0, 2.0, 2.0, 2.0, 2.0, 2.0)

        println((arr * 2).shape)
        // org.apache.mxnet.Shape = (2,3)
        //arr(0,2) = 4.0

        println(arr)
    }


    def test2 = {
        import org.apache.mxnet._
        println("Tensor examples 2")
        val arr = NDArray.ones(2, 3)
        println(arr)
        
    }


    //test1
    test2

}