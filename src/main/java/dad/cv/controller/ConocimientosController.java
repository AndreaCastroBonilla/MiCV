package dad.cv.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.cv.dialogs.NuevoConocimientoDialog;
import dad.cv.dialogs.NuevoIdiomaDialog;
import dad.cv.model.Conocimiento;
import dad.cv.model.Experiencia;
import dad.cv.model.Idioma;
import dad.cv.model.Nivel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

public class ConocimientosController implements Initializable {

	// model
	NuevoConocimientoDialog dialogConocimiento = new NuevoConocimientoDialog();
	NuevoIdiomaDialog dialogIdioma = new NuevoIdiomaDialog();

	private ListProperty<Conocimiento> conocimientos = new SimpleListProperty<>(FXCollections.observableArrayList());
	private IntegerProperty selectedName = new SimpleIntegerProperty();

	// view
	@FXML
	private Button addConocimientoButton, addIdiomaButton, deleteButton;
	@FXML
	private TableView<Conocimiento> conocimientosTable;
	@FXML
	private TableColumn<Conocimiento, String> denominacionColumn;
	@FXML
	private TableColumn<Conocimiento, Nivel> nivelColumn;
	@FXML
	private GridPane view;

	public ConocimientosController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ConocimientosView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		denominacionColumn.setCellValueFactory(v -> v.getValue().denominacionProperty());
		nivelColumn.setCellValueFactory(v -> v.getValue().nivelProperty());
		conocimientosTable.itemsProperty().bind(conocimientos);

		selectedName.bind(conocimientosTable.getSelectionModel().selectedIndexProperty());
		deleteButton.disableProperty().bind(selectedName.lessThan(0));
	}

	public GridPane getView() {
		return view;
	}

	@FXML
	void onAddConocimientoAction(ActionEvent event) {
		Conocimiento conocimiento = dialogConocimiento.showAndWait().orElse(null);
		conocimientos.add(conocimiento);
		System.out.println(conocimiento);
	}

	@FXML
	void onAddIdiomaAction(ActionEvent event) {
		Conocimiento idioma = dialogIdioma.showAndWait().orElse(null);
		conocimientos.add(idioma);
		System.out.println(idioma.toString());
	}

	@FXML
	void onDeleteAction(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Eliminar conocimiento");
		alert.setHeaderText("Está a punto de eliminar este elemento.");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			Conocimiento selected = conocimientosTable.getSelectionModel().getSelectedItem();
			conocimientos.remove(selected);
		}
	}

}
