package com.netpace.notification.services.impl;

import com.netpace.notification.dao.PlaceholderDao;
import com.netpace.notification.entities.Placeholder;
import com.netpace.notification.services.PlaceholderService;
import com.netpace.notification.services.util.ETDConverter;
import com.netpace.notification.vo.PlaceholderVO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("placeholderService")
public class PlaceholderServiceImpl implements PlaceholderService{
    
    private static final Integer PLACEHOLDER_USERNAME       = 10;
    private static final Integer PLACEHOLDER_TO_ADDRESS     = 11;
    private static final Integer PLACEHOLDER_PASSWORD_LINK  = 12;
    
    @Autowired
    PlaceholderDao placeholderDao;

    @Override
    public List<PlaceholderVO> getAllPlaceholders() {
        List<PlaceholderVO> list = new ArrayList<PlaceholderVO>();
        List<Placeholder> placeholders = null;
        
        placeholders = placeholderDao.getAll();
        for (Placeholder placeholder : placeholders) {
            list.add(ETDConverter.convert(placeholder, new PlaceholderVO()));
        }
        
        return list;
    }
    
    public String getPlaceholderValue(String displayName){
        String value = "";
        Placeholder placeholder;
        
        placeholder = placeholderDao.getPlaceholderByName(displayName);
        if(placeholder != null){
            value = getPlaceholderValue(placeholder.getId());
        }
        
        return value;
    }
    
    public String getPlaceholderValue(Integer placeholderId){
        String value = "";
        //TODO: getting source environment and adding placeholder value
        return value;
    }
    
}
