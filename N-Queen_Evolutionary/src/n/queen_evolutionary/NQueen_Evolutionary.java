/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package n.queen_evolutionary;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Roshaan
 */
public class NQueen_Evolutionary {

    public static final int generations = 50;
    public static final int n = 25;
    public static final int m = 20;
    public static ChessBoard[] population;
    public static ChessBoard[] children;
    public static ChessBoard[] totalPopulation = new ChessBoard[m + n];
    public static ChessBoard[] parents = new ChessBoard[2];
    public static boolean stop = false;

    public static final int FITNESS_PROPOTION = 0;
    public static final int RANK_BASED = 1;
    public static final int TRUNCATION = 2;
    public static final int RANDOM = 3;
    public static final int TOURNAMENT = 4;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        ChessBoard.BOARD_DIMENSION = 8;

        // STEP#1 creating population of n elements
        population = new ChessBoard[n];
        for (int i = 0; i < n; i++) {
            population[i] = new ChessBoard();
            population[i].initializeBoard();
            population[i].calculateFitness();
//            System.out.println("parent fitness " + population[i].getFitness());
        }

        ChessBoard populationToPassInFunctions[] = new ChessBoard[n];

         System.out.println("");
        System.out.println("");
        System.out.println("RANK BASED AND TOURNAMENT");
         copyArrayInNewArray(population, populationToPassInFunctions);
        algorithm(populationToPassInFunctions, NQueen_Evolutionary.TOURNAMENT,
                NQueen_Evolutionary.TOURNAMENT);
        
        System.out.println("");
        System.out.println("");
        System.out.println("FITNESS PROPORTION AND RANK BASED");
        copyArrayInNewArray(population, populationToPassInFunctions);
        algorithm(populationToPassInFunctions, NQueen_Evolutionary.FITNESS_PROPOTION,
                NQueen_Evolutionary.RANK_BASED);
        
       System.out.println("");
        System.out.println("");
        System.out.println("RANK BASED AND FITNESS PROPORTION");
        copyArrayInNewArray(population, populationToPassInFunctions);
        algorithm(populationToPassInFunctions, NQueen_Evolutionary.RANK_BASED,
                NQueen_Evolutionary.FITNESS_PROPOTION);
        
       
        
        System.out.println("");
        System.out.println("");
        System.out.println("RANK BASED AND TOURNAMENT");
         copyArrayInNewArray(population, populationToPassInFunctions);
        algorithm(populationToPassInFunctions, NQueen_Evolutionary.RANK_BASED,
                NQueen_Evolutionary.TOURNAMENT);
        
        System.out.println("");
        System.out.println("");
        System.out.println("RANK BASED AND TRUNCATION");
         copyArrayInNewArray(population, populationToPassInFunctions);
        algorithm(populationToPassInFunctions, NQueen_Evolutionary.RANK_BASED,
                NQueen_Evolutionary.TRUNCATION);
        
        System.out.println("");
        System.out.println("");
        System.out.println("TOURNAMENT AND FITNESS PROPORTION");
          copyArrayInNewArray(population, populationToPassInFunctions);
        algorithm(populationToPassInFunctions, NQueen_Evolutionary.TOURNAMENT,
                NQueen_Evolutionary.FITNESS_PROPOTION);
        
        System.out.println("");
        System.out.println("");
        System.out.println("TOURNAMENT AND RANK BASED");
         copyArrayInNewArray(population, populationToPassInFunctions);
        algorithm(populationToPassInFunctions, NQueen_Evolutionary.TOURNAMENT,
                NQueen_Evolutionary.RANK_BASED);
        
        
        System.out.println("");
        System.out.println("");
        System.out.println("TOURNAMENT AND TRUNCATION");
         copyArrayInNewArray(population, populationToPassInFunctions);
        algorithm(populationToPassInFunctions, NQueen_Evolutionary.TOURNAMENT,
                NQueen_Evolutionary.TRUNCATION);
        
        System.out.println("");
        System.out.println("");
        System.out.println("RANDOM AND FITNESS PROPORTION");
         copyArrayInNewArray(population, populationToPassInFunctions);
        algorithm(populationToPassInFunctions, NQueen_Evolutionary.RANDOM,
                NQueen_Evolutionary.FITNESS_PROPOTION);
        
        System.out.println("");
        System.out.println("");
        System.out.println("RANDOM AND RANK BASED");
          copyArrayInNewArray(population, populationToPassInFunctions);
        algorithm(populationToPassInFunctions, NQueen_Evolutionary.RANDOM,
                NQueen_Evolutionary.RANK_BASED);
        
        
        System.out.println("");
        System.out.println("");
        System.out.println("RANDOM AND TRUNCATION");
          copyArrayInNewArray(population, populationToPassInFunctions);
        algorithm(populationToPassInFunctions, NQueen_Evolutionary.RANDOM,
                NQueen_Evolutionary.TRUNCATION);

        
         System.out.println("");
        System.out.println("");
        System.out.println("FITNESS PROPORTION AND TORUNAMENT");
         copyArrayInNewArray(population, populationToPassInFunctions);
        algorithm(populationToPassInFunctions, NQueen_Evolutionary.FITNESS_PROPOTION,
                NQueen_Evolutionary.TOURNAMENT);
        
        System.out.println("");
        System.out.println("");
        System.out.println("FITNESS PROPORTION AND TRUNCATION");
         copyArrayInNewArray(population, populationToPassInFunctions);
        algorithm(populationToPassInFunctions, NQueen_Evolutionary.FITNESS_PROPOTION,
                NQueen_Evolutionary.TRUNCATION);
        
//        for (int i = 0; i < population.length; i++) {
//            System.out.println(population[i].getFitness());
//        }
    }

    public static void algorithm(ChessBoard[] population, int parentSelection, int populationSelection) {

        stop=false;
        int parent1Index;
        int parent2Index;
        int j = 0;
        
        while (true) {
            //STEP #2.0 CREATE m CHILDRENS
            children = new ChessBoard[m];
            for (int i = 0; i < m; i = i + 2) {

                //STEP #2.1 different parents
                //checking which strategy to choose
                switch (parentSelection) {
                    case NQueen_Evolutionary.FITNESS_PROPOTION:
                        parents = EvolutionaryAlgorithm.fitnessProportion(population, parents.length);
                        break;
                    case NQueen_Evolutionary.RANK_BASED:
                        parents = EvolutionaryAlgorithm.rankBased(population, parents.length);
                        break;
                    case NQueen_Evolutionary.TOURNAMENT:
                        parents = EvolutionaryAlgorithm.tournamentSelection(population, parents.length);
                        break;

                    case NQueen_Evolutionary.RANDOM:
                        parents = EvolutionaryAlgorithm.randomSelection(population, parents.length);
                        break;

                }

                //index 0 contains first parent index 1 contains second parent
                //STEP #2.2 apply crossovers and produce 2 children 
                children[i] = new ChessBoard();
                children[i].twoPointCrossOver(parents[0].getQueenLocation(), parents[1].getQueenLocation());
                children[i + 1] = new ChessBoard();
                children[i + 1].twoPointCrossOver(parents[1].getQueenLocation(), parents[0].getQueenLocation());

                //STEP #2.3 APPLY MUTATION ON TWO PRODUCED CHILDREN OF 0.25
                //checking probablity of mutation for first child
                if (Mathematics.getRandom() >= 0.75) {
                    children[i].mutate();
                }

                //checking probablity of mutation for second child
                if (Mathematics.getRandom() >= 0.75) {
                    children[i + 1].mutate();
                }

                //STEP # 2.4 CALCULATE FITNESS OF EACH CHILD
                children[i].calculateFitness();
                children[i + 1].calculateFitness();
            }

            //STEP # 3 FROM m+n individuals select n fittest individuals
            //combining children and population in single array
            System.arraycopy(population, 0, totalPopulation, 0, population.length);
            System.arraycopy(children, 0, totalPopulation, population.length, children.length);

            //population selection and resetting population
             //checking which strategy to choose
                switch (populationSelection) {
                    
                    case NQueen_Evolutionary.FITNESS_PROPOTION:
                        population = EvolutionaryAlgorithm.fitnessProportion(totalPopulation, n);
                        break;
                    case NQueen_Evolutionary.RANK_BASED:
                        population = EvolutionaryAlgorithm.rankBased(totalPopulation, n);
                        break;
                    case NQueen_Evolutionary.TOURNAMENT:
                        population = EvolutionaryAlgorithm.tournamentSelection(totalPopulation, n);
                        break;

                    case NQueen_Evolutionary.TRUNCATION:
                        population = EvolutionaryAlgorithm.truncationSelection(totalPopulation, n);
                        break;

                }
                
//            population = EvolutionaryAlgorithm.tournamentSelection(totalPopulation, n);

//            for (int p = 0; p < population.length; p++) {
//                System.out.print("GENERATION " + j + " Scroes " + population[p].getFitness() + "  "+parentSelection+  "   "+populationSelection+" ");
//                population[p].show();
//                System.out.print(population[p]);
//                System.out.println("");
//            }
            
            int h=0;
            for(int m=0;m<population.length;m++){
                
                for(int d=0;d<population.length;d++){
                    if(population[m]==population[d]){
                        h++;
                        if(h>=2){
                            System.out.println("HAN BHAI HEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
                            break;
                    }
                    }
                    h=0;
                }
            }

            int f;
            //checking if we have reached the soltuion
            for (f = 0; f < population.length; f++) {
                if (population[f].getFitness() == ChessBoard.BOARD_DIMENSION) {
                    stop = true;
                    break;
                }
            }
            
            if (stop) {
                
                System.out.println("GENERATION " + j + " Best Fitness " + population[f].getFitness() + " " + population[f].getQueenLocation());

                //printing matrix
                for (int p = 0; p < ChessBoard.BOARD_DIMENSION; p++) {
                    for (int k = 0; k < ChessBoard.BOARD_DIMENSION; k++) {
                        if (population[f].getQueenLocation().get(p) == k) {
                            System.out.print(" 1");
                        } else {
                            System.out.print(" 0");
                        }
                    }
                    System.out.println("");
                }
                break;
            }

            j++;
        }

    }

    public static void copyArrayInNewArray(ChessBoard[] source, ChessBoard[] destination) {

        for (int i = 0; i < source.length; i++) {
            destination[i] = new ChessBoard();
            for (int j = 0; j < source[i].getQueenLocation().size(); j++) {
                destination[i].getQueenLocation().add(source[i].getQueenLocation().get(j));
            }
            destination[i].calculateFitness();
//            System.out.println("parent1 " + destination[i].getFitness());
        }

    }

}

class ChessBoard {

    public static int BOARD_DIMENSION;
    private List<Integer> queenLocation;
    private int fitness;
    private int rank; // used for selecction algo
    private double proportion; // used for selection algo
    private double commulative; //used for selection algo

    public ChessBoard() {

        this.queenLocation = new ArrayList<Integer>();
    }

    //randomly initialize board while checking columns must not contain two queens
    public void initializeBoard() {

        int random;

        while (true) {

            random = Mathematics.getRandom(0, BOARD_DIMENSION - 1);
            //checking to set each queen in different column
            if (!queenLocation.contains(random)) {
                queenLocation.add(random);
            }
            if (queenLocation.size() >= BOARD_DIMENSION) {
                break;
            }
        }
    }

    public void setQueenLocation(List<Integer> queenLocation) {
        this.queenLocation = queenLocation;
    }

    public void calculateFitness() {

        int columnL;
        int columnR;
        Boolean found = false;

        //current row
        for (int i = 0; i < ChessBoard.BOARD_DIMENSION; i++) {

            columnL = columnR = this.queenLocation.get(i);
            found = false;

            //FOR UPPER DIAGONAL
            for (int row = i - 1; row >= 0; row--) {

                columnL--;
                columnR++;
                if (columnL >= 0 && this.queenLocation.get(row).equals(columnL)) {
//                    System.out.println("foundddd true "+ row + columnL+" WITH "+i + this.queenLocation.get(i));
                    found = true;
                    break;
                }

                if (columnR <= ChessBoard.BOARD_DIMENSION && this.queenLocation.get(row).equals(columnR)) {
                    found = true;
//                    System.out.println("foundddddd true "+ row + columnR+" WITH "+i + this.queenLocation.get(i));
                    break;
                }
            }

            if (!found) {

                columnL = columnR = this.queenLocation.get(i);
                //FOR LOWER DIAGONAL
                for (int row = i + 1; row < ChessBoard.BOARD_DIMENSION; row++) {

                    columnL--;
                    columnR++;
                    if (columnL >= 0 && this.queenLocation.get(row).equals(columnL)) {
                        found = true;
//                        System.out.println("foundd true "+ row + columnL+" WITH "+i + this.queenLocation.get(i));
                        break;
                    }

                    if (columnR <= ChessBoard.BOARD_DIMENSION && this.queenLocation.get(row).equals(columnR)) {
                        found = true;
//                        System.out.println("founddd true "+ row + columnR+" WITH "+i + this.queenLocation.get(i));
                        break;
                    }
                }

            }
//          IF MATCH NOT FOUND THN ITS A VALID POSITION 
            if (!found) {
                this.fitness++;
            }

        }
    }

    //first parent chromosome's first half values will be coppied into this child
    //and second half will comefrom second parentchromosme
    public void onePointCrossOver(List<Integer> firstParentChromosome, List<Integer> secondParentChromosome) {

        int divider = ChessBoard.BOARD_DIMENSION / 2;

        int i;
        //copying first part of first parent
        for (i = 0; i < divider; i++) {
            this.queenLocation.add(firstParentChromosome.get(i));
        }

        i = divider;
        //copying second part and skipping duplicate values from second parent chromosome
        while (this.queenLocation.size() <= ChessBoard.BOARD_DIMENSION - 1) {

            //checking if this chromose already contains parent chromosome value
            //if it contains thn skip this value and move to next
            if (!this.queenLocation.contains(secondParentChromosome.get(i))) {
                this.queenLocation.add(secondParentChromosome.get(i));
            }

            i++;
            //if after skipping values i reach to the end of the chromosme thn reset it to start
            if (i == ChessBoard.BOARD_DIMENSION) {
                i = 0;
            }
        }
    }

    //this function will make 3 parts in child and first and thirs parts will be filled by first parent chromosme
    //and second part will be filled by second parent
    //Ex for two parents p1,p1,p1,p1,p1,p1,p1  and p2,p2,p2,p2,p2,p2,p2 will produce child
    // child= p1,p1,p2,p2,p2,p2,p1  keeping in mind same index should not be placed again 
    public void twoPointCrossOver(List<Integer> firstParentChromosome, List<Integer> secondParentChromosome) {

        int noOfElementsFromFirstParent = ChessBoard.BOARD_DIMENSION / 2;
        int noOfElementsFromSecondParent = ChessBoard.BOARD_DIMENSION - noOfElementsFromFirstParent;

        //no of elements in each part of new child chromosome
        //no of elements in second part is same as noOfElementsFromSecondParent
        int thirdPart = noOfElementsFromFirstParent / 2;
        int firstPart = noOfElementsFromFirstParent - thirdPart;

        int i;

        //filling first part
        for (i = 0; i < firstPart; i++) {
            this.queenLocation.add(firstParentChromosome.get(i));
        }

        //setting i for second part
        i = firstPart;
        //filling second part
        //also checking for already existing elements and skipping them
        while (this.queenLocation.size() < firstPart + noOfElementsFromSecondParent) {

            //if child already contains this index of parent then skip it and check the next index
            if (!this.queenLocation.contains(secondParentChromosome.get(i))) {
                this.queenLocation.add(secondParentChromosome.get(i));
            }

            i++;
            //if after skipping values i reach to the end of the chromosme thn reset it to start
            if (i == ChessBoard.BOARD_DIMENSION) {
                i = 0;
            }
        }

        //setting i for third part
        i = firstPart + noOfElementsFromSecondParent;
        //filling third part
        while (this.queenLocation.size() <= ChessBoard.BOARD_DIMENSION - 1) {
            //if child already contains this index of parent then skip it and check the next index
            if (!this.queenLocation.contains(firstParentChromosome.get(i))) {
                this.queenLocation.add(firstParentChromosome.get(i));
            }

            i++;
            //if after skipping values i reach to the end of the chromosme thn reset it to start
            if (i == ChessBoard.BOARD_DIMENSION) {
                i = 0;
            }
        }

    }

    //problem will come with odd number of board dimension because it will become difficult to
    //tell which part is gene 1 and which part is gene 2
    //example 9 is the board dimension
    //now when I croshh over first 4 elements will come in gene 1 and 5 elements in gene 2
    //but when I mutate last 5 elements will come first and last 4 at the end
    //now my gene sizes are reversed when I produce new child from this chromosome thn it will
    //become difficult to tell how many elements are from gene1 and how many are from gene 2....
    //* last index is not swapping if dimension is odd
    //this function wll swap genes
//    public void mutate() {
//
//        List<Integer> firstTemp=this.getQueenLocation().subList(0, ChessBoard.BOARD_DIMENSION/2);
//        List<Integer> lastTemp=this.getQueenLocation().subList(ChessBoard.BOARD_DIMENSION/2,ChessBoard.BOARD_DIMENSION );
//        this.queenLocation=new ArrayList<Integer>();
//        this.queenLocation.addAll(lastTemp);
//        this.queenLocation.addAll(firstTemp);
//
//    }
    //this function randomly selects two indexes Constraint: indexes should not be same
    //and swap their values
    public void mutate() {

        int random1 = Mathematics.getRandom(0, ChessBoard.BOARD_DIMENSION - 1);
        int random2 = Mathematics.getRandom(0, ChessBoard.BOARD_DIMENSION - 1);

        while (random1 == random2) {
            random2 = Mathematics.getRandom(0, ChessBoard.BOARD_DIMENSION - 1);
        }

        int temp;

        temp = this.queenLocation.get(random1);
        this.queenLocation.set(random1, this.queenLocation.get(random2));
        this.queenLocation.set(random2, temp);
    }

    //reverse sorting chess boards on the basis of their fitness
    public static ChessBoard[] sortFitness(ChessBoard[] array) {
        int n = array.length;
        for (int j = 1; j < n; j++) {
            ChessBoard key = array[j];
            int i = j - 1;
            while ((i > -1) && (array[i].getFitness() < key.getFitness())) {
                array[i + 1] = array[i];
                i--;
            }
            array[i + 1] = key;
        }
        return array;
    }

    public List<Integer> getQueenLocation() {
        return queenLocation;
    }

    public int getFitness() {
        return fitness;
    }

    public void show() {

        for (int i = 0; i < ChessBoard.BOARD_DIMENSION; i++) {
            System.out.print(this.getQueenLocation().get(i));
        }
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public double getProportion() {
        return proportion;
    }

    public void setProportion(double propotion) {
        this.proportion = propotion;
    }

    public double getCommulative() {
        return commulative;
    }

    public void setCommulative(double commulative) {
        this.commulative = commulative;
    }

    public static void test() {

        ChessBoard[] cb = new ChessBoard[10];

        for (int i = 0; i < cb.length; i++) {
            cb[i] = new ChessBoard();
            cb[i].initializeBoard();
            cb[i].calculateFitness();
//            System.out.println(cb[i].getQueenLocation() + " " + cb[i].getFitness());
        }

        EvolutionaryAlgorithm.rankBased(cb, 10);
        ChessBoard cb3 = new ChessBoard();

    }
}

class EvolutionaryAlgorithm {

    //this function arrange array in fitnesProportion order  because array are pass by reference we dont neew to return the change
    public static ChessBoard[] fitnessProportion(ChessBoard[] population, int noOfRequiredIndividuals) {

        List<ChessBoard> newRequiredPopulation = new ArrayList<>();

        //getting total population fitness
        int totalPopulationFitness = 0, i;
        for (i = 0; i < population.length; i++) {
            totalPopulationFitness += population[i].getFitness();
        }
//        System.out.println("total population fitness " + totalPopulationFitness);

        //calculating proportion of each individual to population
        for (i = 0; i < population.length; i++) {
            population[i].setProportion(((double) population[i].getFitness() / (double) totalPopulationFitness));
//            System.out.println("Proportion " + population[i].getProportion());
        }

        //commulating
        //setting first individual commulative = to proportion
        population[0].setCommulative(population[0].getProportion());
//        System.out.println("Commulative "+population[0].getCommulative());
        for (i = 1; i < population.length; i++) {
            population[i].setCommulative(population[i - 1].getCommulative() + population[i].getProportion());
//            System.out.println("Commulative " + population[i].getCommulative()+ " "+population[i]);

        }

        double random;
        int j;
        ///generating random number and selecting individuals for required population
        while (newRequiredPopulation.size() < noOfRequiredIndividuals) {
            random = Mathematics.getRandom();
//            System.out.println("Random " + random);
            //finding element in original population
            for (j = 0; j < population.length - 1; j++) {

                //if random no is les thn the starting elemtn
                if (Mathematics.roundDoubleTo3DecimalPosition(random) < Mathematics.roundDoubleTo3DecimalPosition(population[0].getCommulative())) {
//                    System.out.println("found elementtt " + random + " " + population[j].getCommulative() + " " + population[j]);

                    int temp = j;
                    // below code will be executed so if two numbers are equal in list they wont get ignored
                    do {
                        //if element at j is already present in required population && if element at j+1 = elemtne at j
                        if (population[temp].getCommulative() == population[j].getCommulative()) {

                            if (!newRequiredPopulation.contains(population[temp])) {
                                j = temp;
//                                System.out.println("found elementttt " + population[j].getCommulative() + " " + random + " " + population[temp].getCommulative() + " " + population[temp]);
                                break;
                            }
                            temp++;
                            if (temp >= population.length) {
                                break;
                            }
                        } else {
//                            System.out.println("j jaega " + population[j].getCommulative());
                            break;
                        }
                    } while (true);

                    break;
                }
                //if random no is equal to any number in list
                if (Mathematics.roundDoubleTo3DecimalPosition(population[j].getCommulative()) == Mathematics.roundDoubleTo3DecimalPosition(random)) {
//                    System.out.println("found element " + random + " " + population[j].getCommulative() + " " + population[j]);

                    int temp = j;
                    //but if j+1 is already selected
                    //below code inside if will help in going further if two same commulative score comes in sequence
                    //ex 1 2 3 4 4 5   and my rand is 3.4 but 4 on 3rd index is already selectedd , so this code will help in seleccting 4 on 4th index
                    // below code will be executed so if two numbers are equal in list they wont get ignored
                    do {
                        //if element at j is already present in required population && if element at j+1 = elemtne at j
                        if (population[temp].getCommulative() == population[j].getCommulative()) {

                            if (!newRequiredPopulation.contains(population[temp])) {
                                j = temp;
//                                System.out.println("found elementttt " + population[j].getCommulative() + " " + random + " " + population[temp].getCommulative() + " " + population[temp]);
                                break;
                            }
                            temp++;
                            if (temp >= population.length) {
                                break;
                            }
                        } else {
//                            System.out.println("j jaega " + population[j].getCommulative());
                            break;
                        }
                    } while (true);

                    break;
                }
                if (Mathematics.roundDoubleTo3DecimalPosition(population[j].getCommulative()) < Mathematics.roundDoubleTo3DecimalPosition(random)
                        && Mathematics.roundDoubleTo3DecimalPosition(population[j + 1].getCommulative()) >= Mathematics.roundDoubleTo3DecimalPosition(random)) {
//                    System.out.println("found elementt " + population[j].getCommulative() + " " + random + " " + population[j + 1].getCommulative() + " " + population[j + 1]);
                    j = j + 1;

                    int temp = j;

                    // below code will be executed so if two numbers are equal in list they wont get ignored
                    //but if j+1 is already selected
                    //below code inside if will help in going further if two same commulative score comes in sequence
                    //ex 1 2 3 4 4 5   and my rand is 3.4 but 4 on 3rd index is already selectedd , so this code will help in seleccting 4 on 4th index
                    do {
                        //if element at j is already present in required population && if element at j+1 = elemtne at j
                        if (population[temp].getCommulative() == population[j].getCommulative()) {

                            if (!newRequiredPopulation.contains(population[temp])) {
                                j = temp;
//                                System.out.println("found elementttt " + population[j].getCommulative() + " " + random + " " + population[temp].getCommulative() + " " + population[temp]);
                                break;
                            }
                            temp++;
                            if (temp >= population.length) {
                                break;
                            }
                        } else {
//                            System.out.println("j jaega " + population[j].getCommulative());
                            break;
                        }
                    } while (true);

                    break;
                }
            }  // j will be the position of randomly selected element on the basis of commulative value

            if (!newRequiredPopulation.contains(population[j])) {
//                System.out.println("select " + population[j].getCommulative());
                newRequiredPopulation.add(population[j]);
            }

        }

//        for (int g = 0; g < newRequiredPopulation.size(); g++) {
//            System.out.println("selected " + newRequiredPopulation.get(g).getCommulative() + " " + newRequiredPopulation.get(g));
//        }
        return newRequiredPopulation.toArray(new ChessBoard[newRequiredPopulation.size()]);
    }

    //this function will return new  array in rankBased order  
    public static ChessBoard[] rankBased(ChessBoard[] population, int noOfRequiredIndividuals) {

        ArrayList<ChessBoard> newRequiredPopulation = new ArrayList<>();
        int i, ranksTotal = 0;
        //sorting on the basis of fitness
        Mathematics.sort(population);

        //applying ranks
        for (i = 0; i < population.length; i++) {
            population[i].setRank(i + 1);
            ranksTotal = ranksTotal + i + 1;
        }
//        System.out.println("total ranks " + ranksTotal);

        //calculating rank proportion
        for (i = 0; i < population.length; i++) {
            population[i].setProportion(((double) population[i].getRank() / (double) ranksTotal));
//            System.out.println("Proportion " + population[i].getProportion());
        }

        //commulating
        //setting first individual commulative = to proportion
        population[0].setCommulative(population[0].getProportion());
        for (i = 1; i < population.length; i++) {
            population[i].setCommulative(population[i - 1].getCommulative() + population[i].getProportion());
//            System.out.println("Commulative " + population[i].getCommulative());

        }

        double random;
        int j;
        ///generating random number and selecting individuals for required population
        while (newRequiredPopulation.size() < noOfRequiredIndividuals) {
            random = Mathematics.getRandom();
//            System.out.println("Random " + random);
            //finding element in original population
            for (j = 0; j < population.length - 1; j++) {

                //if random number is less thn commulative of first element
                if (Mathematics.roundDoubleTo3DecimalPosition(random) < Mathematics.roundDoubleTo3DecimalPosition(population[0].getCommulative())) {
//                    System.out.println("found elementtt " + random + " " + population[j].getCommulative() + " " + population[j]);
                    break;
                }
                //if random number is equal to commulative of any element
                if (Mathematics.roundDoubleTo3DecimalPosition(population[j].getCommulative()) == Mathematics.roundDoubleTo3DecimalPosition(random)) {
//                    System.out.println("found element " + random + " " + population[j].getCommulative() + " " + population[j]);
                    break;
                }
                //if random no fall between two numbers such that j < random >j+1 thn select j+1
                if (Mathematics.roundDoubleTo3DecimalPosition(population[j].getCommulative()) < Mathematics.roundDoubleTo3DecimalPosition(random)
                        && Mathematics.roundDoubleTo3DecimalPosition(population[j + 1].getCommulative()) >= Mathematics.roundDoubleTo3DecimalPosition(random)) {
//                    System.out.println("found elementt " + population[j].getCommulative() + " " + random + " " + population[j + 1].getCommulative() + " " + population[j + 1]);
                    j = j + 1;

                    break;
                }
            }  // j will be the position of randomly selected element on the basis of commulative value

            if (!newRequiredPopulation.contains(population[j])) {
                newRequiredPopulation.add(population[j]);
//                System.out.println("seleccted");
            }

        }

//        for (int g = 0; g < newRequiredPopulation.size(); g++) {
//            System.out.println("selected " + newRequiredPopulation.get(g).getCommulative());
//        }
        return newRequiredPopulation.toArray(new ChessBoard[newRequiredPopulation.size()]);
    }

    //will return new array on the basis of tournament selection, size of the array will noOfRequiredIndividuals
    public static ChessBoard[] tournamentSelection(ChessBoard[] population, int noOfRequiredIndividuals) {

        int random1;
        int random2;

        List<ChessBoard> winners = new ArrayList<>();

        while (winners.size() < noOfRequiredIndividuals) {

            random1 = Mathematics.getRandom(0, population.length - 1);
            random2 = Mathematics.getRandom(0, population.length - 1);

//            System.out.println("first " + population[random1].getFitness() + " " + population[random1] + " second " + population[random2].getFitness() + " " + population[random2]);
            //checking if winners already contain this element
            if (winners.contains(population[random1]) && winners.contains(population[random2])) {
                continue;
            } //if winners contains random2 index and does not ccontain random1 index thn random1 will be added to winners
            else if (!winners.contains(population[random1]) && winners.contains(population[random2])) {
                winners.add(population[random1]);
//                System.out.println("selected1 " + population[random1].getFitness() + " object " + population[random1]);
            } //if winners contains random1 index and does not ccontain random2 index thn random2 will be added to winners
            else if (!winners.contains(population[random2]) && winners.contains(population[random1])) {
                winners.add(population[random2]);
//                System.out.println("selected2 " + population[random2].getFitness() + " object " + population[random2]);
            } ////// COMPARISON STARTS HERE
            //if random 1 fitness is greater thn random2
            else if (population[random1].getFitness() > population[random2].getFitness()) {
                winners.add(population[random1]);
//                System.out.println("selected3 " + population[random1].getFitness() + " object " + population[random1]);
            } //            if random 2 fitness is greater thn random1
            else if (population[random1].getFitness() < population[random2].getFitness()) {
                winners.add(population[random2]);
//                System.out.println("selected4 " + population[random2].getFitness() + " object " + population[random2]);
            } //draw case
            else {
                winners.add(population[random1]);
//                System.out.println("selected5 " + population[random1].getFitness() + " object " + population[random1]);

            }
        }

        return winners.toArray(new ChessBoard[winners.size()]);
    }

    //this selection scheme is used only with population selection
    //it will return best no of individuals in sorted order
    public static ChessBoard[] truncationSelection(ChessBoard[] population, int noOfIndividualsRequired) {

        ChessBoard[] newPopulation = new ChessBoard[noOfIndividualsRequired];

        //reverse sorting population on the basis of fitness
        ChessBoard.sortFitness(population);

        System.arraycopy(population, 0, newPopulation, 0, noOfIndividualsRequired);
//        System.out.println(newPopulation.length);
        for (int i = 0; i < newPopulation.length; i++) {
//            System.out.println("new population " + newPopulation[i].getFitness() + " " + newPopulation[i]);
        }

        return newPopulation;
    }

    //only for parent selection
    //it will return new array index 0  will contain 1st parent and 1 will contain 2nd parent
    public static ChessBoard[] randomSelection(ChessBoard[] population, int noOfRequiredIndividuals) {

        List<ChessBoard> parents = new ArrayList<>();

        int random1;
        int random2;

        while (parents.size() < noOfRequiredIndividuals) {
            random1 = Mathematics.getRandom(0, population.length - 1);
            random2 = Mathematics.getRandom(0, population.length - 1);

//            System.out.println("ran1 " + random1 + " ran2 " + random2);
            if (random1 != random2) {
                parents.add(population[random1]);
                parents.add(population[random2]);

//                System.out.println("parent 1 " + parents.get(0).getFitness() + " " + parents.get(0));
//                System.out.println("parent 2 " + parents.get(1).getFitness() + " " + parents.get(1));
//                System.out.println(parents.size());
            }
        }
        return parents.toArray(new ChessBoard[parents.size()]);
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

    public static double square(double d) {
        return java.lang.Math.pow(d, 2);
    }

    //sorting chess boards on the basis of their commulative scores
    public static void sort(ChessBoard[] array) {
        int n = array.length;
        for (int j = 1; j < n; j++) {
            ChessBoard key = array[j];
            int i = j - 1; 
            while ((i > -1) && (array[i].getFitness() > key.getFitness())) {
                array[i + 1] = array[i];
                i--;
            }
            array[i + 1] = key;
        }

    }

}
