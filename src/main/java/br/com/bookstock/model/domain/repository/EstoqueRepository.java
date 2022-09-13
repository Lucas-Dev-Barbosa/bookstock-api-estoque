package br.com.bookstock.model.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.bookstock.model.domain.Estoque;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

	@Query("FROM Estoque e WHERE LOWER(e.livro.titulo) like %:busca% OR LOWER(e.livro.autor) like %:busca%")
	Page<Estoque> getEstoquePorPaginacao(String busca, Pageable pageable);
	
	@Query("FROM Estoque e")
	Page<Estoque> getEstoquePorPaginacaoSemBusca(Pageable pageable);
	
}
