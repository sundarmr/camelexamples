The Idempotent Consumer project reads records from a file which can have duplicated and filters the duplicates based on the JDBC repository

The sample.csv container four records

msg1,jon,33,30328@
msg2,doe,33,30328@
msg3,jane,33,30328@
msg3,jane1,33,30328

We consider the first column as key and then the last records

msg3,jane,33,30328

will be filtered out , 

if we consider the first two columns as key then all the records 

will be consumed


Tweak needed

If you are using my sql , please drop the table CAMEL_MESSAGEPROCESSED as orginially created while you run the route and recreate it with the script provided as below

CREATE TABLE CAMEL_MESSAGEPROCESSED (
    id BIGINT NOT NULL AUTO_INCREMENT,
    createdAt datetime,
    messageId VARCHAR(255) NOT NULL,
    processorName VARCHAR (255),
    PRIMARY KEY(ID)
  );
