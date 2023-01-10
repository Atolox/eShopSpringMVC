package eshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import eshop.entity.Commande;
import eshop.exception.CommandeException;
import eshop.exception.IdException;
import eshop.repository.CommandeRepository;


@Service
public class CommandeService {

	@Autowired
	private CommandeRepository commandeRepository;
	
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

}
