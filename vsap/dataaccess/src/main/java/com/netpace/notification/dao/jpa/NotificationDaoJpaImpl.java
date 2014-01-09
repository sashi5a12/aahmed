package com.netpace.notification.dao.jpa;

import com.netpace.device.dao.jpa.GenericDaoJpaImpl;
import com.netpace.notification.dao.NotificationDao;
import com.netpace.notification.entities.Notification;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository(value = NotificationDao.name)
public class NotificationDaoJpaImpl extends GenericDaoJpaImpl<Notification, Integer> implements NotificationDao {

    private final static Log log = LogFactory.getLog(NotificationDaoJpaImpl.class);

    public NotificationDaoJpaImpl() {
        super(Notification.class);
    }

    @Override
    public List<Notification> getNotifications() {
        Query query = entityManager.createQuery(" from Notification notification where notification.active = '1'");

        return ((List<Notification>) query.getResultList());
    }

    @Override
    public Notification getNotificationById(Integer notificationId) {
        Query query = entityManager.createNamedQuery("findNotificationById");
        query.setParameter("notificationId", notificationId);
        try {
            return (Notification) query.getSingleResult();
        } catch (NoResultException nre) {
            log.debug(nre.getMessage());
        }
        return null;
    }

    @Override
    public List<Notification> getNotificationsByEvent(String eventName) {
        Query query = entityManager.createNamedQuery("findNotificationsByEvent");
        query.setParameter("eventName", eventName);

        return query.getResultList();
    }

    public List<Object[]> getNotificationsByRoleWithOptOutStatus(List<String> userRoles, Integer userId) {        
        //Integer userId=15; 
        String nativeQuery = "SELECT DISTINCT notif.notification_id, notif.notification_title, notif.notification_description, (SELECT notification_optout_id FROM vap_notification_optout notif_optout WHERE notif_optout.user_id=:userId AND notif_optout.notification_id=notif.notification_id) notification_optout_id "
                + "FROM "
                + "	( ( vap_notifications notif INNER JOIN vap_notification_roles notif_roles ON notif.notification_id=notif_roles.notification_id ) "
                + "	INNER JOIN vap_system_role sysrole ON notif_roles.role_id=sysrole.role_id ) 	"
                + "WHERE 	sysrole.title IN (:userRoles) and notif.is_active = '1'";

        Query query = entityManager.createNativeQuery(nativeQuery);
        

        //PARAM1 = userId
        //PARAM2 = ROLE_PARTNER_USER
        query.setParameter("userId", userId);
        query.setParameter("userRoles", userRoles);
        
        List<Object[]> notifications = new ArrayList<Object[]>();
        try {
            notifications = query.getResultList();
        } catch (NoResultException nre) {
            log.debug(nre.getMessage());
        }
        
        return notifications;
    }
    
    @Override
    public void deleteNotificationRoles(Integer notificationId){
        Query query = entityManager.createQuery("delete NotificationRole notificationRole where notificationRole.notification.id = :notificationId");
        query.setParameter("notificationId", notificationId);
        query.executeUpdate();
    }
    
    @Override
    public void deleteNotifAdHocRecipients(Integer notificationId){
        Query query = entityManager.createQuery("delete NotifAdHocRecipient notifAdHocRecipient where notifAdHocRecipient.notification.id = :notificationId");
        query.setParameter("notificationId", notificationId);
        query.executeUpdate();
    }
}
