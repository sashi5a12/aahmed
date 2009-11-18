CREATE OR REPLACE function get_deck_placement_id (p_deck_placement varchar2)
return number is

p_id number;
begin


    begin
        select
            DECK_ID
        into
            p_id
        from
            AIMS_DECKS
        where
            DECK_NAME = p_deck_placement;

    return p_id;
    exception
    when no_data_found then
        return null;
    end;

end get_deck_placement_id;
/

