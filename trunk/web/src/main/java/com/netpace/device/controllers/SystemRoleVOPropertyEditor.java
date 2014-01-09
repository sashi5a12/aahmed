package com.netpace.device.controllers;

import com.netpace.device.vo.SystemRoleVO;
import java.beans.PropertyEditorSupport;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nraza
 */
public class SystemRoleVOPropertyEditor extends PropertyEditorSupport {

    private Map<String, SystemRoleVO> roleMap = new HashMap<String, SystemRoleVO>();

    public SystemRoleVOPropertyEditor(List<SystemRoleVO> roleList) {
        for (SystemRoleVO r : roleList) roleMap.put(r.getRoleName(), r);
    }

    @Override
    public void setAsText(String incomingId) {
        SystemRoleVO role = roleMap.get(incomingId);
        setValue(role);
    }

    @Override
    public String getAsText() {
        return String.valueOf(((SystemRoleVO)getValue()).getRoleId());
    }
}
