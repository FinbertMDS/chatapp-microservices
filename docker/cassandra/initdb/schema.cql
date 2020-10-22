CREATE KEYSPACE IF NOT EXISTS db_messages WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

CREATE TABLE IF NOT EXISTS db_messages.security_tokens(
    series text,
    token_date timestamp,
    authorities set<text>,
    user_agent text,
    token_value text,
    login text,
    pass text,
    ip_address text,
    PRIMARY KEY(series)
);

CREATE TABLE IF NOT EXISTS db_messages.users(
    user_id uuid,
    login text,
    bio text,
    email text,
    chat_rooms set<text>,
    lastname text,
    firstname text,
    pass text,
    PRIMARY KEY(user_id)
);

CREATE TABLE IF NOT EXISTS db_messages.chat_rooms(
    room_id uuid,
    room_name text,
    creation_date timestamp,
    creator_login text,
    participants set<text>,
    banner text,
    creator text,
    PRIMARY KEY(room_id)
);

CREATE TABLE IF NOT EXISTS db_messages.chat_room_messages(
    room_id uuid,
    message_id uuid,
    content text,
    author text,
    system_message boolean,
    PRIMARY KEY(room_id, message_id)
) WITH CLUSTERING ORDER BY (message_id DESC);