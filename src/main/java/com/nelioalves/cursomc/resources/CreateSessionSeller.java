package com.nelioalves.cursomc.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.uol.pagseguro.api.PagSeguro;
import br.com.uol.pagseguro.api.PagSeguroEnv;
import br.com.uol.pagseguro.api.credential.Credential;
import br.com.uol.pagseguro.api.http.JSEHttpClient;
import br.com.uol.pagseguro.api.session.CreatedSession;
import br.com.uol.pagseguro.api.utils.logging.SimpleLoggerFactory;

public class CreateSessionSeller{

	
	    String sellerEmail = "edurock55@gmail.com";
	    String sellerToken = "086022FE2D2547C0847246E4C8C3804B";
	    CreatedSession createdSession = null;
	    @PostMapping
		public ResponseEntity<CreatedSession> getSession() {
	    final PagSeguro pagSeguro = PagSeguro
	        .instance(new SimpleLoggerFactory(), new JSEHttpClient(), Credential.sellerCredential(sellerEmail,
	            sellerToken), PagSeguroEnv.SANDBOX);

	    try {

	      // Criacao de sessao de seller
	       createdSession = pagSeguro.sessions().create();
	      System.out.println(createdSession.getId());
	    }catch (Exception e){
	      e.printStackTrace();
	    }
	    return ResponseEntity.ok().body(createdSession);
	    }

	}
