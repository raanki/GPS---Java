package ressources.Controller;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import src.BellmanFord;
import src.Edge;
import src.Loader;
import src.PointMetro;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ressources.Controller.SearchController.toCamelCaseWithUnderscore;
import static src.Loader.*;

/**
 * Contrôleur de la carte (plan) dans l'application.
 */
public class MapController {

    private MainController mainController;

    Map<String, String> ligneCouleurMap = new HashMap<>();

    private double zoomLevel = 1.0;

    double imageReductionFactor = 1.5;

    int isSource =  1;  // 1 == depart et 0 == destination

    int StartId = -1;
    int DestinationId = -1;
    /**
     * Définit le contrôleur principal associé à cette classe.
     *
     * @param mainController Le contrôleur principal à définir.
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


    /**
     * Initialise la carte (plan) avec la gestion du zoom et du glissement.
     */
    public void initializePlan() {

        setColor();

    }



    // Méthode pour obtenir le nom de la station à partir des coordonnées
    private String getStationNameFromCoordinates(double x, double y) {
        List<PointMetro> pointList = parsingJsonNameMetro();

        // Parcourez la liste des points pour trouver la station correspondante
        for (PointMetro point : pointList) {
            if (Math.abs(point.getX() - x) < 10 && Math.abs(point.getY() - y) < 10) {
                return point.getStationName();
            }
        }

        return "Station inconnue"; // Si aucune station correspondante n'est trouvée
    }

    private String getLineFromCoordonates(double x, double y) {
        List<PointMetro> pointList = parsingJsonNameMetro();

        // Parcourez la liste des points pour trouver la station correspondante
        for (PointMetro point : pointList) {
            if (Math.abs(point.getX() - x) < 10 && Math.abs(point.getY() - y) < 10) {
                return getLigneByname(toCamelCaseWithUnderscore(normalizeString(point.getStationName())));
            }
        }

        return null; // Si aucune station correspondante n'est trouvée
    }


    public void changeDestination()
    {
        mainController.getLabelIndicateBtn().setText("Vous êtes en train de choisir une destination.");
        mainController.getLabelIndicateBtn().setVisible(true);
        isSource = 1;
        addRedPoints();
    }

    public void changeSource()
    {
        mainController.getLabelIndicateBtn().setText("Vous êtes en train de choisir un départ.");
        mainController.getLabelIndicateBtn().setVisible(true);
        isSource = 0;
        addRedPoints();
    }

    /**
     * Réinitialise la carte (plan) en remettant le niveau de zoom, l'échelle X et Y, et la translation à leurs valeurs par défaut.
     */
    public void ResetMap() {
        zoomLevel = 1.0; // Réinitialise le niveau de zoom
        mainController.getPlanMetroId().setScaleX(zoomLevel); // Réinitialise l'échelle X
        mainController.getPlanMetroId().setScaleY(zoomLevel); // Réinitialise l'échelle Y
        mainController.getPlanMetroId().setTranslateX(0); // Réinitialise la translation X
        mainController.getPlanMetroId().setTranslateY(0); // Réinitialise la translation Y
    }

    public void updateLabel(String stationName, int isSource) {
        if (isSource == 0) {
            mainController.getLabelSourceStation().setText(stationName);
            StartId = Loader.getIdByName(toCamelCaseWithUnderscore(normalizeString(stationName)), "null");
        } else {
            mainController.getLabelDestinationStation().setText(stationName);
            DestinationId = Loader.getIdByName(toCamelCaseWithUnderscore(normalizeString(stationName)), "null");
        }
    }

    int first = 1;

    public void ValiderTrajet() {
        if (StartId == -1 || DestinationId == -1) {
            return ;
        }
        List<Edge> edges = Loader.getListEdge("file/graph");
        List<String> path = BellmanFord.getNamedShortestPath(edges, StartId, DestinationId, Loader.getNumberSommets());

        mainController.getTimeMap().setText(Loader.getShortestDistance(StartId, DestinationId)/60 + " min");
        mainController.getTimeMap().setVisible(true);

        int id;
        String previousStationLine = "null";
        for ( String stationName : path) {
            if (first == 1) {
                id = Loader.getIdByName(toCamelCaseWithUnderscore(normalizeString(stationName)), "null");
                first = 0;
            }
            else {
                id = Loader.getIdByName(toCamelCaseWithUnderscore(normalizeString(stationName)), previousStationLine);
            }
            previousStationLine = Loader.getLigneById(id);
            SearchController.populateSingleStation(mainController.getPrintExample(), stationName, previousStationLine);
        }
        //System.out.println(path);
    }

    public void resetTrajet() {
        mainController.getPrintExample().getChildren().clear();
        mainController.getShowStart().getChildren().clear();
        mainController.getShowEnd().getChildren().clear();
        first = 1;
        mainController.getLabelSourceStation().setText("Aucune Station choisie pour l'instant.");
        mainController.getLabelDestinationStation().setText("Aucune Station choisie pour l'instant.");
        StartId = -1;
        DestinationId = -1;
        mainController.getTimeMap().setVisible(false);
    }

    private void addRedPoints() {
        List<PointMetro> pointList = parsingJsonNameMetro();

        for (PointMetro point : pointList) {
            Circle circle = new Circle(5); // Créez un cercle (ou un autre élément visuel) pour chaque point

            String current = getLineFromCoordonates(point.getX(), point.getY());

            if (current != null && !current.isEmpty()) {
                String couleur = ligneCouleurMap.get("ligne" + current);
                Color color = Color.valueOf(couleur);
                double red = color.getRed() * 0.6; // Réduisez la composante rouge de 20%
                double green = color.getGreen() * 0.6; // Réduisez la composante verte de 20%
                double blue = color.getBlue() * 0.6; // Réduisez la composante bleue de 20
                Color darkerColor = Color.color(red, green, blue);
                circle.setFill(darkerColor);
                // Positionnez le cercle en fonction des coordonnées initiales
                double scaledX = (point.getX() * zoomLevel) / imageReductionFactor;
                double scaledY = (point.getY() * zoomLevel) / imageReductionFactor;

                circle.setTranslateX(scaledX);
                circle.setTranslateY(scaledY);

                // Stockez les coordonnées d'origine du cercle en tant que UserData
                circle.setUserData(new double[]{point.getX(), point.getY()}); // Stockez les coordonnées directement dans un tableau de doubles

                // Ajoutez un écouteur de clic pour le point
                circle.setOnMouseClicked(event -> {
                    // Accédez aux coordonnées d'origine du cercle à partir du UserData
                    double[] originalCoordinates = (double[]) circle.getUserData();
                    double originalX = originalCoordinates[0];
                    double originalY = originalCoordinates[1];

                    // Utilisez les coordonnées d'origine pour déterminer la station
                    String stationName = getStationNameFromCoordinates(originalX, originalY);

                    if (stationName != null && !stationName.equals("Station inconnue")) {
                        if (isSource == 0) {
                            mainController.getLabelSourceStation().setText("");
                            mainController.getShowStart().getChildren().clear();
                            SearchController.populateSingleStation(mainController.getShowStart(), stationName, "null");
                            StartId = Loader.getIdByName(toCamelCaseWithUnderscore(normalizeString(stationName)), "null");
                        }
                        else {
                            mainController.getLabelDestinationStation().setText("");
                            mainController.getShowEnd().getChildren().clear();
                            SearchController.populateSingleStation(mainController.getShowEnd(), stationName, "null");
                            DestinationId = Loader.getIdByName(toCamelCaseWithUnderscore(normalizeString(stationName)), "null");
                        }
                        removeRedPoints(); // Supprimez les points rouges une fois qu'un point est sélectionné
                    }
                });
            }

            mainController.getPaneID().getChildren().add(circle);
        }
    }

    private void removeRedPoints() {
        mainController.getLabelIndicateBtn().setVisible(false);
        mainController.getPaneID().getChildren().removeIf(node -> node instanceof Circle);
    }

    public void setColor()
    {
        ligneCouleurMap.put("ligne1", "#FFCE00");
        ligneCouleurMap.put("ligne2", "#0064B0");
        ligneCouleurMap.put("ligne3", "#9F9825");
        ligneCouleurMap.put("ligne3bis", "#98D4E2");
        ligneCouleurMap.put("ligne4", "#C04191");
        ligneCouleurMap.put("ligne5", "#F28E42");
        ligneCouleurMap.put("ligne6", "#83C491");
        ligneCouleurMap.put("ligne7", "#F3A4BA");
        ligneCouleurMap.put("ligne7bis", "#83C491");
        ligneCouleurMap.put("ligne8", "#CEADD2");
        ligneCouleurMap.put("ligne9", "#D5C900");
        ligneCouleurMap.put("ligne10", "#E3B32A");
        ligneCouleurMap.put("ligne11", "#8D5E2A");
        ligneCouleurMap.put("ligne12", "#00814F");
        ligneCouleurMap.put("ligne13", "#98D4E2");
        ligneCouleurMap.put("ligne14", "#662483");
        ligneCouleurMap.put("ligne15", "#B90845");
        ligneCouleurMap.put("ligne16", "#F3A4BA");
        ligneCouleurMap.put("ligne17", "#D5C900");
        ligneCouleurMap.put("ligne18", "#00A88F");
    }
}

