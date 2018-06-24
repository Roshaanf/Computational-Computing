/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolutionaryalgotirhm;

import java.lang.Math;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Roshaann 2.7 gpa
 */
public class EvolutionaryAlgotirhm {

    public static final double minX = -2;
    public static final double maxX = 2;
    public static final double minY = -1;
    public static final double maxY = 3;

    public static final int n = 25;
    public static final int m = 20;
    public static final int generations = 50;
    public static Member[] population;
    public static Member[] children;
    public static Member[] parents = new Member[2];
    public static Member[] totalPopulation = new Member[m + n];

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
        // STEP#1 creating population of n elements
        population = new Member[n];
        for (int i = 0; i < n; i++) {
            population[i] = new Member();
        }
        
        
         Member populationToPassInFunctions[] = new Member[n];

          System.out.println("");
        System.out.println("");
        System.out.println("RANK BASED AND TOURNAMENT");
         copyArrayInNewArray(population, populationToPassInFunctions);
        algorithm(populationToPassInFunctions, EvolutionaryAlgotirhm.RANK_BASED,
                EvolutionaryAlgotirhm.TOURNAMENT);
        
        System.out.println("");
        System.out.println("");
        System.out.println("FITNESS PROPORTION AND RANK BASED");
        copyArrayInNewArray(population, populationToPassInFunctions);
        algorithm(populationToPassInFunctions, EvolutionaryAlgotirhm.FITNESS_PROPOTION,
                EvolutionaryAlgotirhm.RANK_BASED);
        
       System.out.println("");
        System.out.println("");
        System.out.println("RANK BASED AND FITNESS PROPORTION");
        copyArrayInNewArray(population, populationToPassInFunctions);
        algorithm(populationToPassInFunctions, EvolutionaryAlgotirhm.RANK_BASED,
                EvolutionaryAlgotirhm.FITNESS_PROPOTION);
        
        System.out.println("");
        System.out.println("");
        System.out.println("FITNESS PROPORTION AND TORUNAMENT");
         copyArrayInNewArray(population, populationToPassInFunctions);
        algorithm(populationToPassInFunctions, EvolutionaryAlgotirhm.FITNESS_PROPOTION,
                EvolutionaryAlgotirhm.TOURNAMENT);
        
        System.out.println("");
        System.out.println("");
        System.out.println("FITNESS PROPORTION AND TRUNCATION");
         copyArrayInNewArray(population, populationToPassInFunctions);
        algorithm(populationToPassInFunctions, EvolutionaryAlgotirhm.FITNESS_PROPOTION,
                EvolutionaryAlgotirhm.TRUNCATION);
        
        System.out.println("");
        System.out.println("");
        System.out.println("RANK BASED AND TOURNAMENT");
         copyArrayInNewArray(population, populationToPassInFunctions);
        algorithm(populationToPassInFunctions, EvolutionaryAlgotirhm.RANK_BASED,
                EvolutionaryAlgotirhm.TOURNAMENT);
        
        System.out.println("");
        System.out.println("");
        System.out.println("RANK BASED AND TRUNCATION");
         copyArrayInNewArray(population, populationToPassInFunctions);
        algorithm(populationToPassInFunctions, EvolutionaryAlgotirhm.RANK_BASED,
                EvolutionaryAlgotirhm.TRUNCATION);
        
        System.out.println("");
        System.out.println("");
        System.out.println("TOURNAMENT AND FITNESS PROPORTION");
          copyArrayInNewArray(population, populationToPassInFunctions);
        algorithm(populationToPassInFunctions, EvolutionaryAlgotirhm.TOURNAMENT,
                EvolutionaryAlgotirhm.FITNESS_PROPOTION);
        
        System.out.println("");
        System.out.println("");
        System.out.println("TOURNAMENT AND RANK BASED");
         copyArrayInNewArray(population, populationToPassInFunctions);
        algorithm(populationToPassInFunctions, EvolutionaryAlgotirhm.TOURNAMENT,
                EvolutionaryAlgotirhm.RANK_BASED);
        
        
        System.out.println("");
        System.out.println("");
        System.out.println("TOURNAMENT AND TRUNCATION");
         copyArrayInNewArray(population, populationToPassInFunctions);
        algorithm(populationToPassInFunctions, EvolutionaryAlgotirhm.TOURNAMENT,
                EvolutionaryAlgotirhm.TRUNCATION);
        
        System.out.println("");
        System.out.println("");
        System.out.println("RANDOM AND FITNESS PROPORTION");
         copyArrayInNewArray(population, populationToPassInFunctions);
        algorithm(populationToPassInFunctions, EvolutionaryAlgotirhm.RANDOM,
                EvolutionaryAlgotirhm.FITNESS_PROPOTION);
        
        System.out.println("");
        System.out.println("");
        System.out.println("RANDOM AND RANK BASED");
          copyArrayInNewArray(population, populationToPassInFunctions);
        algorithm(populationToPassInFunctions, EvolutionaryAlgotirhm.RANDOM,
                EvolutionaryAlgotirhm.RANK_BASED);
        
        
        System.out.println("");
        System.out.println("");
        System.out.println("RANDOM AND TRUNCATION");
          copyArrayInNewArray(population, populationToPassInFunctions);
        algorithm(populationToPassInFunctions, EvolutionaryAlgotirhm.RANDOM,
                EvolutionaryAlgotirhm.TRUNCATION);

    }

    public static void algorithm(Member[] population, int parentSelection, int populationSelection) {

       

        for (int j = 0; j < generations; j++) {
            //STEP #2.0 CREATE m CHILDRENS
            children = new Member[m];
            for (int i = 0; i < m; i = i + 2) {

                //STEP #2.1 different parents
               //checking which strategy to choose
                switch (parentSelection) {
                    case EvolutionaryAlgotirhm.FITNESS_PROPOTION:
                        parents = EvolutionaryAlgorithm.fitnessProportion(population, parents.length);
                        break;
                    case EvolutionaryAlgotirhm.RANK_BASED:
                        parents = EvolutionaryAlgorithm.rankBased(population, parents.length);
                        break;
                    case EvolutionaryAlgotirhm.TOURNAMENT:
                        parents = EvolutionaryAlgorithm.tournamentSelection(population, parents.length);
                        break;

                    case EvolutionaryAlgotirhm.RANDOM:
                        parents = EvolutionaryAlgorithm.randomSelection(population, parents.length);
                        break;

                }

                //STEP #2.2 apply crossovers and produce 2 children 
                children[i] = new Member(parents[0].getX(), parents[1].getY());
                children[i + 1] = new Member(parents[1].getX(), parents[0].getY());

                //STEP #2.3 APPLY MUTATION ON TWO PRODUCED CHILDREN OF 0.25
                //checking probablity of mutation for first child
                if (Mathematics.getRandom() >= 0.75) {

                    //applying mutation on first child
                    //now checking which gene to mutate
                    //if random num < 0.5 thn on x oherwise x
                    if (Mathematics.getRandom() < 0.5) {
                        //apply mutation on x gene of first child
                        children[i].mutateX();
                    } else {
                        //apply mutation on y gene of first child
                        children[i].mutateY();
                    }
                }

                //checking probablity of mutation for second child
                if (Mathematics.getRandom() >= 0.75) {

                    //applying mutation on first child
                    //now checking which gene to mutate
                    //if random num < 0.5 thn on x oherwise x
                    if (Mathematics.getRandom() < 0.5) {
                        //apply mutation on x gene of first child
                        children[i + 1].mutateX();
                    } else {
                        //apply mutation on y gene of first child
                        children[i + 1].mutateY();
                    }
                }

                //STEP # 2.4 CALCULATE FITNESS OF EACH CHILD
                children[i].calculateFitness();
                children[i + 1].calculateFitness();

            }

            //STEP # 3 FROM m+n individuals select n fittest individuals
            System.arraycopy(population, 0, totalPopulation, 0, population.length);
            System.arraycopy(children, 0, totalPopulation, population.length, children.length);
            
            //population selection and resetting population
             //checking which strategy to choose
                switch (populationSelection) {
                    
                    case EvolutionaryAlgotirhm.FITNESS_PROPOTION:
                        population = EvolutionaryAlgorithm.fitnessProportion(totalPopulation, n);
                        break;
                    case EvolutionaryAlgotirhm.RANK_BASED:
                        population = EvolutionaryAlgorithm.rankBased(totalPopulation, n);
                        break;
                    case EvolutionaryAlgotirhm.TOURNAMENT:
                        population = EvolutionaryAlgorithm.tournamentSelection(totalPopulation, n);
                        break;

                    case EvolutionaryAlgotirhm.TRUNCATION:
                        population = EvolutionaryAlgorithm.truncationSelection(totalPopulation, n);
                        break;

                }


            System.out.println("GENERATION " + j + " Best Fitness " + totalPopulation[0].getFitness());

        }
    }
    
    
    public static void copyArrayInNewArray(Member[] source, Member[] destination) {

        for (int i = 0; i < source.length; i++) {
            
            destination[i] = new Member(source[i].getX(),source[i].getY());
           
            
            destination[i].calculateFitness();
//            System.out.println("parent1 " + destination[i].getFitness());
        }

    }
}

class Member {

    private double x;
    private double y;
    private double fitness;
    private double commulative;
    private int rank;
    private double proportion;

    //for parents and population
    public Member() {

        //setting x and y
        this.x = Mathematics.getRandom(EvolutionaryAlgotirhm.minX, EvolutionaryAlgotirhm.maxX);
        this.y = Mathematics.getRandom(EvolutionaryAlgotirhm.minY, EvolutionaryAlgotirhm.maxY);

        //setting fitness of each individual
        calculateFitness();

    }

    //for children
    public Member(double x, double y) {

        //setting x and y
        this.x = x;
        this.y = y;

    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void mutateX() {

        this.x += 0.25;

        //if mutation is making x greater thn upper limit thn scale it down to upper limit
        if (this.x > EvolutionaryAlgotirhm.maxX) {
            this.x = EvolutionaryAlgotirhm.maxX;
        }
        //if mutation is making x lesser thn lower limit thn scale it down to lower limit
        if (this.x < EvolutionaryAlgotirhm.minX) {
            this.x = EvolutionaryAlgotirhm.minX;
        }

    }

    public void mutateY() {

        //if mutation is making Y greater thn upper limit thn scale it down to upper limit
        if (this.y > EvolutionaryAlgotirhm.maxY) {
            this.y = EvolutionaryAlgotirhm.maxY;
        }
        //if mutation is making Y lesser thn lower limit thn scale it down to lower limit
        if (this.y < EvolutionaryAlgotirhm.minY) {
            this.y = EvolutionaryAlgotirhm.minY;
        }
    }

    public void calculateFitness() {
        java.lang.Math.pow(x, x);
        this.fitness = 100
                * Mathematics.square(Mathematics.square(this.x) - this.y)
                + Mathematics.square(1 - this.x);

    }

    public static Member[] sortFitness(Member[] array) {
        int n = array.length;
        for (int j = 1; j < n; j++) {
            Member key = array[j];
            int i = j - 1;
            while ((i > -1) && (array[i].getFitness() < key.getFitness())) {
                array[i + 1] = array[i];
                i--;
            }
            array[i + 1] = key;
        }
        return array;
    }

    public double getFitness() {
        return this.fitness;
    }

    public double getCommulative() {
        return commulative;
    }

    public void setCommulative(double commulative) {
        this.commulative = commulative;
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

    public void setProportion(double proportion) {
        this.proportion = proportion;
    }

}

class EvolutionaryAlgorithm {

    //this function arrange array in fitnesProportion order  because array are pass by reference we dont neew to return the change
    public static Member[] fitnessProportion(Member[] population, int noOfRequiredIndividuals) {

        List<Member> newRequiredPopulation = new ArrayList<>();

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
                if (random < population[0].getCommulative()) {
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
                if (population[j].getCommulative() == random) {
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
                if (population[j].getCommulative() < random
                        && population[j + 1].getCommulative() >= random) {
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
        return newRequiredPopulation.toArray(new Member[newRequiredPopulation.size()]);
    }

    //this function will return new  array in rankBased order  
    public static Member[] rankBased(Member[] population, int noOfRequiredIndividuals) {

        ArrayList<Member> newRequiredPopulation = new ArrayList<>();
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
                if (random < population[0].getCommulative()) {
//                    System.out.println("found elementtt " + random + " " + population[j].getCommulative() + " " + population[j]);
                    break;
                }
                //if random number is equal to commulative of any element
                if (population[j].getCommulative() == random) {
//                    System.out.println("found element " + random + " " + population[j].getCommulative() + " " + population[j]);
                    break;
                }
                //if random no fall between two numbers such that j < random >j+1 thn select j+1
                if (population[j].getCommulative() < random
                        && population[j + 1].getCommulative() >= random) {
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
        return newRequiredPopulation.toArray(new Member[newRequiredPopulation.size()]);
    }

    //will return new array on the basis of tournament selection, size of the array will noOfRequiredIndividuals
    public static Member[] tournamentSelection(Member[] population, int noOfRequiredIndividuals) {

        int random1;
        int random2;

        List<Member> winners = new ArrayList<>();

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

        return winners.toArray(new Member[winners.size()]);
    }

    //this selection scheme is used only with population selection
    //it will return best no of individuals in sorted order
    public static Member[] truncationSelection(Member[] population, int noOfIndividualsRequired) {

        Member[] newPopulation = new Member[noOfIndividualsRequired];

        //reverse sorting population on the basis of fitness
        Member.sortFitness(population);

        System.arraycopy(population, 0, newPopulation, 0, noOfIndividualsRequired);
//        System.out.println(newPopulation.length);
        for (int i = 0; i < newPopulation.length; i++) {
//            System.out.println("new population " + newPopulation[i].getFitness() + " " + newPopulation[i]);
        }

        return newPopulation;
    }

    //only for parent selection
    //it will return new array index 0  will contain 1st parent and 1 will contain 2nd parent
    public static Member[] randomSelection(Member[] population, int noOfRequiredIndividuals) {

        List<Member> parents = new ArrayList<>();

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
        return parents.toArray(new Member[parents.size()]);
    }

}

class Mathematics {

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
        return Mathematics.getInstance().nextDouble();
    }

    //get int random in min max range
    public static int getRandom(int min, int max) {

        return Mathematics.getInstance().nextInt(max + 1 - min) + min;
    }

    //get double random in min max range 
    public static double getRandom(double min, double max) {

        return min + (max - min) * Mathematics.getInstance().nextDouble();

    }

    //sorting chess boards on the basis of their commulative scores
    public static void sort(Member[] array) {
        int n = array.length;
        for (int j = 1; j < n; j++) {
            Member key = array[j];
            int i = j - 1;
            while ((i > -1) && (array[i].getFitness() > key.getFitness())) {
                array[i + 1] = array[i];
                i--;
            }
            array[i + 1] = key;
        }

    }

    public static double square(double d) {
        return java.lang.Math.pow(d, 2);
    }

}
