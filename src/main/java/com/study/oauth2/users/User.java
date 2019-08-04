package com.study.oauth2.users;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@ToString
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String password;

}

