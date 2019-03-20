package database.entity;

import java.time.LocalDateTime;

public class NewsEntity implements BaseEntity{

    private String description;
    private String headline;
    private String htmlFilePath;
    private LocalDateTime pubTime;
    private String url;
    private Integer id;

    public String getDescription() {
        return this.description;
    }

    public String getHeadline() {
        return this.headline;
    }

    public String getPathToHtmlFile() {
        return this.htmlFilePath;
    }

    public LocalDateTime getPubDate() {
        return this.pubTime;
    }

    public String getUrl() {
        return this.url;
    }

    public Integer getId() {
        return this.id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public void setPathToHtmlFile(String htmlFilePath) {
        this.htmlFilePath = htmlFilePath;
    }

    public void setPublicationDate(LocalDateTime publicationTime) {
        this.pubTime = publicationTime;
    }

    public void setURL(String url) {
        this.url = url;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
