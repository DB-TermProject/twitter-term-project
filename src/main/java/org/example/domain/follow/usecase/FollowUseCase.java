package org.example.domain.follow.usecase;

import org.example.domain.follow.dto.FollowReqDTO.Follow;
import org.example.domain.follow.dto.FollowResDTO.FollowSummary;
import org.example.domain.follow.service.FollowService;
import org.example.domain.notice.service.NoticeService;
import org.example.domain.user.service.UserService;
import org.example.util.TransactionManager;

import java.util.List;

import static org.example.domain.notice.enums.NoticeMessage.*;

public class FollowUseCase {

    private final TransactionManager transactionManager = new TransactionManager();
    private final FollowService followService = new FollowService();
    private final UserService userService = new UserService();
    private final NoticeService noticeService = new NoticeService();

    public void follow(Follow dto) {
        transactionManager.execute(connection -> {
            dto.validate();
            if(userService.isPublic(dto.to(), connection)) {
                followService.save(dto, "ACCEPTED", connection);
                userService.updateFollowCount(dto.from(), dto.to(), 1L, connection);
                noticeService.notice(dto.to(), FOLLOWING.getMessage(userService.findName(dto.from(), connection)), connection);
            } else {
                followService.save(dto, "PENDING", connection);
                noticeService.notice(dto.to(), RECEIVED_FOLLOW_REQUEST.getMessage(userService.findName(dto.from(), connection)), connection);
            }
        });
    }

    public void unfollow(Follow dto) {
        transactionManager.execute(connection -> {
            dto.validate();
            followService.delete(dto, connection);
            userService.updateFollowCount(dto.from(), dto.to(), -1L, connection);
        });
    }

    public void accept(Follow dto) {
        transactionManager.execute(connection -> {
            dto.validate();
            followService.update(dto, connection);
            noticeService.notice(dto.from(), ACCEPTED_FOLLOW_REQUEST.getMessage(userService.findName(dto.to(), connection)), connection);
        });
    }

    public List<FollowSummary> readMyFollowers(Long userId) {
        return transactionManager.executeReadOnly(connection ->
            followService.findFollowers(userId, connection)
        );
    }

    public List<FollowSummary> readMyFollowings(Long userId) {
        return transactionManager.executeReadOnly(connection ->
            followService.findFollowings(userId, connection)
        );
    }

    public List<FollowSummary> readReceivedRequests(Long userId) {
        return transactionManager.executeReadOnly(connection ->
            followService.findFollowersRequests(userId, connection)
        );
    }

    public List<FollowSummary> readSentRequest(Long userId) {
        return transactionManager.executeReadOnly(connection ->
            followService.findFollowingRequests(userId, connection)
        );
    }
}
