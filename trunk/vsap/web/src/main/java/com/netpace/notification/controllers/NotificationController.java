package com.netpace.notification.controllers;

import com.netpace.device.entities.enums.ApplicationPropertyType;
import com.netpace.device.exceptions.BusinessRuleException;
import com.netpace.device.services.ApplicationPropertiesService;
import com.netpace.device.services.SystemRoleService;
import com.netpace.device.utils.VAPConstants;
import com.netpace.device.utils.VAPSecurityManager;
import com.netpace.device.vo.SystemRoleVO;
import com.netpace.device.vo.VAPUserDetails;
import com.netpace.notification.services.EventService;
import com.netpace.notification.services.NotificationService;
import com.netpace.notification.services.PlaceholderService;
import com.netpace.notification.vo.EventVO;
import com.netpace.notification.vo.NotificationOptOutVO;
import com.netpace.notification.vo.NotificationVO;
import com.netpace.notification.vo.PlaceholderVO;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.activiti.engine.impl.util.json.JSONException;
import org.activiti.engine.impl.util.json.JSONWriter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller to handle all notification actions
 *
 */
@Controller
public class NotificationController {

    private static final Log log = LogFactory.getLog(NotificationController.class);
    @Autowired
    private ApplicationPropertiesService applicationPropertiesService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private EventService eventService;
    @Autowired
    private PlaceholderService placeholderService;
    @Autowired
    private SystemRoleService systemRoleService;

    /**
     * Render list of notifications
     *
     * @param request
     * @return
     */
    @RequestMapping("/secure/manage/notifications.do")
    public ModelAndView notificationsList(HttpServletRequest request) {

        VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();

        log.debug("Get notifications list");

        ModelAndView mav = new ModelAndView("/secure/notification/notificationslist.jsp");
        List<NotificationVO> notificationsList =
                notificationService.getAllNotifications(loggedInUser);

        mav.addObject("notificationsList", notificationsList);
        return mav;

    }

    /**
     * Get notification by id
     *
     * @param request
     * @return
     */
    @RequestMapping("/secure/viewnotification.do")
    public ModelAndView viewNotification(HttpServletRequest request,
            @Valid @RequestParam(value = "notificationId", required = true, defaultValue = "0") Integer notificationId,
            @RequestParam(value = "doPopulateModel", required = true, defaultValue = "true") boolean doResetModel) {

        log.debug("Get notification");
        NotificationVO notificationVO;
        List<EventVO> allEvents;
        List<PlaceholderVO> allPlaceholders;
        List<SystemRoleVO> allSystemRoles;
        ModelAndView mav = new ModelAndView("/secure/notification/notification.jsp");

        if (doResetModel) {
            // get data
            if (notificationId > 0) {
                notificationVO = notificationService.getNotification(notificationId);
                mav.addObject("url", "updatenotification.do");
                allPlaceholders = eventService.getEventPlaceholders(notificationVO.getEventId());
            } else {
                notificationVO = new NotificationVO();
                mav.addObject("url", "createnotification.do");
                allPlaceholders = placeholderService.getAllPlaceholders();
            }
            mav.addObject("notificationVO", notificationVO);
        } else {
            allPlaceholders = placeholderService.getAllPlaceholders();
        }

        allEvents = eventService.getAllEvents();
        mav.addObject("eventsList", allEvents);

        mav.addObject("placeholdersList", allPlaceholders);

        allSystemRoles = systemRoleService.getAllSystemRoles();
        mav.addObject("systemRolesList", allSystemRoles);

        return mav;
    }

    @RequestMapping("/secure/eventplaceholders.do")
    public void getEventPlaceholders(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "eventId") Integer eventId)
            throws JSONException, IOException {

        List<PlaceholderVO> placeholders;
        JSONWriter writer;

        writer = new JSONWriter(response.getWriter());
        writer.object();
        writer.key("results");
        writer.array();

        placeholders = eventService.getEventPlaceholders(eventId);

        for (PlaceholderVO placeHolderVO : placeholders) {
            populateNameValueJSONObject(writer, placeHolderVO.getId().toString(), placeHolderVO.getDisplayName());
        }

        writer.endArray();
        writer.key("total");
        writer.value(placeholders.size());
        writer.endObject();
        response.setContentType("application/json");
    }

    /**
     * create notification
     *
     * @param request
     * @param notificationVO
     * @return
     */
    @RequestMapping("/secure/createnotification.do")
    public ModelAndView createNotification(HttpServletRequest request,
            @Valid @ModelAttribute(value = "notificationVO") NotificationVO notificationVO, BindingResult bindingResult) {

        log.debug("Create notification");
        ModelAndView mav = new ModelAndView("redirect:/secure/manage/notifications.do");
        VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();

        if (bindingResult.hasErrors()) {
            mav.addObject("notificationVO", notificationVO);
            return viewNotification(request, 0, false);
        }

        // do service
        notificationService.createNotification(notificationVO, loggedInUser);

        return mav;
    }

    /**
     * update notification
     *
     * @param request
     * @param notificationVO
     * @return
     */
    @RequestMapping("/secure/updatenotification.do")
    public ModelAndView updateNotification(HttpServletRequest request,
            @Valid @ModelAttribute(value = "notificationVO") NotificationVO notificationVO, BindingResult bindingResult) {

        log.debug("Update notification");
        ModelAndView mav = new ModelAndView("redirect:/secure/manage/notifications.do");
        VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();

        if (bindingResult.hasErrors()) {
            mav.addObject("notificationVO", notificationVO);
            return viewNotification(request, notificationVO.getId(), false);
        }

        // do service
        notificationService.updateNotification(notificationVO, loggedInUser);

        return mav;
    }

    /**
     * delete notification
     *
     * @param request
     * @param notificationId
     * @return
     */
    @RequestMapping("/secure/deletenotification.do")
    public ModelAndView deleteNotification(HttpServletRequest request,
            @RequestParam(value = "notificationIds", required = true) Integer[] notificationIds) {

        log.debug("Delete notification");
        ModelAndView mav = new ModelAndView("redirect:/secure/manage/notifications.do");
        VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();

        if (notificationIds.length > 0) {
            notificationService.deleteNotification(notificationIds[0], loggedInUser);
        }

        return mav;
    }

    private void populateNameValueJSONObject(JSONWriter writer, String id,
            String name) throws JSONException {
        writer.object();
        writer.key("id");
        writer.value(id.toString());
        writer.key("name");
        writer.value(name);
        writer.endObject();
    }

    /**
     * Render list of notificationsByRoleWithOptOutStatus
     *
     * @param request
     * @return
     */
    @RequestMapping("/secure/notifications.do")
    public ModelAndView getNotificationsByRoleWithOptOutStatus(HttpServletRequest request) {

        VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();

        log.debug("Get notificationsByRoleWithOptOutStatus list");

        ModelAndView mav = new ModelAndView("/secure/notification/usernotificationslist.jsp");
        List<NotificationOptOutVO> notificationOptOutVOs =
                notificationService.getNotificationsByRoleWithOptOutStatus(loggedInUser);

        mav.addObject("notificationsList", notificationOptOutVOs);

        if (StringUtils.isNotEmpty(request.getParameter("NOTIFICATION_SUBSCRIBED"))) {
            mav.addObject("NOTIFICATION_SUBSCRIBED", "msg.notification.subscribe");
        } else if (StringUtils.isNotEmpty(request.getParameter("NOTIFICATION_UNSUBSCRIBED"))) {
            mav.addObject("NOTIFICATION_UNSUBSCRIBED", "msg.notification.unsubscribe");
        } else if (StringUtils.isNotEmpty(request.getParameter("NOTIFICATION_INVALID"))) {
            mav.addObject("NOTIFICATION_INVALID", "msg.notification.invalid");
        }
        mav.addObject("EDIT_NOTIFICATION_ROLES",
                applicationPropertiesService.propertyByNameAndType(
                ApplicationPropertyType.APPLICATION_PROPERTY,
                VAPConstants.APP_PROPERTY_TO_EDIT_NOTIFICATION_ON_ROLES));
        return mav;
    }

    /**
     * Subscribe notification
     *
     * @param request
     * @param notificationOptOutId
     * @return
     */
    @RequestMapping("/secure/subscribenotification.do")
    public ModelAndView subscribeNotification(HttpServletRequest request,
            @RequestParam(value = "id", required = true) Integer notificationOptOutId) {

        VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();

        log.debug("Subscribe notification.");

        ModelAndView mav = new ModelAndView("redirect:/secure/notifications.do");

        try {
            notificationService.subscribeNotification(loggedInUser, notificationOptOutId);
            mav.addObject("NOTIFICATION_SUBSCRIBED", "msg.notification.subscribe");
        } catch (BusinessRuleException bre) {
            log.debug(bre.getMessage());
            mav.addObject("NOTIFICATION_INVALID", "msg.notification.invalid");
        }
        return mav;
    }

    /**
     * Unsubscribe notification
     *
     * @param request
     * @param notificationId
     * @return
     */
    @RequestMapping("/secure/unsubscribenotification.do")
    public ModelAndView unsubscribeNotification(HttpServletRequest request,
            @RequestParam(value = "id", required = true) Integer notificationId) {

        VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();

        log.debug("Subscribe notification.");

        ModelAndView mav = new ModelAndView("redirect:/secure/notifications.do");

        try {
            notificationService.unsubscribeNotification(loggedInUser, notificationId);
            mav.addObject("NOTIFICATION_UNSUBSCRIBED", "msg.notification.unsubscribe");
        } catch (BusinessRuleException bre) {
            log.debug(bre.getMessage());
            mav.addObject("NOTIFICATION_INVALID", "msg.notification.invalid");
        }
        return mav;
    }
}
