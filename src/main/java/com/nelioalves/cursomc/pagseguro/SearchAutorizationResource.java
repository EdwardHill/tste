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
import com.nelioalves.cursomc.pagseguro.components.AuthorizationRegistrationAux;
import com.nelioalves.cursomc.pagseguro.components.CheckoutGenerico;

import br.com.uol.pagseguro.api.PagSeguro;
import br.com.uol.pagseguro.api.application.authorization.search.AuthorizationDetail;
import br.com.uol.pagseguro.api.application.authorization.search.AuthorizationSummary;
import br.com.uol.pagseguro.api.common.domain.DataList;
import br.com.uol.pagseguro.api.common.domain.builder.DateRangeBuilder;

@RestController
@RequestMapping(value = "/search-autorization")
public class SearchAutorizationResource {
	@Autowired
	PagSeguro pagSeguro = PagSeguro.instance();
	private Gson data = new GsonBuilder().create();
	private static final Logger logger = Logger.getLogger(CheckoutTransparenteResource.class.getName());
	AuthorizationDetail authorizationDetail = null;

	@PostMapping(value = "/bycode", produces = "application/json")
	public ResponseEntity<AuthorizationDetail> searchTransactionByCode(@Valid @RequestBody String transactionCode)
			throws Exception {

		AuthorizationDetail u = this.data.fromJson(transactionCode, AuthorizationDetail.class);
		System.out.println(authorizationDetail);
		authorizationDetail = pagSeguro.authorizations().search().byCode(u.getCode());
		return ResponseEntity.status(HttpStatus.FOUND).body(authorizationDetail);
	}

	@PostMapping(value = "/by-notification-code")
	public ResponseEntity<AuthorizationDetail> searchTransactionByNotificationCode(String codigoNotificacao)
			throws Exception {
		AuthorizationRegistrationAux u = this.data.fromJson(codigoNotificacao, AuthorizationRegistrationAux.class);
		System.out.println(authorizationDetail);
		 authorizationDetail = pagSeguro.authorizations().search().byNotificationCode(u.getNotificationURL());
		System.out.print(authorizationDetail);
		return ResponseEntity.status(HttpStatus.FOUND).body(authorizationDetail);
	}

	@PostMapping(value = "/list")
	public ResponseEntity<DataList<? extends AuthorizationSummary>> searchTransaction(Date dataInicial, Date dataFinal)
			throws Exception {
		final DataList<? extends  AuthorizationSummary> authorizations = pagSeguro.authorizations().search()
				.byDateRange(new DateRangeBuilder().between(dataInicial, dataFinal), 1, 10);
		System.out.println(authorizations.size());
		return ResponseEntity.status(HttpStatus.FOUND).body(authorizations);
	}

}
