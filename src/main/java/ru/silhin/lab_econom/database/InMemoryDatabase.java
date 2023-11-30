package ru.silhin.lab_econom.database;

import ru.silhin.lab_econom.model.ModelObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryDatabase implements Serializable {
    private final Map<UUID, ModelObject> database = new HashMap<>();

    public void save(ModelObject object) {
        this.database.put(object.getId(), object);
    }

    public void saveAll(List<ModelObject> objects) {
        for(ModelObject object : objects) {
            this.save(object);
        }
    }

    public <T> Optional<T> find(Class<T> clazz, UUID id) {
        ModelObject object = database.get(id);
        if(clazz.isInstance(object)) {
            return Optional.of(clazz.cast(object));
        }
        return Optional.empty();
    }

    public <T> List<T> findAll(Class<T> clazz) {
        return this.database.values()
                .stream()
                .filter(o -> clazz.equals(o.getClass()))
                .map(clazz::cast)
                .toList();
    }

    public void delete(UUID id) {
        this.database.remove(id);
    }

    public void drop() {
        database.clear();
    }
}
