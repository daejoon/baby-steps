package com.ddoong2.webapp.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 기본적으로 Getter 만 오픈한다.
 * 필요하면 그때 그때 기능 메소드 오픈
 */
@Getter @Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@EqualsAndHashCode(of = "id")
@Entity
public class Account {

    /** 고유한 ID */
    @Id @GeneratedValue
    private Long id;

    /** 이메일 유니크 */
    @Column(unique = true)
    private String email;

    /** 닉네임 유니크 */
    @Column(unique = true)
    private String nickname;

    /** 비밀번호 */
    private String password;

    /** 이메일 인증 여부 */
    private boolean emailVerified;

    /** 이메일 인증에 필요한 토큰 */
    private String emailCheckToken;

    /** 가입 날짜 */
    private LocalDateTime joinedAt;

    /** 계정 프로필 */
    private String bio;

    /** url */
    private String url;

    /** 직업 */
    private String occupation;

    /** 사는 지역 */
    private String location;

    /** 프로필 이미지 */
    @Lob @Basic(fetch = FetchType.EAGER)
    private String profileImage;

    /** 알림설정 이메일 */
    private boolean studyCreatedByEmail;

    /** 알림설정 웹 */
    private boolean studyCreatedByWeb = true;

    /** 가입결과 이메일 */
    private boolean studyEnrollmentResultByEmail;

    /** 가입결과 웹 */
    private boolean studyEnrollmentResultByWeb = true;

    /** 변경사항 이메일로 받음 */
    private boolean studyUpdatedByEmail;

    /** 변경사항 웹으로 받음 */
    private boolean studyUpdatedByWeb = true;

    /** 토큰을 생성했을때 시간 */
    private LocalDateTime emailCheckTokenGeneratedAt;

    public void generateEmailCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString();
        this.emailCheckTokenGeneratedAt = LocalDateTime.now();
    }

    public void completeSignUp() {
        this.emailVerified = true;
        this.joinedAt = LocalDateTime.now();
    }

    public boolean isValidToken(final String token) {
        return this.emailCheckToken.equals(token);
    }

    public boolean canSendConfirmEmail() {
        return this.emailCheckTokenGeneratedAt.isBefore(LocalDateTime.now().minusHours(1));
    }
}
