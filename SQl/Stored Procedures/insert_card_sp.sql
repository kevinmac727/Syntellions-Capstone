--------------------------------------------------------
--  File created - Thursday-July-05-2018   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Procedure SP_INSERT_CARD
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "DB_USPRING"."SP_INSERT_CARD" (cardID VARCHAR, useID VARCHAR, cardNum VARCHAR2, expDate date, secCode number)
as 
begin
  insert into CARDS
  values (cardID, useID, cardNum, expDate, secCode);  
end;

/
