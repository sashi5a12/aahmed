CREATE OR REPLACE PACKAGE BODY Aims_Bulletin_Pkg
IS


   PROCEDURE get_bulletins_to_show (
      p_user_id           IN       NUMBER,  -- username.
      p_out_bulletin_id   OUT      VARCHAR2 -- Result Bulletin ID of active bulletin, 0(zero) otherwise.
   )

   IS
      /*
      || Overview:        Checks if the user has read the bulletin, the desired times.
      ||
      ||
      || Dependencies:
      ||
      || Modification History:
      || When             Who             What.         Comments.
      ||--------------------------------------------------------------
      || 03-24-2006       amakda          Created.
      || 11-19-2007       mshiraz         Modified.      Added OFFERED Functionally.
      ||
      ||
      ||
      */

      v_user_id    NUMBER   := p_user_id;
      v_platform   AIMS_CONTRACTS.platform_id%TYPE;
      v_cnt        NUMBER;
      l_str        VARCHAR2 (4000) DEFAULT NULL;
      l_sep        VARCHAR2 (5)    DEFAULT NULL;

   BEGIN
      FOR v_cursor IN  (
                          SELECT ac.platform_id platform, COUNT (*) cnt
                            FROM AIMS_USERS au,
                                 AIMS_CONTRACTS ac,
                                 AIMS_ALLIANCE_CONTRACTS aac
                           WHERE au.alliance_id = aac.alliance_id
                             AND aac.contract_id = ac.contract_id
                             AND au.user_id = v_user_id
                             AND aac.status = 'ACCEPTED'
                             AND ac.platform_id = 1
                        GROUP BY ac.platform_id
                        UNION ALL
                          SELECT ac.platform_id platform, COUNT (*) cnt
                            FROM AIMS_USERS au,
                                 AIMS_CONTRACTS ac,
                                 AIMS_ALLIANCE_CONTRACTS aac
                           WHERE au.alliance_id = aac.alliance_id
                             AND aac.contract_id = ac.contract_id
                             AND au.user_id = v_user_id
                             AND aac.status = 'OFFERED'
                             AND ac.contract_id = 288
                             AND ac.platform_id = 5
                        GROUP BY ac.platform_id)

      LOOP

         v_platform := v_cursor.platform; v_cnt := v_cursor.cnt;

         IF (v_platform = 1 AND NVL (v_cnt, 0) > 0) THEN

            FOR c_1 IN (
                        SELECT s.bulletin_id bulletin_id
                          FROM AIMS_BULLETINS s
                         WHERE s.is_active = 'Y'
                           AND TRUNC (s.expiry_date) > TRUNC (SYSDATE)
                           AND s.bulletin_type = 1
                           ORDER BY bulletin_id ) 
            LOOP
                           
                            FOR c_11 IN ( 
                                         SELECT COUNT (*) cnt
                                           FROM AIMS_BULLETIN_USERS
                                          WHERE bulletin_id = c_1.bulletin_id
                                            AND user_id = v_user_id
                                            AND views_remaining <= 0) 
                           
                     LOOP
                
                        IF (c_11.cnt = 0) THEN
                            l_str := l_str||l_sep||c_1.bulletin_id; l_sep := ',';
                        END IF;
            
                     END LOOP;
            
            END LOOP;

         ELSIF (v_platform = 5 AND NVL (v_cnt, 0) > 0) THEN

            FOR c_2 IN  (
                       SELECT bulletin_id
                         FROM AIMS_BULLETINS
                        WHERE is_active = 'Y'
                          AND TRUNC (expiry_date) > TRUNC (SYSDATE)
                          AND bulletin_type = 2)
            LOOP
               l_str := l_str || l_sep || c_2.bulletin_id; l_sep := ',';
            END LOOP;

        END IF;

     END LOOP;

   p_out_bulletin_id := l_str;

END get_bulletins_to_show;

/* -------------------------------------------------------------------------------------------------------------  */

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE check_if_bulletin_viewed
        ( p_user_id               IN   NUMBER,  -- username.
          p_bulletin_id           OUT  NUMBER  -- Bulletin ID of active bulletin, 0(zero) otherwise.
        )

     IS

     /*
     || Overview:        Checks if the user has read the bulletin, the desired times.
     ||
     ||
     || Dependencies:
     ||
     || Modification History:
     || When             Who             What.
     ||---------------------------------------
     || 03-24-2006       amakda          Created.
     ||
     ||
     ||
     ||
     */
            v_active_bulletin   NUMBER;
            v_viewed_all        NUMBER;
   v_brew_user         NUMBER;


        BEGIN

            p_bulletin_id := 0;

            SELECT
                COUNT(*)
            INTO
                v_brew_user
            FROM
                AIMS_USERS au, AIMS_CONTRACTS ac, AIMS_ALLIANCE_CONTRACTS aac
            WHERE
                au.alliance_id = aac.alliance_id
                AND aac.contract_id = ac.contract_id
                AND au.user_id = p_user_id
                AND aac.status = 'ACCEPTED'
                AND ac.platform_id = 1;


            IF (v_brew_user > 0) THEN
                BEGIN
                    SELECT
                        bulletin_id
                    INTO
                        v_active_bulletin
                    FROM
                        AIMS_BULLETINS
                    WHERE
                        is_active = 'Y'
                        AND TRUNC(expiry_date) > TRUNC(SYSDATE);


                    SELECT
                        COUNT(*)
                    INTO
                        v_viewed_all
                    FROM
                        AIMS_BULLETIN_USERS
                    WHERE
                        bulletin_id = v_active_bulletin
                        AND user_id = p_user_id
                        AND views_remaining <= 0;

                    --If not viewed, return bulletin_id of active bulletin.
                    IF (v_viewed_all = 0) THEN
                        p_bulletin_id := v_active_bulletin;
                    END IF;

                EXCEPTION
                    WHEN NO_DATA_FOUND THEN
                       NULL;--INS_SQL('No Active Bulletin');
                END;
            END IF;

    END check_if_bulletin_viewed;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE update_bulletin_counter
        ( p_user_id                IN  NUMBER,  -- username.
          p_bulletin_id            IN  NUMBER   -- bulletin ID.
        )

     IS

     /*
     || Overview:       Updates the count of the times the user has read the bulletin.
     ||
     ||
     || Dependencies:
     ||
     || Modification History:
     || When             Who             What.
     ||---------------------------------------
     || 03-24-2006       amakda          Created.
     ||
     ||
     ||
     ||
     */

            v_active_bulletin   NUMBER;
            const_max_views     CONSTANT NUMBER := 2;


        BEGIN

            BEGIN
                SELECT
                    bulletin_id
                INTO
                    v_active_bulletin
                FROM
                    AIMS_BULLETINS
                WHERE
                    is_active = 'Y'
                    AND TRUNC(expiry_date) > TRUNC(SYSDATE)
                    AND bulletin_id = p_bulletin_id;


                IF (v_active_bulletin = p_bulletin_id) THEN
                    BEGIN
                        INSERT INTO AIMS_BULLETIN_USERS
                                            (
                                                bulletin_id,
                                                user_id,
                                                views_remaining,
                                                last_viewed_date
                                            )
                                            VALUES
                                            (
                                                v_active_bulletin,
                                                p_user_id,
                                                const_max_views - 1,
                                                SYSDATE
                                            );

                    EXCEPTION
                        WHEN DUP_VAL_ON_INDEX THEN
                            BEGIN
                                UPDATE
                                    AIMS_BULLETIN_USERS
                                SET
                                    views_remaining = views_remaining - 1,
                                    last_viewed_date = SYSDATE
                                WHERE
                                    bulletin_id = v_active_bulletin
                                    AND user_id = p_user_id;
                            END;
                    END;
                END IF;

            EXCEPTION
                WHEN NO_DATA_FOUND THEN
                    NULL;--INS_SQL('No Active Bulletin');

        END;

    END update_bulletin_counter;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE update_bulletin_viewed_count
        ( p_user_id                IN  NUMBER,  -- username.
          p_bulletin_id            IN  NUMBER   -- bulletin ID.
        )

     IS

     /*
     || Overview:       Updates the count of the times the user has read the bulletin.
     ||
     ||
     || Dependencies:
     ||
     || Modification History:
     || When             Who             What.
     ||---------------------------------------
     || 03-24-2006       amakda          Created.
     ||
     ||
     ||
     ||
     */

            v_active_bulletin   NUMBER;
            const_max_views     CONSTANT NUMBER := 2;


        BEGIN

            BEGIN
                SELECT
                    bulletin_id
                INTO
                    v_active_bulletin
                FROM
                    AIMS_BULLETINS
                WHERE
                    is_active = 'Y'
                    AND TRUNC(expiry_date) > TRUNC(SYSDATE);



                IF (v_active_bulletin = p_bulletin_id) THEN
                    BEGIN
                        INSERT INTO AIMS_BULLETIN_USERS
                                            (
                                                bulletin_id,
                                                user_id,
                                                views_remaining,
                                                last_viewed_date
                                            )
                                            VALUES
                                            (
                                                v_active_bulletin,
                                                p_user_id,
                                                const_max_views - 1,
                                                SYSDATE
                                            );

                    EXCEPTION
                        WHEN DUP_VAL_ON_INDEX THEN
                            BEGIN
                                UPDATE
                                    AIMS_BULLETIN_USERS
                                SET
                                    views_remaining = views_remaining - 1,
                                    last_viewed_date = SYSDATE
                                WHERE
                                    bulletin_id = v_active_bulletin
                                    AND user_id = p_user_id;
                            END;
                    END;
                END IF;

            EXCEPTION
                WHEN NO_DATA_FOUND THEN
                    NULL;--INS_SQL('No Active Bulletin');

        END;

    END update_bulletin_viewed_count;



END Aims_Bulletin_Pkg; -- Package Body AIMS_BULLETIN_PKG.
/

