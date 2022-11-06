package com.trade.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Data
public class AllStocks {

	@JsonProperty("Stocks") 
    public List<Stock> stock;

	@Override
	public String toString() {
		return "AllStocks [stock=" + stock + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
	


}
