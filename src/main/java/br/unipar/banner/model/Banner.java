package br.unipar.banner.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "BANNERS")
public class Banner {

    @Id
    @GeneratedValue
    private UUID id;
    private String title;
    private String imageUrl;

    @Column(nullable = false)
    private boolean isActive = false;

    @Column(nullable = false)
    private boolean isPaid = false;

    private String externLink;
    private int credit;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date deadLine;
    private int numberOfClicks;

    @NotNull(message = "lojaId não pode ser nulo")
    private String lojaId;

    public Banner() {
    }

    public Banner(int numberOfClicks, Date deadLine, Date createdAt,
                  int credit, String externLink, boolean isPaid,
                  boolean isActive, String imageUrl, String title, UUID id, String lojaId) {
        this.numberOfClicks = numberOfClicks;
        this.deadLine = deadLine;
        this.createdAt = createdAt;
        this.credit = credit;
        this.externLink = externLink;
        this.isPaid = isPaid;
        this.isActive = isActive;
        this.imageUrl = imageUrl;
        this.title = title;
        this.id = id;
        this.lojaId = lojaId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
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
