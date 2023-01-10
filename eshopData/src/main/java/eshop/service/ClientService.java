package eshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import eshop.entity.Adresse;
import eshop.entity.Client;
import eshop.exception.ClientException;
import eshop.exception.IdException;
import eshop.repository.ClientRepository;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepo;

	
	// Create
	public Client create(Client client) {
		checkClientIsNotNull(client);
		return clientRepo.save(client);
	}
	
	// Check client not null
	private void checkClientIsNotNull(Client client) {
		if (client == null) {
			throw new ClientException("client null");
		}
	}
	
	// Get by id
	public Client getById(Long id) {
		if (id == null) {
			throw new IdException();
		}
		return clientRepo.findById(id).orElseThrow(() -> {
			throw new ClientException("client inconnu");
		});
	}
	
	// Liste commande client
	public Client getByIdWithCommandeCommeClient(Long id) {
		if (id == null) {
			throw new IdException();
		}
		return clientRepo.findByIdFetchCommandes(id).orElseThrow(() -> {
			throw new ClientException("client inconnu");
		});
	}
	
	// Delete by Client
	public void delete(Client client) {
		checkClientIsNotNull(client);
		deleteById(client.getId());
	}
	// Delete by ID
	public void delete(Long id) {
		deleteById(id);
	}
	
	// Delete by ID
	private void deleteById(Long id) {
		Client client = getById(id);
		// TODO : Ajout de la méthode deleteByClient de CommandeService
		clientRepo.delete(client);
	}
	
	//TODO les recherches par nom et mail
	
	// Liste des clients par liste
	public List<Client> getAll() {
		return clientRepo.findAll();
	}

	// Liste des clients par page
	public Page<Client> getAll(Pageable pageable) {
		if (pageable == null) {
			throw new ClientException();
		}
		return clientRepo.findAll(pageable);
	}

	// Page suivante
	public Page<Client> getNextPage(Page<Client> page) {
		if (page == null) {
			throw new ClientException();
		}
		return clientRepo.findAll(page.nextOrLastPageable());
	}

	// Page précédente
	public Page<Client> getPrevious(Page<Client> page) {
		if (page == null) {
			throw new ClientException();
		}
		return clientRepo.findAll(page.previousOrFirstPageable());
	}

	// Update du client // A vérifier
	public Client update(Client client) {
		// @formatter:off
			Client clientEnBase = getById(client.getId());
			clientEnBase.setPrenom(client.getPrenom() != null||client.getPrenom().isEmpty() ? clientEnBase.getPrenom() : client.getPrenom());
			clientEnBase.setNom(client.getNom() != null||client.getNom().isEmpty()? clientEnBase.getNom() : client.getNom());
			clientEnBase.setEmail(client.getEmail());
			clientEnBase.setCivilite(client.getCivilite() != null ? clientEnBase.getCivilite() : client.getCivilite());
			clientEnBase.setDateInscription(client.getDateInscription() != null ? clientEnBase.getDateInscription() : client.getDateInscription());
			if (client.getAdresse() != null) {
				clientEnBase.setAdresse(
									new Adresse(
											client.getAdresse().getNumero(), 
											client.getAdresse().getRue(),
											client.getAdresse().getCodePostal(),
											client.getAdresse().getVille()));
			} else {
				clientEnBase.setAdresse(null);
			}
			return clientRepo.save(clientEnBase);
			// @formatter:on
	}
	
	
}
