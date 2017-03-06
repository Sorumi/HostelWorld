package edu.nju.hostelworld.service;

import edu.nju.hostelworld.bean.MemberInfoBean;
import edu.nju.hostelworld.model.Level;
import edu.nju.hostelworld.model.Member;
import edu.nju.hostelworld.util.ResultMessage;

import java.util.List;

/**
 * Created by Sorumi on 17/2/2.
 */
public interface MemberService {

    public MemberInfoBean convertToMemberInfoBean(String memberID);

    public ResultMessage addMember(Member member);

    public ResultMessage updateMember(Member member);

    public Member findMemberByID(String ID);

    public Member findMemberByUsername(String username);

    public ResultMessage checkMember(String username, String password);

    public List<Member> findAllMembers();

    public Level findLevelByMemberID(String memberID);

    public ResultMessage updateMoney(String ID, double money);

    public ResultMessage addPoint(String ID, int point);

    public ResultMessage activate(String ID);

    public ResultMessage pause(String ID);

    public ResultMessage resume(String ID);

    public ResultMessage stop(String ID);

    public ResultMessage deposit(String ID, double money);

    public ResultMessage exchangeMoney(String ID, int point);
}
