package com.mayavi.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Abhishek Garg on 2019-04-12
 */

@Getter
@Setter
@Entity
@ToString
@Table(name = "api_responses")
public class APIResponses {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;

    @Column(name="api_call_name")
    private String apiCallName;

    @Column(name="api_call_url")
    private String apiCallUrl;

    @Column(name = "response")
    private String apiResponse;

    @Column(name = "params")
    private String params;

    public APIResponses(){

    }
}
