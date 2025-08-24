create table artist(
                       id bigserial primary key,
                       name varchar(255) not null unique
);

create table track(
                      id bigserial primary key,
                      title varchar(255) not null,
                      artist_id bigint not null references artist(id),
                      constraint uq_track unique(title, artist_id)
);

create table play(
                     id bigserial primary key,
                     track_id bigint not null references track(id),
                     played_at timestamp not null,
                     source varchar(64) not null,
                     constraint uq_play unique(track_id, played_at)
);

create index idx_play_played_at on play(played_at);
