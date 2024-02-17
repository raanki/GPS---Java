package src;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Classe qui implémente l'algorithme de Prim pour trouver un arbre couvrant de poids minimum (ACPM).
 */
public class PrimAlgorithm {

    /**
     * Classe interne Pair pour stocker les données du sommet.
     */
    public static class Pair implements Comparable<Pair> {
        public int vertex, weight, parent;

        /**
         * Constructeur pour initialiser un objet Pair.
         *
         * @param vertex Le numéro du sommet.
         * @param weight Le poids pour atteindre ce sommet depuis son parent.
         * @param parent Le parent de ce sommet dans l'ACPM.
         */
        public Pair(int vertex, int weight, int parent) {
            this.vertex = vertex;
            this.weight = weight;
            this.parent = parent;
        }

        /**
         * Comparaison de deux objets Pair en fonction de leur poids.
         *
         * @param other L'autre objet Pair à comparer.
         * @return Résultat de la comparaison (-1, 0, ou 1).
         */
        public int compareTo(Pair other) {
            return Integer.compare(this.weight, other.weight);
        }
    }

    /**
     * Méthode pour exécuter l'algorithme de Prim.
     *
     * @param edges    La liste des arêtes du graphe.
     * @param vertices L'ensemble des sommets du graphe.
     */
    public static void prim(List<Edge> edges, Set<Integer> vertices) {
        int n = vertices.size();
        boolean[] visited = new boolean[n];
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        List<Edge> mst = new ArrayList<>();

        int startVertex = vertices.iterator().next();
        pq.add(new Pair(startVertex, 0, -1));

        System.out.println("Démarrage de l'algorithme de Prim à partir du sommet: " + startVertex);

        while (!pq.isEmpty()) {
            Pair pair = pq.poll();
            int u = pair.vertex;

            if (!visited[u]) {
                visited[u] = true;

                if (pair.parent != -1) {
                    mst.add(new Edge(pair.parent, u, pair.weight));
                    System.out.println("Ajout de l'arête au MST: " + pair.parent + " - " + u + " avec poids: " + pair.weight);
                }

                for (Edge edge : edges) {
                    if (edge.source == u && !visited[edge.dest]) {
                        pq.add(new Pair(edge.dest, edge.weight, u));
                    }
                    if (edge.dest == u && !visited[edge.source]) {
                        pq.add(new Pair(edge.source, edge.weight, u));
                    }
                }
            }
        }

        System.out.println("\nArbre Couvrant de Poids Minimum final:");
        int totalWeight = 0;
        for (Edge edge : mst) {
            System.out.println("Arête: " + edge.source + " - " + edge.dest + ", Poids: " + edge.weight);
            totalWeight += edge.weight;
        }
        System.out.println("Poids total de l'ACPM: " + totalWeight);
    }
}
