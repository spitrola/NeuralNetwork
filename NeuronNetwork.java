/* Name: Sonia Pitrola
 * Class: Comp 2503
 * Teacher: Charles Hepler
 * Assignment 1 
 * 
 * Purpose:
 * The purpose of this assignment is to create a Neural Network. That takes in inputs and calculates the 
 * the error to see how far off it is from the real answer. Then go back words to change the weight values 
 * to get a more accurate answer. 
 */
import java.util.*;
public class NeuronNetwork
{
    public static void main(String args[])
    {
        NeuronSystem neuron;
        double total;
        neuron = new NeuronSystem ();//creates new memory 
        for(int j = 1000; j <= 40000; j+=1000){//going through j times 
            neuron.generateRandomSynapsesWeight();//generates random synapse weight values

            // have an untrained net
            for(int i = 0; i < j; i++){
                //System.out.println(String.format("i:%d ==> j:%d\n",i,j));
                neuron.generateRandomInputs();//generates random input values
                neuron.synapseCalculationTesting();// runs one full cycle of the neural net
                neuron.trainingNewWeightsCoordinates();// backpropagation second set of neurons
                neuron.trainingNewSynapseWeights(); // backpropagation 1st set of neurons
            }

            total = 0;
            // have a trained net
            for(int k = 0; k < 500; k++){
                neuron.generateRandomInputs();//generates random input values
                neuron.synapseCalculationTesting();// runs one full cycle of the neural net
                total += neuron.error(); 
            }
            System.out.println(j + "," +(total/500.0));
        }
    }
}