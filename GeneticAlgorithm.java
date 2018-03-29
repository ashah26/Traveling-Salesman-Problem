
public class GeneticAlgorithm {
   
    
    private static final boolean exist = true;
    static int noOfVertex =0;
    static Graph objGraph = new Graph(0);

    /****In this method we have called methods for crossover and mutation
     * First we check if we best trip already there
     * If not we select two parents(Cities) for producing new child trip  ***/
    public static Population produceNewGeneration(Population pop,int vertices,Graph graph) {
    	noOfVertex =vertices;
    	objGraph = graph;
        Population newPopulation = new Population(pop.tripsSize(), false,graph);

        int existingOffset = 0;
        if (exist) {
            newPopulation.saveTrip(0, pop.getFittest());
            existingOffset = 1;
        }

        for (int i = existingOffset; i < newPopulation.tripsSize(); i++) {
            Trip parent1 = tripsSelection(pop,graph);
            Trip parent2 = tripsSelection(pop,graph);

            Trip child = crossover(parent1, parent2);

            newPopulation.saveTrip(i, child);
        }

        for (int i = existingOffset; i < newPopulation.tripsSize(); i++) {
            mutate(newPopulation.getTrip(i));
        }

        return newPopulation;
    }

    /**** This method is used to crossover to a set of parents and creates child  
     * the parents obtained from tripsSelection method are used for creating new child
     * for the first only we will get start and end position of the trip to get only some cities of the trip
     * and for the second we compare the cities numbers if they are present they will not be included again and if not they will be added 
     * according to blank spaces available ****/
    
    public static Trip crossover(Trip parent1, Trip parent2) {
        Trip child = new Trip(noOfVertex,objGraph);

        int startPosition = (int) (Math.random() * parent1.tripSize());
        int endPosition = (int) (Math.random() * parent1.tripSize());

        for (int i = 0; i < child.tripSize(); i++) {
            if (startPosition < endPosition && i > startPosition && i < endPosition) {
                child.setCity(i, parent1.getCity(i));
            } 
            else if (startPosition > endPosition) {
                if (!(i < startPosition && i > endPosition)) {
                    child.setCity(i, parent1.getCity(i));
                }
            }
        }

        for (int i = 0; i < parent2.tripSize(); i++) {
            if (!child.containsCity(parent2.getCity(i))) {
                for (int j = 0; j < child.tripSize(); j++) {
                    if (child.getCity(j) == 0 ) {
                        child.setCity(j, parent2.getCity(i));
                        break;
                    }
                }
            }
        }
        return child;
    }

     final static double mutation = 0.015;
    /*** This method mutates a trip using swap mutation
     * applying loop it generates random number and compare with mutation 
     * after that it generates second random position
     * and from both that positions we get the city number to swap
     * @param trip
     */
    public static void mutate(Trip trip) {
        for(int tripPos1=0; tripPos1 < trip.tripSize(); tripPos1++){
            if(Math.random() < mutation){
                int tripPos2 = (int) (trip.tripSize() * Math.random());

                int city1 = trip.getCity(tripPos1);
                int city2 = trip.getCity(tripPos2);

                trip.setCity(tripPos2, city1);
                trip.setCity(tripPos1, city2);
            }
        }
    }

     final static int populationSize= 5;
    /***** This method selects candidate trip for crossover 
     * first it creates the population of cities and the it randomly selects one city from it
     * after that it calculates the the fitness of the city and returns the most fittest ****/
    public static Trip tripsSelection(Population pop,Graph graph) {
        Population trips = new Population(populationSize, false,graph);
        for (int i = 0; i < populationSize; i++) {
            int randomId = (int) (Math.random() * pop.tripsSize());
            trips.saveTrip(i, pop.getTrip(randomId));
        }
        Trip fittest = trips.getFittest();
        return fittest;
    }
}
