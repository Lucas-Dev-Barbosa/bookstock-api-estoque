package br.com.bookstock.model.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.bookstock.model.domain.Estoque;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

	@Query("FROM Estoque e WHERE e.idLivro = :idLivro")
	Estoque getEstoqueByIdLivro(Long idLivro);
	
	@Query("FROM Estoque e")
	Page<Estoque> getEstoquePorPaginacao(Pageable pageable);
	
	@Modifying
	void deleteByIdLivro(Long id);
	
}
