package com.vas.main;

import com.mongodb.client.MongoDatabase;
import com.vas.conexion.MongoConnection;


public class Main {

	public static void main(String[] args) {
		
		System.out.println("=== Sistema VAS - Demo NoSQL ===");

		// Conexión a MongoDB
        MongoDatabase db = MongoConnection.getDatabase();
		
   

        

       
	}

	
}
