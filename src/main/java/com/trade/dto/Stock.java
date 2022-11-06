package com.trade.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Data
public class Stock {
	
	@JsonProperty("Index") 
    public String index;
    @JsonProperty("Last") 
    public String last;
    @JsonProperty("WeightedAverage") 
    public String vwa;
	
	@Override
	public String toString() {
		return "Stock [index=" + index + ", last=" + last + ", vwa=" + vwa + ", getIndex()=" + getIndex()
				+ ", getLast()=" + getLast() + ", getVwa()=" + getVwa() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}

	
	

}
