package espressioni.grafo;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EspressioniGrafo extends Application {

    InfixPostfixConverter converter = new InfixPostfixConverter();
    Albero a;

    @Override
    public void start(Stage primaryStage) {
        TreeView treeView = new TreeView();
        TextField expressionField = new TextField("1+1+2");
        Button submitButton = new Button("Submit");
        Button solveStepButton = new Button("Solve Step");
        Button solveButton = new Button("Solve All");
        submitButton.setDefaultButton(true);
        submitButton.setOnAction(e -> {
            ArrayList<String> tokens = converter.convert(expressionField.getText());
            if (tokens != null) {
                a = new Albero(tokens);
                a.print(treeView);
                submitButton.setDefaultButton(false);
                solveStepButton.setDefaultButton(true);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "The string isn't a valid expression");
                alert.showAndWait().filter(response -> response == ButtonType.OK);
            }
        });
        solveStepButton.setOnAction(e -> {
            if (a != null) {
                a.solveStep();
                a.print(treeView);

                if (!a.isSolvable()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "The expression is in it's base form");
                    alert.showAndWait().filter(response -> response == ButtonType.OK);
                }

            }
        });
        solveButton.setOnAction(e -> {
            if (a != null) {
                if (!a.isSolvable()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "The expression is in it's base form");
                    alert.showAndWait().filter(response -> response == ButtonType.OK);
                } else {
                    a.solve();
                    a.print(treeView);
                }
            }
        });

        HBox layout = new HBox(10, new VBox(10, new HBox(10, expressionField, submitButton), new HBox(10, solveStepButton, solveButton)), treeView);
        layout.setPadding(new Insets(10, 10, 10, 10));

        primaryStage.setTitle("espressioni grafo");
        primaryStage.setScene(new Scene(layout));
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
