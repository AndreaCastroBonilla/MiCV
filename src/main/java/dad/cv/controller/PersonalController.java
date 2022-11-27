package dad.cv.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.cv.CVApp;
import dad.cv.model.Nacionalidad;
import dad.cv.model.Personal;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class PersonalController implements Initializable {

	// model
	Personal personal = new Personal();
	private ListProperty<String> nacionalidades = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObjectProperty<String> paises = new SimpleObjectProperty<>();
	private IntegerProperty selectedName = new SimpleIntegerProperty();

	// view
	@FXML
	private Button addButton, deleteButton;
	@FXML
	private TextField apellidosText, cpText, dniText, localidadText, nombreText;
	@FXML
	private TextArea direccionTextArea;
	@FXML
	private ListView<String> nacionalidadList;
	@FXML
	private DatePicker fnacimientoDatePicker;
	@FXML
	private ComboBox<String> paisComboBox;
	@FXML
	private GridPane view;

	public PersonalController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PersonalView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// bindings
		personal.setIdentificacion(dniText.getText());
		personal.setNombre(nombreText.getText());
		personal.setApellidos(apellidosText.getText());
		personal.setFechaNacimiento(fnacimientoDatePicker.getValue());
		personal.setDireccion(direccionTextArea.getText());
		personal.setCodigoPostal(cpText.getText());
		personal.setLocalidad(localidadText.getText());

		// load combo
		List<String> choices = loadCombo();
		paisComboBox.getItems().setAll(choices);
		paises.bind(paisComboBox.getSelectionModel().selectedItemProperty());
		personal.setPais(choices.toString());

		nacionalidadList.itemsProperty().bind(nacionalidades);
		selectedName.bind(nacionalidadList.getSelectionModel().selectedIndexProperty());
		deleteButton.disableProperty().bind(selectedName.lessThan(0));

		addButton.setOnAction(this::onAddAction);
		deleteButton.setOnAction(this::onDeleteAction);

	}

	private List<String> loadCombo() {
		List<String> choices = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new FileReader("paises.csv"));
			String line = null;
			String[] linea;
			while ((line = br.readLine()) != null) {
				line = br.readLine();
				linea = line.split(",");
				choices.add(linea[0]);
				br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return choices;
	}

	@FXML
	void onAddAction(ActionEvent event) {
		List<String> choices = new ArrayList<>();
		String[] linea = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader("nacionalidades.csv"));
			String line;
			linea = null;
			while ((line = br.readLine()) != null) {
				line = br.readLine();
				linea = line.split(",");
				choices.add(linea[0]);
				br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ChoiceDialog<String> dialog = new ChoiceDialog<>(linea[0], choices);
		dialog.setTitle("Nueva nacionalidad");
		dialog.setHeaderText("Añadir nacionalidad");
		dialog.setContentText("Seleccione una nacionalidad");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent() && !result.get().isBlank()) {
			nacionalidades.add(result.get().trim());
		}

	}

	@FXML
	void onDeleteAction(ActionEvent event) {
		nacionalidades.remove(selectedName.get());

	}

	public GridPane getView() {
		return view;
	}

	public final ListProperty<String> nacionalidadesProperty() {
		return this.nacionalidades;
	}

	public final ObservableList<String> getNacionalidades() {
		return this.nacionalidadesProperty().get();
	}

	public final void setNacionalidades(final ObservableList<String> nacionalidades) {
		this.nacionalidadesProperty().set(nacionalidades);
	}

	public final ObjectProperty<String> paisesProperty() {
		return this.paises;
	}

	public final String getPaises() {
		return this.paisesProperty().get();
	}

	public final void setPaises(final String paises) {
		this.paisesProperty().set(paises);
	}

}
