package org.nic.lotto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.net.HttpURLConnection;

import org.junit.Before;
import org.junit.Test;
import org.nic.lotto.util.ConnectionHelper;

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
		HttpURLConnection httpConnection = ConnectionHelper.connectTo(ConnectionHelper.LOTTOZAHLEN_URL);
		
		assertEquals(200, httpConnection.getResponseCode());		
	}
	
	@Test
	public void ReturnAnInputStreamFromURLConnection()
	{
		InputStream in = ConnectionHelper.getLottoInputStream(ConnectionHelper.connectTo(ConnectionHelper.LOTTOZAHLEN_URL));
		ConnectionHelper.getLottoNumbersFromInputStream(in);
		assertTrue(in!=null);
	}

	
}
