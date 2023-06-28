package modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Mensagem {
	
	private int id;
	private String texto;
	private Participante emitente;
	private Participante destinatario;
	private LocalDateTime datahora;
	
	public Mensagem(int id, Participante emitente, Participante destinatario, String texto) throws Exception {
		super();
		this.setId(id);
		this.setTexto(texto);
		this.emitente = emitente;
		this.setDestinatario(destinatario);
		this.setDatahora(LocalDateTime.now());
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
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
	
	public Participante getEmitente() {
		return emitente;
	}
	
	public void setEmitente(Participante eminente) {
		this.emitente = eminente;
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
        return "---> " + id + " texto= " + texto + ", eminente= " + emitente.getNome() + ", destinatario= " + destinatario.getNome()
                + ", datahora=" + datahora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "\n";
    }
	
}


