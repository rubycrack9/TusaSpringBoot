package com.tfg.clientix.models.entity;

import javax.persistence.Table;

@Table(name = "estadosenvio")
public class EstadoServicio {

	public String IdEstadoEnvio;
	public String DescripcionEstado;

	public EstadoServicio() {

	}

	public EstadoServicio(String IdEstadoEnvio, String DescripcionEstado)

	{
		this.IdEstadoEnvio = IdEstadoEnvio;
		this.DescripcionEstado = DescripcionEstado;

	}

	public String getIdEstadoEnvio() {
		return IdEstadoEnvio;
	}

	public void setIdEstadoEnvio(String idEstadoEnvio) {
		IdEstadoEnvio = idEstadoEnvio;
	}

	public String getDescripcionEstado() {
		return DescripcionEstado;
	}

	public void setDescripcionEstado(String descripcionEstado) {
		DescripcionEstado = descripcionEstado;
	}

}
