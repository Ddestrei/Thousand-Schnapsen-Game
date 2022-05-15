package scenes;

import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;

public class FirstWindow extends Group{
	public Button create;
	public Button join;
	double screenSizeWidth=1;
	double screenSizeHeight=1;
	public FirstWindow() {
		setSizeOfWindow();
		mainRentangle();
		imageViewWelcome();
		imageViewCreate();
		imageViewJoin();
		addButtonJoin();
		addButtonCreate();
	}
	
	void setSizeOfWindow() {
		double screenWidth=Screen.getPrimary().getBounds().getWidth();
		double screenHeight=Screen.getPrimary().getBounds().getHeight();
		System.out.println(screenWidth+"ScreenWidth");
		System.out.println(screenHeight+"ScreenHeight");
		//double screenWidth=1300;
		//double screenHeight=700;
		if(screenWidth<800) {
			screenSizeWidth=(screenWidth/850);
		}
		if(screenHeight<900) {
			screenSizeHeight=(screenHeight/950);
		}
	}
	
	void addButtonCreate() {
		create = new Button();
		create.setLayoutX(250*screenSizeWidth);
		create.setLayoutY(350*screenSizeHeight);
		create.setPrefWidth(300*screenSizeWidth);
		create.setPrefHeight(100*screenSizeHeight);
		create.setOpacity(0);
		this.getChildren().add(create);
	}
	void addButtonJoin() {
		join = new Button();
		join.setLayoutX(250*screenSizeWidth);
		join.setLayoutY(200*screenSizeHeight);
		join.setPrefWidth(300*screenSizeWidth);
		join.setPrefHeight(100*screenSizeHeight);
		join.setOpacity(0);
		this.getChildren().add(join);
	}
	void mainRentangle() {
		Rectangle r = new Rectangle();
		r.setX(0*screenSizeWidth);
		r.setY(0*screenSizeHeight);
		r.setWidth(800*screenSizeWidth);
		r.setHeight(900*screenSizeHeight);
		Color c = Color.web("eb1c24");
		r.setFill(c);
		this.getChildren().add(r);
	}
	void imageViewWelcome() {
		ImageView r = new ImageView();
		r.setFitWidth(700*screenSizeWidth);
		r.setFitHeight(100*screenSizeHeight);
		r.setX(50*screenSizeWidth);
		r.setY(50*screenSizeHeight);
		r.setImage(new Image("/resources/Welcome.jpg"));
		this.getChildren().add(r);
	} 
	void imageViewCreate() {
		ImageView r = new ImageView();
		r.setFitWidth(300*screenSizeWidth);
		r.setFitHeight(100*screenSizeHeight);
		r.setX(250*screenSizeWidth);
		r.setY(350*screenSizeHeight);
		r.setImage(new Image("/resources/CreateServer.jpg"));
		this.getChildren().add(r);
	}
	void imageViewJoin() {
		ImageView r = new ImageView();
		r.setFitWidth(300*screenSizeWidth);
		r.setFitHeight(100*screenSizeHeight);
		r.setX(250*screenSizeWidth);
		r.setY(200*screenSizeHeight);
		r.setImage(new Image("/resources/joinServer.jpg"));
		this.getChildren().add(r);
	}
}
