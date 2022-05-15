package scenes;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import client.MainClient;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import server.MainServer;

public class ClientConnectToServer extends Group{
	
	TextField enterIP;
	TextField enterPort;
	TextField enterNickName;
	Stage stage;
	boolean withServer;
	MainServer server;
	double screenSizeWidth=1;
	double screenSizeHeight=1;
	
	public ClientConnectToServer(Stage stage,boolean withServer,MainServer server){
		this.stage=stage;
		this.withServer=withServer;
		this.server=server;
		setScene();
	}
	
	public ClientConnectToServer(Stage stage,boolean withServer){
		this.stage=stage;
		this.withServer=withServer;
		setScene();
	}
	
	void setScene() {
		setSizeOfWindow();
		addMainRentangle();
		addImageViewEnterNicname();
		addTextFiledEnterNickName();
		addImageViewConnectServer();
		addButtonStartClient();
		if(!this.withServer) {
			addImageViewEnterIP();
			addImageViewEnterPort();
			addTextFiledEnterIP();
			addTextFiledEnterPort();
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
	
	void addButtonStartClient() {
		Button b = new Button();
		b.setPrefWidth(400*screenSizeWidth);
		b.setPrefHeight(100*screenSizeHeight);
		b.setLayoutX(200*screenSizeWidth);
		b.setLayoutY(750*screenSizeHeight);
		b.setOpacity(0);
		b.addEventHandler(ActionEvent.ACTION, e->{
			if(!withServer) {
				nextWindowClient();
			}
			else {
				nextWindowServerCLient();
			}
		});
		this.getChildren().add(b);
	}
	
	void nextWindowServerCLient() {
		MainClient client = new MainClient(enterNickName.getText(),
		String.valueOf(server.returnPort()),server.game,stage);
		System.out.println("addButtonStartClient+work");
		ServerRoom serverRoom = new ServerRoom(server);
		client.addServerRoom(serverRoom);
		stage.setScene(new Scene(serverRoom));
		stage.show();
	}
	
	String getPort() {
		String port= enterPort.getText();
		for (int i=0;i<port.length();i++) {
			if(port.charAt(i)!='0'&&port.charAt(i)!='1'&&port.charAt(i)!='2'&&port.charAt(i)!='3'&&port.charAt(i)!='4'&&port.charAt(i)!='5'&&port.charAt(i)!='6'&&port.charAt(i)!='7'&&port.charAt(i)!='8'&&port.charAt(i)!='9') {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						File file = new File("C:/Users/Patryk/Desktop/Programing/1000/ThousandGame/Game.exe");
						try {
							Desktop.getDesktop().open(file);
							System.exit(0);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
			}
		}
		return port;
	}
	
	void nextWindowClient() {
		MainClient client = new MainClient(enterNickName.getText(),
		enterIP.getText(),getPort(),stage);
		ServerRoom serverRoom = new ServerRoom();
		client.addServerRoom(serverRoom);
		stage.setScene(new Scene(serverRoom));
		stage.show();
	}
	
	void addTextFiledEnterNickName() {
		enterNickName = new TextField();
		enterNickName.setPrefWidth(600*screenSizeWidth);
		enterNickName.setPrefHeight(100*screenSizeHeight);
		enterNickName.setLayoutX(100*screenSizeWidth);
		enterNickName.setLayoutY(550*screenSizeHeight);
		enterNickName.setFont(new Font(30));
		enterNickName.setAlignment(Pos.CENTER);
		enterNickName.setFocusTraversable(true);
		this.getChildren().add(enterNickName);
	}
	
	void addTextFiledEnterPort() {
		enterPort = new TextField();
		enterPort.setPrefWidth(300*screenSizeWidth);
		enterPort.setPrefHeight(100*screenSizeHeight);
		enterPort.setLayoutX(450*screenSizeWidth);
		enterPort.setLayoutY(250*screenSizeHeight);
		enterPort.setFont(new Font(30));
		enterPort.setAlignment(Pos.CENTER);
		enterPort.setFocusTraversable(true);
		this.getChildren().add(enterPort);
	}
	
	void addTextFiledEnterIP() {
		enterIP = new TextField();
		enterIP.setPrefWidth(300*screenSizeWidth);
		enterIP.setPrefHeight(100*screenSizeHeight);
		enterIP.setLayoutX(50*screenSizeWidth);
		enterIP.setLayoutY(250*screenSizeHeight);
		enterIP.setFont(new Font(30));
		enterIP.setAlignment(Pos.CENTER);
		enterIP.setFocusTraversable(true);
		this.getChildren().add(enterIP);
	}
	
	void addImageViewConnectServer() {
		ImageView v = new ImageView();
		v.setFitWidth(400*screenSizeWidth);
		v.setFitHeight(100*screenSizeHeight);
		v.setX(200*screenSizeWidth);
		v.setY(750*screenSizeHeight);
		v.setImage(new Image("/resources/ConnetServer.jpg"));
		this.getChildren().add(v);
	}
	
	void addImageViewEnterNicname() {
		ImageView v = new ImageView();
		v.setFitWidth(400*screenSizeWidth);
		v.setFitHeight(100*screenSizeHeight);
		v.setX(200*screenSizeWidth);
		v.setY(40*screenSizeHeight);
		v.setImage(new Image("/resources/EnterNickName.jpg"));
		this.getChildren().add(v);
	}
	
	void addImageViewEnterIP() {
		ImageView v = new ImageView();
		v.setFitWidth(200*screenSizeWidth);
		v.setFitHeight(100*screenSizeHeight);
		v.setX(100*screenSizeWidth);
		v.setY(100*screenSizeHeight);
		v.setImage(new Image("/resources/EnterIP.jpg"));
		this.getChildren().add(v);
	}
	
	void addImageViewEnterPort() {
		ImageView v = new ImageView();
		v.setFitWidth(200*screenSizeWidth);
		v.setFitHeight(100*screenSizeHeight);
		v.setX(500*screenSizeWidth);
		v.setY(100*screenSizeHeight);
		v.setImage(new Image("/resources/EnterPortNumber.jpg"));
		this.getChildren().add(v);
	}
	
	void addMainRentangle() {
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
