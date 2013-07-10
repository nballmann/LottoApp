package org.nic.lotto;

import java.util.HashMap;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import org.nic.lotto.util.IController;
import org.nic.lotto.view.controller.NumberPanelController;

public class LottoApp extends Application
{
	private static final String NUMBER_PANEL_ID = "numberPanel";
	private static final String NUMBER_PANEL_PATH = "view/NumberPanel.fxml";
	
	private NumberPanelController numberPanelController;
	
	private ObservableMap<Parent,IController> parentControllerMap = 
			FXCollections.observableMap(new HashMap<Parent,IController>());
	private ObservableMap<String,Parent> screens = 
			FXCollections.observableMap(new HashMap<String,Parent>());
	
	
	@Override
	public void start(Stage stage)  
	{
		try {			
			loadPanel(NUMBER_PANEL_ID, NUMBER_PANEL_PATH);
			
			Scene scene = new Scene(screens.get(NUMBER_PANEL_ID));
			
			screens.get(NUMBER_PANEL_ID).relocate(0, 0);		

			stage.setScene(scene);
		
			stage.maxHeightProperty().set(650);
			stage.maxWidthProperty().set(650);
			stage.centerOnScreen();
			stage.setResizable(false);
			stage.setTitle("App für Lottosüchtige Ver. 1.1B");
			stage.show();
			
			stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent arg0) {
					System.exit(0);
				}
				
			});
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		numberPanelController = (NumberPanelController) parentControllerMap.get(screens.get(NUMBER_PANEL_ID));
		numberPanelController.gotoPanel(900,900);

	}
	
	private <T extends Node> void loadPanel(String id, String resource)
	{
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
			Parent parent = (Parent) loader.load(); 
			IController controller = loader.getController();
			controller.setMainApp(this);
			parentControllerMap.put(parent, controller);
			screens.put(id, parent);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	public static void main(String[] args) {
		launch(args);
	}

	

	

}
