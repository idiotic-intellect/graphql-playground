package com.learn.graphql_playground.lec16.serverapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learn.graphql_playground.lec16.dto.Action;
import com.learn.graphql_playground.lec16.dto.CustomerDto;
import com.learn.graphql_playground.lec16.dto.CustomerEvent;
import com.learn.graphql_playground.lec16.dto.DeleteResponseDto;
import com.learn.graphql_playground.lec16.dto.Status;
import com.learn.graphql_playground.lec16.serverapp.repository.CustomerRepository;
import com.learn.graphql_playground.lec16.serverapp.util.EntityDtoUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private CustomerEventService eventService;
	
	public Flux<CustomerDto> getAllCustomers() {
		return this.customerRepo.findAll()
				.map(EntityDtoUtil::toDto);
	}
	
	public Mono<CustomerDto> getCustomerById(Integer id) {
		return  this.customerRepo.findById(id)
				.map(EntityDtoUtil::toDto);
	}

	public Mono<CustomerDto> createNewCustomer(CustomerDto customerDto) {
		return Mono.just(customerDto)
				.filter(cu -> cu.getId()==null)
				.map(EntityDtoUtil::toEntity)
				.flatMap(this.customerRepo::save)
				.map(EntityDtoUtil::toDto)
				.doOnNext(customer -> this.eventService.emitCustomerEvents(new CustomerEvent().create(customer.getId(), Action.CREATED)));
	}
	
	public Mono<CustomerDto> updateExistingCustomer(Integer id, CustomerDto customerDto) {
		return this.customerRepo.findById(id)
				.map(cu -> EntityDtoUtil.toEntity(id, customerDto))
				.flatMap(this.customerRepo::save)
				.map(EntityDtoUtil::toDto)
				.doOnNext(customer -> this.eventService.emitCustomerEvents(new CustomerEvent().create(id, Action.UPDATED)));
	}
	
	public Mono<DeleteResponseDto> deleteExistingCustomer(Integer id) {
		return this.customerRepo.findById(id)
				.flatMap(cu -> this.customerRepo.deleteById(id)
						.doOnSuccess(customer -> this.eventService.emitCustomerEvents(new CustomerEvent().create(id, Action.DELETED)))
						.thenReturn(DeleteResponseDto.create(id, Status.SUCCESS))
						.onErrorReturn(DeleteResponseDto.create(id,Status.FAILURE))
				);
	}
}
