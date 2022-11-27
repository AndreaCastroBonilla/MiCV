package dad.cv.model;

import java.time.LocalDateTime;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Titulo {

	private ObjectProperty<LocalDateTime> desde = new SimpleObjectProperty<>();
	private ObjectProperty<LocalDateTime> hasta = new SimpleObjectProperty<>();
	private StringProperty denominacion = new SimpleStringProperty();
	private StringProperty organizador = new SimpleStringProperty();

	public final ObjectProperty<LocalDateTime> desdeProperty() {
		return this.desde;
	}

	public final LocalDateTime getDesde() {
		return this.desdeProperty().get();
	}

	public final void setDesde(final LocalDateTime desde) {
		this.desdeProperty().set(desde);
	}

	public final ObjectProperty<LocalDateTime> hastaProperty() {
		return this.hasta;
	}

	public final LocalDateTime getHasta() {
		return this.hastaProperty().get();
	}

	public final void setHasta(final LocalDateTime hasta) {
		this.hastaProperty().set(hasta);
	}

	public final StringProperty denominacionProperty() {
		return this.denominacion;
	}

	public final String getDenominacion() {
		return this.denominacionProperty().get();
	}

	public final void setDenominacion(final String denominacion) {
		this.denominacionProperty().set(denominacion);
	}

	public final StringProperty organizadorProperty() {
		return this.organizador;
	}

	public final String getOrganizador() {
		return this.organizadorProperty().get();
	}

	public final void setOrganizador(final String organizador) {
		this.organizadorProperty().set(organizador);
	}

}
