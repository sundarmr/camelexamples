The Idempotent Consumer project reads records from a file with duplicate records and filters the duplicates based on the JDBC repository

The sample.csv file provided contains below four records

msg1,jon,33,30328@
msg2,doe,33,30328@
msg3,jane,33,30328@
msg3,jane1,33,30328

First Column as Key

msg3,jane,33,30328 will be filtered out , 

First Two Columns as Key all records should be consumed.

Tweak needed
====================

If you are using my sql , please drop the table CAMEL_MESSAGEPROCESSED as orginially created while you run the route and recreate it with the script provided as below

CREATE TABLE CAMEL_MESSAGEPROCESSED (
    id BIGINT NOT NULL AUTO_INCREMENT,
    createdAt datetime,
    messageId VARCHAR(255) NOT NULL,
    processorName VARCHAR (255),
    PRIMARY KEY(ID)
  );
  
  ======================
  
  This feature can be very useful in many enterprise cases where large cumulative files need to be consumed for processing
