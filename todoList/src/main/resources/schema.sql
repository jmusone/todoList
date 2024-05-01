create table if not exists todo (
    id uuid primary key default gen_random_uuid(),
    description varchar,
    status varchar,
    dueDate timestamp
);