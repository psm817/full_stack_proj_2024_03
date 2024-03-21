// dto 패키지를 만들어 해당 패키지로 파일 이동
// 앞으로 데이터와 관련된 클래스는 모두 dto 패키지에 모아둘 것
package org.example.dto;

// 데이터를 관리하는 곳을 dto라 하기도 한다.
// 리펙토링을 해서 Main 클래스와 관련없는 것들은 따로 나눠준다.
public class Article {
    public int id;
    public String title;
    public String body;
    public String regDate;
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
