package com.trendingtech.repo;

import org.springframework.data.repository.CrudRepository;

import com.trendingtech.entity.Tickets;

public interface TicketsRepo extends CrudRepository<Tickets, Long>{

}