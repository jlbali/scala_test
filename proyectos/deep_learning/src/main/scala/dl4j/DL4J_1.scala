
// https://deeplearning4j.org/tutorials/00-quickstart-for-deeplearning4j
// Ejemplo de Red Neuronal para MNIST data set.

package dl4j

import scala.collection.JavaConversions._

import org.deeplearning4j.datasets.iterator._
import org.deeplearning4j.datasets.iterator.impl._
import org.deeplearning4j.nn.api._
import org.deeplearning4j.nn.multilayer._
import org.deeplearning4j.nn.graph._
import org.deeplearning4j.nn.conf._
import org.deeplearning4j.nn.conf.inputs._
import org.deeplearning4j.nn.conf.layers._
import org.deeplearning4j.nn.weights._
import org.deeplearning4j.optimize.listeners._
import org.deeplearning4j.datasets.datavec.RecordReaderMultiDataSetIterator
import org.deeplearning4j.eval.Evaluation

import org.nd4j.linalg.learning.config._ // for different updaters like Adam, Nesterovs, etc.
import org.nd4j.linalg.activations.Activation // defines different activation functions like RELU, SOFTMAX, etc.
import org.nd4j.linalg.lossfunctions.LossFunctions // mean squared error, multiclass cross entropy, etc.

import org.deeplearning4j.datasets.iterator.impl.EmnistDataSetIterator

object DL4J_1 {

    println("Ejemplo de MNIST data set")
    val batchSize = 16 // how many examples to simultaneously train in the network
    val emnistSet = EmnistDataSetIterator.Set.BALANCED
    val emnistTrain = new EmnistDataSetIterator(emnistSet, batchSize, true)
    val emnistTest = new EmnistDataSetIterator(emnistSet, batchSize, false)

    val outputNum = EmnistDataSetIterator.numLabels(emnistSet) // total output classes
    val rngSeed = 123 // integer for reproducability of a random number generator
    val numRows = 28 // number of "pixel rows" in an mnist digit
    val numColumns = 28

    val conf = new NeuralNetConfiguration.Builder()
    conf.seed(rngSeed)
    conf.optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
    conf.updater(new Adam)
    conf.l2(1e-4)
    val inputLayer = new DenseLayer.Builder()
        .nIn(numRows * numColumns) // Number of input datapoints.
        .nOut(1000) // Number of output datapoints.
        .activation(Activation.RELU) // Activation function.
        .weightInit(WeightInit.XAVIER) // Weight initialization.
        .build()
    val outputLayer = new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
        .nIn(1000)
        .nOut(outputNum)
        .activation(Activation.SOFTMAX)
        .weightInit(WeightInit.XAVIER)
        .build()
    conf.list().layer(inputLayer).layer(outputLayer)
    conf.build()
    // No están los métodos pretrain y backprop.

    // create the MLN
    //val network = new MultiLayerNetwork(conf.asInstanceOf[MultiLayerConfiguration])
    //network.init()
    // Problemas de conversion.


}
