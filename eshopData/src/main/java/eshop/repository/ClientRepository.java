package eshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import eshop.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

	// Recherche par nom entier (liste)
	List<Client> findByNom(String nom);

	// Recheche par nom partielle (liste)
	List<Client> findByNomContaining(String nom);

	// Recherche par mail
	List<Client> findByEmail(String email);

	// Recherche par nom entier (page)
	Page<Client> findByNom(String nom, Pageable pageable);

	// Recheche par nom partielle (page)
	Page<Client> findByNomContaining(String nom, Pageable pageable);

	// Recherche par (page)
	Page<Client> findByEmail(String email, Pageable pageable);

	// Recherche par id donnant la liste des commandes
	@Query("select c from Client c left join fetch c.commandes where c.id =:id")
	Optional<Client> findByIdFetchCommandes(@Param("id") Long id);

}
