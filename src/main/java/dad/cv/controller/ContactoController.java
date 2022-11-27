package dad.cv.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.cv.dialogs.NuevaWebDialog;
import dad.cv.dialogs.NuevoEmailDialog;
import dad.cv.dialogs.NuevoTelefonoDialog;
import dad.cv.model.Email;
import dad.cv.model.Telefono;
import dad.cv.model.TipoTelefono;
import dad.cv.model.Web;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;

public class ContactoController implements Initializable {

	// model
	NuevoTelefonoDialog dialogTelefono = new NuevoTelefonoDialog();
	NuevoEmailDialog dialogEmail = new NuevoEmailDialog();
	NuevaWebDialog dialogWeb = new NuevaWebDialog();

	private ListProperty<Telefono> telefonos = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ListProperty<Email> emails = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ListProperty<Web> webs = new SimpleListProperty<>(FXCollections.observableArrayList());
	private IntegerProperty selectedName = new SimpleIntegerProperty();
	private IntegerProperty selectedName2 = new SimpleIntegerProperty();
	private IntegerProperty selectedName3 = new SimpleIntegerProperty();

	// view
	@FXML
	private Button addButton, addButton2, addButton3, deleteButton, deleteButton2, deleteButton3;

	@FXML
	private TitledPane emailPane;
	@FXML
	private TableView<Email> emailTable;
	@FXML
	private TableColumn<Email, String> emailColumn;

	@FXML
	private TitledPane telefonosPane;
	@FXML
	private TableView<Telefono> telefonosTable;
	@FXML
	private TableColumn<Telefono, String> numeroColumn;
	@FXML
	private TableColumn<Telefono, TipoTelefono> tipoColumn;

	@FXML
	private TitledPane websPane;
	@FXML
	private TableView<Web> urlTable;
	@FXML
	private TableColumn<Web, String> urlColumn;

	@FXML
	private SplitPane view;

	public ContactoController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ContactoView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		numeroColumn.setCellValueFactory(v -> v.getValue().numeroProperty());
		tipoColumn.setCellValueFactory(v -> v.getValue().tipoProperty());
		telefonosTable.itemsProperty().bind(telefonos);

		emailColumn.setCellValueFactory(v -> v.getValue().direccionProperty());
		emailTable.itemsProperty().bind(emails);

		urlColumn.setCellValueFactory(v -> v.getValue().urlProperty());
		urlTable.itemsProperty().bind(webs);

		addButton.setOnAction(this::onAddAction);
		addButton2.setOnAction(this::onAddAction2);
		addButton3.setOnAction(this::onAddAction3);

		deleteButton.setOnAction(this::onDeleteAction);
		deleteButton2.setOnAction(this::onDeleteAction2);
		deleteButton3.setOnAction(this::onDeleteAction3);

		selectedName.bind(telefonosTable.getSelectionModel().selectedIndexProperty());
		selectedName2.bind(emailTable.getSelectionModel().selectedIndexProperty());
		selectedName3.bind(urlTable.getSelectionModel().selectedIndexProperty());
		deleteButton.disableProperty().bind(selectedName.lessThan(0));
		deleteButton2.disableProperty().bind(selectedName2.lessThan(0));
		deleteButton3.disableProperty().bind(selectedName3.lessThan(0));

	}

	public SplitPane getView() {
		return view;
	}

	@FXML
	void onAddAction(ActionEvent event) {
		Telefono telefono = dialogTelefono.showAndWait().orElse(null);
		telefonos.add(telefono);
	}

	void onAddAction2(ActionEvent event) {
		Email email = dialogEmail.showAndWait().orElse(null);
		emails.add(email);
	}

	void onAddAction3(ActionEvent event) {
		Web web = dialogWeb.showAndWait().orElse(null);
		webs.add(web);
	}

	@FXML
	void onDeleteAction(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Eliminar teléfono");
		alert.setHeaderText("Está a punto de eliminar este elemento.");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			Telefono selected = telefonosTable.getSelectionModel().getSelectedItem();
			telefonos.remove(selected);
		}
	}

	void onDeleteAction2(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Eliminar e-mail");
		alert.setHeaderText("Está a punto de eliminar este elemento.");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			Email selected = emailTable.getSelectionModel().getSelectedItem();
			emails.remove(selected);
		}
	}

	void onDeleteAction3(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Eliminar web");
		alert.setHeaderText("Está a punto de eliminar este elemento.");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			Web selected = urlTable.getSelectionModel().getSelectedItem();
			webs.remove(selected);
		}
	}

}
