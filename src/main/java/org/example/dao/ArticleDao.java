package org.example.dao;

import org.example.dto.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticleDao extends Dao {
    public List<Article> articles;

    public ArticleDao() {
        articles = new ArrayList<>();
    }

    // 게시물을 추가할 때마다 lastId를 1씩 증가한다.
    public void add(Article article) {
        articles.add(article);
        lastId++;
    }
}
