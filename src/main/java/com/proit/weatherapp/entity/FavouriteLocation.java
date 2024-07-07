package com.proit.weatherapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "t_favourite_location")
public class FavouriteLocation extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;
    private Long elevation;
    @Column(name = "country_code")
    private String countryCode;
    private String country;
    private String admin1;
    private String admin2;
    private String admin3;
    private String admin4;
    private String timezone;
}
