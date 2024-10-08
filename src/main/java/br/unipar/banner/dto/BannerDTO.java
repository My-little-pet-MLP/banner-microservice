package br.unipar.banner.dto;

import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class BannerDTO {

    private String title;
    private String imageUrl;
    private boolean isActive;
    private boolean isPaid;
    private String externLink;
    private int credit;
    private Date deadLine;
    private int numberOfClicks;

    @NotNull(message = "lojaId não pode ser nulo")
    private String lojaId;

    public BannerDTO(String title, String imageUrl, boolean isActive, boolean isPaid,
                     String externLink, int credit, Date deadLine, int numberOfClicks, String lojaId) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.isActive = isActive;
        this.isPaid = isPaid;
        this.externLink = externLink;
        this.credit = credit;
        this.deadLine = deadLine;
        this.numberOfClicks = numberOfClicks;
        this.lojaId = lojaId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public String getExternLink() {
        return externLink;
    }

    public void setExternLink(String externLink) {
        this.externLink = externLink;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    public int getNumberOfClicks() {
        return numberOfClicks;
    }

    public void setNumberOfClicks(int numberOfClicks) {
        this.numberOfClicks = numberOfClicks;
    }

    public String getLojaId() {
        return lojaId;
    }

    public void setLojaId(String lojaId) {
        this.lojaId = lojaId;
    }
}


