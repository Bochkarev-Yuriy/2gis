package ru.two.gis.web.model;

public class Filial {

	private String name;

	private String address;

	private Double rating;

	public Filial() {
	}

	public Filial(String name, String address, Double rating) {
		this.name = name;
		this.address = address;
		this.rating = rating;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Filial filial = (Filial) o;

		if (address != null ? !address.equals(filial.address) : filial.address != null) return false;
		if (name != null ? !name.equals(filial.name) : filial.name != null) return false;
		return rating != null ? !rating.equals(filial.rating) : filial.rating != null;
	}

	@Override
	public int hashCode() {
		int result = 0;
		result = 31 * result + (address != null ? address.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (rating != null ? rating.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Filial{" +
				"name='" + name + '\'' +
				", address='" + address + '\'' +
				", rating=" + rating +
				'}';
	}
}
