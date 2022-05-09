package com.RoutineGongJakSo.BE.teamBoard;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class TeamBoardtDto {

    @Getter
    @Setter
    public static class RequestDto {
        private String groundRule;
        private String workSpace;

        @Builder
        public RequestDto(String groundRule) {

            this.groundRule = groundRule;
        }

        public RequestDto(String workSpace) {

            this.workSpace = workSpace;

        }
    }

    @Getter
    @Setter
    public static class ResponseDto {
        private Long teamId;
        private String groundRule;
        private String workSpace;

        @Builder
        public ResponseDto(TeamBoard entity) {
            this.teamId = entity.getTeamId();
            this.groundRule = entity.getGroundRule();
            this.workSpace = entity.getWorkSpace();
        }
    }
}