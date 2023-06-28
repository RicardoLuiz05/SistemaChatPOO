package modelo;

import java.util.ArrayList;

import regras_negocio.Fachada;

public class Individual extends Participante {

	private String senha;
	private boolean administrador;
	private ArrayList<Grupo> grupos = new ArrayList<>();
	
	public Individual(String nome, String senha) throws Exception {
		super(nome);
		this.setSenha(senha);
		if (nome.equals("admin") && senha.equals("admin")) {
			this.administrador = true;
		}
	}
	
	public Individual(String nome, String senha, boolean administrador) throws Exception {
		super(nome);
		this.setSenha(senha);
		this.administrador = administrador;
	}
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) throws Exception {
		if (senha.isEmpty()) {
			throw new Exception("Um indivíduo não pode ter senha vazia");
		} else {
			this.senha = senha;
		}
	}

	public boolean getAdministrador() {
		return administrador;
	}

	public void setAdministrador(boolean administrador) {
		this.administrador = administrador;
	}

	public ArrayList<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(ArrayList<Grupo> grupos) {
		this.grupos = grupos;
	}
	
	public void addGrupo(Grupo grupo) {
		this.grupos.add(grupo);
		this.setGrupos(grupos);
	}
	
	public void rmvGrupo(Grupo grupo) {
		this.grupos.remove(grupo);
	}
	
	public Grupo localizarGrupo(String nome) {
		for (Grupo g : grupos) {
			if (g.getNome().equals(nome)) {
				return g;
			}
		}
		return null;
	}
	
	public Mensagem localizarEnviada(int id) {
		for (Mensagem m : this.getEnviadas()) {
			if (m.getId() == id) {
				return m;
			}
		}
		return null;
	}
	
	public void removerEnviada(Mensagem mensagem) {
		for (Mensagem m : this.getEnviadas()) {
			if (m.equals(mensagem)) {
				this.getEnviadas().remove(mensagem);
			}
		}
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nome: ").append(super.getNome()).append("\n");
        sb.append("Mensagens Enviadas: ").append("\n").append("\n");
        sb.append(Fachada.getRepositorio().enviadas(getNome())).append("\n");
        sb.append("Mensagens Recebidas: ").append("\n");
        sb.append(Fachada.getRepositorio().recebidas(getNome()));
        return sb.toString();
    }

}
