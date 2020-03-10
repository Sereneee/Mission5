package com.example.Mission5.Model;

import org.springframework.data.annotation.Id;
import org.springframework.lang.Nullable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
/*
spring data jdbc will be in snake case,
so this table name will be 'pets'
if attribute is petName, the column name is 'pet_name'
*/
public class Pet {
    //@NonNull
    @Id private String id;
    private String name;
    @Nullable private int age;
    @Nullable private String type;

    public Pet(String id, String name, int age, String type)
    {
        this.id = id;
        this.name = name;
        this.age= age;
        this.type = type;
    }

    public String toString()
    {
        return "Pet{" +
                "id=" + id +
                "name=" + name +
                "age" + age +
                "type" + type +
                "}";
    }

    static Pet create(String name, int age, String type)
    {
        return new Pet(null, name, age, type);
    }
}
