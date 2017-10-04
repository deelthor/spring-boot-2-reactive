package de.deelthor.whiskybar.entity;

import com.datastax.driver.core.utils.UUIDs;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

import java.util.UUID;

@Table("whiskies")
public class Whisky {

    @PrimaryKeyColumn
    private UUID id;
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String distilleryName;
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String name;
    private Integer age;
    private Double strength;

    public Whisky() {
        id = UUIDs.timeBased();
    }

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

        Whisky whisky = (Whisky) o;

        if (distilleryName != null ? !distilleryName.equals(whisky.distilleryName) : whisky.distilleryName != null)
            return false;
        if (name != null ? !name.equals(whisky.name) : whisky.name != null) return false;
        if (age != null ? !age.equals(whisky.age) : whisky.age != null) return false;
        return strength != null ? strength.equals(whisky.strength) : whisky.strength == null;
    }

    @Override
    public int hashCode() {
        int result = distilleryName != null ? distilleryName.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (strength != null ? strength.hashCode() : 0);
        return result;
    }
}
