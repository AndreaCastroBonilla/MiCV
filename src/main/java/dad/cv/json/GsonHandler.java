package dad.cv.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;

import org.hildan.fxgson.FxGson;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import dad.cv.CVApp;
import dad.cv.model.CV;
import dad.cv.model.Nacionalidad;
import dad.cv.model.Personal;

public class GsonHandler {
	
	private static Gson gson = FxGson.fullBuilder().setPrettyPrinting()
			.registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

	
	public static void loadCV(File file) throws JsonSyntaxException, JsonIOException, FileNotFoundException {

		CVApp cv = gson.fromJson(new FileReader(file), CVApp.class);

		System.out.println(cv.getPersonal().getNombre() + " " + cv.getPersonal().getApellidos());

	}

	public static void saveCV(CV c,File file) throws IOException {
		Personal personal = new Personal();
		personal.setIdentificacion(personal.getIdentificacion());
		personal.setNombre(personal.getNombre());
		personal.setApellidos(personal.getApellidos());
		
		for(Nacionalidad n : personal.getNacionalidades()) {
			personal.getNacionalidades().add(n);
		}
		
		personal.setPais(personal.getPais());
		personal.setFechaNacimiento(LocalDate.of(1954, 11, 25));

		CVApp cv = new CVApp();
		cv.setPersonal(personal);

		String json = gson.toJson(cv, CVApp.class);

		Files.writeString(file.toPath(), json, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
	}
}
