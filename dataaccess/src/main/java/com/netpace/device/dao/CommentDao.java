package com.netpace.device.dao;

import com.netpace.device.entities.VapComment;
import java.util.List;

public interface CommentDao extends GenericDao<VapComment, Integer> {

    public static final String name = "commentDao";

    public List<VapComment> getCommentsByCompany(Integer companyId);

    public List<VapComment> getCommentsByProduct(Integer productId);

    public void addComment(VapComment vapComment);
}
