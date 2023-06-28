package repositorio;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.TreeMap;

import modelo.Grupo;
import modelo.Individual;
import modelo.Mensagem;
import modelo.Participante;

public class Repositorio {
	
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

    public TreeMap<Integer, Mensagem> getMensagens() {
    	return mensagens;
    }

    public void setMensagens(TreeMap<Integer, Mensagem> mensagens) {
        this.mensagens = mensagens;
    }
    
    public Individual localizarIndividual(String nome) {
    	for (Participante participante : participantes.values()) {
    		if (participante instanceof Individual i && participante.getNome().equals(nome)) {
    			return i;
    		}
    	}
    	return null;
    }
    
    public Participante localizarParticipante(String nome) {
    	for (Participante participante : participantes.values()) {
    		if (participante.getNome().equals(nome)) {
    			return participante;
    		}
    	}
    	return null;
    }
    
    public void adicionar(Participante participante) {
    	participantes.put(participante.getNome(), participante);
    }
    
    public void adicionar(Mensagem mensagem) {
    	mensagens.put(mensagem.getId(), mensagem);
    }
    
    public void remover(Mensagem mensagem) {
    	for (Mensagem m : mensagens.values()) {
    		if (m.equals(mensagem)) {
    			mensagens.remove(mensagem.getId());
    		}
    	}
    }
    
    /*===============================================================================*/
    
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
    
    public ArrayList<Mensagem> obterConversa(String nomeindividuo, String nomedestinatario) {
    	ArrayList<Mensagem> conversa = new ArrayList<>();
    	for (Mensagem m : mensagens.values()) {
    		if (m.getEmitente().getNome().equals(nomeindividuo) && m.getDestinatario().getNome().equals(nomedestinatario)
    				|| m.getDestinatario().getNome().equals(nomeindividuo) && m.getEmitente().getNome().equals(nomedestinatario)) {
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
    
    public ArrayList<Mensagem> enviadas(String nomeindividuo) {
    	ArrayList<Mensagem> enviadas = new ArrayList<>();
    	for (Mensagem m : mensagens.values()) {
    		if (m.getEmitente().getNome().equals(nomeindividuo)) {
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
    		if (m.getEmitente().getNome().equals(nome)) {
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
	
	public void carregarObjetos()  	{
		// carregar para o repositorio os objetos dos arquivos csv
		try {
			//caso os arquivos nao existam, serao criados vazios
			File f1 = new File( new File(".\\mensagens.csv").getCanonicalPath() ) ; 
			File f2 = new File( new File(".\\individuos.csv").getCanonicalPath() ) ; 
			File f3 = new File( new File(".\\grupos.csv").getCanonicalPath() ) ; 
			if (!f1.exists() || !f2.exists() || !f3.exists() ) {
				//System.out.println("criando arquivo .csv vazio");
				FileWriter arquivo1 = new FileWriter(f1); arquivo1.close();
				FileWriter arquivo2 = new FileWriter(f2); arquivo2.close();
				FileWriter arquivo3 = new FileWriter(f3); arquivo3.close();
				return;
			}
		}
		catch(Exception ex)		{
			throw new RuntimeException("criacao dos arquivos vazios:"+ex.getMessage());
		}

		String linha;	
		String[] partes;	

		try	{
			String nome,senha,administrador;
			File f = new File( new File(".\\individuos.csv").getCanonicalPath())  ;
			Scanner arquivo1 = new Scanner(f);	 //  pasta do projeto
			while(arquivo1.hasNextLine()) 	{
				linha = arquivo1.nextLine().trim();	
				partes = linha.split(";");
				//System.out.println(Arrays.toString(partes));
				nome = partes[0];
				senha = partes[1];
				administrador = partes[2];
				Individual ind = new Individual(nome,senha,Boolean.parseBoolean(administrador));
				this.adicionar(ind);
			}
			arquivo1.close();
		}
		catch(Exception ex)		{
			throw new RuntimeException("leitura arquivo de individuos:"+ex.getMessage());
		}

		try	{
			String nome;
			Grupo grupo;
			Individual individuo;
			File f = new File( new File(".\\grupos.csv").getCanonicalPath())  ;
			Scanner arquivo2 = new Scanner(f);	 //  pasta do projeto
			while(arquivo2.hasNextLine()) 	{
				linha = arquivo2.nextLine().trim();	
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
			arquivo2.close();
		}
		catch(Exception ex)		{
			throw new RuntimeException("leitura arquivo de grupos:"+ex.getMessage());
		}


		try	{
			String id, nomeemitente, nomedestinatario,texto;
			Mensagem m;
			Participante emitente,destinatario;
			File f = new File( new File(".\\mensagens.csv").getCanonicalPath() )  ;
			Scanner arquivo3 = new Scanner(f);	 //  pasta do projeto
			while(arquivo3.hasNextLine()) 	{
				linha = arquivo3.nextLine().trim();		
				partes = linha.split(";");	
				//System.out.println(Arrays.toString(partes));
				id = partes[0];
				nomeemitente = partes[1];
				nomedestinatario = partes[2];
				texto = partes[3];
				emitente = this.localizarParticipante(nomeemitente);
				destinatario = this.localizarParticipante(nomedestinatario);
				m = new Mensagem(Integer.parseInt(id),emitente,destinatario,texto);
				this.adicionar(m);
			} 
			arquivo3.close();
		}
		catch(Exception ex)		{
			throw new RuntimeException("leitura arquivo de mensagens:"+ex.getMessage());
		}

	}


	public void	salvarObjetos()  {
		//gravar nos arquivos csv os objetos que estão no repositório
		try	{
			File f = new File( new File(".\\mensagens.csv").getCanonicalPath())  ;
			FileWriter arquivo1 = new FileWriter(f); 
			for(Mensagem m : mensagens.values()) 	{
				arquivo1.write(	m.getId()+";"+
						m.getEmitente().getNome()+";"+
						m.getDestinatario().getNome()+";"+
						m.getTexto()+"\n");	
			} 
			arquivo1.close();
		}
		catch(Exception e){
			throw new RuntimeException("problema na criação do arquivo  mensagens "+e.getMessage());
		}

		try	{
			File f = new File( new File(".\\individuos.csv").getCanonicalPath())  ;
			FileWriter arquivo2 = new FileWriter(f) ; 
			for(Individual ind : this.getIndividuos()) {
				arquivo2.write(ind.getNome() +";"+ ind.getSenha() +";"+ ind.getAdministrador() +"\n");	
			} 
			arquivo2.close();
		}
		catch (Exception e) {
			throw new RuntimeException("problema na criação do arquivo  individuos "+e.getMessage());
		}

		try	{
			File f = new File( new File(".\\grupos.csv").getCanonicalPath())  ;
			FileWriter arquivo3 = new FileWriter(f) ; 
			for(Grupo g : this.getGrupos()) {
				String texto="";
				for(Individual ind : g.getIndividuos())
					texto += ";" + ind.getNome();
				arquivo3.write(g.getNome() + texto + "\n");	
			} 
			arquivo3.close();
		}
		catch (Exception e) {
			throw new RuntimeException("problema na criação do arquivo  grupos "+e.getMessage());
		}
	}
    
}