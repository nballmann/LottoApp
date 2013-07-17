package org.nic.lotto.model;

public final class LottoWinClasses 
{
	private static final double ZWEI_RICHTIGE_UND_SUPERZAHL = 5.00;
	private static final double DREI_RICHTIGE = 10.40;
	private static final double DREI_RICHTIGE_UND_SUPERZAHL = 20.90;
	private static final double VIER_RICHTIGE = 42.40;
	private static final double VIER_RICHTIGE_UND_SUPERZAHL = 190.80;
	private static final double FUENF_RICHTIGE = 3340.60;
	private static final double FUENF_RICHTIGE_UND_SUPERZAHL = 10022.00;
	private static final double SECHS_RICHTIGE = 574596.50;
	private static final double SECHS_RICHTIGE_UND_SUPERZAHL = 8949642.20;

	
	public static double moneyValueForMatchingNumbers(int lottoNumberMatches, int superNumberMatch)
	{
		double value = 0;
		
		switch (lottoNumberMatches*2+superNumberMatch) {
		case 5:	value = ZWEI_RICHTIGE_UND_SUPERZAHL;
			break;
		case 6: value = DREI_RICHTIGE;
			break;
		case 7: value = DREI_RICHTIGE_UND_SUPERZAHL;
			break;
		case 8: value = VIER_RICHTIGE;
			break;
		case 9: value = VIER_RICHTIGE_UND_SUPERZAHL;
			break;
		case 10: value = FUENF_RICHTIGE;
			break;
		case 11: value = FUENF_RICHTIGE_UND_SUPERZAHL;
			break;
		case 12: value = SECHS_RICHTIGE;
			break;
		case 13: value = SECHS_RICHTIGE_UND_SUPERZAHL;
			break;
		default:
				
		}
		return value;
	}
}
