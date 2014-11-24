/* Name: Sonia Pitrola
 * Class: Comp 2503
 * Teacher: Charles Hepler
 * Assignment 1 
 * 
 * Purpose:
 * The purpose of the Neuron System class is to set up ArrayLists and do the calculation for 
 * phase one and phase two. That does the testing and takes the error values and trains the 
 * weights accordingly to solve the problem of converting the polar coordinates to x and y 
 * coordinates. There are two parts Training and Testing that help with the process of solving 
 * this problem. Testing is where it takes the inputs and random weight values and calculates a 
 * value. After thats calculated it adds up all the values and generates a medial neuron-in calculation and normalizes it
 * to get the medial neuron-out number. The medial neruon-out number is then multiplied by the weights of x and y then added together. 
 * Then it takes the actual answer for coordinate x and y and subracts them from the error. Now we enter the training. Where 
 * it does all this in reverse. 
 */
import java.util.*;
public class NeuronSystem
{
    Random rand = new Random();
    //ArrayList for synapse out for inputs: Radius(R), Theta(T) and K
    private ArrayList<Double> synapseOutR = new ArrayList<Double>();
    private ArrayList<Double> synapseOutT = new ArrayList<Double>();
    private ArrayList<Double> synapseOutK = new ArrayList<Double>();

    //ArrayList for input weight for: Radius(R), Theta(T) and K
    private ArrayList<Double> weightR = new ArrayList<Double>();
    private ArrayList<Double> weightT = new ArrayList<Double>();
    private ArrayList<Double> weightK = new ArrayList<Double>();

    //ArrayList for synapse in: Coordinate X and Coordinate Y
    private ArrayList<Double> synapseInX = new ArrayList<Double>();
    private ArrayList<Double> synapseInY = new ArrayList<Double>();

    //ArrayList for Coordinate X and Y
    private ArrayList<Double> weightX = new ArrayList<Double>();
    private ArrayList<Double> weightY = new ArrayList<Double>();

    //ArrayList for Medial Neuron In and Out 
    private ArrayList<Double> medialNeuronIn = new ArrayList<Double>();
    private ArrayList<Double> medialNeuronOut = new ArrayList<Double>();

    //Variables set as constants
    public static final double K = 1.0;
    public static final double MAX_RANDOMR = 1.0;
    public static final double MAX_RANDOMT = 2.0*Math.PI;
    public static final int MAX_MEDIAL = 40;
    public static final double SCALE = 1.0;
    public static final double RATE = 0.01;

    //Declaired Variables
    private double inputR;
    private double inputT;
    private double inputK;
    private double xError;
    private double yError;

    //The math calculation for radius input 
    public double randomForInputR (){
        return rand.nextDouble()*MAX_RANDOMR;
    }
    //The math calculation for theta input 
    public double randomForInputT (){
        return rand.nextDouble()*MAX_RANDOMT;
    }
    //The math calculation for synapse weight
    public double randomSynapseWeight(){ 
        return 0.1 * rand.nextDouble();
    }
    //Generates the random inputs for R, T and K
    public void generateRandomInputs(){
        inputR = randomForInputR(); 
        inputT = randomForInputT();
        inputK = K;//set as constant 
    }
    //Generates the random weight for the synapses for R, T, K, X and Y
    public void generateRandomSynapsesWeight(){
        for (int i = 0; i < MAX_MEDIAL; i++){//for each medial neuron
            weightR.add(randomSynapseWeight());
            weightT.add(randomSynapseWeight());      
            weightK.add(randomSynapseWeight());
            weightX.add(randomSynapseWeight());
            weightY.add(randomSynapseWeight());
        }
    }

    //Calculates the synapse input multiplied by weight and saves it in the SynapseOut arrayList for the inputs
    //Calculates medial neuron in by adding the SynapseOut arrayList for the inputs at the given indexes 
    //Calculates medial neuron out by using the math fuction arcTan
    public void synapseCalculationTesting(){   
        //clearing memory for SynapseR, SynapseT, SynapseK, medialNeuronIn, medialNeuronOut
        synapseOutR.clear ();
        synapseOutT.clear ();
        synapseOutK.clear ();
        medialNeuronIn.clear();
        medialNeuronOut.clear();

        for (int i = 0; i < MAX_MEDIAL; i++){//for each medial neuron 
            double synapseR = inputR * weightR.get(i);
            double synapseT = inputT * weightT.get(i);  
            double synapseK = inputK * weightK.get(i);
            synapseOutR.add(synapseR);
            synapseOutT.add(synapseT);
            synapseOutK.add(synapseK);
            double medNeuronIn = (synapseOutR.get(i)) + (synapseOutT.get(i)) + (synapseOutK.get(i));
            medialNeuronIn.add(medNeuronIn);
            double medNeuronOut = Math.atan(SCALE * (medialNeuronIn.get(i)));
            medialNeuronOut.add(medNeuronOut);  
        }
        coordinatesCalculationTesting();
    }

    //Calculates the coordinate x as medial neuron out times the weight for x. Then sums up coordinate x
    //Calculates the coordinate x as medial neuron out times the weight for y. Then sums up coordinate y
    //Calculates the actual x and y
    //Calculates the x and y error
    public void coordinatesCalculationTesting(){
        double x = 0;
        double y = 0;
        synapseInX.clear();
        synapseInY.clear();
        for( int cX = 0; cX < MAX_MEDIAL; cX++){//for each medial neuron calculates for coordinate x 
            x += (medialNeuronOut.get(cX))* (weightX.get(cX));
            synapseInX.add(x);      
        }   

        for( int cY = 0; cY < MAX_MEDIAL; cY++){//for each medial neuron calculates for coordinate y
            y += (medialNeuronOut.get(cY))* (weightY.get(cY));
            synapseInY.add(y);  
        }
        double actualX = inputR * Math.cos(inputT);
        double actualY = inputR * Math.sin(inputT);
        xError = actualX - x;
        yError = actualY - y; 
    }

    //Calculating error 
    public double error(){
        return (Math.sqrt((xError * xError) + (yError * yError) ));    
    }

    //Calculating the new weights of the coordinates 
    public void trainingNewWeightsCoordinates(){
        for(int x = 0; x < MAX_MEDIAL; x++){//go through all the medial neurons
            double changeInX = (RATE * xError * medialNeuronOut.get(x))+ weightX.get(x);
            weightX.set(x, changeInX);//setting the index position with the new value 
        }
        for(int y = 0; y < MAX_MEDIAL; y++){//go through all the medial neurons
            double changeInY = (RATE * yError * medialNeuronOut.get(y)) + weightY.get(y);
            weightY.set(y, changeInY);//setting the index position with the new value
        }
    }

    //Calculating the new synapse weights for the inputs: R, T and K
    public void trainingNewSynapseWeights(){
        ArrayList<Double> sigma = new ArrayList<Double>();   // need these temporary values
        ArrayList<Double> sigmoid = new ArrayList<Double>(); // and these too

        // stage one loop to find sigma and sigmoid
        for(int j = 0; j < MAX_MEDIAL; j++){//go through all the medial neurons
            sigma.add(weightX.get(j) * xError +  weightY.get(j) * yError);
            sigmoid.add(1.0 / (1.0 + ( medialNeuronIn.get(j) * medialNeuronIn.get(j) ) ) );
        }

        // stage two another loop using sigma and sigmoid to find new weightR,T,K    
        for(int i = 0; i < MAX_MEDIAL; i++){//go through all the medial neurons
            //calculating the new synapse weight for Inputs: R, T and K 
            //setting the index positions with the new value
            weightR.set(i, RATE * sigmoid.get(i) * sigma.get(i) * inputR + weightR.get(i));
            weightT.set(i, RATE * sigmoid.get(i) * sigma.get(i) * inputT + weightT.get(i));
            weightK.set(i, RATE * sigmoid.get(i) * sigma.get(i) * inputK + weightK.get(i));
        }

    }
}

