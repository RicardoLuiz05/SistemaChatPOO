package repositorio;

import java.util.TreeMap;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import modelo.Mensagem;
import modelo.Participante;

public class Repositorio {
	private TreeMap<String, Participante> participantes = new TreeMap<>();
	private TreeMap<Integer, Mensagem> mensagens = new  TreeMap<>();
	
	public Repositorio() {
		carregarDados();
	}
	
	public Participante localizarIndividuo(String senha) {
		return(participantes.get(senha));
	}
	
	public void adicionarParticipante(String senha, Participante p) {
		participantes.put(senha, p);
	}
	public void carregarObjetos()  	{
		try {

			File msgArq = new File( new File(".\\mensagens.csv").getCanonicalPath() ) ;
			File individuoArq = new File( new File(".\\individuos.csv").getCanonicalPath() ) ;
			File grpArq = new File( new File(".\\grupos.csv").getCanonicalPath() ) ;

			if (!msgArq.exists() || !individuoArq.exists() || !grpArq.exists() ) {
				FileWriter msgArq = new FileWriter(f1); msgArq.close();
				FileWriter individuoArq = new FileWriter(f2); individuoArq.close();
				FileWriter grpArq = new FileWriter(f3); grpArq.close();
				return;
			}

		} 	catch(Exception ex)	{
				throw new RuntimeException("criacao dos arquivos vazios:"+ex.getMessage());
			}

			String linha;
			String[] partes;

			try	{
				String nome,senha,administrador;
				File f = new File( new File(".\\individuos.csv").getCanonicalPath())  ;
				Scanner individuoArq = new Scanner(f);	 //  pasta do projeto
				while(individuoArq.hasNextLine()) 	{
					linha = individuoArq.nextLine().trim();
					partes = linha.split(";");
					nome = partes[0];
					senha = partes[1];
					administrador = partes[2];
					Individual ind = new Individual(nome,senha,Boolean.parseBoolean(administrador));
					this.adicionar(ind);
				}

				individuoArq.close();

			} catch(Exception ex) {
				throw new RuntimeException("leitura arquivo de individuos:"+ex.getMessage());
			}

			try	{
				String nome;
				Grupo grupo;
				Individual individuo;
				File f = new File( new File(".\\grupos.csv").getCanonicalPath())  ;
				Scanner grpArq = new Scanner(f);	 //  pasta do projeto
				while(grpArq.hasNextLine()) 	{
					linha = grpArq.nextLine().trim();
					partes = linha.split(";");
					//System.out.println(Arrays.toString(partes));
					nome = partes[0];
					grupo = new Grupo(nome);
					if(partes.length>1)
						for(int i=1; i< partes.length; i++) {
							individuo = this.localizarIndividual(partes[i]);
							grupo.adicionar(individuo);
						}
					this.adicionar(grupo);
				}

				grpArq.close();

			} catch(Exception ex)	{
				throw new RuntimeException("leitura arquivo de grupos:"+ex.getMessage());
			}


			try	{
				String id, nomeemitente, nomedestinatario,texto;
				Mensagem m;
				Participante emitente,destinatario;
				File f = new File( new File(".\\mensagens.csv").getCanonicalPath() )  ;
				Scanner msgArq = new Scanner(f);
				while(msgArq.hasNextLine()) 	{
					linha = msgArq.nextLine().trim();
					partes = linha.split(";");
					id = partes[0];
					nomeemitente = partes[1];
					nomedestinatario = partes[2];
					texto = partes[3];
					emitente = this.localizarParticipante(nomeemitente);
					destinatario = this.localizarParticipante(nomedestinatario);
					m = new Mensagem(Integer.parseInt(id),emitente,destinatario,texto);
					this.adicionar(m);
				}

				msgArq.close();

			} catch(Exception ex)	{
				throw new RuntimeException("leitura arquivo de mensagens:"+ex.getMessage());
			}

		}
	public void	salvarObjetos()  {
		try	{

			File f = new File( new File(".\\mensagens.csv").getCanonicalPath())  ;
			FileWriter msgArq = new FileWriter(f);

			for(Mensagem m : mensagens.values()) {
				msgArq.write(	m.getId()+";"+
				m.getEmitente().getNome()+";"+
				m.getDestinatario().getNome()+";"+
				m.getTexto()+"\n");
			}

			msgArq.close();

		} catch(Exception e){
			throw new RuntimeException("problema na criação do arquivo  mensagens "+e.getMessage());
		}

		try	{
			File f = new File( new File(".\\individuos.csv").getCanonicalPath())  ;
			FileWriter individuoArq = new FileWriter(f) ;

			for(Individual ind : this.getIndividuos()) {
				individuoArq.write(ind.getNome() +";"+ ind.getSenha() +";"+ ind.getAdministrador() +"\n");
			}

			individuoArq.close();

		} catch (Exception e) {
			throw new RuntimeException("problema na criação do arquivo  individuos "+e.getMessage());
		}

		try	{

			File f = new File( new File(".\\grupos.csv").getCanonicalPath())  ;
			FileWriter grpArq = new FileWriter(f) ;

			for(Grupo g : this.getGrupos()) {

				String texto="";

				for(Individual ind : g.getIndividuos())
				texto += ";" + ind.getNome();
					grpArq.write(g.getNome() + texto + "\n");
			}

			grpArq.close();

		} catch (Exception e) {
			throw new RuntimeException("problema na criação do arquivo  grupos "+e.getMessage());
		}
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
	
	
}
