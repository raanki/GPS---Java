package ressources.Controller;

import javafx.fxml.FXML;

/**
 * Classe pour gérer les événements et les actions de la barre de menu.
 */
public class MenuController {

    private MainController mainController;

    /**
     * Fixe l'instance du MainController.
     *
     * @param mainController Le MainController à utiliser avec cette classe.
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Masque tous les panneaux dans l'interface utilisateur.
     */
    public void visibleFalseAll() {
        mainController.getSearchPane().setVisible(false);
        mainController.getGrIdPanePlanID().setVisible(false);
        mainController.getHomePane().setVisible(false);
    }

    /**
     * Change l'interface utilisateur pour afficher le panneau de carte.
     */
    @FXML
    public void changeToCarte()
    {
        visibleFalseAll();
        mainController.getGrIdPanePlanID().setVisible(true);
    }

    /**
     * Change l'interface utilisateur pour afficher le panneau de recherche.
     */
    @FXML
    public void changeToRecherche()
    {
        visibleFalseAll();
        mainController.getSearchPane().setVisible(true);
    }

    /**
     * Change l'interface utilisateur pour afficher le panneau d'accueil.
     */
    @FXML
    public void changeToHome()
    {
        visibleFalseAll();
        mainController.getHomePane().setVisible(true);
    }

    /**
     * Change l'interface utilisateur pour afficher le panneau ACPM.
     * Pour le moment, aucun panneau n'est visible.
     */
    @FXML
    public void changeToACPM()
    {
        visibleFalseAll();
    }
}
