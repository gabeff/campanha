package bean;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Campanha {

	private Integer id;
	private String nome;
	private Integer idTime;
	private Date dataInicioVigencia;
	private Date dataFimVigencia;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getIdTime() {
		return idTime;
	}

	public void setIdTime(Integer idTime) {
		this.idTime = idTime;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	public Date getDataInicioVigencia() {
		return dataInicioVigencia;
	}

	public void setDataInicioVigencia(Date dataInicioVigencia) {
		this.dataInicioVigencia = dataInicioVigencia;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	public Date getDataFimVigencia() {
		return dataFimVigencia;
	}

	public void setDataFimVigencia(Date dataFimVigencia) {
		this.dataFimVigencia = dataFimVigencia;
	}

}
