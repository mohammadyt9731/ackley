import java.util.*;

public class Program {


    private final static int POPULATION_SIZE = 1000;

    private final static double P_CROSS_OVER = 0.8;
    private final static double P_MUTATION = 0.1;
    private final static double P_REPLACEMENT = 0.3;

    private final static int PARENT_LIST_SIZE = (int) (POPULATION_SIZE * P_CROSS_OVER);

    private static int epoch = 0;
    private final static int MAX_EPOCH = 10000;


    public static void main(String[] args) {

        ArrayList<Chromosome> populationList;
        ArrayList<Chromosome> parentList;
        ArrayList<Chromosome> childList;



        populationList = initPopulation(POPULATION_SIZE);
        fitnessPopulation(populationList);

        /*

        //  printPopulation(populationList);

        printFitness(populationList);

        parentList = randomSelectParent(populationList);
        //   parentList = ratingSelectParent(populationList);
        //   parentList = cuttingSelectParent(populationList);

        // printPopulation(parentList);

        fitnessPopulation(parentList);
        printFitness(parentList);


        childList = onePointCrossOver(parentList);
        // childList = uniformCrossOver(parentList);

        mutation(childList, P_MUTATION);
        // printPopulation(childList);

        fitnessPopulation(childList);
        printFitness(childList);

        populationList = replacePopulation(parentList, childList);
     //   printPopulation(populationList);

        fitnessPopulation(populationList);
        printFitness(populationList);

        //  childList=multiPointCrossOver(parentList);
        //  childList=uniformCrossOver(parentList);
        //   compareParentWithChild(parentList,childList);




*/

        if (populationList.get(0).getFitness() == 0) {
            System.out.println("epoch " + epoch + " : " + " x ="
                    + populationList.get(0).getX() + " y " + populationList.get(0).getY());
            System.out.println("found answer in epoch " + (epoch ));
            }
        else {

            while (epoch < MAX_EPOCH) {

                 parentList = randomSelectParent(populationList);
                // parentList = ratingSelectParent(populationList);
                // parentList = cuttingSelectParent(populationList);

                Collections.shuffle(parentList);

                childList = onePointCrossOver(parentList);
                //childList = uniformCrossOver(parentList);

                mutation(childList, P_MUTATION);

                fitnessPopulation(childList);

                 populationList = steadyStateReplacement(populationList, childList);
                // populationList = generationalReplacement(populationList, childList);
                fitnessPopulation(populationList);

                System.out.println("epoch " + epoch++ + " : " + " x ="
                        + populationList.get(0).getX() + " y " + populationList.get(0).getY());


                if (populationList.get(0).getFitness() == 0) {
                    System.out.println("found answer in epoch " + (epoch - 1));
                    break;
                }
            }
        }


    }

/*
    public static void printPopulation(ArrayList<Chromosome> population) {

        for (Chromosome chromosome : population) {
            System.out.println("x = " + chromosome.getX() + " , y = " + chromosome.getY());
        }

        System.out.println("size = " + population.size());

        System.out.println("///////////////////////////////////");
    }

    public static void printFitness(ArrayList<Chromosome> list) {
        for (Chromosome chromosome : list)
            System.out.println(chromosome.getFitness() + " , ");

        System.out.println("***************************************");

    }
*/

    public static int generateRandomNumber(int modular) {

        Random random = new Random();
        int randomNumber = random.nextInt() % modular;
        if (randomNumber < 0)
            randomNumber *= -1;

        return randomNumber;
    }

    /////////////////////////////////////////////////////////////////////////
    public static ArrayList<Chromosome> initPopulation(int populationSize) {

        ArrayList<Chromosome> population = new ArrayList<>();

        for (int i = 0; i < populationSize; i++) {

            Chromosome chromosome = new Chromosome();
            chromosome.generateRandomChromosome();
            population.add(chromosome);

        }

        return population;


    }

    public static void fitnessPopulation(ArrayList<Chromosome> populationList) {

        for (Chromosome chromosome : populationList) {

            float sum1 = 0;
            float sum2 = 0;
            float fitness = 0;

            sum1 = (float) (Math.pow(chromosome.getX(), 2) + Math.pow(chromosome.getY(), 2));
            sum2 = (float) (Math.cos(2 * Math.PI * chromosome.getX()) + Math.cos(2 * Math.PI * chromosome.getY()));

            fitness = (float) (20 - (20 * Math.exp(-0.2 * Math.sqrt(sum1 / 2))) + Math.E - Math.exp(sum2 / 2));


            chromosome.setFitness(fitness);

        }

        populationList.sort(new Comparator<Chromosome>() {
            @Override
            public int compare(Chromosome o1, Chromosome o2) {
                return Float.compare(o1.getFitness(), o2.getFitness());
            }
        });


    }

    /////////////////////////////////////////////////////////////////////////

    public static ArrayList<Chromosome> randomSelectParent(ArrayList<Chromosome> populationList) {

        ArrayList<Chromosome> randomParentList = new ArrayList<>();


        for (int i = 0; i < PARENT_LIST_SIZE; i++) {

            int randomIndex = generateRandomNumber(POPULATION_SIZE);
            randomParentList.add(populationList.get(randomIndex));

        }


        return randomParentList;

    }

    public static ArrayList<Chromosome> ratingSelectParent(ArrayList<Chromosome> populationList) {

        ArrayList<Chromosome> ratingParentLIst = new ArrayList<>();

        for (int i = 0; i < PARENT_LIST_SIZE; i++) {
            ratingParentLIst.add(populationList.get(i));

        }

        return ratingParentLIst;
    }

    public static ArrayList<Chromosome> cuttingSelectParent(ArrayList<Chromosome> populationList) {

        ArrayList<Chromosome> bestParentList = new ArrayList<>();
        ArrayList<Chromosome> cuttingParentList = new ArrayList<>();


        for (int i = 0; i < PARENT_LIST_SIZE; i++) {
            bestParentList.add(populationList.get(i));
        }


        for (int i = 0; i < PARENT_LIST_SIZE; i++) {

            int randomIndex = generateRandomNumber(PARENT_LIST_SIZE);

            cuttingParentList.add(bestParentList.get(randomIndex));
        }


        return cuttingParentList;

    }

    /////////////////////////////////////////////////////////////////////////

    public static ArrayList<Chromosome> onePointCrossOver(ArrayList<Chromosome> parentList) {

        ArrayList<Chromosome> childList = new ArrayList<>();

        for (int k = 0; k < parentList.size(); k += 2) {


            Chromosome parent_1 = parentList.get(k);
            Chromosome parent_2 = parentList.get(k + 1);
            Chromosome child_1 = new Chromosome();
            Chromosome child_2 = new Chromosome();


            child_1.setX(parent_1.getX());
            child_2.setX(parent_2.getX());

            child_1.setY(parent_2.getY());
            child_2.setY(parent_1.getY());


            childList.add(child_1);
            childList.add(child_2);

        }


        return childList;

    }

    public static ArrayList<Chromosome> uniformCrossOver(ArrayList<Chromosome> parentList) {

        ArrayList<Chromosome> childList = new ArrayList<>();

        for (int k = 0; k < parentList.size(); k += 2) {


            Chromosome parent_1 = parentList.get(k);
            Chromosome parent_2 = parentList.get(k + 1);
            Chromosome child_1 = new Chromosome();
            Chromosome child_2 = new Chromosome();


            int randomNumber = generateRandomNumber(2);

            if (randomNumber == 0) {

                child_1.setX(parent_1.getX());
                child_2.setX(parent_2.getX());

            } else {

                child_1.setX(parent_2.getX());
                child_2.setX(parent_1.getX());
            }


            randomNumber = generateRandomNumber(2);

            if (randomNumber == 0) {

                child_1.setY(parent_1.getY());
                child_2.setY(parent_2.getY());

            } else {

                child_1.setY(parent_2.getY());
                child_2.setY(parent_1.getY());
            }


            childList.add(child_1);
            childList.add(child_2);

        }


        return childList;

    }


    /////////////////////////////////////////////////////////////////////////

    public static void mutation(ArrayList<Chromosome> childList, double p_mutation) {

        Random random = new Random();

        for (Chromosome chromosome : childList) {

            float randomProb = random.nextFloat();
            if (randomProb < 0)
                randomProb *= -1;


            if (randomProb < p_mutation) {


                int randomGeneIndex = generateRandomNumber(2);
                float randomValue = (float) (Math.round((random.nextFloat() * 60 - 30)*1000d)/1000d);


                if (randomGeneIndex == 0) {

                    chromosome.setX(randomValue);

                } else {

                    chromosome.setY(randomValue);
                }


            }


        }


    }

    /////////////////////////////////////////////////////////////////////////

    public static ArrayList<Chromosome> steadyStateReplacement(ArrayList<Chromosome> parentList, ArrayList<Chromosome> childList) {


        ArrayList<Chromosome> newPopulation = new ArrayList<>();


        int parentSelectedSize = (int) (POPULATION_SIZE * (1 - P_REPLACEMENT));
        for (int i = 0; i < parentSelectedSize; i++)
            newPopulation.add(parentList.get(i));


        int childSelectedSize = (int) (POPULATION_SIZE * P_REPLACEMENT);

        if (parentSelectedSize + childSelectedSize < POPULATION_SIZE)
            childSelectedSize++;

        for (int i = 0; i < childSelectedSize; i++)
            newPopulation.add(childList.get(i));


        return newPopulation;


    }


    public static ArrayList<Chromosome> generationalReplacement(ArrayList<Chromosome> parentList, ArrayList<Chromosome> childList) {


        ArrayList<Chromosome> newPopulation = new ArrayList<>();


        fitnessPopulation(parentList);
        int parentSelectedSize =  (POPULATION_SIZE - PARENT_LIST_SIZE);
        for (int i = 0; i < parentSelectedSize; i++)
            newPopulation.add(parentList.get(i));


        int childSelectedSize = PARENT_LIST_SIZE;

        if (parentSelectedSize + childSelectedSize < POPULATION_SIZE)
            childSelectedSize++;

        for (int i = 0; i < childSelectedSize; i++)
            newPopulation.add(childList.get(i));


        return newPopulation;


    }


}
