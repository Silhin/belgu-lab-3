package ru.silhin.lab_econom.model;

public class ActionWorkModel extends ModelObject {
    private WorkModel workModel;
    private ActionCreatingModel actionModel;
    private Integer time;

    public WorkModel getWorkModel() {
        return workModel;
    }

    public void setWorkModel(WorkModel workModel) {
        this.workModel = workModel;
    }

    public ActionCreatingModel getActionModel() {
        return actionModel;
    }

    public void setActionModel(ActionCreatingModel actionModel) {
        this.actionModel = actionModel;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return String.format("%s: %s [%s дней]", workModel.getName(), actionModel.getName(), time);
    }
}
