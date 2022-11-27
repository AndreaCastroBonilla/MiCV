package dad.cv.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.cv.CVApp;
import dad.cv.json.GsonHandler;
import dad.cv.model.CV;
import dad.cv.model.Nacionalidad;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class RootController2 implements Initializable {

	public static final String DEFAULT_PATH = "cv_files";

	// model

	private ObjectProperty<CV> cv = new SimpleObjectProperty<>();
	private ListProperty<Nacionalidad> nacionalidades = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ListProperty<String> paises = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObjectProperty<File> file = new SimpleObjectProperty<>();

	// controllers
	private ConocimientosController conocimientosController = new ConocimientosController();
	private ContactoController contactoController = new ContactoController();
	private ExperienciaController experienciaController = new ExperienciaController();
	private FormacionController formacionController = new FormacionController();
	private PersonalController personalController = new PersonalController();

	// view

	@FXML
	private MenuItem abrirMenuItem, acercaMenuItem, guardarComoMenuItem, guardarMenuItem, nuevoMenuItem, salirMenuItem;
	@FXML
	private Menu archivoMenu, ayudaMenu;
	@FXML
	private Tab conocimientosTab, contactoTab, experienciaTab, formacionTab, personalTab;
	@FXML
	private TabPane dataTabPane;
	@FXML
	private GridPane view;

	public RootController2() {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
			loader.setController(this);
			loader.load();

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// tab content
		conocimientosTab.setContent(conocimientosController.getView());
		contactoTab.setContent(contactoController.getView());
		experienciaTab.setContent(experienciaController.getView());
		formacionTab.setContent(formacionController.getView());
		personalTab.setContent(personalController.getView());

		// bindings

		loadIcons();

		salirMenuItem.setOnAction(e -> onSalirAction(e));
		guardarMenuItem.setOnAction(e -> onSaveAction(e));
		guardarComoMenuItem.setOnAction(e -> onSaveAsAction(e));
		nuevoMenuItem.setOnAction(e -> onNuevoAction(e));
		abrirMenuItem.setOnAction(e -> onAbrirAction(e));

		try {
			Files.createDirectories(Paths.get(DEFAULT_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}

		cv.set(new CV());

	}

	private void onAbrirAction(ActionEvent e) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Abrir archivo");
		fileChooser.setInitialDirectory(new File(DEFAULT_PATH));
		ExtensionFilter jpgFilter = new ExtensionFilter("CV files", "*.cv");
		fileChooser.getExtensionFilters().add(jpgFilter);
		fileChooser.setSelectedExtensionFilter(jpgFilter);

		File selectedFile = fileChooser.showOpenDialog(CVApp.primaryStage);
		if (selectedFile != null) {
			abrir(selectedFile);
		}

	}

	private void onNuevoAction(ActionEvent e) {
		nuevo();
	}

	private void onSaveAsAction(ActionEvent e) {
		guardarComo();

	}

	private void onSaveAction(ActionEvent e) {
		if (file.get() != null)
			guardar(file.get());
		else {
			guardarComo();
		}

	}

	private void onSalirAction(ActionEvent e) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Mi CV");
		alert.setHeaderText("¿Salir del programa?");
		alert.setContentText("Los datos sin guardar se borrarán.");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			CVApp.primaryStage.close();
		}
	}

	private void nuevo() {

		file.set(null);
		cv.set(new CV());

	}

	private void abrir(File selectedFile) {
		try {
			file.set(selectedFile);
//			cv.set(GsonHandler.loadCV(selectedFile));
		} catch (Exception e) {
			System.err.println("No se ha podido, jappens");
			e.printStackTrace();
		}
	}

	private void guardar(File selectedFile) {

		file.set(selectedFile);

		try {
			file.get().createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			GsonHandler.saveCV(cv.get(), file.get());
		} catch (Exception e) {
			System.err.println("No se ha podido, jappens");
			e.printStackTrace();
		}
	}

	private void guardarComo() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Guardar archivo");
		fileChooser.setInitialDirectory(new File(DEFAULT_PATH));
		fileChooser.setInitialFileName(file.get() != null ? file.get().getName() : "");
		ExtensionFilter jpgFilter = new ExtensionFilter("CV files", "*.cv");
		fileChooser.getExtensionFilters().add(jpgFilter);
		fileChooser.setSelectedExtensionFilter(jpgFilter);

		File selectedFile = fileChooser.showSaveDialog(CVApp.primaryStage);
		if (selectedFile != null) {
			guardar(selectedFile);
		}
	}

	private void loadIcons() {
		Image abrirIcon = new Image(getClass().getResourceAsStream("/images/abrir.gif"));
		ImageView abrir = new ImageView(abrirIcon);
		Image nuevoIcon = new Image(getClass().getResourceAsStream("/images/nuevo.gif"));
		ImageView nuevo = new ImageView(nuevoIcon);
		Image guardarIcon = new Image(getClass().getResourceAsStream("/images/guardar.gif"));
		ImageView guardar = new ImageView(guardarIcon);

		abrirMenuItem.setGraphic(abrir);
		nuevoMenuItem.setGraphic(nuevo);
		guardarMenuItem.setGraphic(guardar);

	}

	public GridPane getView() {
		return view;
	}

}
