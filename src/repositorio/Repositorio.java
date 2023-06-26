package repositorio;

import java.util.TreeMap;

import modelo.Individual;
import modelo.Mensagem;
import modelo.Participante;

public class Repositorio {
	
	public static int idMensagem = 0;
    private TreeMap<String, Participante> participantes;
    private TreeMap<Integer, Mensagem> mensagens;
    
    public Repositorio() {
    	participantes = new TreeMap<>();
    	mensagens = new  TreeMap<>();
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
    
    public boolean participanteExiste(String nomeParticipante) {
    	for (String nome : participantes.keySet()) {
    		if (nome.equals(nomeParticipante)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public void adicionarParticipante(String nome, String senha) throws Exception {
    	participantes.put(nome, new Individual(nome, senha));
    }
    
    public void adicionarAdmin(String nome, String senha) throws Exception {
    	participantes.put(nome, new Individual(nome, senha));
    	for (Participante a : participantes.values()) {
    		if (a instanceof Individual i && a.getNome().equals(nome)) {
    			i.setAdministrador(true);
    		}
    	}
    }
    
    public boolean existeAdmin() {
    	for (Participante a : participantes.values()) {
    		if (a instanceof Individual i && i.getAdministrador() == true) {
    			return true;
    		}
    	}
    	return false;
    }
    
	@Override
	public String toString() {
		return "Repositorio [participantes=" + participantes + ", mensagens=" + mensagens + "]";
	}
    
}