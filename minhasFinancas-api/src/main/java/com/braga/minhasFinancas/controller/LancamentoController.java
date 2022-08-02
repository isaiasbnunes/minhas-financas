package com.braga.minhasFinancas.controller;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.braga.minhasFinancas.api.dto.AtualizaStatusDTO;
import com.braga.minhasFinancas.api.dto.LancamentoDTO;
import com.braga.minhasFinancas.enums.StatusLancamento;
import com.braga.minhasFinancas.enums.TipoLancamento;
import com.braga.minhasFinancas.exceptions.RegraNegocioException;
import com.braga.minhasFinancas.model.entity.Lancamento;
import com.braga.minhasFinancas.model.entity.Usuario;
import com.braga.minhasFinancas.service.LancamentoService;
import com.braga.minhasFinancas.service.UsuarioService;

@RestController
@RequestMapping("/api/lancamentos")
public class LancamentoController {
    
    @Autowired
    private LancamentoService service;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
	public ResponseEntity salvar(@RequestBody LancamentoDTO dto) {
       try{

         Lancamento lancamento = converter(dto);
         lancamento = service.salvar(lancamento);
         return ResponseEntity.ok(lancamento);
       }catch(RegraNegocioException e){
         return ResponseEntity.badRequest().body(e.getMessage());
       }

    }


    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable("id") Long id){
        return service.findById(id).map(entity ->{
            service.deletar(entity);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }).orElseGet(()-> new ResponseEntity("Lançamento não encontrado", HttpStatus.BAD_REQUEST));
    }

    @GetMapping
    public ResponseEntity find(
        @RequestParam(value = "descricao",required = false) String descricao,
        @RequestParam(value = "mes",required = false) Integer mes,
        @RequestParam(value = "ano",required = false) Integer ano,
        @RequestParam("usuario") Long idUsuario
    ){
        Lancamento lancamento = new Lancamento();
        lancamento.setDescricao(descricao);
        lancamento.setMes(mes);
        lancamento.setAno(ano);

        Optional<Usuario> usuario = usuarioService.findById(idUsuario);
        if(!usuario.isPresent()){
            return ResponseEntity.badRequest().body("Usuário não encontrado!");
        }else{
            lancamento.setUsuario(usuario.get());
        }
        List<Lancamento> lancamentos = service.buscar(lancamento);
       return ResponseEntity.ok(lancamentos); 
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody LancamentoDTO dto){
       return service.findById(id).map(entity -> {
            try{
                Lancamento lancamento = converter(dto);
                lancamento.setId(entity.getId());
                service.atualizar(lancamento);
                return ResponseEntity.ok(lancamento);
            }catch(RegraNegocioException e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElseGet(()-> 
        new ResponseEntity("Lançamento não encontrado", HttpStatus.BAD_REQUEST));
    }

    @PutMapping("{id}/atualiza-status")
    public ResponseEntity atualizarStatus(@PathVariable("id") Long id, @RequestBody AtualizaStatusDTO dto){

        return service.findById(id).map(entity ->{
            StatusLancamento statusSelecionado = StatusLancamento.valueOf(dto.getStatus());

            if(statusSelecionado == null){
                return ResponseEntity.badRequest().body("Não foi possivel atualizar o status, envie um status valido");
            }
            try{
                entity.setStatus(statusSelecionado);
                service.atualizar(entity);
                return ResponseEntity.ok(entity);
            }catch(RegraNegocioException e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElseGet(() -> 
        new ResponseEntity("Lançamento não encontrado na base de dados", HttpStatus.BAD_REQUEST));
    }

    private Lancamento converter(LancamentoDTO dto){
        Lancamento l = new Lancamento();
        l.setId(dto.getId());
        l.setDescricao(dto.getDescricao());
        l.setAno(dto.getAno());
        l.setMes(dto.getMes());
        l.setValor(dto.getValor());

        Usuario u =usuarioService.findById(dto.getUsuario())
        .orElseThrow(()-> new RegraNegocioException("Usuário não encontrado!"));
        if(dto.getTipo() != null){
            l.setTipo(TipoLancamento.valueOf(dto.getTipo()));
        }
        if(dto.getStatus() != null){
            l.setStatus(StatusLancamento.valueOf(dto.getStatus()));
        }
        l.setUsuario(u);
        return l;
    }

}


