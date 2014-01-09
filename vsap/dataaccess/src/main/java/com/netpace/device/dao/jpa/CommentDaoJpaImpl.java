package com.netpace.device.dao.jpa;

import com.netpace.device.dao.CommentDao;
import com.netpace.device.entities.VapComment;
import java.util.List;
import javax.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository(CommentDao.name)
public class CommentDaoJpaImpl extends GenericDaoJpaImpl<VapComment, Integer> implements CommentDao {

    private final static Log log = LogFactory.getLog(CommentDaoJpaImpl.class);

    public CommentDaoJpaImpl() {
        super(VapComment.class);
    }

    @Override
    public List<VapComment> getCommentsByCompany(Integer companyId) {
        Query query = entityManager.createNamedQuery("findCommentsByCompany");
        query.setParameter("companyId", companyId);
        return query.getResultList();
    }

    @Override
    public List<VapComment> getCommentsByProduct(Integer productId) {
        Query query = entityManager.createNamedQuery("findCommentsByProduct");
        query.setParameter("productId", productId);
        return query.getResultList();
    }

    @Override
    public void addComment(VapComment vapComment) {
        entityManager.persist(vapComment);
    }
}
