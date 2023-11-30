package ru.silhin.lab_econom.model;

public class QualityProjectModel extends ModelObject {
    private QualityModel qualityModel;
    private Integer score;

    public QualityProjectModel() {
        super();
    }

    public QualityProjectModel(QualityModel model, Integer score) {
        super();
        this.qualityModel = model;
        this.score = score;
    }

    public QualityModel getQualityModel() {
        return qualityModel;
    }

    public void setQualityModel(QualityModel qualityModel) {
        this.qualityModel = qualityModel;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }


    @Override
    public String toString() {
        return String.format("%s: %s", qualityModel.getName(), score);
    }
}
