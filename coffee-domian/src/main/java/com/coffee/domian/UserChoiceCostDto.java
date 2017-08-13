package com.coffee.domian;

import java.io.Serializable;

public class UserChoiceCostDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Double sumCost;
	private Double transport;
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

	public Double getTransport() {
		return transport;
	}

	public void setTransport(Double transport) {
		this.transport = transport;
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
		result = prime * result + ((transport == null) ? 0 : transport.hashCode());
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
		if (transport == null) {
			if (other.transport != null)
				return false;
		} else if (!transport.equals(other.transport))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserChoiceCostDto [sumCost=" + sumCost + ", transport=" + transport + ", totalCost=" + totalCost + "]";
	}

}
