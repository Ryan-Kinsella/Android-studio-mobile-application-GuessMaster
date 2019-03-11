package com.example.ryan.guessmaster;

import android.os.Bundle;
import android.view.View;
import android.content.DialogInterface;
import android.widget.Toast;
import java.util.Random;
import java.util.Scanner;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.*;

public class GuessMaster extends AppCompatActivity {
	private int numOfEntities;
	private Entity[] entities; 
	private int totalTicketNum = 0;
	private int[] tickets;
	private String answer;
	private Entity currentEntity;
	String entName;
	int entityid = 0;
	int currentTicketWon = 0;
	//the following are variables used in the xml file:
	private TextView entityName;
	private TextView ticketsum;
	private Button guessButton;
	private EditText userIn;
	private Button btnclearContent;
	private ImageView entityImage;

	//blank constructor
	public GuessMaster() {
		numOfEntities = 0;
		entities = new Entity[10]; 
	}

	//used in onCreate
	public void addEntity(Entity entity) {
		entities[numOfEntities++] = entity.clone();
	}

	//unused
	public void playGame() {
		int entityId = genRandomEntityId();
		playGame(entityId);
	}

	//randomdly generates an entity id to select from
	public int genRandomEntityId() {
		Random randomNumber = new Random();
		return randomNumber.nextInt(numOfEntities);
	}


	public void playGame(int entityId) {
		Entity entity = entities[entityId];
		//updates the current entity and name
		currentEntity=entity;
		entName = entity.getName();
		//updates the image and name of entity
		entityName.setText(entity.getName());
		ImageSetter(entity);
	}

	//Game played within onCreate, takes in entity
	public void playGame(Entity entity) {
		//sets the entity name
		entityName.setText(entity.getName());
		//sets the current tickets
		ticketsum.setText(Integer.toString(totalTicketNum));
		//redundant for first round but sets the image
		ImageSetter(entity);

		//user input
		answer = userIn.getText().toString();
		answer = answer.replace("\n", "").replace("\r", "");

		AlertDialog.Builder alert = new AlertDialog.Builder(GuessMaster.this);

		//display text
		alert.setTitle("Guess!");
		alert.setMessage("Guess the following birthday: "+entity.getName()+" Use the format (mm/dd/yyyy)");
		alert.setCancelable(false);
		alert.setNegativeButton("Continue?", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int which) {
				Toast.makeText(getBaseContext(), " ", Toast.LENGTH_SHORT).show();
			}
		});
		//Show dialog
		AlertDialog dialog = alert.create();
		dialog.show();

		//type quit to exit
		if (answer.equals("quit")) {
			System.exit(0);
		}
		Date date = new Date(answer);
		//if user guess is less than date
		if (date.precedes(entity.getBorn())) {
			//for displaying text
			alert.setTitle("Incorrect");
			alert.setMessage("Try a later date");
			alert.setCancelable(false);
			alert.setNegativeButton("Continue?", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int which) {
					Toast.makeText(getBaseContext(), " ", Toast.LENGTH_SHORT).show();
				}
			});
			//Show dialog
			AlertDialog dialog1 = alert.create();
			dialog1.show();
		}
		//if user guess is greater than date
		else if (entity.getBorn().precedes(date)) {
			alert.setTitle("Incorrect");
			alert.setMessage("Try an earlier date");
			alert.setCancelable(false);
			alert.setNegativeButton("Continue?", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int which) {
					Toast.makeText(getBaseContext(), " ", Toast.LENGTH_SHORT).show();
				}
			});
			//Show dialog
			AlertDialog dialog2 = alert.create();
			dialog2.show();
		}
		else {
			//adds to the total number of tickets
			tickets = new int [100];
			tickets[entityid++]=entity.getAwardedTicketNumber();
			for (int i=0;i<100;i++){
				totalTicketNum=totalTicketNum+ tickets[i];
			}
			//sets and displays the current tickets
			ticketsum.setText(Integer.toString(totalTicketNum));
			//show how many tickets the user gained this round
			currentTicketWon=entity.getAwardedTicketNumber();
			alert.setTitle("BINGO!");
			alert.setMessage("Nice! You won! "+entity.closingMessage());
			alert.setCancelable(false);
			alert.setNegativeButton("Continue?", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int which) {
					Toast.makeText(getBaseContext(), "Tickets earned this round: "+currentTicketWon, Toast.LENGTH_SHORT).show();
					//continue game here is required for the program to choose another entity
					ContinueGame();
				}
			});
			//Show dialog
			AlertDialog dialog3 = alert.create();
			dialog3.show();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//Create Entities to use in game, and adds them
		Politician jTrudeau = new Politician("Justin Trudeau", new Date("December", 25, 1971), "Male", "Liberal", 0.25);////
		Singer cDion = new Singer("Celine Dion", new Date("March", 30, 1961), "Female", "La voix du bon Dieu", new Date("November", 6, 1981), 0.5);////
		Person myCreator = new Person("My Creator", new Date("September", 1, 2000),"Female", 1);////
		Country usa = new Country("United States", new Date("July", 4, 1776), "Washinton D.C.", 0.1);
		addEntity(jTrudeau);
		addEntity(cDion);
		addEntity(myCreator);
		addEntity(usa);

		super.onCreate(savedInstanceState);
		//Specifying various views
		setContentView(R.layout.activity_guess_activity);
		guessButton = (Button) findViewById(R.id.btnGuess);
		userIn = (EditText) findViewById(R.id.guessinput);
		//TextView for total tickets
		ticketsum = (TextView) findViewById(R.id.ticket);
		//ImageView for entity
		entityImage=(ImageView)findViewById(R.id.entityImage);
		//TextView for entity name
		entityName=(TextView)findViewById(R.id.entityName);
		//button used for clear button method
		btnclearContent=(Button)findViewById(R.id.btnClear);
		//clear button method

		//create entity within onCreate and update current entity
		entityid=genRandomEntityId();
		Entity entity = entities[entityid];
		currentEntity=entity;
		entityName.setText(entity.getName());

		//set image from entity
		ImageSetter(entity);
		//perform a welcome message
		welcomeToGame(entity);
		//create the clear button
		btnclearContent.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				changeEntity();
				}
		});
		//create the guess button
		guessButton.setOnClickListener(new View.OnClickListener(){
		@Override
		public void onClick (View v){
			playGame(currentEntity);
		}
        });

	}

	//Method: clears entity and continues game
	void changeEntity(){
		//clears user entries from user unput
		userIn.getText().clear();
		//randomly chooses another entity?
		ContinueGame();
	}

	//Method: Sets images from drawable for each entity
	void ImageSetter(Entity entity){
		if (entity.getName().equals("Celine Dion")){
			entityImage.setImageResource(R.drawable.celidion);
		}
		else if (entity.getName().equals("Justin Trudeau")){
			entityImage.setImageResource(R.drawable.justint);
		}
		else if (entity.getName().equals("United States")){
			entityImage.setImageResource(R.drawable.usaflag);
		}
		else { //else statement instead of else if, to prevent run time errors
			entityImage.setImageResource(R.drawable.mycreator);
		}
	}

	//Method: welcomes user once to the game
	void welcomeToGame(Entity entity){
		//create a welcomeAlert instance to be displayed
		AlertDialog.Builder welcomeAlert = new AlertDialog.Builder(GuessMaster.this);
		welcomeAlert.setTitle("GuessMaster Game Version 3");
		welcomeAlert.setMessage(entity.welcomeMessage());
		welcomeAlert.setCancelable(false);
		welcomeAlert.setNegativeButton("START GAME", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int which) {
				Toast.makeText(getBaseContext(), "Game is Starting... Enjoy", Toast.LENGTH_SHORT).show();
			}
		});
		//Shows the dialog
		AlertDialog dialog = welcomeAlert.create();
		dialog.show();
	}

	public void ContinueGame(){
		//the following 3 lines updates current entity
		entityid = genRandomEntityId();
		Entity entity = entities[entityid];
		currentEntity=entity;
		//sets the entity name
		entName = entity.getName();
		entityName.setText(entName);
		//sets the image
		ImageSetter(entity);
		//clears the user input
		userIn.getText().clear();
	}
}
