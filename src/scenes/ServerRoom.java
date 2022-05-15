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

public class ServerRoom extends Group{
	public TextField first;
	public TextField second;
	public TextField third;
	public TextField fourth;
	public ImageView firstReady;
	public ImageView secondReady;
	public ImageView thirdReady;
	public ImageView fourthReady;
	public Button startStopButton;
 	public Button setReadyStart;
	ImageView stopStart;
	boolean start=false;
	double screenSizeWidth=1;
	double screenSizeHeight=1;
	public MainServer server;
	public ServerRoom(){
		setSizeOfWindow();
		mainRentangle();
		server=null;
		addImageViewTitle();
		addImageViewStartGame();
		addImageViewClose();
		addImageViewOwner();
		addButtonStartGame();
		addButtonCloseServer();
		addButtonStopStart();
		System.out.println(MainServer.typeServer);
		switch (MainServer.typeServer) {
		case TWO_PLAYER:
			addImageViewFirstGuest();
			addImageViewtReadyFirst();
			addImageViewReadySecond();
			addTextFiledFirstPlayer();
			addTextFiledSecondPlayer();
			break;
		case THREE_PLAYER:
			addImageViewFirstGuest();
			addImageViewSecondGuest();
			addImageViewtReadyFirst();
			addImageViewReadySecond();
			addImageViewReadyThird();
			addTextFiledFirstPlayer();
			addTextFiledSecondPlayer();
			addTextFiledThirdPlayer();
			break;
		case FOUR_PLAYER:
			addImageViewFirstGuest();
			addImageViewSecondGuest();
			addImageViewThirdGuest();
			addImageViewtReadyFirst();
			addImageViewReadySecond();
			addImageViewReadyThird();
			addImageViewReadyFourth();
			addTextFiledFirstPlayer();
			addTextFiledSecondPlayer();
			addTextFiledThirdPlayer();
			addTextFiledFourthPlayer();
			break;
		}
	}
	
	public  ServerRoom(MainServer server){
		this.server=server;
		setSizeOfWindow();
		mainRentangle();
		addImageViewStopStart();
		addTextFiledIP();
		addImageViewTitle();
		addImageViewStartGame();
		addImageViewClose();
		addImageViewOwner();
		addButtonStartGame();
		addButtonCloseServer();
		addButtonStopStart();
		System.out.println("WorkServerRoom");
		switch (MainServer.typeServer) {
		case TWO_PLAYER:
			addImageViewFirstGuest();
			addImageViewFirstPlayer();
			addImageViewSecondPlayer();
			addImageViewtReadyFirst();
			addImageViewReadySecond();
			addTextFiledFirstPlayer();
			addTextFiledSecondPlayer();
			break;
		case THREE_PLAYER:
			addImageViewFirstGuest();
			addImageViewSecondGuest();
			addImageViewFirstPlayer();
			addImageViewSecondPlayer();
			addImageViewThirdPlayer();
			addImageViewtReadyFirst();
			addImageViewReadySecond();
			addImageViewReadyThird();
			addTextFiledFirstPlayer();
			addTextFiledSecondPlayer();
			addTextFiledThirdPlayer();
			break;
		case FOUR_PLAYER:
			addImageViewFirstGuest();
			addImageViewSecondGuest();
			addImageViewThirdGuest();
			addImageViewFirstPlayer();
			addImageViewSecondPlayer();
			addImageViewThirdPlayer();
			addImageViewFourthPlayer();
			addImageViewtReadyFirst();
			addImageViewReadySecond();
			addImageViewReadyThird();
			addImageViewReadyFourth();
			addTextFiledFirstPlayer();
			addTextFiledSecondPlayer();
			addTextFiledThirdPlayer();
			addTextFiledFourthPlayer();
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
	
	public void setFirstNickName(String nickName) {
		first.setText(nickName);
	}
	
	public void setSecondNickName(String nickName) {
		second.setText(nickName);	
	}
	
	public void setThirdNickName(String nickName) {
		third.setText(nickName);
	}
	
	public void setFourthNickName(String nickName) {
		fourth.setText(nickName);
	}
	
	void addTextFiledIP() {
		TextField v = new TextField();
		v.setPrefWidth(250*screenSizeWidth);
		v.setPrefHeight(50*screenSizeHeight);
		v.setLayoutX(515*screenSizeWidth);
		v.setLayoutY(130*screenSizeHeight);
		v.setEditable(false);
		v.setFont(new Font(20));
		v.setAlignment(Pos.CENTER);
		v.setText(server.returnIP()+" "+server.returnPort());
		this.getChildren().add(v);
	}
	
	void addButtonStopStart() {
		startStopButton = new Button();
		startStopButton.setPrefWidth(200*screenSizeWidth);
		startStopButton.setPrefHeight(100*screenSizeHeight);
		startStopButton.setLayoutX(40*screenSizeWidth);
		startStopButton.setLayoutY(105*screenSizeHeight);
		startStopButton.setOpacity(0);
		this.getChildren().add(startStopButton);
	}
	public void changeStartStopImage() {
		if(start==false) {
			stopStart.setImage(new Image("/resources/StopServer.jpg"));
			start=true;
			//server.startServer();
			//server.start.start();
		}
		else {
			stopStart.setImage(new Image("/resources/StartServer.jpg"));
			start=false;
		}
	}
	void addImageViewStopStart() {
		stopStart = new ImageView();
		stopStart.setX(40*screenSizeWidth);
		stopStart.setY(105*screenSizeHeight);
		stopStart.setFitWidth(200*screenSizeWidth);
		stopStart.setFitHeight(100*screenSizeHeight);
		stopStart.setImage(new Image("/resources/StartServer.jpg"));
		this.getChildren().add(stopStart);
	}
	void addButtonStartGame() {
		setReadyStart = new Button();
		setReadyStart.setPrefWidth(150*screenSizeWidth);
		setReadyStart.setPrefHeight(100*screenSizeHeight);
		setReadyStart.setLayoutX(610*screenSizeWidth);
		setReadyStart.setLayoutY(5*screenSizeHeight);
		setReadyStart.setOpacity(0);
		this.getChildren().add(setReadyStart);
	}
	void addButtonCloseServer() {
		Button b = new Button();
		b.setPrefWidth(150*screenSizeWidth);
		b.setPrefHeight(100*screenSizeHeight);
		b.setLayoutX(40*screenSizeWidth);
		b.setLayoutY(4*screenSizeHeight);
		b.setOpacity(0);
		this.getChildren().add(b);
	}
	void addButtonKickSecondPlayer() {
		Button b = new Button();
		b.setPrefWidth(100*screenSizeWidth);
		b.setPrefHeight(100*screenSizeHeight);
		b.setLayoutX(657*screenSizeWidth);
		b.setLayoutY(405*screenSizeHeight);
		b.setOpacity(0);
		this.getChildren().add(b);
	}
	void addButtonKickThirdPlayer() {
		Button b = new Button();
		b.setPrefWidth(100*screenSizeWidth);
		b.setPrefHeight(100*screenSizeHeight);
		b.setLayoutX(657*screenSizeWidth);
		b.setLayoutY(605*screenSizeHeight);
		b.setOpacity(0);
		this.getChildren().add(b);
	}
	void addButtonKickFourthPlayer() {
		Button b = new Button();
		b.setPrefWidth(100*screenSizeWidth);
		b.setPrefHeight(100*screenSizeHeight);
		b.setLayoutX(657*screenSizeWidth);
		b.setLayoutY(800*screenSizeHeight);
		b.setOpacity(0);
		this.getChildren().add(b);
	}
	void addTextFiledFirstPlayer() {
		first = new TextField();
		first.setPrefWidth(450*screenSizeWidth);
		first.setPrefHeight(100*screenSizeHeight);
		first.setLayoutX(50*screenSizeWidth);
		first.setLayoutY(205*screenSizeHeight);
		first.setAlignment(Pos.CENTER);
		first.setFont(new Font(25));
		first.setEditable(false);
		first.setText("Hellow");
		this.getChildren().add(first);
	}
	void addTextFiledSecondPlayer() {
		second = new TextField();
		second.setPrefWidth(450*screenSizeWidth);
		second.setPrefHeight(100*screenSizeHeight);
		second.setLayoutX(50*screenSizeWidth);
		second.setLayoutY(405*screenSizeHeight);
		second.setAlignment(Pos.CENTER);
		second.setFont(new Font(25));
		second.setEditable(false);
		this.getChildren().add(second);
	}
	void addTextFiledThirdPlayer() {
		third = new TextField();
		third.setPrefWidth(450*screenSizeWidth);
		third.setPrefHeight(100*screenSizeHeight);
		third.setLayoutX(50*screenSizeWidth);
		third.setLayoutY(605*screenSizeHeight);
		third.setAlignment(Pos.CENTER);
		third.setFont(new Font(25));
		third.setEditable(false);
		this.getChildren().add(third);
	}
	void addTextFiledFourthPlayer() {
		fourth = new TextField();
		fourth.setPrefWidth(450*screenSizeWidth);
		fourth.setPrefHeight(100*screenSizeHeight);
		fourth.setLayoutX(50*screenSizeWidth);
		fourth.setLayoutY(800*screenSizeHeight);
		fourth.setAlignment(Pos.CENTER);
		fourth.setFont(new Font(25));
		fourth.setEditable(false);
		this.getChildren().add(fourth);
	}
	public void changeReady(int i,String mess) {
		System.out.println("WORK_CHANGE_READY: "+i);
		switch (i) {
		case 2:
			//System.out.println("WORK_CHANGE_READY_2");
			if(mess.equals("READY")) {
				secondReady.setImage(new Image("/resources/green.jpg"));
			}
			else {
				secondReady.setImage(new Image("/resources/black.jpg"));
			}
			break;
		case 3:
			//System.out.println("WORK_CHANGE_READY_3: "+mess);
			if(mess.equals("READY")) {
				//System.out.println("WORK_CHANGE_READY_3_GREEN");
				thirdReady.setImage(new Image("/resources/green.jpg"));
			}
			else if(mess.equals("NOT_READY")){
				//System.out.println("WORK_CHANGE_READY_3_BLACK");
				thirdReady.setImage(new Image("/resources/black.jpg"));
			}
			break;
		case 4:
			//System.out.println("WORK_CHANGE_READY_4");
			if(mess.equals("READY")) {
				fourthReady.setImage(new Image("/resources/green.jpg"));
			}
			else {
				fourthReady.setImage(new Image("/resources/black.jpg"));
			}
			break;
		}
		System.out.println("changeReady-end");
	}
	void addImageViewtReadyFirst() {
		firstReady = new ImageView();
		firstReady.setFitWidth(90*screenSizeWidth);
		firstReady.setFitHeight(90*screenSizeHeight);
		firstReady.setX(550*screenSizeWidth);
		firstReady.setY(210*screenSizeHeight);
		firstReady.setImage(new Image("/resources/green.jpg"));
		this.getChildren().add(firstReady);
	}
	void addImageViewReadySecond() {
		secondReady = new ImageView();
		secondReady.setFitWidth(90*screenSizeWidth);
		secondReady.setFitHeight(90*screenSizeHeight);
		secondReady.setX(550*screenSizeWidth);
		secondReady.setY(410*screenSizeHeight);
		secondReady.setImage(new Image("/resources/black.jpg"));
		this.getChildren().add(secondReady);
	}
	void addImageViewReadyThird() {
		thirdReady = new ImageView();
		thirdReady.setFitWidth(90*screenSizeWidth);
		thirdReady.setFitHeight(90*screenSizeHeight);
		thirdReady.setX(550*screenSizeWidth);
		thirdReady.setY(610*screenSizeHeight);
		thirdReady.setImage(new Image("/resources/black.jpg"));
		this.getChildren().add(thirdReady);
	}
 	void addImageViewReadyFourth() {
 		fourthReady = new ImageView();
 		fourthReady.setFitWidth(90*screenSizeWidth);
 		fourthReady.setFitHeight(90*screenSizeHeight);
 		fourthReady.setX(550*screenSizeWidth);
 		fourthReady.setY(805*screenSizeHeight);
 		fourthReady.setImage(new Image("/resources/black.jpg"));
		this.getChildren().add(fourthReady);
 	}
	void addImageViewFirstPlayer() {
		ImageView v = new ImageView();
		v.setFitWidth(100*screenSizeWidth);
		v.setFitHeight(100*screenSizeHeight);
		v.setX(650*screenSizeWidth);
		v.setY(205*screenSizeHeight);
		v.setImage(new Image("/resources/Player.jpg"));
		this.getChildren().add(v);
	}
	void addImageViewSecondPlayer() {
		ImageView v = new ImageView();
		v.setFitWidth(100*screenSizeWidth);
		v.setFitHeight(100*screenSizeHeight);
		v.setX(650*screenSizeWidth);
		v.setY(405*screenSizeHeight);
		v.setImage(new Image("/resources/Player.jpg"));
		this.getChildren().add(v);
	}
	void addImageViewThirdPlayer() {
		ImageView v = new ImageView();
		v.setFitWidth(100*screenSizeWidth);
		v.setFitHeight(100*screenSizeHeight);
		v.setX(650*screenSizeWidth);
		v.setY(605*screenSizeHeight);
		v.setImage(new Image("/resources/Player.jpg"));
		this.getChildren().add(v);
	}
	void addImageViewFourthPlayer() {
		ImageView v = new ImageView();
		v.setFitWidth(100*screenSizeWidth);
		v.setFitHeight(100*screenSizeHeight);
		v.setX(650*screenSizeWidth);
		v.setY(800*screenSizeHeight);
		v.setImage(new Image("/resources/Player.jpg"));
		this.getChildren().add(v);
	}
	void addImageViewThirdGuest() {
		ImageView v = new ImageView();
		v.setFitWidth(400*screenSizeWidth);
		v.setFitHeight(100*screenSizeHeight);
		v.setX(200*screenSizeWidth);
		v.setY(705*screenSizeHeight);
		v.setImage(new Image("/resources/Third.jpg"));
		this.getChildren().add(v);
	}
	void addImageViewSecondGuest() {
		ImageView v = new ImageView();
		v.setFitWidth(400*screenSizeWidth);
		v.setFitHeight(100*screenSizeHeight);
		v.setX(200*screenSizeWidth);
		v.setY(505*screenSizeHeight);
		v.setImage(new Image("/resources/SecondGuest.jpg"));
		this.getChildren().add(v);
	}
	void addImageViewFirstGuest() {
		ImageView v = new ImageView();
		v.setFitWidth(400*screenSizeWidth);
		v.setFitHeight(100*screenSizeHeight);
		v.setX(200*screenSizeWidth);
		v.setY(305*screenSizeHeight);
		v.setImage(new Image("/resources/FirstGuest.jpg"));
		this.getChildren().add(v);
	}
	void addImageViewOwner() {
		ImageView v = new ImageView();
		v.setFitWidth(200*screenSizeWidth);
		v.setFitHeight(100*screenSizeHeight);
		v.setX(300*screenSizeWidth);
		v.setY(105*screenSizeHeight);
		v.setImage(new Image("/resources/ServerCreator.jpg"));
		this.getChildren().add(v);
	}
	void addImageViewClose() {
		ImageView v = new ImageView();
		v.setFitWidth(150*screenSizeWidth);
		v.setFitHeight(100*screenSizeHeight);
		v.setX(40*screenSizeWidth);
		v.setY(5*screenSizeHeight);
		v.setImage(new Image("/resources/CloseServer.jpg"));
		this.getChildren().add(v);
	}
	void addImageViewStartGame() {
		ImageView v = new ImageView();
		v.setFitWidth(150*screenSizeWidth);
		v.setFitHeight(100*screenSizeHeight);
		v.setX(610*screenSizeWidth);
		v.setY(5*screenSizeHeight);
		if(server!=null) {
			v.setImage(new Image("/resources/StartGame.jpg"));
		}
		else {
			v.setImage(new Image("/resources/SetReady.jpg"));	
		}
		this.getChildren().add(v);
	}
	void addImageViewTitle() {
		ImageView v = new ImageView();
		v.setFitWidth(400*screenSizeWidth);
		v.setFitHeight(100*screenSizeHeight);
		v.setX(200*screenSizeWidth);
		v.setY(5*screenSizeHeight);
		v.setImage(new Image("/resources/Waitingroom.jpg"));
		this.getChildren().add(v);
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
}
