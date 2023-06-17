package br.com.wktechnology.bancosangue.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PercentObesoPorSexo {

	Float homens;
	Float mulheres;

	@JsonIgnore
	Integer somaHomens = 0;
	@JsonIgnore
	Integer somaMulheres = 0;

	@JsonIgnore
	Integer somaHomensObeso = 0;
	@JsonIgnore
	Integer somaMulheresObesa = 0;

	public Float getHomens() {
		return homens;
	}

	public void setHomens(Float homens) {
		this.homens = homens;
	}

	public Float getMulheres() {
		return mulheres;
	}

	public void setMulheres(Float mulheres) {
		this.mulheres = mulheres;
	}

	public Integer getSomaHomens() {
		return somaHomens;
	}

	public void setSomaHomens(Integer somaHomens) {
		this.somaHomens = somaHomens;
	}

	public Integer getSomaMulheres() {
		return somaMulheres;
	}

	public void setSomaMulheres(Integer somaMulheres) {
		this.somaMulheres = somaMulheres;
	}

	public Integer getSomaHomensObeso() {
		return somaHomensObeso;
	}

	public void setSomaHomensObeso(Integer somaHomensObeso) {
		this.somaHomensObeso = somaHomensObeso;
	}

	public Integer getSomaMulheresObesa() {
		return somaMulheresObesa;
	}

	public void setSomaMulheresObesa(Integer somaMulheresObesa) {
		this.somaMulheresObesa = somaMulheresObesa;
	}

}
