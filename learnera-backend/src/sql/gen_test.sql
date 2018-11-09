INSERT INTO users (id, email, password_hash, role) VALUES (1, 'example@google.com', 'qwerty', 'TEACHER');
INSERT INTO users (id, email, password_hash, role) VALUES (2, 'example1@google.com', 'qwerty', 'STUDENT');
INSERT INTO users (id, email, password_hash, role) VALUES (3, 'example2@google.com', 'qwerty', 'STUDENT');
INSERT INTO users (id, email, password_hash, role) VALUES (4, 'example3@google.com', 'qwerty', 'STUDENT');

INSERT INTO user_infos (user_id, nickname, first_name, last_name)
VALUES (1, 'd32f123', 'Andrey', 'Neste-ov'), (3, 'admin', 'Admin', 'Adminovich');

INSERT INTO groups (id, curator_id, name, description, avatar_id)
VALUES (1, 1, 'A group', 'A group description', NULL);

INSERT INTO groups (id, curator_id, name, description, avatar_id)
VALUES (2, 1, 'B group', 'A group description', NULL);


INSERT INTO templates (id, teacher_id, name, description, avatar_id)
VALUES (1, 1, 'A template', 'A template description', NULL);

INSERT INTO courses (id, template_id, name, description, avatar_id, end_date)
VALUES (1, 1, 'A course', 'A course description', NULL, '03/11/2018');

INSERT INTO courses (id, template_id, name, description, avatar_id, end_date)
VALUES (2, 1, 'Second course', 'Second description', NULL, '04/11/2018');

SELECT * FROM courses;
SELECT * FROM groups;
SELECT * FROM course_week_dates;

INSERT INTO group_users (user_id, group_id) VALUES (2, 1), (3, 1), (4, 1);
INSERT INTO course_groups (group_id, course_id) VALUES (1, 1);


INSERT INTO weeks (id, template_id, week_number, name) VALUES (1, 1, 1, 'First week');
INSERT INTO weeks (id, template_id, week_number, name) VALUES (2, 1, 2, NULL);

INSERT INTO lessons (id, week_id, ordering) VALUES (1, 1, 1);
INSERT INTO lessons (id, week_id, ordering) VALUES (2, 1, 2);
INSERT INTO lessons (id, week_id, ordering) VALUES (3, 2, 1);

INSERT INTO lectures (lesson_id, lecture_text, file_id) VALUES (1, 'REAL GOOD LECTURE!', NULL);
INSERT INTO assignments (lesson_id) VALUES (2);
INSERT INTO assignments (lesson_id) VALUES (3);

INSERT INTO questions (id, assignment_id, ordering, question_text, type, answer)
VALUES (1, 2, 1, 'This is a very hard question!', 'FIXED', 'Answer123');

INSERT INTO questions (id, assignment_id, ordering, question_text, type, answer)
VALUES (2, 2, 2, 'This is a very hard multiple-choice question!', 'MC', NULL);
INSERT INTO mc_question_variants (id, question_id, choice_text, is_correct)
VALUES (1, 2, 'Choice 1', true);
INSERT INTO mc_question_variants (id, question_id, choice_text, is_correct)
VALUES (2, 2, 'Choice 2', false);
INSERT INTO mc_question_variants (id, question_id, choice_text, is_correct)
VALUES (3, 2, 'Choice 3', false);

INSERT INTO questions (id, assignment_id, ordering, question_text, type, answer)
VALUES (3, 3, 1, 'Another hard question', 'REGEX', 'ASDASDASDASD');

INSERT INTO question_attempts (id, user_id, course_id, question_id, answer)
VALUES (1, 2, 1, 1, 'XD');

INSERT INTO question_attempts (id, user_id, course_id, question_id, answer)
VALUES (2, 3, 1, 3, 'what?');

INSERT INTO lesson_messages (id, sender_id, lesson_id, message) VALUES (1, 2, 1, 'some message');

INSERT INTO group_messages (id, sender_id, group_id, message) VALUES (1, 3, 1, 'another message');
INSERT INTO group_messages (id, sender_id, group_id, message) VALUES (2, 3, 1, 'yet another message');

INSERT INTO course_week_dates (course_id, week_id, start_date, end_date) VALUES (1, 1, '04/11/2018', '05/11/2018');
INSERT INTO course_week_dates (course_id, week_id, start_date, end_date) VALUES (1, 2, '08/11/2018', '12/11/2018');