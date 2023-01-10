package eshop.restcontroller;

import java.lang.reflect.Field;
import java.time.LocalDate;
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
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonView;

import eshop.entity.Adresse;
import eshop.entity.Fournisseur;
import eshop.jsonviews.Views;
import eshop.service.FournisseurService;

@RestController
@RequestMapping("/api/fournisseur")
public class FournisseurRestController {

	@Autowired
	private FournisseurService fournisseurService;

	@GetMapping("")
	@JsonView(Views.Common.class)
	public List<Fournisseur> getAll() {
		return fournisseurService.getAll();
	}

	@GetMapping("/{id}")
	@JsonView(Views.Common.class)
	public Fournisseur getById(@PathVariable Long id) {
		return fournisseurService.getById(id);
	}

	@GetMapping("/{id}/fournisseur")
	@JsonView(Views.FournisseurWithProduitsCommeFournisseur.class)
	public Fournisseur getByIdWithFormationsCommeReferent(@PathVariable Long id) {
		return fournisseurService.getByIdWithProduitsCommeFournisseur(id);
	}

	// creation post
	// Views.Common parce qu'on crée sur la table fournisseur, pas formation

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("")
	@JsonView(Views.Common.class)
	public Fournisseur create(@Valid @RequestBody Fournisseur fournisseur, BindingResult br) {
		if (br.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		return fournisseurService.create(fournisseur);
	}

	// modification put
	// Views.Common parce qu'on crée sur la table fournisseur, pas formation

	@PutMapping("/{id}")
	@JsonView(Views.Common.class)
	public Fournisseur update(@Valid @RequestBody Fournisseur fournisseur, BindingResult br, @PathVariable Long id) {
		if (br.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		// ou check.checkBindingResultHasError(br);
		fournisseur.setId(id);
		return fournisseurService.update(fournisseur);
	}

	// suppression delete

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		fournisseurService.delete(id);
	}

	// modification partielle

	@PatchMapping("/{id}")
	@JsonView(Views.Common.class)
	public Fournisseur patch(@PathVariable Long id, @RequestBody Map<String, Object> fields) {
		Fournisseur fournisseur = fournisseurService.getById(id);
		fields.forEach((key, value) -> {
			final Adresse adresse ;

			if (key.equals("adresse")) {
				if(fournisseur.getAdresse() !=null) {
					adresse = fournisseur.getAdresse();
				}else {
					adresse= new Adresse();
				}
				((Map<String, Object>) value).forEach((k, v) -> {
					setField(adresse, Adresse.class, k, v);
				});
				fournisseur.setAdresse(adresse);
			} else {
				setField(fournisseur, Fournisseur.class, key, value);
			}
		});
		return fournisseurService.update(fournisseur);
	}

	private void setField(Object target, Class modelClass, String fieldName, Object value) {
		Field field = ReflectionUtils.findField(modelClass, fieldName);
		ReflectionUtils.makeAccessible(field);
		ReflectionUtils.setField(field, target, value);
	}
}
