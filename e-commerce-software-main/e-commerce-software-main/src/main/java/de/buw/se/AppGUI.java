package de.buw.se;

//import java.awt.Menu;
//import javafx.scene.control.MenuBar;
//import javafx.scene.layout.VBox;
//import javafx.scene.control.MenuItem;
//import javafx.scene.control.SeparatorMenuItem;
//import javafx.scene.layout.Pane;
//import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
//import javafx.scene.layout.StackPane;

//import java.awt.Menu;
//import de.buw.se.*;
//import javafx.scene.control.MenuBar;
//import javafx.scene.layout.VBox;
//import javafx.scene.control.MenuItem;
//import javafx.scene.control.SeparatorMenuItem;
//import javafx.scene.layout.Pane;
//import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
//import javafx.scene.layout.StackPane;
import javafx.application.Application;
import javafx.stage.Stage;


public class AppGUI extends Application {

	@Override
	public void start(Stage primaryStage) {
		
	HomePage homepage = new HomePage();
	homepage.start(primaryStage);
	
	}

	public static void main(String[] args) {
		launch(args);
	}
}

