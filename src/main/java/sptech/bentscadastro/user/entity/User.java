package sptech.bentscadastro.user.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUser;
    @NotBlank
    public String name;
    @NotBlank
    @Pattern(regexp = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]{2,}$", message = "E-mail inválido")
    public String email;
    @NotBlank
    @Pattern(regexp = "^\\([1-9]{2}\\) (?:[2-8]|9[1-9])[0-9]{3}\\-[0-9]{4}$" ,
            message = "Informe um telefone válido com ou sem DDD")
    public String phone;
    @NotBlank
    @Pattern(regexp = "^(?=.*[0-9]{3})(?=.*[A-z]{1})[A-z0-9]{6,}$", message = "Senha inválida")
    public String password;
    @NotNull
    public Float lat;
    @NotNull
    public Float lng;
    @NotBlank
    @Size(min = 8, max = 8)
    public String cep;
    @NotBlank
    public String state;
    @NotBlank
    public String city;
    @NotBlank
    public String district;
    @NotBlank
    public String address;
    @NotNull
    public Integer addressNumber;
    public Boolean isLogged;

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLng() {
        return lng;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(Integer addressNumber) {
        this.addressNumber = addressNumber;
    }

    public Boolean getLogged() {
        return isLogged;
    }

    public void setLogged(Boolean logged) {
        isLogged = logged;
    }
}
