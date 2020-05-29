package com.nelioalves.cursomc.resources;

import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nelioalves.cursomc.components.CheckoutGenerico;
import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.domain.Endereco;
import com.nelioalves.cursomc.dto.PaymentDTO;
import com.nelioalves.cursomc.repositories.ClienteRepository;
import com.nelioalves.cursomc.repositories.EnderecoRepository;
import com.nelioalves.cursomc.resources.utils.UTILS;

import br.com.uol.pagseguro.domain.AccountCredentials;
import br.com.uol.pagseguro.domain.Address;
import br.com.uol.pagseguro.domain.Item;
import br.com.uol.pagseguro.domain.Phone;
import br.com.uol.pagseguro.domain.Sender;
import br.com.uol.pagseguro.domain.SenderDocument;
import br.com.uol.pagseguro.domain.Transaction;
import br.com.uol.pagseguro.domain.direct.checkout.BoletoCheckout;
import br.com.uol.pagseguro.enums.Currency;
import br.com.uol.pagseguro.enums.DocumentType;
import br.com.uol.pagseguro.enums.PaymentMode;
import br.com.uol.pagseguro.enums.ShippingType;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import br.com.uol.pagseguro.service.TransactionService;

@RestController

public class CheckoutPagSeguroResource {

	private static final Logger logger = Logger.getLogger(CheckoutPagSeguroResource.class.getName());

	// @Autowired
	// private CheckoutService service;
	private Cliente cliente = null;
	private Endereco endereco;
	private Gson gson = new GsonBuilder().create();
	private Transaction code = null;
	
	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> payment(@Valid @RequestBody String obj) throws Exception {

		Sender sender = new Sender();
		Phone phone = new Phone();
		SenderDocument document = new SenderDocument();
		Address address = new Address();
		Item item = new Item();

		logger.info("line - 1: " + obj);
		CheckoutGenerico dadosPayment =this.gson.fromJson(obj, CheckoutGenerico.class);
		logger.info("line - 2 " + dadosPayment);

		//CreditCardCheckout request = new CreditCardCheckout();
		
		
		BoletoCheckout request = new BoletoCheckout();
		
		
		request.setPaymentMode(PaymentMode.DEFAULT);
		request.setReceiverEmail("edurock55@gmail.com");
		request.setCurrency(Currency.BRL);
		request.setReference("REF1234");
		
		
	
		// dados do comprador

		//cliente = new Cliente();

		//Optional<Cliente> objCli = clienteRepository.findById(dadosPayment.getPedido().getCliente().getId());

		//cliente = objCli.get();
		sender.setName(dadosPayment.getSender().getName());
		sender.setEmail(dadosPayment.getSender().getEmail());
		
		sender.setHash("80659cd1c6b9cf800090a6db6099c5918bae8877cf50604ac06daa532380c131");
		
		phone.setAreaCode(dadosPayment.getSender().getPhone().getAreaCode()); // TODO tratar codigo de area
		phone.setFullPhone(dadosPayment.getSender().getPhone().getNumber()); // TODO tratar numero celular
		sender.setPhone(phone);
		document.setType(DocumentType.CPF);
		document.setValue(dadosPayment.getSender().getDocument().getValue());         // TODO  estava assim : document.setValue(cliente.getCpfOuCnpj());
		sender.addDocument(document);
		request.setSender(sender);

		// DADOS DO COMPRADOR
		//request.setHolder(new Holder("Dados Comprador", //
				//new Phone("11", "56273440"), //
				//new Document(DocumentType.CPF, "000.000.001-91"), //
				//"07/05/1981"));

		////request.setSender(sender);

		/* Endereço do comprador */

		//Optional<Endereco> objEnd = enderecoRepository.findById(dadosPayment.getPedido().getEnderecoDeEntrega().getId());
		endereco = new Endereco();
		//endereco = objEnd.get();
		address.setStreet(dadosPayment.getBilling().getStreet());
		address.setState(dadosPayment.getBilling().getState());
		address.setNumber(dadosPayment.getBilling().getNumber());
		address.setComplement(dadosPayment.getBilling().getNumber());
		address.setDistrict(dadosPayment.getBilling().getDistrict());
		address.setCity(dadosPayment.getBilling().getCity());
		address.setPostalCode(UTILS.formatCEP(dadosPayment.getBilling().getPostalCode()));
		
		address.setCountry(dadosPayment.getBilling().getCountry());
		request.setShippingType(ShippingType.NOT_SPECIFIED);
		request.setShippingAddress(address);
		
		

		/* Dados produto adquirido */

		
		/*
		 * for (ItemProdutoDTO i : dadosPayment.getItems()) {
		 * 
		 * item = new Item(); //item.setId(String.valueOf(i.getProduto().getId()));
		 * item.setDescription(i.getProduto().getNome());
		 * item.setAmount(UTILS.round(i.getProduto().getPreco()));
		 * item.setQuantity(i.getQuantidade()); request.addItem(item); }
		 */
		
		
		 item = new Item(); //item.setId(String.valueOf(i.getProduto().getId()));
		 item.setId("13");
		  item.setDescription("cursoA");
		  item.setAmount(UTILS.round(23.3));
		  item.setQuantity(1); 
		  request.addItem(item);
		
		//request.setCreditCardToken(dadosPayment.getToken());

		//request.setInstallment(new Installment(dadosPayment.getPedido().getPagamento().getNumeroDeParcelas(),
				//(UTILS.round(dadosPayment.getTotal()))));
		//request.setInstallment(new Installment(1,null));
		

		// DADOS DO COMPRADOR ENDEREÇO
		//.setBillingAddress(new Address("BRA", "SP", "Sao Paulo", "Jardim Paulistano", "01452002",
				//"Av. Brig. Faria Lima", "1384", "5º andar"));

		  //AccountCredentials accountCredentials = new AccountCredentials("edurock55@gmail.com", "8e08e5ed-4dc0-492f-8863-0eabcf17e6aa7047c386426c831059763e3bcca7cce911c9-b1b8-44b1-bfc5-b95057f7db9c","086022FE2D2547C0847246E4C8C3804B");
		  AccountCredentials accountCredentials = new AccountCredentials("edurock55@gmail.com","8e08e5ed-4dc0-492f-8863-0eabcf17e6aa7047c386426c831059763e3bcca7cce911c9-b1b8-44b1-bfc5-b95057f7db9c","086022FE2D2547C0847246E4C8C3804B");

		  ///final AccountCredentials accountCredentials = PagSeguroConfig.getAccountCredentials();

		  logger.info("Credenciais: " + accountCredentials);
		  logger.info("Request " + request);
		

		try {

			code = TransactionService.createTransaction(accountCredentials, request);
			
			if (code != null) {
				System.out.println("Transaction Code - Default Mode: " +code.getCode());

				// pedido = pedidoService.insert(this.pedido);
				// URI uri =
				// ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
			}
		} catch (PagSeguroServiceException e) {
			System.err.println(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(code));

	}

}
