package dad.cv.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.cv.dialogs.NuevaExperienciaDialog;
import dad.cv.model.Experiencia;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;

public class ExperienciaController implements Initializable {

	// model
	NuevaExperienciaDialog experienciaDialog = new NuevaExperienciaDialog();
	private ListProperty<Experiencia> experiencias = new SimpleListProperty<>(FXCollections.observableArrayList());
	private IntegerProperty selectedName = new SimpleIntegerProperty();

	// view
	@FXML
	private Button addButton, deleteButton;
	@FXML
	private TableColumn<Experiencia, String> denominacionColumn, empleadorColumn;
	@FXML
	private TableColumn<Experiencia, LocalDateTime> desdeColumn, hastaColumn;
	@FXML
	private TableView<Experiencia> experienciaTable;
	@FXML
	private GridPane view;

	public ExperienciaController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ExperienciaView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		desdeColumn.setCellValueFactory(v -> v.getValue().desdeProperty());
		hastaColumn.setCellValueFactory(v -> v.getValue().hastaProperty());
		denominacionColumn.setCellValueFactory(v -> v.getValue().denominacionProperty());
		empleadorColumn.setCellValueFactory(v -> v.getValue().empleadorProperty());
		experienciaTable.itemsProperty().bind(experiencias);

		selectedName.bind(experienciaTable.getSelectionModel().selectedIndexProperty());
		deleteButton.disableProperty().bind(selectedName.lessThan(0));

	}

	public GridPane getView() {
		return view;
	}

	@FXML
	void onAddButton(ActionEvent event) {
		Experiencia exp = experienciaDialog.showAndWait().orElse(null);
		experiencias.add(exp);
	}

	@FXML
	void onDeleteButton(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Eliminar experiencia");
		alert.setHeaderText("Está a punto de eliminar este elemento.");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			Experiencia selected = experienciaTable.getSelectionModel().getSelectedItem();
			experiencias.remove(selected);
		}
	}

}
