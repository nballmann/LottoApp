package org.nic.lotto.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class User 
{
	private StringProperty name;
	private DoubleProperty money;
	private ObservableList<LottoNumberSet> tipps = FXCollections.observableArrayList();
	
	public String getName()						{ return name.get(); }
	public StringProperty nameProperty()		{ return name; }
	public void setName(final String name)		{ this.name.set(name); }
	
	public double getMoney()					{ return money.get(); }
	public DoubleProperty moneyProperty()		{ return money; }
	public void setMoney(final double value)	{ money.set(value); }
	
	public void addTipp(LottoNumberSet tipp) 
	{
		tipps.add(tipp);
	}
	
	public ObservableList<LottoNumberSet> getTipps() { return tipps; }
	
	public void setTipps(final ObservableList<LottoNumberSet> tipps) { this.tipps = tipps; }
	
	
	public User(final String name)
	{
		this.name = new SimpleStringProperty();
		money = new SimpleDoubleProperty();
		
		this.name.set(name);
	}
	
}
