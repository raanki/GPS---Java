package src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Classe pour vérifier la connexité d'un graphe.
 */
public class GraphConnectivity {

    /**
     * Effectue un parcours en profondeur (DFS) sur le graphe à partir d'un sommet donné.
     *
     * @param start   Sommet de départ.
     * @param visited Ensemble des sommets visités.
     * @param edges   Liste des arêtes du graphe.
     */
    private static void dfs(int start, Set<Integer> visited, List<Edge> edges) {
        visited.add(start);

        for (Edge edge : edges) {
            if (edge.source == start && !visited.contains(edge.dest)) {
                dfs(edge.dest, visited, edges);
            }
            if (edge.dest == start && !visited.contains(edge.source)) {
                dfs(edge.source, visited, edges);
            }
        }
    }

    /**
     * Vérifie si le graphe est connexe.
     *
     * @param edges   Liste des arêtes du graphe.
     * @param vertices Ensemble des sommets du graphe.
     * @return Vrai si le graphe est connexe, faux sinon.
     */
    public static boolean isConnected(List<Edge> edges, Set<Integer> vertices) {
        if (vertices.isEmpty()) {
            return false;
        }

        Set<Integer> visited = new HashSet<>();
        int startVertex = vertices.iterator().next();  // Prendre un sommet arbitraire pour commencer le DFS

        dfs(startVertex, visited, edges);

        return visited.equals(vertices);  // Le graphe est connexe si tous les sommets ont été visités
    }
}
