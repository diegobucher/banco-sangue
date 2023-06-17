package br.com.wktechnology.bancosangue.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import br.com.wktechnology.bancosangue.model.entities.Doador;
import br.com.wktechnology.bancosangue.model.entities.JSON;
import br.com.wktechnology.bancosangue.model.entities.Resultado;

@Controller
public class PaginaController {

	Resultado resultado = new Resultado();

	@GetMapping
	public ModelAndView goHome() {
		ModelAndView mv = new ModelAndView("home");
		mv.addObject("json", new JSON());
		return mv;
	}

	@GetMapping("/result")
	public ModelAndView goResult() {
		ModelAndView mv = new ModelAndView("result");
		mv.addObject("resultado", resultado);
		return mv;
	}

	@PostMapping("/processar")
	public String processar(Model model, @ModelAttribute JSON json) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			List<Doador> doadores = mapper.readValue(json.getConteudo(),
					TypeFactory.defaultInstance().constructCollectionType(List.class, Doador.class));

			ProcessarController controller = new ProcessarController();
			resultado = controller.processar(doadores);
		} catch (JacksonException e) {
			e.printStackTrace();
		}
		return "redirect:/result";
	}

}
