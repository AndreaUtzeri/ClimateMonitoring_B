package climatemonitoring;
import java.util.Date;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
Classe che contiene il metodo main dell'applicazione.
*/

public class ClimateMonitor {
	
	/** 
	Metodo main dell'applicazione. Definisce una semplice interfaccia che permette all'utente di selezionare l'operazione che desidera eseguire.
	*/
	
	public static void main(String[] args) throws IOException{
		
		boolean prova =UserRegistration.registerUser("andrea","utzeri");
		System.out.print(prova);
		

		
		
		/*
		boolean cond=true;
	    String id=null;
	    Scanner sc = new Scanner(System.in);
	    while(cond) {
	    System.out.println("\nEnter 8 to view climate parameters registered for an area");
	    System.out.println("Enter 7 to insert new parameters (operators only)");
	    System.out.println("Enter 6 to associate to a Monitoring Center (operators only)");
	    System.out.println("Enter 5 to register a Monitoring Center (operators only)");
	    System.out.println("Enter 4 to logout");
	    System.out.println("Enter 3 to login as an operator");
	    System.out.println("Enter 2 to register as an operator");
	    System.out.println("Enter 1 to research an Area");
	    System.out.println("Enter 0 to close the program");
	    System.out.print(">");
	    String tmp=sc.nextLine();
	      
	    switch(tmp){
	      case "1":
	        Metodi.cercaAreaGeografica();
	        break;
	      case "0":
	        cond=false;
	        break;
	      case "2":
	        id=Metodi.registrazione();
	        break;
	      case "3":
	        id=Metodi.login();
	        break;
	      case "4":
	        if (id != null){
	          id = null;
	          System.out.print("logout effettuato");
	          break;
	        }
	        else {
	          System.out.println("Non sei loggato");
	          break;
	        }
	          
	      case "5":
	        if (id != null){
	          Metodi.registraCentroAree();
	          break;
	        }
	        else{
	          System.out.println("Non hai effettuato il login");
	          break;
	        }
	      case "6":
	        if(id != null){
	          Metodi.associaCentro(id);
	          break;
	        }
	        else{
	          System.out.println("Non hai effettuato il login");
	          break;
	        }
	      case "7":
	    	  if(id !=null) {
	    		  Metodi.inserisciParametriClimatici(id);
	    		  break;
	    	  }
	    	  else {
	    		  System.out.print("Non hai effettuato il login");
	    		  break;
	    	  }
	      case "8":
	    	  Metodi.visualizzaAreaGeografica();
	    	  break;
	      default:
	        System.out.println("comando non valido");
	    
	      }
	    }
	    System.out.println("execution finished");
	    			
	  */
		
		}  

	}