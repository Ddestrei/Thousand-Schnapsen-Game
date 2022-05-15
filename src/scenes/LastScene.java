package scenes;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import server.MainServer;

public class LastScene extends Group{
	public double screenSizeWidth=1;
	public double screenSizeHeight=1;
	public TextField winner;
	public TextField second;
	public TextField third;
	public TextField last;
	public Button menuButton;
	public LastScene() {
		setSizeOfWindow();
		addMainRen();
		//addImageViewMenu();
		addImageViewExit();
		addButtonExit();
		switch (MainServer.typeServer) {
		case TWO_PLAYER:
			addFirstImageViewWinner();
			addSecondImageViewSecond();
			addTextFiledWinner();
			addTextFiledSecond();
			
			break;
		case THREE_PLAYER:
			addFirstImageViewWinner();
			addSecondImageViewSecond();
			addThirdImgeViewThird();
			addTextFiledWinner();
			addTextFiledSecond();
			addTextFiledThird();
			break;
		case FOUR_PLAYER:
			addFirstImageViewWinner();
			addSecondImageViewSecond();
			addThirdImgeViewThird();
			addFourthImgeViewLast();
			addTextFiledWinner();
			addTextFiledSecond();
			addTextFiledThird();
			addTextFiledLast();
			break;
		}
	}
	void setSizeOfWindow() {
		double screenWidth=Screen.getPrimary().getBounds().getWidth();
		double screenHeight=Screen.getPrimary().getBounds().getHeight();
		//double screenWidth=1300;
		//double screenHeight=700;
		if(screenWidth<800) {
			screenSizeWidth=(screenWidth/850);
		}
		if(screenHeight<900) {
			screenSizeHeight=(screenHeight/950);
		}
	}
	void addButtonExit() {
		Button b = new Button();
		b.setPrefWidth(400*screenSizeWidth);
		b.setPrefHeight(100*screenSizeHeight);
		b.setLayoutX(200*screenSizeWidth);
		b.setLayoutY(790*screenSizeHeight);
		b.setOpacity(0);
		b.addEventHandler(ActionEvent.ACTION, e->{
			System.exit(0);
		});
		this.getChildren().add(b);
	}
	void addImageViewExit() {
		ImageView w = new ImageView();
		w.setFitWidth(400*screenSizeWidth);
		w.setFitHeight(100*screenSizeHeight);
		w.setX(200*screenSizeWidth);
		w.setY(790*screenSizeHeight);
		w.setImage(new Image("/resources/EXITGAME.jpg"));
		this.getChildren().add(w);
	}
	void addImageViewMenu() {
		ImageView w = new ImageView();
		w.setFitWidth(400*screenSizeWidth);
		w.setFitHeight(100*screenSizeHeight);
		w.setX(200*screenSizeWidth);
		w.setY(650*screenSizeHeight);
		w.setImage(new Image("/resources/MAIN_MENU.jpg"));
		this.getChildren().add(w);
	}
	void addTextFiledLast() {
		last= new TextField();
		last.setPrefWidth(400*screenSizeWidth);
		last.setPrefHeight(100*screenSizeHeight);
		last.setLayoutX(390*screenSizeWidth);
		last.setLayoutY(500*screenSizeHeight);
		last.setAlignment(Pos.CENTER);
		last.setEditable(false);
		last.setFont(new Font(20));
		this.getChildren().add(last);
	}
	void addTextFiledThird() {
		third= new TextField();
		third.setPrefWidth(400*screenSizeWidth);
		third.setPrefHeight(100*screenSizeHeight);
		third.setLayoutX(390*screenSizeWidth);
		third.setLayoutY(350*screenSizeHeight);
		third.setAlignment(Pos.CENTER);
		third.setEditable(false);
		third.setFont(new Font(20));
		this.getChildren().add(third);
	}
	void addTextFiledSecond() {
		second= new TextField();
		second.setPrefWidth(400*screenSizeWidth);
		second.setPrefHeight(100*screenSizeHeight);
		second.setLayoutX(390*screenSizeWidth);
		second.setLayoutY(200*screenSizeHeight);
		second.setAlignment(Pos.CENTER);
		second.setEditable(false);
		second.setFont(new Font(20));
		this.getChildren().add(second);
	}
	void addTextFiledWinner() {
		winner= new TextField();
		winner.setPrefWidth(400*screenSizeWidth);
		winner.setPrefHeight(100*screenSizeHeight);
		winner.setLayoutX(390*screenSizeWidth);
		winner.setLayoutY(50*screenSizeHeight);
		winner.setAlignment(Pos.CENTER);
		winner.setEditable(false);
		winner.setFont(new Font(20));
		this.getChildren().add(winner);
	}
	void addThirdImgeViewThird() {
		ImageView w = new ImageView();
		w.setFitWidth(400*screenSizeWidth);
		w.setFitHeight(100*screenSizeHeight);
		w.setX(20*screenSizeWidth);
		w.setY(350*screenSizeHeight);
		w.setImage(new Image("/resources/THETHIRDPLACE.jpg"));
		this.getChildren().add(w);
	}
	void addFourthImgeViewLast() {
		ImageView w = new ImageView();
		w.setFitWidth(400*screenSizeWidth);
		w.setFitHeight(100*screenSizeHeight);
		w.setX(20*screenSizeWidth);
		w.setY(500*screenSizeHeight);
		w.setImage(new Image("/resources/THE_LAST_PLACE.jpg"));
		this.getChildren().add(w);
	}
	void addFirstImageViewWinner() {
		ImageView w = new ImageView();
		w.setFitWidth(400*screenSizeWidth);
		w.setFitHeight(100*screenSizeHeight);
		w.setX(20*screenSizeWidth);
		w.setY(50*screenSizeHeight);
		w.setImage(new Image("/resources/Thewinner.jpg"));
		this.getChildren().add(w);
	}
	void addSecondImageViewSecond() {
		ImageView w = new ImageView();
		w.setFitWidth(400*screenSizeWidth);
		w.setFitHeight(100*screenSizeHeight);
		w.setX(20*screenSizeWidth);
		w.setY(200*screenSizeHeight);
		w.setImage(new Image("/resources/SECOND_PLACE.jpg"));
		this.getChildren().add(w);
	}
	void addMainRen() {
		Rectangle r = new Rectangle();
		r.setX(0*screenSizeWidth);
		r.setY(0*screenSizeHeight);
		r.setWidth(800*screenSizeWidth);
		r.setHeight(900*screenSizeHeight);
		r.setFill(Color.web("eb1c24"));
		this.getChildren().add(r);
	}
}
