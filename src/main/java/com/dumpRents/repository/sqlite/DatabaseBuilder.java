package com.dumpRents.repository.sqlite;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseBuilder {

    public void buildDatabaseIfMissing() {
        if (isDatabaseMissing()) {
            System.out.println("Database is Missing. \nBuilding database...\n");
            buildTables();
        }
    }
    private boolean isDatabaseMissing(){
        return !Files.exists(Paths.get("dumpRents.db"));
    }

    private void buildTables(){
        try(Statement statement = ConnectionFactory.createStatement()) {
            statement.addBatch(createTableRubbleDumpster());
            statement.addBatch(createTableClient());
            statement.addBatch(createTableRental());
            statement.executeBatch();

            System.out.println("Database successfully created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private String createTableRubbleDumpster() {
        String SQL = """
                  CREATE TABLE RubbleDumpster (
                  ID INTEGER PRIMARY KEY AUTOINCREMENT,
                  serialNumber INTEGER NOT NULL,
                  minAmount INTEGER NOT NULL,
                  monthlyAmount INTEGER NOT NULL,
                  rubbleDumpsterStatus TEXT NOT NULL
                  );
                """;
        return SQL;
    }
    private String createTableClient(){
            String SQL = """
                CREATE TABLE Client (
                    ID INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    cpf TEXT NOT NULL,
                    phone1 TEXT NOT NULL,
                    phone2 TEXT,
                    email TEXT NOT NULL,
                    street TEXT NOT NULL,
                    district TEXT NOT NULL,
                    number TEXT NOT NULL,
                    city TEXT NOT NULL,
                    cep TEXT NOT NULL
                );
        """;
            return SQL;
        }
    private String createTableRental(){
        String SQL =  """
                CREATE TABLE Rental (
                   ID INTEGER PRIMARY KEY AUTOINCREMENT,
                   initialDate TEXT NOT NULL,
                   withdrawalRequestDate TEXT,
                   withdrawalDate TEXT,
                   endDate TEXT,
                   finalAmount INTEGER,
                   rentalStatus TEXT NOT NULL,
                   street TEXT NOT NULL,
                   district TEXT NOT NULL,
                   number TEXT NOT NULL,
                   city TEXT NOT NULL,
                   cep TEXT NOT NULL,
                   id_Client INTEGER NOT NULL,
                   id_RubbleDumpster INTEGER NOT NULL,
                   FOREIGN KEY(id_Client) REFERENCES Client(ID),
                   FOREIGN KEY(id_RubbleDumpster) REFERENCES RubbleDumpster(ID)
                  );
        """;
        return SQL;
    }

}
