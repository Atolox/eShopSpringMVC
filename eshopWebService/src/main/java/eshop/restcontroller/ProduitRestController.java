package eshop.restcontroller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import eshop.entity.Produit;
import eshop.jsonviews.Views;
import eshop.service.FournisseurService;
import eshop.service.ProduitService;
import eshop.util.Check;

@RestController
@RequestMapping("api/produit")
public class ProduitRestController {
	
	@Autowired
	private ProduitService produitService;
	
	 @Autowired
	 private FournisseurService fournisseurService;
	
	@GetMapping("")
	@JsonView(Views.Common.class)
	public List<Produit> getAll() {
		return produitService.getAll();
	}
	
	@GetMapping("/{id}")
	@JsonView(Views.Common.class)
	public Produit getById(@PathVariable Long id) {
		return produitService.getById(id);
	}
	
	@GetMapping("libelle/{libelle")
	@JsonView(Views.Common.class)
	public List<Produit> getByLibelle(@PathVariable String libelle) {
		return produitService.getByLibelle(libelle);
	}
	
	@GetMapping("fournisseur/{fournisseurId}")
	@JsonView(Views.Common.class)
	public List<Produit> getByFournisseur(@PathVariable Long fournisseurId) {
		return produitService.getByFournisseur(fournisseurService.getById(fournisseurId));
	    }
	
	
	// creation POST
		@ResponseStatus(HttpStatus.CREATED)
		@PostMapping("")
		@JsonView(Views.Common.class)
		public Produit create(@Valid @RequestBody Produit produit, BindingResult br) {
			Check.checkBindingResultHasError(br);
			return produitService.create(produit);
		}

		// modification PUT
		@PutMapping("/{id}")
		@JsonView(Views.Common.class)
		public Produit update(@Valid @RequestBody Produit produit, BindingResult br, @PathVariable Long id) {
			Check.checkBindingResultHasError(br);
			produit.setId(id);
			return produitService.update(produit);
		}

		// suppression DELETE
		@DeleteMapping("/{id}")
		@ResponseStatus(HttpStatus.NO_CONTENT)
		public void delete(@PathVariable Long id) {
			produitService.delete(id);
		}
		
		@PatchMapping("/{id}")
		@JsonView(Views.Common.class)
		public Produit patch(@PathVariable Long id, @RequestBody Map<String, Object> objetJsonRecu) {
			Produit produitEnBase = produitService.getById(id);

			objetJsonRecu.forEach((key, value) -> {
				if (key.equals("fournisseur")) {
					Map<String, Object> fournisseurtEnJsonRecu = (Map<String, Object>) objetJsonRecu.get("fournisseur");
					produitEnBase
							.setReferent(produitService.getById(Long.parseLong(fournisseurEnJsonRecu.get("id").toString())));
				} else {
					Field field = ReflectionUtils.findField(Produit.class, key);
					ReflectionUtils.makeAccessible(field);
					ReflectionUtils.setField(field, produitEnBase, value);
				}
			});

			return produitService.update(produitEnBase);
		}

	}


}
