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
import com.google.gson.GsonBuilder;
import com.nelioalves.cursomc.pagseguro.components.AuthorizationRegistrationAux;

import br.com.uol.pagseguro.api.PagSeguro;
import br.com.uol.pagseguro.api.application.authorization.AuthorizationRegistration;
import br.com.uol.pagseguro.api.application.authorization.AuthorizationRegistrationBuilder;
import br.com.uol.pagseguro.api.application.authorization.RegisteredAuthorization;
import br.com.uol.pagseguro.api.application.authorization.search.AuthorizationDetail;
import br.com.uol.pagseguro.api.common.domain.PermissionCode;
import br.com.uol.pagseguro.api.common.domain.builder.AccountRegisterSuggestionBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.AddressBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.DocumentBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.PersonBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.PhoneAccountBuilder;

@RestController
@RequestMapping(value = "/create-authorization")
public class CreateAuthorizationResource {
	@Autowired
	PagSeguro pagSeguro = PagSeguro.instance();
	private Gson data = new GsonBuilder().create();
	private static final Logger logger = Logger.getLogger(CheckoutTransparenteResource.class.getName());
	AuthorizationRegistration authorizationRe = null;
	
	
	@PostMapping(value = "/create-simple", produces = "application/json")
	public ResponseEntity<RegisteredAuthorization> createSimple( @RequestBody String auth)
			throws Exception {
		AuthorizationRegistrationAux u = this.data.fromJson(auth, AuthorizationRegistrationAux.class);
		
		AuthorizationRegistration authorizationRegistration = new AuthorizationRegistrationBuilder()
				.withNotificationUrl(u.getNotificationURL()).withReference(u.getReference())
				.withRedirectURL(u.getRedirectURL())
				.addPermission(PermissionCode.Code.CREATE_CHECKOUTS)
				.addPermission(PermissionCode.Code.SEARCH_TRANSACTIONS)
                .addPermission(PermissionCode.Code.RECEIVE_TRANSACTION_NOTIFICATIONS)
				.build();
		RegisteredAuthorization ra = pagSeguro.authorizations().register(authorizationRegistration);
		return ResponseEntity.status(HttpStatus.FOUND).body(ra);
	}
	
	
	@PostMapping(value = "/create-personal-Suggestion",produces="application/json")
	public ResponseEntity<RegisteredAuthorization> createPersonalSuggestion(@Valid @RequestBody AuthorizationRegistration auth)throws Exception{
		try{
            
            // Registra as autorizações com sugestão de cadastro de pessoa
            AuthorizationRegistration authorizationRegistration =
                new AuthorizationRegistrationBuilder()
                    .withReference(auth.getReference())
                    .addPermission(PermissionCode.Code.CREATE_CHECKOUTS)
                    .addPermission(PermissionCode.Code.SEARCH_TRANSACTIONS)
                    .addPermission(PermissionCode.Code.RECEIVE_TRANSACTION_NOTIFICATIONS)
                    .withRedirectURL(auth.getRedirectURL())
                    .withNotificationUrl(auth.getNotificationURL())
                    .withAccount(new AccountRegisterSuggestionBuilder()
                        .withEmail(auth.getAccount().getEmail())
                        .withType(auth.getAccount().getType())
                        .withPerson(new PersonBuilder()
                            .withName(auth.getAccount().getPerson().getName())
                            .addDocument(new DocumentBuilder()
                                .withType(auth.getAccount().getPerson().getDocuments().get(0).getType())
                                .withValue(auth.getAccount().getPerson().getDocuments().get(0).getValue())
                            )
                            .withBirthDate(auth.getAccount().getPerson().getBirthDate())
                            .addPhone(new PhoneAccountBuilder()
                                .withType(auth.getAccount().getPerson().getPhones().get(0).getType())
                                .withAreaCode(auth.getAccount().getPerson().getPhones().get(0).getAreaCode())
                                .withNumber(auth.getAccount().getPerson().getPhones().get(0).getNumber())
                            )
                            
                            .withAddress(new AddressBuilder()
                                .withPostalCode(auth.getAccount().getPerson().getAddress().getPostalCode())
                                .withStreet(auth.getAccount().getPerson().getAddress().getStreet())
                                .withNumber(auth.getAccount().getPerson().getAddress().getNumber())
                                .withComplement(auth.getAccount().getPerson().getAddress().getComplement())
                                .withDistrict(auth.getAccount().getPerson().getAddress().getDistrict())
                                .withCity(auth.getAccount().getPerson().getAddress().getCity())
                                .withState(auth.getAccount().getPerson().getAddress().getState())
                                .withCountry(auth.getAccount().getPerson().getAddress().getCountry())
                            )
                        )
                    )
                    .build();

            RegisteredAuthorization ra = pagSeguro.authorizations().registerWithSuggestion(authorizationRegistration);
            System.out.print(ra);

        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
		
	}
}
