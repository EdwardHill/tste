package com.nelioalves.cursomc.pagseguro;

import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.nelioalves.cursomc.pagseguro.components.CheckoutGenerico;

import br.com.uol.pagseguro.api.PagSeguro;
import br.com.uol.pagseguro.api.exception.PagSeguroBadRequestException;
import br.com.uol.pagseguro.api.transaction.search.TransactionDetail;

@RestController
@RequestMapping(value = "/directPayment")
public class CheckoutTransparenteResource {
	@Autowired
	PagSeguro pagSeguro = PagSeguro.instance();
	@Autowired
	CheckoutTransparenteService service;
	private Gson data = new Gson();
	private static final Logger logger = Logger.getLogger(CheckoutTransparenteResource.class.getName());
	TransactionDetail checkout = null;

	@PostMapping(produces = "application/json")
	public ResponseEntity<String> payment(@Valid @RequestBody String obj)throws Exception, PagSeguroBadRequestException {
		logger.info("line - 1: " + obj);
		CheckoutGenerico dadosPayment = this.data.fromJson(obj, CheckoutGenerico.class);
		logger.info("line - 2 " + dadosPayment);

		switch (dadosPayment.getMethod()) {
			case "BOLETO":
				try {
					checkout = service.checkoutBoleto(dadosPayment);
				} catch (PagSeguroBadRequestException e) {
					e.printStackTrace();
				}
				break;
	
			case "CREDIT_CARD":
				try {
					checkout = service.checkoutCreditCard(dadosPayment);
				} catch (PagSeguroBadRequestException e) {
					e.printStackTrace();
				}
				break;
			case "ONLINE_DEBIT":
				try {
	
					checkout = service.checkoutDebitoOnline(dadosPayment);
	
				} catch (PagSeguroBadRequestException e) {
					e.printStackTrace();
				}
				break;
		}

		return ResponseEntity.status(HttpStatus.OK).body("{" + "\"statusCode\"" + " :" + 200 + "," + "\"status\"" + " :"
				+ "\"success\"" + "," + "\"content\"" + ":" + "" + data.toJson(checkout) + "}");
	}
	
	@PostMapping(value ="cancel-transaction")
	public ResponseEntity<Void> cancelRequest(@Valid @RequestBody String transactionCode)throws  Exception {
		CheckoutGenerico u = this.data.fromJson(transactionCode, CheckoutGenerico.class);
		pagSeguro.transactions().cancelByCode(u.getTransactionCode());
		return ResponseEntity.noContent().build();

	}
}
