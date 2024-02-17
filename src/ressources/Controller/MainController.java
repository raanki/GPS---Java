package ressources.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Contrôleur principal de l'application.
 */
public class MainController implements Initializable {



    @FXML private MapController mapController = new MapController();
    @FXML private MenuController menuController = new MenuController();
    @FXML private SearchController searchController = new SearchController();

    /**
     * Initialise le contrôleur principal lors du chargement de la vue.
     *
     * @param url             L'URL de la vue.
     * @param resourceBundle  Les ressources associées à la vue.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mapController.setMainController(this);
        menuController.setMainController(this);
        searchController.setMainController(this);

        visibleFalseAll();
        getHomePane().setVisible(true);
        initializePlan();
        inizializeSearch();
    }

    /*********************************************************************************************************************************/
    /*********************************************************************************************************************************/
    /***************************************************** REDIRECTIONS **************************************************************/
    /*********************************************************************************************************************************/
    /*********************************************************************************************************************************/

    public void initializePlan() {mapController.initializePlan();}
    public void ResetMap() {mapController.ResetMap();}

    public void ValiderTrajet() {mapController.ValiderTrajet();}
    public void visibleFalseAll() {menuController.visibleFalseAll();}
    public void changeToCarte() {menuController.changeToCarte();}
    public void changeToRecherche() {menuController.changeToRecherche();}
    public void changeToACPM() {menuController.changeToACPM();}

    public void findTraject() {searchController.findTraject();}
    public void inizializeSearch() {searchController.inizializeSearch();}

    public void resetSearch() {searchController.resetSearch();}

    public void changeSource() {mapController.changeSource();}

    public void changeDestination() {mapController.changeDestination();}

    public void resetTrajet() {mapController.resetTrajet();}

    public void changeToHome() {menuController.changeToHome();}


    /**********************************************************************************************************************************************/
    /**********************************************************************************************************************************************/
    /************************************************* Getters and Setters ************************************************************************/
    /**********************************************************************************************************************************************/
    /**********************************************************************************************************************************************/
    public MapController getMapController() {
        return mapController;
    }

    public void setMapController(MapController mapController) {
        this.mapController = mapController;
    }

    public Pane getSearchPane() {
        return SearchPane;
    }

    public void setSearchPane(Pane searchPane) {
        SearchPane = searchPane;
    }

    public AnchorPane getMetroPane() {
        return metroPane;
    }
    public void setMetroPane(AnchorPane metroPane) {
        this.metroPane = metroPane;
    }

    public ImageView getPlanMetroId() {
        return planMetroId;
    }

    public void setPlanMetroId(ImageView planMetroId) {
        this.planMetroId = planMetroId;
    }

    public Pane getPaneID() {
        return PaneID;
    }

    public void setPaneID(Pane paneID) {
        PaneID = paneID;
    }

    public Pane getGridPaneID() {
        return GridPaneID;
    }

    public void setGridPaneID(Pane gridPaneID) {
        GridPaneID = gridPaneID;
    }

    public Pane getGrIdPanePlanID() {
        return GrIdPanePlanID;
    }

    public void setGrIdPanePlanID(Pane grIdPanePlanID) {
        GrIdPanePlanID = grIdPanePlanID;
    }

    public ScrollPane getScrollPaneID() {
        return scrollPaneID;
    }

    public void setScrollPaneID(ScrollPane scrollPaneID) {
        this.scrollPaneID = scrollPaneID;
    }

    public Button getResetMap() {
        return ResetMap;
    }

    public void setResetMap(Button resetMap) {
        ResetMap = resetMap;
    }

    public Button getBtnSourceStation() {
        return BtnSourceStation;
    }

    public void setBtnSourceStation(Button btnSourceStation) {
        BtnSourceStation = btnSourceStation;
    }

    public Button getBtnDestStation() {
        return BtnDestStation;
    }

    public void setBtnDestStation(Button btnDestStation) {
        BtnDestStation = btnDestStation;
    }

    public Button getBtnResetTrajet() {
        return BtnResetTrajet;
    }

    public void setBtnResetTrajet(Button btnResetTrajet) {
        BtnResetTrajet = btnResetTrajet;
    }

    public Label getLabelSourceStation() {
        return labelSourceStation;
    }

    public void setLabelSourceStation(Label labelSourceStation) {
        this.labelSourceStation = labelSourceStation;
    }

    public Label getLabelDestinationStation() {
        return labelDestinationStation;
    }

    public void setLabelDestinationStation(Label labelDestinationStation) {
        this.labelDestinationStation = labelDestinationStation;
    }

    public Label getLogoMetroChoose() {
        return logoMetroChoose;
    }

    public void setLogoMetroChoose(Label logoMetroChoose) {
        this.logoMetroChoose = logoMetroChoose;
    }

    public TextField getTextFieldStart() {
        return textFieldStart;
    }

    public void setTextFieldStart(TextField textFieldStart) {
        this.textFieldStart = textFieldStart;
    }

    public TextField getTextFieldEnd() {
        return textFieldEnd;
    }

    public void setTextFieldEnd(TextField textFieldEnd) {
        this.textFieldEnd = textFieldEnd;
    }

    public VBox getStockStationResult() {
        return StockStationResult;
    }

    public void setStockStationResult(VBox stockStationResult) {
        StockStationResult = stockStationResult;
    }

    public Label getLabelSourceStationNothing() {
        return labelSourceStationNothing;
    }

    public void setLabelSourceStationNothing(Label labelSourceStationNothing) {
        this.labelSourceStationNothing = labelSourceStationNothing;
    }

    public Label getLabelDestinationStationNothing() {
        return labelDestinationStationNothing;
    }

    public void setLabelDestinationStationNothing(Label labelDestinationStationNothing) {
        this.labelDestinationStationNothing = labelDestinationStationNothing;
    }

    public VBox getImgCrossStart() {
        return ImgCrossStart;
    }

    public void setImgCrossStart(VBox imgCrossStart) {
        ImgCrossStart = imgCrossStart;
    }

    public VBox getImgCrossEnd() {
        return ImgCrossEnd;
    }

    public void setImgCrossEnd(VBox imgCrossEnd) {
        ImgCrossEnd = imgCrossEnd;
    }

    public VBox getImgCheckStart() {
        return ImgCheckStart;
    }

    public void setImgCheckStart(VBox imgCheckStart) {
        ImgCheckStart = imgCheckStart;
    }

    public VBox getImgCheckEnd() {
        return ImgCheckEnd;
    }

    public void setImgCheckEnd(VBox imgCheckEnd) {
        ImgCheckEnd = imgCheckEnd;
    }

    public VBox getShowStationEnd() {
        return ShowStationEnd;
    }

    public void setShowStationEnd(VBox showStationEnd) {
        ShowStationEnd = showStationEnd;
    }

    public VBox getShowStationStart() {
        return ShowStationStart;
    }

    public void setShowStationStart(VBox showStationStart) {
        ShowStationStart = showStationStart;
    }

    public Label getWarningCheckSearch() {
        return WarningCheckSearch;
    }

    public void setWarningCheckSearch(Label warningCheckSearch) {
        WarningCheckSearch = warningCheckSearch;
    }

    public Button getBtnResetTrajetSearch() {
        return BtnResetTrajetSearch;
    }

    public void setBtnResetTrajetSearch(Button btnResetTrajetSearch) {
        BtnResetTrajetSearch = btnResetTrajetSearch;
    }

    public VBox getPrintExample() {
        return printExample;
    }

    public void setPrintExample(VBox printExample) {
        this.printExample = printExample;
    }

    public Label getLabelIndicateBtn() {
        return labelIndicateBtn;
    }

    public void setLabelIndicateBtn(Label labelIndicateBtn) {
        this.labelIndicateBtn = labelIndicateBtn;
    }

    public VBox getShowEnd() {
        return ShowEnd;
    }

    public void setShowEnd(VBox showEnd) {
        ShowEnd = showEnd;
    }

    public VBox getShowStart() {
        return ShowStart;
    }

    public void setShowStart(VBox showStart) {
        ShowStart = showStart;
    }

    public GridPane getHomePane() {
        return HomePane;
    }

    public void setHomePane(GridPane homePane) {
        HomePane = homePane;
    }

    public Button getHome() {
        return home;
    }

    public void setHome(Button home) {
        this.home = home;
    }

    public Label getTimeSearch() {
        return TimeSearch;
    }

    public void setTimeSearch(Label timeSearch) {
        TimeSearch = timeSearch;
    }

    public Label getTimeMap() {
        return TimeMap;
    }

    public void setTimeMap(Label timeMap) {
        TimeMap = timeMap;
    }

    /**********************************************************************************************************************************************/
    /**********************************************************************************************************************************************/
    /************************************************ ELEMENTS INTERACTIFS ************************************************************************/
    /**********************************************************************************************************************************************/
    /**********************************************************************************************************************************************/

    // Champs liés à l'interface utilisateur
    @FXML private VBox ShowStationEnd;
    @FXML private VBox ShowStationStart;
    @FXML private AnchorPane metroPane;
    @FXML private ImageView planMetroId;
    @FXML private Pane PaneID;
    @FXML private Pane GridPaneID;
    @FXML private Pane GrIdPanePlanID;
    @FXML private Pane SearchPane;
    @FXML private ScrollPane scrollPaneID;
    @FXML private Button ResetMap;

    // Champs liés aux boutons
    @FXML private Button BtnSourceStation;
    @FXML private Button BtnDestStation;
    @FXML private Button BtnResetTrajet;

    // Champs liés aux étiquettes
    @FXML private Label labelSourceStation;
    @FXML private Label labelDestinationStation;
    @FXML private Label logoMetroChoose;
    @FXML private Label labelSourceStationNothing;
    @FXML private Label labelDestinationStationNothing;

    // Champs liés aux images (VBox)
    @FXML private VBox ImgCrossStart;
    @FXML private VBox ImgCrossEnd;
    @FXML private VBox ImgCheckStart;
    @FXML private VBox ImgCheckEnd;

    // Champs liés aux champs de texte
    @FXML private TextField textFieldStart;
    @FXML private TextField textFieldEnd;

    // Champs liés à l'affichage des résultats
    @FXML private VBox StockStationResult;
    @FXML private VBox ShowEnd;
    @FXML private VBox ShowStart;

    // Champs liés à la vérification de la recherche
    @FXML private Label WarningCheckSearch;
    @FXML private Label labelIndicateBtn;
    @FXML private Button BtnResetTrajetSearch;
    @FXML private VBox printExample;
    @FXML private GridPane HomePane;
    @FXML private Button home;
    @FXML private Label TimeSearch;
    @FXML private Label TimeMap;

}


