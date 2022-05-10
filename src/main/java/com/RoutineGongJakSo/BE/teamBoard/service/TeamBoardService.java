package com.RoutineGongJakSo.BE.teamBoard.service;

import com.RoutineGongJakSo.BE.admin.dto.MemberDto;
import com.RoutineGongJakSo.BE.admin.repository.MemberRepository;
import com.RoutineGongJakSo.BE.admin.repository.WeekTeamRepository;
import com.RoutineGongJakSo.BE.model.Member;
import com.RoutineGongJakSo.BE.model.User;
import com.RoutineGongJakSo.BE.model.WeekTeam;
import com.RoutineGongJakSo.BE.security.UserDetailsImpl;
import com.RoutineGongJakSo.BE.security.validator.Validator;
import com.RoutineGongJakSo.BE.teamBoard.dto.*;
import com.RoutineGongJakSo.BE.toDo.ToDo;
import com.RoutineGongJakSo.BE.toDo.ToDoValidator;
import com.RoutineGongJakSo.BE.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamBoardService {

    private final Validator validator;
    private final WeekTeamRepository weekTeamRepository;
    private final MemberRepository memberRepository;
    private final ToDoValidator toDoValidator;


    public TeamBoardDto getAllTeamBoard(UserDetailsImpl userDetails) {

        User user = validator.userInfo(userDetails);// 유저 정보를 찾음(로그인 하지 않았다면 에러 뜰 것)

        List<Member> userMemberList = user.getMemberList();

        List<WeekTeam> weekTeamList = new ArrayList<>();
        List<WeekTeamDto> weekTeamDtoList = new ArrayList<>();

        for (Member member : userMemberList) {
            WeekTeam weekTeam = member.getWeekTeam();
            weekTeamList.add(weekTeam);
            weekTeamDtoList.add(new WeekTeamDto(weekTeam));
        }

        WeekTeam lastTeam = weekTeamList.get(weekTeamList.size() - 1);

        List<ToDo> toDoList = toDoValidator.toDoList(lastTeam);

        List<ToDoDto> toDoDtoList = new ArrayList<>();

        for (ToDo todo : toDoList) {
            ToDoDto toDoDto = ToDoDto.builder()
                    .todoId(todo.getToDoId())
                    .todoContent(todo.getTodoContent())
                    .todoCheck(todo.isTodoCheck())
                    .build();
            toDoDtoList.add(toDoDto);
        }

        List<Member> teamMemberList = lastTeam.getMemberList();
        List<MemberDto> teamMemberDtoList = new ArrayList<>();
        for (Member member : teamMemberList) {
            MemberDto memberDto = new MemberDto();
            memberDto.setMemberId(member.getMemberId());
            memberDto.setUser(new UserDto(member.getUser()));
            teamMemberDtoList.add(memberDto);
        }
        TeamBoardDto teamBoardDto = new TeamBoardDto(lastTeam);

        teamBoardDto.setWeekTeamList(weekTeamDtoList);
        teamBoardDto.setMemberList(teamMemberDtoList);
        teamBoardDto.setToDoList(toDoDtoList);

        System.out.println("teamBoardDto = " + teamBoardDto);

        return teamBoardDto;
    }

    public void updateGroundRule(UserDetailsImpl userDetails, Long weekTeamId, GroundRuleDto groundRule) {
        validator.userInfo(userDetails);// 유저 정보를 찾음(로그인 하지 않았다면 에러 뜰 것)

        User user = userDetails.getUser();

        WeekTeam weekTeam = weekTeamRepository.findById(weekTeamId).orElseThrow(() -> new IllegalArgumentException("팀없다."));

        memberRepository.findByUserAndWeekTeam(user, weekTeam).orElseThrow(() -> new IllegalArgumentException("이 팀의 멤버가 아닙니다."));

        weekTeam.setGroundRole(groundRule.getGroundRule());

        weekTeamRepository.save(weekTeam);

    }

    public void updateWorkSpace(UserDetailsImpl userDetails, Long weekTeamId, WorkSpaceDto workSpace) {
        validator.userInfo(userDetails);// 유저 정보를 찾음(로그인 하지 않았다면 에러 뜰 것)

        User user = userDetails.getUser();

        WeekTeam weekTeam = weekTeamRepository.findById(weekTeamId)
                .orElseThrow(() -> new IllegalArgumentException("팀없다."));

        memberRepository.findByUserAndWeekTeam(user, weekTeam)
                .orElseThrow(() -> new IllegalArgumentException("이 팀의 멤버가 아닙니다."));

        weekTeam.setWorkSpace(workSpace.getWorkSpace());

        weekTeamRepository.save(weekTeam);
    }

    public TeamBoardDto getTeamBoard(UserDetailsImpl userDetails, Long weekTeamId) {

        User user = validator.userInfo(userDetails);// 유저 정보를 찾음(로그인 하지 않았다면 에러 뜰 것)

        WeekTeam findWeekTeam = toDoValidator.findWeekTeam(weekTeamId); //Id로 주차팀 찾기

        List<ToDo> toDoList = toDoValidator.toDoList(findWeekTeam);

        List<ToDoDto> toDoDtoList = new ArrayList<>();

        for (ToDo todo : toDoList) {
            ToDoDto toDoDto = ToDoDto.builder()
                    .todoId(todo.getToDoId())
                    .todoContent(todo.getTodoContent())
                    .todoCheck(todo.isTodoCheck())
                    .build();
            toDoDtoList.add(toDoDto);
        }

        List<Member> userMemberList = user.getMemberList();

        List<WeekTeam> weekTeamList = new ArrayList<>();
        List<WeekTeamDto> weekTeamDtoList = new ArrayList<>();

        WeekTeam targetTeam = new WeekTeam();

        for (Member member : userMemberList) {
            WeekTeam weekTeam = member.getWeekTeam();
            weekTeamList.add(weekTeam);
            weekTeamDtoList.add(new WeekTeamDto(weekTeam));
            if (weekTeamId.equals(weekTeam.getWeekTeamId())) {
                targetTeam = weekTeam;
            }
        }
        List<Member> teamMemberList = targetTeam.getMemberList();
        List<MemberDto> teamMemberDtoList = new ArrayList<>();
        for (Member member : teamMemberList) {
            MemberDto memberDto = new MemberDto();
            memberDto.setMemberId(member.getMemberId());
            memberDto.setUser(new UserDto(member.getUser()));
            teamMemberDtoList.add(memberDto);
        }
        TeamBoardDto teamBoardDto = new TeamBoardDto(targetTeam);

        teamBoardDto.setWeekTeamList(weekTeamDtoList);
        teamBoardDto.setMemberList(teamMemberDtoList);
        teamBoardDto.setToDoList(toDoDtoList);

        System.out.println("teamBoardDto = " + teamBoardDto);

        return teamBoardDto;

    }
}