package server;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import enums.TypeServer;
import javafx.stage.Stage;

public class MainServer {
	
	public static TypeServer typeServer=TypeServer.THREE_PLAYER;
	
	ServerSocket serverSocket;

	int amountPlayers;

	public ArrayList<OneClient> players = new ArrayList<>(); 
	
	public StartServer start = new StartServer();
	
	public Game game;
	
	Stage stage;
	
	MainServer server;
	
	public MainServer(Game game,Stage stage){
		this.game=game;
		this.stage=stage;
		server=this;
		start.start();
		System.out.println(false);
		if(typeServer == TypeServer.TWO_PLAYER) {
			amountPlayers=2;
		}
		else if(typeServer == TypeServer.THREE_PLAYER){
			amountPlayers=3;
		}
		else if(typeServer == TypeServer.FOUR_PLAYER){
			amountPlayers=4;
		}
	}
	
	public class StartServer extends Thread{
		public void run() {
			try {
				serverSocket = new ServerSocket(0);
				System.out.println(serverSocket.getLocalPort());
				Socket socket;
				while (players.size()<amountPlayers) {
					System.out.println("StartServer");
					socket=serverSocket.accept();
					OneClient client= new OneClient(socket,players.size(),stage,server);
					players.add(client);
				}
				game.addPlayers(players);
			} catch (IOException e) {
				System.out.println("Conection lose!!!-MainServer");
				e.printStackTrace();
			}
		}
	}
	
	public String returnIP() {
		//System.out.println("returnIP+Work");
		InetAddress adres;
		/*String gateway = null;
		if (Desktop.isDesktopSupported()) {
	            try {
	                Process process = Runtime.getRuntime().exec("ipconfig");
	                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	                    String line;
	                    while ((line = bufferedReader.readLine()) != null) {
	                    	System.out.println(line);
	                    	if(line.trim().startsWith("Default Gateway")) {
	                    		gateway=line.substring(line.indexOf(":")+1, line.length());
	                    		System.out.println(gateway);
	                    	}
	                    }
	                
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }*/
		try {
			adres=Inet4Address.getLoopbackAddress();
			//System.out.println(adres.getHostAddress());
			adres=Inet4Address.getLocalHost();
			//System.out.println(adres.getHostAddress());
			return adres.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int returnPort() {
		System.out.println(serverSocket.getLocalPort());
		System.out.println(serverSocket.getInetAddress().getHostAddress());
		return serverSocket.getLocalPort();
	}
	/*
	 pik winno
	 caro dzwonek
	 trefl - ¿o³¹dz
	 kier czerwieñ
	*/
	/*
	 nine
	 ten
	 queene
	 king
	 as
	 jack
	 */
}
