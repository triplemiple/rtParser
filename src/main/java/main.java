import database.PostgresConnection;
import database.entity.NewsEntity;
import database.entityDAO.NewsDAO;
import parsers.RtPageParser;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

public class main {
    public static void main(String[] args) {

        if (args.length > 1 || args.length == 0) {
            throw new IllegalArgumentException("Неверное число аргуметов. Доступные команды:\n" +
                    "download Загрузить первые три новости с https://russian.rt.com\n" +
                    "view Просмотреть все загруженные новости\n");
        }

        final int PARSE_COUNT = 3;
        RtPageParser rtPageParser = RtPageParser.getInstance();
        NewsDAO newsDAO = new NewsDAO();
        PostgresConnection conn = new PostgresConnection();

        switch (args[0]) {
            case "download":
                try {
                    for (int i = 1; i <= PARSE_COUNT; i++) {
                        NewsEntity newsEntry = rtPageParser.parse(i);
                        List<NewsEntity> queryResult = newsDAO.selectByFields(newsEntry, conn.getConnection());

                        if (queryResult != null) {
                            continue;
                        }

                        newsDAO.insert(newsEntry, conn.getConnection());
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "view":
                try {
                    List<NewsEntity> queryResult = newsDAO.selectAll(conn.getConnection());

                    if (queryResult == null) {
                        System.out.println("В базе нет значений");
                        return;
                    }

                    StringBuilder sb = new StringBuilder();

                    for (NewsEntity entry : queryResult) {
                        sb.append("\n=============================================================================\n");
                        sb.append("ID в БД:").append("\n");
                        sb.append(entry.getId()).append("\n");
                        sb.append("Заголовок:").append("\n");
                        sb.append(entry.getHeadline()).append("\n");
                        sb.append("Краткое описание:").append("\n");
                        sb.append(entry.getDescription()).append("\n");
                        DateTimeFormatter f = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
                        sb.append("Дата публикации:").append("\n");
                        sb.append(f.format(entry.getPubDate())).append("\n");
                        sb.append("Ссылка на новость:").append("\n");
                        sb.append(entry.getUrl()).append("\n");
                        sb.append("Путь до локального файла:").append("\n");
                        sb.append(entry.getPathToHtmlFile()).append("\n");
                        sb.append("=============================================================================\n");
                    }

                    System.out.println(sb.toString());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            default:
                throw new IllegalArgumentException("Передан неверный параметр. Доступные команды:\n " +
                        "download Загрузить первые три новости с https://russian.rt.com\n" +
                        "view Просмотреть все загруженные новости\n");
        }
    }
}
