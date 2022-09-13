package br.com.bookstock.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.bookstock.model.domain.Estoque;
import br.com.bookstock.model.domain.Livro;
import br.com.bookstock.model.domain.service.EstoqueService;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/")
@Tag(name = "Estoque Endpoint")
public class EstoqueController {

	@Autowired
	private EstoqueService service;
	
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping
	@Tag(name = "listarEstoque", description = "Retorna uma lista com todos os registros contidos no estoque")
	public List<Estoque> listarEstoque() {
		return service.getListaEstoque();
	}

	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/paginacao")
	@Tag(name = "listarEstoquePorPaginacao", description = "Retorna uma lista paginada dos registros contidos no estoque")
	public Page<Estoque> listarEstoquePorPaginacao(
			@RequestParam(name = "pagina", required = false, defaultValue = "1") Integer pagina,
			@RequestParam(name = "direcao", required = false, defaultValue = "asc") String direcao,
			@RequestParam(name = "filtro", required = false) String filtro) {

		Page<Estoque> listaEstoque = service.buscarEstoquePorPaginacao(pagina, direcao, filtro);

		return listaEstoque;
	}
	
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/{id}")
	@Tag(name = "obterEstoquePorId", description = "Retorna um registro do estoque pelo seu ID")
	public Estoque obterEstoquePorId(@PathVariable Long id) {
		return service.obterEstoquePorId(id);
	}
	
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	@Tag(name = "gerarEstoque", description = "Gera um novo registro na base de estoque a partir de um novo livro cadastrado")
	public Estoque gerarEstoque(@Valid @RequestBody Livro livro) {
		Estoque estoque = service.gerarEstoque(livro);
		return estoque;
	}

	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	@PutMapping
	@ResponseStatus(code = HttpStatus.OK)
	@Tag(name = "editarEstoque", description = "Permite alteração de alguma informação de registro do estoque")
	public void editarEstoque(@Valid @RequestBody Estoque estoque) {
		service.editarEstoque(estoque);
	}
	
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	@Tag(name = "excluirTitulo", description = "Realiza a exclusão de um título no estoque pelo ID do estoque.")
	public void excluirEstoque(@PathVariable Long id) {
		service.excluirEstoque(id);
	}

}
