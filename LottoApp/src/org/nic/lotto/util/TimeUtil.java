package org.nic.lotto.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class TimeUtil 
{
	private static final String TIMEFORMAT = "yyyy-MM-dd";
	
	
	public static String getFormattedTime()
	{
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat(TIMEFORMAT);
			
		return df.format(date);
	}
	
	public static Date getDateFromString(String formattedDateString)
	{
		SimpleDateFormat df = new SimpleDateFormat(TIMEFORMAT);
		try {
			return df.parse(formattedDateString);
		} catch (ParseException e) {
			return new Date();
		}
	}
	
	public static String getLastLottoDate()
	{
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		
		if(cal.get(Calendar.DAY_OF_WEEK)>Calendar.WEDNESDAY&&cal.get(Calendar.DAY_OF_WEEK)<Calendar.SATURDAY)
		{
			
		}
		
		return null;
		
	}
}

