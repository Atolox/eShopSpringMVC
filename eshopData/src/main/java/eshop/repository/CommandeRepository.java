package eshop.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import eshop.entity.Client;
import eshop.entity.Commande;

public interface CommandeRepository extends JpaRepository<Commande, Long> {

	List<Commande> findByDate(LocalDate date);
	
	List<Commande> findByClientContaining (Client client);
	
	@Query("select f from Commande f left join fetch f.achats where f.id=:id")
	Optional<Commande> findByIdFetchAchats(@Param("id") Long id);
	
    @Query("delete Commande c where c.client=:client")
    @Modifying
    @Transactional
    void deleteByCommandes(@Param("client") Client client);
}
