package pgv.service.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

public class TextServiceController implements Initializable {
	

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
	
	}
	public BorderPane getRoot() {
		return root;
	}
	
}