package com.docket.cartorio.controllers;

import com.docket.cartorio.models.Cartorio;
import com.docket.cartorio.repository.CartorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class CartorioController {

	@Autowired
	private CartorioRepository cr;

	@RequestMapping(value = "/cadastrar", method = RequestMethod.GET)
	public String form() {
		return "cartorio/formCadastro";
	}

	@RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
	public String form(@Valid Cartorio cartorio, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return "redirect:/cadastrar";
		}

		cr.save(cartorio);
		return "redirect:/listaCartorios";
	}

	@RequestMapping("/listaCartorios")
	public ModelAndView listaCartorios() {
		ModelAndView mv = new ModelAndView("cartorio/listaCartorios");
		Iterable<Cartorio> cartorios = cr.findAll();
		mv.addObject("cartorios", cartorios);
		return mv;
	}

	@RequestMapping(value = "/visualizarCartorio", method = RequestMethod.POST)
	public ModelAndView visualizarCartorio(long id) {
		Cartorio cartorio = cr.findById(id).get();
		ModelAndView mv = new ModelAndView("cartorio/visualizarCartorio");
		mv.addObject("cartorio", cartorio);
		return mv;
	}

	@RequestMapping("/excluirCartorio")
	public String deletarCartorio(long id) {
		Cartorio cartorio = cr.findById(id).get();
		cr.delete(cartorio);
		return "redirect:/listaCartorios";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String formEditar(@PathVariable("id") long id, Model model) {
		Cartorio cartorio = cr.findById(id).get();
		model.addAttribute("cartorio", cartorio);
		return "cartorio/formEditar";
	}

	@RequestMapping(value = "/editarCartorio", method = RequestMethod.POST)
	public String editar(@Valid Cartorio cartorio, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:/formEditar";
		}
		
		cr.save(cartorio);
		return "redirect:/listaCartorios";
	}

}
