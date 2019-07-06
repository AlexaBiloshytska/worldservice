package alexa.com.worldservice.entity;

public class CountryLanguage {
    private String countryLanguage;
    private String language;
    private boolean isOficial;
    private Integer percentage;

    public String getCountryLanguage() {
        return countryLanguage;
    }

    public void setCountryLanguage(String countryLanguage) {
        this.countryLanguage = countryLanguage;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isOficial() {
        return isOficial;
    }

    public void setOficial(boolean oficial) {
        isOficial = oficial;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return "CountryLanguage{" +
                "countryLanguage='" + countryLanguage + '\'' +
                ", language='" + language + '\'' +
                ", isOficial=" + isOficial +
                ", percentage=" + percentage +
                '}';
    }
}
