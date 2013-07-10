package org.nic.lotto.model;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public final class LottoNumber extends Group
{
	public LottoNumber(int number)
	{
		Circle circ = new Circle(30.0, Color.WHITE);
		circ.getStyleClass().add("circle-effect");
		
		Text txt = new Text(String.valueOf(number));
		txt.getStyleClass().add("lotto-label");
		if(number<10)
		{
			txt.setLayoutX(-9);
			txt.setLayoutY(11);
		} 
		else
		{
			txt.setLayoutX(-18);
			txt.setLayoutY(11);
		}
		
		idProperty().set("number_" + number);
		getChildren().addAll(circ, txt);
	}
	
	public LottoNumber(int number, char actual)
	{
		Circle circ = new Circle(30.0, Color.WHITE);
		circ.getStyleClass().add("circle-effect");
		
		Text txt = new Text(String.valueOf(number));
		txt.getStyleClass().add("lotto-label");
		if(number<10)
		{
			txt.setLayoutX(-9);
			txt.setLayoutY(11);
		} 
		else
		{
			txt.setLayoutX(-18);
			txt.setLayoutY(11);
		}
		
		idProperty().set("actualNumber_" + number);
		getChildren().addAll(circ, txt);
		
	}
	
	public LottoNumber(int number, boolean isSuperNumber)
	{
		Circle circ = new Circle(30.0, Color.WHITE);
		circ.getStyleClass().add("circle-effect-sz");
		
		Text txt = new Text(String.valueOf(number));
		txt.getStyleClass().add("lotto-label");
		txt.setLayoutX(-9);
		txt.setLayoutY(11);
		
		if(isSuperNumber)
		{
			idProperty().set("superNumber_" + number);
			
		} else {
			idProperty().set("actualSuperNumber_" + number);
		}
		getChildren().addAll(circ, txt);	
	}
}
