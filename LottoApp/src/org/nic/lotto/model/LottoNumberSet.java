package org.nic.lotto.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class LottoNumberSet 
{
	private StringProperty date;
	
	public String getDate()					{ return date.get(); }
	public StringProperty dateProperty()	{ return date; }
	public void setDate(final String value)					{ date.set(value); }
	
	
	private IntegerProperty zahl_1;
	
	public int getZahl_1()				{ return zahl_1.get(); }
	public IntegerProperty zahl_1Property()	{ return zahl_1; }
	public void setZahl_1(final int value)	{ zahl_1.set(value); }
	
	private IntegerProperty zahl_2;
	private IntegerProperty zahl_3;
	private IntegerProperty zahl_4;
	private IntegerProperty zahl_5;
	private IntegerProperty zahl_6;
	private IntegerProperty szahl;
	
	
	
	// TODO implement db object
}
