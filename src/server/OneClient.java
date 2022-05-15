package server;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

import engine.Cards;
import enums.TypeClient;
import enums.TypePlayer;
import enums.TypeServer;
import javafx.application.Platform;
import javafx.stage.Stage;

public class OneClient {
	//MulticastSocket socket;
	Socket socket;
	String userName;
	int pointsInOneDeal=0;
	int pointInGame=0;
	boolean isReady=false;
	boolean isPass=false;
	boolean startAuction=false;
	boolean leadingGame=false;
	boolean messegeWasRead=false;
	BufferedReader bufferedReader;
	BufferedWriter bufferedWriter;
	public TypePlayer typePlayer;
	TypeClient typeClient;
	ArrayList<Cards> playerDeck = new ArrayList<>();
	ArrayList<Cards> musik = new ArrayList<>();
	public Cards cardOnBoard;
	static ArrayList<OneClient> clients = new ArrayList<>();
	public String code;
	int orderInRoom;
	int givenCards=0;
	Stage stage;
	MainServer server;
	ReadData readData;
	CheckWorking checkWorking;
	
	OneClient(Socket socket,int orderInRoom,Stage stage,MainServer server){
		clients.add(this);
		this.orderInRoom=orderInRoom;
		this.socket=socket;
		this.server=server;
		this.stage=stage;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			System.out.println("Conection lose!!!-OneClient");
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					try {
						bufferedReader.close();
						bufferedWriter.close();
						socket.close();
						System.exit(0);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			e.printStackTrace();
		}
		sendTypeServer();
		readData = new ReadData();
		readData.start();	
		checkWorking = new CheckWorking();
		checkWorking.start();
	}
	
	void sendCode() {
		sendMessege("SEND_CODE");
		sendMessege(code);
	}
	
	class CheckWorking extends Thread{
		public void run() {
			while (stage.isShowing()) {
				setSleep(1);
			}
			System.exit(0);
		}
	}
	
	void sendDealTowCards() {
		this.sendMessege("GIVE_TOW_CARDS");
	}
	
	public void setCards(ArrayList<Cards> cards,TypePlayer type,boolean startAuction) {
		typePlayer=type;
		playerDeck=cards;
		this.startAuction=startAuction;
	}

	void readNickName() {
		String mess;
		mess= readMessege();
		System.out.println("readNickName: "+mess);
		String messIn;
		switch(mess) {
		case "CASUAL_PLAYER":
			typeClient=TypeClient.CASUAL_CLIENT;
			messIn= readMessege();
			userName=messIn;
			code=userName+String.valueOf(clients.size()*1000);
			break;
		case "SERVER_PLAYER":
			typeClient=TypeClient.SERVER_CLIENT;
			messIn= readMessege();
			userName=messIn;
			code=userName+String.valueOf(1000);
			break;
		}
		sendCode();
	}
	
	public void sendMessege(String mess) {
		try {
		this.bufferedWriter.write(mess);
		this.bufferedWriter.newLine();
		this.bufferedWriter.flush();
		}catch (IOException e) {
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
	
	void sendNickName() {
		System.out.println("SEND_NICK_SERVER-WORK");
		for (int i=0;i<clients.size()-1;i++) {
			////////System.out.println("SEND_NICK_SERVER-WORK-SECOND"+this.userName);
			this.sendMessege("START_OTHER_NICKNAME");
			if(clients.get(i).typeClient==TypeClient.CASUAL_CLIENT) {
				////////System.out.println("SEND_NICK_SERVER-WORK-SECOND"+this.userName+"CASUAL");
				this.sendMessege("CASUAL_PLAYER");
				this.sendMessege(clients.get(i).userName);
				this.sendMessege(clients.get(i).code);
			}
			else {
				////////System.out.println("SEND_NICK_SERVER-WORK-SECOND"+this.userName+"SERVER");
				this.sendMessege("SERVER_PLAYER");
				this.sendMessege(clients.get(i).userName);
				this.sendMessege(clients.get(i).code);
			}
			System.out.println("is-weaing-sendNickName-first-oneclient");
			readMessege();
			this.sendMessege("CHANGE_READINESS");
			this.sendMessege(clients.get(i).code);
			if(clients.get(i).isReady) {
				this.sendMessege("READY");
			}
			else {
				this.sendMessege("NOT_READY");
			}
		}
		for(int i=0;i<clients.size()-1;i++) {
			////////System.out.println("SEND_NICK_SERVER-WORK-FIRST"+this.userName);
			clients.get(i).sendMessege("START_OTHER_NICKNAME");
			if(this.typeClient==TypeClient.CASUAL_CLIENT) {
				////////System.out.println("SEND_NICK_SERVER-WORK-FIRST"+this.userName+"CASUAL");
				clients.get(i).sendMessege("CASUAL_PLAYER");
				clients.get(i).sendMessege(this.userName);
				clients.get(i).sendMessege(this.code);
			}
			else {
				////////System.out.println("SEND_NICK_SERVER-WORK-FIRST"+this.userName+"SERVER");
				clients.get(i).sendMessege("SERVER_PLAYER");
				clients.get(i).sendMessege(this.userName);
				clients.get(i).sendMessege(this.code);
			}
		}
	}
	
	void setSleep(int x) {
		try {
			Thread.sleep(x);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	void sendMusik(ArrayList<Cards>musik) {
		//////System.out.println("sendMusik-oneClient");
		this.musik=musik;
		this.sendMessege("SEND_MUSIK");
		for (int i=0;i<musik.size();i++) {
			this.sendMessege(musik.get(i).sygnature);
		}
		this.sendMessege("STOP");
	} 
	
	void sendTypeServer() {
		////////System.out.println("START_TYPE_SERVER");
		if(this.orderInRoom!=0) {
			this.sendMessege("START_TYPE_SERVER");
			switch(MainServer.typeServer) {
			case TWO_PLAYER:
				this.sendMessege("TWO");
				break;
			case THREE_PLAYER:
				this.sendMessege("THREE");
				break;
			case FOUR_PLAYER:
				this.sendMessege("FOUR");
				break;
			default:
				;	
			}
		}
	}
	
	void sendDeckToPlayer() {
		System.out.println("sendDeckToPlayer "+this.userName);
		this.sendMessege("START_DECK_SEND");
		System.out.println(playerDeck.size());	
		if(typePlayer==TypePlayer.NORMAL_PLAYER) {
			//////System.out.println("sendDeckToPlayer "+this.userName+" NORMAL_PLAYER");
			this.sendMessege("NORMAL_PLAYER");
			for (int i=0;i<playerDeck.size();i++) {
				if(playerDeck.get(i).sygnature==null) {
					//////System.out.println("sendDeckToPlayer+null");
				}
				if(this.bufferedWriter==null) {
					//////System.out.println("sendDeckToPlayer+bufferedWriter+null");
				}
				this.sendMessege(playerDeck.get(i).sygnature);
			}
			this.sendMessege("STOP");
			if(this.startAuction) {
				this.sendMessege("TRUE");
			}
			else {
				this.sendMessege("FALSE");
			}
			if(MainServer.typeServer==TypeServer.FOUR_PLAYER) {
				for (int i=0;i<clients.size();i++) {
					if(clients.get(i)!=this) {
						if(clients.get(i).typePlayer==TypePlayer.MUSIK_PLAYER) {
							this.sendMessege("HAVE_MUSIK");
							this.sendMessege(clients.get(i).code);
						}
					}
				}
			}
		}
		else {
			//////System.out.println("sendDeckToPlayer "+this.userName+" MUSIK_PLAYER");
			this.sendMessege("MUSIK_PLAYER");
		}
	}
	
	void readReadiness() {
		////////System.out.println("WORK");
		String mess=readMessege();
		////////System.out.println("WORK"+mess);
		switch(mess) {
		case "NOT_READY":
			isReady=false;
			////////System.out.println("WORK_NOT_READY");
			//clients.get(clients.indexOf(this)).isReady=false;
			for (int i=0;i<clients.size();i++) {
				if(clients.get(i)!=this) {
					clients.get(i).sendMessege("CHANGE_READINESS");
					clients.get(i).sendMessege(this.code);
					clients.get(i).sendMessege("NOT_READY");
				}
			}
			break;
		case "READY":
			isReady=true;
			////////System.out.println("WORK_READY");
			//clients.get(clients.indexOf(this)).isReady=true;
			for (int i=0;i<clients.size();i++) {
				if(clients.get(i)!=this) {
					clients.get(i).sendMessege("CHANGE_READINESS");
					clients.get(i).sendMessege(this.code);
					clients.get(i).sendMessege("READY");
				}
			}
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
	
	String readMessege() {
		String mess;
		try {
			mess = bufferedReader.readLine();
			return mess;
		} catch (IOException e) {
			System.out.println("END_WORK_SERVER");
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
	
	void sendChangeLevelOfPoints() {
		for (int i=0;i<clients.size();i++) {
			if(clients.get(i)!=this) {
				////////System.out.println("sendChangeLevelOfPoints+OneClient to :"+clients.get(i).userName);
				clients.get(i).sendMessege("CHANGE_LEVEL_OF_POINTS");
			}
		}
		int i=clients.indexOf(this);
		while (true) {
			++i;
			if(i>=clients.size())i=0;
			if((!clients.get(i).isPass&&MainServer.typeServer!=TypeServer.FOUR_PLAYER)||
				(!clients.get(i).isPass&&clients.get(i).typePlayer==TypePlayer.NORMAL_PLAYER)) {
				clients.get(i).sendMessege("YOUR_TURN");
				break;
			}
		}
	}
	
	public void sendGiveTowCards() {
		this.sendMessege("GIVE_TWO_CARDS");
	}
	
	public void setButtonCards() {
		this.sendMessege("SET_BUTTON");
	}
	
	class ReadData extends Thread{
		boolean work=true;
		public void run() {
			String messege;
			while (work) {
				if(!stage.isShowing())System.exit(0);
				System.out.println(" :SERVER_MESSEGE_IS_WEATING"+clients.get(orderInRoom).code);
				messege = readMessege();
				System.out.println(messege+" :SERVER_MESSEGE");
				switch(messege) {
				case "MESSEGE_READ":
					messegeWasRead=true;
					break;
				case "SEND_NAME":
					readNickName();
					sendMessege("MESSEGE_READ");
					sendNickName();
					break;
				case "SET_READINESS":
					readReadiness();
					//sendMessege("MESSEGE_READ");
					break;
				case "START_GAME":
					for (int i=1;i<clients.size();i++) {
						clients.get(i).sendMessege("START_GAME");
					}
					//sendMessege("MESSEGE_READ");
					break;
				case "CHANGE_LEVEL_OF_POINTS":
					sendChangeLevelOfPoints();
					//sendMessege("MESSEGE_READ");
					break;
				case "SET_PASS":{
						//System.out.println("SET_PASS_SERVER_WORK");
						isPass=true;
						//System.out.println("SET_PASS");
						clients.get(0).sendMessege("SET_PASS");
						clients.get(0).sendMessege(code);
						if(MainServer.typeServer!=TypeServer.TWO_PLAYER) {
							int i=orderInRoom;
							//System.out.println("READDATE_RUN_SET_PASS_FIRST: "+i);
							while (true) {
								++i;
								////System.out.println("READDATE_RUN_SET_PASS_SECOND: "+i);
								if(i>=clients.size())i=0;
								if((!clients.get(i).isPass&&MainServer.typeServer!=TypeServer.FOUR_PLAYER)||
										(!clients.get(i).isPass&&clients.get(i).typePlayer==TypePlayer.NORMAL_PLAYER)) {
									////////System.out.println("READDATE_RUN_SET_PASS: "+i);
									clients.get(i).sendMessege("YOUR_TURN");
									break;
								}
							}
						} 
						//sendMessege("MESSEGE_READ");
						break;
					}
				case "TAKE_MUSIK":
					for (int i=0;i<clients.size();i++) {
						if(clients.get(i)!=clients.get(orderInRoom)) {
							clients.get(i).sendMessege("TAKE_MUSIK");
						}
					}
					//sendMessege("MESSEGE_READ");
					break;
				case "YOU_TAKE_MUSIK":
					playerDeck.add(setCard(readMessege()));
					playerDeck.add(setCard(readMessege()));
					playerDeck.add(setCard(readMessege()));
					break;
				case "SEND_TWO_CARDS":
					////////System.out.println("SEND_TWO_CARDS"+" SERVER_WORK");
					String mess = readMessege();
					System.out.println(mess+ "SEND_TO_CARD");
					for (int i=0;i<clients.size();i++) {
						if(clients.get(i).code.equals(mess)){
							////////System.out.println("SEND_TWO_CARDS"+" SERVER_WORK"+"_SENDINFO");
							clients.get(i).sendMessege("SEND_TWO_CARDS");
							Cards card = setCard(readMessege());
							for (int j=0;j<playerDeck.size();j++) {
								if(playerDeck.get(j).sygnature.equals(card.sygnature)) {
									playerDeck.get(j).setUsed();
									j=playerDeck.size();
								}
							}
							clients.get(i).playerDeck.add(card);
							clients.get(i).sendMessege(card.sygnature);
						}
					}
					//sendMessege("MESSEGE_READ");
					break;
				case "CARD_TO_BOARD":
					//System.out.println("CART_TO_BOARD"+" SERVER_WORK"+"_SENDINFO");
					String messs=readMessege();
					if(messs.equals("NOT_FROM_SERVER")) {
						//System.out.println("NOT_FROM_SERVER"+" WORK");
						String card = readMessege();
						System.out.println("Sygnateure card: "+card);
						cardOnBoard=setCard(card);
						for(int i=0;i<playerDeck.size();i++) {
							System.out.println(i);
							if(playerDeck.get(i).sygnature.equals(cardOnBoard.sygnature)) {
								playerDeck.get(i).setUsed();
								i=playerDeck.size();
							}
						}
						clients.get(0).sendMessege("CARD_TO_BOARD");
						clients.get(0).sendMessege(clients.get(orderInRoom).code);
						clients.get(0).sendMessege(cardOnBoard.sygnature);
						String isDispatch=readMessege();
						System.out.println(isDispatch);
						clients.get(0).sendMessege(isDispatch);
						System.out.println(cardOnBoard.sygnature+" :cardOnBoard.sygnature");
						System.out.println(playerDeck.size());
						for (int i=1;i<clients.size();i++) {
							if(clients.get(i)!=clients.get(orderInRoom)) {
								clients.get(i).sendMessege("CARD_TO_BOARD");
								clients.get(i).sendMessege(cardOnBoard.sygnature);
							}
						}
					}
					else {
						//System.out.println("FROM_SERVER"+" WORK");
						cardOnBoard=setCard(readMessege());
						System.out.println(cardOnBoard.sygnature+" :cardOnBoard.sygnature");
						for(int i=0;i<playerDeck.size();i++) {
							if(playerDeck.get(i).sygnature.equals(cardOnBoard.sygnature)) {
								playerDeck.get(i).setUsed();
								i=playerDeck.size();
							}
						}						
						for (int i=1;i<clients.size();i++) {
							//System.out.println(i+"FIRST");
							if(clients.get(i)!=clients.get(orderInRoom)) {
								clients.get(i).sendMessege("CARD_TO_BOARD");
								clients.get(i).sendMessege(cardOnBoard.sygnature);
							}
						}
					}
					break;
				}
			}
		}
	}
}
