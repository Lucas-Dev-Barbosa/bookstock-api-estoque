package br.com.bookstock.model.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "t_livro", uniqueConstraints = @UniqueConstraint(columnNames = { "isbn" }))
@SuppressWarnings("serial")
public class Livro extends AbstractEntity {

	@NotBlank(message = "Digite o título.")
	private String titulo;

	@NotBlank(message = "Digite a sinopse do livro.")
	@Column(columnDefinition = "TEXT")
	private String sinopse;

	@NotBlank(message = "Digite o autor.")
	private String autor;

	@NotBlank(message = "Digite o ISBN.")
	@Column(length = 20)
	private String isbn;

	@NotBlank(message = "Digite o nome da editora.")
	private String editora;

	@Column(columnDefinition = "LONGBLOB")
	@NotEmpty(message = "Insira a foto da capa do livro.")
	private byte[] fotoCapa;

	@Column(columnDefinition = "DECIMAL(19,2)")
	@NotNull(message = "Informe o preço.")
	private BigDecimal preco;

	@NotNull(message = "Informe a data de publicação.")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dataPublicacao;

	@Min(value = 1, message = "O número de páginas tem que ser maior do que zero.")
	@NotNull(message = "Digite o número de páginas")
	private Integer numeroPaginas;

}
