package sptech.bentscadastro.image.form;

public class UpdateImageRestaurantForm {

    private byte[] image;

    public UpdateImageRestaurantForm(byte[] image) {
        this.image = image;
    }



    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }


}
