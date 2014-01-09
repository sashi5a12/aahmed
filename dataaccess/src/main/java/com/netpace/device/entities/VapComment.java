package com.netpace.device.entities;

import java.io.Serializable;

import javax.persistence.*;

/**
 */
@Entity
@Table(name = "vap_comments")
@NamedQueries({
    @NamedQuery(name = "findCommentsByCompany", query = "from VapComment comment inner join fetch comment.company company"
        + " where comment.active = '1' and comment.product.productId is null and company.id = :companyId order by comment.createdDate desc"
    ),
    @NamedQuery(name = "findCommentsByProduct", query = "from VapComment comment inner join fetch comment.product product"
        + " where comment.active = '1' and product.productId = :productId order by comment.createdDate desc"
    )
})
public class VapComment extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 4430133476964635284L;

    private Integer commentsId;
    private String commentsType;
    private String commentsText;    
    private Company company;
    private VapProduct product;

    public VapComment() {
    	
	}

	public VapComment(String commentsType, String commentsText) {
		this.commentsType = commentsType;
		this.commentsText = commentsText;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comments_id", nullable = false, unique = true)
	public Integer getCommentsId() {
        return commentsId;
    }

    public void setCommentsId(Integer commentsId) {
        this.commentsId = commentsId;
    }

	@Enumerated(EnumType.STRING)
    @Column(name = "comments_type")
    public String getCommentsType() {
        return commentsType;
    }

    public void setCommentsType(String commentsType) {
        this.commentsType = commentsType;
    }

    @Column(name = "comments_text")
    public String getCommentsText() {
        return commentsText;
    }

    public void setCommentsText(String commentsText) {
        this.commentsText = commentsText;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
	public VapProduct getProduct() {
		return product;
	}

	public void setProduct(VapProduct product) {
		this.product = product;
	}

	@Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("commentsId=[").append(commentsId).append("] ");
        buffer.append("commentsType=[").append(commentsType).append("] ");
        buffer.append("commentsText=[").append(commentsText).append("] ");
        return buffer.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (int) (prime * result + ((commentsId == null) ? 0 : commentsId.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof VapComment)) {
            return false;
        }
        VapComment equalCheck = (VapComment) obj;
        if ((commentsId == null && equalCheck.commentsId != null) || (commentsId != null && equalCheck.commentsId == null)) {
            return false;
        }
        if (commentsId != null && !commentsId.equals(equalCheck.commentsId)) {
            return false;
        }
        return true;
    }
}
