package org.example.service;

import org.example.Container;
import org.example.dao.ArticleDao;
import org.example.dto.Article;

import java.util.ArrayList;
import java.util.List;

// ArticleService는 ArticleController에서만 받아와서 ArticleDao에게 전달
public class ArticleService {
    private ArticleDao articleDao;

    public ArticleService() {
        articleDao = Container.articleDao;
    }
    public List<Article> getForPrintArticles(String searchKeyword) {
        return articleDao.getForPrintArticles(searchKeyword);
    }

    public Article getArticleById(int id) {
        return articleDao.getArticleById(id);
    }

    public void remove(Article foundArticle) {
        articleDao.remove(foundArticle);
    }

    public void add(Article article) {
        articleDao.add(article);
    }

    public int getNewId() {
        return articleDao.getNewId();
    }
}
