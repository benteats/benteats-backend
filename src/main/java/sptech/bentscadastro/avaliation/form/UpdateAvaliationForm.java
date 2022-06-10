package sptech.bentscadastro.avaliation.form;

import java.util.Date;

public class UpdateAvaliationForm {
    private Date dhAvaliation;
    private Double rating;
    private String comment;

    public Date getDhAvaliation() {
        return dhAvaliation;
    }

    public void setDhAvaliation(Date dhAvaliation) {
        this.dhAvaliation = dhAvaliation;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
