package com.tfg.clientix.errorCharger;

public class ErrorRest 
{
	private String codError;
	private String litError;
	private boolean validado;
	public boolean isValidado() {
		return validado;
	}
	public void setValidado(boolean validado) {
		this.validado = validado;
	}
	public String getCodError() {
		return codError;
	}
	public void setCodError(String codError) {
		this.codError = codError;
	}
	public String getLitError() {
		return litError;
	}
	public void setLitError(String litError) {
		this.litError = litError;
	}
	
	
}
