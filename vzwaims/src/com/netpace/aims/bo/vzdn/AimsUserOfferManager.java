package com.netpace.aims.bo.vzdn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.JDBCException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import com.netpace.aims.bo.core.UniqueConstraintException;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.core.AimsUserOffer;
import com.netpace.aims.util.AimsConstants;
import com.opensymphony.workflow.loader.RestrictionDescriptor;
import org.apache.log4j.Logger;

public class AimsUserOfferManager {

    private static Logger log = Logger.getLogger(AimsUserOfferManager.class.getName());

	public static Collection getUserOffers(String userName)
			throws HibernateException {

		Session session = null;
		Collection userOfferCollection;
		try {
			session = DBHelper.getInstance().getSession();
			Query query = session
					.createQuery("select from com.netpace.aims.model.core.AimsUserOffer userOffer where userOffer.status='"
							+ AimsConstants.ACTIVE
							+ "' and trim(lower(userOffer.offerTo))= :userName");
			
			query.setString("userName", userName.toLowerCase().trim());

			userOfferCollection = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (session != null)
				session.close();
		}

		return userOfferCollection;
	}

	public static AimsUserOffer getUserOffer(Long offerId)
			throws HibernateException {

		Session session = null;
		AimsUserOffer aimsUserOffer = null;
		try {
			session = DBHelper.getInstance().getSession();
			Query query = session
					.createQuery("select from com.netpace.aims.model.core.AimsUserOffer userOffer where "
							+ "userOffer.offerId=" + offerId);
			aimsUserOffer = (AimsUserOffer) query.list().get(0);
			return aimsUserOffer;

		} catch (HibernateException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (session != null)
				session.close();
		}

	}

	public static void updateUserOffer(AimsUserOffer userOffer) throws UniqueConstraintException, HibernateException {
		Session session = null;
		Transaction tx = null;
		try {
			session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();
			session.update(userOffer);
			tx.commit();
		}catch (JDBCException je) {
			if (tx != null) {
                tx.rollback();
            }
			String exMessage = je.getMessage();
			if (exMessage.indexOf("ORA-00001: unique constraint") > -1) {
				je.printStackTrace();
				throw new UniqueConstraintException();
			}else {
				je.printStackTrace();
				throw new HibernateException(je);
			}
		}catch (HibernateException e) {
			if (tx != null) {
                tx.rollback();
            }
            
			e.printStackTrace();
			throw e;
		}finally {
            if (session != null) {
                session.close();
                log.debug("Session closed in updateUserOffer(AimsUserOffer userOffer)");
            }
		}
	}

	public static void updateUserAllaince(AimsUser user,Session session) throws UniqueConstraintException, HibernateException {
		
		try {
			// Check that the email does not already exists
			session.update(user);
		}catch (JDBCException je) {
			String exMessage = je.getMessage();
			if (exMessage.indexOf("ORA-00001: unique constraint") > -1) {
				je.printStackTrace();
				throw new UniqueConstraintException();
			}else {
				je.printStackTrace();
				throw new HibernateException(je);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw e;
		}
	}


    public static void saveUserOffer(AimsUserOffer userOffer) throws UniqueConstraintException, HibernateException, Exception {
        Session session = null;
        Transaction tx = null;
        try {
            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();

            AimsUserOfferManager.saveUserOffer(userOffer, session);
            tx.commit();
        }
        catch (UniqueConstraintException uce) {
            uce.printStackTrace();
            if( tx!=null) {
                tx.rollback();
            }
            throw uce;
        }
        catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
            he.printStackTrace();
            throw he;
        }
        catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            throw e;
        }
        finally {
            if (session != null) {
                session.close();
                log.debug("Session closed in saveUserOffer(AimsUserOffer userOffer)");
            }
        }
    }//end saveUserOffer

    public static void saveUserOffer(AimsUserOffer userOffer,
                                     Session session) throws UniqueConstraintException, HibernateException, Exception {

        try {
            session.save(userOffer);
        }
        catch (JDBCException je) {
            String exMessage = je.getMessage();
            if (exMessage.indexOf("ORA-00001: unique constraint") > -1) {
                je.printStackTrace();
                throw new UniqueConstraintException();
            }
            else {
                je.printStackTrace();
                throw new HibernateException(je);
            }
        }
        catch (HibernateException he) {
            he.printStackTrace();
            throw he;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        finally {
            log.debug("Session will	be closed by the calling method of saveUserOffer(AimsUserOffer userOffer, Session session) method");
        }
    }//end saveUserOffer


    /**
     * This method returns aimsUserOffers of given alliance and email,
     * where status is accepted or status is invited in last 30 days
     * @param offerToUserEmail
     * @param allianceId
     * @param duration
     * @return
     * @throws HibernateException
     */
    public static Collection getUserAcceptedOrInvitedOffers(String offerToUserEmail, Long allianceId, int duration) throws HibernateException {

        Session session = null;
        Collection userOfferCollection = null;
        Query query = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        try {
                queryStringBuffer
                        .append("select userOffer from com.netpace.aims.model.core.AimsUserOffer userOffer ")
                        .append("   where ")
                        .append("       userOffer.offerTo = :offerToUserEmail ");
            if(allianceId!=null) {
                queryStringBuffer
                        .append("   and userOffer.allianceId = :allianceId ");
            }
                queryStringBuffer
                        .append("   and (   (userOffer.status = 'Accepted') ")
                        .append("       OR (    (userOffer.status = 'Active')")
                        .append("           AND (TRUNC (userOffer.offerDate) > TRUNC (SYSDATE - :duration))")
                        .append("           )")
                        .append("       )");
                queryStringBuffer
                        .append("   order by userOffer.offerId ");

            log.debug("AimsUserOFferManager.getUserOffers(): query= "+queryStringBuffer.toString());
            session = DBHelper.getInstance().getSession();
            query = session.createQuery(queryStringBuffer.toString());
            query.setString("offerToUserEmail", offerToUserEmail);
            if(allianceId!=null) {
                query.setLong("allianceId", allianceId.longValue());
            }
            query.setInteger("duration", duration);

            userOfferCollection = query.list();
        }
        catch (HibernateException he) {
            he.printStackTrace();
            throw he;
        }
        finally {
            if (session != null) {
                session.close();
                log.debug("session closed in AimsUserOFferManager.getUserOffers()");
            }
        }
        return userOfferCollection;
    }//end getUserOffers

    /**
     * This method returns Active offers by this user
     * @param offerFromEmail
     * @param session
     * @return
     * @throws HibernateException
     * @throws Exception
     */
    public static Collection getUserOffersByOfferFromEmail(String offerFromEmail, Session session) throws HibernateException, Exception {

		Collection userOfferCollection = null;
        Query query = null;
        StringBuffer queryBuffer = new StringBuffer();
        try {
            queryBuffer
                    .append("select ")
                    .append("   from    com.netpace.aims.model.core.AimsUserOffer userOffer ")
                    .append("   where   userOffer.status = :offerStatus ")
                    .append("   and     userOffer.offerFromEmail = :offerFromEmail ");

            query = session.createQuery(queryBuffer.toString());
            query.setString("offerStatus", AimsConstants.ACTIVE);
			query.setString("offerFromEmail", offerFromEmail);

            userOfferCollection = query.list();
		}
        catch (HibernateException he) {
			he.printStackTrace();
			throw he;
		}
        catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
        finally {
			log.debug("Session will be closed by calling method of AimsUserOfferManager.getUserOffersByOfferFromEmail()");
		}
		return userOfferCollection;
	}//end getUserOffersByOfferFromEmail


    public static int revokeOffersByUser(String userEmail, Session session) throws HibernateException, Exception {
        int rowsUpdated = 0;
        log.debug("start revokeOffersByUser, userEmail: "+userEmail);
        Collection<AimsUserOffer> offersByUser = null;
        try {
            offersByUser = AimsUserOfferManager.getUserOffersByOfferFromEmail(userEmail, session);
            if(offersByUser!=null && offersByUser.size()>0) {
                for ( AimsUserOffer userOffer : offersByUser ){
                    userOffer.setStatus(AimsConstants.OFFER_REVOKED);
                    session.update(userOffer);
                }
                rowsUpdated = offersByUser.size();
            }
        }
        catch (HibernateException he) {
			he.printStackTrace();
			throw he;
		}
        catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
        finally {
			log.debug("Session will be closed by calling method of AimsUserOfferManager.revokeOffersByUser(), rowsUpdated= "+rowsUpdated);
		}
        return rowsUpdated;
    }
}
