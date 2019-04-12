package com.mayavi.models;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Abhishek Garg on 2019-04-12
 */

@Entity
@Table(name = "partner")
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="partner_id")
    private int partnerId;

    public Partner(String name, int partnerId) {
        this.name = name;
        this.partnerId = partnerId;
    }

    public  Partner(){

    }
}
