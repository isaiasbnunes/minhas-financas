package com.braga.minhasFinancas.service.servImplement;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braga.minhasFinancas.enums.StatusLancamento;
import com.braga.minhasFinancas.enums.TipoLancamento;
import com.braga.minhasFinancas.exceptions.RegraNegocioException;
import com.braga.minhasFinancas.model.entity.Lancamento;
import com.braga.minhasFinancas.model.repository.LancamentoRepository;
import com.braga.minhasFinancas.service.LancamentoService;

@Service
public class LancamentoImpl implements LancamentoService{

	@Autowired
	private LancamentoRepository repository;
	
	@Override
	@Transactional
	public Lancamento salvar(Lancamento lancamento) {
		validar(lancamento);
		lancamento.setStatus(StatusLancamento.PENDENTE);
		return repository.save(lancamento);
	}

	@Override
	@Transactional
	public Lancamento atualizar(Lancamento lancamento) {
		validar(lancamento);
		Objects.requireNonNull(lancamento.getId());
		return repository.save(lancamento);
	}

	@Override
	@Transactional
	public void deletar(Lancamento lancamento) {
		Objects.requireNonNull(lancamento.getId());
		repository.delete(lancamento);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Lancamento> buscar(Lancamento lancamentoFiltro) {
		Example example = Example.of(lancamentoFiltro,
				ExampleMatcher.matching().withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING));
		return repository.findAll(example);
	}

	@Override
	public void atualizarStatus(Lancamento lancamento, StatusLancamento status) {
		lancamento.setStatus(status);
		atualizar(lancamento);
	}

	@Override
	public void validar(Lancamento l) {
		if(l.getDescricao() == null || l.getDescricao().trim().equals("")) {
			throw new RegraNegocioException("Informe uma descrição válida.");
		}
		
		if(l.getMes() == null || l.getMes() < 1 || l.getMes() > 12) {
			throw new RegraNegocioException("Informe um mês válido.");
		}
		
		if(l.getAno() == null || l.getAno().toString().length() != 4) {
			throw new RegraNegocioException("Informe um ano válido.");
		}
		
		if(l.getUsuario() == null || l.getUsuario().getId() == null) {
			throw new RegraNegocioException("Informe um usuário.");
		}
		
		if(l.getValor() == null || l.getValor().compareTo(BigDecimal.ZERO) < 1) {
			throw new RegraNegocioException("Informe um valor válido.");
		}
		
	}

	@Override
	public Optional<Lancamento> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal obterSaldoPorUsuario(Long id) {
		 BigDecimal receitas = repository.obterSaldoPorTipoLancamentoUsuario(id, TipoLancamento.RECEITA);
		 BigDecimal despesas = repository.obterSaldoPorTipoLancamentoUsuario(id, TipoLancamento.DESPESA);
			if(receitas == null){
				receitas = BigDecimal.ZERO;
			}
			if(despesas == null){
				despesas = BigDecimal.ZERO;
			}
		 return receitas.subtract(despesas);
	}
	
	

}
