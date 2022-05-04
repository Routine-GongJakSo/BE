package com.RoutineGongJakSo.BE.checkIn;

import com.RoutineGongJakSo.BE.checkIn.repository.CheckInRepository;
import com.RoutineGongJakSo.BE.model.CheckIn;
import com.RoutineGongJakSo.BE.model.User;
import com.RoutineGongJakSo.BE.security.UserDetailsImpl;
import com.RoutineGongJakSo.BE.security.validator.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckInService {

    private final CheckInRepository checkInRepository;
    private final Validator validator;

    //체크인
    @Transactional
    public String checkIn(UserDetailsImpl userDetails) {
        // 로그인 여부 확인
        validator.loginCheck(userDetails);

        //현재 서울 날짜
        String date = LocalDate.now(ZoneId.of("Asia/Seoul")).toString();

        // 유저 정보를 찾음
        User user = validator.userInfo(userDetails);

        List<CheckIn> checkInList = checkInRepository.findByUserAndDate(user, date);

        for (CheckIn check : checkInList) {
            if (check.getCheckOut() == null) {
                throw new NullPointerException("체크아웃을 먼저 해주세요");
            }
        }
        //ToDo if(다음날 29시 보다 작다면, 날짜는 = 오늘 날짜로 ㄱㄱ)

        //현재 시간
        ZonedDateTime nowSeoul = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar tomorrow = Calendar.getInstance();
//        tomorrow.setTime(sdf.parse(date));
        tomorrow.add(Calendar.DATE, 1);
        String strTomorrow = sdf.format(tomorrow.getTime());

//        if (nowSeoul > 오늘 05시보다 작다면 date는 -1) {
//        }

        CheckIn checkIn = CheckIn.builder()
                .user(user)
                .date(date)
                .checkIn(nowSeoul.format(DateTimeFormatter.ofPattern("HH:mm:ss")))
                .build();

        checkInRepository.save(checkIn);

        return "Start!";
    }

    //사용자가 이미 start를 누른 상태라면, 값을 내려주는 곳(당일)
    public String getCheckIn(UserDetailsImpl userDetails) throws ParseException {

        validator.loginCheck(userDetails);  // 로그인 여부 확인
        User user = validator.userInfo(userDetails);   // 유저 정보를 찾음

        //[서울]현재 날짜
        String date = LocalDate.now(ZoneId.of("Asia/Seoul")).toString();

        //내일 날짜를 구함
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar yesterDay = Calendar.getInstance();
        yesterDay.setTime(sdf.parse(date)); //전날
        yesterDay.add(Calendar.DATE, -1);
        String strYesterDay = sdf.format(yesterDay.getTime());

        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
        String sumTomorrow = strYesterDay + "05:00:00"; //전일 오전5시 기준
        Date setFormatter = formatter2.parse(sumTomorrow);

        //해당 유저의 해당 날짜의 전체 기록 찾기
        List<CheckIn> checkInList = checkInRepository.findByUserAndDate(user, date);

        //해당 유저의 전날 날짜의 전체 기록 찾기
        List<CheckIn> findCheckList = checkInRepository.findByUserAndDate(user, strYesterDay);

        Calendar setFormat = Calendar.getInstance(); // 초기화 시간 05시
        setFormat.setTime(setFormatter);

        //현재시간보다 과거일 때,
        for (CheckIn checkIn :  findCheckList){
            String sumDateTime = checkIn.getDate() + checkIn.getCheckIn();
            Date sumFormatter = formatter2.parse(sumDateTime); //체크인 시간
            Calendar sumFormat = Calendar.getInstance(); //체크인 시간
            sumFormat.setTime(sumFormatter);
            if (setFormat.compareTo(sumFormat) < 0){ // 인자보다 과거일 경우
                checkInList.add(checkIn); //해당 유저의 해당 날짜 전체 기록에 추가
            }
        }

        //기록 정보가 없을 때 return
        if (checkInList.size() < 1) {
            return "00:00:00";
        }

        //마지막 체크인 시간 확인
        CheckIn lastCheckIn = checkInList.get(checkInList.size() - 1);

        //마지막 기록의 체크아웃 값이 있는지 확인
        if (lastCheckIn.getCheckOut() != null) {
            //ToDo 해당 부분 추가로 수정하기!!
            //return Analysis analysis = 해당 유저의 해당 날짜의 마지막 sumTime;
            throw new NullPointerException("아직 Start 를 누르지 않았습니다.");
        }

        String[] timeStamp = lastCheckIn.getCheckIn().split(":"); //시, 분, 초 나누기

        int HH = Integer.parseInt(timeStamp[0]); //시
        int mm = Integer.parseInt(timeStamp[1]); //분
        int ss = Integer.parseInt(timeStamp[2]); //초

        //시간을 형식에 맞게 포맷
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
        SimpleDateFormat calenderFormatter = new SimpleDateFormat("HH:mm:ss");

        //현재 시간
        ZonedDateTime nowSeoul = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

        //Date 포맷을 위해 년, 월, 일을 하나씩 적출해서 string 으로 변환하고
        String nowYear = String.valueOf(nowSeoul.getYear());
        String nowMonth = String.valueOf(nowSeoul.getMonthValue());
        String nowDay = String.valueOf(nowSeoul.getDayOfMonth());

        //현재 시간을 스트링으로 변환하고
        String nowTime = nowSeoul.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        //시간과 년월일을 합친다
        String sumDateTime = nowYear+ "-" + nowMonth+ "-" + nowDay+ " " + nowTime;

        //Date 형식으로 포맷
        Date nowFormatter = formatter.parse(sumDateTime);

        //캘린더에 기준 시간(현재시간)을 넣어준다.
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowFormatter);

        //기록된 체크인 시간을 시, 분, 초 단위로 쪼개서 빼준다.
        calendar.add(Calendar.HOUR, -HH);
        calendar.add(Calendar.MINUTE, -mm);
        calendar.add(Calendar.SECOND, -ss);

        return calenderFormatter.format(calendar.getTime());
    }
}