package pl.warczynski.jedrzej.backend;

public enum ApiUrls {
    BASE_URL("localhost:8080/api"),
    VERIFICATION_EMAIL_URL(BASE_URL.url + "/user/register"),
    RESET_PASSWORD_EMAIL_URL(BASE_URL.url + "/user/reset-password");
    private final String url;
    ApiUrls(String url) {
        this.url = url;
    }
    public String getUrl() {
        return url;
    }
}
