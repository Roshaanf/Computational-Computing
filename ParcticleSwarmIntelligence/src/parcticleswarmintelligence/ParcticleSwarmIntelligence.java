/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parcticleswarmintelligence;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Roshaann 2.7 gpa
 */
public class ParcticleSwarmIntelligence {

    public static final double MINX = -2.0;
    public static final double MAXX = 2.0;
    public static final double MINY = -1.0;
    public static final double MAXY = 3.0;

    public static final double C1 = 1.5;
    public static final double C2 = 1.5;

    public static Particle globalBest;

    public static final int N = 25;
    static ArrayList<Particle> swarm;

    public static void main(String[] args) {
        // TODO code application logic here

        swarm = new ArrayList<>();

        //initializing n particles
        for (int i = 0; i < N; i++) {
           
            swarm.add(new Particle());

            //calculating perssonal best
            swarm.get(i).calculatePersonalBest();
        }

        ///calculating global best
       globalBest= Particle.calculateGlobalBest(swarm, globalBest);
       

       int j=1;
        while (j<10) {

            for (Particle particle : swarm) {

                //setting x velocity
                particle.setVelocityX(particle.getVelocityX()
                        + (C1 * Mathematics.getRandom()) * (particle.getPersonalBest().getX() - particle.getX())
                        + (C2 * Mathematics.getRandom()) * (globalBest.getX() - particle.getX()));
//                System.out.println("VELOCITYY   XXX"+particle.getVelocityX());
                //setting y velocity
                particle.setVelocityY(particle.getVelocityY()
                        + (C1 * Mathematics.getRandom()) * (particle.getPersonalBest().getY() - particle.getY())
                        + (C2 * Mathematics.getRandom()) * (globalBest.getY() - particle.getY()));
                
                //setting new x and y
                particle.setX(particle.getX() + particle.getVelocityX());
                particle.setY(particle.getY() + particle.getVelocityY());

                //calcualte fitness
                particle.calculateFitness();

                //calculating personal best
                particle.calculatePersonalBest();
            }
                    System.out.println("GENERATION "+j++ +" "+globalBest.getFitness());
            //calculating global best
            globalBest=Particle.calculateGlobalBest(swarm, globalBest);

            for (int i = 0; i < swarm.size(); i++) {
                System.out.println(swarm.get(i).getFitness()+"   "+ swarm.get(i).getX()+"  "+swarm.get(i).getY());
            }
        }
    }
}

class Particle {

    private double x;
    private double y;
    private double velocityX;
    private double velocityY;
    private Particle personalBest;
    private double fitness;

    public Particle() {

        personalBest = null;

        //setting x and y
        this.x = Mathematics.getRandom(ParcticleSwarmIntelligence.MINX, ParcticleSwarmIntelligence.MAXX);
        this.y = Mathematics.getRandom(ParcticleSwarmIntelligence.MINY, ParcticleSwarmIntelligence.MAXY);

        //setting fitness of each individual
        calculateFitness();

    }

    public Particle(double x, double y) {

        this.x = x;
        this.y = y;

        calculateFitness();
    }

    public void calculateFitness() {
        java.lang.Math.pow(x, x);
        this.fitness = 100
                * Mathematics.square(Mathematics.square(this.x) - this.y)
                + Mathematics.square(1 - this.x);

    }

    //calculating global best
    public static Particle calculateGlobalBest(ArrayList<Particle> swarm, Particle globalBest) {

        if (globalBest != null) {
            for (int i = 1; i < swarm.size(); i++) {
                if (swarm.get(i).getFitness() > globalBest.getFitness()) {

                    ///making global best
                    globalBest = new Particle(swarm.get(i).getX(), swarm.get(i).getY());

                }
            }
        } //for the first time
        else {
            globalBest = new Particle(swarm.get(0).getX(), swarm.get(0).getVelocityY());
        }

        return globalBest;
    }

    public void calculatePersonalBest() {

        if (personalBest != null) {
            if (this.getFitness() > this.personalBest.getFitness()) {
                this.personalBest = new Particle(this.x, this.y);
            }
        } //for the first time
        else {
            personalBest = new Particle(this.x, this.y);
        }
    }

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {

        if (x > ParcticleSwarmIntelligence.MAXX) {
            this.x = ParcticleSwarmIntelligence.MAXX; 
        } else if (x < ParcticleSwarmIntelligence.MINX) {
            this.x = ParcticleSwarmIntelligence.MINX; 
        }
        else{            

            this.x=x;
        }
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {

        if (y > ParcticleSwarmIntelligence.MAXY) {
            this.y = ParcticleSwarmIntelligence.MAXY; 
        } else if (y < ParcticleSwarmIntelligence.MINY) {
            this.y = ParcticleSwarmIntelligence.MINY;
        }
        else{
            
            this.y=y;
        }
    }

    public Particle getPersonalBest() {
        return personalBest;
    }

    public void setPersonalBest(Particle best) {
        this.personalBest = best;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
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
}
