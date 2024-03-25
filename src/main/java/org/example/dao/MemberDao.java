package org.example.dao;

import org.example.dto.Member;

import java.util.ArrayList;
import java.util.List;

public class MemberDao extends Dao {
    public List<Member> members;

    public MemberDao() {
        members = new ArrayList<>();
    }

    // 회원을 추가할 때마다 lastId를 1씩 증가한다.
    public void add(Member member) {
        members.add(member);
        lastId++;
    }
}
