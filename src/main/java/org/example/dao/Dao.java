package org.example.dao;

public class Dao {
    // protected를 써서 자식클래스까지만 lastId를 쓸 수 있도록 한다.
    // 게시물 번호(article.id)와 회원 번호(member.id)를 삭제하고 다시 중복 사용하지 않기 위해서 lastId를 생성
    // Dao에서 관리하는 이유는 각 Dao에서 리스트(articles, members)를 만들기 때문
    protected int lastId;

    public Dao() {
        lastId = 0;
    }

    public int getNewId() {
        return lastId + 1;
    }

    // MemeberDao와 ArticleDao에서 모두 쓰는 메서드이기 때문에 부모 클래스인 Dao에서 한번에 관리
    public int getLastId() {
        return lastId;
    }
}
