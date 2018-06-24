/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ann;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Roshaann 2.7 gpa
 */
public class ANN {

    private static NeuralNetwork network;
    private static ArrayList<Node> inputLayer;
    private static ArrayList<Node> hiddenLayer;
    private static ArrayList<Node> outputLayer;
    private static ArrayList<String> inputFile;
    private static double epochError = 0;
    private static boolean isFirstEpoch = true;

    public static void main(String[] args) {
        // TODO code application logic here

        inputFile = new ArrayList<>();
        network = new NeuralNetwork();

        readFile("AnnValues.txt");

        //initlaizing network randomly
        initializeNetwork();

        double j = 1.0;
        //epoch
        while (true) {

            //setting learning rate
            network.setLearningRate(1.0 / (j + 1.0));

            //for each tupple
            for (int i = 0; i < inputFile.size(); i++) {
                String values[] = inputFile.get(i).split("\\s+");

                //setting input
                network.inputLayer.get(0).addAttribute(Double.parseDouble(values[0]));
                network.inputLayer.get(0).setOutput(Double.parseDouble(values[0]));

                network.inputLayer.get(1).addAttribute(Double.parseDouble(values[1]));
                network.inputLayer.get(1).setOutput(Double.parseDouble(values[1]));

                network.inputLayer.get(2).addAttribute(Double.parseDouble(values[2]));
                network.inputLayer.get(2).setOutput(Double.parseDouble(values[2]));

                while (true) {
                    //calculating output
                    network.calculateNodesOutput(network.inputLayer, network.hiddenLayer);
                    network.calculateNodesOutput(network.hiddenLayer, network.outputLayer);

                    //calculating error
                    network.calculateOutputLayerError(Double.parseDouble(values[3]), network.outputLayer.get(0).getOutput());

                    if (Math.abs(network.getError()) <= 0.005) {

                        //saving error of last tuple
                        if (i == inputFile.size() - 1) {
//                          save epoch error only if it is first epoch 
                            if (epochError == 0) {
                                epochError = network.getError();
                            }
                        }
                        break;

                    } else {
                        // do bacckpropagation
                        network.calculateHiddenLayerErrorsAndUpdateWeights();
                        network.calculateInputLayerErrorsAndUpdateWeights();
                    }

                }
            }
            System.out.println("Epoch " + j + "\n" + network.toString());

            System.out.println("previous " + epochError + " new " + network.getError());
            if (epochError - network.getError() <= 0.001 && !isFirstEpoch) {
                break;
            }
            else{
                epochError=network.getError();
            }
            isFirstEpoch = false;

            j++;
        }

    }

    private static void readFile(String fileName) {

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader
                    = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader
                    = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {

                String values[] = line.split("\\s+");
                inputFile.add(line);

//              addNetwork(values[0],values[1],values[2],values[3]);
            }

            // Always close files.
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '"
                    + fileName + "'");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '"
                    + fileName + "'");
            // Or we could just do this: 
            // ex.printStackTrace();
        }
    }

    private static void initializeNetwork() {

        inputLayer = new ArrayList<>();
        hiddenLayer = new ArrayList<>();
        outputLayer = new ArrayList<>();

        //creating initial nodes  
        Node node1 = new Node();
        Node node2 = new Node();
        Node node3 = new Node();

        //nodes added in input layer 
        inputLayer.add(node1);
        inputLayer.add(node2);
        inputLayer.add(node3);

        //setting hidden layer
        Node node4 = new Node();
        node4.setBias(Mathematics.getRandom(-1.0, 1.0));
        Node node5 = new Node();
        node5.setBias(Mathematics.getRandom(-1.0, 1.0));

        hiddenLayer.add(node4);
        hiddenLayer.add(node5);

        //setting output  layer
        Node outputNode = new Node();
        outputNode.setBias(Mathematics.getRandom(-1.0, 1.0));

        outputLayer.add(outputNode);

        network.setInputLayer(inputLayer);
        network.setHiddenLayer(hiddenLayer);
        network.setOutputLayer(outputLayer);

        //initializing hidden layer nodes weights
        network.initializeNodesWeights(inputLayer, hiddenLayer);

        //calculating output layer nodes wieghts
        network.initializeNodesWeights(hiddenLayer, outputLayer);

    }

}

class NeuralNetwork {

    private double learningRate;
    private double error;
    ArrayList<Node> inputLayer;
    ArrayList<Node> hiddenLayer;
    ArrayList<Node> outputLayer;

    public NeuralNetwork() {

    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }

    public double getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    //only for hidden and output layers
    public void initializeNodesWeights(ArrayList<Node> previousLayer, ArrayList<Node> currentLayer) {

        for (int i = 0; i < currentLayer.size(); i++) {

            for (int j = 0; j < previousLayer.size(); j++) {

                //setting hidden layer weight
                currentLayer.get(i).getInCommingWeights().add(Mathematics.getRandom(-1.0, 1.0));
            }
        }
    }

    public void calculateNodesOutput(ArrayList<Node> previousLayer, ArrayList<Node> currentLayer) {

        //for each current layer node
        for (int i = 0; i < currentLayer.size(); i++) {

            double x = 0;
            for (int j = 0; j < previousLayer.size(); j++) {
                //bias
                x = x + previousLayer.get(j).getOutput() * currentLayer.get(i).getInCommingWeights().get(j) + currentLayer.get(i).getBias();

//               double output=1/(1+(Math.exp(-x)));
            }
            // output= 1/ 1 + e^ -x
            currentLayer.get(i).setOutput(1 / (1 + (Math.
                    exp(-(x + currentLayer.get(i).getBias())))));

        }
    }

    public void calculateOutputLayerError(double targetOutput, double networkOutput) {

//        System.out.println(targetOutput + " " + networkOutput);
        double error = networkOutput * (1 - networkOutput) * (targetOutput - networkOutput);
//        System.out.println(error);
        this.outputLayer.get(0).setError(error);
        this.setError(error);
    }

    //for input and hidden layers
    public void calculateHiddenLayerErrorsAndUpdateWeights() {

        Node outputNode = outputLayer.get(0);

        //for each hidden layer node
        for (int i = 0; i < hiddenLayer.size(); i++) {

            Node hiddenNode = hiddenLayer.get(i);

            hiddenNode.setError(hiddenNode.getOutput() * (1 - hiddenNode.getOutput())
                    * outputNode.getError() * outputNode.getInCommingWeights().get(i));

            double w = (this.learningRate) * outputLayer.get(0).getError() * hiddenNode.getOutput();
            //updating weights
            outputNode.getInCommingWeights().set(i, outputNode.getInCommingWeights().get(i) + w);

            //updating bias
            double deltaTheta = this.learningRate * outputLayer.get(0).getError();
            outputNode.setBias(outputNode.getBias() + deltaTheta);

        }
    }

    public void calculateInputLayerErrorsAndUpdateWeights() {

        for (int i = 0; i < inputLayer.size(); i++) {

            Node inputNode = inputLayer.get(i);

            double errorJK = 0;

            for (int j = 0; j < hiddenLayer.size(); j++) {

                errorJK += hiddenLayer.get(j).getError() * hiddenLayer.get(j).getInCommingWeights().get(i);
            }

            inputNode.setError(inputNode.getOutput() * (1 - inputNode.getOutput()) * errorJK);

            //updating weights
            for (int k = 0; k < hiddenLayer.size(); k++) {
                double w = (this.learningRate) * hiddenLayer.get(k).getError() * inputNode.getOutput();

                hiddenLayer.get(k).getInCommingWeights().set(i, hiddenLayer.get(k).getInCommingWeights().get(i) + w);

//                System.out.println("han");
                //updating bias
                double deltaTheta = this.learningRate * hiddenLayer.get(0).getError();
                hiddenLayer.get(k).setBias(hiddenLayer.get(k).getBias() + deltaTheta);
            }
        }
    }

    public ArrayList<Node> getInputLayer() {
        return inputLayer;
    }

    public void setInputLayer(ArrayList<Node> inputLayer) {
        this.inputLayer = inputLayer;
    }

    public ArrayList<Node> getHiddenLayer() {
        return hiddenLayer;
    }

    public void setHiddenLayer(ArrayList<Node> hiddenLayer) {
        this.hiddenLayer = hiddenLayer;
    }

    public ArrayList<Node> getOutputLayer() {
        return outputLayer;
    }

    public void setOutputLayer(ArrayList<Node> outputLayer) {
        this.outputLayer = outputLayer;
    }

    public String toString() {

        return "w14 " + hiddenLayer.get(0).getInCommingWeights().get(0) + " \n"
                + "w24 " + hiddenLayer.get(0).getInCommingWeights().get(1) + " \n"
                + "w34 " + hiddenLayer.get(0).getInCommingWeights().get(2) + " \n"
                + "w15 " + hiddenLayer.get(1).getInCommingWeights().get(0) + " \n"
                + "w25 " + hiddenLayer.get(1).getInCommingWeights().get(1) + " \n"
                + "w35 " + hiddenLayer.get(1).getInCommingWeights().get(2) + " \n"
                + "w46 " + outputLayer.get(0).getInCommingWeights().get(0) + " \n"
                + "w56 " + outputLayer.get(0).getInCommingWeights().get(1) + " \n"
                + "bias 4 " + hiddenLayer.get(0).getBias() + "\n"
                + "bias 5 " + hiddenLayer.get(1).getBias() + "\n"
                + "bias 6 " + outputLayer.get(0).getBias() + "\n"
                + "Learning Rate " + this.getLearningRate() + "\n"
                + "Output " + outputLayer.get(0).getOutput() + "\n"
                + "Target Output 0.6002295" + " \n"
                + "Error " + this.getError();
    }
}

//initial values will also be stored in inComming wights array
class Node {

    double output;
    double error;
    double bias;
    ArrayList<Double> inCommingWeights;

    public Node() {
        this.inCommingWeights = new ArrayList<>();
    }

    public double getOutput() {
        return output;
    }

    public void setOutput(double output) {
        this.output = output;
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }

    public double getBias() {
        return bias;
    }

    public void setBias(double bias) {
        this.bias = bias;
    }

    public ArrayList<Double> getInCommingWeights() {
        return inCommingWeights;
    }

    public void setInCommingWeights(ArrayList<Double> inCommingWeights) {
        this.inCommingWeights = inCommingWeights;
    }

//    public void addAttribute(int inCommingIndex, double weight){
//        this.inCommingWeights.add(new Weights(inCommingIndex,weight));
//    }
    public void addAttribute(double weight) {
        this.inCommingWeights.add(weight);
    }

    public void addHiddenLayerNodeWeight() {

    }

    public void calculateHiddenNodeInput() {

    }

}

class Mathematics {

    public static DecimalFormat f = new DecimalFormat("##.000");
    static Random random = null;

    public Mathematics() {
    }

    public static Random getInstance() {

        if (random == null) {
            random = new Random(System.currentTimeMillis());
        }

        return random;
    }

    //get double random number between 0.0(inclusive) and 1.0 (exclusive)
    public static double getRandom() {

        return Mathematics.roundDoubleTo3DecimalPosition(Mathematics.getInstance().nextDouble());
    }

    //get int random in min max range
    public static int getRandom(int min, int max) {

        return Mathematics.getInstance().nextInt(max + 1 - min) + min;
    }

    //get double random in min max range 
    public static double getRandom(double min, double max) {

        return min + (max - min) * Mathematics.getInstance().nextDouble();

    }

    public static double roundDoubleTo3DecimalPosition(Double d) {
        return Double.parseDouble(f.format(d));
    }
}
