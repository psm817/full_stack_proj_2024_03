package org.example.dto;

public class Article extends Dto {
    public String title;
    public String body;
    public int hit;

    public Article(int id, String title, String body, String regDate, int hit) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.regDate = regDate;
        this.hit = hit;
    }

    public Article(int id, String title, String body, String regDate) {
        this(id, title, body, regDate, 0);
    }

    public void increaseHit() {
        hit++;
    }
}
