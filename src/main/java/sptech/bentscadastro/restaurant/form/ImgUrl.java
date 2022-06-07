package sptech.bentscadastro.restaurant.form;

public class ImgUrl {
    private String[] imgUrl;
    private int size;

    public ImgUrl(String[] imgUrl) {
        this.imgUrl = new String[5];
        size = 0;
    }

    public String getImgUrl() {
        return imgUrl[size];
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl[size] = imgUrl;
        size++;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
