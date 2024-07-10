package com.proit.weatherapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "t_user")
public class User extends AbstractEntity {

    @Column(unique = true)
    private String username;

    private String name;

    @JsonIgnore
    @Column(name = "password")
    private String hashedPassword;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<UserRole> roles = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<FavouriteLocation> favouriteLocations = new ArrayList<>();

    @Column(name = "profile_picture", length = 1000000)
    private byte[] profilePicture;
}
