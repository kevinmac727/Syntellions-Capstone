--------------------------------------------------------
--  File created - Thursday-July-05-2018   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Procedure SP_UPDATE_CARD
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "DB_USPRING"."SP_UPDATE_CARD" (cardID VARCHAR, useID VARCHAR, cardNum VARCHAR2, expDate date, secCode number)
as 
begin
  update CARDS
  set USER_ID = useID, CARD_NUMBER = cardNum, EXPIRY_DATE = expDate, SECURITY_CODE = secCode
  where CARD_ID = cardID;
end;

/
