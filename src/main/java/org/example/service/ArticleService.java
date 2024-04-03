package org.example.service;

import org.example.container.Container;
import org.example.dao.ArticleDao;
import org.example.dto.Article;
import org.example.dto.ArticleReply;
import org.example.dto.Board;

import java.util.List;

// ArticleService는 ArticleController에서만 받아와서 ArticleDao에게 전달
public class ArticleService {
    private ArticleDao articleDao;

    public ArticleService() {
        articleDao = Container.articleDao;
    }

    public List<Article> getForPrintArticles(String boardCode, String searchKeyword) {
        return articleDao.getForPrintArticles(boardCode, searchKeyword);
    }

    public int write(int memberId, int boardId, String title, String body) {
        Article article = new Article(memberId, boardId, title, body);
        return articleDao.write(article);
    }

    public void modify(int id, String title, String body) {
        articleDao.modify(id, title, body);
    }

    public void delete(int id) {
        articleDao.delete(id);
    }

    public int getNewId() {
        return articleDao.getNewId();
    }

    // 게시물 전체 가져오기
    public List<Article> getArticles() {
        return articleDao.getArticles();
    }

    // 순수하게 article만 가져오는 것
    public Article getArticle(int id) {
        return articleDao.getArticle(id);
    }

    public Board getBoard(int id) {
        return articleDao.getBoard(id);
    }

    // 조인한 article을 가져오는 것
    public Article getForPrintArticle(int articleId) {
        return articleDao.getForPrintArticle(articleId);
    }

    // 댓글 관련
    public int replyWrite(int id, int memberId, String replyBody) {
        return articleDao.replyWrite(id, memberId, replyBody);
    }

    public List<ArticleReply> getForPrintArticleReplies(int id) {
        return articleDao.getForPrintArticleReplies(id);
    }
}
