package com.nelioalves.cursomc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.uol.pagseguro.api.PagSeguro;

@Configuration
public class PagSeguroConfig {

	@Bean
	public PagSeguro getPagSeguro() {
		return PagSeguro.instance();
	}

}
