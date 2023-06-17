package br.com.wktechnology.bancosangue.model.entities;

public class Resultado {

	CandidatosPorEstado candidatosPorEstado;
	ImcPorFaixaIdade imcPorFaixaIdade;
	PercentObesoPorSexo percentObesoPorSexo;
	IdadeMediaPorTipoSanguineo idadeMediaPorTipoSanguineo;
	QtdDoadorPorTipoSanguineo qtdDoadorPorTipoSanguineo;

	public CandidatosPorEstado getCandidatosPorEstado() {
		return candidatosPorEstado;
	}

	public void setCandidatosPorEstado(CandidatosPorEstado candidatosPorEstado) {
		this.candidatosPorEstado = candidatosPorEstado;
	}

	public ImcPorFaixaIdade getImcPorFaixaIdade() {
		return imcPorFaixaIdade;
	}

	public void setImcPorFaixaIdade(ImcPorFaixaIdade imcPorFaixaIdade) {
		this.imcPorFaixaIdade = imcPorFaixaIdade;
	}

	public PercentObesoPorSexo getPercentObesoPorSexo() {
		return percentObesoPorSexo;
	}

	public void setPercentObesoPorSexo(PercentObesoPorSexo percentObesoPorSexo) {
		this.percentObesoPorSexo = percentObesoPorSexo;
	}

	public IdadeMediaPorTipoSanguineo getIdadeMediaPorTipoSanguineo() {
		return idadeMediaPorTipoSanguineo;
	}

	public void setIdadeMediaPorTipoSanguineo(IdadeMediaPorTipoSanguineo idadeMediaPorTipoSanguineo) {
		this.idadeMediaPorTipoSanguineo = idadeMediaPorTipoSanguineo;
	}

	public QtdDoadorPorTipoSanguineo getQtdDoadorPorTipoSanguineo() {
		return qtdDoadorPorTipoSanguineo;
	}

	public void setQtdDoadorPorTipoSanguineo(QtdDoadorPorTipoSanguineo qtdDoadorPorTipoSanguineo) {
		this.qtdDoadorPorTipoSanguineo = qtdDoadorPorTipoSanguineo;
	}

}
