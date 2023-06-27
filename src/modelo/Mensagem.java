package modelo;

import java.time.LocalDateTime;

import repositorio.Repositorio;

public class Mensagem {
	
	private int id;
	private String texto;
	private Participante eminente;
	private Participante destinatario;
	private LocalDateTime datahora;
	
	public Mensagem(String texto, Participante eminente, Participante destinatario) throws Exception {
		super();
		this.setId();
		this.setTexto(texto);
		this.eminente = eminente;
		this.setDestinatario(destinatario);
		this.setDatahora(LocalDateTime.now());
	}
	
	public Mensagem(int id, String texto, Participante eminente, Participante destinatario) throws Exception {
		super();
		this.setId(id);
		this.setTexto(texto);
		this.eminente = eminente;
		this.setDestinatario(destinatario);
		this.setDatahora(LocalDateTime.now());
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setId() {
		this.setId(Repositorio.idMensagem);
		Repositorio.idMensagem++;
	}
	
	public String getTexto() {
		return texto;
	}
	
	public void setTexto(String texto) throws Exception {
		if (texto.isEmpty()) {
			throw new Exception("Uma mensagem não pode ter texto vazio");
		} else {
			this.texto = texto;
		}
	}
	
	public Participante getEminente() {
		return eminente;
	}
	
	public void setEminente(Participante eminente) {
		this.eminente = eminente;
	}
	
	public Participante getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(Participante destinatario) {
		this.destinatario = destinatario;
	}

	public LocalDateTime getDatahora() {
		return datahora;
	}
	
	public void setDatahora(LocalDateTime datahora) {
		this.datahora = datahora;
	}

	@Override
	public String toString() {
		return "Mensagem [id=" + id + ", texto=" + texto + ", eminente=" + eminente.getNome() + ", destinatario=" + destinatario.getNome()
				+ ", datahora=" + datahora + "]";
	}
	
	}


