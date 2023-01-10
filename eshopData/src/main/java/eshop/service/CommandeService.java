package eshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import eshop.entity.Client;
import eshop.entity.Commande;
import eshop.entity.Fournisseur;
import eshop.exception.CommandeException;
import eshop.exception.FournisseurException;
import eshop.exception.IdException;
import eshop.repository.AchatRepository;
import eshop.repository.CommandeRepository;

@Service
public class CommandeService {

	@Autowired
	private CommandeRepository commandeRepository;

	@Autowired
	private AchatRepository achatRepository;

	public Commande create(Commande commande) {
		checkCommandeIsNotNull(commande);
		if (commande.getAchats().isEmpty() || commande.getAchats() == null) {
			throw new CommandeException("commande sans achats");
		}
		return commandeRepository.save(commande);
	}

	private void checkCommandeIsNotNull(Commande commande) {
		if (commande == null) {
			throw new CommandeException("commande null");
		}
	}

	public Commande getById(Long id) {
		if (id == null) {
			throw new IdException();
		}
		return commandeRepository.findById(id).orElseThrow(() -> {
			throw new CommandeException("commande inconnu");
		});
	}
	
	public Commande getByIdWithAchats(Long id) {
		if (id == null) {
			throw new IdException();
		}
		return commandeRepository.findByIdFetchAchats(id).orElseThrow(() -> {
			throw new FournisseurException("aucun achat pour la commande");
		});
	}

	public List<Commande> getAll() {
		return commandeRepository.findAll();
	}
	
	public Page<Commande> getAll(Pageable pageable) {
		if (pageable == null) {
			throw new CommandeException();
		}
		return commandeRepository.findAll(pageable);
	}

	public Page<Commande> getNextPage(Page<Commande> page) {
		if (page == null) {
			throw new CommandeException();
		}
		return commandeRepository.findAll(page.nextOrLastPageable());
	}

	public Page<Commande> getPrevious(Page<Commande> page) {
		if (page == null) {
			throw new CommandeException();
		}
		return commandeRepository.findAll(page.previousOrFirstPageable());
	}

	private void deleteById(Long id) {
		Commande commande = getById(id);
		achatRepository.deleteByCommande(commande);
		commandeRepository.delete(getById(id));
		
	}
	
	private void deleteByClient(Client client, Long id) {
		Commande commande = getById(id);
		deleteById(id);
		commandeRepository.deleteByClient(client);
	}

	public void delete(Long id) {
		deleteById(id);
	}
	
	
	
	public void delete(Commande commande) {
		checkCommandeIsNotNull(commande);
		deleteById(commande.getNumero());
	}

	public Commande update(Commande commande) {
		// @formatter:off
		Commande commandeEnBase = getById(commande.getNumero());
		commandeEnBase.setAchats(commande.getAchats().isEmpty() || commande.getAchats() == null ? commandeEnBase.getAchats() : commande.getAchats());
		commandeEnBase.setClient(commande.getClient());
		commandeEnBase.setDate(commande.getDate());
		return commandeRepository.save(commandeEnBase);
		// @formatter:on
	}

}
