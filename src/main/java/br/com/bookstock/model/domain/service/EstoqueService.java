package br.com.bookstock.model.domain.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.bookstock.model.domain.Estoque;
import br.com.bookstock.model.domain.repository.EstoqueRepository;
import lombok.extern.java.Log;

@Log
@Service
public class EstoqueService {

	@Autowired
	private EstoqueRepository repository;

	public List<Estoque> getListaEstoque() {
		return repository.findAll();
	}

	public Page<Estoque> buscarEstoquePorPaginacao(int paginaAtual, String ordem) {
		PageRequest pageRequest = PageRequest.of(paginaAtual - 1, 12, Sort.Direction.valueOf(ordem.toUpperCase()),
				"id");

		return repository.getEstoquePorPaginacao(pageRequest);
	}

	public Estoque obterEstoquePorId(Long id) {
		return repository.findById(id).orElse(null);
	}

	public Estoque obterEstoquePorIdLivro(Long id) {
		return repository.getEstoqueByIdLivro(id);
	}

	@Transactional(readOnly = false)
	public Estoque gerarEstoque(Map<String, Long> livro) {
		Long idLivro = livro.get("id-livro");
		log.info("Gerando novo estoque com o livro ID [" + idLivro + "]");

		Estoque estoque = new Estoque(idLivro);
		return repository.save(estoque);
	}

	@Transactional(readOnly = false)
	public Estoque editarEstoque(Estoque estoque) {
		Estoque oldEstoque = repository.getReferenceById(estoque.getId());
		estoque.setIdLivro(oldEstoque.getIdLivro());
		return repository.save(estoque);
	}

	@Transactional(readOnly = false)
	public void excluirEstoquePorId(Long id) {
		log.info("Realizando exclusão do estoque pelo ID do estoque - ID [" + id + "]");
		repository.deleteById(id);
	}
	
	@Transactional(readOnly = false)
	public void excluirEstoquePorIdLivro(Long id) {
		log.info("Realizando exclusão do estoque pelo ID do livro - ID [" + id + "]");
		repository.deleteByIdLivro(id);
	}

}
