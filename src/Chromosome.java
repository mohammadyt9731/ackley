import java.util.Random;

public class Chromosome {



    private double x;
    private double y;

    private double fitness;


    public void generateRandomChromosome() {
        Random random=new Random();

        this.x=(int)(random.nextDouble()*30);
        this.y=(int)(random.nextDouble()*30);

    }



    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
}
