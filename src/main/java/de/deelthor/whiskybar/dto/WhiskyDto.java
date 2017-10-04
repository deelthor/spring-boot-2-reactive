package de.deelthor.whiskybar.dto;

import java.util.UUID;

public class WhiskyDto {

    private UUID id;
    private String distilleryName;
    private String name;
    private Integer age;
    private Double strength;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDistilleryName() {
        return distilleryName;
    }

    public void setDistilleryName(String distilleryName) {
        this.distilleryName = distilleryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getStrength() {
        return strength;
    }

    public void setStrength(Double strength) {
        this.strength = strength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WhiskyDto whiskyDto = (WhiskyDto) o;

        if (id != null ? !id.equals(whiskyDto.id) : whiskyDto.id != null) return false;
        if (distilleryName != null ? !distilleryName.equals(whiskyDto.distilleryName) : whiskyDto.distilleryName != null)
            return false;
        if (name != null ? !name.equals(whiskyDto.name) : whiskyDto.name != null) return false;
        if (age != null ? !age.equals(whiskyDto.age) : whiskyDto.age != null) return false;
        return strength != null ? strength.equals(whiskyDto.strength) : whiskyDto.strength == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (distilleryName != null ? distilleryName.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (strength != null ? strength.hashCode() : 0);
        return result;
    }
}
