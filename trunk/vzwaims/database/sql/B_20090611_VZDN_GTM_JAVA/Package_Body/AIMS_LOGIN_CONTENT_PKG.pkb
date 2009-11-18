CREATE OR REPLACE PACKAGE BODY aims_login_content_pkg
IS
   PROCEDURE get_login_content_to_show (
      p_user_id                IN       NUMBER, -- username.
      p_alliance_id            IN       NUMBER, -- Alliance ID.
      p_days                   IN       NUMBER, -- Days.
      p_out_login_content_id   OUT      VARCHAR2 -- Result Login Content ID.
   )
   IS
      /*
      || Overview:        Checks if the user has acknowledged login content, if not or acknowledged before given number of days then show login content
      ||
      ||
      || Dependencies:
      ||
      || Modification History:
      || When             Who             What.         Comments.
      ||--------------------------------------------------------------
      || 05-30-2008       mshiraz         Created.
      || 04-29-2009       mshiraz         Modified.     Add the Company info. functionally.
      ||
      ||
      */

      v_user_id     NUMBER          := p_user_id;
      v_alliance_id NUMBER          := p_alliance_id;
      v_cnt         NUMBER;
      l_str         VARCHAR2 (4000) DEFAULT NULL;
      l_sep         VARCHAR2 (5)    DEFAULT NULL;
   BEGIN
      FOR c_1 IN  (SELECT * FROM 
                        (SELECT login_content_id,login_content_type typ,sort_order ord, COUNT (*) cnt
                           FROM aims_login_content
                          WHERE is_active = 'Y'
                       GROUP BY login_content_id,login_content_type,sort_order )
                       ORDER BY ORD)
      LOOP
         v_cnt := c_1.cnt;

         IF (NVL (v_cnt, 0) > 0) THEN
         
          IF c_1.typ = 2 THEN
         
            FOR c_3 IN (SELECT is_jma_alliance, is_jma_info_complete
                          FROM aims_alliances
                         WHERE upper(is_jma_alliance) = 'Y'
                           AND alliance_id = v_alliance_id)
            LOOP
               IF (c_3.is_jma_info_complete IS NULL OR c_3.is_jma_info_complete<> 'Y') THEN
                  l_str :=  l_str || l_sep || c_1.login_content_id;
                  l_sep := ',';
               END IF;
             END LOOP;      
         
          ELSIF c_1.typ = 1 THEN  

            FOR c_4 IN  (SELECT COUNT (*) cnt
                           FROM aims_alliances 
                          WHERE alliance_id = v_alliance_id
                            AND TRUNC (created_date) > TRUNC (SYSDATE - 1))
            LOOP
               IF (c_4.cnt = 0) THEN
                 
                  FOR c_2 IN  (SELECT COUNT (*) cnt
                                 FROM aims_contact_update_alliances 
                                WHERE alliance_id = v_alliance_id
                                  AND login_content_id = c_1.login_content_id
                                  AND TRUNC (last_ack_date) > TRUNC (SYSDATE - p_days))
                  LOOP
                     IF (c_2.cnt = 0) THEN
                       l_str :=  l_str || l_sep || c_1.login_content_id;
                       l_sep := ',';
                     END IF;
                  END LOOP;                  
               END IF;
            END LOOP;
            
            
         END IF;                     
         END IF;
      END LOOP;

      p_out_login_content_id := l_str;

   END get_login_content_to_show;


/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE update_alliance_contact_ack
        ( 
          p_login_content_id       IN  NUMBER,  -- Content ID.
          p_alliance_id            IN  NUMBER,  -- Alliance ID.
          p_user_name              IN  VARCHAR2 -- userid name
        )

     IS

     /*
     || Overview:       Updates the ack date when user (alliance admin) updates its contact info, if not present then add record for that alliance .
     ||
     ||
     || Dependencies:
     ||
     || Modification History:
     || When             Who             What.
     ||---------------------------------------
     || 05-30-2008       sajjad         Created.
     ||
     ||
     ||
     ||
     */

         BEGIN

                        INSERT INTO aims_contact_update_alliances
                                            (
                                                login_content_id,
                                                alliance_id,
                                                last_ack_by,
                                                last_ack_date,
                                                created_by,
                                                created_date
                                            )
                                            VALUES
                                            (
                                                p_login_content_id ,
                                                p_alliance_id,
                                                p_user_name,
                                                sysdate,
                                                p_user_name,
                                                sysdate
                                            );

                    EXCEPTION
                        WHEN DUP_VAL_ON_INDEX THEN
                            BEGIN
                                UPDATE aims_contact_update_alliances
                                   SET last_ack_by   = p_user_name,
                                       last_ack_date = sysdate
                                 WHERE login_content_id = p_login_content_id
                                   AND alliance_id = p_alliance_id;
                            END;

    END update_alliance_contact_ack;


END aims_login_content_pkg; -- Package Body aims_login_content_pkg.

