package com.nelioalves.cursomc.resources;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
import com.nelioalves.cursomc.components.CheckoutGenerico;

import br.com.uol.pagseguro.api.PagSeguro;
import br.com.uol.pagseguro.api.PagSeguroEnv;
import br.com.uol.pagseguro.api.common.domain.BankName;
import br.com.uol.pagseguro.api.common.domain.ShippingType;
import br.com.uol.pagseguro.api.common.domain.builder.AddressBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.BankBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.CreditCardBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.DocumentBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.HolderBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.InstallmentBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.PaymentItemBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.PhoneBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.SenderBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.ShippingBuilder;
import br.com.uol.pagseguro.api.common.domain.enums.Currency;
import br.com.uol.pagseguro.api.credential.Credential;
import br.com.uol.pagseguro.api.http.JSEHttpClient;
import br.com.uol.pagseguro.api.transaction.register.DirectPaymentRegistrationBuilder;
import br.com.uol.pagseguro.api.transaction.search.TransactionDetail;
import br.com.uol.pagseguro.api.utils.logging.SimpleLoggerFactory;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;

@RestController
@RequestMapping(value = "/directPayment")
public class CheckoutTransparente {
	private Gson data = new GsonBuilder().create();
	private static final Logger logger = Logger.getLogger(CheckoutTransparente.class.getName());

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> payment(@Valid @RequestBody String obj) throws Exception , PagSeguroServiceException{

		TransactionDetail checkout = null;
		logger.info("line - 1: " + obj);
		CheckoutGenerico dadosPayment = this.data.fromJson(obj, CheckoutGenerico.class);
		logger.info("line - 2 " + dadosPayment);
		
		switch (dadosPayment.getMethod()) {
		case "BOLETO":
			try {
				final PagSeguro pagSeguro = PagSeguro.instance(new SimpleLoggerFactory(), new JSEHttpClient(),
						Credential.sellerCredential("edurock55@gmail.com", "086022FE2D2547C0847246E4C8C3804B"),
						PagSeguroEnv.SANDBOX);
				// Checkout transparente (pagamento direto) com boleto
				checkout = pagSeguro.transactions()
						.register(
								new DirectPaymentRegistrationBuilder().withPaymentMode("default").withCurrency(Currency.BRL)
									.withExtraAmount(new BigDecimal(0))
									.addItem(new PaymentItemBuilder().withId((dadosPayment.getItems()[0].getId()))
											.withDescription(dadosPayment.getItems()[0].getDescription())
											.withAmount(new BigDecimal(dadosPayment.getItems()[0].getAmount()))
											.withQuantity(1).withWeight(0))
									.withNotificationURL("localhost:8080/notification")
									.withReference(dadosPayment.getReference())
									.withSender(new SenderBuilder().withEmail(dadosPayment.getSender().getEmail())
											.withName(dadosPayment.getSender().getName())
											.withCPF(dadosPayment.getSender().getDocument().getValue())
											.withHash(dadosPayment.getSender().getHash())
											
									.withPhone(new PhoneBuilder()
											.withAreaCode(dadosPayment.getSender().getPhone().getAreaCode())
											.withNumber(dadosPayment.getSender().getPhone().getNumber())))
									.withShipping(new ShippingBuilder().withType(ShippingType.Type.USER_CHOISE)
											.withCost(BigDecimal.ZERO)
											.withAddress(new AddressBuilder()
											.withPostalCode(dadosPayment.getBilling().getPostalCode())
											.withCountry(dadosPayment.getBilling().getCountry())
											.withState(dadosPayment.getBilling().getState())
											.withCity(dadosPayment.getBilling().getCity())
											.withComplement(dadosPayment.getBilling().getComplement())
											.withDistrict(dadosPayment.getBilling().getDistrict())
											.withNumber(dadosPayment.getBilling().getNumber())
											.withStreet(dadosPayment.getBilling().getStreet())))									
							).withBankSlip();
				System.out.println(checkout.getPaymentLink());
			} catch (Exception e) {
				e.printStackTrace();
			}
            break;
           
        case "CREDIT_CARD":
        	try {
    			final PagSeguro pagSeguro = PagSeguro.instance(new SimpleLoggerFactory(), new JSEHttpClient(),
    					Credential.sellerCredential("edurock55@gmail.com", "086022FE2D2547C0847246E4C8C3804B"),
    					PagSeguroEnv.SANDBOX);
    			// Checkout transparente (pagamento direto) cartão de crédito
    			checkout = pagSeguro.transactions()
    					.register(
    							new DirectPaymentRegistrationBuilder().withPaymentMode("default").withCurrency(Currency.BRL)
    								.withExtraAmount(new BigDecimal(0))
    								.addItem(new PaymentItemBuilder().withId((dadosPayment.getItems()[0].getId()))
    										.withDescription(dadosPayment.getItems()[0].getDescription())
    										.withAmount(new BigDecimal(dadosPayment.getItems()[0].getAmount()))
    										.withQuantity(1))
    								.withNotificationURL("localhost:8080/notification")
    								.withReference(dadosPayment.getReference())
    								.withSender(new SenderBuilder().withEmail(dadosPayment.getSender().getEmail())
    										.withName(dadosPayment.getSender().getName())
    										.withCPF(dadosPayment.getSender().getDocument().getValue())
    										.withHash(dadosPayment.getSender().getHash())
    										
    								.withPhone(new PhoneBuilder()
    										.withAreaCode(dadosPayment.getSender().getPhone().getAreaCode())
    										.withNumber(dadosPayment.getSender().getPhone().getNumber())))
    								.withShipping(new ShippingBuilder().withType(ShippingType.Type.USER_CHOISE)
    										.withCost(BigDecimal.ZERO)
    										.withAddress(new AddressBuilder()
    										.withPostalCode(dadosPayment.getBilling().getPostalCode())
    										.withCountry(dadosPayment.getBilling().getCountry())
    										.withState(dadosPayment.getBilling().getState())
    										.withCity(dadosPayment.getBilling().getCity())
    										.withComplement(dadosPayment.getBilling().getComplement())
    										.withDistrict(dadosPayment.getBilling().getDistrict())
    										.withNumber(dadosPayment.getBilling().getNumber())
    										.withStreet(dadosPayment.getBilling().getStreet())))
    										
    							    ).withCreditCard(new CreditCardBuilder()
    			                    .withBillingAddress(new AddressBuilder()
    			                    		.withPostalCode(dadosPayment.getBilling().getPostalCode())
    										.withCountry(dadosPayment.getBilling().getCountry())
    										.withState(dadosPayment.getBilling().getState())
    										.withCity(dadosPayment.getBilling().getCity())
    										.withComplement(dadosPayment.getBilling().getComplement())
    										.withDistrict(dadosPayment.getBilling().getDistrict())
    										.withNumber(dadosPayment.getBilling().getNumber())
    										.withStreet(dadosPayment.getBilling().getStreet())
    			                        )
    			                        .withInstallment(new InstallmentBuilder()
    			                        .withNoInterestInstallmentQuantity(dadosPayment.getCreditCard().getMaxInstallmentNoInterest())		
										.withQuantity(dadosPayment.getCreditCard().getInstallment().getQuantity())
										.withValue(new BigDecimal(dadosPayment.getCreditCard().getInstallment().getInstallmentAmount())))
    			                        
    			                        .withHolder(new HolderBuilder()
    			                            .addDocument(new DocumentBuilder()
    			                                .withType(dadosPayment.getCreditCard().getHolder().getDocument().getType())
    			                                .withValue(dadosPayment.getCreditCard().getHolder().getDocument().getValue())
    			                            
    			                            )
    			                            .withName(dadosPayment.getCreditCard().getHolder().getName())
    			                            .withBithDate(new SimpleDateFormat("dd/MM/yyyy").parse(dadosPayment.getCreditCard().getHolder().getBirthDate().toString()))
    			                            .withPhone(new PhoneBuilder()
    			                                .withAreaCode(dadosPayment.getCreditCard().getHolder().getPhone().getAreaCode())
    			                                .withNumber(dadosPayment.getCreditCard().getHolder().getPhone().getNumber())
    			                            )
    			                        )
    			                        .withToken(dadosPayment.getCreditCard().getToken())
    			                    );
    			                System.out.println(checkout);

    			            }catch (Exception e){
    			                e.printStackTrace();
    			            }
        	break;
        case "ONLINE_DEBIT":
        	try {
				final PagSeguro pagSeguro = PagSeguro.instance(new SimpleLoggerFactory(), new JSEHttpClient(),
						Credential.sellerCredential("edurock55@gmail.com", "086022FE2D2547C0847246E4C8C3804B"),
						PagSeguroEnv.SANDBOX);
				// Checkout transparente (pagamento direto) com boleto
				checkout = pagSeguro.transactions()
						.register(
								new DirectPaymentRegistrationBuilder().withPaymentMode("default").withCurrency(Currency.BRL)
									.withExtraAmount(new BigDecimal(0))
									.addItem(new PaymentItemBuilder().withId((dadosPayment.getItems()[0].getId()))
											.withDescription(dadosPayment.getItems()[0].getDescription())
											.withAmount(new BigDecimal(dadosPayment.getItems()[0].getAmount()))
											.withQuantity(1).withWeight(0))
									.withNotificationURL("localhost:8080/notification")
									.withReference(dadosPayment.getReference())
									.withSender(new SenderBuilder().withEmail(dadosPayment.getSender().getEmail())
											.withName(dadosPayment.getSender().getName())
											.withCPF(dadosPayment.getSender().getDocument().getValue())
											.withHash(dadosPayment.getSender().getHash())
											
									.withPhone(new PhoneBuilder()
											.withAreaCode(dadosPayment.getSender().getPhone().getAreaCode())
											.withNumber(dadosPayment.getSender().getPhone().getNumber())))
									.withShipping(new ShippingBuilder().withType(ShippingType.Type.USER_CHOISE)
											.withCost(BigDecimal.ZERO)
											.withAddress(new AddressBuilder()
											.withPostalCode(dadosPayment.getBilling().getPostalCode())
											.withCountry(dadosPayment.getBilling().getCountry())
											.withState(dadosPayment.getBilling().getState())
											.withCity(dadosPayment.getBilling().getCity())
											.withComplement(dadosPayment.getBilling().getComplement())
											.withDistrict(dadosPayment.getBilling().getDistrict())
											.withNumber(dadosPayment.getBilling().getNumber())
											.withStreet(dadosPayment.getBilling().getStreet())))
									
							).withOnlineDebit(new BankBuilder()
				                    .withName(dadosPayment.getBank().getName().getName())
					                );
				System.out.println(checkout.getPaymentLink());
			} catch (Exception e) {
				e.printStackTrace();
			}
        	break;
		}
		

		

		return ResponseEntity.status(HttpStatus.OK).body("{" + "\"statusCode\"" + " :" + 200 + "," + "\"status\"" + " :"
				+ "\"success\"" + "," + "\"content\"" + ":" + "" + data.toJson(checkout) + "}");

	}
}
