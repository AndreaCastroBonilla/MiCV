package dad.cv.dialogs;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.cv.model.Web;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class NuevaWebDialog extends Dialog<Web> implements Initializable {

	// TODO IMPLEMENTACIÓN DE LA CLASE EN LA CLASE APP EN GIT

	// model

	private StringProperty url = new SimpleStringProperty();

	// view

	@FXML
	private TextField urlText;
	@FXML
	private GridPane view;

	@SuppressWarnings("unused")
	private Button addButton;

	public NuevaWebDialog() {
		super();
		setTitle("Nueva web");
		setHeaderText("Crea una nueva dirección web.");
		loadContent();
		getDialogPane().setContent(view);

		ButtonType addButtonType = new ButtonType("Añadir", ButtonData.OK_DONE);
		getDialogPane().getButtonTypes().setAll(addButtonType, ButtonType.CANCEL);

		addButton = (Button) getDialogPane().lookupButton(addButtonType);

		setResultConverter(button -> onResultConverter(button));// this::onResultConverter

	}

	private Web onResultConverter(ButtonType button) {
		Web web = new Web();
		if (button.getButtonData() == ButtonData.OK_DONE) {
			web.setUrl(url.get());
		}
		return web;
	}

	private void loadContent() {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NuevaWebView.fxml"));
			loader.setController(this);
			loader.load();

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// bindings
		url.bind(urlText.textProperty());

		// disable add button

	}

}
