package src;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main extends Application
{
    public static void main(String[] args) {

        Loader loader = new Loader();

        int choix;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nVoulez-vous lancer le programme en : ");
            System.out.println("1. Interface graphique");
            System.out.println("2. Console");
            System.out.println("3. Exit");
            System.out.print("Votre choix : ");

            choix = scanner.nextInt();

            if (choix == 1) {
                launch(args);
                System.exit(0);
            } else if (choix == 2) {
                while(true) {
                    int sousChoix;

                    System.out.println("\nSous-menu Console: ");
                    System.out.println("1. Vérifier si le graphe est connexe");
                    System.out.println("2. Lancer Bellman-Ford");
                    System.out.println("3. ACPM");
                    System.out.println("4. Exit");
                    System.out.print("Votre choix : ");

                    sousChoix = scanner.nextInt();

                    if (sousChoix == 1) {
                        if (Loader.isConnectedBool())
                            System.out.println("Le graphe est connexe.");
                    } else if (sousChoix == 2) {

                        int idDepart;
                        int idDestination;
                        List<Edge> edges = Loader.getListEdge("file/graph");

                        System.out.print("Entrez l'ID de la station de départ : ");
                        idDepart = scanner.nextInt();

                        System.out.print("Entrez l'ID de la station de destination : ");
                        idDestination = scanner.nextInt();

                        loader.loadAllEdges(idDepart, idDestination, 0);

                        List<String> namedPath = BellmanFord.getNamedShortestPath(edges, idDepart, idDestination, Loader.getNumberSommets());
                        System.out.println("\nLe trajet avec le nom des stations :\n" + namedPath);

                    } else if (sousChoix == 3) {
                        List<Edge> edges = Loader.getListEdge("file/graph");
                        Set<Integer> vertices = new HashSet<>();

                        // Enregistrement des sommets
                        for (Edge edge : edges) {
                            vertices.add(edge.source);
                            vertices.add(edge.dest);
                        }

                        PrimAlgorithm.prim(edges, vertices);
                    } else if (sousChoix == 4) {
                        System.exit(0);
                    } else {
                        break;
                    }
                }
            } else if (choix == 3) {
                scanner.close();
                System.exit(0);
            } else {
                System.out.println("Choix non valide.");
            }
        }


    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../ressources/FXML/GPS.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("../ressources/CSS/Table.css").toExternalForm());
        primaryStage.setTitle("GPS Metro");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
