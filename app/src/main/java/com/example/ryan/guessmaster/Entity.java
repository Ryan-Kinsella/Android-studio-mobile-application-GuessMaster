package com.example.ryan.guessmaster;
public abstract class Entity {
	private String name;
	private Date born;
	private double difficulty;
	public Entity(String name, Date birthDate) {
		this.name = name;
		this.born = new Date(birthDate);
	}
	public Entity(String name, Date birthDate, double difficulty) {
		this(name, birthDate);
		this.difficulty = difficulty;
	}
	public Entity(Entity entity) {
		this.name = entity.name;
		this.born = new Date(entity.born);
		this.difficulty = entity.difficulty;
	}
	public String getName() {
		return name;
	}
	public Date getBorn() {
		return new Date(born);
	}
	public Date getBornRange() {
		return new Date(born);
	}
	public String welcomeMessage() {
		return "Welcome! Let's start the game! "+entityType();
	}
	public String closingMessage() {
		return "Congratudations! The detailed information of "
				+ "the entity you guessed is:\n"+toString();
	}
	public abstract String entityType();
	public abstract Entity clone();
	public String toString() {
		return "Name: "+name+"\n"+"Born at: "+born.toString()+"\n";
	}
	public int getAwardedTicketNumber() {
		return (int)Math.round(difficulty*100);
	}
}