package com.trade.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@lombok.Data
@JsonPropertyOrder
public class Stock {
	
	@JsonProperty("Index") 
    public String index;
	@JsonProperty("LastPrice") 
    public String lastPrice;
	@JsonProperty("Weighted_Avg") 
    public String weightedAvg;
	
	@JsonProperty("Time") 
    public String priceDate;

	@Override
	public String toString() {
		return "Stock [index=" + index + ", lastPrice=" + lastPrice + ", weightedAvg=" + weightedAvg + ", priceDate="
				+ priceDate + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	

}
