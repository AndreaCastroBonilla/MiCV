package dad.cv.dialogs;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.cv.model.Email;
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

public class NuevoEmailDialog extends Dialog<Email> implements Initializable {

	// TODO IMPLEMENTACIÓN DE LA CLASE EN LA CLASE APP EN GIT

	// model

	private StringProperty direccion = new SimpleStringProperty();

	// view

	@FXML
	private TextField emailText;
	@FXML
	private GridPane view;

	@SuppressWarnings("unused")
	private Button addButton;

	public NuevoEmailDialog() {
		super();
		setTitle("Nuevo e-mail");
		setHeaderText("Crea una nueva dirección de correo.");
		loadContent();
		getDialogPane().setContent(view);

		ButtonType addButtonType = new ButtonType("Añadir", ButtonData.OK_DONE);
		getDialogPane().getButtonTypes().setAll(addButtonType, ButtonType.CANCEL);

		addButton = (Button) getDialogPane().lookupButton(addButtonType);

		setResultConverter(button -> onResultConverter(button));// this::onResultConverter

	}

	private Email onResultConverter(ButtonType button) {
		Email email = new Email();
		if (button.getButtonData() == ButtonData.OK_DONE) {
			email.setDireccion(direccion.get());
		}
		return email;
	}

	private void loadContent() {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NuevoEmailView.fxml"));
			loader.setController(this);
			loader.load();

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// bindings
		direccion.bind(emailText.textProperty());

		// disable add button

	}

}
