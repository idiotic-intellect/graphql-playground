package com.learn.graphql_playground.lec16.clientapp.service;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.learn.graphql_playground.lec16.clientapp.client.CustomerClient;
import com.learn.graphql_playground.lec16.clientapp.client.CustomerSubscriptionClient;
import com.learn.graphql_playground.lec16.dto.CustomerDto;

import reactor.core.publisher.Mono;

@Service
public class ClientDemo implements CommandLineRunner{

	@Autowired
	private CustomerClient customerClient;
	
	@Autowired
	private CustomerSubscriptionClient subsClient;
	
	@Override
	public void run(String... args) throws Exception {
//		rawQueryExec().then(this.getCustomerById()).subscribe();
//		getAllCustomers().then(this.getCustomerById(3)).subscribe();
		
		this.subsClient.customerEvents()
		.doOnNext(eve -> System.out.println("**** logging " + eve.toString() + " ****")).subscribe();
		
		
		getAllCustomers()
		.then(createNewCustomer())
		.then(getAllCustomers())
		.then(updateExistingCustomer())
		.then(getAllCustomers())
		.then(deleteCustomerById(4))
		.then(getAllCustomers())
		.subscribe();
	}
	
	private Mono<Void> rawQueryExec() {
		String query = """
				{
					allCustomers: customers {
						id
						name
						age
						city
					}
				}
				
				""";
		Mono<List<CustomerDto>> document =  this.customerClient.rawQuery(query)
		.map(cust -> cust.field("allCustomers").toEntityList(CustomerDto.class));
		
		return this.executor("Raw Query Exec Starting", "Raw Query Exec Completed", document);
	}
	
	private Mono<Void> getCustomerById() {
		return this.executor("File Query Exec Starting", "File Query Exec Completed", this.customerClient.getCustomerByIdWithUnion(13));
	}
	
	private Mono<Void> getAllCustomers() {
		return this.executor("GetAll Starts", "GetAll Ends", this.customerClient.getAllCustomers());
	}
	
	private Mono<Void> getCustomerById(Integer id) {
		return this.executor("GetCustomerById Starts", "GetCustomerById Ends", this.customerClient.getCustomerById(id));
	}
	
	private Mono<Void> createNewCustomer() {
		return this.executor("CreateCustomer Starts", "CreateCustomer Ends", 
				this.customerClient.createNewCustomer(
						CustomerDto.create(null, "Jack", 35, "Ohio")
						));
	}
	
	private Mono<Void> updateExistingCustomer() {
		return this.executor("UpdateCustomer Starts", "UpdateCustomer Ends", 
				this.customerClient.updateExistingCustomer(1,
						CustomerDto.create(null, "Martin", 15, "Berlin")
						));
	}
	
	private Mono<Void> deleteCustomerById(Integer id) {
		return this.executor("DeleteCustomer Starts", "DeleteCustomer Ends", this.customerClient.deleteCustomerById(id));
	}
	
	private <T> Mono<Void> executor(String sMsg, String eMsg, Mono<T> document) {
		return Mono.delay(Duration.ofSeconds(1))
				.doFirst(() -> System.out.println(sMsg))
				.then(document)
				.doOnNext(System.out::println).then()
				.doAfterTerminate(() -> System.out.println(eMsg));
	}
	
//	private Mono<Void> getCustomerEvents() {
//		return this.subsClient.customerEvents()
//				.doOnNext(eve -> System.out.println("**** logging " + eve.toString() + " ****"))
//				.then();
//	}
	
	

}
