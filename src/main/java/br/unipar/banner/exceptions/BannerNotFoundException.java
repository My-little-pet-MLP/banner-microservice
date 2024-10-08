package br.unipar.banner.exceptions;

public class BannerNotFoundException extends RuntimeException{

    public BannerNotFoundException(String message) {
        super(message);
    }

    public BannerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
