package parsers;

import database.entity.BaseEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public abstract class NewsPageParser<T extends BaseEntity> implements PageParser<T> {

    public final String pageURL;
    protected Document page;

    NewsPageParser(String pageURL) throws IOException {
        this.page = Jsoup.connect(pageURL).get();
        this.pageURL = this.page.baseUri();
    }

    public void refreshPage() throws IOException {
        this.page = Jsoup.connect(this.pageURL).get();
    }

    public abstract T parse(int id);
}
