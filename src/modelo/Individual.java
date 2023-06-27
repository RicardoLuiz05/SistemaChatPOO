package modelo;

import java.util.ArrayList;

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

	@Override
	public String toString() {
		return "Individual [senha=" + senha + ", administrador=" + administrador + "]" +"GRUPO"+ this.getGrupos();
	}

	
	
}
