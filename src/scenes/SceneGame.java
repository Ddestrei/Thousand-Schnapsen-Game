package scenes;

import java.util.ArrayList;

import client.MainClient;
import engine.Cards;
import enums.TypeClient;
import enums.TypeServer;
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

public class SceneGame extends Group{
	
	public TextField firstPlayerName;
	public TextField secondPlayerName;
	public TextField thirdPlayerName;
	public TextField firstPlayerPoints;
	public TextField secondPlayerPoints;
	public TextField thirdPlayerPoints;
	public TextField levelOfPointsMin;
	public TextField levelOfPointsInOneDeal;
	public TextField levelOfPointsPlayer;
	public TextField playerDeling;
	public TextField typeDispatch;
	
	public int levelOfPointNumber=100;
	public Button plusFiveButton;
	public Button passButton;
	public Button borderButton;
	
	ImageView plusFiveImageView;
	ImageView passImageView;
	
	public ArrayList<ImageView> playerCardsImage = new ArrayList<>();
	public ArrayList<Button> playerCardsButtons = new ArrayList<>();
	public ArrayList<Cards> playerCards = new ArrayList<>();
	
	public ArrayList<ImageView> borderCards = new ArrayList<>();
	public ArrayList<Cards> borderCardsCards = new ArrayList<>();
	
	public double screenSizeWidth=1;
	public double screenSizeHeight=1;
	
	public SceneGame(TypeClient typeClient){
		setSizeOfWindow();
		mainRentangle();
		addRentanglePlyerCards();
		addRentangleBoard();
		addImageViewCards();
		addPassImageView();
		addPlusFiveImageView();
		addPlusFiveButton();
		addpassButton();
		addTextFiledlevelOfPoints();
		addTextFiledPlayerDeling();
		addBorderImageView();
		addBorderButton();
		addTextFiledLevelOfPointsInOneDeal();
		addTextFliedLevelOfPointsPlayer();
		addTextFliedTypeDispatch();
		switch(MainServer.typeServer) {
		case TWO_PLAYER:
			addTextFiledFirstPlayerName();
			addTextFiledFirstPlayerPoints();
			break;
		case THREE_PLAYER:
			addTextFiledFirstPlayerName();
			addTextFiledFirstPlayerPoints();
			addTextFiledSecondPlayerName();
			addTextFiledSecondPlayerPoints();
			break;
		case FOUR_PLAYER:
			addTextFiledFirstPlayerName();
			addTextFiledFirstPlayerPoints();
			addTextFiledSecondPlayerName();
			addTextFiledSecondPlayerPoints();
			addTextFiledThirdPlayerName();
			addTextFiledThirdPlayerPoints();
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
	
	void addTextFliedTypeDispatch() {
		typeDispatch= new TextField();
		typeDispatch.setPrefWidth(220*screenSizeWidth);
		typeDispatch.setPrefHeight(50*screenSizeHeight);
		typeDispatch.setLayoutX(290*screenSizeWidth);
		typeDispatch.setLayoutY(300*screenSizeHeight);
		typeDispatch.setAlignment(Pos.CENTER);
		typeDispatch.setEditable(false);
		typeDispatch.setFont(new Font(20));
		typeDispatch.setText("");
		this.getChildren().add(typeDispatch);
	}
	
	void addTextFliedLevelOfPointsPlayer() {
		levelOfPointsPlayer= new TextField();
		levelOfPointsPlayer.setPrefWidth(200*screenSizeWidth);
		levelOfPointsPlayer.setPrefHeight(50*screenSizeHeight);
		levelOfPointsPlayer.setLayoutX(10*screenSizeWidth);
		levelOfPointsPlayer.setLayoutY(130*screenSizeHeight);
		levelOfPointsPlayer.setAlignment(Pos.CENTER);
		levelOfPointsPlayer.setEditable(false);
		levelOfPointsPlayer.setFont(new Font(20));
		levelOfPointsPlayer.setText("0");
		this.getChildren().add(levelOfPointsPlayer);
	}
	
	void addTextFiledLevelOfPointsInOneDeal() {
		levelOfPointsInOneDeal= new TextField();
		levelOfPointsInOneDeal.setPrefWidth(200*screenSizeWidth);
		levelOfPointsInOneDeal.setPrefHeight(50*screenSizeHeight);
		levelOfPointsInOneDeal.setLayoutX(10*screenSizeWidth);
		levelOfPointsInOneDeal.setLayoutY(70*screenSizeHeight);
		levelOfPointsInOneDeal.setAlignment(Pos.CENTER);
		levelOfPointsInOneDeal.setEditable(false);
		levelOfPointsInOneDeal.setFont(new Font(20));
		levelOfPointsInOneDeal.setText("0");
		this.getChildren().add(levelOfPointsInOneDeal);
	}
	
	void addBorderButton() {
		borderButton = new Button();
		borderButton.setPrefWidth(220*screenSizeWidth);
		borderButton.setPrefHeight(180*screenSizeHeight);
		borderButton.setLayoutX(290*screenSizeWidth);
		borderButton.setLayoutY(360*screenSizeHeight);
		borderButton.setOpacity(0);
		this.getChildren().add(borderButton);
	}
	
	void addBorderImageView() {
		ImageView e = new ImageView();
		e.setFitWidth(70*screenSizeWidth);
		e.setFitHeight(90*screenSizeHeight);
		e.setX(290*screenSizeWidth);
		e.setY(360*screenSizeHeight);
		borderCards.add(e);
		this.getChildren().add(e);
		ImageView r = new ImageView();
		r.setFitWidth(70*screenSizeWidth);
		r.setFitHeight(90*screenSizeHeight);
		r.setX(365*screenSizeWidth);
		r.setY(405*screenSizeHeight);
		borderCards.add(r);
		this.getChildren().add(r);
		ImageView t = new ImageView();
		t.setFitWidth(70*screenSizeWidth);
		t.setFitHeight(90*screenSizeHeight);
		t.setX(440*screenSizeWidth);
		t.setY(450*screenSizeHeight);
		borderCards.add(t);
		this.getChildren().add(t);
	}
	
	void addTextFiledPlayerDeling() {
		playerDeling= new TextField();
		playerDeling.setPrefWidth(200*screenSizeWidth);
		playerDeling.setPrefHeight(50*screenSizeHeight);
		playerDeling.setLayoutX(590*screenSizeWidth);
		playerDeling.setLayoutY(10*screenSizeHeight);
		playerDeling.setAlignment(Pos.CENTER);
		playerDeling.setEditable(false);
		playerDeling.setFont(new Font(20));
		playerDeling.setText("100");
		this.getChildren().add(playerDeling);
	}
	
	public void addBoard(Cards card) {
		System.out.println("add-card-to-board: "+card.sygnature);
		if(borderCards.get(0).getImage()==null) {
			borderCards.get(0).setImage(new Image(card.pathToImage));
			borderCardsCards.add(card);
		}
		else if(borderCards.get(1).getImage()==null) {
			borderCards.get(1).setImage(new Image(card.pathToImage));
			borderCardsCards.add(card);
		}
		else if(borderCards.get(2).getImage()==null) {
			borderCards.get(2).setImage(new Image(card.pathToImage));
			borderCardsCards.add(card);
		}
	}
	
	public void addCard(Cards card) {
		playerCards.add(card);
		int i=0;
		while (i<playerCards.size()) {
			if(playerCardsImage.get(i).getImage()==null) {
				playerCardsImage.get(i).setImage(new Image(card.pathToImage));
			}
			i+=1;
		}
	}
	
	public void setCards(ArrayList<Cards>images) {
		System.out.println("SET_CARDS_WORK");
		System.out.println(images.size());
		for (int i=0;i<images.size();i++) {
			System.out.println(images.get(i)+" :"+i);
			playerCardsImage.get(i).setImage(new Image(images.get(i).pathToImage));
			playerCards.add(images.get(i));
		}
		
	}
	
	public void setButtonsAndImages() {
		plusFiveButton.setVisible(true);
		passButton.setVisible(true);
		plusFiveImageView.setVisible(true);
		passImageView.setVisible(true);
	}
	
	public void setPass() {
		 plusFiveButton.setVisible(false);
		 passButton.setVisible(false);
		 plusFiveImageView.setVisible(false);
		 passImageView.setVisible(false);
	}
	
	void addTextFiledlevelOfPoints() {
		levelOfPointsMin= new TextField();
		levelOfPointsMin.setPrefWidth(200*screenSizeWidth);
		levelOfPointsMin.setPrefHeight(50*screenSizeHeight);
		levelOfPointsMin.setLayoutX(10*screenSizeWidth);
		levelOfPointsMin.setLayoutY(10*screenSizeHeight);
		levelOfPointsMin.setAlignment(Pos.CENTER);
		levelOfPointsMin.setEditable(false);
		levelOfPointsMin.setFont(new Font(20));
		levelOfPointsMin.setText("100");
		this.getChildren().add(levelOfPointsMin);
	}
	
	void addPassImageView() {
		passImageView = new ImageView();
		passImageView.setFitWidth(100*screenSizeWidth);
		passImageView.setFitHeight(100*screenSizeHeight);
		passImageView.setX(405*screenSizeWidth);
		passImageView.setY(550*screenSizeHeight);
		passImageView.setImage(new Image("/resources/Pass.jpg"));
		this.getChildren().add(passImageView);
	}
	
	void addPlusFiveImageView() {
		plusFiveImageView = new ImageView();
		plusFiveImageView.setFitWidth(100*screenSizeWidth);
		plusFiveImageView.setFitHeight(100*screenSizeHeight);
		plusFiveImageView.setX(295*screenSizeWidth);
		plusFiveImageView.setY(550*screenSizeHeight);
		plusFiveImageView.setImage(new Image("/resources/Plus5.jpg"));
		this.getChildren().add(plusFiveImageView);
	}
	
	void addPlusFiveButton() {
		plusFiveButton = new Button();
		plusFiveButton.setPrefHeight(100*screenSizeHeight);
		plusFiveButton.setPrefWidth(100*screenSizeWidth);
		plusFiveButton.setLayoutX(295*screenSizeWidth);
		plusFiveButton.setLayoutY(550*screenSizeHeight);
		plusFiveButton.setOpacity(0);
		this.getChildren().add(plusFiveButton);
	}
	
	void addpassButton() {
		passButton = new Button();
		passButton.setPrefHeight(100*screenSizeHeight);
		passButton.setPrefWidth(100*screenSizeWidth);
		passButton.setLayoutX(405*screenSizeWidth);
		passButton.setLayoutY(550*screenSizeHeight);
		passButton.setOpacity(0);
		this.getChildren().add(passButton);
	}
	
	void addImageViewCards() {
		//System.out.println("addImageViewCards SceneGame");
		if(MainServer.typeServer==TypeServer.TWO_PLAYER) {
			for (int i=0;i<12;i++) {
				addImageViewOneCard(i*66,755);
				addButtonOneCard(i*66,755);
			}
		}
		else {
			for (int i=0;i<10;i++) {
				addImageViewOneCard(i*75,755);
				addButtonOneCard(i*75,755);
			}
		}
	}
	
 	public void addMusik(Cards first,Cards second,Cards third) {
 		//System.out.println("SceneGame-addMusik-work");
 		playerCardsImage.get(7).setImage(new Image(first.pathToImage));
 		playerCards.add(first);
 		playerCardsImage.get(8).setImage(new Image(second.pathToImage));
 		playerCards.add(second);
 		playerCardsImage.get(9).setImage(new Image(third.pathToImage));
 		playerCards.add(third);
	}
	
	public void borderSetNull() {
		borderCards.get(0).setImage(null);
		borderCards.get(1).setImage(null);
		borderCards.get(2).setImage(null);
		borderCardsCards.clear();
	}
	
	public void setBorderCard(Cards s) {
		System.out.println("BOrderCard+"+s.sygnature);
		System.out.println(borderCardsCards.size()+"size of borderCardsCards");
		if(borderCardsCards.size()>2) borderCardsCards.clear();
		if(borderCards.get(0).getImage()==null) {
			borderCards.get(0).setImage(new Image(s.pathToImage));
			borderCardsCards.add(s);
		}
		else if(borderCards.get(1).getImage()==null) {
			borderCards.get(1).setImage(new Image(s.pathToImage));
			borderCardsCards.add(s);
		}
		else if(borderCards.get(2).getImage()==null) {
			borderCards.get(2).setImage(new Image(s.pathToImage));
			borderCardsCards.add(s);
		}
	}
	
	void addButtonOneCard(int x,int y) {
		Button b = new Button();
		b.setPrefWidth(70*screenSizeWidth);
		b.setPrefHeight(90*screenSizeHeight);
		b.setLayoutX(x*screenSizeWidth);
		b.setLayoutY(y*screenSizeHeight);
		b.setOpacity(0);
		playerCardsButtons.add(b);
		this.getChildren().add(b);
	}
	
	void addImageViewOneCard(int x,int y) {
		ImageView e = new ImageView();
		e.setFitWidth(70*screenSizeWidth);
		e.setFitHeight(90*screenSizeHeight);
		e.setX(x*screenSizeWidth);
		e.setY(y*screenSizeHeight);
		e.setOpacity(0.75);
		playerCardsImage.add(e);
		this.getChildren().add(e);
	}
	
	void addTextFiledFirstPlayerName() {
		firstPlayerName= new TextField();
		firstPlayerName.setPrefWidth(200*screenSizeWidth);
		firstPlayerName.setPrefHeight(50*screenSizeHeight);
		firstPlayerName.setLayoutX(300*screenSizeWidth);
		firstPlayerName.setLayoutY(10*screenSizeHeight);
		firstPlayerName.setAlignment(Pos.CENTER);
		firstPlayerName.setEditable(false);
		firstPlayerName.setFont(new Font(20));
		this.getChildren().add(firstPlayerName);
	}
	void addTextFiledFirstPlayerPoints() {
		firstPlayerPoints= new TextField();
		firstPlayerPoints.setPrefWidth(200*screenSizeWidth);
		firstPlayerPoints.setPrefHeight(50*screenSizeHeight);
		firstPlayerPoints.setLayoutX(300*screenSizeWidth);
		firstPlayerPoints.setLayoutY(110*screenSizeHeight);
		firstPlayerPoints.setAlignment(Pos.CENTER);
		firstPlayerPoints.setEditable(false);
		firstPlayerPoints.setFont(new Font(20));
		this.getChildren().add(firstPlayerPoints);
	}
	void addTextFiledSecondPlayerName() {
		secondPlayerName= new TextField();
		secondPlayerName.setPrefWidth(200*screenSizeWidth);
		secondPlayerName.setPrefHeight(50*screenSizeHeight);
		secondPlayerName.setLayoutX(590*screenSizeWidth);
		secondPlayerName.setLayoutY(325*screenSizeHeight);
		secondPlayerName.setAlignment(Pos.CENTER);
		secondPlayerName.setEditable(false);
		secondPlayerName.setFont(new Font(20));
		this.getChildren().add(secondPlayerName);
	}
	void addTextFiledSecondPlayerPoints() {
		secondPlayerPoints= new TextField();
		secondPlayerPoints.setPrefWidth(200*screenSizeWidth);
		secondPlayerPoints.setPrefHeight(50*screenSizeHeight);
		secondPlayerPoints.setLayoutX(590*screenSizeWidth);
		secondPlayerPoints.setLayoutY(425*screenSizeHeight);
		secondPlayerPoints.setAlignment(Pos.CENTER);
		secondPlayerPoints.setEditable(false);
		secondPlayerPoints.setFont(new Font(20));
		this.getChildren().add(secondPlayerPoints);
	}
	void addTextFiledThirdPlayerName() {
		thirdPlayerName= new TextField();
		thirdPlayerName.setPrefWidth(200*screenSizeWidth);
		thirdPlayerName.setPrefHeight(50*screenSizeHeight);
		thirdPlayerName.setLayoutX(10*screenSizeWidth);
		thirdPlayerName.setLayoutY(325*screenSizeHeight);
		thirdPlayerName.setAlignment(Pos.CENTER);
		thirdPlayerName.setEditable(false);
		thirdPlayerName.setFont(new Font(20));
		this.getChildren().add(thirdPlayerName);
	}
	void addTextFiledThirdPlayerPoints() {
		thirdPlayerPoints= new TextField();
		thirdPlayerPoints.setPrefWidth(200*screenSizeWidth);
		thirdPlayerPoints.setPrefHeight(50*screenSizeHeight);
		thirdPlayerPoints.setLayoutX(10*screenSizeWidth);
		thirdPlayerPoints.setLayoutY(425*screenSizeHeight);
		thirdPlayerPoints.setAlignment(Pos.CENTER);
		thirdPlayerPoints.setEditable(false);
		thirdPlayerPoints.setFont(new Font(20));
		this.getChildren().add(thirdPlayerPoints);
	}
	void addRentangleBoard() {
		Rectangle r = new Rectangle();
		r.setX(290*screenSizeWidth);
		r.setY(360*screenSizeHeight);
		r.setWidth(220*screenSizeWidth);
		r.setHeight(180*screenSizeHeight);
		Color c = Color.web("000000");
		r.setFill(c);
		this.getChildren().add(r);
	}
	void addRentanglePlyerCards() {
		Rectangle r = new Rectangle();
		r.setX(0*screenSizeWidth);
		r.setY(750*screenSizeHeight);
		r.setWidth(800*screenSizeWidth);
		r.setHeight(100*screenSizeHeight);
		Color c = Color.web("39ff1f");
		r.setFill(c);
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
}
