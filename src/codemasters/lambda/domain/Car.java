package codemasters.lambda.domain;

public class Car {
	
	private String brand;
	private String model;
	private int year;
	private double originalValue;
	
	public Car(String brand, String model, int year, double originalValue) {
		this.brand = brand;
		this.model = model;
		this.year = year;
		this.originalValue = originalValue;
	}
	
	public String getBrand() {
		return this.brand;
	}
	
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public String getModel() {
		return this.model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	
	public int getYear() {
		return this.year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public double getOriginalValue() {
		return this.originalValue;
	}
	
	public void setOriginalValue(double originalValue) {
		this.originalValue = originalValue;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) {
			return true;
		}
		if(o instanceof Car){
			Car other = (Car) o;
			return (this.brand == other.brand || (this.brand != null && this.brand.equals(other.brand))) &&
				   (this.model == other.model || (this.model != null && this.model.equals(other.model))) &&
				   (this.year == other.year);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		result = result * 31 + this.brand.hashCode();
		result = result * 31 + this.model.hashCode();
		result = result * 31 + this.year;
		return result;
	}
	
	@Override
	public String toString() {
		return String.format("%s %s (%d)",
			this.brand,
			this.model,
			this.year);
	}
	
	public static int compareByYear(Car car1, Car car2) {
		return Integer.compare(car1.year, car2.year);
	}
	
	
}
