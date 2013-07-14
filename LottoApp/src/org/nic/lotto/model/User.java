package org.nic.lotto.model;

import javafx.beans.property.DoubleProperty;

public class User 
{
	private String name;
	private DoubleProperty money;
	
	
	public String getName()						{ return name; }
	public void setName(final String name)		{ this.name = name; }
	
	public double getMoney()					{ return money.get(); }
	public DoubleProperty moneyProperty()		{ return money; }
	public void setMoney(final double value)	{ money.set(value); }
	
}
