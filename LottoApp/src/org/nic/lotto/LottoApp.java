package org.nic.lotto;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import org.nic.lotto.view.controller.NumberPanelController;

public class LottoApp extends Application
{

	
	public static final String LOTTOZAHLEN_URL = "http://www.lottozahlenonline.de/data_extern/lottozahlen.php";
	private static final String REGEX_IMG = "alt=\"([^\"]+)\"";
	
	@Override
	public void start(Stage stage) throws Exception {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("view/NumberPanel.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();
			NumberPanelController controller = loader.getController();
			controller.setMainApp(this);
			
			Scene scene = new Scene(pane);
			
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
	}

	public static void main(String[] args) {
		launch(args);
	}

	public HttpURLConnection connectTo(String lottozahlenUrl) {
		try {
			URL url = new URL(lottozahlenUrl);
			URLConnection con = url.openConnection();
			HttpURLConnection httpCon = (HttpURLConnection) con;
			
			return httpCon;
		} catch (IOException e) { }
			
		return null;
	}

	public InputStream getLottoInputStream(HttpURLConnection httpConnection) {
		try {
			final int responceCode = httpConnection.getResponseCode();
			if(responceCode == HttpURLConnection.HTTP_OK)
			{
				return httpConnection.getInputStream();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int[] getLottoNumbersFromInputStream(InputStream in)
	{
		ArrayList<String> strNumberList = new ArrayList<>();
		int[] lottoNumbers = new int[7];
		
		Scanner sc = new Scanner(in);
		Pattern p = Pattern.compile(REGEX_IMG);
		
		while(sc.findWithinHorizon(p, 0) != null)
		{
			MatchResult m = sc.match();
			String[] splittedResult = m.group(1).split(" ");
			strNumberList.add(splittedResult[1]);
		}
		
		for(int i=0;i<7;i++)
		{
			lottoNumbers[i] = Integer.parseInt(strNumberList.get(i));
			System.out.println(lottoNumbers[i]);
		}
		
		return lottoNumbers;
	}

	

}
