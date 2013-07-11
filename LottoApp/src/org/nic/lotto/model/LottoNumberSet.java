package org.nic.lotto.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LottoNumberSet 
{
	private StringProperty date;
	
	public String getDate()					{ return date.get(); }
	public StringProperty dateProperty()	{ return date; }
	public void setDate(final String value)		{ date.set(value); }
	
	
	private StringProperty numbers;
	
	public String getNumbers()				{ return numbers.get(); }
	public StringProperty numbersProperty() { return numbers; }
	public void setNumbers(final int[] n)	{ 
		numbers.set(
				n[0] + ", " + n[1] + ", " + n[2] + 
				", "+ n[3] + ", " + n[4] + ", " + n[5]
					);
	}
	
	private IntegerProperty szahl;
	
	public int getSZahl()					{ return szahl.get(); }
	public IntegerProperty szahlProperty()	{ return szahl; }
	public void setSzahl(final int value)	{ szahl.set(value); }
	
	
	public LottoNumberSet()
	{
		date = new SimpleStringProperty();
		numbers = new SimpleStringProperty();
		szahl = new SimpleIntegerProperty();
	}
	
}
