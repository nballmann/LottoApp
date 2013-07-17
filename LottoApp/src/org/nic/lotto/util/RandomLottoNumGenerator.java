package org.nic.lotto.util;

import java.util.ArrayList;
import java.util.Random;

public final class RandomLottoNumGenerator 
{
	public static ArrayList<Integer> generateNumbers()
	{
		ArrayList<Integer> numbers = new ArrayList<>();
		int count = 0;
		Random rng = new Random();
		
		while(count<6)
		{
			
			int rnd = rng.nextInt(49)+1;

			if(!numbers.contains(rnd))
			{
				numbers.add(rnd);
				count++;
			}
		}
		
//		for(Integer i : numbers)
//		{
//			System.out.println(i.toString());
//		}
		
		return numbers;
	}
	
	public static Integer generateSuperNumber()
	{
		return new Random().nextInt(10);
	}
}
