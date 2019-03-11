package com.example.ryan.guessmaster;

public class Country extends Entity{
	private String capital;
	
	public Country(String name, Date birthDate) {
		super(name, birthDate);
	}
	
	public Country(String name, 
			Date birthDate, 
			String capital, 
			double difficulty) {
		super(name, birthDate, difficulty);
		this.capital = capital;
	}
	
	public Country(Country country) {
		super(country);
		this.capital = country.capital;
	}
	
	public String entityType() {
		return "This entity is a country!";
	}
	
	public String toString() {
		return super.toString() + "Capital:" + capital +"\n";
	}
	
//	public String welcomeMessage() {
//		return "Welcom`````e! Let�s start the game! "+entityType();
//	}

//	public String toString() {
//		return aaa;
//	}
	
	public Country clone() {
		return new Country(this);
	}

}
