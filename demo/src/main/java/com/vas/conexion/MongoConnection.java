package com.vas.conexion;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoConnection {
    
    private static final String URI = "mongodb://localhost:27017";
    private static final String DB_NAME = "VASolutionsDB";

    public static MongoDatabase getDatabase() {
        MongoClient client = MongoClients.create(URI);
        return client.getDatabase(DB_NAME);
    }
}
