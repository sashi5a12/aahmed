CREATE OR REPLACE Package Body BDS_MVNFORUM
IS

/* -------------------------------------------------------------------------------------------------------------  */

    PROCEDURE sync_mvnforum_account
         ( p_bds_user_id                 IN  number,             -- bds user id whose account is to be updated
           p_trans_type                  IN  varchar2            -- 'I' add  'U' update 'D' delete           
          )
		  
    IS

    /*
    || Overview:        Updates a given user account to the database
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 05-13-2004       amakda           Created
    ||
    ||
    ||
    || 
    */

        v_mvnforum_user_id number;
		v_bds_role_id number;

   BEGIN

        if (p_trans_type = 'D') then
            update bdsforum.mvnforummember 
			set memberstatus = 1
			where memberid = p_bds_user_id;
        end if;
        
        if (p_trans_type = 'I') then
		
  			insert into bdsforum.mvnforummember  
                (memberid, membername, memberpassword, memberfirstemail, memberemail, membercreationdate, membermodifieddate, memberlastlogon, memberfirstname, memberlastname)
            select
                b.user_id, b.username, b.password, b.username, b.username, b.created_date, b.created_date, b.created_date, c.first_name, c.last_name 
                    from bds_users b, bds_contacts c 
					where b.contact_id = c.contact_id 
					and b.user_id = p_bds_user_id and b.user_type = 'V';
			
			insert into bdsforum.mvnforummessagefolder
				(foldername, memberid, folderorder, foldercreationdate, foldermodifieddate)
			values
				('Inbox', p_bds_user_id, 0, sysdate, sysdate);    		
				
			insert into bdsforum.mvnforummessagefolder
				(foldername, memberid, folderorder, foldercreationdate, foldermodifieddate)
			values
				('Sent', p_bds_user_id, 0, sysdate, sysdate);    		
			
			begin
			select role_id into v_bds_role_id from 
				   bds_user_roles where role_id = 3 and user_id = p_bds_user_id;
		    
			     begin
				 insert into bdsforum.mvnforummemberpermission
				     (memberid, permission)
			     values
				     (p_bds_user_id, 100);
				 
				 exception
				 when dup_val_on_index then
				     null;
			     end;
				 
		    exception
			when no_data_found then
			       delete from bdsforum.mvnforummemberpermission where memberid = p_bds_user_id;
			end;
			             	
        end if;

        if (p_trans_type = 'U') then
          begin
            select f.memberid into v_mvnforum_user_id from bdsforum.mvnforummember f 
		            where f.memberid = p_bds_user_id and rownum = 1;
        
            update bdsforum.mvnforummember set
                (membername, memberpassword, memberfirstemail, memberemail, membermodifieddate, memberfirstname, memberlastname) =
                (select
                 b.username, b.password, b.username, b.username, b.created_date, c.first_name, c.last_name 
                    from bds_users b, bds_contacts c 
					where b.contact_id = c.contact_id 
					and b.user_id = p_bds_user_id and b.user_type = 'V')
            where memberid = p_bds_user_id;               
			
			begin
			select role_id into v_bds_role_id from 
				   bds_user_roles where role_id = 3 and user_id = p_bds_user_id;
		    
			     begin
				 insert into bdsforum.mvnforummemberpermission
				     (memberid, permission)
			     values
				     (p_bds_user_id, 100);
				 
				 exception
				 when dup_val_on_index then
				     null;
			     end;
				
		    exception
			when no_data_found then
			       delete from bdsforum.mvnforummemberpermission where memberid = p_bds_user_id;
			end;
				  
        exception
            when no_data_found then
                sync_mvnforum_account(p_bds_user_id, 'I');
        end;                 
        end if; 

   END sync_mvnforum_account;

/* -------------------------------------------------------------------------------------------------------------  */

END BDS_MVNFORUM; -- Package Body BDS_MVNFORUM
/

