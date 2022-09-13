package br.com.bookstock.model.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.bookstock.model.domain.Estoque;
import br.com.bookstock.model.domain.Livro;
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
	
	public Page<Estoque> buscarEstoquePorPaginacao(int paginaAtual, String ordem, String busca) {
		PageRequest pageRequest = PageRequest.of(paginaAtual - 1, 12, Sort.Direction.valueOf(ordem.toUpperCase()),
				"livro.titulo");

		if (busca == null || busca.isEmpty())
			return repository.getEstoquePorPaginacaoSemBusca(pageRequest);

		return repository.getEstoquePorPaginacao(busca, pageRequest);
	}

	public Estoque obterEstoquePorId(Long id) {
		return repository.findById(id).orElse(null);
	}
	
	@Transactional(readOnly = false)
	public Estoque gerarEstoque(Livro livro) {
		log.info("Gerando novo estoque com o livro [" + livro.getTitulo() + "]");
		Estoque estoque = new Estoque(livro);
		return repository.save(estoque);
	}
	
	@Transactional(readOnly = false)
	public Estoque editarEstoque(Estoque estoque) {
		Estoque oldEstoque = repository.getReferenceById(estoque.getId());
		estoque.setLivro(oldEstoque.getLivro());
		return repository.save(estoque);
	}

	@Transactional(readOnly = false)
	public void excluirEstoque(Long id) {
		log.info("Realizando exclusão do estoque ID [" + id + "]");
		repository.deleteById(id);
	}

}
