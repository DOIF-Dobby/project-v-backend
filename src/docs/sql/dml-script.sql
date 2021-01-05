select * from user;
select * from role;
select * from user_role;
select * from role_resource;

select * from resource;
select * from resource_authority;
select * from resource_button;


update user
set created_by = 'system',
    last_modified_by = 'system';

update role
set created_by = 'system',
    last_modified_by = 'system';

update user_role
set created_by = 'system',
    last_modified_by = 'system';

update resource
set created_by = 'system',
    last_modified_by = 'system';

update role_resource
set created_by = 'system',
    last_modified_by = 'system';

