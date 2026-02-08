
    create table roles (
        created_at datetime(6),
        id bigint not null auto_increment,
        name varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table users (
        is_active bit not null,
        created_at datetime(6) not null,
        id bigint not null auto_increment,
        role_id bigint not null,
        password varchar(255) not null,
        phone varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    alter table roles 
       add constraint UKofx66keruapi6vyqpv6f2or37 unique (name);

    alter table users 
       add constraint UKdu5v5sr43g5bfnji4vb8hg5s3 unique (phone);

    alter table users 
       add constraint FKp56c1712k691lhsyewcssf40f 
       foreign key (role_id) 
       references roles (id);
