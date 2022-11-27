package dad.cv.dialogs;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.cv.model.Conocimiento;
import dad.cv.model.Nivel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
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

public class NuevoConocimientoDialog extends Dialog<Conocimiento> implements Initializable {

	// model

	private StringProperty denominacion = new SimpleStringProperty();
	private ObjectProperty<Nivel> nivel = new SimpleObjectProperty<>();

	// view

	@FXML
	private TextField denominacionText;
	@FXML
	private ComboBox<Nivel> nivelComboBox;
	@FXML
	private Button restartButton;
	@FXML
	private GridPane view;

	@SuppressWarnings("unused")
	private Button addButton;

	public NuevoConocimientoDialog() {
		super();
		setTitle("Nuevo conocimiento");
		loadContent();
		getDialogPane().setContent(view);

		ButtonType addButtonType = new ButtonType("Crear", ButtonData.OK_DONE);
		getDialogPane().getButtonTypes().setAll(addButtonType, ButtonType.CANCEL);

		addButton = (Button) getDialogPane().lookupButton(addButtonType);

		setResultConverter(button -> onResultConverter(button));// this::onResultConverter

	}

	private Conocimiento onResultConverter(ButtonType button) {
		Conocimiento conocimiento = new Conocimiento();
		if (button.getButtonData() == ButtonData.OK_DONE) {
			conocimiento.setDenominacion(denominacion.get());
			conocimiento.setNivel(nivel.get());
		}
		return conocimiento;
	}

	private void loadContent() {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NuevoConocimientoView.fxml"));
			loader.setController(this);
			loader.load();

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// load combo
		nivelComboBox.getItems().setAll(Nivel.values());

		// bindings
		denominacion.bind(denominacionText.textProperty());
		nivel.bind(nivelComboBox.getSelectionModel().selectedItemProperty());

		// disable add button

	}

	@FXML
	void onRestartAction(ActionEvent event) {
		nivelComboBox.getItems().clear();
	}

}
