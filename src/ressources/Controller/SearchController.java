package ressources.Controller;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import src.BellmanFord;
import src.Edge;
import src.Loader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static src.Loader.normalizeString;

/**
 * Classe de contrôleur pour la recherche de stations et le calcul des trajets.
 */
public class SearchController {

    HashMap<Integer, HashMap<String, Object>> stations = Loader.getAllStationNameAndLine();

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    int first = 1;

    /**
     * Initialise la recherche de stations.
     */
    public void inizializeSearch() {

        mainController.getTextFieldStart().textProperty().addListener((observable, oldValue, newValue) -> {
            VBox stockStationResult = mainController.getShowStationStart(); // Remplacez par votre VBox réel
            if (newValue != null && !newValue.isEmpty()) {
                String normalizedStart = toCamelCaseWithUnderscore(normalizeString(newValue));
                if (stationExists(normalizedStart, stations)) {
                    mainController.getImgCheckStart().setVisible(true);
                    mainController.getImgCrossStart().setVisible(false);

                    stockStationResult.getChildren().clear();
                    populateSingleStation(stockStationResult, newValue, "null");

                } else {
                    mainController.getImgCheckStart().setVisible(false);
                    mainController.getImgCrossStart().setVisible(true);
                    stockStationResult.getChildren().clear();
                    mainController.getLabelSourceStationNothing().setText("Aucune Station choisis pour l'intant.");
                }
            } else {
                stockStationResult.getChildren().clear();
                mainController.getLabelSourceStationNothing().setText("Aucune Station choisis pour l'intant.");
            }
        });

        // Pour la destination
        mainController.getTextFieldEnd().textProperty().addListener((observable, oldValue, newValue) -> {
            VBox stockStationResultEnd = mainController.getShowStationEnd();
            if (newValue != null && !newValue.isEmpty()) {
                String normalizedEnd = toCamelCaseWithUnderscore(normalizeString(newValue));
                if (stationExists(normalizedEnd, stations)) {
                    mainController.getImgCheckEnd().setVisible(true);
                    mainController.getImgCrossEnd().setVisible(false);

                    stockStationResultEnd.getChildren().clear();
                    populateSingleStation(stockStationResultEnd, newValue, "null");
                } else {
                    mainController.getImgCheckEnd().setVisible(false);
                    mainController.getImgCrossEnd().setVisible(true);
                    stockStationResultEnd.getChildren().clear();
                    mainController.getLabelSourceStationNothing().setText("Aucune Station choisis pour l'intant.");
                }
            } else {
                stockStationResultEnd.getChildren().clear();
                mainController.getLabelSourceStationNothing().setText("Aucune Station choisis pour l'intant.");
            }
        });

    }

    /**
     * Effectue la recherche et le calcul du trajet.
     *
     */
    public void findTraject() {

        if (mainController.getTextFieldStart() == null || mainController.getTextFieldEnd() == null
            || mainController.getTextFieldStart().getText().isEmpty() || mainController.getTextFieldEnd().getText().isEmpty())
        {
            mainController.getWarningCheckSearch().setVisible(true);
            return ;
        }
        mainController.getWarningCheckSearch().setVisible(false);
        String start = normalizeString(mainController.getTextFieldStart().getText());
        String end = normalizeString(mainController.getTextFieldEnd().getText());

        int startId = Loader.getIdByName(toCamelCaseWithUnderscore(normalizeString(start)), "null");
        int endId = Loader.getIdByName(toCamelCaseWithUnderscore(normalizeString(end)), "null");

        if (startId < 0 || endId < 0) {
            mainController.getWarningCheckSearch().setVisible(true);
            return;
        }
        mainController.getWarningCheckSearch().setVisible(false);

        List<Edge> edges = Loader.getListEdge("file/graph");
        List<String> path = BellmanFord.getNamedShortestPath(edges, startId, endId, Loader.getNumberSommets());
        mainController.getTimeSearch().setText(Loader.getShortestDistance(startId, endId)/60 + " min");
        mainController.getTimeSearch().setVisible(true);

        VBox stockStationResult = mainController.getStockStationResult();
        populateVBox(stockStationResult, path);

        //System.out.println(path);
    }

    /**
     * Crée une boîte de station pour l'affichage.
     *
     * @param metroLine   La ligne de métro.
     * @param stationName Le nom de la station.
     * @return Une boîte de station configurée.
     */
    public static HBox createStationBox(String metroLine, String stationName) {
        HBox hBox = new HBox();
        Label logoMetroChoose = new Label(metroLine);
        Label dotLabel = new Label("•");
        String stationNameWithSpaces = stationName.replaceAll("([a-z])([A-Z])", "$1 $2");
        Label stationLabel = new Label(stationNameWithSpaces);

        logoMetroChoose.getStyleClass().add("RondMetro");
        logoMetroChoose.getStyleClass().add("ligne" + metroLine);
        HBox.setMargin(logoMetroChoose, new Insets(3, 3, 0, 10));
        HBox.setMargin(dotLabel, new Insets(0, 0, 0, 10));
        HBox.setMargin(stationLabel, new Insets(11, 0, 0, 7));
        dotLabel.getStyleClass().add("dotLabel");

        // Ajouter les Labels au HBox et le configurer
        hBox.getChildren().addAll(logoMetroChoose, dotLabel, stationLabel);

        return hBox;
    }

    /**
     * Peuple le VBox avec les stations du trajet.
     *
     * @param stockStationResult Le VBox où afficher les stations.
     * @param path               La liste des noms des stations du trajet.
     */
    public void populateVBox(VBox stockStationResult, List<String> path) {
        HashMap<Integer, HashMap<String, Object>> stations = Loader.getAllStationNameAndLine();

        String previousStationLine = "null";
        int id;
        for (String stationName : path) {
            if (first == 1) {
                id = Loader.getIdByName(toCamelCaseWithUnderscore(normalizeString(stationName)), "null");
                first = 0;
            }
            else {
                id = Loader.getIdByName(toCamelCaseWithUnderscore(normalizeString(stationName)), previousStationLine);
            }
            previousStationLine = Loader.getLigneById(id);
            if (id == -1) {
                System.out.println("non trouvé avec " + toCamelCaseWithUnderscore(normalizeString(stationName)));
                continue;
            }
            HashMap<String, Object> stationInfo = stations.get(id);

            String metroLine = (String) stationInfo.get("line");
            String nextMinutes = "7"; // mettre une logique pour les minutes

            HBox stationBox = createStationBox(metroLine, stationName);
            stockStationResult.getChildren().add(stationBox);
        }
    }

    /**
     * Vérifie si une station existe dans la liste des stations.
     *
     * @param stationName Le nom de la station à vérifier.
     * @param stations    La liste des stations.
     * @return Vrai si la station existe, sinon faux.
     */
    private boolean stationExists(String stationName, HashMap<Integer, HashMap<String, Object>> stations) {
        String normalizedStationName = normalizeString(stationName);

        for (Map.Entry<Integer, HashMap<String, Object>> entry : stations.entrySet()) {
            HashMap<String, Object> infoMap = entry.getValue();
            String storedStationName = normalizeString((String) infoMap.get("name"));

            if (storedStationName.equals(normalizedStationName)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Convertit une chaîne en format CamelCase avec des underscores.
     *
     * @param input La chaîne d'entrée.
     * @return La chaîne convertie en format CamelCase avec des underscores.
     */
    public static String toCamelCaseWithUnderscore(String input) {
        input = removeSpaces(input);
        String[] words = input.split("[\\s-]");
        StringBuilder camelCase = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (!word.isEmpty()) {
                camelCase.append(Character.toUpperCase(word.charAt(0)));
                camelCase.append(word.substring(1).toLowerCase());
            }
            if (i < words.length - 1) {
                camelCase.append('_');
            }
        }

        // Supprimer le dernier underscore ajouté
        if(camelCase.charAt(camelCase.length() - 1) == '_') {
            camelCase.deleteCharAt(camelCase.length() - 1);
        }

        return camelCase.toString();
    }

    /**
     * Supprime les espaces d'une chaîne.
     *
     * @param input La chaîne d'entrée.
     * @return La chaîne sans espaces.
     */
    public static String removeSpaces(String input) {
        return input.replaceAll("\\s", "");
    }

    /**
     * Peuple le VBox avec une seule station.
     *
     * @param stockStationResult Le VBox où afficher la station.
     * @param stationName        Le nom de la station.
     */
    public static void populateSingleStation(VBox stockStationResult, String stationName, String previousStationLine) {
        HashMap<Integer, HashMap<String, Object>> stations = Loader.getAllStationNameAndLine();
        int id = Loader.getIdByName(toCamelCaseWithUnderscore(normalizeString(stationName)), previousStationLine);

        HashMap<String, Object> stationInfo = stations.get(id);
        String metroLine = (String) stationInfo.get("line");
        String nextMinutes = "null";

        HBox stationBox = createStationBox(metroLine, stationName);
        stockStationResult.getChildren().add(stationBox);

        for (Node node : stationBox.getChildren())
        {
            if (node instanceof Label)
            {
                Label label = (Label) node;
                if (label.getText().contains("min"))
                {
                    label.setText(null);
                    break;
                }
            }
        }
    }

    /**
     * Réinitialise tous les éléments de recherche dans l'interface utilisateur.
     * Vide les champs de texte et les résultats de la station,
     * et réinitialise les icônes et les labels.
     */
    public void resetSearch() {
        mainController.getTextFieldStart().clear();
        mainController.getTextFieldEnd().clear();
        mainController.getShowStationStart().getChildren().clear();
        mainController.getShowStationEnd().getChildren().clear();
        mainController.getStockStationResult().getChildren().clear();
        mainController.getImgCheckStart().setVisible(false);
        mainController.getImgCheckEnd().setVisible(false);
        mainController.getImgCrossStart().setVisible(true);
        mainController.getImgCrossEnd().setVisible(true);
        mainController.getLabelSourceStationNothing().setText("Aucune Station choisis pour l'intant.");
        mainController.getLabelDestinationStationNothing().setText("Aucune Station choisis pour l'intant.");
        mainController.getWarningCheckSearch().setVisible(false);
        mainController.getTimeSearch().setVisible(false);
        first = 1;
    }





}
