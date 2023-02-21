BEGIN TRANSACTION;

DROP TABLE IF EXISTS users, groups, user_group, lists CASCADE;
DROP SEQUENCE IF EXISTS seq_group_id;


CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE SEQUENCE seq_group_id
  INCREMENT BY 1
  START WITH 330
  NO MAXVALUE;

CREATE TABLE groups (
    group_id int NOT NULL DEFAULT nextval('seq_group_id'),
    group_code varchar(200) UNIQUE,
    group_name varchar(100) NOT NULL UNIQUE,
    owner_id INTEGER NOT NULL,
    CONSTRAINT PK_group PRIMARY KEY (group_id),
    CONSTRAINT FK_group_owner FOREIGN KEY (owner_id) REFERENCES users (user_id)
);

CREATE TABLE user_group (
    group_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    CONSTRAINT PK_user_group PRIMARY KEY (user_id, group_id),
    CONSTRAINT FK_user_group_groups FOREIGN KEY (group_id) REFERENCES groups(group_id),
    CONSTRAINT FK_user_group_users FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE lists (
    list_id SERIAL,
    list_name varchar(100) NOT NULL UNIQUE,
    group_id INTEGER NOT NULL,
    CONSTRAINT PK_list PRIMARY KEY (list_id),
    CONSTRAINT FK_list_group FOREIGN KEY (group_id) REFERENCES groups (group_id)
);

CREATE TABLE items (
    list_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    item_id SERIAL,
    item_name varchar(100) NOT NULL UNIQUE,
    item_category varchar(100),
    item_quantity int DEFAULT 1,
    CONSTRAINT PK_item PRIMARY KEY (item_id),
    CONSTRAINT FK_item_list FOREIGN KEY (list_id) REFERENCES lists (list_id)
);

COMMIT TRANSACTION;

BEGIN TRANSACTION;

DROP TABLE IF EXISTS invitation;
DROP SEQUENCE IF EXISTS seq_invitation_id;

-- Sequence to start user_id values at 1001 instead of 1
CREATE SEQUENCE seq_invitation_id
  INCREMENT BY 1
  START WITH 3001
  NO MAXVALUE;

CREATE TABLE invitations (
	invitation_id int NOT NULL DEFAULT nextval('seq_invitation_id'),
	group_id int NOT NULL,
	sender varchar(200) NOT NULL,
	recipient varchar(200) NOT NULL,
	status varchar(200) NOT NULL,
	CONSTRAINT PK_invitation PRIMARY KEY (invitation_id),
	CONSTRAINT FK_invitation_group FOREIGN KEY (group_id) REFERENCES groups(group_id),
	CONSTRAINT FK_invitation_sender FOREIGN KEY (sender) REFERENCES users(username),
	CONSTRAINT FK_invitation_recipient FOREIGN KEY (recipient) REFERENCES users(username),
	CONSTRAINT FK_invitation_status FOREIGN KEY (status) REFERENCES invitation_status(status),
);

COMMIT;
