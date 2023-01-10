package eshop.restcontroller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import eshop.entity.Commande;
import eshop.service.CommandeService;
import eshop.util.Check;



@RestController
@RequestMapping("/api/commande")
public class CommandeRestController {

	@Autowired
	private CommandeService commandeService;
	
	@GetMapping("")
	public List<Commande> getAll() {
		return commandeService.getAll();
	}
	
	@GetMapping("/{id}")
	public Commande getById(@PathVariable Long id) {
		return commandeService.getById(id);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("")
	public Commande create(@PathVariable Long id) {
		return commandeService.getById(id);
	}
	
	@PutMapping("/{id}")
	public Commande update(@Valid @RequestBody Commande commande, BindingResult br, @PathVariable Long id) {
		Check.checkBindingResulHasError(br);
		commande.setId(id);
		return formationService.update(formation);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		commandeService.delete(id);
	}
}
