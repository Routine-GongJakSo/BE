package com.RoutineGongJakSo.BE.social;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoUserInfoDto {
    private Long kakaoId;
    private String userName;
    private String email;
}