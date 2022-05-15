package server;

import java.util.ArrayList;
import java.util.Random;

import engine.Cards;
import enums.DealingPlayer;
import enums.Pairs;
import enums.TypePlayer;
import enums.TypeServer;

public class Game {
	final Cards ninePik = new Cards(0,"pik","nine","PN","/resources/NinePik.jpg");//PN
	final Cards tenPik = new Cards(10,"pik","ten","PT","/resources/TenPik.jpg");//PT
	final Cards asPik = new Cards(11,"pik","as","PA","/resources/AsPik.jpg");//PA
	final Cards quenePik = new Cards(3,"pik","quene","PQ","/resources/QueenPik.jpg");//PQ
	final Cards kingPik = new Cards(4,"pik","king","PK","/resources/KingPik.jpg");//PK
	final Cards jackPik = new Cards(2,"pik","jack","PJ","/resources/JackPik.jpg");//PJ
	final Cards nineCaro = new Cards(0,"caro","nine","CN","/resources/NineKaro.jpg");//CN
	final Cards tenCaro = new Cards(10,"caro","ten","CT","/resources/TenKaro.jpg");//CT
	final Cards asCaro = new Cards(11,"caro","as","CA","/resources/AsKaro.jpg");//CA
	final Cards queneCaro = new Cards(3,"caro","quene","CQ","/resources/QueenKaro.jpg");//CQ
	final Cards kingCaro = new Cards(4,"caro","king","CK","/resources/KingKaro.jpg");//CK
	final Cards jackCaro = new Cards(2,"caro","jack","CJ","/resources/JackKaro.jpg");//CJ
	final Cards nineTrefl = new Cards(0,"trefl","nine","TN","/resources/NineTrefl.jpg");//TN
	final Cards tenTrefl = new Cards(10,"trefl","ten","TT","/resources/TenTrefl.jpg");//TT
	final Cards asTrefl = new Cards(11,"trefl","as","TA","/resources/AsTrefl.jpg");//TA
	final Cards queneTrefl = new Cards(3,"trefl","quene","TQ","/resources/QueenTrefl.jpg");//TQ
	final Cards kingTrefl = new Cards(4,"trefl","king","TK","/resources/KingTrefl.jpg");//TK
	final Cards jackTrefl = new Cards(2,"trefl","jack","TJ","/resources/JackTrefl.jpg");//TJ
	final Cards nineKier = new Cards(0,"kier","nine","KN","/resources/NineKier.jpg");//KN
	final Cards tenKier = new Cards(10,"kier","ten","KT","/resources/TenKier.jpg");//KT
	final Cards asKier = new Cards(11,"kier","as","KA","/resources/AsKier.jpg");//KA
	final Cards queneKier = new Cards(3,"kier","quene","KQ","/resources/QueenKier.jpg");//KQ
	final Cards kingKier = new Cards(4,"kier","king","KK","/resources/KingKier.jpg");//KK
	final Cards jackKier = new Cards(2,"kier","jack","KJ","/resources/JackKier.jpg");//KJ
	
	public OneDealing deal;
	
	ArrayList<Cards> deck;
	ArrayList<Cards> firstMusik;
	ArrayList<Cards> firstPlayer;
	ArrayList<Cards> secondPlayer;
	ArrayList<Cards> thirdPlayer;
	ArrayList<Cards> fourthPlayer;
	
	TypeServer typeServer = MainServer.typeServer;
	DealingPlayer dealingPlayer=DealingPlayer.FIRST;

	int amountPlayers;

	public ArrayList<OneClient> players = new ArrayList<>(); 
	
	public Game() {
		deck = new ArrayList<>();
		players= new ArrayList<>();
		//////System.out.println(typeServer);
		if(typeServer==TypeServer.TWO_PLAYER) {
			firstPlayer = new ArrayList<>();
			secondPlayer = new ArrayList<>();
			firstMusik = new ArrayList<>();
			amountPlayers=2;
			
		}
		else if(typeServer==TypeServer.THREE_PLAYER){
			firstPlayer = new ArrayList<>();
			secondPlayer = new ArrayList<>();
			thirdPlayer = new ArrayList<>();
			firstMusik = new ArrayList<>();
			amountPlayers=3;
		}
		else if(typeServer==TypeServer.FOUR_PLAYER) {
			firstPlayer = new ArrayList<>();
			secondPlayer = new ArrayList<>();
			thirdPlayer = new ArrayList<>();
			fourthPlayer = new ArrayList<>();
			firstMusik = new ArrayList<>();
			dealingPlayer=DealingPlayer.FOURTH;
			amountPlayers=4;
		}
	}
	
	public void leadingGame(int x) {
		switch(x) {
		case 1:
			//System.out.println("leadingGame 1"+deck.size());
			dealingCards();
			break;
		case 2:
			//System.out.println("leadingGame,GAME,2");
			sendMusik();
			break;
		case 3:
			break;
		case 4:
			//System.out.println("leadingGame,GAME,4");
			int y=0;
			for (int i=0;i<players.size();i++) {
				if(MainServer.typeServer!=TypeServer.FOUR_PLAYER) {
					if(!players.get(i).isPass) {
						if (players.get(i).typePlayer==TypePlayer.NORMAL_PLAYER) {
							players.get(i).leadingGame=true;
							//System.out.println("setLeadingGame()+: "+players.get(i).userName+" is leading the game");
							//System.out.println(i);
							players.get(i).sendMessege("YOU_LEADGAME");
							y=i;
						}
					}
				}
				else if(MainServer.typeServer==TypeServer.FOUR_PLAYER) {
					if(!players.get(i).isPass) {
						if(players.get(i).typePlayer==TypePlayer.NORMAL_PLAYER) {
							players.get(i).leadingGame=true;
							//System.out.println("setLeadingGame()+: "+players.get(i).userName+" is leading the game");
							//System.out.println(i);
							players.get(i).sendMessege("YOU_LEADGAME");
							y=i;
						}
					}
				}
			}
			for (int i=0;i<players.size();i++) {
				players.get(i).sendMessege("LEADING_IS");
				players.get(i).sendMessege(players.get(y).code);
			}
			break;
		case 5:
			System.out.println("LEADINGGAME_5");
			deal = new OneDealing();
			break;
		}
	}
	
	void sendMusik() {
		if(MainServer.typeServer==TypeServer.THREE_PLAYER) {
			players.get(0).sendMusik(firstMusik);
			players.get(1).sendMusik(firstMusik);
			players.get(2).sendMusik(firstMusik);
		}
		if(MainServer.typeServer==TypeServer.FOUR_PLAYER) {
			players.get(0).sendMusik(firstMusik);
			players.get(1).sendMusik(firstMusik);
			players.get(2).sendMusik(firstMusik);
			players.get(3).sendMusik(firstMusik);
		}
	}
	
	void addPlayers(ArrayList<OneClient>players) {
		//////System.out.println("addPlayers_WORK");
		this.players=players;
	} 
	
	void drowFourPlayer() {
		switch(dealingPlayer) {
		case FIRST:
			for (int i=0;i<3;i++) {
				firstMusik.add(drowCard());
			}
			for (int i=0;i<7;i++) {
				fourthPlayer.add(drowCard());
			}
			for (int i=0;i<7;i++) {
				secondPlayer.add(drowCard());
			}
			for (int i=0;i<7;i++) {
				thirdPlayer.add(drowCard());
			}
			break;
		case SECOND:
			for (int i=0;i<3;i++) {
				firstMusik.add(drowCard());
			}
			for (int i=0;i<7;i++) {
				fourthPlayer.add(drowCard());
			}
			for (int i=0;i<7;i++) {
				firstPlayer.add(drowCard());
			}
			for (int i=0;i<7;i++) {
				thirdPlayer.add(drowCard());
			}
			break;
		case THIRD:
			for (int i=0;i<3;i++) {
				firstMusik.add(drowCard());
			}
			for (int i=0;i<7;i++) {
				fourthPlayer.add(drowCard());
			}
			for (int i=0;i<7;i++) {
				firstPlayer.add(drowCard());
			}
			for (int i=0;i<7;i++) {
				secondPlayer.add(drowCard());
			}
			break;
		case FOURTH:
			for (int i=0;i<3;i++) {
				firstMusik.add(drowCard());
			}
			for (int i=0;i<7;i++) {
				thirdPlayer.add(drowCard());
			}
			for (int i=0;i<7;i++) {
				firstPlayer.add(drowCard());
			}
			for (int i=0;i<7;i++) {
				secondPlayer.add(drowCard());
			}
			break;
		}
	}
	void drowThreePlayer() {
		for (int i=0;i<3;i++) {
			firstMusik.add(drowCard());
		}
		for (int i=0;i<7;i++) {
			firstPlayer.add(drowCard());
			secondPlayer.add(drowCard());
			thirdPlayer.add(drowCard());
		}
		//printDeck(firstPlayer);
		//printDeck(secondPlayer);
		//printDeck(thirdPlayer);
	}
	void drowTwoPlayer() {
		for (int i=0;i<12;i++) {
			firstPlayer.add(drowCard());
			secondPlayer.add(drowCard());
		}
		//printDeck(firstPlayer);
		//printDeck(secondPlayer);	
	}
	
	void addCardsToDeck() {
		deck.clear();
		deck.add(ninePik);
		deck.add(tenPik);
		deck.add(quenePik);
		deck.add(kingPik);
		deck.add(asPik);
		deck.add(jackPik);
		deck.add(nineCaro);
		deck.add(tenCaro);
		deck.add(queneCaro);
		deck.add(kingCaro);
		deck.add(asCaro);
		deck.add(jackCaro);
		deck.add(nineKier);
		deck.add(tenKier);
		deck.add(queneKier);
		deck.add(kingKier);
		deck.add(asKier);
		deck.add(jackKier);
		deck.add(nineTrefl);
		deck.add(tenTrefl);
		deck.add(queneTrefl);
		deck.add(kingTrefl);
		deck.add(asTrefl);
		deck.add(jackTrefl);
		System.out.println("addCardsToDeck-work");
		for (int i=0;i<deck.size();i++) {
			deck.get(i).setNotUsed();
		}
	}
	ArrayList<Cards> shufflingDeck() {
		ArrayList<Cards> buffor = new ArrayList<>();
		Random rand = new Random();
		while (deck.size()>0) {
			int randInt=rand.nextInt(deck.size());
			buffor.add(deck.get(randInt));
			deck.remove(randInt);
		}
		printDeck(buffor);
		System.out.println("BUffor.Size-shufflingDeck"+buffor.size());
		return buffor;
	}
	void dealingCards() {
		//////System.out.println("DEALINGCARDS_GAME");
		switch(typeServer) {
		case TWO_PLAYER:{
				addCardsToDeck();
				deck= shufflingDeck();
				//////System.out.println("dealingCards+TWO_PLAYER+deck.SIZE"+deck.size());
				firstPlayer.clear();
				secondPlayer.clear();
				drowTwoPlayer();
				if(dealingPlayer==DealingPlayer.FIRST) {
					players.get(0).setCards(firstPlayer, TypePlayer.NORMAL_PLAYER,true);
					players.get(1).setCards(secondPlayer, TypePlayer.NORMAL_PLAYER,false);
				}
				else {
					players.get(0).setCards(firstPlayer, TypePlayer.NORMAL_PLAYER,false);
					players.get(1).setCards(secondPlayer, TypePlayer.NORMAL_PLAYER,true);
				}
				//////System.out.println("dealingCards+TWO_PLAYER+FIRST_PLAYER.SIZE"+firstPlayer.size());
				//////System.out.println("dealingCards+TWO_PLAYER+SECOND_PLAYER.SIZE"+secondPlayer.size());
				players.get(0).sendDeckToPlayer();
				players.get(1).sendDeckToPlayer();
				break;
			}
		case THREE_PLAYER:{
				addCardsToDeck();
				deck= shufflingDeck();
				//////System.out.println("WORK_THREE_PLAYERS"+deck.size());
				firstPlayer.clear();
				secondPlayer.clear();
				thirdPlayer.clear();
				firstMusik.clear();
				drowThreePlayer();
				if(dealingPlayer==DealingPlayer.FIRST) {
					players.get(0).setCards(firstPlayer, TypePlayer.NORMAL_PLAYER,false);
					players.get(1).setCards(secondPlayer, TypePlayer.NORMAL_PLAYER,false);
					players.get(2).setCards(thirdPlayer, TypePlayer.NORMAL_PLAYER,true);
				}
				else if (dealingPlayer==DealingPlayer.SECOND){
					players.get(0).setCards(firstPlayer, TypePlayer.NORMAL_PLAYER,true);
					players.get(1).setCards(secondPlayer, TypePlayer.NORMAL_PLAYER,false);
					players.get(2).setCards(thirdPlayer, TypePlayer.NORMAL_PLAYER,false);
				}
				else if(dealingPlayer==DealingPlayer.THIRD) {
					players.get(0).setCards(firstPlayer, TypePlayer.NORMAL_PLAYER,false);
					players.get(1).setCards(secondPlayer, TypePlayer.NORMAL_PLAYER,true);
					players.get(2).setCards(thirdPlayer, TypePlayer.NORMAL_PLAYER,false);
				}		
				players.get(0).sendDeckToPlayer();
				players.get(1).sendDeckToPlayer();
				players.get(2).sendDeckToPlayer();
				break;
			}
		case FOUR_PLAYER:{
			addCardsToDeck();
			deck= shufflingDeck();
			////System.out.println("dealingCards+FOURTH_PLAYER+deck.SIZE"+deck.size());
			firstPlayer.clear();
			secondPlayer.clear();
			thirdPlayer.clear();
			fourthPlayer.clear();
			firstMusik.clear();
			drowFourPlayer();
			if(dealingPlayer==DealingPlayer.FIRST) {
				System.out.println("FOUR_PLAYER_FIRST_DEALING_CARDS");
				players.get(0).setCards(firstPlayer, TypePlayer.MUSIK_PLAYER,false);
				players.get(1).setCards(secondPlayer, TypePlayer.NORMAL_PLAYER,false);
				players.get(2).setCards(thirdPlayer, TypePlayer.NORMAL_PLAYER,true);
				players.get(3).setCards(fourthPlayer, TypePlayer.NORMAL_PLAYER,false);
			}
			else if(dealingPlayer==DealingPlayer.SECOND){
				System.out.println("FOUR_PLAYER_SECOND_DEALING_CARDS");
				players.get(0).setCards(firstPlayer, TypePlayer.NORMAL_PLAYER,false);
				players.get(1).setCards(secondPlayer, TypePlayer.MUSIK_PLAYER,false);
				players.get(2).setCards(thirdPlayer, TypePlayer.NORMAL_PLAYER,false);
				players.get(3).setCards(fourthPlayer, TypePlayer.NORMAL_PLAYER,true);
			}
			else if(dealingPlayer==DealingPlayer.THIRD) {
				System.out.println("FOUR_PLAYER_THIRD_DEALING_CARDS");
				players.get(0).setCards(firstPlayer, TypePlayer.NORMAL_PLAYER,true);
				players.get(1).setCards(secondPlayer, TypePlayer.NORMAL_PLAYER,false);
				players.get(2).setCards(thirdPlayer, TypePlayer.MUSIK_PLAYER,false);
				players.get(3).setCards(fourthPlayer, TypePlayer.NORMAL_PLAYER,false);
			}
			else if(dealingPlayer==DealingPlayer.FOURTH){
				System.out.println("FOUR_PLAYER_FOURTH_DEALING_CARDS");
				players.get(0).setCards(firstPlayer, TypePlayer.NORMAL_PLAYER,false);
				players.get(1).setCards(secondPlayer, TypePlayer.NORMAL_PLAYER,true);
				players.get(2).setCards(thirdPlayer, TypePlayer.NORMAL_PLAYER,false);
				players.get(3).setCards(fourthPlayer, TypePlayer.MUSIK_PLAYER,false);
			}
			players.get(0).sendDeckToPlayer();
			players.get(1).sendDeckToPlayer();
			players.get(2).sendDeckToPlayer();
			players.get(3).sendDeckToPlayer();
			break;
			}
		}
		addCardsToDeck();
	}
	Cards drowCard() {
		int randInt;
		Random rand = new Random();
		Cards returned;
		////System.out.println("DROWCARDS_DECK.SIZE: "+deck.size());
		if(deck.size()>0) {
			randInt = rand.nextInt(deck.size());
			//////System.out.println(randInt);
			returned = deck.get(randInt);
			deck.remove(randInt);
			return returned;
		}
		else {
			////System.out.println("RETURN_NULL_DROWCARDS");
			return null;
		}
	}
	
	void printDeck(ArrayList<Cards> deckFunc) {
		for (int i=0;i<deckFunc.size();i++) {
			System.out.print(deckFunc.get(i).sygnature+" ");
		}
		System.out.println("");
	}
	
	public class OneDealing {
		OneClient leading;
		Cards fromDealing;
		Cards fromSecond;
		Cards fromThird;
		public OneClient first;
		public OneClient second;
		public OneClient third;
		int levelOfPoint;
		Pairs pairInDealing = Pairs.NULL_DISPATCH;
		OneDealing(){
			;
		}
		
		public void setClass(int levelOfPoint) {
			this.levelOfPoint=levelOfPoint;
		}
		
		void clearBoard() {
			first.cardOnBoard=null;
			second.cardOnBoard=null;
			first=null;
			second=null;
			if(third!=null) {
				third.cardOnBoard=null;
				third=null;
			}
		}
		
		public void addDispatch(OneClient player,String sing) {
			//System.out.println("addDispatch+WORK");
			if(sing.equals("PQ")) {
				player.pointsInOneDeal+=40;
				pairInDealing=Pairs.PIK_DISPATCH;
				for (int i=0;i<players.size();i++) {
					System.out.println("addDispatch+WORK: "+i);
					players.get(i).sendMessege("SET_DISPATCH");
					players.get(i).sendMessege("PIK");
				}
				player.sendMessege("ADD_DISPATCH_POINT");
			}
			else if(sing.equals("CQ")) {
				player.pointsInOneDeal+=80;
				pairInDealing=Pairs.CARO_DISPATCH;
				for (int i=0;i<players.size();i++) {
					System.out.println("addDispatch+WORK: "+i);
					players.get(i).sendMessege("SET_DISPATCH");
					players.get(i).sendMessege("CARO");
				}
				player.sendMessege("ADD_DISPATCH_POINT");
			}
			else if(sing.equals("TQ")) {
				player.pointsInOneDeal+=60;
				pairInDealing=Pairs.TREFL_DISPATCH;
				for (int i=0;i<players.size();i++) {
					System.out.println("addDispatch+WORK: "+i);
					players.get(i).sendMessege("SET_DISPATCH");
					players.get(i).sendMessege("TREFL");
				}
				player.sendMessege("ADD_DISPATCH_POINT");
			}
			else if(sing.equals("KQ")) {
				player.pointsInOneDeal+=100;
				pairInDealing=Pairs.KIER_DISPATCH;
				for (int i=0;i<players.size();i++) {
					System.out.println("addDispatch+WORK: "+i);
					players.get(i).sendMessege("SET_DISPATCH");
					players.get(i).sendMessege("KIER");
				}
				player.sendMessege("ADD_DISPATCH_POINT");
			}
		}
		
		public void addFirstCards(Cards card,String player,boolean isDispatch) {
			System.out.println("FIRST: "+card.sygnature);
			for (int i=0;i<players.size();i++) {
				if(players.get(i).code.equals(player)) {
					first=players.get(i);
					if(leading==null) {
						leading=first;
					}
				}
			}
			fromDealing=card;
			if(isDispatch) {
				//System.out.println("IS_DIS_PATCH");
				addDispatch(first,card.sygnature);
			}
			int i;
			if(first.orderInRoom==players.size()-1) {
				i=0;
			}
			else {
				i=first.orderInRoom+1;
			}
			while (first.cardOnBoard==null) {
				setSleep(5);
			}
			while (true) {
				if(players.get(i).typePlayer==TypePlayer.NORMAL_PLAYER&&players.get(i).cardOnBoard==null) {
					//System.out.println("SEND_SET_TO_SEND "+players.get(i).code+" "+i);
					players.get(i).sendMessege("SET_TO_SEND");
					break;
				}
				i+=1;
				if(i==players.size()) {
					i=0;
				}
			}
			if(first.server==null) {
				first.cardOnBoard=null;
			}
		}
		
		public void addSecondCards(Cards card,String player) {
			System.out.println("SECOND: "+card.sygnature);
			fromSecond=card;
			for (int i=0;i<players.size();i++) {
				if(players.get(i).code.equals(player)) second=players.get(i);
			}
			if(MainServer.typeServer==TypeServer.TWO_PLAYER) {
				checkBoard();
			}
			else {
				int i;
				if(second.orderInRoom==players.size()-1) {
					i=0;
				}
				else {
					i=second.orderInRoom+1;
				}
				while (second.cardOnBoard==null) {
					setSleep(5);
				}
				second.cardOnBoard=null;
				while (true) {
					if(players.get(i).typePlayer==TypePlayer.NORMAL_PLAYER&&players.get(i).cardOnBoard==null) {
						//System.out.println("SEND_SET_TO_SEND "+players.get(i).code+" "+i);
						players.get(i).sendMessege("SET_TO_SEND");
						break;
					}
					i+=1;
					if(i==players.size()) {
						i=0;
					}
					
				}
			}
		}
		public void addThirdCards(Cards card,String player) {
			System.out.println("THIRD: "+card.sygnature);
			fromThird=card;
			for (int i=0;i<players.size();i++) {
				if(players.get(i).code.equals(player)) third=players.get(i);
			}
			checkBoard();
		}
		
		void preperPlayerToNext(int i) {
			players.get(i).isPass=false;
			players.get(i).leadingGame=false;
			players.get(i).startAuction=false;
			players.get(i).pointsInOneDeal=0;
		}
		
		void startNewRound() {
			if(MainServer.typeServer==TypeServer.TWO_PLAYER) {
				//System.out.println("endRound_Work_TWO");
				preperPlayerToNext(0);
				preperPlayerToNext(1);
				if(dealingPlayer==DealingPlayer.FIRST) {
					dealingPlayer=DealingPlayer.SECOND;
					players.get(1).sendMessege("YOUR_TURN");
				}
				else if(dealingPlayer==DealingPlayer.SECOND) {
					dealingPlayer=DealingPlayer.FIRST;
					players.get(0).sendMessege("YOUR_TURN");
				}
				dealingCards();
			}
			else if(MainServer.typeServer==TypeServer.THREE_PLAYER) {
				//System.out.println("endRound_Work_THREE");
				preperPlayerToNext(0);
				preperPlayerToNext(1);
				preperPlayerToNext(2);
				if(dealingPlayer==DealingPlayer.FIRST) {
					dealingPlayer=DealingPlayer.SECOND;
					players.get(1).sendMessege("YOUR_TURN");
				}
				else if(dealingPlayer==DealingPlayer.SECOND) {
					dealingPlayer=DealingPlayer.THIRD;
					players.get(2).sendMessege("YOUR_TURN");
				}
				else if(dealingPlayer==DealingPlayer.THIRD) {
					dealingPlayer=DealingPlayer.FIRST;
					players.get(0).sendMessege("YOUR_TURN");
				}
				dealingCards();
			}
			else if(MainServer.typeServer==TypeServer.FOUR_PLAYER) {
				//System.out.println("endRound_Work_FOUR");
				preperPlayerToNext(0);
				preperPlayerToNext(1);
				preperPlayerToNext(2);
				preperPlayerToNext(3);
				if(dealingPlayer==DealingPlayer.FIRST) {
					System.out.println("first"+players.get(2).code);
					dealingPlayer=DealingPlayer.SECOND;
					players.get(0).sendMessege("YOUR_TURN");
				}
				else if(dealingPlayer==DealingPlayer.SECOND) {
					System.out.println("second"+players.get(3).code);
					dealingPlayer=DealingPlayer.THIRD;
					players.get(1).sendMessege("YOUR_TURN");
				}
				else if(dealingPlayer==DealingPlayer.THIRD) {
					System.out.println("third"+players.get(0).code);
					dealingPlayer=DealingPlayer.FOURTH;
					players.get(2).sendMessege("YOUR_TURN");
				}
				else if(dealingPlayer==DealingPlayer.FOURTH) {
					System.out.println("fourth"+players.get(1).code);
					dealingPlayer=DealingPlayer.FIRST;
					players.get(3).sendMessege("YOUR_TURN");
				}
				dealingCards();
			}
			pairInDealing=Pairs.NULL_DISPATCH;
			leading=null;
			//clearBoard();
		}
		
		void endRound() {
			System.out.println("endRound_Work");
			for (int i=0;i<players.size();i++) {
				players.get(i).playerDeck.clear();
				players.get(i).sendMessege("END_ROUND");
				if(first==leading) {
					players.get(i).sendMessege("ADD_POINT");
					players.get(i).sendMessege(leading.code);
					if(leading.pointsInOneDeal>=levelOfPoint) {
						players.get(i).sendMessege("PLUS");
					}
					else {
						players.get(i).sendMessege("MINUS");
					}
					players.get(i).sendMessege(String.valueOf(levelOfPoint));
				}
				else {
					if(first.pointInGame<800){
						players.get(i).sendMessege("ADD_POINT");
						players.get(i).sendMessege(first.code);
						players.get(i).sendMessege("PLUS");
						players.get(i).sendMessege(String.valueOf(first.pointsInOneDeal));
					}
					else {
						players.get(i).sendMessege("NOT_ADD_POINT");
					}
				}
				if(second==leading) {
					players.get(i).sendMessege("ADD_POINT");
					players.get(i).sendMessege(leading.code);
					if(leading.pointsInOneDeal>=levelOfPoint) {
						players.get(i).sendMessege("PLUS");
					}
					else {
						players.get(i).sendMessege("MINUS");
					}
					players.get(i).sendMessege(String.valueOf(levelOfPoint));
				}
				else {
					if(second.pointInGame<800) {
						players.get(i).sendMessege("ADD_POINT");
						players.get(i).sendMessege(second.code);
						players.get(i).sendMessege("PLUS");
						players.get(i).sendMessege(String.valueOf(second.pointsInOneDeal));
					}
					else {
						players.get(i).sendMessege("NOT_ADD_POINT");
					}
				}
				if(third!=null) {
					if(third==leading) {
						players.get(i).sendMessege("ADD_POINT");
						players.get(i).sendMessege(leading.code);
						if(leading.pointsInOneDeal>=levelOfPoint) {
							players.get(i).sendMessege("PLUS");
						}
						else {
							players.get(i).sendMessege("MINUS");
						}
						players.get(i).sendMessege(String.valueOf(levelOfPoint));
					}
					else {
						if(third.pointInGame<800) {
							players.get(i).sendMessege("ADD_POINT");
							players.get(i).sendMessege(third.code);
							players.get(i).sendMessege("PLUS");
							players.get(i).sendMessege(String.valueOf(third.pointsInOneDeal));
						}
						else {
							players.get(i).sendMessege("NOT_ADD_POINT");
						}
					}
				}
			}
			if(leading==first) {
				if(leading.pointsInOneDeal>=levelOfPoint){
					leading.pointInGame+=levelOfPoint;
				}
				else {
					leading.pointInGame-=levelOfPoint;
				}
			}
			else {
				if(first.pointInGame<800) {
					first.pointInGame+=first.pointsInOneDeal;
				}
			}
			if(leading==second) {
				if(leading.pointsInOneDeal>=levelOfPoint){
					leading.pointInGame+=levelOfPoint;
				}
				else {
					leading.pointInGame-=levelOfPoint;
				}
			}
			else {
				if(second.pointInGame<800) {
					second.pointInGame+=second.pointsInOneDeal;
				}
			}
			if(third!=null) {
				if(leading==third) {
					if(leading.pointsInOneDeal>=levelOfPoint){
						leading.pointInGame+=levelOfPoint;
					}
					else {
						leading.pointInGame-=levelOfPoint;
					}
				}
				else {
					if(third.pointInGame<800) {
						third.pointInGame+=third.pointsInOneDeal;
					}
				}
			}
			if(MainServer.typeServer==TypeServer.TWO_PLAYER) {
				System.out.println("FIRST_POINTs_IN_GAME      "+first.pointInGame+"   "+ first.code);
				System.out.println("Second_POINTs_IN_GAME     "+second.pointInGame+"   "+ second.code);
				if(first.pointInGame>=1000) {
					System.out.println(first.pointInGame+" first end game ");
					for (int i=0;i<players.size();i++) {
						players.get(i).sendMessege("END_GAME");
					}
				}
				else if(second.pointInGame>=1000) {
					System.out.println(second.pointInGame+" second end game ");
					for (int i=0;i<players.size();i++) {
						players.get(i).sendMessege("END_GAME");
					}
				}
				else {
					startNewRound();
				}
			}
			else {
				System.out.println("FIRST_POINTs_IN_GAME      "+first.pointInGame);
				System.out.println("Second_POINTs_IN_GAME     "+second.pointInGame);
				System.out.println("THIRD_POINTs_IN_GAME      "+third.pointInGame);
				if(first.pointInGame>=1000) {
					System.out.println(first.pointInGame+" first end game ");
					for (int i=0;i<players.size();i++) {
						players.get(i).sendMessege("END_GAME");
					}
				}
				else if(second.pointInGame>=1000) {
					System.out.println(second.pointInGame+" Second end game ");
					for (int i=0;i<players.size();i++) {
						players.get(i).sendMessege("END_GAME");
					}
				}
				else if(third.pointInGame>=1000) {
					System.out.println(third.pointInGame+" third end game ");
					for (int i=0;i<players.size();i++) {
						players.get(i).sendMessege("END_GAME");
					}
				}
				else {
					startNewRound();
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
		
		void setWinner(Cards strong) {
			//System.out.println("setWinner+WORK");
			if(strong==fromDealing) {
				System.out.println("setWinner+WORK+FIRST_IF: "+first.code);
				first.sendMessege("YOU_WIN");
				if(fromThird!=null) {
					first.sendMessege(String.valueOf(fromDealing.power+fromSecond.power+fromThird.power));
					first.pointsInOneDeal+=fromDealing.power+fromSecond.power+fromThird.power;
				}
				else {
					first.sendMessege(String.valueOf(fromDealing.power+fromSecond.power));
					first.pointsInOneDeal+=fromDealing.power+fromSecond.power;
				}
				//System.out.println("is-weaing");
				//first.readMessege();
				for (int i=0;i<players.size();i++) {
					players.get(i).sendMessege("END_ONE_DEALING");
					//System.out.println("is-weaing");
					//players.get(i).readMessege();
				}
				//System.out.println(first.code+": SET_TO_SEND");
				int notUsedCardsFirst=0;
				int notUsedCardsSecond=0;
				System.out.println(first.playerDeck.size()+"-size");
				for (int i=0;i<first.playerDeck.size();i++) {
					if(!first.playerDeck.get(i).wasUsed) {
						notUsedCardsFirst+=1;
					}
				}
				for (int i=0;i<second.playerDeck.size();i++) {
					if(!second.playerDeck.get(i).wasUsed) {
						notUsedCardsSecond+=1;
					}
				}
				System.out.println("notUsedFirst:"+notUsedCardsFirst+first.code);
				System.out.println("notUsedSecond:"+notUsedCardsSecond+second.code);
				if(notUsedCardsFirst==0&& notUsedCardsSecond==0) {
					endRound();
					clearBoard();
				}
				else {
					first.sendMessege("SET_TO_SEND");
					//first.readMessege();
					clearBoard();
					return;
				}
			}
			if(strong==fromSecond) {
				System.out.println("setWinner+WORK+SECOND_IF: "+second.code);
				players.get(second.orderInRoom).sendMessege("YOU_WIN");
				if(fromThird!=null) {
					second.sendMessege(String.valueOf(fromDealing.power+fromSecond.power+fromThird.power));
					second.pointsInOneDeal+=fromDealing.power+fromSecond.power+fromThird.power;
				}
				else {
					second.sendMessege(String.valueOf(fromDealing.power+fromSecond.power));
					second.pointsInOneDeal+=fromDealing.power+fromSecond.power;
				}
				//System.out.println("is-weaing");
				//second.readMessege();
				for (int i=0;i<players.size();i++) {
					players.get(i).sendMessege("END_ONE_DEALING");
					//System.out.println("is-weaing");
					//players.get(i).readMessege();
				}
				System.out.println(first.playerDeck.size()+"-size");
				int notUsedCardsFirst=0;
				int notUsedCardsSecond=0;
				for (int i=0;i<first.playerDeck.size();i++) {
					if(!first.playerDeck.get(i).wasUsed) {
						notUsedCardsFirst+=1;
					}
				}
				for (int i=0;i<second.playerDeck.size();i++) {
					if(!second.playerDeck.get(i).wasUsed) {
						notUsedCardsSecond+=1;
					}
				}
				System.out.println("notUsedFirst:"+notUsedCardsFirst+first.code);
				System.out.println("notUsedSecond:"+notUsedCardsSecond+second.code);
				if(notUsedCardsFirst==0&&notUsedCardsSecond==0) {
					endRound();
					clearBoard();
				}
				else {
					second.sendMessege("SET_TO_SEND");
					//second.readMessege();
					clearBoard();
					return;
				}
			}
			if(fromThird!=null) {
				if(strong==fromThird) {
					System.out.println("setWinner+WORK+THIRD_IF: "+third.code);
					third.sendMessege("YOU_WIN");
					third.sendMessege(String.valueOf(fromDealing.power+fromSecond.power+fromThird.power));
					third.pointsInOneDeal+=fromDealing.power+fromSecond.power+fromThird.power;
					//System.out.println("is-weaing");
					//third.readMessege();
					for (int i=0;i<players.size();i++) {
						players.get(i).sendMessege("END_ONE_DEALING");
						//System.out.println("is-weaing");
						//players.get(i).readMessege();
					}
					System.out.println(third.code+": SET_TO_SEND");
					int notUsedCardsFirst=0;
					int notUsedCardsSecond=0;
					int notUsedCardsThird=0;
					for (int i=0;i<first.playerDeck.size();i++) {
						if(!first.playerDeck.get(i).wasUsed) {
							notUsedCardsFirst+=1;
						}
					}
					for (int i=0;i<second.playerDeck.size();i++) {
						if(!second.playerDeck.get(i).wasUsed) {
							notUsedCardsSecond+=1;
						}
					}
					for (int i=0;i<third.playerDeck.size();i++) {
						if(!third.playerDeck.get(i).wasUsed) {
							notUsedCardsThird+=1;
						}
					}
					System.out.println("notUsedFirst:"+notUsedCardsFirst+first.code);
					System.out.println("notUsedSecond:"+notUsedCardsSecond+second.code);
					System.out.println("notUsedThird:"+notUsedCardsThird+third.code);
					if(notUsedCardsFirst==0&&notUsedCardsSecond==0&&notUsedCardsThird==0) {
						endRound();
						clearBoard();
					}
					else {
						third.sendMessege("SET_TO_SEND");
						//third.readMessege();
						clearBoard();
						return;
					}
				}
			}
		}
		void checkBoard() {
			//System.out.println("checkBoard+WORK");
			//System.out.println("FIRST-"+fromDealing.sygnature);
			//System.out.println("SECOND-"+fromSecond.sygnature);
			//if(fromThird!=null) {
				//System.out.println("THIRD-"+fromThird.sygnature);
			//}
			switch (pairInDealing) {
			case NULL_DISPATCH:{
				System.out.println("checkBoard+WORK+NULL");	
				Cards strongest=fromDealing;
					if(strongest.color.equals(fromSecond.color)) {
						if(strongest.power<fromSecond.power) {
							//System.out.println("FIRST_IF_NULL");
							strongest=fromSecond;
						}
					}
					if(fromThird!=null) {
						if(strongest.color.equals(fromThird.color)) {
							if(strongest.power<fromThird.power) {
								//System.out.println("SECOND_IF_NULL");
								strongest=fromThird;
							}
						}
					}
					setWinner(strongest);
					break;
				}
			case PIK_DISPATCH:{
				System.out.println("checkBoard+PIK");
				Cards strongest=fromDealing;
					if(strongest.color.equals("pik")) {
						if(strongest.color.equals(fromSecond.color)) {
							if(strongest.power<fromSecond.power) {
								strongest=fromSecond;
							}
						}
					}
					else {
						if(fromSecond.color.equals("pik")) {
							strongest=fromSecond;
						}
						else {
							if(strongest.color.equals(fromSecond.color)) {
								if(strongest.power<fromSecond.power) {
									strongest=fromSecond;
								}
							}
						}
					}
					if(fromThird!=null) {
						if(strongest.color.equals("pik")) {
							if(strongest.color.equals(fromThird.color)) {
								if(strongest.power<fromThird.power) {
									strongest=fromThird;
								}
							}
						}
						else {
							if(fromThird.color.equals("pik")) {
								strongest=fromSecond;
							}
							else {
								if(strongest.color.equals(fromThird.color)) {
									if(strongest.power<fromThird.power) {
										strongest=fromThird;
									}
								}
							}
						}
					}
					setWinner(strongest);
					break;
				}
			case CARO_DISPATCH:{
				System.out.println("checkBoard+WORK+CARO");	
				Cards strongest=fromDealing;
					if(strongest.color.equals("caro")) {
						if(strongest.color.equals(fromSecond.color)) {
							if(strongest.power<fromSecond.power) {
								strongest=fromSecond;
							}
						}
					}
					else {
						if(fromSecond.color.equals("caro")) {
							strongest=fromSecond;
						}
						else {
							if(strongest.color.equals(fromSecond.color)) {
								if(strongest.power<fromSecond.power) {
									strongest=fromSecond;
								}
							}
						}
					}
					if(fromThird!=null) {
						if(strongest.color.equals("caro")) {
							if(strongest.color.equals(fromThird.color)) {
								if(strongest.power<fromThird.power) {
									strongest=fromThird;
								}
							}
						}
						else {
							if(fromThird.color.equals("caro")) {
								strongest=fromThird;
							}
							else {
								if(strongest.color.equals(fromThird.color)) {
									if(strongest.power<fromThird.power) {
										strongest=fromThird;
									}
								}
							}
						}
					}
					setWinner(strongest);
					break;
				}
			case TREFL_DISPATCH:{
					System.out.println("checkBoard+TREFL");	
					Cards strongest=fromDealing;
					if(strongest.color.equals("trefl")) {
						if(strongest.color.equals(fromSecond.color)) {
							if(strongest.power<fromSecond.power) {
								strongest=fromSecond;
							}
						}
					}
					else {
						if(fromSecond.color.equals("trefl")) {
							strongest=fromSecond;
						}
						else {
							if(strongest.color.equals(fromSecond.color)) {
								if(strongest.power<fromSecond.power) {
									strongest=fromSecond;
								}
							}
						}
					}
					if(fromThird!=null) {
						if(strongest.color.equals("trefl")) {
							if(strongest.color.equals(fromThird.color)) {
								if(strongest.power<fromThird.power) {
									strongest=fromThird;
								}
							}
						}
						else {
							if(fromThird.color.equals("trefl")) {
								strongest=fromThird;
							}
							else {
								if(strongest.color.equals(fromThird.color)) {
									if(strongest.power<fromThird.power) {
										strongest=fromThird;
									}
								}
							}
						}
					}
					setWinner(strongest);
					break;
				}
			case KIER_DISPATCH:{
				System.out.println("checkBoard+WORK+KIER");
				Cards strongest=fromDealing;
					if(strongest.color.equals("kier")) {
						if(strongest.color.equals(fromSecond.color)) {
							if(strongest.power<fromSecond.power) {
								strongest=fromSecond;
							}
						}
					}
					else {
						if(fromSecond.color.equals("kier")) {
							strongest=fromSecond;
						}
						else {
							if(strongest.color.equals(fromSecond.color)) {
								if(strongest.power<fromSecond.power) {
									strongest=fromSecond;
								}
							}
						}
					}
					if(fromThird!=null) {
						if(strongest.color.equals("kier")) {
							if(strongest.color.equals(fromThird.color)) {
								if(strongest.power<fromThird.power) {
									strongest=fromThird;
								}
							}
						}
						else {
							if(fromThird.color.equals("kier")) {
								strongest=fromThird;
							}
							else {
								if(strongest.color.equals(fromThird.color)) {
									if(strongest.power<fromThird.power) {
										strongest=fromThird;
									}
								}
							}
						}
					}
					setWinner(strongest);
					break;
				}
			}
			
		}

	}
}
