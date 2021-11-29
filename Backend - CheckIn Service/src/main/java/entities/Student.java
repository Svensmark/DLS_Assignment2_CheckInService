/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Emil Svensmark
 */
@Entity
public class Student implements Serializable {

      private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @Column(name = "user_id", length = 25)
    private int id;
    @NotNull
    @Column(name = "name", length = 25)
    private String name;

    public Student() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
