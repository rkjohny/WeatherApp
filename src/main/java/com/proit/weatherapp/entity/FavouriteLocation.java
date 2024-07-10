package com.proit.weatherapp.entity;

import com.proit.weatherapp.dto.Location;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "t_favourite_location")
public class FavouriteLocation extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "location_id")
    private Long locationId;
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

    public static FavouriteLocation fromLocation(User user, Location location) {
        FavouriteLocation favouriteLocation = new FavouriteLocation();
        favouriteLocation.setUser(user);
        favouriteLocation.locationId = location.getId();
        favouriteLocation.name = location.getName();
        favouriteLocation.latitude = location.getLatitude();
        favouriteLocation.longitude = location.getLongitude();
        favouriteLocation.elevation = location.getElevation();
        favouriteLocation.countryCode = location.getCountryCode();
        favouriteLocation.country = location.getCountry();
        favouriteLocation.admin1 = location.getAdmin1();
        favouriteLocation.admin2 = location.getAdmin2();
        favouriteLocation.admin3 = location.getAdmin3();
        favouriteLocation.admin4 = location.getAdmin4();
        favouriteLocation.timezone = location.getTimezone();
        return favouriteLocation;
    }
}
