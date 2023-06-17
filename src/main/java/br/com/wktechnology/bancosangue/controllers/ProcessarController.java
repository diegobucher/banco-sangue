package br.com.wktechnology.bancosangue.controllers;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.wktechnology.bancosangue.model.entities.CandidatosPorEstado;
import br.com.wktechnology.bancosangue.model.entities.Doador;
import br.com.wktechnology.bancosangue.model.entities.IdadeMediaPorTipoSanguineo;
import br.com.wktechnology.bancosangue.model.entities.ImcPorFaixaIdade;
import br.com.wktechnology.bancosangue.model.entities.PercentObesoPorSexo;
import br.com.wktechnology.bancosangue.model.entities.QtdDoadorPorTipoSanguineo;
import br.com.wktechnology.bancosangue.model.entities.Resultado;

@RestController
@RequestMapping("/api/processar")
public class ProcessarController {

	@PostMapping
	public @ResponseBody Resultado processar(@RequestBody List<Doador> doadores) {
		Resultado resultado = new Resultado();

		resultado.setCandidatosPorEstado(contarCandidatosPorEstado(doadores));
		resultado.setImcPorFaixaIdade(calcularImcMedio(doadores));
		resultado.setPercentObesoPorSexo(contarObesosPorSexo(doadores));
		resultado.setIdadeMediaPorTipoSanguineo(contarIdadeMediaPorTipoSanguineo(doadores));
		resultado.setQtdDoadorPorTipoSanguineo(contarDoadorPorTipoSanguineo(doadores, resultado));

		return resultado;
	}

	private QtdDoadorPorTipoSanguineo contarDoadorPorTipoSanguineo(List<Doador> doadores, Resultado resultado) {
		// A quantidade de possíveis doadores para cada tipo sanguíneo receptor.
		// ATENÇÃO: Somente pessoas com idade de 16 a 69 anos e com peso acima de 50 Kg
		// podem doar sangue.

		Integer countAPositivo = 0;
		Integer countANegativo = 0;
		Integer countBPositivo = 0;
		Integer countBNegativo = 0;
		Integer countABPositivo = 0;
		Integer countABNegativo = 0;
		Integer countOPositivo = 0;
		Integer countONegativo = 0;

		for (Doador doador : doadores) {
			Integer idade = calcularIdade(doador.getDataNasc());
			if (idade >= 16 && idade <= 69 && doador.getPeso() >= 50) {

				if (doador.getTipoSanguineo().equalsIgnoreCase("A+") || doador.getTipoSanguineo().equalsIgnoreCase("A-")
						|| doador.getTipoSanguineo().equalsIgnoreCase("O+")
						|| doador.getTipoSanguineo().equalsIgnoreCase("O-")) {
					countAPositivo++;
				}
				if (doador.getTipoSanguineo().equalsIgnoreCase("A-")
						|| doador.getTipoSanguineo().equalsIgnoreCase("O-")) {
					countANegativo++;
				}
				if (doador.getTipoSanguineo().equalsIgnoreCase("B+") || doador.getTipoSanguineo().equalsIgnoreCase("B-")
						|| doador.getTipoSanguineo().equalsIgnoreCase("O+")
						|| doador.getTipoSanguineo().equalsIgnoreCase("O-")) {
					countBPositivo++;
				}
				if (doador.getTipoSanguineo().equalsIgnoreCase("B-")
						|| doador.getTipoSanguineo().equalsIgnoreCase("O-")) {
					countBNegativo++;
				}
				if (doador.getTipoSanguineo().equalsIgnoreCase("A+") || doador.getTipoSanguineo().equalsIgnoreCase("B+")
						|| doador.getTipoSanguineo().equalsIgnoreCase("O+")
						|| doador.getTipoSanguineo().equalsIgnoreCase("AB+")
						|| doador.getTipoSanguineo().equalsIgnoreCase("A-")
						|| doador.getTipoSanguineo().equalsIgnoreCase("B-")
						|| doador.getTipoSanguineo().equalsIgnoreCase("O-")
						|| doador.getTipoSanguineo().equalsIgnoreCase("AB-")) {
					countABPositivo++;
				}
				if (doador.getTipoSanguineo().equalsIgnoreCase("A-") || doador.getTipoSanguineo().equalsIgnoreCase("B-")
						|| doador.getTipoSanguineo().equalsIgnoreCase("O-")
						|| doador.getTipoSanguineo().equalsIgnoreCase("AB-")) {
					countABNegativo++;
				}
				if (doador.getTipoSanguineo().equalsIgnoreCase("O+")
						|| doador.getTipoSanguineo().equalsIgnoreCase("O-")) {
					countOPositivo++;
				}
				if (doador.getTipoSanguineo().equalsIgnoreCase("O-")) {
					countONegativo++;
				}
			}
		}
		QtdDoadorPorTipoSanguineo qtdDoadorPorTipoSanguineo = new QtdDoadorPorTipoSanguineo();
		qtdDoadorPorTipoSanguineo.setAPositivo(countAPositivo);
		qtdDoadorPorTipoSanguineo.setANegativo(countANegativo);
		qtdDoadorPorTipoSanguineo.setBPositivo(countBPositivo);
		qtdDoadorPorTipoSanguineo.setBNegativo(countBNegativo);
		qtdDoadorPorTipoSanguineo.setABPositivo(countABPositivo);
		qtdDoadorPorTipoSanguineo.setABNegativo(countABNegativo);
		qtdDoadorPorTipoSanguineo.setOPositivo(countOPositivo);
		qtdDoadorPorTipoSanguineo.setONegativo(countONegativo);

		return qtdDoadorPorTipoSanguineo;
	}

	private IdadeMediaPorTipoSanguineo contarIdadeMediaPorTipoSanguineo(List<Doador> doadores) {
		// Qual a média de idade para cada tipo sanguíneo?
		Integer countAPos = 0;
		Integer countANeg = 0;
		Integer countBPos = 0;
		Integer countBNeg = 0;
		Integer countABPos = 0;
		Integer countABNeg = 0;
		Integer countOPos = 0;
		Integer countONeg = 0;

		Integer sumIdadeAPos = 0;
		Integer sumIdadeANeg = 0;
		Integer sumIdadeBPos = 0;
		Integer sumIdadeBNeg = 0;
		Integer sumIdadeABPos = 0;
		Integer sumIdadeABNeg = 0;
		Integer sumIdadeOPos = 0;
		Integer sumIdadeONeg = 0;
		for (Doador doador : doadores) {
			switch (doador.getTipoSanguineo()) {
			case "A+":
				countAPos++;
				sumIdadeAPos += calcularIdade(doador.getDataNasc());
				break;
			case "A-":
				countANeg++;
				sumIdadeANeg += calcularIdade(doador.getDataNasc());
				break;
			case "B+":
				countBPos++;
				sumIdadeBPos += calcularIdade(doador.getDataNasc());
				break;
			case "B-":
				countBNeg++;
				sumIdadeBNeg += calcularIdade(doador.getDataNasc());
				break;
			case "AB+":
				countABPos++;
				sumIdadeABPos += calcularIdade(doador.getDataNasc());
				break;
			case "AB-":
				countABNeg++;
				sumIdadeABNeg += calcularIdade(doador.getDataNasc());
				break;
			case "O+":
				countOPos++;
				sumIdadeOPos += calcularIdade(doador.getDataNasc());
				break;
			case "O-":
				countONeg++;
				sumIdadeONeg += calcularIdade(doador.getDataNasc());
			}
		}
		IdadeMediaPorTipoSanguineo idadeMediaPorTipoSanguineo = new IdadeMediaPorTipoSanguineo();
		if (countAPos > 0)
			idadeMediaPorTipoSanguineo.setAPositivo(sumIdadeAPos / countAPos);
		if (countANeg > 0)
			idadeMediaPorTipoSanguineo.setANegativo(sumIdadeANeg / countANeg);
		if (countBPos > 0)
			idadeMediaPorTipoSanguineo.setBPositivo(sumIdadeBPos / countBPos);
		if (countBNeg > 0)
			idadeMediaPorTipoSanguineo.setBNegativo(sumIdadeBNeg / countBNeg);
		if (countABPos > 0)
			idadeMediaPorTipoSanguineo.setABPositivo(sumIdadeABPos / countABPos);
		if (countABNeg > 0)
			idadeMediaPorTipoSanguineo.setABNegativo(sumIdadeABNeg / countABNeg);
		if (countOPos > 0)
			idadeMediaPorTipoSanguineo.setOPositivo(sumIdadeOPos / countOPos);
		if (countONeg > 0)
			idadeMediaPorTipoSanguineo.setONegativo(sumIdadeONeg / countONeg);
		return idadeMediaPorTipoSanguineo;
	}

	private PercentObesoPorSexo contarObesosPorSexo(List<Doador> doadores) {
		// Qual o percentual de obesos entre os homens e entre as mulheres? (É obeso
		// quem tem IMC >30)
		PercentObesoPorSexo percentObesoPorSexo = new PercentObesoPorSexo();
		for (Doador doador : doadores) {
			if (doador.getSexo().equalsIgnoreCase("Masculino")) {
				percentObesoPorSexo.setSomaHomens(percentObesoPorSexo.getSomaHomens() + 1);
				if (calcularImc(doador) > 30) {
					percentObesoPorSexo.setSomaHomensObeso(percentObesoPorSexo.getSomaHomensObeso() + 1);
				}
			} else if (doador.getSexo().equalsIgnoreCase("Feminino")) {
				percentObesoPorSexo.setSomaMulheres(percentObesoPorSexo.getSomaMulheres() + 1);
				if (calcularImc(doador) > 30) {
					percentObesoPorSexo.setSomaMulheresObesa(percentObesoPorSexo.getSomaMulheresObesa() + 1);
				}
			}
		}

		percentObesoPorSexo.setHomens((percentObesoPorSexo.getSomaHomensObeso().floatValue()
				/ percentObesoPorSexo.getSomaHomens().floatValue()) * 100);
		percentObesoPorSexo.setMulheres((percentObesoPorSexo.getSomaMulheresObesa().floatValue()
				/ percentObesoPorSexo.getSomaMulheres().floatValue()) * 100);
		return percentObesoPorSexo;
	}

	private ImcPorFaixaIdade calcularImcMedio(List<Doador> doadores) {
		// IMC médio em cada faixa de idade de dez em dez anos: 0 a 10; 11 a 20; 21 a
		// 30, etc. (IMC = peso / altura^2)

		ImcPorFaixaIdade imcPorFaixaIdade = new ImcPorFaixaIdade();
		for (Doador doador : doadores) {
			int years = calcularIdade(doador.getDataNasc());

			if (years >= 0 && years <= 10) {
				imcPorFaixaIdade.setSomaDoadores0a10(imcPorFaixaIdade.getSomaDoadores0a10() + 1);
				imcPorFaixaIdade.setSomaImc0a10(imcPorFaixaIdade.getSomaImc0a10() + calcularImc(doador));
			} else if (years >= 11 && years <= 20) {
				imcPorFaixaIdade.setSomaDoadores11a20(imcPorFaixaIdade.getSomaDoadores11a20() + 1);
				imcPorFaixaIdade.setSomaImc11a20(imcPorFaixaIdade.getSomaImc11a20() + calcularImc(doador));
			} else if (years >= 21 && years <= 30) {
				imcPorFaixaIdade.setSomaDoadores21a30(imcPorFaixaIdade.getSomaDoadores21a30() + 1);
				imcPorFaixaIdade.setSomaImc21a30(imcPorFaixaIdade.getSomaImc21a30() + calcularImc(doador));
			} else if (years >= 31 && years <= 40) {
				imcPorFaixaIdade.setSomaDoadores31a40(imcPorFaixaIdade.getSomaDoadores31a40() + 1);
				imcPorFaixaIdade.setSomaImc31a40(imcPorFaixaIdade.getSomaImc31a40() + calcularImc(doador));
			} else if (years >= 41 && years <= 50) {
				imcPorFaixaIdade.setSomaDoadores41a50(imcPorFaixaIdade.getSomaDoadores41a50() + 1);
				imcPorFaixaIdade.setSomaImc41a50(imcPorFaixaIdade.getSomaImc41a50() + calcularImc(doador));
			} else if (years >= 51 && years <= 60) {
				imcPorFaixaIdade.setSomaDoadores51a60(imcPorFaixaIdade.getSomaDoadores51a60() + 1);
				imcPorFaixaIdade.setSomaImc51a60(imcPorFaixaIdade.getSomaImc51a60() + calcularImc(doador));
			} else if (years >= 61 && years <= 70) {
				imcPorFaixaIdade.setSomaDoadores61a70(imcPorFaixaIdade.getSomaDoadores61a70() + 1);
				imcPorFaixaIdade.setSomaImc61a70(imcPorFaixaIdade.getSomaImc61a70() + calcularImc(doador));
			} else if (years >= 71 && years <= 80) {
				imcPorFaixaIdade.setSomaDoadores71a80(imcPorFaixaIdade.getSomaDoadores71a80() + 1);
				imcPorFaixaIdade.setSomaImc71a80(imcPorFaixaIdade.getSomaImc71a80() + calcularImc(doador));
			} else if (years >= 81 && years <= 90) {
				imcPorFaixaIdade.setSomaDoadores81a90(imcPorFaixaIdade.getSomaDoadores81a90() + 1);
				imcPorFaixaIdade.setSomaImc81a90(imcPorFaixaIdade.getSomaImc81a90() + calcularImc(doador));
			} else if (years >= 91 && years <= 100) {
				imcPorFaixaIdade.setSomaDoadores91a100(imcPorFaixaIdade.getSomaDoadores91a100() + 1);
				imcPorFaixaIdade.setSomaImc91a100(imcPorFaixaIdade.getSomaImc91a100() + calcularImc(doador));
			} else if (years >= 101) {
				imcPorFaixaIdade.setSomaDoadores101(imcPorFaixaIdade.getSomaDoadores101() + 1);
				imcPorFaixaIdade.setSomaImc101(imcPorFaixaIdade.getSomaImc101() + calcularImc(doador));
			}
		}
		imcPorFaixaIdade.setImc0a10(imcPorFaixaIdade.getSomaImc0a10() / imcPorFaixaIdade.getSomaDoadores0a10());
		imcPorFaixaIdade.setImc11a20(imcPorFaixaIdade.getSomaImc11a20() / imcPorFaixaIdade.getSomaDoadores11a20());
		imcPorFaixaIdade.setImc21a30(imcPorFaixaIdade.getSomaImc21a30() / imcPorFaixaIdade.getSomaDoadores21a30());
		imcPorFaixaIdade.setImc31a40(imcPorFaixaIdade.getSomaImc31a40() / imcPorFaixaIdade.getSomaDoadores31a40());
		imcPorFaixaIdade.setImc41a50(imcPorFaixaIdade.getSomaImc41a50() / imcPorFaixaIdade.getSomaDoadores41a50());
		imcPorFaixaIdade.setImc51a60(imcPorFaixaIdade.getSomaImc51a60() / imcPorFaixaIdade.getSomaDoadores51a60());
		imcPorFaixaIdade.setImc61a70(imcPorFaixaIdade.getSomaImc61a70() / imcPorFaixaIdade.getSomaDoadores61a70());
		imcPorFaixaIdade.setImc71a80(imcPorFaixaIdade.getSomaImc71a80() / imcPorFaixaIdade.getSomaDoadores71a80());
		imcPorFaixaIdade.setImc81a90(imcPorFaixaIdade.getSomaImc81a90() / imcPorFaixaIdade.getSomaDoadores81a90());
		imcPorFaixaIdade.setImc91a100(imcPorFaixaIdade.getSomaImc91a100() / imcPorFaixaIdade.getSomaDoadores91a100());
		imcPorFaixaIdade.setImc101(imcPorFaixaIdade.getSomaImc101() / imcPorFaixaIdade.getSomaDoadores101());

		return imcPorFaixaIdade;
	}

	private CandidatosPorEstado contarCandidatosPorEstado(List<Doador> doadores) {
		// Quantos candidatos temos nessa lista em cada estado do Brasil?
		CandidatosPorEstado candidatosPorEstado = new CandidatosPorEstado();
		for (Doador doador : doadores) {
			switch (doador.getEstado().toUpperCase()) {
			case "SC":
				candidatosPorEstado.setSc(candidatosPorEstado.getSc() + 1);
				break;
			case "PR":
				candidatosPorEstado.setPr(candidatosPorEstado.getPr() + 1);
				break;
			case "RS":
				candidatosPorEstado.setRs(candidatosPorEstado.getRs() + 1);
				break;
			case "SP":
				candidatosPorEstado.setSp(candidatosPorEstado.getSp() + 1);
				break;
			case "RJ":
				candidatosPorEstado.setRj(candidatosPorEstado.getRj() + 1);
				break;
			case "ES":
				candidatosPorEstado.setEs(candidatosPorEstado.getEs() + 1);
				break;
			case "BA":
				candidatosPorEstado.setBa(candidatosPorEstado.getBa() + 1);
				break;
			case "AC":
				candidatosPorEstado.setAc(candidatosPorEstado.getAc() + 1);
				break;
			case "AL":
				candidatosPorEstado.setAl(candidatosPorEstado.getAl() + 1);
				break;
			case "AP":
				candidatosPorEstado.setAp(candidatosPorEstado.getAp() + 1);
				break;
			case "AM":
				candidatosPorEstado.setAm(candidatosPorEstado.getAm() + 1);
				break;
			case "CE":
				candidatosPorEstado.setCe(candidatosPorEstado.getCe() + 1);
				break;
			case "DF":
				candidatosPorEstado.setDf(candidatosPorEstado.getDf() + 1);
				break;
			case "GO":
				candidatosPorEstado.setGo(candidatosPorEstado.getGo() + 1);
				break;
			case "MA":
				candidatosPorEstado.setMa(candidatosPorEstado.getMa() + 1);
				break;
			case "MT":
				candidatosPorEstado.setMt(candidatosPorEstado.getMt() + 1);
				break;
			case "MS":
				candidatosPorEstado.setMs(candidatosPorEstado.getMs() + 1);
				break;
			case "MG":
				candidatosPorEstado.setMg(candidatosPorEstado.getMg() + 1);
				break;
			case "PA":
				candidatosPorEstado.setPa(candidatosPorEstado.getPa() + 1);
				break;
			case "PB":
				candidatosPorEstado.setPb(candidatosPorEstado.getPb() + 1);
				break;
			case "PE":
				candidatosPorEstado.setPe(candidatosPorEstado.getPe() + 1);
				break;
			case "PI":
				candidatosPorEstado.setPi(candidatosPorEstado.getPi() + 1);
				break;
			case "RR":
				candidatosPorEstado.setRr(candidatosPorEstado.getRr() + 1);
				break;
			case "RO":
				candidatosPorEstado.setRo(candidatosPorEstado.getRo() + 1);
				break;
			case "RN":
				candidatosPorEstado.setRn(candidatosPorEstado.getRn() + 1);
				break;
			case "SE":
				candidatosPorEstado.setSe(candidatosPorEstado.getSe() + 1);
				break;
			case "TO":
				candidatosPorEstado.setTo(candidatosPorEstado.getTo() + 1);
				break;
			}
		}
		return candidatosPorEstado;
	}

	private Float calcularImc(Doador doador) {
		return doador.getPeso() / (doador.getAltura() * doador.getAltura());
	}

	private int calcularIdade(String dataNasc) {
		LocalDate dataNascParsed = LocalDate.parse(dataNasc, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		Period age = Period.between(dataNascParsed, LocalDate.now());
		int years = age.getYears();
		return years;
	}

}
