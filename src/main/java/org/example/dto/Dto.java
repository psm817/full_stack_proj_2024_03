package org.example.dto;

// dto 패키지에 안에 있는 클래스(article, member)에서 겹치는 변수인 id와 regDate를
// Dto 클래스에 한번에 담고, 각 클래스에서는 extends를 통해 Dto를 상속받게 해준다.
public class Dto {
    public int id;
    public String regDate;
}
