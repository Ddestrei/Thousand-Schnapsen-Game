package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import engine.Cards;
import enums.Pairs;
import enums.TypeClient;
import enums.TypePlayer;
import enums.TypeServer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import scenes.LastScene;
import scenes.SceneGame;
import scenes.ServerRoom;
import server.Game;
import server.MainServer;

public class MainClient {
	ArrayList<Friend> friends= new ArrayList<>();
	Socket socket;
	BufferedReader bufferedReader;
	BufferedWriter bufferedWriter;
	String userName;
	String code;
	ArrayList<Cards> playerDeck = new ArrayList<>();
	Cards firstCard; 
	int cardsOnBoard=0;
	TypePlayer typePlayer;
	TypeClient typeClient=TypeClient.SERVER_CLIENT;
	Pairs dispatch=Pairs.NULL_DISPATCH;
	ServerRoom serverRoom;
	boolean isReady=false;
	boolean isPass=false;
	boolean startAuction=false;
	boolean leadingGame=false;
	boolean youGiveCard=false;
	boolean messegeWasRead=false;
	ReadDataClientServer readServer;
	ReadDataClient readClient;
	Game game;
	SceneGame scene;
	Stage stage;
	Cards cardToGive;
	String dealingPlayer;
	int pointsInOneDeal=0;
	int pointInGame=0;
	int givenCards=0;
	int endedRounds=0;
	CheckWorking checkWorking;
	// for serverClient
	public MainClient(String name,String port,Game game,Stage stage) {
		this.stage= stage;
		this.game= game;
		this.userName=name;
		typeClient= TypeClient.SERVER_CLIENT;
		try {
			this.socket=new Socket("localhost",Integer.parseInt(port));
			if(socket.isConnected()) {
				//////System.out.println("Client connected with serverloacl");
			}
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			sendName();
			readServer= new ReadDataClientServer();
			readServer.start();
			//code=name+String.valueOf(1000);
			checkWorking = new CheckWorking();
			checkWorking.start();
			//////System.out.println("CODE_CLIENT-"+code);
		} catch (IOException e) {
			System.out.println("Conection lose!!!-MainClient+first");
			System.out.println("END_WORK_SERVER");
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					System.exit(0);
				}
			});
			e.printStackTrace();
		}
	}
	//for client
	public MainClient(String name,String IP,String port,Stage stage){
		this.stage= stage;
		this.userName=name;
		typeClient= TypeClient.CASUAL_CLIENT;
		try {
			this.socket=new Socket(IP,Integer.parseInt(port));
			if(socket.isConnected()) {
				//////System.out.println("Client connected");
			}
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			sendName();
			readClient= new ReadDataClient();
			readClient.start();
			checkWorking = new CheckWorking();
			checkWorking.start();
			//code=userName+String.valueOf((friends.size()+1)*1000);
			System.out.println("CODE_CLIENT-"+code+"_____________________________________");
		} catch (IOException e) {
			System.out.println("Conection lose!!!-MainClient+second");
			System.out.println("END_WORK_SERVER");
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					System.exit(0);
				}
			});
			e.printStackTrace();
		}
	}
	
	void sendMessege(String mess) {
		try {
			bufferedWriter.write(mess);
			bufferedWriter.newLine();
			bufferedWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	ArrayList<Integer> checkCards() {
		System.out.println("checkCards+WORK");
		ArrayList<Integer> pos=new ArrayList<>();
		switch(dispatch) {
		case NULL_DISPATCH:
			System.out.println("checkCards_NULL_DIS_FIRST");
			for (int i=0;i<playerDeck.size();i++) {
				if(playerDeck.get(i).color.equals(firstCard.color)) {
					if(playerDeck.get(i).power>firstCard.power) {
						if(!playerDeck.get(i).wasUsed) {
							pos.add(i);
						}
					}
				}
			}
			if(pos.size()==0) {
				System.out.println("checkCards_NULL_DIS_SECOND");
				for (int i=0;i<playerDeck.size();i++) {
					if(playerDeck.get(i).color.equals(firstCard.color)) {
						if(!playerDeck.get(i).wasUsed) {
							pos.add(i);
						}
					}
				}
			}
			return pos;
		case PIK_DISPATCH:
			System.out.println("checkCards_PIK_DIS_FIRST");
			if(firstCard.color.equals("pik")) {
				for (int i=0;i<playerDeck.size();i++) {
					if(playerDeck.get(i).color.equals("pik")) {
						if(playerDeck.get(i).power>firstCard.power) {
							if(!playerDeck.get(i).wasUsed) {
								pos.add(i);
							}
						}
					}
				}
				if(pos.size()==0) {
					for (int i=0;i<playerDeck.size();i++) {
						if(playerDeck.get(i).color.equals("pik")) {
							if(!playerDeck.get(i).wasUsed) {
								pos.add(i);
							}
						}
					}
				}
				if(pos.size()==0) {
					for (int i=0;i<playerDeck.size();i++) {
						if(!playerDeck.get(i).wasUsed) {
							pos.add(i);
						}
					}
				}
			}
			else {
				for (int i=0;i<playerDeck.size();i++) {
					if(playerDeck.get(i).color.equals(firstCard.color)) {
						if(playerDeck.get(i).power>firstCard.power) {
							if(!playerDeck.get(i).wasUsed) {
								pos.add(i);
							}
						}
					}
				}
				if(pos.size()==0) {
					for (int i=0;i<playerDeck.size();i++) {
						if(playerDeck.get(i).color.equals(firstCard.color)) {
							if(!playerDeck.get(i).wasUsed) {
								pos.add(i);
							}
						}
					}
				}
				if(pos.size()==0) {
					for (int i=0;i<playerDeck.size();i++) {
						if(playerDeck.get(i).color.equals("pik")) {
							if(!playerDeck.get(i).wasUsed) {
								pos.add(i);
							}
						}
					}
				}
			}
			if(cardsOnBoard==2) {
				for (int i=0;i<playerDeck.size();i++) {
					if(playerDeck.get(i).color.equals(firstCard.color)) {
						if(!playerDeck.get(i).wasUsed) {
							pos.add(i);
						}
					}
				}
				if(pos.size()==0) {
					for (int i=0;i<playerDeck.size();i++) {
						if(playerDeck.get(i).color.equals("pik")) {
							if(!playerDeck.get(i).wasUsed) {
								pos.add(i);
							}
						}
					}
				}
			}
			return pos;
		case TREFL_DISPATCH:
			System.out.println("checkCards_TREFL_DIS_FIRST");
			if(firstCard.color.equals("trefl")) {
				for (int i=0;i<playerDeck.size();i++) {
					if(playerDeck.get(i).color.equals("trefl")) {
						if(playerDeck.get(i).power>firstCard.power) {
							if(!playerDeck.get(i).wasUsed) {
								pos.add(i);
							}
						}
					}
				}
				if(pos.size()==0) {
					for (int i=0;i<playerDeck.size();i++) {
						if(playerDeck.get(i).color.equals("trefl")) {
							if(!playerDeck.get(i).wasUsed) {
								pos.add(i);
							}
						}
					}
				}
				if(pos.size()==0) {
					for (int i=0;i<playerDeck.size();i++) {
						if(!playerDeck.get(i).wasUsed) {
							pos.add(i);
						}
					}
				}
			}
			else {
				for (int i=0;i<playerDeck.size();i++) {
					if(playerDeck.get(i).color.equals(firstCard.color)) {
						if(playerDeck.get(i).power>firstCard.power) {
							if(!playerDeck.get(i).wasUsed) {
								pos.add(i);
							}
						}
					}
				}
				if(pos.size()==0) {
					for (int i=0;i<playerDeck.size();i++) {
						if(playerDeck.get(i).color.equals(firstCard.color)) {
							if(!playerDeck.get(i).wasUsed) {
								pos.add(i);
							}
						}
					}
				}
				if(pos.size()==0) {
					for (int i=0;i<playerDeck.size();i++) {
						if(playerDeck.get(i).color.equals("trefl")) {
							if(!playerDeck.get(i).wasUsed) {
								pos.add(i);
							}
						}
					}
				}
			}
			if(cardsOnBoard==2) {
				for (int i=0;i<playerDeck.size();i++) {
					if(playerDeck.get(i).color.equals(firstCard.color)) {
						if(!playerDeck.get(i).wasUsed) {
							pos.add(i);
						}
					}
				}
				if(pos.size()==0) {
					for (int i=0;i<playerDeck.size();i++) {
						if(playerDeck.get(i).color.equals("trefl")) {
							if(!playerDeck.get(i).wasUsed) {
								pos.add(i);
							}
						}
					}
				}
			}
			return pos;
		case CARO_DISPATCH:
			System.out.println("checkCards_CARO_DIS_FIRST");
			if(firstCard.color.equals("caro")) {
				for (int i=0;i<playerDeck.size();i++) {
					if(playerDeck.get(i).color.equals("caro")) {
						if(playerDeck.get(i).power>firstCard.power) {
							if(!playerDeck.get(i).wasUsed) {
								pos.add(i);
							}
						}
					}
				}
				if(pos.size()==0) {
					for (int i=0;i<playerDeck.size();i++) {
						if(playerDeck.get(i).color.equals("caro")) {
							if(!playerDeck.get(i).wasUsed) {
								pos.add(i);
							}
						}
					}
				}
				if(pos.size()==0) {
					for (int i=0;i<playerDeck.size();i++) {
						if(!playerDeck.get(i).wasUsed) {
							pos.add(i);
						}
					}
				}
			}
			else {
				for (int i=0;i<playerDeck.size();i++) {
					if(playerDeck.get(i).color.equals(firstCard.color)) {
						if(playerDeck.get(i).power>firstCard.power) {
							if(!playerDeck.get(i).wasUsed) {
								pos.add(i);
							}
						}
					}
				}
				if(pos.size()==0) {
					for (int i=0;i<playerDeck.size();i++) {
						if(playerDeck.get(i).color.equals(firstCard.color)) {
							if(!playerDeck.get(i).wasUsed) {
								pos.add(i);
							}
						}
					}
				}
				if(pos.size()==0) {
					for (int i=0;i<playerDeck.size();i++) {
						if(playerDeck.get(i).color.equals("caro")) {
							if(!playerDeck.get(i).wasUsed) {
								pos.add(i);
							}
						}
					}
				}
			}
			if(cardsOnBoard==2) {
				for (int i=0;i<playerDeck.size();i++) {
					if(playerDeck.get(i).color.equals(firstCard.color)) {
						if(!playerDeck.get(i).wasUsed) {
							pos.add(i);
						}
					}
				}
				if(pos.size()==0) {
					for (int i=0;i<playerDeck.size();i++) {
						if(playerDeck.get(i).color.equals("caro")) {
							if(!playerDeck.get(i).wasUsed) {
								pos.add(i);
							}
						}
					}
				}
			}
			return pos;
		case KIER_DISPATCH:
			System.out.println("checkCards_KIER_DIS_FIRST");
			if(firstCard.color.equals("kier")) {
				for (int i=0;i<playerDeck.size();i++) {
					if(playerDeck.get(i).color.equals("kier")) {
						if(playerDeck.get(i).power>firstCard.power) {
							if(!playerDeck.get(i).wasUsed) {
								pos.add(i);
							}
						}
					}
				}
				if(pos.size()==0) {
					for (int i=0;i<playerDeck.size();i++) {
						if(playerDeck.get(i).color.equals("kier")) {
							if(!playerDeck.get(i).wasUsed) {
								pos.add(i);
							}
						}
					}
				}
				if(pos.size()==0) {
					for (int i=0;i<playerDeck.size();i++) {
						if(!playerDeck.get(i).wasUsed) {
							pos.add(i);
						}
					}
				}
			}
			else {
				for (int i=0;i<playerDeck.size();i++) {
					if(playerDeck.get(i).color.equals(firstCard.color)) {
						if(playerDeck.get(i).power>firstCard.power) {
							if(!playerDeck.get(i).wasUsed) {
								pos.add(i);
							}
						}
					}
				}
				if(pos.size()==0) {
					for (int i=0;i<playerDeck.size();i++) {
						if(playerDeck.get(i).color.equals(firstCard.color)) {
							if(!playerDeck.get(i).wasUsed) {
								pos.add(i);
							}
						}
					}
				}
				if(pos.size()==0) {
					for (int i=0;i<playerDeck.size();i++) {
						if(playerDeck.get(i).color.equals("kier")) {
							if(!playerDeck.get(i).wasUsed) {
								pos.add(i);
							}
						}
					}
				}
			}
			if(cardsOnBoard==2) {
				for (int i=0;i<playerDeck.size();i++) {
					if(playerDeck.get(i).color.equals(firstCard.color)) {
						if(!playerDeck.get(i).wasUsed) {
							pos.add(i);
						}
					}
				}
				if(pos.size()==0) {
					for (int i=0;i<playerDeck.size();i++) {
						if(playerDeck.get(i).color.equals("kier")) {
							if(!playerDeck.get(i).wasUsed) {
								pos.add(i);
							}
						}
					}
				}
			}
			return pos;
		}
		return null;
	}
	public void addServerRoom(ServerRoom serverRoom) {
		this.serverRoom=serverRoom;
		if(typeClient==TypeClient.CASUAL_CLIENT) {
			serverRoom.setReadyStart.addEventHandler(ActionEvent.ACTION, e->{
				//serverRoom.changeStartStopImage();
				//System.out.println("WORK_SET_READY_BUTTON");
				chnageReadiness();
			});
		}
		else {
			serverRoom.setReadyStart.addEventHandler(ActionEvent.ACTION, e->{
			//serverRoom.changeStartStopImage();
			//System.out.println("WORK_START_GAME_BUTTON");
			boolean gameReady=true;
			for (int i=0;i<friends.size();i++) {
				if(!friends.get(i).isReady) {
					//////System.out.println("NOT_READY_PLAYER: "+friends.get(i).name);
					gameReady=false;
				}
			}
			switch(MainServer.typeServer) {
			case TWO_PLAYER:
				//System.out.println("TWO_WORK: "+gameReady+" friend "+friends.size());
				if(gameReady&&friends.size()==1) {
					game.leadingGame(1);
					sendMessege("START_GAME");
					setSceneGame();
					stage.setScene(new Scene(scene));
				}
				break;
			case THREE_PLAYER:
				//////System.out.println("TWO_WORK: "+gameReady+" friend "+friends.size());
				if(gameReady&&friends.size()==2) {
					game.leadingGame(1);
					sendMessege("START_GAME");
					setSceneGame();
					stage.setScene(new Scene(scene));
				}
				break;
			case FOUR_PLAYER:
				//////System.out.println("TWO_WORK: "+gameReady+" friend "+friends.size());
				if(gameReady&&friends.size()==3) {
					game.leadingGame(1);
					sendMessege("START_GAME");
					setSceneGame();
					stage.setScene(new Scene(scene));
				}
				break;
			}
			//serverRoom.setReadyStart.addEventHandler(null, null);
			});
		}
		setNameInServerRoom();
		setReadiness();
	}
	void setSceneGame() {
		scene = new SceneGame(typeClient);
		scene.plusFiveButton.addEventHandler(ActionEvent.ACTION, e->{
			////System.out.println("setSceneGame: "+userName);
			scene.levelOfPointNumber+=5;
			scene.levelOfPointsMin.setText(String.valueOf(scene.levelOfPointNumber));
			sendMessege("CHANGE_LEVEL_OF_POINTS");
			scene.setPass();
		});
		scene.passButton.addEventHandler(ActionEvent.ACTION, e->{
			scene.setPass();
			if(typeClient==TypeClient.SERVER_CLIENT) {
				readServer.amountOfPass+=1;
			}
			sendMessege("SET_PASS");
		});
		if(startAuction) {
			scene.setButtonsAndImages();
		}
		else {
			scene.setPass();
		}
		scene.setCards(playerDeck);
		switch (MainServer.typeServer) {
		case TWO_PLAYER:
				scene.firstPlayerName.setText(friends.get(0).name);
				scene.firstPlayerPoints.setText(String.valueOf(friends.get(0).pointInGame));
				scene.playerDeling.setText(dealingPlayer);
			break;
		case THREE_PLAYER:
				scene.firstPlayerName.setText(friends.get(0).name);
				scene.firstPlayerPoints.setText(String.valueOf(friends.get(0).pointInGame));
				scene.secondPlayerName.setText(friends.get(1).name);
				scene.secondPlayerPoints.setText(String.valueOf(friends.get(1).pointInGame));
				scene.playerDeling.setText(dealingPlayer);
			break;
		case FOUR_PLAYER:
				scene.firstPlayerName.setText(friends.get(0).name);
				scene.firstPlayerPoints.setText(String.valueOf(friends.get(0).pointInGame));
				scene.secondPlayerName.setText(friends.get(1).name);
				scene.secondPlayerPoints.setText(String.valueOf(friends.get(1).pointInGame));
				scene.thirdPlayerName.setText(friends.get(2).name);
				scene.thirdPlayerPoints.setText(String.valueOf(friends.get(2).pointInGame));
				scene.playerDeling.setText(dealingPlayer);
			break;
		}
	}
	
	void chnageReadiness() {
		System.out.println("chnageReadiness");
		sendMessege("SET_READINESS");
		if(isReady) {
			serverRoom.changeReady(2, "NOT_READY");
			isReady=false;
			sendMessege("NOT_READY");
		}
		else {
			serverRoom.changeReady(2, "READY");
			isReady=true;
			sendMessege("READY");
		}
	}
	
	void setReadiness() {
		switch(MainServer.typeServer){
		case THREE_PLAYER:
			if(friends.size()>=1) {
				if(friends.get(0).isReady) {
					serverRoom.changeReady(3, "READY");
				}
				else {
					serverRoom.changeReady(3, "NOT_READY");
				}
			}
			break;
		case FOUR_PLAYER:
			if(friends.size()>=1) {
				if(friends.get(0).isReady) {
					serverRoom.changeReady(3, "READY");
				}
				else {
					serverRoom.changeReady(3, "NOT_READY");
				}
			}
			if(friends.size()>=2) {
				if(friends.get(1).isReady) {
					serverRoom.changeReady(4, "READY");
				}
				else {
					serverRoom.changeReady(4, "NOT_READY");
				}
			}
			break;
		default:
			break;
		}
	}
	
	void setNameInServerRoom() {	
		System.out.println("setNameInServerRoom+work");
		if(serverRoom!=null) {
			switch(MainServer.typeServer) {
			case TWO_PLAYER:
				if(typeClient==TypeClient.SERVER_CLIENT) {
					serverRoom.first.setText(userName);
					if(friends.size()==1) {
						serverRoom.second.setText(friends.get(0).name);
					}
				}
				else {
					if(friends.size()>=1) {
						serverRoom.first.setText(friends.get(0).name);
					}
					serverRoom.second.setText(userName);
				}
				break;
			case THREE_PLAYER:
				if(typeClient==TypeClient.SERVER_CLIENT) {
					serverRoom.first.setText(userName);
					if(friends.size()>=1) {
						serverRoom.second.setText(friends.get(0).name);
					}
					if(friends.size()>=2) {
						serverRoom.third.setText(friends.get(1).name);
					}
				}
				else {
					if(friends.size()>=1) {
						serverRoom.first.setText(friends.get(0).name);
					}
					serverRoom.second.setText(userName);
					if(friends.size()>=2) {
						serverRoom.third.setText(friends.get(1).name);
					}
				}
				break;
			case FOUR_PLAYER:
				if(typeClient==TypeClient.SERVER_CLIENT) {
					serverRoom.first.setText(userName);
					if(friends.size()>=1) {
						serverRoom.second.setText(friends.get(0).name);
					}
					if(friends.size()>=2) {
						serverRoom.third.setText(friends.get(1).name);
					}
					if(friends.size()>=3) {
						serverRoom.fourth.setText(friends.get(2).name);
					}
				}
				else {
					if(friends.size()>=1) {
						serverRoom.first.setText(friends.get(0).name);
					}
					serverRoom.second.setText(userName);
					if(friends.size()>=2) {
						serverRoom.third.setText(friends.get(1).name);
					}
					if(friends.size()>=3) {
						serverRoom.fourth.setText(friends.get(2).name);
					}
				}
				break;
			}
		}
	}
	
	void readTypeServer() {
		//////System.out.println("readTypeServer-WORK_CLIENT");
		String mess=readMessege();
		System.out.println("readTypeServer+"+mess);
		switch(mess) {
		case "TWO":
			MainServer.typeServer=TypeServer.TWO_PLAYER;
			//////System.out.println("TWOPLAYERWORK");
			break;
		case "THREE":
			MainServer.typeServer=TypeServer.THREE_PLAYER;
			//////System.out.println("THREEPLAYERWORK");
			break;
		case "FOUR":
			MainServer.typeServer=TypeServer.FOUR_PLAYER;
			//////System.out.println("FOURPLAYERWORK");
			break;
		default:
			;	
		}
		//////System.out.println(mess);
	}
	
	void sendName() {
		sendMessege("SEND_NAME");
		switch (typeClient){
		case CASUAL_CLIENT:	
			sendMessege("CASUAL_PLAYER");
			sendMessege(userName);
		//////System.out.println("SEND_NICK_CLIENT_CASUAL: "+userName);
			break;
		case SERVER_CLIENT:
			sendMessege("SERVER_PLAYER");
			sendMessege(userName);
			//////System.out.println("SEND_NICK_CLIENT_SERVER: "+userName);
			break;
		}
	}
	
	Cards setCard(String mess) {
		if(mess.equals("PN")) {
			return new Cards(0,"pik","nine","PN","/resources/NinePik.jpg");
		}
		else if(mess.equals("PT")) {
			return new Cards(10,"pik","ten","PT","/resources/TenPik.jpg");
		}
		else if(mess.equals("PA")) {
			return new Cards(11,"pik","as","PA","/resources/AsPik.jpg");
		}
		else if(mess.equals("PQ")) {
			return new Cards(3,"pik","quene","PQ","/resources/QueenPik.jpg");
		}
		else if(mess.equals("PK")) {
			return new Cards(4,"pik","king","PK","/resources/KingPik.jpg");
		}
		else if(mess.equals("PJ")) {
			return new Cards(2,"pik","jack","PJ","/resources/JackPik.jpg");
		}
		else if(mess.equals("CN")) {
			return new Cards(0,"caro","nine","CN","/resources/NineKaro.jpg");
		}
		else if(mess.equals("CT")) {
			return new Cards(10,"caro","ten","CT","/resources/TenKaro.jpg");
		}
		else if(mess.equals("CA")) {
			return new Cards(11,"caro","as","CA","/resources/AsKaro.jpg");
		}
		else if(mess.equals("CQ")) {
			return new Cards(3,"caro","quene","CQ","/resources/QueenKaro.jpg");
		}
		else if(mess.equals("CK")) {
			return new Cards(4,"caro","king","CK","/resources/KingKaro.jpg");
		}
		else if(mess.equals("CJ")) {
			return new Cards(2,"caro","jack","CJ","/resources/JackKaro.jpg");
		}
		else if(mess.equals("TN")) {
			return new Cards(0,"trefl","nine","TN","/resources/NineTrefl.jpg");
		}
		else if(mess.equals("TT")) {
			return new Cards(10,"trefl","ten","TT","/resources/TenTrefl.jpg");
		}
		else if(mess.equals("TA")) {
			return new Cards(11,"trefl","as","TA","/resources/AsTrefl.jpg");
		}
		else if(mess.equals("TQ")) {
			return new Cards(3,"trefl","quene","TQ","/resources/QueenTrefl.jpg");
		}
		else if(mess.equals("TK")) {
			return new Cards(4,"trefl","king","TK","/resources/KingTrefl.jpg");
		}
		else if(mess.equals("TJ")) {
			return new Cards(2,"trefl","jack","TJ","/resources/JackTrefl.jpg");
		}
		else if(mess.equals("KN")) {
			return new Cards(0,"kier","nine","KN","/resources/NineKier.jpg");
		}
		else if(mess.equals("KT")) {
			return new Cards(10,"kier","ten","KT","/resources/TenKier.jpg");
		}
		else if(mess.equals("KA")) {
			return new Cards(11,"kier","as","KA","/resources/AsKier.jpg");
		}
		else if(mess.equals("KQ")) {
			return new Cards(3,"kier","quene","KQ","/resources/QueenKier.jpg");
		}
		else if(mess.equals("KK")) {
			return new Cards(4,"kier","king","KK","/resources/KingKier.jpg");
		}
		else if(mess.equals("KJ")) {
			return new Cards(2,"kier","jack","KJ","/resources/JackKier.jpg");
		}
		return null;
	}
	
	void takeDeck(String messege) {
		if(messege.equals("START_DECK_SEND")) {
			//////System.out.println("START_DECK_SEND_CLIENT");
			String mess1=readMessege();
			////System.out.println("takeDeck_ mess1: "+mess1);
			if(mess1.equals("NORMAL_PLAYER")) {
				////System.out.println("takeDeck+NORMALPLAYER");
				typePlayer=TypePlayer.NORMAL_PLAYER;
				String mess;
				while (!(mess=readMessege()).equals("STOP")) {
					//////System.out.println(mess);
					playerDeck.add(setCard(mess));
				}
				mess= readMessege();
				if(mess.equals("TRUE")) {
					startAuction=true;
				}
				else {
					startAuction=false;
				}
			}
			else {
				////System.out.println("takeDeck+MUSIKPLAYER");
				typePlayer=TypePlayer.MUSIK_PLAYER;
				startAuction=false;
			}
		}
		if(endedRounds!=0) {
			scene.setCards(playerDeck);
		}
	}
	void takeNickName() {
		String mess=readMessege();
		////System.out.println("CLIENT_TAKE_NICKNAME"+mess);
		switch (mess) {
		case"SERVER_PLAYER":
			Friend f = new Friend();
			f.name=readMessege();
			f.code=readMessege();
			f.isServer= false;
			f.isServerOwner=true;
			if(MainServer.typeServer==TypeServer.TWO_PLAYER) {
				f.amountCard=12;
				givenCards=2;
			}
			else {
				f.amountCard=7;
			}
			f.pointInGame=0;
			friends.add(f);
			System.out.println(f.name+ " :NormalPlayer-"+f.code);	
			break;
		case "CASUAL_PLAYER":
			Friend f1 = new Friend();
			f1.name=readMessege();
			f1.code=readMessege();
			f1.isServer= false;
			if(MainServer.typeServer==TypeServer.TWO_PLAYER) {
				f1.amountCard=12;
				givenCards=2;
			}
			else {
				f1.amountCard=7;
			}
			f1.pointInGame=0;
			friends.add(f1);
			System.out.println(f1.name+ " :NormalPlayer-"+f1.code);	
			break;
		}
		setNameInServerRoom();
	}
	
	String readMessege() {
		String mess;
		try {
			mess = bufferedReader.readLine();
			return mess;
		} catch (IOException e) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					System.exit(0);
				}
			});
			e.printStackTrace();
		}
		return null;
	}
	
	void readReadiness() {
		String mess =readMessege();
		System.out.println("CHANGE_READINESS-"+mess);
		for (int i=0;i<friends.size();i++) {
			//////System.out.println("CODE: "+friends.get(i).code);
			if(mess.equals(friends.get(i).code)) {
				if(typeClient==TypeClient.SERVER_CLIENT) {
					mess=readMessege();
					serverRoom.changeReady(i+2, mess);
					if(mess.equals("READY")) {
						friends.get(i).isReady=true;
					}
					else {
						friends.get(i).isReady=false;
					}
				}
				else {
					mess=readMessege();
					if(serverRoom!=null) {
						serverRoom.changeReady(i+2, mess);
					}
					if(mess.equals("READY")) {
						friends.get(i).isReady=true;
					}
					else {
						friends.get(i).isReady=false;
					}
				}
			}
		}
	}
	
	void startGame() {
		//sendStartGame();
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				setSceneGame();
				stage.setScene(new Scene(scene));
			}
		});
	}
	
	void takeMusik() {
		String s;
		scene.setPass();
		while (!(s=readMessege()).equals("STOP")) {
			scene.setBorderCard(setCard(s));
		}
	}
	
	void setCardsButtons() {
		int i=0;
		System.out.println("setCardsButtons()-first-size: "+scene.playerCardsImage.size());
		System.out.println("setCardsButtons()-second-size: "+scene.playerCards.size());
		while (i<scene.playerCards.size()) {
			if(!scene.playerCards.get(i).wasUsed) {
				scene.playerCardsButtons.get(i).setVisible(true);
				if(scene.playerCardsImage.get(i).getImage()!=null) {
					//System.out.println("setCardsButtons()+setEvent: "+i);
					if(scene.playerCardsImage.get(i).getOpacity()==1) {
						int x=i;
						scene.playerCardsButtons.get(x).addEventHandler(ActionEvent.ACTION, e->{
							//System.out.println("addFirstFunctionButton+work");
							scene.playerCardsImage.get(x).setY(700*scene.screenSizeWidth);
							scene.playerCardsButtons.get(x).setLayoutY(700*scene.screenSizeWidth);
							cardToGive=scene.playerCards.get(x);
							int j=0;
							while (j<scene.playerCardsImage.size()) {
								//System.out.println("WHILE_LOOP_WORK");
								if(scene.playerCardsImage.get(j).getImage()!=null) {
									if(j!=x) {
										scene.playerCardsImage.get(j).setY(755*scene.screenSizeWidth);
										scene.playerCardsButtons.get(j).setLayoutY(755*scene.screenSizeWidth);
									}
								}
								j+=1;
							}
						});
					}
				}
			}
			i+=1;
		}
	}
	
	void addEventSendCards() {
		System.out.println("addEventSendCards");
		scene.addEventHandler(KeyEvent.KEY_PRESSED, e->{
			if(e.getCode()==KeyCode.ENTER&&youGiveCard&&cardToGive!=null) {
				System.out.println("addEventSendCards+WORK");
				int indexCard=playerDeck.indexOf(cardToGive);
				if(scene.playerCardsImage.get(indexCard).getOpacity()==1||firstCard==null) {
					System.out.println("SEND_WORK_GIVEN_CARD: "+givenCards);
					if(givenCards==0) {
						sendMessege("SEND_TWO_CARDS");
						System.out.println("FIRST_CARD");
						if(typeClient==TypeClient.CASUAL_CLIENT) {
							for (int i=0;i<friends.size();i++) {
								if(friends.get(i).amountCard==7) {
									////System.out.println("SEND_SECOND");
									sendMessege(friends.get(i).code);
									sendMessege(cardToGive.sygnature);
									friends.get(i).amountCard+=1;
									i=friends.size();
								}
							}
						}
						else {
							for (int i=0;i<friends.size();i++) {
								if(friends.get(i).amountCard==7) {
									////System.out.println("SEND_THIRD");
									sendMessege(friends.get(i).code);
									sendMessege(cardToGive.sygnature);
									friends.get(i).amountCard+=1;
									i=friends.size();
								}
							}
						}
						cardToGive=null;
					}
					else if(givenCards==1){
						sendMessege("SEND_TWO_CARDS");
						System.out.println("SECOND_CARD");
						for (int i=0;i<friends.size();i++) {
							System.out.println(friends.get(i).code+":"+friends.get(i).amountCard);
							if(friends.get(i).amountCard==7) {
								System.out.println(friends.get(i).code);
								sendMessege(friends.get(i).code);
								sendMessege(cardToGive.sygnature);
								scene.addEventHandler(KeyEvent.KEY_PRESSED, s->{});
								i=friends.size();
							}
						}
						cardToGive=null;
					}
					else if(givenCards>=2){
						//here is misstake
						System.out.println("GIVENCARDS_2");
						if(typeClient==TypeClient.SERVER_CLIENT) {
							//System.out.println("givenCard_If ");
							sendMessege("CARD_TO_BOARD");
							sendMessege("FROM_SERVER");
							sendMessege(cardToGive.sygnature);
							scene.addBoard(cardToGive);
							setSleep(1000);
							//System.out.println("givenCard_If_FIRST ");
							if(game.deal==null) {
								game.leadingGame(5);
								game.deal.setClass(scene.levelOfPointNumber);
							}
							if(game.deal.first==null) {
								//setSleep(1000);
								Platform.runLater(new Runnable() {
									@Override
									public void run() {
										game.deal.addFirstCards(cardToGive, code, checkDispatch(cardToGive.sygnature));
									}
								});
							}
							else if(game.deal.second==null){
								//scene.addBoard(cardToGive);
								Platform.runLater(new Runnable() {
									@Override
									public void run() {
										game.deal.addSecondCards(cardToGive, code);
									}
								});
							}
							else if(game.deal.third==null) {
								//scene.addBoard(cardToGive);
								Platform.runLater(new Runnable() {
									@Override
									public void run() {
										game.deal.addThirdCards(cardToGive, code);
									}
								});
							}
						}
						else {
							//System.out.println("givenCard_2_else");
							scene.addBoard(cardToGive);
							sendMessege("CARD_TO_BOARD");
							sendMessege("NOT_FROM_SERVER");
							sendMessege(cardToGive.sygnature);
							if(checkDispatch(cardToGive.sygnature)) {
								sendMessege("IS_DISPATCH");
							}
							else {
								sendMessege("ISNOT_DISPATCH");
							}
						}
						for (int i=0;i<scene.playerCardsImage.size();i++) {
							scene.playerCardsImage.get(i).setOpacity(0.75);
						}
						youGiveCard=false;
					}
					givenCards+=1;
					//System.out.println("givenCards="+givenCards);
					scene.playerCardsImage.get(indexCard).setImage(null);
					scene.playerCardsImage.get(indexCard).setY(755*scene.screenSizeWidth);
					scene.playerCardsButtons.get(indexCard).setLayoutY(755*scene.screenSizeWidth);
					scene.playerCardsButtons.get(indexCard).setVisible(false);
					scene.playerCards.get(indexCard).setUsed();
					playerDeck.get(indexCard).setUsed();
				}
			}
		});
	}
	
	boolean checkDispatch(String syng) {
		//System.out.println("checkDispatch");
		switch (syng) {
		case "PQ":
			for (int i=0;i<playerDeck.size();i++) {
				if(playerDeck.get(i).sygnature.equals("PK")) { 
					if(!playerDeck.get(i).wasUsed) {
						System.out.println("is-dispatch-pik");
						return true;
					}
				}
			}
			break;
		case "CQ":
			for (int i=0;i<playerDeck.size();i++) {
				if(playerDeck.get(i).sygnature.equals("CK")) { 
					if(!playerDeck.get(i).wasUsed) {
						System.out.println("is-dispatch-caro");
						return true;
					}
				}
			}
			break;
		case "TQ":
			for (int i=0;i<playerDeck.size();i++) {
				if(playerDeck.get(i).sygnature.equals("TK")) { 
					if(!playerDeck.get(i).wasUsed) {
						System.out.println("is-dispatch-trefl");
						return true;
					}
				}
			}
			break;
		case "KQ":
			for (int i=0;i<playerDeck.size();i++) {
				if(playerDeck.get(i).sygnature.equals("KK")) { 
					if(!playerDeck.get(i).wasUsed) {
						System.out.println("is-dispatch-kier");
						return true;
					}
				}
			}
			break;
		}
		//System.out.println("isnot-dispatchk");
		return false;
	}
	
	void setSleep(int x) {
		try {
			Thread.sleep(x);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	void endRound() {
		scene.levelOfPointsMin.setText("100");
		scene.levelOfPointNumber=100;
		scene.levelOfPointsInOneDeal.setText("0");
		scene.playerCards.clear();
		scene.typeDispatch.setText("");
		dispatch=Pairs.NULL_DISPATCH;
		pointsInOneDeal=0;
		endedRounds+=1;
		playerDeck.clear();
		givenCards=0;
		for (int i=0;i<friends.size();i++) {
			friends.get(i).isPass=false;
			if(MainServer.typeServer!=TypeServer.TWO_PLAYER) {
				friends.get(i).amountCard=7;
			}
		}
		if(MainServer.typeServer==TypeServer.TWO_PLAYER) {
			String addNotPoints=readMessege();
			if(addNotPoints.equals("ADD_POINT")) {
				String codeF=readMessege();
				if(codeF.equals(code)) {
					String addNot=readMessege();
					int point=Integer.parseInt(readMessege());
					if(addNot.equals("PLUS")) {
						pointInGame+=point;
					}
					else {
						pointInGame-=point;
					}
					scene.levelOfPointsPlayer.setText(String.valueOf(pointInGame));
				}
				else {
					String addNot=readMessege();
					int point=Integer.parseInt(readMessege());
					if(addNot.equals("PLUS")) {
						friends.get(0).pointInGame+=point;
					}
					else {
						friends.get(0).pointInGame-=point;
					}
					scene.firstPlayerPoints.setText(String.valueOf(friends.get(0).pointInGame));
				}
			}
			String addNotPoints1=readMessege();
			if(addNotPoints1.equals("ADD_POINT")) {
				String codeS=readMessege();
				if(codeS.equals(code)) {
					String addNot=readMessege();
					int point=Integer.parseInt(readMessege());
					if(addNot.equals("PLUS")) {
						pointInGame+=point;
					}
					else {
						pointInGame-=point;
					}
					scene.levelOfPointsPlayer.setText(String.valueOf(pointInGame));
				}
				else {
					String addNot=readMessege();
					int point=Integer.parseInt(readMessege());
					if(addNot.equals("PLUS")) {
						friends.get(0).pointInGame+=point;
					}
					else {
						friends.get(0).pointInGame-=point;
					}
					scene.firstPlayerPoints.setText(String.valueOf(friends.get(0).pointInGame));
				}
			}
		}
		else if(MainServer.typeServer==TypeServer.THREE_PLAYER){
			System.out.println("CODE_THIS"+code);
			System.out.println("CODE_FIRST_FRIEND"+friends.get(0).code);
			System.out.println("CODE_SECOND_FRIEND"+friends.get(1).code);
			String addNotPoints=readMessege();
			System.out.println(addNotPoints+"THREE_PLAYERS");
			if(addNotPoints.equals("ADD_POINT")) {
				String codeF=readMessege();
				System.out.println(codeF+"THREE_PLAYERS"+"   CODEFIRST");
				if(codeF.equals(code)) {
					String addNot=readMessege();
					System.out.println(addNot);
					String mess=readMessege();
					System.out.println(mess);
					int point=Integer.parseInt(mess);
					if(addNot.equals("PLUS")) {
						pointInGame+=point;
					}
					else {
						pointInGame-=point;
					}
					scene.levelOfPointsPlayer.setText(String.valueOf(pointInGame));
				}
				else if(codeF.equals(friends.get(0).code)) {
					String addNot=readMessege();
					System.out.println(addNot);
					String mess=readMessege();
					System.out.println(mess);
					int point=Integer.parseInt(mess);
					if(addNot.equals("PLUS")) {
						friends.get(0).pointInGame+=point;
					}
					else {
						friends.get(0).pointInGame-=point;
					}
					scene.firstPlayerPoints.setText(String.valueOf(friends.get(0).pointInGame));
				}
				else if(codeF.equals(friends.get(1).code)) {
					String addNot=readMessege();
					System.out.println(addNot);
					String mess=readMessege();
					System.out.println(mess);
					int point=Integer.parseInt(mess);
					if(addNot.equals("PLUS")) {
						friends.get(1).pointInGame+=point;
					}
					else {
						friends.get(1).pointInGame-=point;
					}
					scene.secondPlayerPoints.setText(String.valueOf(friends.get(1).pointInGame));
				}
			}
			String addNotPoints1=readMessege();
			System.out.println(addNotPoints1+"THREE_PLAYERS_SECOND");
			if(addNotPoints1.equals("ADD_POINT")) {
				String codeS=readMessege();
				System.out.println(codeS+"THREE_PLAYERS"+"CODESecond");
				if(codeS.equals(code)) {
					String addNot=readMessege();
					System.out.println(addNot);
					String mess=readMessege();
					System.out.println(mess);
					int point=Integer.parseInt(mess);
					if(addNot.equals("PLUS")) {
						pointInGame+=point;
					}
					else {
						pointInGame-=point;
					}
					scene.levelOfPointsPlayer.setText(String.valueOf(pointInGame));
				}
				else if(codeS.equals(friends.get(0).code)) {
					String addNot=readMessege();
					System.out.println(addNot);
					String mess=readMessege();
					System.out.println(mess);
					int point=Integer.parseInt(mess);
					if(addNot.equals("PLUS")) {
						friends.get(0).pointInGame+=point;
					}
					else {
						friends.get(0).pointInGame-=point;
					}
					scene.firstPlayerPoints.setText(String.valueOf(friends.get(0).pointInGame));
				}
				else if(codeS.equals(friends.get(1).code)) {
					String addNot=readMessege();
					System.out.println(addNot);
					String mess=readMessege();
					System.out.println(mess);
					int point=Integer.parseInt(mess);
					if(addNot.equals("PLUS")) {
						friends.get(1).pointInGame+=point;
					}
					else {
						friends.get(1).pointInGame-=point;
					}
					scene.secondPlayerPoints.setText(String.valueOf(friends.get(1).pointInGame));
				}
			}
			String addNotPoints2=readMessege();
			System.out.println(addNotPoints2+"THREE_PLAYERS_THIRD");
			if(addNotPoints2.equals("ADD_POINT")) {
				String codeT=readMessege();
				System.out.println(codeT+"THREE_PLAYERS"+"CODEThird");
				if(codeT.equals(code)) {
					String addNot=readMessege();
					System.out.println(addNot);
					String mess=readMessege();
					System.out.println(mess);
					int point=Integer.parseInt(mess);
					if(addNot.equals("PLUS")) {
						pointInGame+=point;
					}
					else {
						pointInGame-=point;
					}
					scene.levelOfPointsPlayer.setText(String.valueOf(pointInGame));
				}
				else if(codeT.equals(friends.get(0).code)) {
					String addNot=readMessege();
					System.out.println(addNot);
					String mess=readMessege();
					System.out.println(mess);
					int point=Integer.parseInt(mess);
					if(addNot.equals("PLUS")) {
						friends.get(0).pointInGame+=point;
					}
					else {
						friends.get(0).pointInGame-=point;
					}
					scene.firstPlayerPoints.setText(String.valueOf(friends.get(0).pointInGame));
				}
				else if(codeT.equals(friends.get(1).code)) {
					String addNot=readMessege();
					System.out.println(addNot);
					String mess=readMessege();
					System.out.println(mess);
					int point=Integer.parseInt(mess);
					if(addNot.equals("PLUS")) {
						friends.get(1).pointInGame+=point;
					}
					else {
						friends.get(1).pointInGame-=point;
					}
					scene.secondPlayerPoints.setText(String.valueOf(friends.get(1).pointInGame));
				}
			}
		}
		else if(MainServer.typeServer==TypeServer.FOUR_PLAYER) {
			String addNotPoints=readMessege();
			if(addNotPoints.equals("ADD_POINT")) {
				String codeF=readMessege();
				if(codeF.equals(code)) {
					String addNot=readMessege();
					int point=Integer.parseInt(readMessege());
					if(addNot.equals("PLUS")) {
						pointInGame+=point;
					}
					else {
						pointInGame-=point;
					}
					scene.levelOfPointsPlayer.setText(String.valueOf(pointInGame));
				}
				else if(codeF.equals(friends.get(0).code)) {
					String addNot=readMessege();
					int point=Integer.parseInt(readMessege());
					if(addNot.equals("PLUS")) {
						friends.get(0).pointInGame+=point;
					}
					else {
						friends.get(0).pointInGame-=point;
					}
					scene.firstPlayerPoints.setText(String.valueOf(friends.get(0).pointInGame));
				}
				else if(codeF.equals(friends.get(1).code)) {
					String addNot=readMessege();
					int point=Integer.parseInt(readMessege());
					if(addNot.equals("PLUS")) {
						friends.get(1).pointInGame+=point;
					}
					else {
						friends.get(1).pointInGame-=point;
					}
					scene.secondPlayerPoints.setText(String.valueOf(friends.get(1).pointInGame));
				}
				else if(codeF.equals(friends.get(2).code)) {
					String addNot=readMessege();
					int point=Integer.parseInt(readMessege());
					if(addNot.equals("PLUS")) {
						friends.get(2).pointInGame+=point;
					}
					else {
						friends.get(2).pointInGame-=point;
					}
					scene.secondPlayerPoints.setText(String.valueOf(friends.get(2).pointInGame));
				}
			}
			String addNotPoints1=readMessege();
			if(addNotPoints1.equals("ADD_POINT")) {
				String codeS=readMessege();
				if(codeS.equals(code)) {
					String addNot=readMessege();
					int point=Integer.parseInt(readMessege());
					if(addNot.equals("PLUS")) {
						pointInGame+=point;
					}
					else {
						pointInGame-=point;
					}
					scene.levelOfPointsPlayer.setText(String.valueOf(pointInGame));
				}
				else if(codeS.equals(friends.get(0).code)) {
					String addNot=readMessege();
					int point=Integer.parseInt(readMessege());
					if(addNot.equals("PLUS")) {
						friends.get(0).pointInGame+=point;
					}
					else {
						friends.get(0).pointInGame-=point;
					}
					scene.firstPlayerPoints.setText(String.valueOf(friends.get(0).pointInGame));
				}
				else if(codeS.equals(friends.get(1).code)) {
					String addNot=readMessege();
					int point=Integer.parseInt(readMessege());
					if(addNot.equals("PLUS")) {
						friends.get(1).pointInGame+=point;
					}
					else {
						friends.get(1).pointInGame-=point;
					}
					scene.secondPlayerPoints.setText(String.valueOf(friends.get(1).pointInGame));
				}
				else if(codeS.equals(friends.get(2).code)) {
					String addNot=readMessege();
					int point=Integer.parseInt(readMessege());
					if(addNot.equals("PLUS")) {
						friends.get(2).pointInGame+=point;
					}
					else {
						friends.get(2).pointInGame-=point;
					}
					scene.secondPlayerPoints.setText(String.valueOf(friends.get(2).pointInGame));
				}
			}
			String addNotPoints2=readMessege();
			if(addNotPoints2.equals("ADD_POINT")) {
				String codeT=readMessege();
				if(codeT.equals(code)) {
					String addNot=readMessege();
					int point=Integer.parseInt(readMessege());
					if(addNot.equals("PLUS")) {
						pointInGame+=point;
					}
					else {
						pointInGame-=point;
					}
					scene.levelOfPointsPlayer.setText(String.valueOf(pointInGame));
				}
				else if(codeT.equals(friends.get(0).code)) {
					String addNot=readMessege();
					int point=Integer.parseInt(readMessege());
					if(addNot.equals("PLUS")) {
						friends.get(0).pointInGame+=point;
					}
					else {
						friends.get(0).pointInGame-=point;
					}
					scene.firstPlayerPoints.setText(String.valueOf(friends.get(0).pointInGame));
				}
				else if(codeT.equals(friends.get(1).code)) {
					String addNot=readMessege();
					int point=Integer.parseInt(readMessege());
					if(addNot.equals("PLUS")) {
						friends.get(1).pointInGame+=point;
					}
					else {
						friends.get(1).pointInGame-=point;
					}
					scene.secondPlayerPoints.setText(String.valueOf(friends.get(1).pointInGame));
				}
				else if(codeT.equals(friends.get(2).code)) {
					String addNot=readMessege();
					int point=Integer.parseInt(readMessege());
					if(addNot.equals("PLUS")) {
						friends.get(2).pointInGame+=point;
					}
					else {
						friends.get(2).pointInGame-=point;
					}
					scene.secondPlayerPoints.setText(String.valueOf(friends.get(2).pointInGame));
				}
			}
		}
		System.out.println(scene.levelOfPointsPlayer.getText()+"end_endRound_game");
	}
	
	void endGame() {
		LastScene last = new LastScene(); 
		ArrayList<Integer> points = new ArrayList<>();
		points.add(pointInGame);
		for (int i=0;i<friends.size();i++) {
			points.add(friends.get(i).pointInGame);
		}
		Collections.sort(points, Collections.reverseOrder());
		switch (MainServer.typeServer) {
		case TWO_PLAYER:
			if(pointInGame==points.get(0)) {
				last.winner.setText(userName); 
				last.second.setText(friends.get(0).name);
			}
			else if(friends.get(0).pointInGame==points.get(0)) {
				last.winner.setText(friends.get(0).name); 
				last.second.setText(userName);
			}
			break;
		case THREE_PLAYER:
			if(pointInGame==points.get(0)) {
				last.winner.setText(userName);
			}
			else if(pointInGame==points.get(1)) {
				last.second.setText(userName);
				if(pointInGame==friends.get(0).pointInGame) {
					last.second.setText(last.second.getText()+" ex aequo "+friends.get(0).name);
				}
				if(pointInGame==friends.get(1).pointInGame) {
					last.second.setText(last.second.getText()+" ex aequo "+friends.get(1).name);
				}
			}
			else if(pointInGame==points.get(2)) {
				last.third.setText(userName);
				if(pointInGame==friends.get(0).pointInGame) {
					last.third.setText(last.third.getText()+" ex aequo "+friends.get(0).name);
				}
				if(pointInGame==friends.get(1).pointInGame) {
					last.third.setText(last.third.getText()+" ex aequo "+friends.get(1).name);
				}
			}	
			if(friends.get(0).pointInGame==points.get(0)) {
				last.winner.setText(friends.get(0).name);
				
			}
			else if(friends.get(0).pointInGame==points.get(1)) {
				last.second.setText(friends.get(0).name);
				if(friends.get(0).pointInGame==pointInGame) {
					last.second.setText(last.second.getText()+" ex aequo "+userName);
				}
				if(friends.get(0).pointInGame==friends.get(1).pointInGame) {
					last.second.setText(last.second.getText()+" ex aequo "+friends.get(1).name);
				}
			}
			else if(friends.get(0).pointInGame==points.get(2)) {
				last.third.setText(friends.get(0).name);
				if(friends.get(0).pointInGame==pointInGame) {
					last.third.setText(last.third.getText()+" ex aequo "+userName);
				}
				if(friends.get(0).pointInGame==friends.get(1).pointInGame) {
					last.third.setText(last.third.getText()+" ex aequo "+friends.get(1).name);
				}
			}
			if(friends.get(1).pointInGame==points.get(0)) {
				last.winner.setText(friends.get(1).name);
			}
			else if(friends.get(1).pointInGame==points.get(1)) {
				last.second.setText(friends.get(1).name);
				if(friends.get(1).pointInGame==pointInGame) {
					last.second.setText(last.second.getText()+" ex aequo "+userName);
				}
				if(friends.get(1).pointInGame==friends.get(0).pointInGame) {
					last.second.setText(last.second.getText()+" ex aequo "+friends.get(0).name);
				}
			}
			else if(friends.get(1).pointInGame==points.get(2)) {
				last.third.setText(friends.get(1).name);
				if(friends.get(1).pointInGame==pointInGame) {
					last.third.setText(last.third.getText()+" ex aequo "+userName);
				}
				if(friends.get(1).pointInGame==friends.get(0).pointInGame) {
					last.third.setText(last.third.getText()+" ex aequo "+friends.get(0).name);
				}
			}
			break;
		case FOUR_PLAYER:
			if(pointInGame==points.get(0)) {
				last.winner.setText(userName);
			}
			else if(pointInGame==points.get(1)) {
				last.second.setText(userName);
			}
			else if(pointInGame==points.get(2)) {
				last.second.setText(userName);
			}
			else if(pointInGame==points.get(3)) {
				last.second.setText(userName);
			}
			
			if(friends.get(0).pointInGame==points.get(0)) {
				last.winner.setText(friends.get(0).name);
			}
			else if(friends.get(0).pointInGame==points.get(1)) {
				if(last.second.getText()!=null) {
					last.second.setText(last.second.getText()+" ex aequo "+friends.get(0).name);
				}
				else {
					last.second.setText(friends.get(0).name);
				}
			}
			else if(friends.get(0).pointInGame==points.get(2)) {
				if(last.third.getText()!=null) {
					last.third.setText(last.third.getText()+" ex aequo "+friends.get(0).name);
				}
				else {
					last.third.setText(friends.get(0).name);
				}
			}
			else if(friends.get(0).pointInGame==points.get(3)) {
				if(last.last.getText()!=null) {
					last.last.setText(last.last.getText()+" ex aequo "+friends.get(0).name);
				}
				else {
					last.last.setText(friends.get(0).name);
				}
			}
			
			if(friends.get(1).pointInGame==points.get(0)) {
				last.winner.setText(friends.get(1).name);
			}
			else if(friends.get(1).pointInGame==points.get(1)) {
				if(last.second.getText()!=null) {
					last.second.setText(last.second.getText()+" ex aequo "+friends.get(1).name);
				}
				else {
					last.second.setText(friends.get(1).name);
				}
			}
			else if(friends.get(1).pointInGame==points.get(2)) {
				if(last.third.getText()!=null) {
					last.third.setText(last.third.getText()+" ex aequo "+friends.get(1).name);
				}
				else {
					last.third.setText(friends.get(1).name);
				}
			}
			else if(friends.get(1).pointInGame==points.get(3)) {
				if(last.last.getText()!=null) {
					last.last.setText(last.last.getText()+" ex aequo "+friends.get(1).name);
				}
				else {
					last.last.setText(friends.get(1).name);
				}
			}
			if(friends.get(2).pointInGame==points.get(0)) {
				last.winner.setText(friends.get(2).name);
			}
			else if(friends.get(2).pointInGame==points.get(2)) {
				if(last.second.getText()!=null) {
					last.second.setText(last.second.getText()+" ex aequo "+friends.get(2).name);
				}
				else {
					last.second.setText(friends.get(2).name);
				}
			}
			else if(friends.get(2).pointInGame==points.get(2)) {
				if(last.third.getText()!=null) {
					last.third.setText(last.third.getText()+" ex aequo "+friends.get(2).name);
				}
				else {
					last.third.setText(friends.get(2).name);
				}
			}
			else if(friends.get(2).pointInGame==points.get(3)) {
				if(last.last.getText()!=null) {
					last.last.setText(last.last.getText()+" ex aequo "+friends.get(2).name);
				}
				else {
					last.last.setText(friends.get(2).name);
				}
			}
			break;
		}
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				stage.setScene(new Scene(last));
			}
		});
	}
	
	class CheckWorking extends Thread{
		public void run() {
			while (stage.isShowing()) {
				setSleep(1);
			}
			System.exit(0);
		}
	}
	
	class ReadDataClientServer extends Thread{
		int amountOfPass=0;
		boolean work=true;
		public void run() {
			String messege;
			while (work) {
				if(!stage.isShowing())System.exit(0);
				System.out.println("CLIENT_SERVER_MESSEGE is weating");
				messege = readMessege();
				System.out.println("CLIENT_SERVER_MESSEGE: "+messege);
				switch(messege) {
				case "SEND_CODE":
					code=readMessege();
					break;
				case "MESSEGE_READ":
					messegeWasRead=true;
					break;
				case "SEND_MUSIK":
					////System.out.println("START_MUSIK");
					takeMusik();
					//sendMessege("MESSEGE_READ");
					break;
				case "START_DECK_SEND":
					//////System.out.println("START_DECK");
					takeDeck(messege);
					//sendMessege("MESSEGE_READ");
					break;
				case "START_OTHER_NICKNAME":
					//////System.out.println("START_OTHER_NICKNAME");
					takeNickName();
					sendMessege("MESSEGE_READ");
					break;
				case "CHANGE_READINESS":
					////System.out.println("CHANGE_READINESS");
					readReadiness();
					//sendMessege("MESSEGE_READ");
					break;
				case "CHANGE_LEVEL_OF_POINTS":
					//////System.out.println("CHANGE_LEVEL_OF_POINTS");
					scene.levelOfPointNumber+=5;
					scene.levelOfPointsMin.setText(String.valueOf(scene.levelOfPointNumber));
					//sendMessege("MESSEGE_READ");
					break;
				case "YOUR_TURN":
					//System.out.println("YOUR_TURN");
					scene.setButtonsAndImages();
					//sendMessege("MESSEGE_READ");
					break;
				case "SET_PASS":
					String codeBuff = readMessege();
					for (int i=0;i<friends.size();i++) {
						if(friends.get(i).code.equals(codeBuff)) {
							////System.out.println("SET_PASS_SERVER");
							friends.get(i).isPass=true;
							amountOfPass+=1;
							////System.out.println("SET_PASS_SERVER: "+amountOfPass);
						}
					}
					if(MainServer.typeServer==TypeServer.FOUR_PLAYER) {
						if(amountOfPass==friends.size()-1) {
							//System.out.println("SET_PASS_WORK_FOUR_PAYER");
							scene.setPass();
							game.leadingGame(2);
							game.leadingGame(4);
						}
					}
					else {
						if(amountOfPass==friends.size()) {
							//System.out.println("SET_PASS_WORK_THREE_PAYER");
							if(MainServer.typeServer==TypeServer.THREE_PLAYER) {
								scene.setPass();
								game.leadingGame(2);
								game.leadingGame(4);
							}
							else if(MainServer.typeServer==TypeServer.TWO_PLAYER){
								scene.setPass();
								game.leadingGame(4);
							}
						}
					} 
					//sendMessege("MESSEGE_READ");
					break;
				case "YOU_LEADGAME":
					//System.out.println("YOU_LEADGAME_+WORK");
					leadingGame=true;
					youGiveCard=true;
					if(MainServer.typeServer!=TypeServer.TWO_PLAYER) {
						scene.borderButton.setVisible(true);
						scene.borderButton.addEventHandler(ActionEvent.ACTION, e->{
							sendMessege("TAKE_MUSIK");
							if(scene.borderCardsCards.size()!=0) {
								playerDeck.add(setCard(scene.borderCardsCards.get(0).sygnature));
								playerDeck.add(setCard(scene.borderCardsCards.get(1).sygnature));
								playerDeck.add(setCard(scene.borderCardsCards.get(2).sygnature));
								scene.addMusik(playerDeck.get(7),playerDeck.get(8),playerDeck.get(9));
								System.out.println(playerDeck.get(7).sygnature);
								System.out.println(playerDeck.get(8).sygnature);
								System.out.println(playerDeck.get(9).sygnature);
								sendMessege("YOU_TAKE_MUSIK");
								sendMessege(playerDeck.get(7).sygnature);
								sendMessege(playerDeck.get(8).sygnature);
								sendMessege(playerDeck.get(9).sygnature);
							}
							scene.borderCardsCards.clear();
							scene.borderCards.get(0).setImage(null);
							scene.borderCards.get(1).setImage(null);
							scene.borderCards.get(2).setImage(null);
							scene.borderButton.setVisible(false);
							for (int i=0;i<scene.playerCardsImage.size();i++) {
								scene.playerCardsImage.get(i).setOpacity(1);
							}
							setCardsButtons();
							addEventSendCards();
						});
					}
					else {
						//////System.out.println("YOU_LEADGAME_TWO_PLAYER");
						for (int i=0;i<scene.playerCardsImage.size();i++) {
							scene.playerCardsImage.get(i).setOpacity(1);
						}
						givenCards=2;
						setCardsButtons();
						addEventSendCards();
					}
					//sendMessege("MESSEGE_READ");
					break;
				case "TAKE_MUSIK":
					//////System.out.println("YOUR_TURN+WORK+CLIENT");
					scene.borderCards.get(0).setImage(null);
					scene.borderCards.get(1).setImage(null);
					scene.borderCards.get(2).setImage(null);
					scene.borderCardsCards.clear();
					//game.leadingGame(3);
					break;
				case "HAVE_MUSIK":
					String mess = readMessege(); 
					for (int i=0;i<friends.size();i++) {
						if(friends.get(i).code.equals(mess)) {
							friends.get(i).typePlayer=TypePlayer.MUSIK_PLAYER;
							friends.get(i).amountCard=0;
						}
					}
					//sendMessege("MESSEGE_READ");
					break;
				case "SEND_TWO_CARDS":{
						//System.out.println("SEND_TWO_CARDS");
						Cards card = setCard(readMessege());
						playerDeck.add(card);
						scene.addCard(card);
						break;
					}
				case "CARD_TO_BOARD":
					//System.out.println("CARD_TO_BOARD_WORK");
					String codeDealing=readMessege();
					Cards card = setCard(readMessege());
					if(game.deal==null) {
						game.leadingGame(5);
						game.deal.setClass(scene.levelOfPointNumber);
					}
					if(game.deal.first==null) {
						//System.out.println("check_CARD_TO_BOARD");
						game.deal.setClass(scene.levelOfPointNumber);
						String check = readMessege();
						scene.addBoard(card);
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								if(check.equals("IS_DISPATCH")) {
									//System.out.println("FIRST_RUN_CARD");
									game.deal.addFirstCards(card, codeDealing, true);
								}
								else {
									//System.out.println("FIRST_RUN_CARD");
									game.deal.addFirstCards(card, codeDealing, false);
								}
							}
						});
					}
					else if(game.deal.second==null){
						scene.addBoard(card);
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								game.deal.addSecondCards(card, codeDealing);
							}
						});
						//setSleep(1000);
					}
					else if(game.deal.third==null) {
						scene.addBoard(card);
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								game.deal.addThirdCards(card, codeDealing);
							}
						});
						//setSleep(1000);
					}
					if(firstCard==null) {
						firstCard=card;
						cardsOnBoard+=1;
					}
					else {
						cardsOnBoard+=1;
					}
					givenCards=2;
					//sendMessege("MESSEGE_READ");
					break;
				case "SET_TO_SEND":
					//System.out.println("SET_TO_SEND");
					youGiveCard=true;
					if(firstCard!=null) {
						ArrayList<Integer> cards=checkCards();
						System.out.println("SET_TO_SEND_: "+cards.size());
						if(cards.size()!=0) {
							for (int i=0;i<cards.size();i++) {
								scene.playerCardsImage.get(cards.get(i)).setOpacity(1);
							}
						}
						else {
							for (int i=0;i<scene.playerCardsImage.size();i++) {
								scene.playerCardsImage.get(i).setOpacity(1);
							}
						}
					}
					else {
						for (int i=0;i<scene.playerCardsImage.size();i++) {
							scene.playerCardsImage.get(i).setOpacity(1);
						}
					}
					setCardsButtons();
					addEventSendCards();
					//sendMessege("MESSEGE_READ");
					break;	
				case "YOU_WIN":
					pointsInOneDeal+=Integer.parseInt(readMessege());
					scene.levelOfPointsInOneDeal.setText(String.valueOf(pointsInOneDeal));
					System.out.println(scene.levelOfPointsInOneDeal.getText());
					//sendMessege("MESSEGE_READ");
					break;
				case "END_ONE_DEALING":
					//System.out.println("END_ONE_DEALING_WORK");
					setSleep(2000);
					firstCard=null;
					cardsOnBoard=0;
					scene.borderCards.get(0).setImage(null);
					scene.borderCards.get(1).setImage(null);
					scene.borderCards.get(2).setImage(null);
					cardToGive=null;
					//sendMessege("MESSEGE_READ");
					break;
				case "END_ROUND":
					amountOfPass=0;
					endRound();
					//sendMessege("MESSEGE_READ");
					break;
				case "LEADING_IS":
					String codeDealing1=readMessege();
					//System.out.println(code+": equals");
					if(codeDealing1.equals(code)) {
						scene.playerDeling.setText("LEADING IS: YOU");
					}
					else {
						for (int i=0;i<friends.size();i++) {
							if(friends.get(i).code.equals(codeDealing1)) {
								scene.playerDeling.setText("LEADING IS: "+friends.get(i).name);
							}
						}
					}
					//sendMessege("MESSEGE_READ");
					break;
				case "SET_DISPATCH":
				//	System.out.println("SET_DISPATCH+work");
					String patch = readMessege();
					if(patch.equals("PIK")) {
						scene.typeDispatch.setText("PIK");
						dispatch=Pairs.PIK_DISPATCH;
					}
					else if(patch.equals("CARO")) {
						scene.typeDispatch.setText("CARO");
						dispatch=Pairs.CARO_DISPATCH;
					}
					else if(patch.equals("TREFL")) {
						scene.typeDispatch.setText("TREFL");
						dispatch=Pairs.TREFL_DISPATCH;
					}
					else if(patch.equals("KIER")) {
						scene.typeDispatch.setText("KIER");
						dispatch=Pairs.KIER_DISPATCH;
					}
					//System.out.println("SEND_MESSEGE_READ");
					//sendMessege("MESSEGE_READ");
					break;
				case "ADD_DISPATCH_POINT":
					///System.out.println("ADD_DISPATCH_POINT+work");
					if(dispatch==Pairs.PIK_DISPATCH) {
						pointsInOneDeal+=40;
					}
					else if(dispatch==Pairs.TREFL_DISPATCH) {
						pointsInOneDeal+=60;
					}
					else if(dispatch==Pairs.CARO_DISPATCH) {
						pointsInOneDeal+=80;
					}
					else if(dispatch==Pairs.KIER_DISPATCH) {
						pointsInOneDeal+=100;
					}
					scene.levelOfPointsInOneDeal.setText(String.valueOf(pointsInOneDeal));
					///System.out.println("SEND_MESSEGE_READ");
					//sendMessege("MESSEGE_READ");
					break;
				case "END_GAME":
					setSleep(2000);
					endGame();
					break;
				}
			}
		}
	}
	
	class ReadDataClient extends Thread{
		boolean work=true;
		public void run() {
			String messege;
			while (work) {
				//System.out.println(socket.isConnected()+"WORK");
				if(!stage.isShowing())System.exit(0);
				System.out.println("CLIENT_MESSEGE_IS_WEATING");
				messege = readMessege();
				System.out.println("CLIENT_MESSEGE: "+messege);
				switch(messege) {
				case "SEND_CODE":
					code=readMessege();
					break;
				case "MESSEGE_READ":
					messegeWasRead=true;
					break;
				case "SEND_MUSIK":
					////System.out.println("START_MUSIK");
					takeMusik();
					//sendMessege("MESSEGE_READ");
					break;
				case "START_DECK_SEND":
					//////System.out.println("START_DECK");
					takeDeck(messege);
					System.out.println("START_SEND_DECK_SIZE_PLAYER_CARDS:"+ playerDeck.size());
					//sendMessege("MESSEGE_READ");
					break;
				case "START_OTHER_NICKNAME":
					//////System.out.println("START_OTHER_NICKNAME");
					takeNickName();
					sendMessege("MESSEGE_READ");
					break;
				case "START_TYPE_SERVER":
					////System.out.println("START_TYPE_SERVER");
					readTypeServer();
					//sendMessege("MESSEGE_READ");
					break;
				case "CHANGE_READINESS":
					////System.out.println("CHANGE_READINESS CLIENT: "+userName);
					readReadiness();
					//sendMessege("MESSEGE_READ");
					break;
				case "START_GAME":
					startGame();
					//sendMessege("MESSEGE_READ");
					break;
				case "CHANGE_LEVEL_OF_POINTS":
					//System.out.println("CHANGE_LEVEL_OF_POINTS");
					scene.levelOfPointNumber+=5;
					scene.levelOfPointsMin.setText(String.valueOf(scene.levelOfPointNumber));
					//sendMessege("MESSEGE_READ");
					break;
				case "YOUR_TURN":
					//System.out.println("YOUR_TURN");
					scene.setButtonsAndImages();
					//sendMessege("MESSEGE_READ");
					break;	
				case "YOU_LEADGAME":
					System.out.println("YOU_LEADGAME+WORK");
					leadingGame=true;
					youGiveCard=true;
					if(MainServer.typeServer!=TypeServer.TWO_PLAYER) {
						scene.borderButton.setVisible(true);
						scene.borderButton.addEventHandler(ActionEvent.ACTION, e->{
							System.out.println("YOU_LEADGAME+event");
							sendMessege("TAKE_MUSIK");
							System.out.println("YOU_LEADGAME_SIZE_PLAYER_CARDS:"+ playerDeck.size());
							if(scene.borderCardsCards.size()!=0) {
								playerDeck.add(setCard(scene.borderCardsCards.get(0).sygnature));
								playerDeck.add(setCard(scene.borderCardsCards.get(1).sygnature));
								playerDeck.add(setCard(scene.borderCardsCards.get(2).sygnature));
								scene.addMusik(playerDeck.get(7),playerDeck.get(8),playerDeck.get(9));
								System.out.println(playerDeck.get(7).sygnature);
								System.out.println(playerDeck.get(8).sygnature);
								System.out.println(playerDeck.get(9).sygnature);
								sendMessege("YOU_TAKE_MUSIK");
								sendMessege(playerDeck.get(7).sygnature);
								sendMessege(playerDeck.get(8).sygnature);
								sendMessege(playerDeck.get(9).sygnature);
							}
							scene.borderCardsCards.clear();
							scene.borderCards.get(0).setImage(null);
							scene.borderCards.get(1).setImage(null);
							scene.borderCards.get(2).setImage(null);
							System.out.println("YOU_LEADGAME_SIZE_PLAYER_CARDS_SCENE:"+ scene.playerCards.size());
							scene.borderButton.setVisible(false);
							for (int i=0;i<scene.playerCardsImage.size();i++) {
								scene.playerCardsImage.get(i).setOpacity(1);
							}
							setCardsButtons();
							addEventSendCards();
						});
					}
					else {
						//////System.out.println("YOU_LEADGAME_TWO_PLAYER");
						for (int i=0;i<scene.playerCardsImage.size();i++) {
							scene.playerCardsImage.get(i).setOpacity(1);
						}
						givenCards=2;
						setCardsButtons();
						addEventSendCards();
					}
					//sendMessege("MESSEGE_READ");
					break;
				case "TAKE_MUSIK":
					//System.out.println("YOUR_TURN+WORK+CLIENT");
					scene.borderCards.get(0).setImage(null);
					scene.borderCards.get(1).setImage(null);
					scene.borderCards.get(2).setImage(null);
					scene.borderCardsCards.clear();
					break;
				case "HAVE_MUSIK":
					String mess=readMessege();
					for (int i=0;i<friends.size();i++) {
						if(friends.get(i).code.equals(mess)) {
							friends.get(i).typePlayer=TypePlayer.MUSIK_PLAYER;
							friends.get(i).amountCard=0;
						}
					}
					//sendMessege("MESSEGE_READ");
					break;
				case "SEND_TWO_CARDS":
					//System.out.println("SEND_TWO_CARDS");
					Cards card = setCard(readMessege());
					playerDeck.add(card);
					scene.addCard(card);
					//sendMessege("MESSEGE_READ");
					break;
				case "CARD_TO_BOARD":
					//System.out.println("CARD_TO_BOARD+CLIENT");
					Cards card1 = setCard(readMessege());
					if(firstCard==null) {
						firstCard=card1;
						cardsOnBoard=+1;
					}
					else {
						cardsOnBoard+=1;
					} 
					//setSleep(1000);
					scene.addBoard(card1);
					givenCards=2;
					//sendMessege("MESSEGE_READ");
					break;
				case "SET_TO_SEND":
					//System.out.println("SET_TO_SEND");
					youGiveCard=true;
					if(firstCard!=null) {
						ArrayList<Integer> cards=checkCards();
						System.out.println("SET_TO_SEND_: "+cards.size());
						if(cards.size()!=0) {
							for (int i=0;i<cards.size();i++) {
								scene.playerCardsImage.get(cards.get(i)).setOpacity(1);
							}
						}
						else {
							for (int i=0;i<scene.playerCardsImage.size();i++) {
								scene.playerCardsImage.get(i).setOpacity(1);
							}
						}
					}
					else {
						for (int i=0;i<scene.playerCardsImage.size();i++) {
							scene.playerCardsImage.get(i).setOpacity(1);
						}
					}
					setCardsButtons();
					addEventSendCards();
					//sendMessege("MESSEGE_READ");
					break;
				case "YOU_WIN":
					pointsInOneDeal+=Integer.parseInt(readMessege());
					scene.levelOfPointsInOneDeal.setText(String.valueOf(pointsInOneDeal));
					System.out.println(scene.levelOfPointsInOneDeal.getText());
					//sendMessege("MESSEGE_READ");
					break;
				case "END_ONE_DEALING":
					//System.out.println("END_ONE_DEALING_WORK");
					setSleep(2000);
					firstCard=null;
					cardsOnBoard=0;
					scene.borderCards.get(0).setImage(null);
					scene.borderCards.get(1).setImage(null);
					scene.borderCards.get(2).setImage(null);
					cardToGive=null;
					//sendMessege("MESSEGE_READ");
					break;
				case "LEADING_IS":
					String codeDealing=readMessege();
					//System.out.println(code+": equals");
					if(codeDealing.equals(code)) {
						scene.playerDeling.setText("LEADING IS: YOU");
					}
					else {
						for (int i=0;i<friends.size();i++) {
							if(friends.get(i).code.equals(codeDealing)) {
								scene.playerDeling.setText("LEADING IS: "+friends.get(i).name);
							}
						}
					}
					//sendMessege("MESSEGE_READ");
					break;
				case "END_ROUND":
					endRound();
					//sendMessege("MESSEGE_READ");
					break;
				case "SET_DISPATCH":
					//System.out.println("SET_DISPATCH+work");
					String patch = readMessege();
					if(patch.equals("PIK")) {
						scene.typeDispatch.setText("PIK");
						dispatch=Pairs.PIK_DISPATCH;
					}
					else if(patch.equals("CARO")) {
						scene.typeDispatch.setText("CARO");
						dispatch=Pairs.CARO_DISPATCH;
					}
					else if(patch.equals("TREFL")) {
						scene.typeDispatch.setText("TREFL");
						dispatch=Pairs.TREFL_DISPATCH;
					}
					else if(patch.equals("KIER")) {
						scene.typeDispatch.setText("KIER");
						dispatch=Pairs.KIER_DISPATCH;
					}
					//System.out.println("SEND_MESSEGE_READ");
					//sendMessege("MESSEGE_READ");
					break;
				case "ADD_DISPATCH_POINT":
				//	System.out.println("ADD_DISPATCH_POINT+work");
					if(dispatch==Pairs.PIK_DISPATCH) {
						pointsInOneDeal+=40;
					}
					else if(dispatch==Pairs.TREFL_DISPATCH) {
						pointsInOneDeal+=60;
					}
					else if(dispatch==Pairs.CARO_DISPATCH) {
						pointsInOneDeal+=80;
					}
					else if(dispatch==Pairs.KIER_DISPATCH) {
						pointsInOneDeal+=100;
					}
					scene.levelOfPointsInOneDeal.setText(String.valueOf(pointsInOneDeal));
				//	System.out.println("SEND_MESSEGE_READ");
					//sendMessege("MESSEGE_READ");
					break;
				case "END_GAME":
					setSleep(2000);
					endGame();
					break;
				}
			}
		}
	}
}
