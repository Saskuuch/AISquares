/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;

/**
 *
 * @author t00688749
 */
public class JavaFXApplication1 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        StackPane layout = createLayout(primaryStage);
        Scene scene = new Scene(layout, 1000, 500);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public StackPane createLayout(Stage primaryStage){
        FileChooser inputChooser = new FileChooser();
        Button chooseFileButton = new Button("Select File");
        Label fileName = new Label();
        inputChooser.setInitialDirectory(new File("/C:/Users/t00688749/Desktop"));
        inputChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("png Files", "*.png"));
        
        Label picSize = new Label();
        
        
        
        chooseFileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File inputFile = inputChooser.showOpenDialog(primaryStage);
                fileName.setText(inputFile.getAbsolutePath());
            }
        });
        
        HBox h1 = new HBox();
        h1.getChildren().add(fileName);
        h1.getChildren().add(chooseFileButton);
        h1.setAlignment(Pos.CENTER);
        h1.setSpacing(5);
        
        HBox h2 = new HBox();
        h2.getChildren.add(picSize);
        
        
        StackPane root = new StackPane();
        root.getChildren().add(h1);
        
        return root;
    }
    
}
