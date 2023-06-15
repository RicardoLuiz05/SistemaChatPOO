package modelo;

import java.time.LocalDateTime;

public class Mensagem {
	private int id;
	private String texto;
	private Participante eminente;
	private LocalDateTime datahora;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public Participante getEminente() {
		return eminente;
	}
	public void setEminente(Participante eminente) {
		this.eminente = eminente;
	}
	public LocalDateTime getDatahora() {
		return datahora;
	}
	public void setDatahora(LocalDateTime datahora) {
		this.datahora = datahora;
	}
	@Override
	public String toString() {
		return "Mensagem [id=" + id + ", texto=" + texto + ", eminente=" + eminente + ", datahora=" + datahora + "]";
	}
	

}
