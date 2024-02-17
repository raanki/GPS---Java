package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classe implémentant l'algorithme de Bellman-Ford pour trouver le chemin le plus court dans un graphe pondéré.
 */
public class BellmanFord {

    /**
     * Méthode récursive pour obtenir le chemin à partir de l'arbre de chemin minimum stocké dans parent[].
     * @param parent Le tableau des sommets parents.
     * @param vertex Le sommet pour lequel trouver le chemin.
     * @param path La liste stockant le chemin.
     */
    static void getPath(int parent[], int vertex, List<Integer> path)
    {
        if (vertex < 0) {
            return;
        }

        getPath(parent, parent[vertex], path);
        path.add(vertex);
    }

    /**
     * Exécute l'algorithme de Bellman-Ford sur un graphe pour trouver le chemin le plus court entre un sommet source et un sommet destination.
     *
     * @param edges La liste des arêtes du graphe. Chaque arête est représentée par une instance de la classe Edge contenant les sommets source, destination et le poids.
     * @param source Le sommet d'origine à partir duquel calculer les chemins les plus courts.
     * @param destination Le sommet destination pour lequel on veut trouver le chemin le plus court. Si le sommet destination est égal au sommet source, la méthode ne fera rien.
     * @param n Le nombre total de sommets dans le graphe.
     *
     * Utilisation :
     * - La méthode commence par initialiser deux tableaux `distance` et `parent` pour stocker respectivement la distance minimale de la source à chaque sommet et le prédécesseur de chaque sommet sur le chemin le plus court.
     * - Ensuite, elle exécute l'étape de relaxation V-1 fois, où V est le nombre de sommets dans le graphe.
     * - Après cela, la méthode vérifie l'existence de cycles de poids négatif dans le graphe.
     * - Finalement, si un chemin le plus court est trouvé du sommet source au sommet destination, la méthode affiche la distance et le chemin.
     */
    public static void bellmanFord(List<Edge> edges, int source, int destination, int n)
    {
        int distance[] = new int[n];
        int parent[] = new int[n];

        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[source] = 0;

        Arrays.fill(parent, -1);

        // Étape de relaxation
        for (int i = 0; i < n - 1; i++)
        {
            for (Edge edge: edges)
            {
                int u = edge.source;
                int v = edge.dest;
                int w = edge.weight;

                // Si un chemin plus court est trouvé, mettre à jour `distance[v]` et `parent[v]`
                if (distance[u] != Integer.MAX_VALUE && distance[u] + w < distance[v])
                {
                    distance[v] = distance[u] + w;
                    parent[v] = u;
                }
            }
        }

        // Vérification de l'existence de cycles de poids négatif
        for (Edge edge: edges)
        {
            int u = edge.source;
            int v = edge.dest;
            int w = edge.weight;

            if (distance[u] != Integer.MAX_VALUE && distance[u] + w < distance[v])
            {
                System.out.println("Un cycle de poids négatif a été trouvé !");
                return;
            }
        }

        // Afficher le chemin le plus court si existant
        if (destination != source && distance[destination] < Integer.MAX_VALUE) {
            List<Integer> path = new ArrayList<>();
            getPath(parent, destination, path);
            System.out.println("\nLe chemin le plus court du sommet " + source +
                    " au sommet " + destination + " est de " + distance[destination] +
                    ".\nLe chemin est " + path);
            return;
        }
    }

    /**
     * Fonction de back-end pour trouver le chemin le plus court entre deux sommets
     * dans un graphe pondéré en utilisant l'algorithme de Bellman-Ford.
     *
     * @param edges La liste des arêtes du graphe.
     * @param source Le sommet source.
     * @param destination Le sommet de destination.
     * @param n Le nombre total de sommets dans le graphe.
     * @return Une liste d'entiers représentant le chemin le plus court de la source à la destination.
     * Renvoie null si un cycle de poids négatif est détecté.
     */
    public static List<Integer> bellmanFordShortestPath(List<Edge> edges, int source, int destination, int n)
    {
        int[] distance;
        int[] parent;
        List<Integer> path = new ArrayList<>();

        distance = new int[n];
        parent = new int[n];

        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[source] = 0;

        Arrays.fill(parent, -1);

        // Étape de relaxation
        for (int i = 0; i < n - 1; i++)
        {
            for (Edge edge: edges)
            {
                int u = edge.source;
                int v = edge.dest;
                int w = edge.weight;

                if (distance[u] != Integer.MAX_VALUE && distance[u] + w < distance[v])
                {
                    distance[v] = distance[u] + w;
                    parent[v] = u;
                }
            }
        }

        // Vérification de l'existence de cycles de poids négatif
        for (Edge edge: edges)
        {
            int u = edge.source;
            int v = edge.dest;
            int w = edge.weight;

            if (distance[u] != Integer.MAX_VALUE && distance[u] + w < distance[v])
            {
                return null; // Un cycle de poids négatif a été trouvé
            }
        }

        // Retourner le chemin le plus court si existant
        if (destination != source && distance[destination] < Integer.MAX_VALUE)
        {
            getPath(parent, destination, path);
        }

        return path;
    }

    /**
     * Calcule le chemin le plus court entre une source et une destination en utilisant l'algorithme de Bellman-Ford,
     * et renvoie ce chemin sous forme de noms de stations.
     *
     * @param edges Liste des arêtes du graphe représentant le réseau de stations.
     * @param source Identifiant de la station source.
     * @param destination Identifiant de la station de destination.
     * @param n Nombre total de stations dans le graphe.
     * @return Liste de noms de stations représentant le chemin le plus court. Retourne null si un cycle de poids négatif est détecté.
     */
    public static List<String> getNamedShortestPath(List<Edge> edges, int source, int destination, int n)  {
        // Obtenir le chemin le plus court en utilisant l'algorithme de Bellman-Ford
        List<Integer> path = BellmanFord.bellmanFordShortestPath(edges, source, destination, n);

        // Initialiser une liste pour stocker les noms des stations
        List<String> namedPath = new ArrayList<>();

        // Convertir les identifiants des stations en noms
        if (path != null) {
            for (Integer station : path) {
                String name = Loader. getNameById(station);
                if (name != null && !name.isEmpty()) {
                    namedPath.add(name);
                }
            }
        }

        return namedPath;
    }



}
