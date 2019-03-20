package database.entity;

import java.time.LocalDateTime;

public interface BaseEntity {

    String getDescription();

    String getHeadline();

    String getPathToHtmlFile();

    LocalDateTime getPubDate();

    String getUrl();

    Integer getId();
}
