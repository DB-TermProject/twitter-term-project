package org.example.domain.follow.service;

import org.example.domain.follow.dto.FollowReqDTO.Follow;
import org.example.domain.follow.dto.FollowResDTO.FollowSummary;
import org.example.domain.follow.repository.FollowRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class FollowService {

    private final FollowRepository followRepository = new FollowRepository();

    public void save(Follow dto, String status, Connection connection) throws SQLException {
        followRepository.save(dto, status, connection);
    }

    public void delete(Follow dto, Connection connection) throws SQLException {
        followRepository.delete(dto, connection);
    }

    public boolean alreadyFollowed(Follow dto, Connection connection) throws SQLException {
        return followRepository.alreadyFollowed(dto, connection);
    }

    public void update(Follow dto, Connection connection) throws SQLException {    // from: 팔로우 보낸 유저, to: 팔로우 받은 유저
        followRepository.updateStatus(dto, connection);
    }

    public List<FollowSummary> findFollowers(Long id, Connection connection) throws SQLException {
        return followRepository.findFollowers(id, "ACCEPTED", connection);
    }

    public List<FollowSummary> findFollowings(Long id, Connection connection) throws SQLException {
        return followRepository.findFollowings(id, "ACCEPTED", connection);
    }

    public List<FollowSummary> findFollowingRequests(Long id, Connection connection) throws SQLException {     // 나의 신청한 팔로우 요청 목록
        return followRepository.findFollowers(id, "PENDING", connection);
    }

    public List<FollowSummary> findFollowersRequests(Long id, Connection connection) throws SQLException {     // 내가 받은 팔로우 요청 목록
        return followRepository.findFollowers(id, "PENDING", connection);
    }
}
