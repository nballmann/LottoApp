package org.nic.lotto.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public final class ConnectionHelper 
{
	public static final String LOTTOZAHLEN_URL = "http://www.lottozahlenonline.de/data_extern/lottozahlen.php";
	private static final String REGEX_IMG = "alt=\"([^\"]+)\"";
	
	private static final String PROXY_IP = "10.140.142.10";
	private static final String PROXY_PORT = "3128";
	
	
	public static HttpURLConnection connectTo(String lottozahlenUrl) {
		try {
//			Properties systemProperties = System.getProperties();
//			systemProperties.setProperty("http.proxyHost", PROXY_IP);
//			systemProperties.setProperty("http.proxyPort", PROXY_PORT);
			
			URL url = new URL(lottozahlenUrl);
			URLConnection con = url.openConnection();
			HttpURLConnection httpCon = (HttpURLConnection) con;
			
			return httpCon;
		} catch (IOException e) { }
			
		return null;
	}

	public static InputStream getLottoInputStream(HttpURLConnection httpConnection) {
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
	
	public static int[] getLottoNumbersFromInputStream(InputStream in)
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
		
		sc.close();
		
		for(int i=0;i<7;i++)
		{
			lottoNumbers[i] = Integer.parseInt(strNumberList.get(i));
			System.out.println(lottoNumbers[i]);
		}
		
		return lottoNumbers;
	}
	
	public static int[] getActualLottoNumbers()
	{
		return getLottoNumbersFromInputStream(getLottoInputStream(connectTo(LOTTOZAHLEN_URL)));		
	}
}
