REGISTER ./UDF/target/UDF-1.0.0.jar;

A = LOAD './inputWithinTheSameCampaign.dat' AS (userID:chararray, time_impression:chararray, time_click:chararray, bannerID:int);

B =FOREACH A GENERATE userID, (time_impression, time_click) AS time, bannerID; 

C = FOREACH B GENERATE userID, com.brainpage.clustering.TimeSubstraction(time) AS relative_time, bannerID;

D = GROUP C BY userID;

E = FOREACH D GENERATE C;

STORE E INTO 'groupByUserID';


