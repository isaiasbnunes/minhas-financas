package com.braga.minhasFinancas.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.braga.minhasFinancas.enums.StatusLancamento;
import com.braga.minhasFinancas.model.entity.Lancamento;

public interface LancamentoService {
	Lancamento salvar(Lancamento lancamento);
	Lancamento atualizar(Lancamento lancamento);
	void deletar(Lancamento lancamento);
	List<Lancamento> buscar(Lancamento lancamentoFiltro);
	void atualizarStatus(Lancamento lancamento, StatusLancamento status);
	void validar(Lancamento lancamento );
	Optional<Lancamento> findById(Long id);
	BigDecimal obterSaldoPorUsuario(Long id);
}
