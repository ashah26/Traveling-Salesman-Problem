import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;


class Graph{

	class Edge{
		int destination, distance;
		public Edge(int destination,int distance) {
			this.destination = destination;
			this.distance=distance;
		}
		@Override
		public String toString() {
			return (destination+","+distance);
		}
	}
	List<Edge> adjList[];
	public Graph(int vertices) {
		adjList = new LinkedList[vertices+1];
		for(int i=0; i< adjList.length;i++) {
			adjList[i] = new LinkedList<Edge>();
		}
	}
	@Override
	public String toString() {
		String result="";
		for (int i=0 ; i<adjList.length ;i++)
			result += i+"==>"+adjList[i]+"\n";
		return result;
	}

	void addEdge(int source, int dest, int weight) {
		adjList[source].add(0,new Edge(dest, weight));
	}

	int weight(int source, int destination) {
		for(Edge i : adjList[source] ){
			return i.distance;
		}
		return 0;
	}
}

public class TestGraph {
	static int maxBound = 0;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/**User Input Starts ***/
		Scanner s = new Scanner(System.in);
		System.out.println("Enter 1 for Simple And 2 for Sophisticated:");
		int algoSelection = s.nextInt();
		System.out.println("Enter 1 for Cost Function 1 or 2 for Cost Function 2 or 3 for Cost Function 3");
		int costFunction = s.nextInt();
		System.out.println("Enter number of vertices:");
		int vertices = s.nextInt();
		System.out.println("Enter MEB:");
		int meb = s.nextInt();
		/**User Input End ***/
		
		LinkedList<Integer> visited = new LinkedList<Integer>();
		Graph graph = new Graph(vertices);
		
		if(costFunction == 1) {
			graph = costFunction1(vertices, graph);
		}else if(costFunction == 2) {
			graph = costFunction2(vertices, graph);
		}else if(costFunction == 3) {
			graph = costFunction3(vertices, graph);
		}
		
		/** Simple Algorithm Starts **/
		if(algoSelection == 1) {
			
			int sum = 0;
			int jVertex = 0;
			int maxBound1 = 0;
	
			for(int i=1; i<vertices ; i++) {
				int min = graph.adjList[i].get(vertices-1).distance;
				if(meb == maxBound1) {
					break;
				}
				for(int j = vertices-1 ; j>0; j-- ) {
					int j1 = graph.adjList[i].get(j).destination;
					if(i == j1 && (!visited.contains(i) && !visited.contains(j))) {
						visited.add(i);
						maxBound1 += 1;
					}else {
						int d1= graph.adjList[i].get(j).distance;
						//to check array does not run out of indexes
						if(j-1 >= 0) {
							int d2 = graph.adjList[i].get(j-1).distance;
							if(meb>=maxBound1 && d2 != 0 && d1>d2 && !visited.contains(i)) {
								min = d2;
								jVertex =graph.adjList[i].get(j-1).destination;
								maxBound1 += 1;
							}else if(meb>=maxBound1 && (min == 0 || min>d1) && d1 !=0 && !visited.contains(j1)){
									min = d1;
									jVertex = graph.adjList[i].get(j).destination;
									maxBound1 += 1;
							}else if(meb>=maxBound1 && d1 == 0 && (min > d2 || !visited.contains(j))) {
								min = d2;
								jVertex =graph.adjList[i].get(j-1).destination;
								maxBound1 += 1;
							}
						}
						maxBound1 +=1;
					}
				}
				visited.add(jVertex);
				sum += min;
			}
			System.out.println("meb==="+maxBound1);
			System.out.println("COST=="+sum);
			System.out.println("Vertices vivited=="+visited);
			/** Simple Algorithm ENDS **/
		}else {
			/** Sophisticated Algorithm Starts  **/
			/** Creating a new population  **/
			
			Population pop = new Population(vertices, true,graph);
			System.out.println("Initial Solution :"+pop.getFittest());
	        System.out.println("Initial distance: " + pop.getFittest().getWeight(Count.counter));

	        /** Population is generated for 100 generations **/
 	        pop = GeneticAlgorithm.produceNewGeneration(pop,vertices,graph);
	        for (int i = 0; i < 100; i++) {
	            pop = GeneticAlgorithm.produceNewGeneration(pop,vertices,graph);
	        }
	        System.out.println("Final distance: " + pop.getFittest().getWeight(Count.counter));
	        System.out.println("Final Solution:");
	        System.out.println(pop.getFittest());
	        System.out.println("MEB=="+Count.counter);
	    }
			/** Sophisticated Algorithm End  **/
	}
	


	/*** Cost Function Start  ***/
	private static Graph costFunction2(int vertices, Graph graph) {
		// TODO Auto-generated method stub
		int i=1;
		while(i<=vertices) {
			int j=1;
			while(j<=vertices) {
				if(i==j) {
					graph.addEdge(i, j, 0);
				}else if(i+j<10){
					if(i>j) {
						graph.addEdge(i, j,(i-j)+4);
					}else {
						graph.addEdge(i, j, (j-i)+4);
					}
				}else if((i+j)%11 == 0) {
					graph.addEdge(i, j, 3);
				}else {
					graph.addEdge(i, j, (int) (Math.pow(i-j, 2)+10));
				}
				j++;
			}
			i++;
		}
		return graph;
	}

	private static Graph costFunction3(int vertices, Graph graph) {
		// TODO Auto-generated method stub
		int i=1;
		while(i<=vertices) {
			int j=1;
			while(j<=vertices) {
				if(i==j) {
					graph.addEdge(i, j, 0);
				}else {
					graph.addEdge(i, j, (int) (Math.pow(i+j, 2)));
				}
				j++;
			}
			i++;
		}
		return graph;
	}

	private static Graph costFunction1(int vertices, Graph graph) {
		// TODO Auto-generated method stub

		int i=1;
		while(i<=vertices) {
			int j=1;
			while(j<=vertices) {
				if(i==j) {
					graph.addEdge(i, j, 0);
				}else if(i<3 && j< 3){
					graph.addEdge(i, j, 1);
				}else if(i<3) {
					graph.addEdge(i, j, 200);
				}else if(j<3) {
					graph.addEdge(i, j, 200);
				}else if((i%7)==(j%7)) {
					graph.addEdge(i, j, 2);
				}else {
					if(i>j) {
						graph.addEdge(i, j, (i-j)+3);
					}else {
						graph.addEdge(i, j, (j-i)+3);
					}
				}
				j++;
			}
			i++;
		}

		return graph;
	}
	
	/*** Cost Function End  ***/
}


