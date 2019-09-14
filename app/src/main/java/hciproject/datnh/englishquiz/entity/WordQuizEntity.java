package hciproject.datnh.englishquiz.entity;

public class WordQuizEntity {
    private int id;
    private String name;
    private String word;
    private String blankWord;
    private String meaning;
    private String type;
    private String author;

    public WordQuizEntity() {
    }

    public WordQuizEntity( String name, String word, String blankWord) {
        this.name = name;
        this.word = word;
        this.blankWord = blankWord;
    }

    public WordQuizEntity(int id, String name, String meaning, String type) {
        this.id = id;
        this.name = name;
        this.meaning = meaning;
        this.type = type;
    }

    public WordQuizEntity(int id, String name, String word, String blankWord, String meaning, String type) {
        this.id = id;
        this.name = name;
        this.word = word;
        this.blankWord = blankWord;
        this.meaning = meaning;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getBlankWord() {
        return blankWord;
    }

    public void setBlankWord(String blankWord) {
        this.blankWord = blankWord;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
