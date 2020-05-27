package com.nelioalves.cursomc.resources;

import java.math.BigDecimal;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nelioalves.cursomc.dto.BoletoDTO;

import br.com.uol.pagseguro.api.PagSeguro;
import br.com.uol.pagseguro.api.PagSeguroEnv;
import br.com.uol.pagseguro.api.checkout.CheckoutRegistrationBuilder;
import br.com.uol.pagseguro.api.checkout.RegisteredCheckout;
import br.com.uol.pagseguro.api.common.domain.ShippingType;
import br.com.uol.pagseguro.api.common.domain.builder.AcceptedPaymentMethodsBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.AddressBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.ConfigBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.PaymentItemBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.PaymentMethodBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.PaymentMethodConfigBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.PhoneBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.SenderBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.ShippingBuilder;
import br.com.uol.pagseguro.api.common.domain.enums.ConfigKey;
import br.com.uol.pagseguro.api.common.domain.enums.Currency;
import br.com.uol.pagseguro.api.common.domain.enums.PaymentMethodGroup;
import br.com.uol.pagseguro.api.common.domain.enums.State;
import br.com.uol.pagseguro.api.credential.Credential;
import br.com.uol.pagseguro.api.http.JSEHttpClient;
import br.com.uol.pagseguro.api.transaction.register.DirectPaymentRegistrationBuilder;
import br.com.uol.pagseguro.api.transaction.search.TransactionDetail;
import br.com.uol.pagseguro.api.utils.logging.SimpleLoggerFactory;
import br.com.uol.pagseguro.domain.Address;
import br.com.uol.pagseguro.domain.Item;
import br.com.uol.pagseguro.domain.Phone;
import br.com.uol.pagseguro.domain.Sender;
import br.com.uol.pagseguro.domain.SenderDocument;


@RestController
@RequestMapping(value = "/directPayment")
public class CheckoutBoleto {
	private Gson gson = new GsonBuilder().create();
	
	private static final Logger logger = Logger.getLogger(CheckoutBoleto.class.getName());
	
@RequestMapping(method = RequestMethod.POST, produces = "application/json")
public ResponseEntity<String> payment(@Valid @RequestBody String obj) throws Exception {

	Sender sender = new Sender();
	Phone phone = new Phone();
	SenderDocument document = new SenderDocument();
	Address address = new Address();
	Item item = new Item();

	logger.info("line - 1: " + obj);
	BoletoDTO dadosPayment =this.gson.fromJson(obj, BoletoDTO.class);
	logger.info("line - 2 " + dadosPayment);

	 
	 
	 
	 try {
		 
		 final PagSeguro pagSeguro = PagSeguro
			        .instance(new SimpleLoggerFactory(), new JSEHttpClient(),
			            Credential.sellerCredential("edurock55@gmail.com", "086022FE2D2547C0847246E4C8C3804B"), PagSeguroEnv.SANDBOX);
	
		 
		      // Checkout transparente (pagamento direto) com boleto
		      TransactionDetail bankSlipTransaction =
		          pagSeguro.transactions().register(new DirectPaymentRegistrationBuilder()
		              .withPaymentMode("default")
		              .withCurrency(Currency.BRL)
		              .withExtraAmount(new BigDecimal(100.00))
		              .addItem(new PaymentItemBuilder()
		                  .withId("0001")
		                  .withDescription("Produto PagSeguroI")
		                  .withAmount(new BigDecimal(99999.99))
		                  .withQuantity(1)
		                  .withWeight(1000))

		              .addItem(new PaymentItemBuilder()
		                  .withId("0002")
		                  .withDescription("Produto PagSeguroII")
		                  .withAmount(new BigDecimal(99999.98))
		                  .withQuantity(2)
		                  .withWeight(750)
		              )
		              .withNotificationURL("www.sualoja.com.br/notification")
		              .withReference("LIBJAVA_DIRECT_PAYMENT")
		              .withSender(new SenderBuilder()
		                  .withEmail("comprador@uol.com.br")
		                  .withName("Jose Comprador")
		                  .withCPF("99999999999")
		                  /*
		                   * Para saber como obter o valor do Hash, acesse:
		                   * https://devs.pagseguro.uol.com.br/docs/checkout-web-usando-a-sua-tela#obter-identificacao-do-comprador
		                   */
		                  .withHash("abc123")
		                  .withPhone(new PhoneBuilder()
		                      .withAreaCode("99")
		                      .withNumber("99999999")))
		              .withShipping(new ShippingBuilder()
		                  .withType(ShippingType.Type.SEDEX)
		                  .withCost(BigDecimal.TEN)
		                  .withAddress(new AddressBuilder()
		                      .withPostalCode("99999999")
		                      .withCountry("BRA")
		                      .withState(State.SP)
		                      .withCity("Cidade Exemplo")
		                      .withComplement("99o andar")
		                      .withDistrict("Jardim Internet")
		                      .withNumber("9999")
		                      .withStreet("Av. PagSeguro")))
		          ).withBankSlip();
		      System.out.println(bankSlipTransaction);


		    }catch (Exception e){
		      e.printStackTrace();
		    }

	return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(200));

}
}
