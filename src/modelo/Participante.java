package modelo;

import java.util.ArrayList;

public class Participante {
	
	private String nome;
	private ArrayList<Mensagem> recebidas = new ArrayList<>();
	private ArrayList<Mensagem> enviadas = new ArrayList<>();
	
	public Participante(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
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
	
	public void adicionarEnviada(Mensagem mensagem) {
		this.enviadas.add(mensagem);
	}
	
	public void removerRecebida(Mensagem mensagem) {
		for (Mensagem m : this.getRecebidas()) {
			if (m.equals(mensagem)) {
				this.getRecebidas().remove(m);
			}
		}
	}
	
	public void removerEnviadas(Mensagem msg){
        enviadas.remove(msg);
    }
	
	public void removerRecebidas(Mensagem msg){
        recebidas.remove(msg);
    }

	@Override
	public String toString() {
        String string = "Nome=" + nome + "\n Mensagens enviadas: ";
            for(Mensagem m : enviadas) {
                string += "\n  --> " + m;
            }
        string += " Mensagens recebidas: ";
            for(Mensagem m : recebidas) {
                string += "\n  --> " + m;
            }
        return string;
    }

}
