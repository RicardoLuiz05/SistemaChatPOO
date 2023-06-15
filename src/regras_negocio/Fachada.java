package regras_negocio;

import java.util.ArrayList;


import modelo.Mensagem;
import modelo.Participante;
import repositorio.Repositorio;

public class Fachada {
	private static Repositorio repositorio = new Repositorio();
	private Fachada() {}		

	public static void criarIndividuo(String nomeindividuo, String senha ) throws Exception {
		Participante p = repositorio.localizarIndividuo(senha);
		if (p!=null) 
			throw new Exception("Nao foi possivel criar usario - nome " + nomeindividuo + " já cadastrado!");

		p = new Participante(nomeindividuo);
		repositorio.adicionarParticipante(senha,p);
		repositorio.salvarObjetos();
		
	}
	
	public static boolean validarIndividuo( String nomeindividuo, String senha) throws Exception {
		Participante p = repositorio.localizarIndividuo(senha);
		if (p = false) 
			throw new Exception("Este usuário não existe!");
		
		return true;

		
		
	}
	
	public static void criarAdministrador(String nomeadministrador, String senha) {
		
	}
	
	public static void criarGrupo(String nomegrupo) {
		
	}
	
	public static void inserirGrupo( String nomeindividuo, String nomegrupo) {
		
	}
	
	public static void removerGrupo(String nomeindiviuo, String nomegrupo) {
		
	}
	
	public static void criarMensagem(String nomeindividuo, String nomedestinatario, String texto) {
		
	}
	
	public static ArrayList<Mensagem> obterConversa(String nomeindividuo, String nomedestinatario) {
		
	}
	
	public static void apagarMensagem(String nomeindividuo, int id) {
		
	}
	
	public static ArrayList<Mensagem> listarMensagensEnviadas(String nomeindividuo) {
		
	}
	
	public static ArrayList<Mensagem> listarMensagensRecebidas(String nomeparticipante) {
		
	}
	
	public static ArrayList<String> listarIndividuos() {
		
	}
	
	public static ArrayList<String> listarGrupos() {
		
	}
	
	public static ArrayList<Mensagem> espionarMensagens() {
		
	}
	
	public static ArrayList<String> ausentes(String nomeadministrador){
		
	}

}
