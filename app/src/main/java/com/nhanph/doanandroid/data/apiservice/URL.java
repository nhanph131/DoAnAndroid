package com.nhanph.doanandroid.data.apiservice;


public enum URL {
    //deploy len Render
//    BASE_URL("https://hoyonime-springboot.onrender.com/hoyonime/"),
//    IP("hoyonime-springboot.onrender.com"),


    //test tren may that
//    BASE_URL("http://192.168.1.27:8080/hoyonime/"),
//    IP("192.168.1.27:8080"),

    //tren emulator
    BASE_URL("http://10.0.2.2:8080/doanandroid/"),
    IP("10.0.2.2:8080"),

    IMG_URL("https://res.cloudinary.com/decztaxn1/image/upload/"),

    IMG_URL_USER("https://res.cloudinary.com/dtrl2znqh/image/upload/users/user_beta_01/upload/upload_user_beta_01_img_31.jpg"),

    IMG_URL_ADMIN("https://res.cloudinary.com/dtrl2znqh/image/upload/users/");
    private final String url;
    private URL(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

}
