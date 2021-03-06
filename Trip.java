import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Trip {
     ArrayList trip = new ArrayList<Integer>();
    int maxBound = 0;
    Graph objGraph =new Graph(0);
    /***  For creating blank trips of the size of vertices ***/
    public Trip(int vertices,Graph graph){
        for (int i = 1; i <= vertices; i++) {
            trip.add(0);
        }
        objGraph = graph;
    }
    
    public Trip(ArrayList trip){
        this.trip = trip;
    }

    /***  this method creates a trip and shuffles it in the end***/
    public void generateIndividual(int vertices) {
        for (int cityIndex = 0; cityIndex < vertices; cityIndex++) {
          setCity(cityIndex, cityIndex+1);
        }
        Collections.shuffle(trip);
    }

    double fitness = 0;
    int distance = 0;
    
    /*** it sets the city at a particular position in a particular trip
     * Also if we modify the trip these variables need to be reset****/
    public void setCity(int tripPosition, int city) {
        trip.set(tripPosition, city);
        fitness = 0;
        distance = 0;
    }


    /** to get a particular city from a partiular trip**/
    public int getCity(int tripPosition) {
        return (int)trip.get(tripPosition);
    }
    
    /** The fitness Funtction**/
    public double getFitness() {
        if (fitness == 0) {
            fitness = 1/(double)getWeight(Count.counter);
        }
        return fitness;
    }
    
    /** this method gives us the weight between two vertices that we did using cost function **/
    public int getWeight(int maxBound){
        if (distance == 0) {
            int tripDistance = 0;

            for (int cityIndex=0; cityIndex < tripSize(); cityIndex++) {
            	if(Count.counter == 200000) {
            	   break;
               }
                int fromVertex = getCity(cityIndex);
                int toVertex;
                if(cityIndex+1 < tripSize()){
                	toVertex = getCity(cityIndex+1);
                }
                else{
                	toVertex = getCity(0);
                }
                tripDistance += calculateDistance(fromVertex, toVertex) ;
               
                Count.counter += 1;
            }
            distance = tripDistance;
        }
        return distance;
    }


	@Override
    	public String toString() {
        	String geneString = "-";
        	for (int i = 0; i < tripSize(); i++) {
            		geneString += getCity(i)+"-";
        	}
        return geneString;
    }
    /*** this is for getting the size of a particular trip **/
    public int tripSize() {
        return trip.size();
    }
    
    /** check if a particular city is present in a particular trip **/
    public boolean containsCity(int city){
        return trip.contains(city);
    }
    
    /** to obtain weight calculated from cost function  ***/
    public int calculateDistance(int source,int destination) {
    	int weight=0;
    	List<Graph.Edge> edgeList =objGraph.adjList[source];
    	for(int i =0; i< edgeList.size(); i++) {
    		if(destination == edgeList.get(i).destination) {
    			weight = edgeList.get(i).distance;
    			break;
    		}
    	}
    	return weight;
    }
    
    

}
