package regras_negocio;

import java.util.ArrayList;

import modelo.Grupo;
import modelo.Individual;
import modelo.Mensagem;
import repositorio.Repositorio;

public class Fachada {
	
	private static Repositorio repositorio;
	
	public Fachada() {
		repositorio = new Repositorio();
	}

	public static void criarIndividuo(String nomeindividuo, String senha) throws Exception {
		if (Fachada.validarIndividuo(nomeindividuo, senha)) {
			throw new Exception("Esse participante já existe");
		} else {
			repositorio.adicionarParticipante(nomeindividuo, senha);
		}
	}
	
	public static boolean validarIndividuo(String nomeindividuo, String senha) {
		if (repositorio.participanteExiste(nomeindividuo)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void criarAdministrador(String nomeadministrador, String senha) throws Exception {
		if (repositorio.existeAdmin()) {
			throw new Exception("Já existe um administrador nesse repositório");
		} else {
			repositorio.adicionarAdmin(nomeadministrador, senha);
		}
	}
	
	public static void criarGrupo(String nomegrupo) {
		
	}
	
	public static void inserirGrupo(String nomeindividuo, String nomegrupo) {
		
	}
	
	public static void removerGrupo(String nomeindividuo, String nomegrupo) {
		
	}
	
	public static void criarMensagem(String nomeindividuo, String nomedestinatario, String testo) {
		
	}
	
	public static ArrayList<Mensagem> obterConversa(String nomeindividuo, String nomedestinatario) {
		
	}
	
	public static void apagarMensagem(String nomeindividuo, int id) {
		
	}
	
	public static ArrayList<Mensagem> listarMensagensEnviadas(String nomeindividuo) {
		
	}
	
	public static ArrayList<Mensagem> listarMensagensRecebidas(String nomeparticipante) {
		
	}
	
	public static ArrayList<Individual> listarIndividuos() {
		
	}
	
	public static ArrayList<Grupo> listarGrupos() {
		
	}
	
	public static ArrayList<Mensagem> espionarMensagens(String nomeadministrador, String termo) {
		
	}
	
	public static ArrayList<String> ausentes(String nomeadministrador) {
		
	}
	
}
