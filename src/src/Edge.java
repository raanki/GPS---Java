package src;

/**
 * Classe représentant une arête dans un graphe pondéré dirigé.
 * Chaque arête possède un sommet source, un sommet destination et un poids associé.
 */
public class Edge
{
    /**
     * Le sommet d'origine de cette arête.
     */
    int source;

    /**
     * Le sommet de destination de cette arête.
     */
    int dest;

    /**
     * Le poids associé à cette arête.
     */
    int weight;

    /**
     * Constructeur pour créer une nouvelle arête.
     *
     * @param source Le sommet d'origine de l'arête.
     * @param dest Le sommet de destination de l'arête.
     * @param weight Le poids de l'arête.
     */
    public Edge(int source, int dest, int weight)
    {
        this.source = source;
        this.dest = dest;
        this.weight = weight;
    }
}
