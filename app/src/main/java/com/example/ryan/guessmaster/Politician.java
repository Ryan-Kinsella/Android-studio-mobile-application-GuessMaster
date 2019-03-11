package com.example.ryan.guessmaster;

public class Politician extends Person{
	private String party;

	public Politician(String name, 
			Date birthDate, 
			String gender, 
			String party,
			double difficulty) {
		super(name, birthDate, gender, difficulty);
		this.party = party;
	}
	
	public Politician(Politician politician) {
		super(politician);
		this.party = politician.party;
	}
	
	public String entityType() {
		return "This entity is a politician!";
	}

	public String toString() {
		return super.toString() + "Party: " + party + "\n";
	}
	
	public Politician clone() {
		return new Politician(this);
	}

}
