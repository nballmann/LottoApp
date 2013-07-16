package org.nic.lotto.view.controller;

import java.math.BigDecimal;
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
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
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
import org.nic.lotto.model.User;
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
	private Button addUserButton;
	
	@FXML
	private ComboBox<String> userChoiceComboBox;
	
	@FXML
	private ChoiceBox<BigDecimal> moneyChoiceBox;
	
	@FXML
	private TextField userNameInput;
	
	@FXML
	private ListView<String> listView;
	
	private ObservableMap<String,ToggleButton> gridButtons = 
			FXCollections.observableMap(new HashMap<String,ToggleButton>());
	
	private TableView<LottoNumberSet> lottoTableView = new TableView<LottoNumberSet>();
	private TableColumn<LottoNumberSet,String> dateColumn = new TableColumn<>();
	private TableColumn<LottoNumberSet,String> numbersColumn = new TableColumn<>();
	private TableColumn<LottoNumberSet,Integer> szahlColumn = new TableColumn<>();
	
	private ObservableList<BooleanProperty> userTipp = FXCollections.observableArrayList();
	
	private ArrayList<Integer> actualUserTipps = new ArrayList<>();
	
	private ArrayList<Integer> actualNumbers;
	
	private ObservableList<String> userTipps = FXCollections.observableArrayList();
	
	private User actualUser;
	
	private ObservableList<User> userList = FXCollections.observableArrayList();
	
	private ChangeListener<Number> comboBoxChangeListener = null;

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
	
	StringProperty oldString = new SimpleStringProperty("");
	
	@FXML
	private void handleTippAbgeben()
	{
		if(actualUser.getMoney()>2.5)
		{
			int z1 = actualUserTipps.get(0); 
			int z2 = actualUserTipps.get(1); 
			int z3 = actualUserTipps.get(2); 
			int z4 = actualUserTipps.get(3); 
			int z5 = actualUserTipps.get(4); 
			int z6 = actualUserTipps.get(5); 
			int sz = RandomLottoNumGenerator.generateSuperNumber();


			String sep = ", ";
			String tipps = z1 + sep + z2 + sep + z3 + sep + z4 + sep + z5 + sep + z6 + sep + sz;

			StringBuilder sb = new StringBuilder();

			ArrayList<Integer> result = compareLottoNumbers(actualUserTipps);
			if(result!=null && !result.isEmpty())
			{
				for(Integer i : result)
				{
					sb.append(i + sep);
				}
				sb.delete(sb.lastIndexOf(sep), sb.lastIndexOf(sep)+1);

				userTipps.add(tipps + " | " +  sb.toString());
			}

			DB_ConnectionHelper.insertNumbersIntoTipps(
					actualUser.getName(), TimeUtil.getFormattedTime(), 
					z1,z2,z3,z4,z5,z6,sz,sb.toString()
					);
			
			actualUser.setMoney(actualUser.getMoney()-2.5);
		}
		else
		{
			System.out.println("Benutzer " + actualUser.getName() + " ist Pleite");
			// TODO PopUp-Window No Money left
		}
	}
	
	@FXML
	private void handleAddUser()
	{
		int count = 0;
		
		for(User user : userList)
		{
			if(!user.getName().equals(userNameInput.getText().trim()))
			{
				count++;
			}
		}
		
		if(count==userList.size())
		{
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					User newUser = new User(userNameInput.getText());
					newUser.setMoney(moneyChoiceBox.getValue().doubleValue());
					userList.add(newUser);
					actualUser = newUser;
					
					DB_ConnectionHelper.insertNewUser(newUser);
					
					updateUserChoiceBox();
				}
				
			});
			
		} else {
			System.out.println("Existing name");
		}

	}
	
	private class BtnChangeListener implements ChangeListener<Boolean>
	{

		@Override
		public void changed(ObservableValue<? extends Boolean> observable,
				Boolean oldValue, Boolean newValue) {

			int count = 0;
			
			for(BooleanProperty bool : userTipp)
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
		
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				userList = DB_ConnectionHelper.getUsers();
			
				actualUser = userList.get(0);
				
				initUserComboBox();
				System.out.println("selectedindex: " + userChoiceComboBox.getSelectionModel().getSelectedIndex());
			}
		});
		
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				initActualNumbers();				
			}
		});
		
		moneyChoiceBox.setItems(FXCollections.observableArrayList(
				new BigDecimal(50.00), 
				new BigDecimal(45.00),
				new BigDecimal(40.00),
				new BigDecimal(35.00),
				new BigDecimal(30.00),
				new BigDecimal(25.00),
				new BigDecimal(20.00),
				new BigDecimal(15.00),
				new BigDecimal(10.00)));
		
		moneyChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> ov,
					Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				
			}
		});
		
		moneyChoiceBox.getSelectionModel().selectFirst();
		
		anchorPane_bottom.getChildren().add(lottoTableView);
		generateTableView();
		
		listView.setItems(userTipps);
		
		lottoTableView.relocate(75, 120);
	}
	
	private void initUserComboBox()
	{
		ObservableList<String> userNames = FXCollections.observableArrayList();

		for(User user : userList)
		{
			userNames.add(user.getName());
			System.out.println(user.getName());
		}

		userChoiceComboBox.getItems().addAll(userNames); 
		
		userChoiceComboBox.setValue(actualUser.getName());
		
		comboBoxChangeListener = new ChangeListener<Number>() {
			
			@Override
			public void changed(ObservableValue<? extends Number> ov,
					Number oldValue, Number newValue) {

				System.out.println("selectedindex: " + userChoiceComboBox.getSelectionModel().getSelectedIndex());
				actualUser = userList.get(userChoiceComboBox.getSelectionModel().getSelectedIndex());
			}
			
		};
		
		userChoiceComboBox.getSelectionModel().selectedIndexProperty().addListener(comboBoxChangeListener);
	}
	
	private void updateUserChoiceBox()
	{
		userChoiceComboBox.getItems().add(userList.get(userList.indexOf(actualUser)).getName());
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
		animationList.add(AnimationHelper.getTransition(
				(Group) anchorPane_center.lookup("#actualSuperNumber_" + actualNumbers.get(6)), 6, true));
		
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
			userTipp.add(bool);
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
	
	private ArrayList<Integer> compareLottoNumbers(final ArrayList<Integer> userTip)
	{
		ArrayList<Integer> matches = new ArrayList<>();
		
		for(Integer number : userTip)
		{
			if(actualNumbers.contains(number))
			{
				matches.add(number);
			}
		}
		
		return matches;
	}
	
	// TODO Gewinnberechnung 
	
}
