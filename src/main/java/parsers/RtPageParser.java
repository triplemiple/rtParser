package parsers;

import database.entity.NewsEntity;
import helpers.ContentDownloadUtils;
import org.jsoup.select.Elements;
import parseParams.RTParseParams;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

public class RtPageParser extends NewsPageParser<NewsEntity> {

    private static RtPageParser instance;

    private RtPageParser() throws IOException {
        super(RTParseParams.PAGE_URL.getParam());
    }

    public static RtPageParser getInstance() {
        if (instance == null) {
            try {
                instance = new RtPageParser();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return instance;
    }

    public NewsEntity parse(int id) {
        NewsEntity news = new NewsEntity();

        String headlineSelector = RTParseParams.HEADLINE_SELECTOR.getParam().replace("@id", String.valueOf(id));
        String descriptionSelector = RTParseParams.DESCRIPTION_SELECTOR.getParam().replace("@id", String.valueOf(id));

        news.setHeadline(page.select(headlineSelector).text());
        news.setDescription(page.select(descriptionSelector).text());

        String pubDateSelector = RTParseParams.PUB_TIME_SELECTOR.getParam().replace("@id", String.valueOf(id));
        String pubDate = page.select(pubDateSelector).text();

        String[] dateTimeArr = pubDate.split(", ");
        LocalTime time = LocalTime.parse(dateTimeArr[dateTimeArr.length - 1]);
        LocalDate date = LocalDate.now();

        if (dateTimeArr.length > 1) {
            DateTimeFormatter f = new DateTimeFormatterBuilder()
                    .appendPattern("dd MMMM")
                    .parseDefaulting(ChronoField.YEAR, LocalDate.now().getYear())
                    .toFormatter()
                    .withLocale(Locale.getDefault());
            date = f.parse(dateTimeArr[0], LocalDate::from);
        }

        news.setPublicationDate(LocalDateTime.of(date, time));

        String urlSelector = RTParseParams.NEWS_URL_SELECTOR.getParam().replace("@id", String.valueOf(id));
        Elements urlElement = page.select(urlSelector);
        String url = urlElement.attr("abs:href");
        String fileName = url.substring(url.lastIndexOf("/")) + ".html";

        String htmlFilePath = null;
        try {
            htmlFilePath = ContentDownloadUtils.downloadContentByLink(fileName, url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        news.setURL(url);
        news.setPathToHtmlFile(htmlFilePath);

        return news;
    }
}
