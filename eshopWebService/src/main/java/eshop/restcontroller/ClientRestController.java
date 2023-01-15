package eshop.restcontroller;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import eshop.entity.Adresse;
import eshop.entity.Client;
import eshop.jsonviews.Views;
import eshop.service.ClientService;
import eshop.util.Check;

@RestController
@RequestMapping("/api/client")
public class ClientRestController {

	@Autowired
	private ClientService clientService;

	// TODO Fix GET vue par pages
	@GetMapping("/page")
	@JsonView(Views.Common.class)
	public Page<Client> getAllPage() {
		Pageable pageable = PageRequest.of(0, 20);
		Page<Client> page = clientService.getAll(pageable);
		return page;
	}

	// GET Liste des clients
	@GetMapping("")
	@JsonView(Views.Common.class)
	public List<Client> getAllList() {
		return clientService.getAll();
	}

	// GET Client avec commandes par id
	@GetMapping("/{id}")
	@JsonView(Views.ListeCommandesClient.class)
	public Client getByIdWithCommandes(@PathVariable Long id) {
		return clientService.getByIdWithCommandeCommeClient(id);
	}

	// POST Création client
	@PostMapping("")
	@JsonView(Views.Common.class)
	public Client create(@Valid @RequestBody Client client, BindingResult br) {
		Check.checkBindingResultHasError(br);
		return clientService.create(client);
	}

	// PUT Update client
	@PutMapping("/{id}")
	@JsonView(Views.Common.class)
	public Client update(@Valid @RequestBody Client client, BindingResult br, @PathVariable Long id) {
		Check.checkBindingResultHasError(br);
		client.setId(id);
		return clientService.update(client);
	}
	
	// PATCH Update Client
	@PatchMapping("/{id}")
	@JsonView(Views.Common.class)
	public Client patch(@RequestBody Map<String, Object> fields, @PathVariable Long id) {
		Client client = clientService.getById(id);
		fields.forEach((key, value) -> {
			if (key.equals("adresse")) {
				final Adresse adresse;

				if (client.getAdresse() != null) {
					adresse = client.getAdresse();
				} else {
					adresse = new Adresse();
				}
				((Map<String, Object>) value).forEach((k, v) -> {
					setField(adresse, Adresse.class, k, v);
				});
				client.setAdresse(adresse);
			} else if (key.equals("dtNaiss")) {
				client.setDateInscription(LocalDate.parse(value.toString()));
			} else {
				setField(client, Client.class, key, value);
			}
		});
		return clientService.update(client);
	}
	
	private void setField(Object target, Class modelClass, String fieldName, Object value) {
		Field field = ReflectionUtils.findField(modelClass, fieldName);
		ReflectionUtils.makeAccessible(field);
		ReflectionUtils.setField(field, target, value);
	}
	
	// Delete Client (avec commandes liées)
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		clientService.delete(id);
	}
}
