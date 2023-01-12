package eshop.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import eshop.entity.Adresse;
import eshop.entity.Client;
import eshop.entity.Commande;
import eshop.exception.ClientException;
import eshop.exception.IdException;
import eshop.repository.AchatRepository;
import eshop.repository.ClientRepository;
import eshop.repository.CommandeRepository;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepo;
	
	@Autowired 
	private CommandeRepository commandeRepo;
	
	@Autowired 
	private AchatRepository achatRepo;

	
	// Create
	public Client create(Client client) {
		checkClientIsNotNull(client);
		if (client.getNom() == null || client.getNom().isEmpty()) {
			throw new ClientException("Nom manquant");
		}
		if (client.getPrenom() == null || client.getPrenom().isEmpty()) {
			throw new ClientException("Prénom manquant");
		}
		if (client.getEmail() == null || client.getEmail().isEmpty()) {
			throw new ClientException("Mail manquant");
		}
		return clientRepo.save(client);
	}
	
	// Check client not null
	private void checkClientIsNotNull(Client client) {
		if (client == null) {
			throw new ClientException("Pas de client trouvé");
		}
	}
	
	// Get by id
	public Client getById(Long id) {
		if (id == null) {
			throw new IdException();
		}
		return clientRepo.findById(id).orElseThrow(() -> {
			throw new ClientException("Pas de client trouvé");
		});
	}
	
	// Liste commande client
	public Client getByIdWithCommandeCommeClient(Long id) {
		if (id == null) {
			throw new IdException();
		}
		return clientRepo.findByIdFetchCommandes(id).orElseThrow(() -> {
			throw new ClientException("Pas de client trouvé");
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
	
	// Delete by ID et commandes en cascade
	// TODO : suppression des achats en cascade des commandes
	private void deleteById(Long id) {
		Client client = getById(id);
//		clientRepo.findByIdFetchCommandes(id).stream().forEach(c -> {
//			achatRepo.deleteByCommande();
//		});
		commandeRepo.deleteByClient(client);
		clientRepo.delete(client);
	}
	
	// Recherche par nom 
	public List<Client> getByNom (String nom) {
		if (nom == null || nom.isEmpty()) {
			throw new ClientException("Nom nécessaire pour recherche");
		}
		return clientRepo.findByNom(nom);
	}
	
	// Recherche par nom partielle
	public List<Client> getByNomCont (String nom) {
		if (nom == null || nom.isEmpty()) {
			throw new ClientException("Nom nécessaire pour recherche");
		}
		return clientRepo.findByNomContaining(nom);
	}
	
	
	// Recherche par mail
	public List<Client> getByEmail (String email) {
		if (email == null || email.isEmpty()) {
			throw new ClientException("Email nécessaire pour recherche");
		}
		return clientRepo.findByEmail(email);
	}
	
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
			clientEnBase.setPrenom(client.getPrenom() == null||client.getPrenom().isEmpty() ? clientEnBase.getPrenom() : client.getPrenom());
			clientEnBase.setNom(client.getNom() == null||client.getNom().isEmpty()? clientEnBase.getNom() : client.getNom());
			clientEnBase.setEmail(client.getEmail() == null || client.getEmail().isEmpty()? clientEnBase.getEmail() : client.getEmail());
			clientEnBase.setCivilite(client.getCivilite() == null ? clientEnBase.getCivilite() : client.getCivilite());
			clientEnBase.setDateInscription(client.getDateInscription() == null ? clientEnBase.getDateInscription() : client.getDateInscription());
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
