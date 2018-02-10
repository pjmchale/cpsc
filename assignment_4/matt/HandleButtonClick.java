import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

public class HandleButtonClick implements EventHandler<ActionEvent>
{

	private String btnName;

	public HandleButtonClick(String buttonName){
		btnName = buttonName;
	}

	@Override
	public void handle(ActionEvent event)
	{	
		// messageLabel.setText("Clicked " + message);
        // System.out.println("clicked");
        // HandleButtonClick();
        onClick();
    }

    public void onClick() {

		if (btnName == "withdraw"){
			System.out.print("WUTHINGSKDF");
		} else if (btnName == "deposit"){
			System.out.print("DEPOSTITING");
		}
		System.out.println(btnName);
    }
}