package ru.silhin.lab_econom.model;

import java.io.Serializable;
import java.util.UUID;

public abstract class ModelObject implements Serializable {
    private UUID id;

    public ModelObject() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public abstract String toString();
}
