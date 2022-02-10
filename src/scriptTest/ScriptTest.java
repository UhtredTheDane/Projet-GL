package scriptTest;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import dijkstra.Edge;
import dijkstra.Graph;
import dijkstra.TestGraphs;

public class ScriptTest {
	public static void main(String[] args) throws Exception {
		Graph graphe = null;
		List<Graph> list_graph = new LinkedList<Graph>();
		List<String> list_oracle = new LinkedList<>();
		int vCount = 0;
		Set<Edge> arcs = new HashSet<>();

		File testFile = new File("src/scriptTest/test.txt");

		//Ouverture d'une lecture
		FileReader fr = new FileReader(testFile);
		BufferedReader br = new BufferedReader(fr);

		String line;

		while((line = br.readLine()) != null)
		{
			if (line.equals("")) {
				//lancer les tests
				graphe = new Graph(vCount);
				for (Edge arc : arcs)
					graphe.addEdge(arc);
				vCount = 0;
				list_graph.add(graphe);
			}
			else {
				vCount++;
				char cara;
				int index = 0;
				int startVertex = 0, endVertex = 0;
				int beginIndex, endIndex;
				float weight;

				do {
					cara = line.charAt(index);
					index++;
				}while (cara != ':');

				String arcLine = line.substring(index + 1).replace(" ", "");;

				beginIndex = 0;
				endIndex = 0;
				
				for(index = 0; index < arcLine.length(); index++) {
					cara = arcLine.charAt(index);
					if (cara == '-' || cara == '(' || cara == ')') {
						if (index > beginIndex) {
							endIndex = index;
							String mainVertex = arcLine.substring(beginIndex, endIndex);
							switch(cara) {
							case '-':	startVertex = Integer.parseInt(mainVertex);
							break;
							case '(':	endVertex = Integer.parseInt(mainVertex);
							break;
							case ')':	weight = Float.parseFloat(mainVertex);
										arcs.add(new Edge(startVertex, endVertex, weight));
							break;
							// Ne rien faire en cas de default
							}

							if (index + 1 <= arcLine.length())
								beginIndex = index + 1;
							else {
								//TODO Gestion des erreurs
								System.out.println("Erreur dans le format");
								System.exit(1);
							}
						}
						else {
							System.out.println("Erreur dans le format");
							System.exit(1);
						}
					}
				}
			}
		}
		
		File oracleFile = new File("src/scriptTest/oracle.txt");

		//Ouverture d'une lecture
		FileReader fr2 = new FileReader(oracleFile);
		BufferedReader br2 = new BufferedReader(fr2);
		String line2;
		String oracle = "";
		
		while((line2 = br2.readLine()) != null)
		{
			if (line2.equals("")) {
				list_oracle.add(oracle);
			}
			else {
				oracle += line2 + "\n";
			}
		}
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    PrintStream ps = new PrintStream(baos);
	    PrintStream old = System.out;
	    int index = 0;
	    
	    for(ListIterator<Graph> iterateur = list_graph.listIterator(); iterateur.hasNext() && index < list_oracle.size();) {
	    	System.setOut(ps);
	    	TestGraphs.Dijkstra(iterateur.next(), 0);
	    	String test1 = baos.toString();    
	    	
	    	System.out.flush();
	    	System.setOut(old);	
	    	
	    	if(test1.contentEquals(list_oracle.get(index))) 
	    		System.out.println("test " + index + " pass");
	    	else
	    		System.out.println("test " + index + " fail");
	    }
		br.close();
	}
	
}