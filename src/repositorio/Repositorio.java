package repositorio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeMap;

import modelo.Grupo;
import modelo.Individual;
import modelo.Mensagem;
import modelo.Participante;

public class Repositorio {
	
	public static int idMensagem = 1;
    private TreeMap<String, Participante> participantes;
    private TreeMap<Integer, Mensagem> mensagens;
    
    public Repositorio() {
    	participantes = new TreeMap<>();
    	mensagens = new  TreeMap<>();
    }

    public TreeMap<String, Participante> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(TreeMap<String, Participante> participantes) {
        this.participantes = participantes;
    }

    public ArrayList<Mensagem> getMensagens() {
    	ArrayList<Mensagem> mensagem = new ArrayList<>();
        for(Mensagem m : mensagens.values()) {
            mensagem.add(m);
        }
        return mensagem;
    }

    public void setMensagens(TreeMap<Integer, Mensagem> mensagens) {
        this.mensagens = mensagens;
    }
    
    public void carregarObjetos() {
        
    }
    
    public void salvarObjetos() {
        
    }
    
    public boolean existeParticipante(String nomeParticipante) {
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
    
    public boolean existeAdmin(String nomeadministrador) {
    	for (Participante a : participantes.values()) {
    		if (a instanceof Individual i && i.getNome().equals(nomeadministrador) && i.getAdministrador() == true) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public void adicionarAdmin(String nome, String senha) throws Exception {
    	participantes.put(nome, new Individual(nome, senha));
    	for (Participante a : participantes.values()) {
    		if (a instanceof Individual i && a.getNome().equals(nome)) {
    			i.setAdministrador(true);
    		}
    	}
    }

    public boolean existeGrupo(String nomegrupo) {
    	for (Participante a : participantes.values()) {
    		if (a instanceof Grupo g && a.getNome().equals(nomegrupo)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public void adicionarGrupo(String nomegrupo) throws Exception {
    	participantes.put(nomegrupo, new Grupo(nomegrupo));
    }
    
    public void addPessoaNoGrupo(String nomeindividuo, String nomegrupo) {
    	Individual pessoa = null;
    	Grupo grupo = null;
    	for (Participante a : participantes.values()) {
    		if (a instanceof Individual i && a.getNome().equals(nomeindividuo)) {
    			pessoa = i;
    			break;

    		}
    	}
    	for (Participante a : participantes.values()) {
	    	if (a instanceof Grupo g && a.getNome().equals(nomegrupo)) {
				grupo = g;
				break;
	    	}
    	}
	    pessoa.addGrupo(grupo);
    	grupo.addParticipante(pessoa);
    	
    }
    
    public void rmvPessoaDoGrupo(String nomeindividuo, String nomegrupo) {
    	Individual pessoa = null;
    	Grupo grupo = null;
    	for (Participante a : participantes.values()) {
    		if (a instanceof Individual i && a.getNome().equals(nomeindividuo)) {
    			pessoa = i;
    			break;

    		}
    	}
    	for (Participante a : participantes.values()) {
	    	if (a instanceof Grupo g && a.getNome().equals(nomegrupo)) {
				grupo = g;
				break;
	    	}
    	}
	    pessoa.rmvGrupo(grupo);
    	grupo.rmvParticipante(pessoa);
    }
    
    public void criarMensagem(String nomeindividuo, String nomedestinatario, String texto) throws Exception {
    	Individual emitente = null;
    	Participante destinatario = null;
    	for (Participante a : participantes.values()) {
    		if (a instanceof Individual i && a.getNome().equals(nomeindividuo)) {
    			emitente = i;
    		}
    	}
    	for (Participante a : participantes.values()) {
    		if (a.getNome().equals(nomedestinatario)) {
    			destinatario = a;
    		}
    	}
    	if (destinatario instanceof Individual) {
    		Mensagem mensagem = new Mensagem(texto, emitente, destinatario);
    		mensagens.put(mensagem.getId(), mensagem);
    		
    	} else if (destinatario instanceof Grupo g) {
    		for (Individual dest : g.getIndividuos()) {
    			if (!dest.equals(emitente)) {
    				Mensagem mensagem = new Mensagem(Repositorio.idMensagem, texto, emitente, dest);
    				mensagens.put(mensagem.getId(), mensagem);
    			}
    		}
    	}
    }
    
    public ArrayList<Mensagem> obterConversa(String nomeindividuo, String nomedestinatario) {
    	ArrayList<Mensagem> conversa = new ArrayList<>();
    	for (Mensagem m : mensagens.values()) {
    		if (m.getEminente().getNome().equals(nomeindividuo) && m.getDestinatario().getNome().equals(nomedestinatario)
    				|| m.getDestinatario().getNome().equals(nomeindividuo) && m.getEminente().getNome().equals(nomedestinatario)) {
    			conversa.add(m);
    		}
    	}
    	Collections.sort(conversa, new Comparator<Mensagem>() {
            public int compare(Mensagem m1,Mensagem m2) {
                return Integer.compare(m1.getId(), m2.getId());
            }
        });
    	return conversa;
    }
    
    public boolean existeMsg(int id) {
    	for (Mensagem m : mensagens.values()) {
    		if (m.getId() == id) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public void excluirMensagem(String nomeindividuo, int id) {
    	for (Mensagem m : mensagens.values()) {
    		if (m.getEminente().getNome().equals(nomeindividuo) && m.getId() == id) {
    			m.setEminente(null);
    			m.setDestinatario(null);
    			mensagens.remove(id);
    		}
    	}
    }
    
    /*
    public ArrayList<Mensagem> listarMensagens() {
    	ArrayList<Mensagem> msgs = new ArrayList<>();
		for (Mensagem m : mensagens.values()) {
			msgs.add(m);
		}
		return msgs;
    }
    */
    
    public ArrayList<Mensagem> enviadas(String nomeindividuo) {
    	ArrayList<Mensagem> enviadas = new ArrayList<>();
    	for (Mensagem m : mensagens.values()) {
    		if (m.getEminente().getNome().equals(nomeindividuo)) {
    			enviadas.add(m);
    		}
    	}
    	return enviadas;
    }
    
    public ArrayList<Mensagem> recebidas(String nomeindividuo) {
    	ArrayList<Mensagem> recebidas = new ArrayList<>();
    	for (Mensagem m : mensagens.values()) {
    		if (m.getDestinatario().getNome().equals(nomeindividuo)) {
    			recebidas.add(m);
    		}
    	}
    	return recebidas;
    }
    
    public ArrayList<Individual> getIndividuos() {
    	ArrayList<Individual> individuos = new ArrayList<>();
    	for (Participante p : participantes.values()) {
    		if (p instanceof Individual i) {
    			individuos.add(i);
    		}
    	}
    	return individuos;
    }
    
    public ArrayList<Grupo> getGrupos() {
    	ArrayList<Grupo> grupos = new ArrayList<>();
    	for (Participante p : participantes.values()) {
    		if (p instanceof Grupo g) {
    			grupos.add(g);
    		}
    	}
    	return grupos;
    }
    
    public ArrayList<Mensagem> espionar(String termo) {
    	ArrayList<Mensagem> msgs = new ArrayList<>();
    	for (Mensagem m : mensagens.values()) {
    		if (m.getTexto().contains(termo)) {
    			msgs.add(m);
    		}
    	}
    	return msgs;
    }
    
    public boolean mandouMsg(String nome) {
    	for (Mensagem m : mensagens.values()) {
    		if (m.getEminente().getNome().equals(nome)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public ArrayList<String> ausentes() {
    	ArrayList<String> ausentes = new ArrayList<>();
    	for (Participante p : participantes.values()) {
    		if (p instanceof Individual i && !this.mandouMsg(i.getNome())) {
    			ausentes.add(i.getNome());
    		}
    	}
    	return ausentes;
    }
    
	@Override
	public String toString() {
		return "Repositorio [participantes=" + participantes + ", mensagens=" + mensagens + "]";
	}
    
}