package com.trade.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@JsonPropertyOrder
public class Stocks {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="SERIALNUMBER", nullable = false, unique = true)
	public Integer serialNumber;
	
	@Column(name="TICKER", nullable = true)
	@JsonProperty("Index") 
    public String index;
	@Column(name="LAST_PRICE", nullable = true)
	@JsonProperty("LastPrice") 
    public String lastPrice;
	@Column(name="WT_AVRGE", nullable = true)
	@JsonProperty("Weighted_Avg") 
    public String weightedAvg;
	
	@Column(name="PRICEDATE", nullable = true)
	@JsonProperty("Time") 
    public String priceDate;
	
	public Integer getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getLastPrice() {
		return lastPrice;
	}
	public void setLastPrice(String lastPrice) {
		this.lastPrice = lastPrice;
	}
	public String getWeightedAvg() {
		return weightedAvg;
	}
	public void setWeightedAvg(String weightedAvg) {
		this.weightedAvg = weightedAvg;
	}
	public String getPriceDate() {
		return priceDate;
	}
	public void setPriceDate(String priceDate) {
		this.priceDate = priceDate;
	}
	@Override
	public String toString() {
		return "Stocks [serialNumber=" + serialNumber + ", index=" + index + ", lastPrice=" + lastPrice
				+ ", weightedAvg=" + weightedAvg + ", priceDate=" + priceDate + ", getSerialNumber()="
				+ getSerialNumber() + ", getIndex()=" + getIndex() + ", getLastPrice()=" + getLastPrice()
				+ ", getWeightedAvg()=" + getWeightedAvg() + ", getPriceDate()=" + getPriceDate() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
    



}
