package repository;

import model.entities.Client;
import model.entities.valueObjects.Cpf;
import persistence.dao.ClientDAO;
import java.util.*;

public class InMemoryClientDAO implements ClientDAO {

    private static final Map<Integer, Client> db = new LinkedHashMap<>();
    private static int idCounter;

    @Override
    public Integer create(Client client) {
        idCounter++;
        client.setId(idCounter);
        db.put(idCounter, client);
        return idCounter;
    }

    @Override
    public Optional<Client> findOne(Integer key) {
        if(db.containsKey(key)) {
            return Optional.of(db.get(key));
        }
        return Optional.empty();
    }

    @Override
    public List<Client> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public boolean update(Client client) {
        if (db.containsKey(client.getId())) {
            db.replace(client.getId(), client);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteByKey(Integer key) {
        if (db.containsKey(key)) {
            db.remove(key);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Client client) {
        return deleteByKey(client.getId());
    }

    @Override
    public Optional<Client> findByCpf(Cpf cpf) {
        return Optional.empty();
    }

    @Override
    public Optional<Client> findByName(String name) {
        return Optional.empty();
    }
}
