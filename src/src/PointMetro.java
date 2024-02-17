package src;
/**
 * Classe représentant un point de métro sur une carte.
 */
public class PointMetro {
    private int x;
    private int y;
    private String stationName;

    /**
     * Constructeur pour créer un nouveau point de métro.
     *
     * @param x La coordonnée x du point.
     * @param y La coordonnée y du point.
     * @param stationName Le nom de la station de métro associée à ce point.
     */
    public PointMetro(int x, int y, String stationName) {
        this.x = x;
        this.y = y;
        this.stationName = stationName;
    }

    /**
     * Obtient la coordonnée x du point.
     *
     * @return La coordonnée x.
     */
    public int getX() {
        return x;
    }

    /**
     * Obtient la coordonnée y du point.
     *
     * @return La coordonnée y.
     */
    public int getY() {
        return y;
    }

    /**
     * Obtient le nom de la station de métro associée à ce point.
     *
     * @return Le nom de la station.
     */
    public String getStationName() {
        return stationName;
    }
}
