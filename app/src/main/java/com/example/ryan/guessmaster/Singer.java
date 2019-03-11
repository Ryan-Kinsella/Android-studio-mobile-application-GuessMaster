package com.example.ryan.guessmaster;

public class Singer extends Person{
	private String debutAlbum;
	private Date debugAlbumReleaseDate;

	public Singer(String name, 
			Date birthDate, 
			String gender, 
			String debutAlbum, 
			Date debugAlbumReleaseDate,
			double difficulty) {
		super(name, birthDate, gender, difficulty);
		this.debutAlbum = debutAlbum;
		this.debugAlbumReleaseDate = new Date(debugAlbumReleaseDate);
	}
	
	public Singer(Singer singer) {
		super(singer);
		this.debutAlbum = singer.debutAlbum;
		this.debugAlbumReleaseDate = new Date(singer.debugAlbumReleaseDate);
	}
	
	public String entityType() {
		return "This entity is a Singer!";
	}
	
	public String toString() {
		return super.toString() +"Debut Album: " + debutAlbum + "\n" 
	      + "Release Date: " + debugAlbumReleaseDate.toString() + "\n";
	}
	
	public Singer clone() {
		return new Singer(this);
	}
}

