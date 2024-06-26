package com.dumpRents.repository.sqlite;

import com.dumpRents.model.entities.Client;
import com.dumpRents.model.entities.Rental;
import com.dumpRents.model.entities.RentalStatus;
import com.dumpRents.model.entities.RubbleDumpster;
import com.dumpRents.model.entities.valueObjects.Address;
import com.dumpRents.model.entities.valueObjects.Cep;
import com.dumpRents.persistence.dao.RentalDAO;

import javax.swing.*;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class  SQLiteRentalDAO implements RentalDAO {
    @Override
    public List<Rental> findRentalByPeriod(LocalDate initialDate, LocalDate endDate) {
        String sql = "SELECT * from Rental where initialDate >= ? and endDate <= ?";
        List<Rental> rentals= new ArrayList<>();
        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1,initialDate.toString());
            stmt.setString(2,endDate.toString());
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Rental rental = resultSetToRental(rs);
                rentals.add(rental);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentals;
    }

    @Override
    public List<Rental> findRentalByClient(Client client) {
        String sql = "SELECT * from Rental where id_Client = ?";
        List<Rental> rentals= new ArrayList<>();
        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1,client.getId());
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Rental rental = resultSetToRental(rs);
                rentals.add(rental);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentals;
    }

    @Override
    public List<Rental> findRentalByRubbleDumpster(RubbleDumpster rubbleDumpster) {
        String sql = "SELECT * from Rental where id_RubbleDumpster = ?";
        List<Rental> rentals= new ArrayList<>();
        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1,rubbleDumpster.getId());
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Rental rental = resultSetToRental(rs);
                rentals.add(rental);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentals;
    }

    @Override
    public List<Rental> findRentalByStatus(RentalStatus status) {

        String sql = "SELECT * from Rental where rentalStatus = ?";
        List<Rental> rentals= new ArrayList<>();
        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1,status.toString());
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Rental rental = resultSetToRental(rs);
                rentals.add(rental);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentals;
    }

    @Override
    public Integer create(Rental rental) {

        String sql = "INSERT INTO Rental(initialDate,rentalStatus,street,district,number,city,cep,id_Client,id_RubbleDumpster) VALUES (?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, rental.getInitialDate().toString());
            stmt.setString(2, rental.getRentalStatus().toString());
            stmt.setString(3, rental.getAddress().getStreet());
            stmt.setString(4, rental.getAddress().getDistrict());
            stmt.setString(5, rental.getAddress().getNumber());
            stmt.setString(6, rental.getAddress().getCity());
            stmt.setString(7, rental.getAddress().getCep().toString());
            stmt.setInt(8, rental.getClient().getId());
            stmt.setInt(9, rental.getRubbleDumpster().getId());
            stmt.execute();

            ResultSet rs = stmt.getGeneratedKeys();
            int generatedKey = rs.getInt(1);
            return generatedKey;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Rental resultSetToRental(ResultSet rs) throws SQLException {
        SQLiteRubbleDumbsterDAO dumbsterDAO = new SQLiteRubbleDumbsterDAO();
        SQLiteClientDAO clientDAO = new SQLiteClientDAO();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


        Optional<Client> client = clientDAO.findOne(rs.getInt("id_Client"));
        Optional<RubbleDumpster> rubbleDumpster = dumbsterDAO.findById(rs.getInt("id_RubbleDumpster"));

        String withdrawalRequestDate =  rs.getString("withdrawalRequestDate");
        String  withdrawalDate = rs.getString("withdrawalDate");
        String endDate = rs.getString("endDate");

        Rental rental = new Rental(rubbleDumpster.get(),client.get(),LocalDate.parse(rs.getString("initialDate")),
                new Address(rs.getString("street"),
                        rs.getString("district"),
                        rs.getString("number"),
                        rs.getString("city"),
                        new Cep(rs.getString("cep"))));
        rental.setId(rs.getInt("ID"));
        return rental;
    }


    @Override
    public Optional<Rental> findOne(Integer key) {
        String sql = "SELECT * from Rental where id = ?";
        Rental rental= null;
        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1,key);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                rental = resultSetToRental(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(rental);
    }

    @Override
    public List<Rental> findAll() {
        String sql = "SELECT * from Rental";
        List<Rental> rentals= new ArrayList<>();
        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Rental rental = resultSetToRental(rs);
                rentals.add(rental);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentals;
    }

    @Override
    public boolean update(Rental rental) {
    String sql = "UPDATE Rental set initialDate = ?, withdrawalRequestDate = ?, withdrawalDate = ?, endDate = ?" +
            "finalAmount = ?, rentalStatus = ?, street = ?, district = ?, number = ?, city = ?," +
            " cep = ?, id_Client = ?, id_RubbleDumpster = ? WHERE id = ?";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, rental.getInitialDate().toString());
            stmt.setString(2, rental.getWithdrawalRequestDate().toString());
            stmt.setString(3, rental.getWithdrawalDate().toString());
            stmt.setString(4, rental.getEndDate().toString());
            stmt.setDouble(5, rental.getFinalAmount());
            stmt.setString(6, rental.getRentalStatus().toString());
            stmt.setString(7, rental.getAddress().getStreet());
            stmt.setString(8, rental.getAddress().getDistrict());
            stmt.setString(9, rental.getAddress().getNumber());
            stmt.setString(10, rental.getAddress().getCity());
            stmt.setString(11, rental.getAddress().getCep().toString());
            stmt.setInt(12, rental.getClient().getId());
            stmt.setInt(13, rental.getRubbleDumpster().getId());
            stmt.execute();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteByKey(Integer key) {

        String sql = "DELETE FROM Rental WHERE id = ?";
        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1,key);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Rental rental) {
        if(rental == null || rental.getId() == null)
            throw new IllegalArgumentException("Rental and your id must be not null");
        return deleteByKey(rental.getId());
    }
}
