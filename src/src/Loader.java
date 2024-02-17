package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Classe pour charger des graphes à partir de fichiers.
 */
public class Loader {

    /**
     * Chemin du fichier contenant les arêtes du graphe.
     */
    String edgeFilePath = "file/graph";

    /**
     * Charge toutes les arêtes et les sommets à partir du fichier,
     * puis exécute l'algorithme de Bellman-Ford sur le graphe ainsi créé.
     *
     * @param depart      Sommet de départ pour l'algorithme de Bellman-Ford.
     * @param destination Sommet de destination pour l'algorithme de Bellman-Ford.
     */
    public void loadAllEdges(int depart, int destination, int ACPM) {
        List<Edge> edges = getListEdge(edgeFilePath);  // Utilisation de getListEdge
        Set<Integer> vertices = new HashSet<>();

        // Enregistrement des sommets
        for (Edge edge : edges) {
            vertices.add(edge.source);
            vertices.add(edge.dest);
        }

        boolean isConnected = GraphConnectivity.isConnected(edges, vertices);
        if (!isConnected) {
            System.out.println("Le graphe n'est pas connexe.");
            return;
        }

        if (ACPM == 1)
            PrimAlgorithm.prim(edges, vertices);

        // Trouver le sommet max pour définir la taille du tableau dans Bellman-Ford
        int maxVertex = Collections.max(vertices) + 1;

        // Exécuter l'algorithme de Bellman-Ford
        BellmanFord.bellmanFord(edges, depart, destination, maxVertex);
    }

    /**
     * Obtient le nom associé à un numéro à partir d'un fichier.
     *
     * @return Le nom associé au numéro donné.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    public static String getNameById(int id) {
        HashMap<Integer, HashMap<String, Object>> stationMap = getAllStationNameAndLine();
        String name = "";

        for (Map.Entry<Integer, HashMap<String, Object>> entry : stationMap.entrySet()) {
            if (entry.getKey() == id) {
                name = (String) entry.getValue().get("name");
                break;
            }
        }

        return name;
    }

    /**
     * Retourne la liste des arêtes d'un graphe à partir d'un fichier.
     *
     * @param edgeFilePath Le chemin du fichier contenant les informations des arêtes du graphe.
     *                     Le fichier doit être formaté de telle manière que chaque ligne décrit une arête avec la syntaxe "E source destination poids".
     * @return Une liste d'objets Edge représentant les arêtes du graphe.
     * @throws IOException Si une erreur de lecture du fichier survient.
     */
    public static List<Edge> getListEdge(String edgeFilePath) {
        List<Edge> edges = new ArrayList<>();
        try {
            // Lecture du fichier ligne par ligne
            BufferedReader reader = new BufferedReader(new FileReader(edgeFilePath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length >= 4 && parts[0].equals("E")) {
                    int source = Integer.parseInt(parts[1].trim());
                    int dest = Integer.parseInt(parts[2].trim());
                    int weight = Integer.parseInt(parts[3].trim());
                    // Ajout des arêtes au graphe
                    edges.add(new Edge(source, dest, weight));
                    edges.add(new Edge(dest, source, weight));  // Graphe non orienté
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return edges;
    }

    /**
     * Cette méthode compte le nombre de sommets dans un fichier de graphes.
     *
     * @edgeFilePath Le chemin du fichier qui contient les informations sur les sommets.
     * @return Le nombre de lignes dans le fichier, ce qui correspond au nombre de sommets.
     * @throws IOException Si une erreur de lecture du fichier se produit.
     */
    public static int getNumberSommets() {
        int lineCount = 0;
        String edgeFilePath = "file/graph";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(edgeFilePath));
            while (reader.readLine() != null) {
                lineCount++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lineCount;
    }

    /**
     * Parse un fichier texte pour extraire les coordonnées et les noms des stations de métro.
     * Le fichier doit être au format suivant pour chaque ligne : "x;y;Nom@de@la@Station"
     * '@' dans le nom de la station est remplacé par un espace.
     *
     * @return Une HashMap où la clé est le nom de la station et la valeur est un tableau d'entiers [x, y].
     *
     * @throws IOException Si le fichier ne peut pas être lu.
     */
    public static List<PointMetro> parsingJsonNameMetro() {
        String filePath = "file/pospoints.txt";
        List<PointMetro> pointList = new ArrayList<>();
        String line;
        String[] parts;
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(filePath));
            while ((line = reader.readLine()) != null) {
                parts = line.split(";");
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                String name = parts[2].replace("@", " ");

                pointList.add(new PointMetro(x, y, name));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pointList;
    }


    /**
     * Parse un fichier texte pour extraire les informations des stations de métro.
     * Le fichier doit être au format suivant pour chaque ligne : "V;ID;Nom;Ligne;Terminus;Ignoré"
     *
     * @return Une HashMap où la clé est l'ID de la station et la valeur est une autre HashMap contenant les infos de la station.
     *
     * @throws IOException Si le fichier ne peut pas être lu.
     */
    public static HashMap<Integer, HashMap<String, Object>> getAllStationNameAndLine() {
        HashMap<Integer, HashMap<String, Object>> stationMap = new HashMap<>();
        String filePath = "file/graph_names";
        String line;
        String[] parts;
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(filePath));
            while ((line = reader.readLine()) != null)
            {
                parts = line.split(";");
                int id = Integer.parseInt(parts[1].substring(1));
                String name = parts[2].replaceAll("([a-z])([A-Z])", "$1$2");
                String lineStr = parts[3];
                boolean isTerminus = Boolean.parseBoolean(parts[4]);

                HashMap<String, Object> infoMap = new HashMap<>();
                infoMap.put("id", id);
                infoMap.put("name", name);
                infoMap.put("line", lineStr);
                infoMap.put("isTerminus", isTerminus);

                stationMap.put(id, infoMap);
            }
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return stationMap;
    }

    /**
     * Recherche l'ID d'une station par son nom.
     *
     * @param name Le nom de la station à rechercher.
     *
     * @return L'ID de la station, ou -1 si la station n'est pas trouvée.
     */
    public static int getIdByName(String name, String previousLine)
    {
        HashMap<Integer, HashMap<String, Object>> stationMap = getAllStationNameAndLine();
        int id;
        String normalizedName;
        String foundLine = null;

        id = -1;
        normalizedName = normalizeString(name);

        for (Map.Entry<Integer, HashMap<String, Object>> entry : stationMap.entrySet())
        {
            HashMap<String, Object> infoMap;
            String stationName, line;

            infoMap = entry.getValue();
            stationName = normalizeString((String) infoMap.get("name"));
            line = (String) infoMap.get("line"); // Je suppose que "line" est la clé pour la ligne de métro

            if (stationName.equals(normalizedName))
            {
                if (previousLine != null && previousLine.equals(line))
                {
                    return entry.getKey();
                }
                else if (foundLine == null)
                {
                    id = entry.getKey();
                    foundLine = line;
                }
            }
        }

        if (id != -1)
        {
            // Chercher dans les 10 prochaines stations
            int startIdx;
            int endIdx;

            startIdx = id + 1;
            endIdx = id + 10;

            for (int i = startIdx; i <= endIdx; i++)
            {
                HashMap<String, Object> infoMap;
                String stationName, line;

                infoMap = stationMap.get(i);
                if (infoMap == null)
                {
                    continue;
                }

                stationName = normalizeString((String) infoMap.get("name"));
                line = (String) infoMap.get("line");

                if (stationName.equals(normalizedName) && line.equals(previousLine))
                {
                    return i;
                }
            }
        }

        return id;
    }


    /**
     * Normalise une chaîne de caractères en supprimant les accents et en la mettant en minuscules.
     *
     * @param str La chaîne de caractères à normaliser.
     * @return La chaîne de caractères normalisée.
     */
    public static String normalizeString(String str) {
        String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").toLowerCase();
    }

    /**
     * Obtient la ligne de métro associée à un nom de station donné.
     *
     * @param name Le nom de la station.
     * @return Le nom de la ligne associée à cette station, ou une chaîne vide si non trouvée.
     */
    public static String getLigneByname(String name) {
        HashMap<Integer, HashMap<String, Object>> stationMap = getAllStationNameAndLine();
        String ligne = "";

        String normalizedName = normalizeString(name);

        for (Map.Entry<Integer, HashMap<String, Object>> entry : stationMap.entrySet()) {
            HashMap<String, Object> infoMap = entry.getValue();
            String stationName = normalizeString((String) infoMap.get("name"));
            if (stationName.equals(normalizedName)) {
                ligne = (String) infoMap.get("line"); // Obtenez la ligne de métro associée
                break;
            }
        }

        return ligne;
    }

    /**
     * Obtient la ligne associée à un ID de station.
     *
     * @param id L'ID de la station.
     * @return La ligne associée à cet ID, ou une chaîne vide si non trouvée.
     */
    public static String getLigneById(int id)
    {
        HashMap<Integer, HashMap<String, Object>> stationMap;
        String ligne;

        stationMap = getAllStationNameAndLine();
        ligne = "";

        HashMap<String, Object> infoMap;
        infoMap = stationMap.get(id);

        if (infoMap != null)
        {
            ligne = (String) infoMap.get("line"); // Obtenez la ligne de métro associée
        }

        return ligne;
    }

    private static List<Edge> edges;
    private static int maxVertex;

    static {
        initGraph("file/graph");
    }

    /**
     * Initialise le graphe en utilisant un fichier de données.
     *
     * @param edgeFilePath Le chemin du fichier contenant les arêtes du graphe.
     */
    public static void initGraph(String edgeFilePath) {
        edges = getListEdge(edgeFilePath);
        Set<Integer> vertices = new HashSet<>();

        for (Edge edge : edges) {
            vertices.add(edge.source);
            vertices.add(edge.dest);
        }
        maxVertex = Collections.max(vertices) + 1;
    }

    /**
     * Trouve la distance la plus courte entre deux stations.
     *
     * @param source L'ID de la station source.
     * @param destination L'ID de la station de destination.
     * @return La distance la plus courte, ou -1 si non trouvée ou en cas d'erreur.
     */
    public static int getShortestDistance(int source, int destination) {
        return getShortestDistance(edges, source, destination, maxVertex);
    }

    /**
     * Implémente l'algorithme pour trouver la distance la plus courte.
     *
     * @param edges Les arêtes du graphe.
     * @param source L'ID de la station source.
     * @param destination L'ID de la station de destination.
     * @param n Le nombre total de sommets dans le graphe.
     * @return La distance la plus courte, ou -1 si non trouvée ou en cas d'erreur.
     */
    public static int getShortestDistance(List<Edge> edges, int source, int destination, int n)
    {
        int[] distance;
        int[] parent;

        distance = new int[n];
        parent = new int[n];

        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[source] = 0;

        Arrays.fill(parent, -1);

        // Étape de relaxation
        for (int i = 0; i < n - 1; i++)
        {
            for (Edge edge : edges)
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
        for (Edge edge : edges)
        {
            int u = edge.source;
            int v = edge.dest;
            int w = edge.weight;

            if (distance[u] != Integer.MAX_VALUE && distance[u] + w < distance[v])
            {
                System.out.println("Un cycle de poids négatif a été trouvé !");
                return -1;
            }
        }

        if (distance[destination] == Integer.MAX_VALUE)
        {
            System.out.println("Pas de chemin disponible.");
            return -1;
        }
        return distance[destination];
    }

    public static boolean isConnectedBool()
    {
        List<Edge> edges = getListEdge("file/graph");  // Utilisation de getListEdge
        Set<Integer> vertices = new HashSet<>();

        // Enregistrement des sommets
        for (Edge edge : edges) {
            vertices.add(edge.source);
            vertices.add(edge.dest);
        }

        boolean isConnected = GraphConnectivity.isConnected(edges, vertices);
        if (!isConnected) {
            System.out.println("Le graphe n'est pas connexe.");
            return false;
        }
        return true;
    }


}
