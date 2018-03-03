import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import java.util.HashMap;
import java.util.Map;
import java.util.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.shape.Rectangle; 
import javafx.scene.shape.Circle;
import javafx.scene.Group; 
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.ColorAdjust;
import javafx.beans.binding.Bindings;
import javafx.scene.effect.*;
import javafx.scene.image.*;
import javafx.scene.text.Font;

public class infoView {
	Label title;
	Label ownerLabel;
	Label amountOfUnitsLabel;
	Rectangle background;
	Circle colorIcon;

	Pane root;

	infoView(double corX, double corY, String ownerName, int units, String countryName, int ownerId, Pane mainRoot) {
		root = mainRoot;

		title = new Label(countryName);
		title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		title.setLayoutX(corX);
		title.setLayoutY(corY);

		ownerLabel = new Label(ownerName);
		ownerLabel.setFont(Font.font("Arial", 12));
		corY = corY + 20;
		ownerLabel.setLayoutX(corX);
		ownerLabel.setLayoutY(corY);

		amountOfUnitsLabel = new Label("Units: "+units);
		amountOfUnitsLabel.setFont(Font.font("Arial", 12));
		corY = corY + 15;
		amountOfUnitsLabel.setLayoutX(corX);
		amountOfUnitsLabel.setLayoutY(corY);

		// int titleWidth = title.getWidth();
		// int ownerWidth = ownerLabel.getWidth();
		// int amountOfUnitsLabelWidth = amountOfUnitsLabel.getWidth();

		int titleWidth = 110;
		int ownerWidth = 30;
		int amountOfUnitsLabelWidth = 30;

		int backgroundWidth = Math.max(Math.max(titleWidth,ownerWidth),amountOfUnitsLabelWidth) + 40;

		background = new Rectangle(corX-10,corY-40,backgroundWidth,60);
		background.setFill(Color.rgb(255, 255, 255, 0.9));
		background.setArcHeight(10);
	    background.setArcWidth(10);

	    DropShadow backgroundShadow = new DropShadow();
	    backgroundShadow.setWidth(1);
	    backgroundShadow.setHeight(1);
	    backgroundShadow.setOffsetX(1);
	    backgroundShadow.setOffsetY(1);
	    backgroundShadow.setRadius(10);
	    background.setEffect(backgroundShadow);

	    colorIcon = new Circle();
		colorIcon.setCenterX(corX+60);
		colorIcon.setCenterY(corY-5);
		colorIcon.setRadius(10);
		colorIcon = iconBackgroundColor(ownerId, colorIcon);

		root.getChildren().add(background);
		root.getChildren().add(title);
		root.getChildren().add(ownerLabel);
		root.getChildren().add(amountOfUnitsLabel);
		root.getChildren().add(colorIcon);
	}

	public Circle iconBackgroundColor(int playerNumber, Circle circle) {
		int R = 0;
		int G = 0;
		int B = 0;

		switch(playerNumber) {
			case 1:
				// Green RGB(178, 255, 102)
				R = 178;
				G = 255;
				B = 102;
			break;
			case 2:
				// Red RGB(255, 51, 51)
				R = 255;
				G = 51;
				B = 51;
			break;
			case 3:
				// Orange RGB(255, 148, 102)
				R = 255;
				G = 148;
				B = 102;
			break;
			case 4:
				// Purple RGB(120, 131, 239)
				R = 120;
				G = 131;
				B = 239;
			break;
			default:
				// White RGB(255, 255, 255)
				R = 255;
				G = 255;
				B = 255;
		}
		circle.setFill(Color.rgb(R, G, B, 1.0));
		return circle;
	}

	public void clear() {
		root.getChildren().remove(colorIcon);
		root.getChildren().remove(background);
		root.getChildren().remove(title);
		root.getChildren().remove(ownerLabel);
		root.getChildren().remove(amountOfUnitsLabel);
	}
}