package pgv.service.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import pgv.service.app.TextServiceApp;

public class TextServiceController implements Initializable {


	private String IP="";
//	private String IP = "10.2.3.12";

	@FXML
	private BorderPane root;

	@FXML
	private Button importarButton;

	@FXML
	private Button eliminarButton;

	@FXML
	private Button exportarButton;

	@FXML
	private ListView<String> lista;

	@FXML
	private Button refreshButton;

	public Button getImportarButton() {
		return importarButton;
	}

	public Button getEliminarButton() {
		return eliminarButton;
	}

	public Button getExportarButton() {
		return exportarButton;
	}

	public ListView<String> getLista() {
		return lista;
	}

	public TextServiceController() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/View.fxml"));
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void initialize(URL location, ResourceBundle resources) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Introduzca IP del servidor");
		dialog.setContentText("Introduzca IP:");

		Optional<String> result = dialog.showAndWait();
		setIP(result.get());
		
		exportarButton.disableProperty().bind(lista.getSelectionModel().selectedIndexProperty().isEqualTo(-1));
		eliminarButton.disableProperty().bind(lista.getSelectionModel().selectedIndexProperty().isEqualTo(-1));
	}

	public BorderPane getRoot() {
		return root;
	}

	public void listar() {
		try {
			Socket clientSocket = new Socket();
			InetSocketAddress addr = new InetSocketAddress(IP, 5555);
			clientSocket.connect(addr);

			DataInputStream entrada = new DataInputStream(clientSocket.getInputStream());
			DataOutputStream salida = new DataOutputStream(clientSocket.getOutputStream());
			try {
				salida.writeUTF("lista");
				getLista().getItems().setAll(entrada.readUTF().split(","));
			} catch (Exception e) {
				e.printStackTrace();
			}


			salida.close();
			entrada.close();
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void onEliminarAction(ActionEvent event) throws IOException {
		Socket clientSocket = new Socket();
		InetSocketAddress addr = new InetSocketAddress(IP, 5555);
		clientSocket.connect(addr);

		DataOutputStream salida = new DataOutputStream(clientSocket.getOutputStream());
		try {
			salida.writeUTF("eliminar:" + getLista().getSelectionModel().getSelectedItem());
		} catch (Exception e) {
			e.printStackTrace();
		}


		salida.close();
		clientSocket.close();

		listar();
	}

	@FXML
	void onExportarButton(ActionEvent event) throws IOException {
		Socket clientSocket = new Socket();
		InetSocketAddress addr = new InetSocketAddress(IP, 5555);
		clientSocket.connect(addr);

		DataInputStream entrada = new DataInputStream(clientSocket.getInputStream());
		DataOutputStream salida = new DataOutputStream(clientSocket.getOutputStream());

		BufferedWriter bw = new BufferedWriter(
				new FileWriter("Txt/" + getLista().getSelectionModel().getSelectedItem()));
		salida.writeUTF("exportar:" + getLista().getSelectionModel().getSelectedItem());
		try {
			while (true) {
				bw.write(entrada.readUTF() + "\n");
			}
		} catch (Exception e) {
			System.out.println("Exportado");
			bw.close();
		}


		entrada.close();
		salida.close();
		clientSocket.close();

		listar();
	}

	@FXML
	void onImportarAction(ActionEvent event) throws IOException {
		Socket clientSocket = new Socket();
		InetSocketAddress addr = new InetSocketAddress(IP, 5555);
		clientSocket.connect(addr);

		DataInputStream entrada = new DataInputStream(clientSocket.getInputStream());
		DataOutputStream salida = new DataOutputStream(clientSocket.getOutputStream());

		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(new ExtensionFilter("txt files", "*.txt"));

		fc.setTitle("Importar Txt");

		File fichero = fc.showOpenDialog(TextServiceApp.getStage());
		if (fichero != null) {
			BufferedReader br = new BufferedReader(new FileReader(fichero));
			salida.writeUTF("importar:" + fichero.getName());
			String linea;
			try {
				while ((linea = br.readLine()) != null) {
					salida.writeUTF(linea);
				}
			} catch (Exception e) {
				System.out.println("Importado");
				br.close();
			}
		}else {
			salida.writeUTF("abortado");
		}

		entrada.close();
		salida.close();
		clientSocket.close();

		listar();
	}

	@FXML
	void onRefreshAction(ActionEvent event) {
		listar();
	}
	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

}