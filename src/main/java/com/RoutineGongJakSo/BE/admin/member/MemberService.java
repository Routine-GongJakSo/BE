package com.RoutineGongJakSo.BE.admin.member;

import com.RoutineGongJakSo.BE.admin.team.Team;
import com.RoutineGongJakSo.BE.admin.team.TeamDto;
import com.RoutineGongJakSo.BE.admin.team.TeamRepository;
import com.RoutineGongJakSo.BE.admin.week.Week;
import com.RoutineGongJakSo.BE.admin.week.WeekRepository;
import com.RoutineGongJakSo.BE.client.user.User;
import com.RoutineGongJakSo.BE.client.user.UserRepository;
import com.RoutineGongJakSo.BE.exception.CustomException;
import com.RoutineGongJakSo.BE.exception.ErrorCode;
import com.RoutineGongJakSo.BE.security.exception.UserException;
import com.RoutineGongJakSo.BE.security.exception.UserExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;
    private final UserRepository userRepository;
    private final WeekRepository weekRepository;

    // 팀원 추가
    @Transactional
    public String addMembers(Long weekId, MemberDto.RequestDto addTeamDto) {

        Long targetTeamId = addTeamDto.getTeamId();
        Long targetUserId = addTeamDto.getUserId();

        Week targetWeek = weekRepository.findById(weekId).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_WEEK_ID)
        );

        Team targetTeam = teamRepository.findById(targetTeamId).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_TEAM_ID)
        );

        User targetUser = userRepository.findById(targetUserId).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_USER_ID)
        );

        // 이미 소속된 팀이 존재하는지 확인
        List<Team> teamList = targetWeek.getTeamList();

        if(!teamList.contains(targetTeam)){
            throw new CustomException(ErrorCode.NOT_FOUND_TEAM_IN_WEEK);
        }

        for (Team _team : teamList) {
            List<Member> memberList = _team.getMemberList();

            for (Member _member : memberList) {
                if (_member.getUser() == targetUser) {
                    throw new CustomException(ErrorCode.SOLD_OUT_USER);
                }
            }
        }

        Member member = Member.builder()
                .team(targetTeam)
                .user(targetUser)
                .build();

        targetUser.addMember(member);
        targetTeam.addMember(member);

        memberRepository.save(member);

        return "팀원 추가 완료!";
    }

    //팀원 삭제
    @Transactional
    public String deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_MEMBER_ID)
        );

        memberRepository.delete(member);
        return "삭제 완료";
    }


    //    //해당 주차에 멤버아이디가 없는 유저 리스트
    public List<MemberDto.GetNoMember> getNoMember(Long weekId) {

        Week targetWeek = weekRepository.findById(weekId).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_WEEK_ID)
        );

        List<Team> teamList = targetWeek.getTeamList();

        //모든 유저를 찾기
        List<User> noMemberList = userRepository.findAll();
        //값을 return 할 CheckInListDto 만들기
        List<MemberDto.GetNoMember> noMembers = new ArrayList<>();

        for (Team team : teamList) {
            List<Member> memberList = team.getMemberList();
            for (Member find : memberList) {
                User getUser = userRepository.findById(find.getUser().getUserId()).orElseThrow(
                        () -> new UserException(UserExceptionType.NOT_FOUND_MEMBER)
                );
                //제거 대상 제거
                noMemberList.remove(getUser);
            }
        }

        //return 값 가공하기
        for (User user : noMemberList) {
            MemberDto.GetNoMember response = MemberDto.GetNoMember.builder()
                    .userId(user.getUserId())
                    .userName(user.getUserName())
                    .build();

            noMembers.add(response);
        }

        return noMembers;
    }
}
