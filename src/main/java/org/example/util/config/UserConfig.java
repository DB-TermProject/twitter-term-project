package org.example.util.config;

public class UserConfig {

    private static final UserConfig instance = new UserConfig(); // 클래스 로딩 시 인스턴스 생성
    private static Long myId;

    private UserConfig() {
    }

    public static UserConfig getInstance() {
        return instance; // 이미 생성된 인스턴스를 반환
    }

    public void setMyId(Long id) {
        myId = id;
    }

    public Long getMyId() {
        return myId;
    }
}
