package org.nic.lotto.view.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import org.nic.lotto.LottoApp;
import org.nic.lotto.model.LottoNumber;
import org.nic.lotto.model.LottoNumberSet;
import org.nic.lotto.util.AnimationHelper;
import org.nic.lotto.util.ConnectionHelper;
import org.nic.lotto.util.IController;
import org.nic.lotto.util.RandomLottoNumGenerator;

public class NumberPanelController implements Initializable, IController
{
	@SuppressWarnings("unused")
	private LottoApp lottoApp;
	
	@FXML
	private GridPane gridPane;
	
	@FXML
	private AnchorPane anchorPane;
	
	@FXML
	private AnchorPane anchorPane_center;
	
	@FXML
	private AnchorPane anchorPane_top;
	
	@FXML
	private AnchorPane anchorPane_right;
	
	@FXML
	private AnchorPane anchorPane_bottom;
	
	@FXML
	private AnchorPane anchorPane_left;
	
	@FXML
	private ScrollPane scrollPane;
	
	@FXML
	private Group centerToLeft;
	
	private ObservableMap<String,ToggleButton> gridButtons = FXCollections.observableMap(new HashMap<String,ToggleButton>());
	
	private TableView<LottoNumberSet> lottoTableView;

	@FXML
	private void handleCenterToLeft()
	{
		gotoPanel(0,900);
	}
	
	@FXML
	private void handleCenterToTop()
	{
		gotoPanel(900,0);
	}
	
	@FXML
	private void handleCenterToRight()
	{
		gotoPanel(1800,900);
	}
	
	@FXML
	private void handleCenterToBottom()
	{
		gotoPanel(900,1800);
	}
	
	@FXML
	private void handleToCenter()
	{
		gotoPanel(900,900);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		gridPane.getStyleClass().add("grid-pane");	
		initGrid();

		initRndNumbers();
		
		initActualNumbers();
	}
	
	private void initActualNumbers()
	{
		int[] actualNumbers = ConnectionHelper.getActualLottoNumbers();
		
		ArrayList<Animation> animationList = new ArrayList<>();
		
		for(int i=0; i<6;i++)
		{
			Group actualNumber = new LottoNumber(actualNumbers[i], 'a');
			anchorPane_center.getChildren().add(actualNumber);
			actualNumber.setVisible(false);
//			anchorPane_center.lookup("#actualNumber_" + actualNumbers[i]).relocate(60+i*70, 80);
//			actualNumber.relocate(650, 80);
			ParallelTransition transition = AnimationHelper.getTransition(actualNumber, i, true);
			animationList.add(transition);
		}
		anchorPane_center.getChildren().add(new LottoNumber(actualNumbers[6], false));
//		anchorPane_center.lookup("#actualSuperNumber_" + actualNumbers[6]).relocate(60+6*70, 80);
		anchorPane_center.lookup("#actualSuperNumber_" + actualNumbers[6]).setVisible(false);
		animationList.add(AnimationHelper.getTransition((Group) anchorPane_center.lookup("#actualSuperNumber_" + actualNumbers[6]), 6, true));
		
		SequentialTransition sequenz = new SequentialTransition();
		sequenz.getChildren().addAll(animationList);
		sequenz.play();
	}
	
	private void initRndNumbers()
	{
		ArrayList<Integer> rndNumbers = RandomLottoNumGenerator.generateNumbers();
		Integer superNumber = RandomLottoNumGenerator.generateSuperNumber();
		int count = 0;
		
		for(Integer i : rndNumbers)
		{
			anchorPane_center.getChildren().add(new LottoNumber(i));
			anchorPane_center.lookup("#number_" + i).relocate(60+count*70, 460);
			count++;
		}
		
		anchorPane_center.getChildren().add(new LottoNumber(superNumber, true));
		anchorPane_center.lookup("#superNumber_" + superNumber).relocate(60+count*70, 460);
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

	public void setMainApp(LottoApp lottoApp) 
	{
		this.lottoApp = lottoApp;
	}
	
	public void gotoPanel(int x, int y)
	{
		System.out.println(scrollPane.getHvalue());
	
		final Timeline timeline = new Timeline();
		timeline.setCycleCount(1);
		timeline.setAutoReverse(false);
		
		final KeyValue kv_x = new KeyValue(scrollPane.hvalueProperty(), x);
		final KeyFrame kf_x = new KeyFrame(Duration.millis(500), kv_x);
		final KeyValue kv_y = new KeyValue(scrollPane.vvalueProperty(), y);
		final KeyFrame kf_y = new KeyFrame(Duration.millis(500), kv_y);
		
		timeline.getKeyFrames().addAll(kf_x,kf_y);
		timeline.play();
	}
	 

	private void generateTableView()
	{
		
	}
	
}
