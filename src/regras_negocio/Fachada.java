package regras_negocio;

import java.util.ArrayList;


import modelo.Mensagem;
import modelo.Participante;
import repositorio.Repositorio;

public class Fachada {
	private static Repositorio repositorio = new Repositorio();
	private Fachada() {}		

	public static void criarIndividuo(String nomeindividuo, String senha ) throws Exception {
		if(nome.isEmpty())
			throw new Exception("criar individual - nome vazio");
		if(senha.isEmpty())
			throw new Exception("criar individual - senha vazia");

		Participante p = Repositorio.localizarIndividuo(nome);
		if(p != null)
			throw new Exception("criar individual - nome " + nome + "já existe");


		Individual individuo = new Individual(nome,senha, false);
		Repositorio.adicionar(individuo);
		Repositorio.salvarObjetos();

	}
	
	public static boolean validarIndividuo( String nomeindividuo, String senha) throws Exception {
		Participante p = repositorio.localizarIndividuo(senha);
		if (p = false) 
			throw new Exception("Este usuário não existe!");
		
		return true;

	}
	
	public static void criarAdministrador(String nomeadministrador, String senha) {
		if(nome.isEmpty())
			throw new Exception("criar individual - nome vazio");
		if(senha.isEmpty())
			throw new Exception("criar individual - senha vazia");

		Participante p = Repositorio.localizarIndividuo(nome);
		if(p != null)
			throw new Exception("criar individual - nome " + nome + "já existe");

		Individual individuo = new Individual(nome,senha, true);
		Repositorio.adicionar(individuo);
		Repositorio.salvarObjetos();
	}
	
	public static void criarGrupo(String nomegrupo) {
		//localizar nome no repositorio
		//criar o grupo
	}
	
	public static void inserirGrupo( String nomeindividuo, String nomegrupo) {
		//localizar nomeindividuo no repositorio
		//localizar nomegrupo no repositorio
		//verificar se individuo nao esta no grupo
		//adicionar individuo com o grupo e vice-versa
	}
	
	public static void removerGrupo(String nomeindiviuo, String nomegrupo) {
		//localizar nomeindividuo no repositorio
		//localizar nomegrupo no repositorio
		//verificar se individuo ja esta no grupo
		//remover individuo com o grupo e vice-versa
	}
	
	public static void criarMensagem(String nomeindividuo, String nomedestinatario, String texto) {

		if(texto.isEmpty())
			throw new Exception("criar mensagem - texto vazio:");

		Individual emitente = Repositorio.localizarIndividuo(nomeindividuo);
		if(emitente == null)
			throw new Exception("criar mensagem - emitente nao existe:" + nomeindividuo);

		Participante destinatario = Repositorio.localizarParticipante(nomedestinatario);
		if(destinatario == null)
			throw new Exception("criar mensagem - destinatario nao existe:" + nomedestinatario);

		if(destinatario instanceof Grupo g && emitente.localizarGrupo(g.getNome())==null)
			throw new Exception("criar mensagem - grupo nao permitido:" + nomedestinatario);


		//cont.
		//gerar id no repositorio para a mensagem
		//criar mensagem
		//adicionar mensagem ao emitente e destinatario
		//adicionar mensagem ao repositorio
		//
		//caso destinatario seja tipo Grupo então criar copias da mensagem, tendo o grupo como emitente e cada membro do grupo como destinatario,
		//  usando mesmo id e texto, e adicionar essas copias no repositorio

	}
		
	}
	
	public static ArrayList<Mensagem> obterConversa(String nomeindividuo, String nomedestinatario) {
		//localizar emitente no repositorio
		//localizar destinatario no repositorio
		//obter do emitente a lista  enviadas
		//obter do emitente a lista  recebidas

		//criar a lista conversa
		//Adicionar na conversa as mensagens da lista enviadas cujo destinatario é igual ao parametro destinatario
		//Adicionar na conversa as mensagens da lista recebidas cujo emitente é igual ao parametro destinatario
		//ordenar a lista conversa pelo id das mensagens
		//retornar a lista conversa
	}
	
	public static void apagarMensagem(String nomeindividuo, int id) {
		Individual emitente = 	Repositorio.localizarIndividuo(nomeindividuo);
		if(emitente == null)
			throw new Exception("apagar mensagem - nome nao existe:" + nomeindividuo);

		Mensagem m = emitente.localizarEnviada(id);
		if(m == null)
			throw new Exception("apagar mensagem - mensagem nao pertence a este individuo:" + id);

		emitente.removerEnviada(m);
		Participante destinatario = m.getDestinatario();
		destinatario.removerRecebida(m);
		Repositorio.remover(m);

		if(destinatario instanceof Grupo g) {
			ArrayList<Mensagem> lista = destinatario.getEnviadas();
			lista.removeIf(new Predicate<Mensagem>() {
				@Override
				public boolean test(Mensagem t) {
					if(t.getId() == m.getId()) {
						t.getDestinatario().removerRecebida(t);
						Repositorio.remover(t);
						return true;
					}
					else
						return false;
				}

			});
			Repositorio.salvarObjetos();
		}
	}
		
	}
	
	public static ArrayList<Mensagem> listarMensagensEnviadas(String nomeindividuo) throws Exception {

		Individual ind = Repositorio.localizarIndividuo(senha);
		if(ind == null)
			throw new Exception("listar  mensagens enviadas - nome nao existe:" + nome);

		return ind.getEnviadas();

	}
	
	public static ArrayList<Mensagem> listarMensagensRecebidas(String nomeparticipante) {
		
	}
	
	public static ArrayList<String> listarIndividuos() {
		
	}
	
	public static ArrayList<String> listarGrupos() {
		
	}
	
	public static ArrayList<Mensagem> espionarMensagens() {
		//localizar individuo no repositorio
		//verificar se individuo é administrador
		//listar as mensagens que contem o termo no texto
	}
	
	public static ArrayList<String> ausentes(String nomeadministrador){
		//localizar individuo no repositorio
		//verificar se individuo é administrador
		//listar os nomes dos participante que nao enviaram mensagens
	}

}
