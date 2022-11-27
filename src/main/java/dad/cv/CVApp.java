package dad.cv;

import dad.cv.controller.RootController2;
import dad.cv.model.Personal;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class CVApp extends Application {

	public static Stage primaryStage;
//	private RootController rootController = new RootController();
	private RootController2 rootController = new RootController2();

	private ObjectProperty<Personal> personal = new SimpleObjectProperty<>();

	@Override
	public void start(Stage primaryStage) throws Exception {

		CVApp.primaryStage = primaryStage;

		primaryStage.setTitle("Mi CV");
		primaryStage.getIcons().add(new Image("/images/cv64x64.png"));
		primaryStage.setScene(new Scene(rootController.getView()));
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public final ObjectProperty<Personal> personalProperty() {
		return this.personal;
	}

	public final Personal getPersonal() {
		return this.personalProperty().get();
	}

	public final void setPersonal(final Personal personal) {
		this.personalProperty().set(personal);
	}

}
