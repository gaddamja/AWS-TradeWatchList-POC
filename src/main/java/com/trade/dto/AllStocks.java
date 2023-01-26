package com.trade.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trade.domain.Stocks;

@lombok.Data
public class AllStocks {

	@JsonProperty("Stocks") 
    public List<Stocks> stocks;

	@Override
	public String toString() {
		return "AllStocks [stocks=" + stocks + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
	


}
