package org.example;

import org.example.dao.ArticleDao;
import org.example.dao.MemberDao;

// Container는 Dao을 관리하는 바다같은 공간
// Dao는 DB를 관리하는 관리인같은 존재
public class Container {
    public static ArticleDao articleDao;
    public static MemberDao memberDao;

    static {
        articleDao = new ArticleDao();
        memberDao = new MemberDao();
    }
}