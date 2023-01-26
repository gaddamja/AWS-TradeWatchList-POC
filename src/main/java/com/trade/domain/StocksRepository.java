/**
 * 
 */
package com.trade.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

/**
 * @author nimishashah
 *
 */
public interface StocksRepository extends CrudRepository<Stocks, Integer> {
	
	List<Stocks> findByIndex(String index);
	
	List<Stocks> findAll();

}
