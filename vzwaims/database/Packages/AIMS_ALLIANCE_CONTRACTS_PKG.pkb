CREATE OR REPLACE Package Body      AIMS_ALLIANCE_CONTRACTS_PKG
IS

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE ins_alliance_cont_amendments
         ( p_contract_id            IN  number,             -- alliance_id of the alliance to be deleted
           p_amendment_ids          IN  varchar2,           -- comma seperated list of amendment ids
           p_curr_user_name         IN  varchar2            -- the username of the person creating the amendment
          )
    IS

    /*
    || Overview:        Inserts the give amendments against the alliance contracts
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 8-05-2004       rqazi           Created
    ||
    ||
    ||
    ||
    */

        v_alliance_user_ids     varchar2(32767);
        v_alliance_contact_ids  varchar2(32767);

        v_amendments_array      DBMS_UTILITY.UNCL_ARRAY;

        v_cnt_amendments        number;

   BEGIN


        if (INSTR(p_amendment_ids,',') > 0) then
            PARSE.delimstring_to_table(p_amendment_ids, v_amendments_array, v_cnt_amendments, ',');
        elsif (LENGTH(p_amendment_ids) > 0) then
            v_amendments_array(1) := p_amendment_ids;
            v_cnt_amendments := 1;
        end if;


        if (v_cnt_amendments > 0) then

            for i IN 1..v_amendments_array.count loop
                if(length(trim(v_amendments_array(i))) > 0) then


                        for c in (select alliance_contract_id from aims_alliance_contracts
                                    where contract_id = p_contract_id) loop
                            begin

                                insert into aims_alliance_contract_amends (alliance_contract_id, addendum_id, status,
                                                                            vzw_amendment_present_date, created_by, created_date)
                                    values (c.alliance_contract_id, v_amendments_array(i), 'O', sysdate, p_curr_user_name, sysdate);

                            exception
                                when dup_val_on_index then
                                    null;
                            end;

                        end loop;
                end if;
            end loop;

        end if;

   END ins_alliance_cont_amendments;

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE ins_cont_level_amend_for_alncs
         ( p_contract_id            IN  number,             -- contract_id
           p_alliance_contract_id   IN  number,             -- alliance_contract_id
           p_curr_user_name         IN  varchar2            -- the username of the person creating the amendment
          )
    IS

    /*
    || Overview:        Inserts the contract level amendments against the alliance_contract_id (for a single alliance)
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 8-05-2004       rqazi           Created
    ||
    ||
    ||
    ||
    */

   BEGIN

          for c in (select addendum_id from aims_contract_amendments
                      where contract_id = p_contract_id) loop
              begin

                  insert into aims_alliance_contract_amends (alliance_contract_id, addendum_id, status,
                                                              vzw_amendment_present_date, created_by, created_date)
                      values (p_alliance_contract_id, c.addendum_id, 'O', sysdate, p_curr_user_name, sysdate);

              exception
                  when dup_val_on_index then
                      null;
              end;

        end loop;


   END ins_cont_level_amend_for_alncs;

/* -------------------------------------------------------------------------------------------------------------  */

END AIMS_ALLIANCE_CONTRACTS_PKG; -- Package Body AIMS_ALLIANCE_CONTRACTS_PKG
/

