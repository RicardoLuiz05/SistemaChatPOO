package modelo;

import java.util.ArrayList;

import regras_negocio.Fachada;

public class Participante {
	
	private String nome;
	private ArrayList<Mensagem> recebidas = new ArrayList<>();
	private ArrayList<Mensagem> enviadas = new ArrayList<>();
	
	public Participante(String nome) throws Exception {
		this.setNome(nome);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) throws Exception {
		if (nome.equals("") || Fachada.getRepositorio().existeParticipante(nome)) {
			throw new Exception("Nome vazio ou já existente");
		}
		this.nome = nome;
	}

	public ArrayList<Mensagem> getRecebidas() {
		return recebidas;
	}

	public void setRecebidas(ArrayList<Mensagem> recebidas) {
		this.recebidas = recebidas;
	}

	public ArrayList<Mensagem> getEnviadas() {
		return enviadas;
	}

	public void setEnviadas(ArrayList<Mensagem> enviadas) {
		this.enviadas = enviadas;
	}

	@Override
	public String toString() {
		return "Participante [nome=" + nome + ", recebidas=" + recebidas + ", enviadas=" + enviadas + "]";
	}

}
