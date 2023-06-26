package modelo;

import java.util.ArrayList;

public class Grupo extends Participante {

	private ArrayList<Individual> individuos;
	
	public Grupo(String nome) throws Exception {
		super(nome);
		individuos = new ArrayList<>();
	}

	public ArrayList<Individual> getIndividuos() {
		return individuos;
	}

	public void setIndividuos(ArrayList<Individual> individuos) {
		this.individuos = individuos;
	}
	
	public void addParticipante(Individual individual) {
		this.individuos.add(individual);
	}
	
	public void rmvParticipante(Individual individual) {
		this.individuos.remove(individual);
	}

	@Override
	public String toString() {
		return "Grupo [individuos=" + individuos + "]";
	}

}
