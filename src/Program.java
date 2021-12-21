import java.util.*;

public class Program {


    private final static int POPULATION_SIZE = 200;

    private final static int P_CROSS_OVER = (int) (POPULATION_SIZE * 0.7);
    private final static double P_MUTATION = 0.3;
    private final static double P_REPLACEMENT = 0.3;

    private static int epoch = 0;
    private final static int MAX_EPOCH = 2000000;


    public static void main(String[] args) {


        ArrayList<Chromosome> populationList;
        ArrayList<Chromosome> parentList;
        ArrayList<Chromosome> childList;

        populationList = initPopulation(POPULATION_SIZE);
        fitnessPopulation(populationList);

/*
        populationList.get(0).setX(1);
        populationList.get(0).setY(1);



    //    printFitness(populationList);

        parentList = randomSelectParent(populationList);
        //   parentList = ratingSelectParent(populationList);
        //   parentList = cuttingSelectParent(populationList);

    //    printFitness(parentList);


        childList = onePointCrossOver(parentList);
        // childList = uniformCrossOver(parentList);
        mutation(childList, P_MUTATION);

        printPopulation(parentList);
        printPopulation(childList);
        populationList = replacePopulation(parentList, childList);
        printPopulation(populationList);*/


/*
//

//



        //  childList=multiPointCrossOver(parentList);
        //  childList=uniformCrossOver(parentList);
        //   compareParentWithChild(parentList,childList);


//        System.out.println(childList.get(0).getChromosomeArray());
//        mutation(childList,P_MUTATION);
//        System.out.println(childList.get(0).getChromosomeArray());

*/


        if (populationList.get(0).getFitness() == 0) {
            System.out.println("epoch " + epoch + " : " +" x ="
                    + populationList.get(0).getX()+ " y "+populationList.get(0).getY());
        }
        else {

            while (epoch < MAX_EPOCH) {

                //parentList = randomSelectParent(populationList);
                parentList = ratingSelectParent(populationList);
                // parentList = cuttingSelectParent(populationList);


                 childList = onePointCrossOver(parentList);
                //childList = multiPointCrossOver(parentList);
                // childList = uniformCrossOver(parentList);

                mutation(childList, P_MUTATION);

                fitnessPopulation(childList);

                populationList = replacePopulation(populationList, childList);
                fitnessPopulation(populationList);

                System.out.println("epoch " + epoch++ + " : " +" x ="
                        + populationList.get(0).getX()+ " y "+populationList.get(0).getY());


                if (populationList.get(0).getFitness() == 0) {
                    System.out.println("found answer in epoch "+(epoch-1));
                    System.out.println("epoch " + epoch + " : " +" x ="
                            + populationList.get(0).getX()+ " y "+populationList.get(0).getY());
                    break;
                }
            }
        }


    }


    public static void printPopulation(ArrayList<Chromosome> population) {

        for (Chromosome chromosome : population) {
            System.out.println("x = "+chromosome.getX()+" , y = "+chromosome.getY());
        }

        System.out.println("size = "+population.size());

        System.out.println("///////////////////////////////////");
    }

    public static void printFitness(ArrayList<Chromosome> list) {
        for (Chromosome chromosome : list)
            System.out.println(chromosome.getFitness() + " , ");

        System.out.println("***************************************");

    }

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


            double sum1 = 0;
            double sum2 = 0;
            double fitness = 0;

                sum1 = Math.pow(chromosome.getX(), 2) + Math.pow(chromosome.getY(), 2);
                sum2 = Math.cos(2*Math.PI*chromosome.getX()) +Math.cos(2*Math.PI*chromosome.getY()) ;

            fitness = 20 - (20 * Math.exp(-0.2 * Math.sqrt(sum1/2))) + Math.E - Math.exp(sum2/2);


            chromosome.setFitness(fitness);

        }

        populationList.sort(new Comparator<Chromosome>() {
            @Override
            public int compare(Chromosome o1, Chromosome o2) {
                return Double.compare(o1.getFitness(), o2.getFitness());
            }
        });


    }

    /////////////////////////////////////////////////////////////////////////

   public static ArrayList<Chromosome> randomSelectParent(ArrayList<Chromosome> populationList) {

        ArrayList<Chromosome> randomParent = new ArrayList<>();

        for (int i = 0; i < P_CROSS_OVER; i++) {

            int randomIndex = generateRandomNumber(POPULATION_SIZE);
            randomParent.add(populationList.get(randomIndex));

        }


        return randomParent;

    }

    public static ArrayList<Chromosome> ratingSelectParent(ArrayList<Chromosome> populationList) {


        ArrayList<Chromosome> ratingParent = new ArrayList<>();

        for (int i = 0; i < P_CROSS_OVER; i++) {
            ratingParent.add(populationList.get(i));

        }

        return ratingParent;
    }

    public static ArrayList<Chromosome> cuttingSelectParent(ArrayList<Chromosome> populationList) {

        ArrayList<Chromosome> bestParent = new ArrayList<>();
        ArrayList<Chromosome> randomParent = new ArrayList<>();

        int bestSelectedSize = (int) (POPULATION_SIZE * 0.8);


        for (int i = 0; i < bestSelectedSize; i++) {

            bestParent.add(populationList.get(i));
        }


        for (int i = 0; i < P_CROSS_OVER; i++) {

            int randomIndex = generateRandomNumber(bestSelectedSize);

            randomParent.add(bestParent.get(randomIndex));
        }


        return randomParent;

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



            int randomNumber=generateRandomNumber(2);

            if(randomNumber==0){

                child_1.setX(parent_1.getX());
                child_2.setX(parent_2.getX());

            }else {

                child_1.setX(parent_2.getX());
                child_2.setX(parent_1.getX());
            }


            randomNumber=generateRandomNumber(2);

            if(randomNumber==0){

                child_1.setY(parent_1.getY());
                child_2.setY(parent_2.getY());

            }else {

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

            double randomProb = random.nextDouble() % 1;
            if (randomProb < 0)
                randomProb *= -1;


            if (randomProb < p_mutation) {



                int randomGeneIndex = generateRandomNumber(2);
                double randomValue = random.nextDouble()*30;


                if(randomGeneIndex==0){

                    chromosome.setX(randomValue);

                }else {

                    chromosome.setY(randomValue);
                }


            }


        }


    }

    /////////////////////////////////////////////////////////////////////////

    public static ArrayList<Chromosome> replacePopulation(ArrayList<Chromosome> parentList, ArrayList<Chromosome> childList) {


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


}
