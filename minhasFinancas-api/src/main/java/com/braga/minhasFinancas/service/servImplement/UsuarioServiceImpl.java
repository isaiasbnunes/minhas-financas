package com.braga.minhasFinancas.service.servImplement;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.braga.minhasFinancas.exceptions.ErrorAutenticacao;
import com.braga.minhasFinancas.exceptions.RegraNegocioException;
import com.braga.minhasFinancas.model.entity.Usuario;
import com.braga.minhasFinancas.model.repository.UsuarioRepository;
import com.braga.minhasFinancas.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService{

	@Autowired
	private UsuarioRepository repository;
	
	@Override
	public Usuario autenticar(String email, String senha) {
		
		Optional<Usuario> usuario = repository.findByEmail(email);
		
		if(!usuario.isPresent()) {
			throw new ErrorAutenticacao("Usuário não encontrado");
		}
		
		if(!usuario.get().getSenha().equals(senha)) {
			throw new ErrorAutenticacao("Senha inválida");
		}

		return usuario.get();
	}

	@Override
	public Usuario salvarUsuario(Usuario u) {
		validarEmail(u.getEmail());
		return repository.save(u);
	}

	@Override
	public void validarEmail(String email) {
		boolean existe = repository.existsByEmail(email);
		if(existe) {
			throw new RegraNegocioException("Já existe um usuário cadastrado com esse e-mail!");
		}
	}

	@Override
	public Optional<Usuario> findById(Long id) {
		return repository.findById(id);
	}

}




