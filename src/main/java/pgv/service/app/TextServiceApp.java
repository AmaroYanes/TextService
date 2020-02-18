package pgv.service.app;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pgv.service.controller.TextServiceController;

public class TextServiceApp  extends Application {
	TextServiceController controller = new TextServiceController();

	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Scene(controller.getRoot(),600,400);
		primaryStage.setTitle("TextService");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public void init()  {
		System.out.println("Sesion abierta");
		controller.listar();

	}
	public void stop() {
		System.out.println("Sesion cerrada");

	}
	public static void main(String[] args) {
		launch(args);
	}

}
