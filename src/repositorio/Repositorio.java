package repositorio;

import java.util.TreeMap;

import modelo.Mensagem;
import modelo.Participante;

public class Repositorio {
	private TreeMap<String, Participante> participantes = new TreeMap<>();
	private TreeMap<Integer, Mensagem> mensagens = new  TreeMap<>();
	
	public Repositorio() {
		carregarObjetos();
	}
	
	public Participante localizarIndividuo(String senha) {
		return(participantes.get(senha));
		
		
	}
	
	public void adicionarParticipante(String senha, Participante p) {
		participantes.put(senha, p);
	}
	

	public void carregarObjetos() {
		
	}
	
	public void salvarObjetos() {
		
	}



	public TreeMap<String, Participante> getParticipantes() {
		return participantes;
	}



	public void setParticipantes(TreeMap<String, Participante> participantes) {
		this.participantes = participantes;
	}



	public TreeMap<Integer, Mensagem> getMensagens() {
		return mensagens;
	}



	public void setMensagens(TreeMap<Integer, Mensagem> mensagens) {
		this.mensagens = mensagens;
	}
	
	
}
