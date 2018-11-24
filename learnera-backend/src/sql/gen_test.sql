INSERT INTO users (id, email, password_hash, role)
VALUES (1, 'example@google.com', '$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S', 'TEACHER');
INSERT INTO users (id, email, password_hash, role)
VALUES (2, 'example1@google.com', '$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S', 'STUDENT');
INSERT INTO users (id, email, password_hash, role)
VALUES (3, 'example2@google.com', '$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S', 'STUDENT');
INSERT INTO users (id, email, password_hash, role)
VALUES (4, 'example3@google.com', '$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S', 'STUDENT');

INSERT INTO user_infos (user_id, nickname, first_name, last_name)
VALUES (1, 'd32f123', 'Andrey', 'Neste-ov'),
       (3, 'admin', 'Admin', 'Adminovich');

INSERT INTO groups (id, curator_id, name, description, avatar_id)
VALUES (5, 1, 'A group', 'A group description', NULL);

INSERT INTO groups (id, curator_id, name, description, avatar_id)
VALUES (6, 1, 'B group', 'B group description', NULL);


INSERT INTO templates (id, teacher_id, name, description, avatar_id)
VALUES (7, 1, 'A template', 'A template description', NULL);

INSERT INTO courses (id, template_id, name, description, avatar_id, end_date)
VALUES (8, 7, 'A course', 'A course description', NULL, '03/11/2018');

INSERT INTO courses (id, template_id, name, description, avatar_id, end_date)
VALUES (9, 7, 'Second course', 'Second description', NULL, '04/11/2018');

-- SELECT * FROM courses;
-- SELECT * FROM groups;
-- SELECT * FROM course_week_dates;

INSERT INTO group_users (user_id, group_id)
VALUES (2, 5),
       (3, 5),
       (4, 5);
INSERT INTO course_groups (group_id, course_id)
VALUES (5, 8);


INSERT INTO weeks (id, template_id, week_number, name)
VALUES (10, 7, 0, 'First week');
INSERT INTO weeks (id, template_id, week_number, name)
VALUES (11, 7, 1, NULL);

INSERT INTO lessons (id, week_id, ordering, lecture_text)
VALUES (12, 10, 0, 'REAL GOOD LECTURE!');
INSERT INTO lessons (id, week_id, ordering, type)
VALUES (13, 10, 1, 'assignment');
INSERT INTO lessons (id, week_id, ordering, type)
VALUES (14, 11, 0, 'assignment');

INSERT INTO questions (id, assignment_id, ordering, question_text, type, answer)
VALUES (15, 13, 0, 'This is a very hard question!', 'FIXED', 'Answer123');

INSERT INTO questions (id, assignment_id, ordering, question_text, type, answer)
VALUES (16, 14, 1, 'This is a very hard multiple-choice question!', 'MC', NULL);
INSERT INTO mc_question_variants (id, question_id, choice_text, is_correct)
VALUES (17, 16, 'Choice 1', true);
INSERT INTO mc_question_variants (id, question_id, choice_text, is_correct)
VALUES (18, 16, 'Choice 2', false);
INSERT INTO mc_question_variants (id, question_id, choice_text, is_correct)
VALUES (19, 16, 'Choice 3', false);

INSERT INTO questions (id, assignment_id, ordering, question_text, type, answer)
VALUES (20, 14, 0, 'Another hard question', 'REGEX', 'ASDASDASDASD');

INSERT INTO question_attempts (id, user_id, course_id, question_id, answer)
VALUES (21, 2, 8, 15, 'XD');

INSERT INTO question_attempts (id, user_id, course_id, question_id, answer)
VALUES (22, 3, 8, 16, 'what?');

INSERT INTO lesson_messages (id, sender_id, lesson_id, message)
VALUES (23, 2, 12, 'some message');

INSERT INTO group_messages (id, sender_id, group_id, message)
VALUES (24, 3, 5, 'another message');
INSERT INTO group_messages (id, sender_id, group_id, message)
VALUES (25, 3, 5, 'yet another message');

INSERT INTO course_week_dates (course_id, week_id, start_date, end_date)
VALUES (8, 10, '04/11/2018', '05/11/2018');
INSERT INTO course_week_dates (course_id, week_id, start_date, end_date)
VALUES (8, 11, '08/11/2018', '12/11/2018');