drop table app_cluster_locktable;

create table app_cluster_locktable(
	application_name varchar(250),
	server_name varchar(250),
	TIME bigint
)
