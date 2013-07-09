package org.nic.lotto.view.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import org.nic.lotto.LottoApp;
import org.nic.lotto.util.IController;

public class NumberPanelController implements Initializable, IController
{
	@SuppressWarnings("unused")
	private LottoApp lottoApp;
	
	@FXML
	private GridPane gridPane;
	
	private ObservableMap<String,ToggleButton> gridButtons = FXCollections.observableMap(new HashMap<String,ToggleButton>());

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		gridPane.getStyleClass().add("grid-pane");	
		initGrid();
		
		Text txt = new Text("Number");
		
	}
	
	private void initGrid()
	{
		for(int i=0;i<7;i++)
		{
			for(int j=0;j<7;j++)
			{
				ToggleButton tempBtn = new ToggleButton(String.valueOf((j+1)+(i*7)));
				tempBtn.setPrefSize(50, 50);
				tempBtn.setAlignment(Pos.CENTER);
				
				gridButtons.put(String.valueOf((j+1)+(i*7)), tempBtn);
				
				gridPane.add(tempBtn, j, i);
			}
		}
	}

	public void setMainApp(LottoApp lottoApp) {
		this.lottoApp = lottoApp;
	}
}
