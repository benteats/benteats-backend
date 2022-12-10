package sptech.bentscadastro.restaurant.DTO;

public interface RestaurantDetailDTO {
    Integer getId();

    String getName();

    String getAddress();

    String getAddressNumber();

    String getFoodType();

    String getPriceAverage();

    String getOpeningTime();

    String getDescription();

    Double getRatingAverage();

    Float getLat();

    Float getLng();

    byte[] getImgUrl();
}
