package com.dogy.lab1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.annotations.Check;

@Entity
public class Coordinates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Positive(message = "Id должен быть больше нуля")
    @Check(constraints = "id > 0")
    private Integer id;

    @NotNull(message = "Координата X не может быть пустой")
    @DecimalMin(value = "-648", inclusive = false, message = "Координата X должна быть больше -648")
    @Check(constraints = "x > -648")
    @Column(nullable = false)
    private Double x;

    @NotNull(message = "Координата Y не может быть пустой")
    @Column(nullable = false)
    private Double y;

    public Integer getId() {
        return id;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }
}
