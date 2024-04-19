//Work in progress UI, not functional at the moment, go to Algorithm.java for working algorithm
package javafxapplication1;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

/**
 *
 * @author t00688749
 */
public class JavaFXApplication1 extends Application {
    
    private static String backgroundStyle = "-fx-background-color: #27374D;";
    private static String buttonStyle = "-fx-font:normal 20px 'serif' #FFFFFF; -fx-background-color:#DC5E09";
    private static String sliderStyle;
    private static String labelStyle;
    
    @Override
    public void start(Stage primaryStage) {
        
        VBox layout = createLayout(primaryStage);
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
    
    public VBox createLayout(Stage primaryStage){
        //HBox1
        FileChooser inputChooser = new FileChooser();
        Button chooseFileButton = new Button("Select File");
        Label fileName = new Label();
        inputChooser.setInitialDirectory(new File("/C:/Users/t00688749/Desktop"));
        inputChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("png Files", "*.png"));
        
        //HBox2
        Label picSize = new Label();
        picSize.applyCss(labelStyle);
        
        //Hbox3
        Label rectSizeLabel = new Label("Max rectangle size: 0");
        Slider rectSizeSlider = new Slider();
        
        //HBox4
        Label popSizeLabel = new Label("Max population size: 100");
        Slider popSizeSlider = new Slider(1, 100, 10);
        
        //HBox5
        Label mutSizeLabel = new Label("Mutation level: 1%");
        Slider mutSizeSlider = new Slider(1, 100, 1);
        
        //HBox 6
        Button startButt = new Button("Start");
        
        
        chooseFileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File inputFile = inputChooser.showOpenDialog(primaryStage);
                fileName.setText(inputFile.getAbsolutePath());
                
                try{
                BufferedImage img = ImageIO.read(inputFile);
                picSize.setText("" + img.getWidth() + "px x " + img.getHeight() + "px");
                rectSizeSlider.setMax(Math.min(img.getWidth(), img.getHeight()));
                }
                catch(Exception e){
                System.out.println("Here");}
            }
        });
        
        rectSizeSlider.valueProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue){
                rectSizeLabel.setText("Max rectangle size: " + newValue.intValue());
            }
        });
        
        popSizeSlider.valueProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue){
                popSizeLabel.setText("Max population size: " + newValue.intValue()*10);
            }
        });
        
        mutSizeSlider.valueProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue){
                mutSizeLabel.setText("Mutation level: " + newValue.intValue() + "%");
            }
        });
        
        startButt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File targetFile = new File(fileName.getText());
                int width = 0, height = 0;
                int rectSize = (int)rectSizeSlider.getValue();
                int pop = (int)popSizeSlider.getValue() * 10;
                int mutRate = (int)mutSizeSlider.getValue();
                
                BufferedImage img = null;
                try{
                    img = ImageIO.read(targetFile);
                }
                catch(Exception e){}
                width = img.getWidth();
                height = img.getHeight();
            }
        });
        
        HBox h1 = new HBox();
        h1.getChildren().add(fileName);
        h1.getChildren().add(chooseFileButton);
        h1.setAlignment(Pos.CENTER);
        h1.setSpacing(5);
        
        HBox h2 = new HBox();
        h2.getChildren().add(picSize);
        h2.setAlignment(Pos.CENTER);
        
        HBox h3 = new HBox();
        h3.getChildren().add(rectSizeLabel);
        h3.getChildren().add(rectSizeSlider);
        h3.setAlignment(Pos.CENTER);
        h3.setSpacing(5);
        
        HBox h4 = new HBox();
        h4.getChildren().add(popSizeLabel);
        h4.getChildren().add(popSizeSlider);
        h4.setAlignment(Pos.CENTER);
        h4.setSpacing(5);
        
        HBox h5 = new HBox();
        h5.getChildren().add(mutSizeLabel);
        h5.getChildren().add(mutSizeSlider);
        h5.setAlignment(Pos.CENTER);
        h5.setSpacing(5);
        
        HBox h6 = new HBox();
        h6.getChildren().add(startButt);
        h5.setAlignment(Pos.CENTER);
        
        
        VBox root = new VBox();
        root.getChildren().add(h1);
        root.getChildren().add(h2);
        root.getChildren().add(h3);
        root.getChildren().add(h4);
        root.getChildren().add(h5);
        root.getChildren().add(h6);
        root.setAlignment(Pos.CENTER);
        h4.setSpacing(10);
        
        return root;
    }
    
}
