package com.coffee.domian;

import java.io.Serializable;

public class UserChoiceCostDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Double sumCost;
	private Double shipping;
	private Double totalCost;

	public UserChoiceCostDto() {
		super();
	}

	public Double getSumCost() {
		return sumCost;
	}

	public void setSumCost(Double sumCost) {
		this.sumCost = sumCost;
	}

	public Double getShipping() {
		return shipping;
	}

	public void setShipping(Double transport) {
		this.shipping = transport;
	}

	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sumCost == null) ? 0 : sumCost.hashCode());
		result = prime * result + ((totalCost == null) ? 0 : totalCost.hashCode());
		result = prime * result + ((shipping == null) ? 0 : shipping.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserChoiceCostDto other = (UserChoiceCostDto) obj;
		if (sumCost == null) {
			if (other.sumCost != null)
				return false;
		} else if (!sumCost.equals(other.sumCost))
			return false;
		if (totalCost == null) {
			if (other.totalCost != null)
				return false;
		} else if (!totalCost.equals(other.totalCost))
			return false;
		if (shipping == null) {
			if (other.shipping != null)
				return false;
		} else if (!shipping.equals(other.shipping))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserChoiceCostDto [sumCost=" + sumCost + ", shipping=" + shipping + ", totalCost=" + totalCost + "]";
	}

}
