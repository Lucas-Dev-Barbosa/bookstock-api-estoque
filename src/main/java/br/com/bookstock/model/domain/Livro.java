package br.com.bookstock.model.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "t_livro", uniqueConstraints = @UniqueConstraint(columnNames = { "isbn" }))
public class Livro {
	
	@Id
	@NotBlank(message = "O campo ID é obrigatório.")
	private Long id;

	private String titulo;

	private String sinopse;

	private String autor;

	private String isbn;

	private String editora;

	private byte[] fotoCapa;

	private BigDecimal preco;

	private Date dataPublicacao;

	private Integer numeroPaginas;

}
