package com.trade.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trade.domain.Currency;

@lombok.Data
public class Root {
	
	@JsonProperty("Currency") 
    public List<Currency> currency;


	@Override
	public String toString() {
		return "Root [currency=" + currency + "]";
	}

	
	
	
	
}
