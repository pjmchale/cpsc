import javafx.scene.layout.Pane;

import java.util.ArrayList;

import javafx.scene.image.Image;

public class AnimationTransition{
	private static ArrayList<Image> toBattleTransition;
	
	public static ArrayList<Image> getToBattleTransition(Pane pane) {
		ArrayList<Image> clone = new ArrayList<Image>();
		
		if(toBattleTransition == null) {
			MyAnimation transition= new MyAnimation(pane,false);
			transition.addFrames("battleStarting", 43);	
			toBattleTransition = transition.getFrames();
		}
		for (Image image : toBattleTransition) clone.add(image);
		return clone;
	}
}