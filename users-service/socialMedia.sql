CREATE TABLE "users" (
  "id" integer PRIMARY KEY,
  "username" varchar,
  "password" varchar,
  "email" varchar,
  "profile_picture" varchar,
  "bio" text,
  "created_at" timestamp
);

CREATE TABLE "posts" (
  "id" integer PRIMARY KEY,
  "body" text,
  "user_id" integer,
  "created_at" timestamp
);

CREATE TABLE "comments" (
  "id" integer PRIMARY KEY,
  "post_id" integer,
  "user_id" integer,
  "content" text,
  "created_at" timestamp
);

CREATE TABLE "likes" (
  "id" integer PRIMARY KEY,
  "post_id" integer,
  "user_id" integer,
  "created_at" timestamp
);

CREATE TABLE "followers" (
  "following_user_id" integer,
  "followed_user_id" integer,
  "created_at" timestamp,
  "id" integer PRIMARY KEY
);

COMMENT ON COLUMN "posts"."body" IS 'Content of the post';

ALTER TABLE "posts" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "comments" ADD FOREIGN KEY ("post_id") REFERENCES "posts" ("id");

ALTER TABLE "comments" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "likes" ADD FOREIGN KEY ("post_id") REFERENCES "posts" ("id");

ALTER TABLE "likes" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "followers" ADD FOREIGN KEY ("following_user_id") REFERENCES "users" ("id");

ALTER TABLE "followers" ADD FOREIGN KEY ("followed_user_id") REFERENCES "users" ("id");
