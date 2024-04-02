package org.example.service;

import org.example.container.Container;
import org.example.dao.ArticleDao;
import org.example.dto.Article;
import org.example.dto.Board;

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

    public List<Article> getForPrintArticles() {
        return articleDao.getForPrintArticles(null);
    }

    public Article getArticleById(int id) {
        return articleDao.getArticleById(id);
    }

    public void remove(Article foundArticle) {
        articleDao.remove(foundArticle);
    }

    public int write(int memberId, int boardId, String title, String body) {
        Article article = new Article(memberId, boardId, title, body);
        return articleDao.write(article);
    }

    public int getNewId() {
        return articleDao.getNewId();
    }

    public List<Article> getArticles() {
        return articleDao.getArticles();
    }

    public Board getBoard(int id) {
        return articleDao.getBoard(id);
    }

    public Article getForPrintArticle(int id) {
        return articleDao.getForPrintArticle(id);
    }
}
