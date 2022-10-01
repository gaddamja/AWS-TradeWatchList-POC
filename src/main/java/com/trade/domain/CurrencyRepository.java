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
public interface CurrencyRepository extends CrudRepository<Currency, Integer> {
	
	List<Currency> findByFromSymbolAndToSymbol(String fromSymbol, String toSymbol);

}
