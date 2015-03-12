use css490pr;

create table users
(
    username varchar(20) primary key,
    f_name varchar(30) not null,
    l_name varchar(30) not null,
    email varchar(50) not null,
    phone int(10),
    passwd char(32) not null,
    signup_date date not null,
    last_login datetime,
    is_staff bool
);

create table books 
(
    isbn varchar(20) primary key,
    title varchar(200) not null,
    publisher varchar(100) not null,
    price decimal(6,2) not null,
    description text,
    publish_date date not null,
    cover_image varchar(100),
    cover_image_thumb varchar(100),
    inventory int(10)
);

create table author 
(
    author_id int(10) auto_increment primary key,
    author_name varchar(100),
    date_of_birth date
);

create table book_author
(
    isbn varchar(20),
    author_id int(10),
    foreign key (isbn)
        references books(isbn)
        on delete cascade
        on update cascade,
    foreign key (author_id)
        references author(author_id)
        on delete cascade
        on update cascade,
    primary key (isbn, author_id)	
);

create table transactions 
(
    transaction_id int(10) auto_increment primary key,
    username varchar(20),
    purchase_date datetime,
    total_price decimal(6,2),
    foreign key (username)
		references customers(username)
);

create table transaction_book 
(
    id int(10) auto_increment primary key,
    isbn varchar(20),
    transaction_id int(10),
    sale_price decimal(6,2),
    is_returned bool,
    foreign key (isbn)
        references books(isbn),
    foreign key (transaction_id)
        references transactions(transaction_id)
);


create table rating 
(
    username varchar(20),
    isbn varchar(20),
    rating tinyint,
    review_text text,
    foreign key (isbn)
        references books(isbn)
        on delete cascade,
    foreign key (username)
        references users(username)
        on delete cascade,
	primary key (username, isbn)
);

create table genres
(
	genre_name varchar(40) primary key
);

create table genre_book
(
    isbn varchar(20),
	genre_name varchar(40),
    foreign key (isbn)
        references books(isbn)
        on delete cascade
        on update cascade,
	foreign key (genre_name)
        references genres(genre_name)
        on delete cascade
        on update cascade,
	primary key (isbn, genre_name)
);