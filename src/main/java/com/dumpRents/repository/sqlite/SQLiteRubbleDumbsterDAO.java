package com.dumpRents.repository.sqlite;

import com.dumpRents.model.entities.RubbleDumpster;
import com.dumpRents.model.entities.RubbleDumpsterStatus;
import com.dumpRents.persistence.dao.RubbleDumpsterDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SQLiteRubbleDumbsterDAO implements RubbleDumpsterDAO {

    @Override
    public List<RubbleDumpster> findAll(RubbleDumpsterStatus status) {
            String rubbleDumpsterStatus = status.toString();
            String sql = "SELECT * from RubbleDumpster where status=?";
            List<RubbleDumpster> rubbleDumpsters= new ArrayList<>();

            try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
                stmt.setString(1, rubbleDumpsterStatus);
                ResultSet rs = stmt.executeQuery();
                while(rs.next()) {
                    RubbleDumpster rubbleDumpster = resultSetToEntity(rs);
                    rubbleDumpsters.add(rubbleDumpster);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return rubbleDumpsters;
    }

    @Override
    public Optional<RubbleDumpster> findById(Integer id) {
        String sql = "SELECT * from RubbleDumpster where id = ?";
        RubbleDumpster rubbleDumpster= null;

        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                rubbleDumpster = resultSetToEntity(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(rubbleDumpster);
    }

    public Optional<RubbleDumpster> findOneBySerialNumber(int serialNumber) {
        String sql = "SELECT * from RubbleDumpster where serialNumber = ?";
        RubbleDumpster rubbleDumpster= null;

        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1,serialNumber);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                rubbleDumpster = resultSetToEntity(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(rubbleDumpster);
    }

    private RubbleDumpster resultSetToEntity(ResultSet rs) throws SQLException {

        double minAmount = (double) rs.getInt("minAmount");
        double monthlyAmount = (double) rs.getInt("monthlyAmount");

        return new RubbleDumpster(
                rs.getInt("serialNumber"),
                monthlyAmount,
                minAmount,
                RubbleDumpsterStatus.toEnum(rs.getString("rubbleDumpsterStatus")),
                rs.getInt("ID"));

    }
    @Override
    public Integer create(RubbleDumpster rubbleDumpster) {
        String sql = "INSERT INTO RubbleDumpster (serialNumber, minAmount,MonthlyAmount,rubbleDumpsterStatus) VALUES (?,?,?,?)";


        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1,rubbleDumpster.getSerialNumber());
            stmt.setDouble(2, rubbleDumpster.getMinAmount());
            stmt.setDouble(3, rubbleDumpster.getMonthlyAmount());
            stmt.setString(4, rubbleDumpster.getStatus().toString());
            stmt.execute();

            ResultSet rs = stmt.getGeneratedKeys();
            int generatedKey = rs.getInt(1);
            return generatedKey;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<RubbleDumpster> findOne(Integer key) {
        String sql = "SELECT * from RubbleDumpster where id = ?";
        RubbleDumpster rubbleDumpster= null;

        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1,key);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                rubbleDumpster = resultSetToEntity(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(rubbleDumpster);
    }

    @Override
    public List<RubbleDumpster> findAll() {
        String sql = "SELECT * from RubbleDumpster";
        List<RubbleDumpster> rubbleDumpsters= new ArrayList<>();
        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                RubbleDumpster rubbleDumpster = resultSetToEntity(rs);
                rubbleDumpsters.add(rubbleDumpster);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rubbleDumpsters;
    }

    @Override
    public boolean update(RubbleDumpster rubbleDumpster) {
        String sql = "Update RubbleDumpster set serialNumber = ? , minAmountm = ?, "+
                "MonthlyAmount = ? ,rubbleDumpsterStatus = ? where id = ?";


        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1,rubbleDumpster.getSerialNumber());
            stmt.setDouble(2, rubbleDumpster.getMinAmount());
            stmt.setDouble(3, rubbleDumpster.getMonthlyAmount());
            stmt.setString(4, rubbleDumpster.getStatus().toString());
            stmt.setInt(5, rubbleDumpster.getId());
            stmt.execute();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteByKey(Integer key) {
        String sql = "DELETE FROM RubbleDumpster WHERE id = ?";
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
    public boolean delete(RubbleDumpster rubbleDumpster) {
        if(rubbleDumpster == null || rubbleDumpster.getId() == null)
            throw new IllegalArgumentException("Rubble dumpster and your id must be not null");
        return deleteByKey(rubbleDumpster.getId());
    }
}
