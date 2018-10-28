-- UNCOMMENT BELOW TO SET UP DATABASE --
-- CREATE ROLE nc WITH LOGIN PASSWORD 'qwerty';
-- CREATE DATABASE nc_learn WITH OWNER = nc ENCODING = 'UTF8';
-- ////////////////////////////////// --

-- Execute everything below this line in database 'nc_learn'
--- Clearing schema
DROP VIEW IF EXISTS certificates;
DROP VIEW IF EXISTS template_lessons;

DROP TABLE IF EXISTS mc_question_variants;
DROP TABLE IF EXISTS question_attempts;
DROP TABLE IF EXISTS questions;
DROP TABLE IF EXISTS assignments;
DROP TABLE IF EXISTS lectures;
DROP TABLE IF EXISTS lesson_messages;
DROP TABLE IF EXISTS lessons;
DROP TABLE IF EXISTS course_week_dates;
DROP TABLE IF EXISTS weeks;

DROP TABLE IF EXISTS course_groups;
DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS templates;

DROP TABLE IF EXISTS group_messages;
DROP TABLE IF EXISTS group_users;
DROP TABLE IF EXISTS groups;

DROP TABLE IF EXISTS user_infos;
DROP TABLE IF EXISTS users;

DROP TABLE IF EXISTS files;
DROP TABLE IF EXISTS images;

--- IMAGES AND FILES ---
CREATE TABLE images (
  id        BIGINT PRIMARY KEY,
  name      VARCHAR(50) NOT NULL,
  extension VARCHAR(5)  NOT NULL,
  bytes     TEXT        NOT NULL
);

-- if bytes are null, then url is not null
CREATE TABLE files (
  id        BIGINT PRIMARY KEY,
  name      VARCHAR(50) NOT NULL,
  extension VARCHAR(5)  NOT NULL,
  bytes     TEXT,
  url       TEXT
);
--- // IMAGES AND FILES ---

--- USERS ---
CREATE TABLE users (
  id            BIGINT PRIMARY KEY,
  email         VARCHAR(50) UNIQUE NOT NULL,
  password_hash TEXT               NOT NULL,
  role          VARCHAR(10)          NOT NULL
);

-- FOREIGN KEY (b, c) REFERENCES other_table (c1, c2)
-- PRIMARY KEY (product_no, order_id)
CREATE TABLE user_infos (
  user_id       BIGINT PRIMARY KEY REFERENCES users (id) ON DELETE CASCADE,
  nickname      TEXT,
  first_name    VARCHAR(20),
  last_name     VARCHAR(20),
  date_of_birth DATE,
  city          VARCHAR(30),
  avatar_id     BIGINT REFERENCES images (id) ON DELETE SET NULL
);
--- // USERS ---

--- GROUPS ---
CREATE TABLE groups (
  id          BIGINT PRIMARY KEY,
  curator_id  BIGINT REFERENCES users (id) ON DELETE SET NULL,
  name        VARCHAR(30) NOT NULL,
  description TEXT,
  avatar_id   BIGINT REFERENCES images (id) ON DELETE SET NULL
);

CREATE TABLE group_users (
  user_id  BIGINT REFERENCES users (id) ON DELETE CASCADE,
  group_id BIGINT REFERENCES groups (id) ON DELETE CASCADE,
  PRIMARY KEY (user_id, group_id)
);

CREATE TABLE group_messages (
  id        BIGINT PRIMARY KEY,
  sender_id BIGINT    NOT NULL REFERENCES users (id) ON DELETE CASCADE,
  group_id  BIGINT    NOT NULL REFERENCES groups (id) ON DELETE CASCADE,
  message   TEXT      NOT NULL,
  time      TIMESTAMP NOT NULL DEFAULT now()
);
--- // GROUPS ---

--- COURSE TEMPLATES ---
CREATE TABLE templates (
  id           BIGINT PRIMARY KEY,
  teacher_id   BIGINT REFERENCES users (id) ON DELETE SET NULL,
  name         VARCHAR(30) NOT NULL,
  is_completed BOOLEAN     NOT NULL DEFAULT FALSE,
  description  TEXT,
  avatar_id    BIGINT REFERENCES images (id) ON DELETE SET NULL
);
--- // COURSE TEMPLATES ---

--- COURSES ---
CREATE TABLE courses (
  id                BIGINT PRIMARY KEY,
  template_id       BIGINT REFERENCES templates (id) ON DELETE SET NULL,
  name              VARCHAR(30) NOT NULL,
  description       TEXT,
  avatar_id         BIGINT REFERENCES images (id) ON DELETE SET NULL,
  start_date        DATE        NOT NULL                                            DEFAULT now(),
  end_date          DATE        NOT NULL,
  pass_percent      INTEGER     NOT NULL
    CHECK (pass_percent >= 0 AND pass_percent <= 100)                               DEFAULT 60,
  good_percent      INTEGER     NOT NULL
    CHECK (good_percent > pass_percent AND good_percent <= 100)                     DEFAULT 75,
  excellent_percent INTEGER     NOT NULL
    CHECK (excellent_percent > good_percent AND excellent_percent <= 100)           DEFAULT 90
);

CREATE TABLE course_groups (
  group_id  BIGINT NOT NULL REFERENCES groups (id) ON DELETE CASCADE,
  course_id BIGINT NOT NULL REFERENCES courses (id) ON DELETE CASCADE,
  PRIMARY KEY (group_id, course_id)
);
--- // COURSES ---

--- WEEKS ---
CREATE TABLE weeks (
  id          BIGINT PRIMARY KEY,
  template_id BIGINT  NOT NULL REFERENCES templates (id) ON DELETE CASCADE,
  week_number INTEGER NOT NULL,
  name        VARCHAR(15)
);

CREATE TABLE course_week_dates (
  course_id  BIGINT NOT NULL REFERENCES courses (id) ON DELETE CASCADE,
  week_id    BIGINT NOT NULL REFERENCES weeks (id) ON DELETE CASCADE,
  start_date DATE   NOT NULL,
  end_date   DATE   NOT NULL,
  PRIMARY KEY (course_id, week_id)
);
--- // WEEKS ---

--- LESSONS ---
CREATE TABLE lessons (
  id       BIGINT PRIMARY KEY,
  week_id  BIGINT  NOT NULL REFERENCES weeks (id) ON DELETE CASCADE,
  ordering INTEGER NOT NULL
);

CREATE TABLE lectures (
  lesson_id    BIGINT PRIMARY KEY REFERENCES lessons (id) ON DELETE CASCADE,
  lecture_text TEXT,
  file_id      BIGINT REFERENCES files (id) ON DELETE SET NULL
);

CREATE TABLE assignments (
  lesson_id BIGINT PRIMARY KEY REFERENCES lessons (id) ON DELETE CASCADE
);

CREATE TABLE questions (
  id            BIGINT PRIMARY KEY,
  assignment_id BIGINT        NOT NULL REFERENCES assignments (lesson_id) ON DELETE CASCADE,
  ordering      INTEGER       NOT NULL,
  question_text TEXT          NOT NULL,
  type          VARCHAR(10)   NOT NULL,
  answer        TEXT,
  points        REAL          NOT NULL DEFAULT 1.0 CHECK (points > 0.0),
  penalty       REAL          NOT NULL DEFAULT 1.0 CHECK (penalty > 0.0 AND penalty <= points)
);

CREATE TABLE lesson_messages (
  id        BIGINT PRIMARY KEY,
  sender_id BIGINT    NOT NULL REFERENCES users (id) ON DELETE CASCADE,
  lesson_id BIGINT    NOT NULL REFERENCES lessons (id) ON DELETE CASCADE,
  message   TEXT      NOT NULL,
  time      TIMESTAMP NOT NULL DEFAULT now()
);

--- MULTIPLE CHOICE ---

CREATE TABLE mc_question_variants (
  id          BIGINT PRIMARY KEY,
  question_id BIGINT  NOT NULL REFERENCES questions (id) ON DELETE CASCADE,
  choice_text TEXT    NOT NULL,
  is_correct  BOOLEAN NOT NULL DEFAULT FALSE
);

--- // MULTIPLE CHOICE ---

CREATE TABLE question_attempts (
  id          BIGINT PRIMARY KEY,
  user_id     BIGINT    NOT NULL REFERENCES users (id) ON DELETE CASCADE,
  course_id   BIGINT    NOT NULL REFERENCES courses (id) ON DELETE CASCADE,
  question_id BIGINT    NOT NULL REFERENCES questions (id) ON DELETE CASCADE,
  answer      TEXT      NOT NULL,
  score       REAL      NOT NULL DEFAULT 0,
  time        TIMESTAMP NOT NULL DEFAULT now()
);
--- // LESSONS ---

--- VIEWS ---
-- CREATE VIEW template_lessons AS
--   SELECT l.id, l.week_id, w.template_id, l.lesson_text, l.file_id
--   FROM lessons l
--          INNER JOIN weeks w ON l.week_id = w.id;
--
--
-- CREATE VIEW certificates AS
--   SELECT ucaa_m.user_id, cg.group_id, ucaa_m.course_id, sum(ucaa_m.max_score) AS total_score
--   FROM group_users gu,
--        course_groups cg,
--        courses c,
--        (SELECT ucaa1.user_id, ucaa1.course_id, ucaa1.lesson_id, max(ucaa1.score) AS max_score
--         FROM user_course_assignment_attempts ucaa1
--         GROUP BY (ucaa1.user_id, ucaa1.course_id, ucaa1.lesson_id)) ucaa_m
--   WHERE gu.group_id = cg.group_id
--     AND c.id = cg.course_id
--     AND c.end_date < now()
--     AND ucaa_m.user_id = gu.user_id
--     AND ucaa_m.course_id = cg.course_id
--   GROUP BY (ucaa_m.user_id, cg.group_id, ucaa_m.course_id);
-- --- // VIEWS ---