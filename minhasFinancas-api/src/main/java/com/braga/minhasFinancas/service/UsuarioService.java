package com.braga.minhasFinancas.service;

import java.util.Optional;

import com.braga.minhasFinancas.model.entity.Usuario;

public interface UsuarioService {

	Usuario autenticar(String email, String senha);
	Usuario salvarUsuario(Usuario u);
	void validarEmail(String email);
	Optional<Usuario> findById(Long id);
}
