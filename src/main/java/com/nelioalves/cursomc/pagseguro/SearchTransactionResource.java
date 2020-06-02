package com.nelioalves.cursomc.pagseguro;

import java.util.Date;
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
import com.google.gson.GsonBuilder;
import com.nelioalves.cursomc.pagseguro.components.CheckoutGenerico;

import br.com.uol.pagseguro.api.PagSeguro;
import br.com.uol.pagseguro.api.common.domain.DataList;
import br.com.uol.pagseguro.api.common.domain.builder.DateRangeBuilder;
import br.com.uol.pagseguro.api.transaction.search.TransactionDetail;
import br.com.uol.pagseguro.api.transaction.search.TransactionSummary;

@RestController
@RequestMapping(value = "/search-transaction")
public class SearchTransactionResource {
	@Autowired
	PagSeguro pagSeguro = PagSeguro.instance();
	private Gson data = new GsonBuilder().create();
	private static final Logger logger = Logger.getLogger(CheckoutTransparenteResource.class.getName());
	TransactionDetail transaction =null;
	
	
	@PostMapping(value = "/bycode" , produces= "application/json")
	public ResponseEntity<TransactionDetail> searchTransactionByCode(@Valid @RequestBody String transactionCode) throws  Exception {	
		
		CheckoutGenerico u = this.data.fromJson(transactionCode, CheckoutGenerico.class);	
		System.out.println(transaction);
		transaction = pagSeguro.transactions().search().byCode(u.getTransactionCode());
		return ResponseEntity.status(HttpStatus.FOUND).body(transaction);
	}

	@PostMapping(value = "/by-notification-code")
	public ResponseEntity<TransactionDetail> searchTransactionByNotificationCode(String codigoNotificacao)throws  Exception {
		TransactionDetail transaction = pagSeguro.transactions().search().byNotificationCode(codigoNotificacao);
		System.out.println(transaction);
		return ResponseEntity.status(HttpStatus.FOUND).body(transaction);
	}

	@PostMapping(value = "/list")
	public ResponseEntity<DataList<? extends TransactionSummary>> searchTransaction(Date dataInicial, Date dataFinal)throws  Exception {
		final DataList<? extends TransactionSummary> transactions = pagSeguro.transactions().search()
				.byDateRange(new DateRangeBuilder().between(dataInicial, dataFinal), 1, 10);
		System.out.println(transactions.size());
		return ResponseEntity.status(HttpStatus.FOUND).body(transactions);
	}

}
