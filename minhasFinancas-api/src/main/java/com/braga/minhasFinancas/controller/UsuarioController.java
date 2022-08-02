package com.braga.minhasFinancas.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.braga.minhasFinancas.api.dto.UsuarioDTO;
import com.braga.minhasFinancas.exceptions.ErrorAutenticacao;
import com.braga.minhasFinancas.exceptions.RegraNegocioException;
import com.braga.minhasFinancas.model.entity.Usuario;
import com.braga.minhasFinancas.service.LancamentoService;
import com.braga.minhasFinancas.service.servImplement.LancamentoImpl;
import com.braga.minhasFinancas.service.servImplement.UsuarioServiceImpl;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioServiceImpl service;
	
	@Autowired
	private LancamentoImpl lancamentoService;


	@PostMapping("/autenticar")
	public ResponseEntity autenticar(@RequestBody UsuarioDTO dto) {
		try {
	 	    Usuario u =	service.autenticar(dto.getEmail(), dto.getSenha());
			return ResponseEntity.ok(u);
		}catch(ErrorAutenticacao e) {
			System.out.println(">>>>>>>> erro ao autenticar");
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping
	public ResponseEntity salvar(@RequestBody UsuarioDTO dto) {
		Usuario usuario = new Usuario(dto.getNome(),dto.getEmail(),dto.getSenha());
		try {
			Usuario uSave = service.salvarUsuario(usuario);
			return new ResponseEntity(uSave, HttpStatus.CREATED);
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@GetMapping("{id}/saldo")
	public ResponseEntity obterSaldo(@PathVariable("id")Long id){
		Optional<Usuario> usuario = service.findById(id);
		if(!usuario.isPresent()){
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		System.out.println(">>>>>>>>> idUsuario: "+id);
		return ResponseEntity.ok(lancamentoService.obterSaldoPorUsuario(id));
	}
}
