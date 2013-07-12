package org.nic.lotto.util;

import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.util.Duration;


public final class AnimationHelper
{
	
	public static ParallelTransition getTransition(final Group lottoNumber, final int numberIndex, final boolean isActualNumber)
	{
		ParallelTransition parallelTransition = new ParallelTransition();
		
		int xCoord = 90+numberIndex*70;
	
		
		parallelTransition.getChildren().addAll(
				getTranslateTransition(xCoord, numberIndex, lottoNumber, isActualNumber),
				getRotateTransition(numberIndex, lottoNumber)
				);
		
		parallelTransition.setInterpolator(Interpolator.EASE_OUT);
		
		parallelTransition.statusProperty().addListener(new ChangeListener<Animation.Status>() {

			@Override
			public void changed(ObservableValue<? extends Status> observable,
					Status oldValue, Status newValue) {
				lottoNumber.setVisible(true);
			}
		});
		
		return parallelTransition;
	}
	
	private static int getYCoordForNumberType(final boolean numberType)
	{
		if(numberType)
			return 110;
		else
			return 490;
	}
	
	private static TranslateTransition getTranslateTransition(final int xCoord, final int numberIndex, Group group, final boolean numberType)
	{
		TranslateTransition transition = new TranslateTransition(Duration.millis((8-numberIndex)*300), group);
		
		transition.setFromY(getYCoordForNumberType(numberType));
		transition.setToY(getYCoordForNumberType(numberType));
		transition.setFromX(650);
		transition.setToX(xCoord);
		transition.setCycleCount(1);
		transition.setAutoReverse(false);
		
		return transition;
	}
	
	
	
	private static RotateTransition getRotateTransition(final int numberIndex, final Group group)
	{
		RotateTransition transition = new RotateTransition(Duration.millis((8-numberIndex)*300), group);
		transition.setCycleCount(1);
		transition.setFromAngle(360-(numberIndex*numberIndex*15));
		transition.setToAngle(-360);
		transition.setByAngle(0);
		
		return transition;
	}

	
	
}
