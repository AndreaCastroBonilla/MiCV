package dad.cv.model;

public class CV {

	private Personal personal;
	private Contacto contacto;
	private Titulo titulo;
	private Experiencia experiencia;
	private Conocimiento conocimiento;

	public CV() {
		super();
	}

	public CV(Personal personal, Contacto contacto, Titulo titulo, Experiencia experiencia, Conocimiento conocimiento) {
		super();
		this.personal = personal;
		this.contacto = contacto;
		this.titulo = titulo;
		this.experiencia = experiencia;
		this.conocimiento = conocimiento;
	}

	public Personal getPersonal() {
		return personal;
	}

	public void setPersonal(Personal personal) {
		this.personal = personal;
	}

	public Contacto getContacto() {
		return contacto;
	}

	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}

	public Titulo getTitulo() {
		return titulo;
	}

	public void setTitulo(Titulo titulo) {
		this.titulo = titulo;
	}

	public Experiencia getExperiencia() {
		return experiencia;
	}

	public void setExperiencia(Experiencia experiencia) {
		this.experiencia = experiencia;
	}

	public Conocimiento getConocimiento() {
		return conocimiento;
	}

	public void setConocimiento(Conocimiento conocimiento) {
		this.conocimiento = conocimiento;
	}

	@Override
	public String toString() {
		return "CV [personal=" + personal + ", contacto=" + contacto + ", titulo=" + titulo + ", experiencia="
				+ experiencia + ", conocimiento=" + conocimiento + "]";
	}

}
