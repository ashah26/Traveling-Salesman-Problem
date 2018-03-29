
public class Population {
    Trip[] trips;
    int maxBound =0;
    /** this constructor helps to create a population of all the trips  **/
    public Population(int vertices, boolean initialise,Graph graph) {
        trips = new Trip[vertices];
        if (initialise) {
            for (int i = 0; i < tripsSize(); i++) {
                Trip newTrip = new Trip(vertices,graph);
                newTrip.generateIndividual(vertices);
                saveTrip(i, newTrip);
            }
        }
    }
   
    /** Saves the trip in the population at a particular index **/
    public void saveTrip(int index, Trip trip) {
        trips[index] = trip;
    }
    
    /*** Returns a particular trip from all trips  ***/
    public Trip getTrip(int index) {
        return trips[index];
    }
    
    /** With this method population size**/
    public int tripsSize() {
        return trips.length;
    }

    /** In this mehtod we can get the best trip in all trips  **/
    public Trip getFittest() {
        Trip fittest = trips[0];
        // Loop through individuals to find fittest
        for (int i = 1; i < tripsSize(); i++) {
            if (fittest.getFitness() <= getTrip(i).getFitness()) {
                fittest = getTrip(i);
            }
        }
        return fittest;
    }

    
    /** Returns meb **/
    public int meb() {
    	return maxBound;
    }
}
