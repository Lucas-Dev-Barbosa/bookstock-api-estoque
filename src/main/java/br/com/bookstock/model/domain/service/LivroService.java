package br.com.bookstock.model.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.bookstock.exception.EstoqueException;
import br.com.bookstock.model.domain.Estoque;
import br.com.bookstock.model.domain.Livro;
import br.com.bookstock.model.domain.repository.EstoqueRepository;
import br.com.bookstock.model.domain.repository.LivroRepository;
import lombok.extern.java.Log;

@Log
@Service
public class LivroService {

	@Autowired
	private LivroRepository repository;

	@Autowired
	private EstoqueRepository estoqueRepository;

	public List<Livro> getListaLivros() {
		return repository.findAll();
	}

	public Page<Livro> buscarLivrosPorPaginacao(int paginaAtual, String ordem, String busca) {
		PageRequest pageRequest = PageRequest.of(paginaAtual - 1, 12, Sort.Direction.valueOf(ordem.toUpperCase()),
				"titulo");

		if (busca == null || busca.isEmpty())
			return repository.getLivrosPorPaginacaoSemBusca(pageRequest);

		return repository.getLivrosPorPaginacao(busca, pageRequest);
	}

	@Transactional(readOnly = false)
	public Estoque salvarLivro(Livro livro) throws EstoqueException {
		if (livro != null)
			log.info("Salvando informacoes do livro [" + livro.getTitulo() + "] no estoque");

		Estoque estoque = new Estoque(livro);
		return estoqueRepository.save(estoque);
	}

	@Transactional(readOnly = false)
	public Livro editarLivro(Livro livro) throws EstoqueException {
		if (livro != null)
			log.info("Editando informacoes do livro [" + livro.getTitulo() + "] no estoque");

		return repository.save(livro);
	}

	public Livro obterLivroPorId(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Transactional(readOnly = false)
	public void excluirTitulo(Long id) {
		Estoque estoque = estoqueRepository.getEstoquePorLivroId(id);

		if (estoque != null) {
			estoqueRepository.deleteById(estoque.getId());
		} else {
			repository.deleteById(id);
		}
	}

}
