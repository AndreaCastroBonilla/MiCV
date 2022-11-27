package dad.cv.dialogs;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ResourceBundle;

import dad.cv.model.Experiencia;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
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

public class NuevaExperienciaDialog extends Dialog<Experiencia> implements Initializable {

	// model

	private ObjectProperty<LocalDateTime> desde = new SimpleObjectProperty<>();
	private ObjectProperty<LocalDateTime> hasta = new SimpleObjectProperty<>();
	private StringProperty denominacion = new SimpleStringProperty();
	private StringProperty empleador = new SimpleStringProperty();

	// view

	@FXML
	private TextField denominacionText, empleadorText;
	@FXML
	private DatePicker desdeDate, hastaDate;
	@FXML
	private GridPane view;

	@SuppressWarnings("unused")
	private Button addButton;

	public NuevaExperienciaDialog() {
		super();
		setTitle("Nueva experiencia");
		loadContent();
		getDialogPane().setContent(view);

		ButtonType addButtonType = new ButtonType("Crear", ButtonData.OK_DONE);
		getDialogPane().getButtonTypes().setAll(addButtonType, ButtonType.CANCEL);

		addButton = (Button) getDialogPane().lookupButton(addButtonType);

		setResultConverter(button -> onResultConverter(button));// this::onResultConverter

	}

	private Experiencia onResultConverter(ButtonType button) {
		Experiencia experiencia = new Experiencia();
		if (button.getButtonData() == ButtonData.OK_DONE) {
			experiencia.setDenominacion(denominacion.get());
			experiencia.setEmpleador(empleador.get());
			experiencia.setDesde(desde.get());
			experiencia.setHasta(hasta.get());
		}
		return experiencia;
	}

	private void loadContent() {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NuevaExperienciaView.fxml"));
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
		empleador.bind(empleadorText.textProperty());
		String ld = desdeDate.valueProperty().toString();
		DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//		LocalDateTime dt = LocalDateTime.parse(ld, formateador);
//		desde.bind(desdeDate.converterProperty());

		// disable add button

	}

}
