package eshop;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import eshop.config.JpaConfig;
import eshop.entity.Achat;
import eshop.entity.AchatKey;
import eshop.entity.Adresse;
import eshop.entity.Civilite;
import eshop.entity.Client;
import eshop.entity.Commande;
import eshop.entity.Fournisseur;
import eshop.entity.Produit;
import eshop.repository.ClientRepository;
import eshop.repository.ProduitRepository;
import eshop.service.ClientService;
import eshop.service.CommandeService;
import eshop.service.ProduitService;

@SpringJUnitConfig(JpaConfig.class)
class ClientServiceTest {

	@Autowired
	private ClientService clientServ;

	@Autowired
	private CommandeService commandeServ;
	
	@Autowired
	private ProduitService produitServ;
	
	@Autowired
	private ClientRepository clientRepo;

	// Test création client
	@Test
	void create() {
		Client c1 = new Client("Antoine", "a.r@essai.fr", new Adresse("20", "rue blabla", "48399", "Trouville"),
				"Antoine", LocalDate.now(), Civilite.M);
		clientServ.create(c1);
	}

	// Test suppression client
	@Test
	void delete() {
		Client c2 = new Client("Arrthur", "a.azd.fr", new Adresse("20", "rue blabla", "48399", "Trouville"), "Blabla",
				LocalDate.now(), Civilite.M);
		clientServ.create(c2);
		Produit prod = new Produit("Essai", "Blabla", 1000);
		Commande com = new Commande();
		com.setClient(c2);
		AchatKey achkey = new AchatKey(com, prod);
		Achat achat = new Achat(achkey, 3);
		List<Achat> achats = new ArrayList<Achat>();
		achats.add(achat);
		com.setAchats(achats);
		commandeServ.create(com);
//		clientServ.delete(c2);
		System.out.println(clientRepo.findByIdFetchCommandes(c2.getId()));
	}

	// Test mise à jour de client
	@Test
	void update() {
		Client c3 = new Client("Quentin", "qsdsqd.azd.fr", new Adresse("20", "rue zer", "32424", "Trouville"), "Blabla",
				LocalDate.now(), Civilite.M);
		clientServ.create(c3);
		c3.setCivilite(Civilite.MLLE);
		clientServ.update(c3);
		System.out.println(c3.getCivilite());
	}

	// Test des getby ID, email, nom complet et partiel
	@Test
	void getBy() {
		System.out.println(clientServ.getById(50L));
		System.out.println(clientServ.getByEmail("qsdsqd.azd.fr"));
		System.out.println(clientServ.getByNom("Antoine"));
		System.out.println(clientServ.getByNomCont("toi"));
	}

	// Test liste par page
	@Test
	void pages() {
		Client c4 = new Client("Damien", "qsdsqd.azd.fr", new Adresse("20", "rue zer", "32424", "Trouville"), "Blabla",
				LocalDate.now(), Civilite.M);
		clientServ.create(c4);
		Client c2 = new Client("Arrthur", "a.azd.fr", new Adresse("20", "rue blabla", "48399", "Trouville"), "Blabla",
				LocalDate.now(), Civilite.M);
		clientServ.create(c2);
		Client c1 = new Client("Antoine", "a.r@essai.fr", new Adresse("20", "rue blabla", "48399", "Trouville"),
				"Antoine", LocalDate.now(), Civilite.M);
		clientServ.create(c1);
		Client c3 = new Client("Quentin", "qsdsqd.azd.fr", new Adresse("20", "rue zer", "32424", "Trouville"), "Blabla",
				LocalDate.now(), Civilite.M);
		clientServ.create(c3);
		Pageable pageable = PageRequest.of(0, 2);
		Page<Client> page = clientServ.getAll(pageable);
		page.forEach(f -> {
			System.out.println(f.getId());
		});
		page = clientServ.getAll(page.nextOrLastPageable());
		System.out.println(page);
		page.forEach(f -> {
			System.out.println(f.getId());
		});
	}
}
