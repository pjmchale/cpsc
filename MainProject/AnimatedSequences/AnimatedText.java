package AnimatedSequences;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class AnimatedText extends AnimationTimer{
	private double deltaX;
	private double deltaY;
	private long curTime;
	private long prevTime;
	private int curFrame;
	private double curX;
	private double curY;
	private int totalFrames;
	private double curAlpha = 1;
	private double deltaAlpha;
	private boolean loop = false;
	private Pane root;
	private Pane animationLayer;
	private Label message;
	public AnimatedText(String msg,Pane p , double x, double y, double seconds) {
		curX = x;
		curY = y;
		animationLayer = new Pane();
		root = p;
		p.getChildren().add(animationLayer);
		totalFrames = (int) (seconds*24);
		message = new Label(msg);
		message.setFont(Font.font("Courier New", FontWeight.BOLD, 20));
		message.setLayoutX(curX);
		message.setLayoutY(curY);
		p.getChildren().add(message);
	}
	public void setFadeOut() {
		deltaAlpha = (double)1/(totalFrames-8);
	}
	public void setDeltaX(double amount) {
		deltaX = amount;
	}
	public void setDeltaY(double amount) {
		deltaY = amount;
	}
	public void setCanLoop(boolean boo) {
		loop = boo;
	}
	public void moveX() {
		curX += deltaX;
		message.setLayoutX(curX);
	}
	public void moveY() {
		curY += deltaY;
		message.setLayoutY(curY);
		
	}
	public void fade() {
		curAlpha -= deltaAlpha;
		if(curAlpha <= 0) curAlpha = 0;
		//System.out.println(curAlpha);
		message.setTextFill(Color.rgb(0, 0, 0, curAlpha));
		
	}
    @Override
    public void handle(long now) {
		curTime = now;
		if(curTime - prevTime >= 42_000_000) {
			prevTime = curTime;
			if(loop == false && curFrame == totalFrames) {
				curFrame = totalFrames-1;
			}else{
				moveX();
				moveY();
				fade();
				if(curFrame >= totalFrames) {
					root.getChildren().remove(animationLayer);
				}
				curFrame %= totalFrames;
			}
			curFrame++;
			
		}
    }
}