package org.nic.lotto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.net.HttpURLConnection;

import javafx.application.Application;
import javafx.stage.Stage;

import org.junit.Before;
import org.junit.Test;

public class EveryLottoAppShould
{
	LottoApp lottoApp;
	
	@Before
	public void init()
	{
		lottoApp = new LottoApp();
		
	}
	
	@Test
	public void BeAbleToConnectToLottoZahlenPhp() throws Exception
	{
		HttpURLConnection httpConnection = lottoApp.connectTo(LottoApp.LOTTOZAHLEN_URL);
		
		assertEquals(200, httpConnection.getResponseCode());		
	}
	
	@Test
	public void ReturnAnInputStreamFromURLConnection()
	{
		InputStream in = lottoApp.getLottoInputStream(lottoApp.connectTo(LottoApp.LOTTOZAHLEN_URL));
		lottoApp.getLottoNumbersFromInputStream(in);
		assertTrue(in!=null);
	}

	
}
