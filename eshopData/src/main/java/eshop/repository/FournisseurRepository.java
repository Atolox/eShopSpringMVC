package eshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import eshop.entity.Fournisseur;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {

	// remonter liste par nom
	List<Fournisseur> findByNom(String nom);
	
	// remonter liste par nom contenant
	List<Fournisseur> findByNomContaining(String nom);

	// remonter liste par contact
	List<Fournisseur> findByContact(String contact);

	// remonter liste par contact contenant
	List<Fournisseur> findByContactContaining(String contact);

	// remonter liste par email
	List<Fournisseur> findByEmail(String email);

	// remonter liste par email contenant
	List<Fournisseur> findByEmailContaining(String email);

	// remonter page avec des noms contenant
	Page<Fournisseur> findByNomContaining(String nom, Pageable pageable);

	@Query("select f from Fournisseur f left join fetch f.produits where f.id =:id")
	Optional<Fournisseur> findByKeyFetchProduits(@Param("id") Long id);
}
