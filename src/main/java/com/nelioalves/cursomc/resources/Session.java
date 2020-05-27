package com.nelioalves.cursomc.resources;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.uol.pagseguro.api.PagSeguro;
import br.com.uol.pagseguro.api.PagSeguroEnv;
import br.com.uol.pagseguro.api.credential.Credential;
import br.com.uol.pagseguro.api.http.JSEHttpClient;
import br.com.uol.pagseguro.api.session.CreatedSession;
import br.com.uol.pagseguro.api.utils.logging.SimpleLoggerFactory;

@RestController
@RequestMapping(value = "/session")
public class Session{

	@PostMapping(produces = { "application/json" }, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<String> getSession() {
		CreatedSession data = null;
		try {

			final PagSeguro pagSeguro = PagSeguro.instance();
			if (pagSeguro != null) {

				data = pagSeguro.sessions().create();
				System.out.println(data.getId());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(
						"{"
						+"\"statusCode\"" +" :"+200+","+
						"\"status\""+" :"+"\"success\""+","+
						"\"content\""+" : "+"\""+ data.getId()+"\""+   
						"}");
	}

}
