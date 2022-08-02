package com.braga.minhasFinancas.exceptions;

public class ErrorAutenticacao extends RuntimeException {
	
	public ErrorAutenticacao(String msn) {
		super(msn);
	}
}
