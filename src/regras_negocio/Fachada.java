package regras_negocio;

import java.util.ArrayList;
import java.util.function.Predicate;

import modelo.Grupo;
import modelo.Individual;
import modelo.Mensagem;
import modelo.Participante;
import repositorio.Repositorio;

import java.util.Collections;
import java.util.Comparator;

public class Fachada {
	
	private static Repositorio repositorio = new Repositorio();
	private static int idMensagem = 1;
	
	public static Repositorio getRepositorio() {
		return repositorio;
	}

	public static void criarIndividuo(String nome, String senha) throws Exception {
		if(nome.isEmpty()) 
			throw new Exception("criar individual - nome vazio:");
		if(senha.isEmpty()) 
			throw new Exception("criar individual - senha vazia:");
		
		Participante p = repositorio.localizarParticipante(nome);	
		if(p != null) 
			throw new Exception("criar individual - nome ja existe:" + nome);
		Individual individuo = new Individual(nome,senha, false);
		repositorio.adicionar(individuo);
		repositorio.salvarObjetos();
	}
	
	public static Individual validarIndividuo(String nomeindividuo, String senha) {
		for (Participante p : repositorio.getParticipantes().values()) {
			if (p instanceof Individual i && i.getNome().equals(nomeindividuo) && i.getSenha().equals(senha)) {
				return i;
			}
		}
		return null;
	}
	
	public static void criarAdministrador(String nomeadministrador,String senha) throws Exception {
        if(nomeadministrador.isEmpty())
            throw new Exception("criar individual - nome vazio:");
        if(senha.isEmpty())
            throw new Exception("criar individual - senha vazia:");
        Participante p = repositorio.localizarParticipante(nomeadministrador);
        if(p != null)
            throw new Exception("criar admin - nome ja existe:" + nomeadministrador);
        Individual individuo = new Individual(nomeadministrador, senha, true);
        repositorio.adicionar(individuo);
        repositorio.salvarObjetos();
    }
	
	public static void criarGrupo(String nomegrupo) throws Exception {
		if (repositorio.localizarParticipante(nomegrupo) == null) {
			repositorio.getParticipantes().put(nomegrupo, new Grupo(nomegrupo));
		} else {
			throw new Exception("criar grupo - grupo já existente");
		}
		repositorio.salvarObjetos();
	}
	
	public static void inserirGrupo(String nomeindividuo, String nomegrupo) throws Exception {
		Individual individual = repositorio.localizarIndividual(nomeindividuo);	
		if(individual == null) 
			throw new Exception("remover grupo - individuo nao existe");
		Grupo grupo = repositorio.localizarGrupo(nomegrupo);	
		if(grupo == null) 
			throw new Exception("remover grupo - grupo nao existe");
		if (individual.localizarGrupo(nomegrupo) != null) {
			throw new Exception("inserir grupo - participante já está no grupo");
		} else {
			grupo.addParticipante(individual);
			individual.addGrupo(grupo);
		}
		repositorio.salvarObjetos();
	}
	
	public static void removerGrupo(String nomeindividuo, String nomegrupo) throws Exception {
		Individual individual = repositorio.localizarIndividual(nomeindividuo);	
		if(individual == null) 
			throw new Exception("remover grupo - individuo nao existe");
		Grupo grupo = repositorio.localizarGrupo(nomegrupo);	
		if(grupo == null) 
			throw new Exception("remover grupo - grupo nao existe");
		if (individual.localizarGrupo(nomegrupo) == null) {
			throw new Exception("remover grupo - participante já está fora do grupo");
		} else {
			grupo.rmvParticipante(individual);
			individual.rmvGrupo(grupo);
		}
		repositorio.salvarObjetos();
	}
	
	public static void criarMensagem(String nomeemitente, String nomedestinatario, String texto) throws Exception {
		if(texto.isEmpty()) 
			throw new Exception("criar mensagem - texto vazio:");
		Individual emitente = repositorio.localizarIndividual(nomeemitente);	
		if(emitente == null) 
			throw new Exception("criar mensagem - emitente nao existe:" + nomeemitente);
		Participante destinatario = repositorio.localizarParticipante(nomedestinatario);	
		if(destinatario == null) 
			throw new Exception("criar mensagem - destinatario nao existe:" + nomeemitente);
		if(destinatario instanceof Grupo g && emitente.localizarGrupo(g.getNome())==null)
			throw new Exception("criar mensagem - grupo nao permitido:" + nomedestinatario);
		Mensagem m = new Mensagem(idMensagem, emitente, destinatario, texto);
		emitente.getEnviadas().add(m);
		destinatario.getRecebidas().add(m);
		repositorio.getMensagens().add(m);
		if (destinatario instanceof Grupo grupo) {
			texto = nomeemitente + "/" + texto;
			for (Individual i : grupo.getIndividuos()) {
				if (!i.getNome().equals(nomeemitente)) {
					Mensagem mensagem = new Mensagem(idMensagem, grupo, i, texto);
					grupo.getEnviadas().add(mensagem);
					i.getRecebidas().add(mensagem);
					repositorio.getMensagens().add(mensagem);
				}
			}
		}
		idMensagem++;
		repositorio.salvarObjetos();
	}
	
	public static ArrayList<Mensagem> obterConversa(String nomeindividuo, String nomedestinatario) throws Exception {
		Individual emitente = repositorio.localizarIndividual(nomeindividuo);	
		if(emitente == null) 
			throw new Exception("obter conversa - emitente nao encontrado");
		Participante destinatario = repositorio.localizarParticipante(nomedestinatario);	
		if(destinatario == null) 
			throw new Exception("obter conversa - destinatario nao encontrado");
			ArrayList<Mensagem> enviadas= emitente.getEnviadas();
			ArrayList<Mensagem> recebidas= emitente.getRecebidas();
			ArrayList<Mensagem> conversa= new ArrayList<>();
			for(Mensagem m : enviadas) {
				if(m.getDestinatario().getNome().equals(nomedestinatario)) {
					conversa.add(m);
				}
			}
			for(Mensagem m : recebidas) {
				if(m.getEmitente().getNome().equals(nomeindividuo)) {
					conversa.add(m);
				}
			}
			Collections.sort(conversa,new Comparator<Mensagem>() {
				public int compare(Mensagem m1,Mensagem m2) {
					return Integer.compare(m1.getId(), m2.getId());
				}
			});
			return conversa;
	}
	
	public static void apagarMensagem(String nomeindividuo, int id) throws  Exception{
        Individual emitente = repositorio.localizarIndividual(nomeindividuo);
        if(emitente == null)
            throw new Exception("apagar mensagem - nome nao existe:" + nomeindividuo);
        Mensagem m = emitente.localizarEnviada(id);
        if(m == null)
            throw new Exception("apagar mensagem - mensagem nao pertence a este individuo:" + id);
        emitente.removerEnviada(m);
        Participante destinatario = m.getDestinatario();
        destinatario.removerRecebida(m);
        repositorio.remover(m);
        if(destinatario instanceof Grupo g) {
            ArrayList<Mensagem> lista = destinatario.getEnviadas();
            lista.removeIf(new Predicate<>() {
                @Override
                public boolean test(Mensagem t) {
                    if (t.getId() == m.getId()) {
                        t.getDestinatario().removerRecebida(t);
                        repositorio.remover(t);
                        return true;
                    } else
                        return false;
                }
            });
        }
        repositorio.salvarObjetos();
    }
	
	public static ArrayList<Mensagem> listarMensagens() {
		return repositorio.getMensagens();
	}
	
	public static ArrayList<Mensagem> listarMensagensEnviadas(String nomeindividuo) throws Exception {
		Individual ind = repositorio.localizarIndividual(nomeindividuo);	
		if(ind == null) 
			throw new Exception("listar  mensagens enviadas - nome nao existe:" + nomeindividuo);
		return ind.getEnviadas();
	}
	
	public static ArrayList<Mensagem> listarMensagensRecebidas(String nomeparticipante) throws Exception {
		Participante pat = repositorio.localizarParticipante(nomeparticipante);	
		if(pat == null) 
			throw new Exception("listar  mensagens recebidas - nome nao existe:" + nomeparticipante);
		return pat.getRecebidas();
	}
	
	public static ArrayList<Individual> listarIndividuos() {
		return repositorio.getIndividuos();
	}
	
	public static ArrayList<Grupo> listarGrupos() {
		return repositorio.getGrupos();
	}
	
	public static ArrayList<Mensagem> espionarMensagens(String nomeadministrador, String termo) throws Exception {
		if (repositorio.localizarIndividual(nomeadministrador).getAdministrador() != true)
			throw new Exception("espionar mensagens - " + nomeadministrador + " não é um administrador");
		if (termo.isEmpty()) {
			return repositorio.getMensagens();
		}
		else {
			ArrayList<Mensagem> mensagens = new ArrayList<>();
			for (Mensagem m : repositorio.getMensagens()) {
				if (m.getTexto().contains(termo)) {
					mensagens.add(m);
				}
			}
			return mensagens;
		}
	}
	
	public static ArrayList<String> ausentes(String nomeadministrador) throws Exception {
		if (repositorio.localizarIndividual(nomeadministrador).getAdministrador() != true)
			throw new Exception("espionar mensagens - " + nomeadministrador + " não é um administrador");
		ArrayList<String> ausentes = new ArrayList<>();
		for (Participante p : repositorio.getParticipantes().values()) {
			if (p.getEnviadas().size() == 0) {
				ausentes.add(p.getNome());
			}
		}
		return ausentes;
	}
	
}
