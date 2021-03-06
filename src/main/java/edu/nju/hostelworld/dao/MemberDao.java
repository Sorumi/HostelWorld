package edu.nju.hostelworld.dao;

import edu.nju.hostelworld.model.Member;
import edu.nju.hostelworld.util.MemberState;
import edu.nju.hostelworld.util.ResultMessage;

import java.util.List;

/**
 * Created by Sorumi on 17/2/2.
 */
public interface MemberDao {

    public ResultMessage addMember(Member member);

    public ResultMessage updateMember(Member member);

    public long countMembers();

    public Member findMemberByID(String ID);

    public Member findMemberByUsername(String username);

    public List<Member> findAllMembers();

    public List<Member> findMembersByStateAndMaxDate(MemberState memberState, String dateField, String date);
}
