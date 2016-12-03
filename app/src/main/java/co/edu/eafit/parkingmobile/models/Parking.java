package co.edu.eafit.parkingmobile.models;

/**
 * Created by Alejocram on 2/12/2016.
 */

public class Parking {
    private String id;
    private String name;
    private String details;
    private String fieldsAvailable;
    private String price;
    private Double latitude;
    private Double longitude;
    private String picture;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getFieldsAvailable() {
        return fieldsAvailable;
    }

    public void setFieldsAvailable(String fieldsAvailable) {
        this.fieldsAvailable = fieldsAvailable;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
