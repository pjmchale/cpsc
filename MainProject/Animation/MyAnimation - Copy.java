package Animation;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
/**
 * An animation system that takes images as frames and plays them at a specific fps (Frames Per Second)
 */
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
	/**
	 * initialize the class
	 * @param pane the pane this animation will be drawn on
	 * @param isLoop determines if the animation will loop
	 */
	public MyAnimation(MyAnimation ma) {
		this(ma.getPane(),ma.getLoop());
		frames = ma.getFrames();
		
	}
	public MyAnimation(Pane pane, boolean isLoop){
		this.pane = pane;
		this.frames = new ArrayList<Image>();
		frameImage = new ImageView();	
		loop = isLoop;
		middleX = 960/2;
		middleY = 600/2;
	}
	public MyAnimation(boolean isLoop) {
		this.frames = new ArrayList<Image>();
		frameImage = new ImageView();	
		loop = isLoop;
		middleX = 960/2;
		middleY = 600/2;
	}
	/**
	 * set the center coordinate of the animation
	 * @param x the x-value of the center coordinate
	 * @param y the y-value of the center coordinate
	 */
	public void setMiddleCoord(double x, double y) {
		middleX = x;
		middleY = y;
	}
	
	/**
	 * add a collection of images by the name of the animation sequence
	 * @param name the name of the animation
	 * @param numFrames the number of frames in that animation
	 */
	public void addFrames(String name, int numFrames) {
		for (int i = 1; i <= numFrames; i++) {
			String imagePath = "AnimatingImages/"+name +"_";
			if(i < 10) imagePath += "000";
			else if(i < 100) imagePath += "00";
			else if(i < 1000) imagePath += "0";
			imagePath += i+".png";;
			frames.add(new Image(imagePath));
		}
		frameImage.setImage(frames.get(0));
		pane.getChildren().add(frameImage);
		totalFrames = frames.size();
	}
	public void setFrames(ArrayList<Image>frames) {
		this.frames = frames;
		frameImage.setImage(frames.get(0));
		pane.getChildren().add(frameImage);
		totalFrames = frames.size();
	}
	/**
	 * add a collection of images by the name of the animation sequence
	 * @param name the name of the animation
	 * @param start the starting frame of the animation
	 * @param end the last frame of the animation
	 */
	public void addFrames(String name, int start, int end) {
		for (int i = start; i <= end; i++) {
			String imagePath = "AnimatingImages/"+name +"_";
			if(i < 10) imagePath += "000";
			else if(i < 100) imagePath += "00";
			else if(i < 1000) imagePath += "0";
			imagePath += i+".png";;
			frames.add(new Image(imagePath));
		}
		frameImage.setImage(frames.get(0));
		pane.getChildren().add(frameImage);
		totalFrames = frames.size();
	}
	public ArrayList<Image> getFrames(){
		return frames;
	}
	public void setPane(Pane p) {
		pane = p;
	}
	public Pane getPane() {
		return pane;
	}
	public boolean getLoop() {
		return loop;
	}

    @Override
    public void handle(long now) {
		curTime = now;
		if(curTime - prevTime >= 42_000_000) {
			prevTime = curTime;
			if(loop == false && curFrame == totalFrames) {
				curFrame = totalFrames-1;
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