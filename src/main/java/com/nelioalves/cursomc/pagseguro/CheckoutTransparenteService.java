package com.nelioalves.cursomc.pagseguro;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nelioalves.cursomc.pagseguro.components.CheckoutGenerico;

import br.com.uol.pagseguro.api.PagSeguro;
import br.com.uol.pagseguro.api.common.domain.BankName;
import br.com.uol.pagseguro.api.common.domain.BankName.Name;
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
import br.com.uol.pagseguro.api.exception.PagSeguroBadRequestException;
import br.com.uol.pagseguro.api.transaction.register.DirectPaymentRegistrationBuilder;
import br.com.uol.pagseguro.api.transaction.search.TransactionDetail;

@Service
public class CheckoutTransparenteService {
	@Autowired
	PagSeguro pagSeguro = PagSeguro.instance();
	private TransactionDetail checkout = null;
	
	// Checkout transparente (pagamento direto) com boleto
	public TransactionDetail checkoutBoleto(CheckoutGenerico dadosPayment) {			
		try {	
		
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
		} catch (PagSeguroBadRequestException e) {
			e.printStackTrace();
		}			
		return checkout;
		
	}
	
	// Checkout transparente (pagamento direto) cartão de crédito
	public TransactionDetail checkoutCreditCard(CheckoutGenerico dadosPayment) throws ParseException {		
		try {
			
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
									.withStreet(dadosPayment.getBilling().getStreet()))))  									
										.withCreditCard(new CreditCardBuilder()
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
			                                .withValue(dadosPayment.getCreditCard().getHolder().getDocument().getValue()))
			                            	.withName(dadosPayment.getCreditCard().getHolder().getName())
			                            	.withBithDate(new SimpleDateFormat("dd/MM/yyyy").parse(dadosPayment.getCreditCard().getHolder().getBirthDate().toString()))
			                            	.withPhone(new PhoneBuilder()
			                                .withAreaCode(dadosPayment.getCreditCard().getHolder().getPhone().getAreaCode())
			                                .withNumber(dadosPayment.getCreditCard().getHolder().getPhone().getNumber())))
			                        .withToken(dadosPayment.getCreditCard().getToken()));						
				System.out.println(checkout);
		} catch (PagSeguroBadRequestException e) {
			e.printStackTrace();
		}
		return checkout;		
	}
	// Checkout transparente (pagamento direto) com boleto			
	public TransactionDetail checkoutDebitoOnline(CheckoutGenerico dadosPayment) throws ParseException {			
		try {
			 Name name = BankName.Name.fromName(dadosPayment.getBank().getName());
			if(dadosPayment.getBank().getName() .equals("BANCO_BRASIL")) {				
				  name =BankName.Name.BANCO_DO_BRASIL;
			}
			
			 
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
										//.withName(BankName.Name.fromName(dadosPayment.getBank().getName()))
										//.withName(BankName.Name.BANCO_DO_BRASIL)
										.withName(name)
							);
			
			
			System.out.println(checkout.getPaymentLink());
		} catch (PagSeguroBadRequestException e) {
			e.printStackTrace();
		}
		return checkout;
		
	}
}
