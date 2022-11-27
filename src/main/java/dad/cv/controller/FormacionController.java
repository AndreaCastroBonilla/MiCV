package dad.cv.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.cv.dialogs.NuevoTituloDialog;
import dad.cv.model.Titulo;
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

public class FormacionController implements Initializable {

	// model
	NuevoTituloDialog dialogTitulo = new NuevoTituloDialog();
	private ListProperty<Titulo> titulos = new SimpleListProperty<>(FXCollections.observableArrayList());
	private IntegerProperty selectedName = new SimpleIntegerProperty();

	// view

	@FXML
	private Button addButton, deleteButton;
	@FXML
	private TableColumn<Titulo, LocalDateTime> hastaColumn, desdeColumn;
	@FXML
	private TableColumn<Titulo, String> organizadorColumn, denominacionColumn;
	@FXML
	private TableView<Titulo> experienciaTable;
	@FXML
	private GridPane view;

	public FormacionController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FormacionView.fxml"));
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
		organizadorColumn.setCellValueFactory(v -> v.getValue().organizadorProperty());
		experienciaTable.itemsProperty().bind(titulos);

		selectedName.bind(experienciaTable.getSelectionModel().selectedIndexProperty());
		deleteButton.disableProperty().bind(selectedName.lessThan(0));

	}

	public GridPane getView() {
		return view;
	}

	@FXML
	void onAddButton(ActionEvent event) {
		Titulo titulo = dialogTitulo.showAndWait().orElse(null);
		titulos.add(titulo);
	}

	@FXML
	void onDeleteButton(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Eliminar título");
		alert.setHeaderText("Está a punto de eliminar este elemento.");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			Titulo selected = experienciaTable.getSelectionModel().getSelectedItem();
			titulos.remove(selected);
		}
	}

}
