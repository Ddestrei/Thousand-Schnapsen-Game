package scenes;

import enums.TypeServer;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import server.Game;
import server.MainServer;

public class CreateServer extends Group{
	ImageView two;
	ImageView three;
	ImageView four;
	public Button butCreate;
	Stage stage;
	Scene scene;
	double screenSizeWidth=1;
	double screenSizeHeight=1;
	public CreateServer(Stage stage){
		this.stage=stage; 
		//this.scene= new Scene(null);
		setSizeOfWindow();
		mainRentangle();
		addImageViewTowPlayer();
		addImageViewThreePlayer();
		addImageViewFourPlayer();
		addImageViewTitle();
		addImageViewCreate();
		addButtonTwo();
		addButtonThree();
		addButtonFour();
		addButtonCreate();
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
	void addButtonTwo() {
		Button but = new Button();
		but.setPrefWidth(200*screenSizeWidth);
		but.setPrefHeight(200*screenSizeHeight);
		but.setLayoutX(50*screenSizeWidth);
		but.setLayoutY(200*screenSizeHeight);
		but.setOpacity(0);
		but.addEventHandler(ActionEvent.ACTION, e->{
			if(MainServer.typeServer==TypeServer.THREE_PLAYER) {
				three.setImage(new Image("/resources/ThreePlayers.jpg"));
			}
			else if(MainServer.typeServer==TypeServer.FOUR_PLAYER) {
				four.setImage(new Image("/resources/FourPlayers.jpg"));
			}
			two.setImage(new Image("/resources/TwoPlayerChoosen.jpg"));
			MainServer.typeServer=TypeServer.TWO_PLAYER;
		});
		this.getChildren().add(but);
	}
	void addButtonThree() {
		Button but = new Button();
		but.setPrefWidth(200*screenSizeWidth);
		but.setPrefHeight(200*screenSizeHeight);
		but.setLayoutX(300*screenSizeWidth);
		but.setLayoutY(200*screenSizeHeight);
		but.setOpacity(0);
		but.addEventHandler(ActionEvent.ACTION, e->{
			if(MainServer.typeServer==TypeServer.TWO_PLAYER) {
				two.setImage(new Image("/resources/TwoPlayer.jpg"));
			}
			else if(MainServer.typeServer==TypeServer.FOUR_PLAYER) {
				four.setImage(new Image("/resources/FourPlayers.jpg"));
			}
			three.setImage(new Image("/resources/ThreePlayersChoosen.jpg"));
			MainServer.typeServer=TypeServer.THREE_PLAYER;
		});
		this.getChildren().add(but);
	}
	void addButtonFour() {
		Button but = new Button();
		but.setPrefWidth(200*screenSizeWidth);
		but.setPrefHeight(200*screenSizeHeight);
		but.setLayoutX(550*screenSizeWidth);
		but.setLayoutY(200*screenSizeHeight);
		but.setOpacity(0);
		but.addEventHandler(ActionEvent.ACTION, e->{
			if(MainServer.typeServer==TypeServer.TWO_PLAYER) {
				two.setImage(new Image("/resources/TwoPlayer.jpg"));
			}
			else if(MainServer.typeServer==TypeServer.THREE_PLAYER) {
				three.setImage(new Image("/resources/ThreePlayers.jpg"));
			}
			four.setImage(new Image("/resources/FourPlayersChoosen.jpg"));
			MainServer.typeServer=TypeServer.FOUR_PLAYER;
		});
		this.getChildren().add(but);
	}
	void addButtonCreate() {
		butCreate = new Button();
		butCreate.setPrefWidth(400*screenSizeWidth);
		butCreate.setPrefHeight(100*screenSizeHeight);
		butCreate.setLayoutX(200*screenSizeWidth);
		butCreate.setLayoutY(500*screenSizeHeight);
		butCreate.setOpacity(0);
		butCreate.addEventHandler(ActionEvent.ACTION, e->{
			Game game = new Game();
			MainServer server = new MainServer(game,stage);
			ClientConnectToServer client = new ClientConnectToServer(stage,true,server);
			stage.setScene(new Scene(client));
			stage.show();
			//System.out.println("Server-Start");
			//server.server.startServer();
		});
		this.getChildren().add(butCreate);
	}
	void addImageViewTitle() {
		ImageView r = new ImageView();
		r.setX(200*screenSizeWidth);
		r.setY(50*screenSizeHeight);
		r.prefWidth(800*screenSizeWidth);
		r.prefHeight(900*screenSizeHeight);
		r.setImage(new Image("/resources/ChooseType.jpg"));
		this.getChildren().add(r);
	}
	void addImageViewCreate() {
		ImageView r = new ImageView();
		r.setX(200*screenSizeWidth);
		r.setY(500*screenSizeHeight);
		r.prefWidth(800*screenSizeWidth);
		r.prefHeight(900*screenSizeHeight);
		r.setImage(new Image("/resources/CreateServerButton.jpg"));
		this.getChildren().add(r);
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
	void addImageViewTowPlayer() {
		two = new ImageView();
		two.prefWidth(200*screenSizeWidth);
		two.prefHeight(200*screenSizeHeight);
		two.setX(50*screenSizeWidth);
		two.setY(200*screenSizeHeight);
		if(MainServer.typeServer==TypeServer.TWO_PLAYER) {
			two.setImage(new Image("/resources/TwoPlayerChoosen.jpg"));
		}else {
			two.setImage(new Image("/resources/TwoPlayer.jpg"));
		}
		this.getChildren().add(two);
	}
	void addImageViewThreePlayer() {
		three = new ImageView();
		three.prefWidth(200*screenSizeWidth);
		three.prefHeight(200*screenSizeHeight);
		three.setX(300*screenSizeWidth);
		three.setY(200*screenSizeHeight);
		if(MainServer.typeServer==TypeServer.THREE_PLAYER) {
			three.setImage(new Image("/resources/ThreePlayersChoosen.jpg"));
		}
		else {
			three.setImage(new Image("/resources/ThreePlayers.jpg"));
		}
		this.getChildren().add(three);
	}
	void addImageViewFourPlayer() {
		four = new ImageView();
		four.prefWidth(200*screenSizeWidth);
		four.prefHeight(200*screenSizeHeight);
		four.setX(550*screenSizeWidth);
		four.setY(200*screenSizeHeight);
		if(MainServer.typeServer==TypeServer.FOUR_PLAYER) {
			four.setImage(new Image("/resources/FourPlayersChoosen.jpg"));
		}
		else {
			four.setImage(new Image("/resources/FourPlayers.jpg"));
		} 
		this.getChildren().add(four);
	}
}
