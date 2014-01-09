package com.netpace.notification.dao;

import com.netpace.device.dao.GenericReadOnlyDao;
import com.netpace.notification.entities.Placeholder;
import java.util.List;

public interface PlaceholderDao extends GenericReadOnlyDao<Placeholder, Integer> {

    public static final String name = "placeholderDao";

    public Placeholder getPlaceholderById(Integer id);
    
    public Placeholder getPlaceholderByName(String displayName);
    
    public List<Placeholder> getPlaceHoldersList(List<Integer> placeHoldersIds);
    
}