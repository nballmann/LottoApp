package org.nic.lotto.view.controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import org.nic.lotto.LottoApp;
import org.nic.lotto.model.LottoNumber;
import org.nic.lotto.model.LottoNumberSet;
import org.nic.lotto.util.AnimationHelper;
import org.nic.lotto.util.ConnectionHelper;
import org.nic.lotto.util.DB_ConnectionHelper;
import org.nic.lotto.util.IController;
import org.nic.lotto.util.RandomLottoNumGenerator;
import org.nic.lotto.util.TimeUtil;

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
	
	@FXML
	private Button tippButton;
	
	@FXML
	private TextField moneyInput;
	
	private ObservableMap<String,ToggleButton> gridButtons = 
			FXCollections.observableMap(new HashMap<String,ToggleButton>());
	
	private TableView<LottoNumberSet> lottoTableView = new TableView<LottoNumberSet>();
	private TableColumn<LottoNumberSet,String> dateColumn = new TableColumn<>();
	private TableColumn<LottoNumberSet,String> numbersColumn = new TableColumn<>();
	private TableColumn<LottoNumberSet,Integer> szahlColumn = new TableColumn<>();
	
	private ObservableList<BooleanProperty> userTipps = FXCollections.observableArrayList();
	
	private ArrayList<Integer> actualUserTipps = new ArrayList<>();

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
	
	@FXML
	private void handleReset()
	{
		for(int i=1;i<50;i++)
		{
			gridButtons.get(String.valueOf(i)).selectedProperty().set(false);
		}
	}
	
	String oldString = "";

	private ArrayList<Integer> actualNumbers;
	
	@FXML
	private void handleTextInput()
	{
		if(moneyInput.getText()=="")
			oldString="";
		else {
			
			try {
				double d = Double.parseDouble(moneyInput.getText());
				
				NumberFormat fm = new DecimalFormat("#0.00");
				moneyInput.setText(fm.format(d) + "€");
				
			} catch (NumberFormatException e) {
				moneyInput.setText(oldString);
			}
		}
		
		oldString = moneyInput.getText();
		
	}
	
	@FXML
	private void handleTippAbgeben()
	{
		DB_ConnectionHelper.insertNumbersIntoTipps(
				TimeUtil.getFormattedTime(), 
				actualUserTipps.get(0), 
				actualUserTipps.get(1), 
				actualUserTipps.get(2), 
				actualUserTipps.get(3), 
				actualUserTipps.get(4), 
				actualUserTipps.get(5), 
				RandomLottoNumGenerator.generateSuperNumber()
				);
	}
	
	private class BtnChangeListener implements ChangeListener<Boolean>
	{

		@Override
		public void changed(ObservableValue<? extends Boolean> observable,
				Boolean oldValue, Boolean newValue) {

			int count = 0;
			
			for(BooleanProperty bool : userTipps)
			{
				if(bool.get())
				{
					count++;
				}
			}
			if(count==6)
			{
				actualUserTipps.clear();
				
				for(int i=1;i<50;i++)
				{
					if(!gridButtons.get(String.valueOf(i)).isSelected())
						gridButtons.get(String.valueOf(i)).disableProperty().set(true);
					else
						actualUserTipps.add(i);
				}
				
				tippButton.disableProperty().set(false);
			}
			else
			{
				tippButton.disableProperty().set(true);
				for(int i=1;i<50;i++)
				{
					gridButtons.get(String.valueOf(i)).disableProperty().set(false);
				}
			}
		}
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		gridPane.getStyleClass().add("grid-pane");	
		initGrid();
		tippButton.disableProperty().set(true);
		
		manageUserTipps();
		
		initRndNumbers();
		
		initActualNumbers();
		
		anchorPane_bottom.getChildren().add(lottoTableView);
		generateTableView();
		
		lottoTableView.relocate(75, 120);
	}
	
	private void initActualNumbers()
	{
		actualNumbers = ConnectionHelper.getActualLottoNumbers();
		
		ArrayList<Animation> animationList = new ArrayList<>();
		
		for(int i=0; i<6;i++)
		{
			Group actualNumber = new LottoNumber(actualNumbers.get(i), 'a');
			anchorPane_center.getChildren().add(actualNumber);
			actualNumber.setVisible(false);

			ParallelTransition transition = AnimationHelper.getTransition(actualNumber, i, true);
			animationList.add(transition);
		}
		anchorPane_center.getChildren().add(new LottoNumber(actualNumbers.get(6), false));
		anchorPane_center.lookup("#actualSuperNumber_" + actualNumbers.get(6)).setVisible(false);
		animationList.add(AnimationHelper.getTransition((Group) anchorPane_center.lookup("#actualSuperNumber_" + actualNumbers.get(6)), 6, true));
		
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
				tempBtn.setId(String.valueOf((j+1)+(i*7)));
				
				gridButtons.put(String.valueOf((j+1)+(i*7)), tempBtn);
				gridPane.add(tempBtn, j, i);
			}
		}
	}
	
	private void manageUserTipps()
	{
		for(int i=1;i<50;i++)
		{
			BooleanProperty bool = new SimpleBooleanProperty(false);
			bool.addListener(new BtnChangeListener());
			gridButtons.get(String.valueOf(i)).selectedProperty().bindBidirectional(bool);
			userTipps.add(bool);
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
	 

	@SuppressWarnings("unchecked")
	private void generateTableView()
	{
		dateColumn.setPrefWidth(100);
		dateColumn.setMaxWidth(100);
		dateColumn.setEditable(false);
		dateColumn.setText("Date");
		
		numbersColumn.setPrefWidth(250);
		numbersColumn.setMaxWidth(250);
		numbersColumn.setEditable(false);
		numbersColumn.setText("Lottozahlen");
		
		szahlColumn.setPrefWidth(100);
		szahlColumn.setMaxWidth(100);
		szahlColumn.setEditable(false);
		szahlColumn.setText("SuperZahl");
		
		lottoTableView.setPrefWidth(450);
		lottoTableView.setPrefHeight(450);
		
		dateColumn.setCellValueFactory(new PropertyValueFactory<LottoNumberSet, String>("date"));
		numbersColumn.setCellValueFactory(new PropertyValueFactory<LottoNumberSet, String>("numbers"));
		szahlColumn.setCellValueFactory(new PropertyValueFactory<LottoNumberSet, Integer>("szahl"));
		
		lottoTableView.columnResizePolicyProperty().set(TableView.CONSTRAINED_RESIZE_POLICY);
		lottoTableView.getColumns().addAll(dateColumn,numbersColumn,szahlColumn);
		
		lottoTableView.setItems(DB_ConnectionHelper.getLottoZiehungen());
	}
	
}
