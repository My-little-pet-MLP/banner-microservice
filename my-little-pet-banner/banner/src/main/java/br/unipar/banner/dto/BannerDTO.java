package br.unipar.banner.dto;

public class BannerDTO {

    private String imageUrl;
    private String externLink;

    public BannerDTO(String imageUrl, String externLink) {
        this.imageUrl = imageUrl;
        this.externLink = externLink;
    }

    public String getExternLink() {
        return externLink;
    }

    public void setExternLink(String externLink) {
        this.externLink = externLink;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}


