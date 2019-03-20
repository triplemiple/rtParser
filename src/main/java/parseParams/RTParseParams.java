package parseParams;

public enum RTParseParams {

    HEADLINE_SELECTOR(".card__heading_main-promobox_@id"),
    DESCRIPTION_SELECTOR(".card__summary_main-promobox_@id"),
    PUB_TIME_SELECTOR(".card__date_main-promobox_@id"),
    NEWS_URL_SELECTOR(".card__heading_main-promobox_@id a"),
    PAGE_URL("https://russian.rt.com");

    private String param;

    RTParseParams(String param) {
        this.param = param;
    }

    public String getParam() {
        return param;
    }
}
