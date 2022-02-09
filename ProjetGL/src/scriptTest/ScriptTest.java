package scriptTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.util.HashSet;
import java.util.Set;

import dijkstra.Graph;
import dijkstra.Edge;

public class ScriptTest {
	public static void main(String[] args) throws Exception {
		Graph graphe;
		int vCount = 0;
		Set<Edge> arcs = new HashSet<>();

		File testFile = new File("src/scriptTest/test.txt");

		//Ouverture d'une lecture
		FileReader fr = new FileReader(testFile);
		BufferedReader br = new BufferedReader(fr);

		StringBuffer sb = new StringBuffer();
		String line;

		while((line = br.readLine()) != null)
		{
			if (line.equals("")) {
				//lancer les tests
				graphe = new Graph(vCount);
				for (Edge arc : arcs)
					graphe.addEdge(arc);
				// print Graph
		        graphe.printGraph();
				vCount = 0;
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

		//sb.append(line);
		br.close();
	}
}