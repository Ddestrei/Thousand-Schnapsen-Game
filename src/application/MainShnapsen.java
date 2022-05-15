package application;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import scenes.*;
import server.MainServer;

/*
 * 
 * TODO
 * client and server must work together.
 */

public class MainShnapsen extends Application{
	
	public void start(Stage primaryStage) throws Exception {
		Stage stage = primaryStage;
		stage.setTitle("1000 Game");
		FirstWindow first = new FirstWindow();
		first.create.addEventHandler(ActionEvent.ACTION, e->{
			CreateServer create =new CreateServer(stage);
			stage.setScene(new Scene(create));
			stage.show();
		});
		first.join.addEventFilter(ActionEvent.ACTION, e->{
			ClientConnectToServer client = new ClientConnectToServer(stage,false);
			stage.setScene(new Scene(client));
			stage.show();
		});
		//ServerRoom server = new ServerRoom();
		
		Scene scene = new Scene(first);
		stage.setScene(scene);
		stage.show();
	} 
	
	
	
	public static void main(String[] args) {
		System.setProperty("java.net.preferIPv4Stack", "true");
		launch(args);
	}
	String nameUser;
	void enterTheUserNick() {
		Scanner scan = new Scanner(System.in);
		nameUser = scan.nextLine();
	}
}
