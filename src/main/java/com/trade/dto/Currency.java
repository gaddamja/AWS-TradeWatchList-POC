package com.trade.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@lombok.Data
@JsonPropertyOrder
public class Currency {
	

    @JsonProperty("Index") 
    public String index;
    @JsonProperty("Last") 
    public String last;
    @JsonProperty("PriceDate") 
    public String priceDate;
	
	@Override
	public String toString() {
		return "Currency [Index=" + index + ", last=" + last + ", priceDate=" + priceDate + "]";
	}
	
	

}
