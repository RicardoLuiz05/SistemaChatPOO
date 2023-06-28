package regras_negocio;

import java.util.ArrayList;
import java.util.function.Predicate;

import modelo.Grupo;
import modelo.Individual;
import modelo.Mensagem;
import modelo.Participante;
import repositorio.Repositorio;

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
	}
	
	public static boolean validarIndividuo(String nomeindividuo, String senha) {
		if (repositorio.existeParticipante(nomeindividuo)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void criarAdministrador(String nomeadministrador, String senha) throws Exception {
		if (repositorio.existeAdmin(nomeadministrador)) {
			throw new Exception("Esse administrador já existe");
		} else {
			repositorio.adicionarAdmin(nomeadministrador, senha);
		}
	}
	
	public static void criarGrupo(String nomegrupo) throws Exception {
		if (repositorio.existeGrupo(nomegrupo)) {
			throw new Exception("Esse grupo já existe");
		} else {
			repositorio.adicionarGrupo(nomegrupo);
		}
	}
	
	public static void inserirGrupo(String nomeindividuo, String nomegrupo) throws Exception {
		if (!repositorio.existeParticipante(nomeindividuo)) {
			throw new Exception("Individuo inexistente");
		} else if (!repositorio.existeGrupo(nomegrupo)) {
			throw new Exception("Grupo inexistente");
		} else {
			repositorio.addPessoaNoGrupo(nomeindividuo, nomegrupo);
		}
	}
	
	public static void removerGrupo(String nomeindividuo, String nomegrupo) throws Exception {
		if (!repositorio.existeParticipante(nomeindividuo)) {
			throw new Exception("Individuo inexistente");
		} else if (!repositorio.existeGrupo(nomegrupo)) {
			throw new Exception("Grupo inexistente");
		} else {
			repositorio.rmvPessoaDoGrupo(nomeindividuo, nomegrupo);
		}
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
		repositorio.getMensagens().put(m.getId(), m);
		if (destinatario instanceof Grupo grupo) {
			for (Individual i : grupo.getIndividuos()) {
				Mensagem mensagem = new Mensagem(idMensagem, grupo, i, texto);
				grupo.getEnviadas().add(mensagem);
				i.getRecebidas().add(mensagem);
			}
		}
		idMensagem++;
	}
	
	public static ArrayList<Mensagem> obterConversa(String nomeindividuo, String nomedestinatario) throws Exception {
		if (!repositorio.existeParticipante(nomeindividuo)) {
			throw new Exception("Emitente inexistente");
		} else if (!repositorio.existeParticipante(nomedestinatario)) {
			throw new Exception("Destinatario inexistente");
		} else {
			return repositorio.obterConversa(nomeindividuo, nomedestinatario);
		}
	}
	
	public static void apagarMensagem(String nomeindividuo, int id) throws Exception {
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
			lista.removeIf(new Predicate<Mensagem>() {
				public boolean test(Mensagem t) {
					if(t.getId() == m.getId()) {
						t.getDestinatario().removerRecebida(t);
						repositorio.remover(t);	
						return true;
					}
					else
						return false;
				}

			});

		}
	}
	
	public static ArrayList<Mensagem> listarMensagens() {
		ArrayList<Mensagem> mensagens = new ArrayList<>();
		for (Mensagem m : repositorio.getMensagens().values()) {
			mensagens.add(m);
		}
		return mensagens;
	}
	
	public static ArrayList<Mensagem> listarMensagensEnviadas(String nome) throws Exception {
		Individual ind = repositorio.localizarIndividual(nome);	
		if(ind == null) {
			throw new Exception("listar  mensagens enviadas - nome nao existe:" + nome);
		}
		return ind.getEnviadas();
	}
	
	public static ArrayList<Mensagem> listarMensagensRecebidas(String nomeparticipante) throws Exception {
		if (!repositorio.existeParticipante(nomeparticipante)) {
			throw new Exception("Participante inexistente");
		} else {
			return repositorio.recebidas(nomeparticipante);
		}
	}
	
	public static ArrayList<Individual> listarIndividuos() {
		return repositorio.getIndividuos();
	}
	
	public static ArrayList<Grupo> listarGrupos() {
		return repositorio.getGrupos();
	}
	
	public static ArrayList<Mensagem> espionarMensagens(String nomeadministrador, String termo) throws Exception {
		if (!repositorio.existeAdmin(nomeadministrador)) {
			throw new Exception(nomeadministrador + " não é um administrador");
		} else {
			return repositorio.espionar(termo);
		}
	}
	
	public static ArrayList<String> ausentes(String nomeadministrador) throws Exception {
		if (!repositorio.existeAdmin(nomeadministrador)) {
			throw new Exception(nomeadministrador + " não é um administrador");
		} else {
			return repositorio.ausentes();
		}
	}
	
}
