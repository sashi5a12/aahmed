package com.netpace.notification.services;

import com.netpace.device.vo.VAPUserDetails;
import com.netpace.notification.vo.NotificationOptOutVO;
import com.netpace.notification.vo.NotificationVO;
import java.util.List;

public interface NotificationService {

    public List<NotificationVO> getAllNotifications(VAPUserDetails loggedInUser);

    public NotificationVO getNotification(Integer notificationId);

    public void createNotification(NotificationVO notificationVO, VAPUserDetails loggedInUser);

    public void updateNotification(NotificationVO notificationVO, VAPUserDetails loggedInUser);

    public void deleteNotification(Integer notificationId, VAPUserDetails loggedInUser);

    public List<NotificationOptOutVO> getNotificationsByRoleWithOptOutStatus(VAPUserDetails loggedInUser);
    
    public void subscribeNotification(VAPUserDetails loggedInUser, Integer notificationOptOutId);
    
    public void unsubscribeNotification(VAPUserDetails loggedInUser, Integer notificationId);
}
