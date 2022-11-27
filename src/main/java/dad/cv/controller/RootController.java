package dad.cv.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.cv.CVApp;
import dad.cv.json.GsonHandler;
import dad.cv.model.CV;
import dad.cv.model.Nacionalidad;
import dad.cv.model.Personal;
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

public class RootController implements Initializable {

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

	public RootController() {
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

	}

	private void onAbrirAction(ActionEvent e) {
		try {
			String line;
			FileReader fr = new FileReader("cv.json");
			BufferedReader br = new BufferedReader(fr);
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
			br.close();
			System.out.println("ARCHIVO ABIERTO CORRECTAMENTE");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	private void onNuevoAction(ActionEvent e) {
		file.set(null);
		cv.set(new CV());
		System.out.println("ARCHIVO CREADO CORRECTAMENTE");
	}

	private void onSaveAsAction(ActionEvent e) {
		try {
			File file = new File("NEW_miCV.txt");
			FileWriter fw = new FileWriter(file, true);
			// TODO CV.TOSTRING
			fw.write(cv.toString());
			fw.close();
			System.out.println("ARCHIVO GUARDADO CORRECTAMENTE");
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	private void onSaveAction(ActionEvent e) {
		try {
			File file = new File("miCV.txt");
			FileWriter fw = new FileWriter(file, true);
			// TODO CV.TOSTRING
			fw.write(cv.toString());
			fw.close();
			System.out.println("ARCHIVO GUARDADO CORRECTAMENTE");
		} catch (IOException e1) {
			e1.printStackTrace();
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
