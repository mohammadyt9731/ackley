import java.util.Random;

public class Chromosome {


    private float x;
    private float y;

    private float fitness;


    public void generateRandomChromosome() {
        Random random = new Random();

        this.x = (float) (Math.round((random.nextFloat() * 60 - 30)*1000d)/1000d);
        this.y = (float) (Math.round((random.nextFloat() * 60 - 30)*1000d)/1000d);

    }


    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getFitness() {
        return fitness;
    }

    public void setFitness(float fitness) {
        this.fitness = fitness;
    }
}
