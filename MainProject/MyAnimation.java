import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class MyAnimation extends AnimationTimer {
	private ArrayList<Image>frames;
	private int totalFrames;
	private int curFrame;
	private double fps;
	private Pane pane;
	private double middleX;
	private double middleY;
	private long curTime;
	private ImageView frameImage;
	private long prevTime;
	private boolean loop;

	public MyAnimation(Pane pane, boolean isLoop){
		this.frames = new ArrayList<Image>();
		frameImage = new ImageView();
		
		loop = isLoop;
		this.pane = pane;
		middleX = 960/2;
		middleY = 600/2;
	}
	public void setMiddleCoord(double x, double y) {
		middleX = x;
		middleY = y;
	}
	public void addFrames(String name, int numFrames) {
		for (int i = 0; i < numFrames; i++) {
			String imagePath = "AnimatingImages/"+name +"_"+i+".png";
			frames.add(new Image(imagePath));
		}
		frameImage.setImage(frames.get(0));
		pane.getChildren().add(frameImage);
		totalFrames = frames.size();
	}
	public void addFrames(String name, int start, int end) {
		for (int i = start; i < end; i++) {
			String imagePath = "AnimatingImages/"+name +"_"+i+".png";
			frames.add(new Image(imagePath));
		}
		frameImage.setImage(frames.get(0));
		pane.getChildren().add(frameImage);
		totalFrames = frames.size();
	}

    @Override
    public void handle(long now) {
		curTime = now;
		if(curTime - prevTime >= 42_000_000) {
			prevTime = curTime;
			if(loop == false && curFrame == totalFrames) {
				curFrame--;
			}else{
				curFrame %= totalFrames;
			}
			Image frame = frames.get(curFrame);
			frameImage.setImage(frame);
			frameImage.setLayoutX(middleX - frame.getWidth()/2);
			frameImage.setLayoutY(middleY - frame.getHeight()/2);
			curFrame++;
			
		}
    }
}