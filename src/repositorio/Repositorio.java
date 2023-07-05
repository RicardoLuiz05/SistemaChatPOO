package repositorio;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

import modelo.Grupo;
import modelo.Individual;
import modelo.Mensagem;
import modelo.Participante;

public class Repositorio {
	
    private TreeMap<String, Participante> participantes;
    private ArrayList<Mensagem> mensagens;
    
    public Repositorio() {
    	participantes = new TreeMap<>();
    	mensagens = new ArrayList<>();
    	this.carregarObjetos();
    }

    public TreeMap<String, Participante> getParticipantes() {
        return participantes;
    }

    public ArrayList<Mensagem> getMensagens() {
    	return mensagens;
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
    
    public Grupo localizarGrupo(String nome) {
    	for (Participante participante : participantes.values()) {
    		if (participante instanceof Grupo g && participante.getNome().equals(nome)) {
    			return g;
    		}
    	}
    	return null;
    }
    
    public void adicionar(Participante participante) {
    	participantes.put(participante.getNome(), participante);
    }
    
    public void adicionar(Mensagem mensagem) {
    	mensagens.add(mensagem);
    }
    
    public void remover(Mensagem mensagem) {
    	mensagens.remove(mensagem);
    }
    
    public ArrayList<Individual> getIndividuos() {
    	ArrayList<Individual> individuos = new ArrayList<>();
    	for (Participante p : this.getParticipantes().values()) {
    		if (p instanceof Individual i) {
    			individuos.add(i);
    		}
    	}
    	return individuos;
    }
    
    public ArrayList<Grupo> getGrupos() {
    	ArrayList<Grupo> grupos = new ArrayList<>();
    	for (Participante p : this.getParticipantes().values()) {
    		if (p instanceof Grupo g) {
    			grupos.add(g);
    		}
    	}
    	return grupos;
    }
    
    public void removerMensagem(Mensagem msg){
        mensagens.remove(msg);
    }
    
    public int gerarID() {
        if (mensagens.isEmpty()) {
            return 1;
        } else {
            int maiorId = Integer.MIN_VALUE;
            for (Mensagem mensagem : mensagens) {
                if (mensagem.getId() > maiorId) {
                    maiorId = mensagem.getId();
                }
            }
            return maiorId + 1;
        }
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
			for(Mensagem m : mensagens) 	{
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