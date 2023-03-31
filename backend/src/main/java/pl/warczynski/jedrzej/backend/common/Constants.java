package pl.warczynski.jedrzej.backend.common;

public enum Constants {
  BASE_URL("localhost:8080/api"),
  VERIFICATION_EMAIL_CONTENT("Please click on the link to verify your email: ");
  private final String value;
  Constants(String value) {
    this.value = value;
  }
  public String getValue() {
    return value;
  }

}
