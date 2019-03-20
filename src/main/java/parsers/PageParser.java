package parsers;

public interface PageParser<T> {
    T parse (int id);
}
