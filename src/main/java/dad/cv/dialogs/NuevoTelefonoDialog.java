package dad.cv.dialogs;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.cv.model.Telefono;
import dad.cv.model.TipoTelefono;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class NuevoTelefonoDialog extends Dialog<Telefono> implements Initializable {

	// model

	private StringProperty numero = new SimpleStringProperty();
	private ObjectProperty<TipoTelefono> tipo = new SimpleObjectProperty<TipoTelefono>();

	// view

	@FXML
	private TextField numeroText;
	@FXML
	private ComboBox<TipoTelefono> tipoCombo;
	@FXML
	private GridPane view;
	@SuppressWarnings("unused")
	private Button addButton;

	public NuevoTelefonoDialog() {
		super();
		setTitle("Nuevo Teléfono");
		setHeaderText("Introduzca el nuevo número de teléfono.");
		loadContent();
		getDialogPane().setContent(view);

		ButtonType addButtonType = new ButtonType("Añadir", ButtonData.OK_DONE);
		getDialogPane().getButtonTypes().setAll(addButtonType, ButtonType.CANCEL);

		addButton = (Button) getDialogPane().lookupButton(addButtonType);

		setResultConverter(button -> onResultConverter(button));// this::onResultConverter

	}

	private Telefono onResultConverter(ButtonType button) {
		Telefono telefono = new Telefono();
		if (button.getButtonData() == ButtonData.OK_DONE) {
			telefono.setNumero(numero.get());
			telefono.setTipo(tipo.get());
		}
		return telefono;
	}

	private void loadContent() {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NuevoTelefonoView.fxml"));
			loader.setController(this);
			loader.load();

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// load combo
		tipoCombo.getItems().setAll(TipoTelefono.values());

		// bindings
		numero.bind(numeroText.textProperty());
		tipo.bind(tipoCombo.getSelectionModel().selectedItemProperty());

		// disable add button

	}

}
