package codemasters.lambda.domain;

public class Person {
	
	private String firstName;
	private String lastName;
	private int age;
	private boolean male;
	
	public Person(String firstName, String lastName, int age, boolean male) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.male = male;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public int getAge() {
		return this.age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public boolean isMale() {
		return this.male;
	}
	
	public void setMale(boolean male) {
		this.male = male;
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + this.firstName.hashCode();
		result = 37 * result + this.lastName.hashCode();
		result = 37 * result + this.age;
		result = 37 * result + (this.male ? 1 : 0);
		return result;
	}
	
	public boolean equals(Object o) {
		if(this==o){
			return true;
		}
		if(o instanceof Person) {
			Person other = (Person) o;
			return (this.firstName == other.firstName) || (this.firstName != null && this.firstName.equals(other.firstName))
			 		&& (this.lastName == other.lastName) || (this.lastName != null && this.lastName.equals(other.lastName))
			 		&& (this.age == other.age)
			 		&& (this.male == other.male);
		}
		return false;
	}
	
	@Override
	public String toString(){
		return String.format("%s %s (%d)",
			this.firstName, 
			this.lastName,
			this.age);
	}
	
}
