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
		try {
			System.out.println("Creando socket cliente");
			Socket clientSocket = new Socket();
			System.out.println ("Estableciendo la conexión");
			InetSocketAddress addr = new InetSocketAddress("10.2.3.12", 5555);
			clientSocket.connect(addr);
			
			DataInputStream entrada = new DataInputStream(clientSocket.getInputStream());
			DataOutputStream salida = new DataOutputStream(clientSocket.getOutputStream());
			try {
					salida.writeUTF("lista");
					controller.getLista().getItems().setAll(entrada.readUTF().split(","));
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("Mensaje enviado");

			System.out.println("Cerrando el socket cliente");
			salida.close();
			entrada.close();
			clientSocket.close();
			System.out.println("Terminado");
		} catch (IOException e) { e.printStackTrace(); }

	}
	public void stop() {
		System.out.println("Sesion cerrada");

	}
	public static void main(String[] args) {
		launch(args);
	}

}
