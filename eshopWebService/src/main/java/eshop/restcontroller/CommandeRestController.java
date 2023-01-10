package eshop.restcontroller;

import java.util.List;

import javax.validation.Valid;

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
import eshop.jsonviews.Views;
import eshop.service.CommandeService;
import eshop.util.Check;



@RestController
@RequestMapping("/api/commande")
public class CommandeRestController {

	@Autowired
	private CommandeService commandeService;
	
	@GetMapping("")
	@JsonView(Views.Common.class)
	public List<Commande> getAll() {
		return commandeService.getAll();
	}
	
	@GetMapping("/{id}")
	@JsonView(Views.Common.class)
	public Commande getById(@PathVariable Long id) {
		return commandeService.getById(id);
	}
	
	@GetMapping("/{id}/commande")
	@JsonView(Views.CommandeWithAchats.class)
	public Commande getByIdWithAchats(@PathVariable Long id) {
		return commandeService.getByIdWithAchats(id);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("")
	@JsonView(Views.Common.class)
	public Commande create(@PathVariable Long id) {
		return commandeService.getById(id);
	}
	
	@PutMapping("/{id}")
	@JsonView(Views.Common.class)
	public Commande update(@Valid @RequestBody Commande commande, BindingResult br, @PathVariable Long id) {
		Check.checkBindingResultHasError(br);
		commande.setNumero(id);
		return commandeService.update(commande);
	}
	
	@DeleteMapping("/{id}")
	@JsonView(Views.Common.class)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		commandeService.delete(id);
	}
}
