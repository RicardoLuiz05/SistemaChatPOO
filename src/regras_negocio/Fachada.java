package regras_negocio;

import java.util.ArrayList;

import modelo.Grupo;
import modelo.Individual;
import modelo.Mensagem;
import repositorio.Repositorio;

public class Fachada {
	
	private static Repositorio repositorio = new Repositorio();
	
	public static Repositorio getRepositorio() {
		return repositorio;
	}

	public static void criarIndividuo(String nomeindividuo, String senha) throws Exception {
		if (Fachada.validarIndividuo(nomeindividuo, senha)) {
			throw new Exception("Esse participante já existe");
		} else {
			repositorio.adicionarParticipante(nomeindividuo, senha);
		}
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
	
	public static void criarMensagem(String nomeindividuo, String nomedestinatario, String texto) throws Exception {
		if (!repositorio.existeParticipante(nomeindividuo)) {
			throw new Exception("Emitente inexistente");
		} else if (!repositorio.existeParticipante(nomedestinatario)) {
			throw new Exception("Destinatario inexistente");
		} else {
			repositorio.criarMensagem(nomeindividuo, nomedestinatario, texto);
		}
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
		if (!repositorio.existeParticipante(nomeindividuo)) {
			throw new Exception("Individuo inexistente");
		} else if (!repositorio.existeMsg(id)) {
			throw new Exception("Mensagem inexistente");
		} else {
			repositorio.excluirMensagem(nomeindividuo, id);
		}
	}
	
	public static ArrayList<Mensagem> listarMensagens() {
		return repositorio.getMensagens();
	}
	
	public static ArrayList<Mensagem> listarMensagensEnviadas(String nomeindividuo) throws Exception {
		if (!repositorio.existeParticipante(nomeindividuo)) {
			throw new Exception("Individuo inexistente");
		} else {
			return repositorio.enviadas(nomeindividuo);
		}
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
