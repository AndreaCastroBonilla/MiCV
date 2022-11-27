package dad.cv.dialogs;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import dad.cv.model.Titulo;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class NuevoTituloDialog extends Dialog<Titulo> implements Initializable {

	// model

	private ObjectProperty<LocalDateTime> desde = new SimpleObjectProperty<>();
	private ObjectProperty<LocalDateTime> hasta = new SimpleObjectProperty<>();
	private StringProperty denominacion = new SimpleStringProperty();
	private StringProperty organizador = new SimpleStringProperty();

	// view

	@FXML
	private TextField denominacionText, organizadorText;
	@FXML
	private DatePicker desdeDate, hastaDate;
	@FXML
	private GridPane view;

	@SuppressWarnings("unused")
	private Button addButton;

	public NuevoTituloDialog() {
		super();
		setTitle("Nuevo título");
		loadContent();
		getDialogPane().setContent(view);

		ButtonType addButtonType = new ButtonType("Crear", ButtonData.OK_DONE);
		getDialogPane().getButtonTypes().setAll(addButtonType, ButtonType.CANCEL);

		addButton = (Button) getDialogPane().lookupButton(addButtonType);

		setResultConverter(button -> onResultConverter(button));// this::onResultConverter

	}

	private Titulo onResultConverter(ButtonType button) {
		Titulo titulo = new Titulo();
		if (button.getButtonData() == ButtonData.OK_DONE) {
			titulo.setDenominacion(denominacion.get());
			titulo.setOrganizador(organizador.get());
			titulo.setDesde(desde.get());
			titulo.setHasta(hasta.get());
		}
		return titulo;
	}

	private void loadContent() {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NuevoTituloView.fxml"));
			loader.setController(this);
			loader.load();

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// bindings
		denominacion.bind(denominacionText.textProperty());
		organizador.bind(organizadorText.textProperty());
//		desdeDate.valueProperty().bindBidirectional(desde);

		// disable add button

	}

}
