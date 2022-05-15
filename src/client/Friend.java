package client;

import java.util.ArrayList;

import engine.Cards;
import enums.TypePlayer;

public class Friend {
	String name;
	String code;
	int amountCard;
	int pointInGame;
	boolean isServer;
	boolean isReady=false;
	boolean isPass=false;
	boolean isServerOwner=false;
	TypePlayer typePlayer;
	public Friend(){
		pointInGame=0;
	}
}
