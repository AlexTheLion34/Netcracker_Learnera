INSERT INTO users (id, email, password_hash, role)
VALUES (1, 'example@google.com', '$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S', 'TEACHER');
INSERT INTO users (id, email, password_hash, role)
VALUES (2, 'example1@google.com', '$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S', 'STUDENT');
INSERT INTO users (id, email, password_hash, role)
VALUES (3, 'example2@google.com', '$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S', 'STUDENT');
INSERT INTO users (id, email, password_hash, role)
VALUES (4, 'example3@google.com', '$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S', 'STUDENT');
INSERT INTO users (id, email, password_hash, role)
VALUES (5, 'example4@google.com', '$2a$10$ud9azktwmW.81ZELDGDdt.Yipg8CgUNPFZm.3FKTLF/oieoH.Xs4S', 'STUDENT');

INSERT INTO user_infos (user_id, nickname, first_name, last_name)
VALUES (1, 'd32f123', 'Andrey', 'Neste-ov'),
       (2, 'xXxBo$$xXx', 'Oleg', 'Olegovich'),
       (3, 'admin', 'Admin', 'Adminovich'),
       (4, 'xXxKoTxXx', 'Pavel', 'Kot'),
       (5, 'kitten', 'Dasha', 'Zamsheva');

INSERT INTO groups (id, curator_id, name, description, avatar_id)
VALUES (6, 1, 'P3205', 'The perfect group', NULL);

INSERT INTO groups (id, curator_id, name, description, avatar_id)
VALUES (7, 1, 'P3566', 'The not-so-good group (what???)', NULL);


INSERT INTO templates (id, teacher_id, name, is_completed, description, avatar_id)
VALUES (8, 1, 'JavaTemplate', TRUE, 'The perfect template on Java programming language', NULL);

INSERT INTO modules (id, template_id, module_number, name)
VALUES (10, 8, 0, 'Basics');
INSERT INTO modules (id, template_id, module_number, name)
VALUES (11, 8, 1, 'Loops');

INSERT INTO lessons (id, name, module_id, ordering, lecture_text)
VALUES (12, 'Data types', 10, 0, 'The eight primitive data types in Java are:

    boolean, the type whose values are either true or false
    char, the character type whose values are 16-bit Unicode characters
    the arithmetic types:
        the integral types:
            byte
            short
            int
            long
        the floating-point types:
            float
            double

Values of class type are references. Strings are references to an instance of class String.');
INSERT INTO lessons (id, name, module_id, ordering, type)
VALUES (13, 'Data types assignment', 10, 1, 'assignment');
INSERT INTO lessons (id, name, module_id, ordering, lecture_text)
VALUES (14, 'Equality operators lecture', 10, 2, 'The Equality and Relational Operators

The equality and relational operators determine if one operand is greater than, less than, equal to, or not equal to another operand.
The majority of these operators will probably look familiar to you as well.
Keep in mind that you must use "==", not "=", when testing if two primitive values are equal.

==      equal to
!=      not equal to
>       greater than
>=      greater than or equal to
<       less than
<=      less than or equal to
');
INSERT INTO lessons (id, name, module_id, ordering, type)
VALUES (15, 'Equality operators assignment', 10, 3, 'assignment');

-- Second module lessons
INSERT INTO lessons (id, name, module_id, ordering, lecture_text)
VALUES (16, 'While loop lecture', 11, 0, 'The while statement continually executes a block of statements while a particular condition is true. Its syntax can be expressed as:

while (expression) {
     statement(s)
}

The while statement evaluates expression, which must return a boolean value. If the expression evaluates to true, the while statement executes the statement(s) in the while block. The while statement continues testing the expression and executing its block until the expression evaluates to false. Using the while statement to print the values from 1 through 10 can be accomplished as in the following WhileDemo program:


class WhileDemo {
    public static void main(String[] args){
        int count = 1;
        while (count < 11) {
            System.out.println("Count is: " + count);
            count++;
        }
    }
}

You can implement an infinite loop using the while statement as follows:

while (true){
    // your code goes here
}
');
INSERT INTO lessons (id, name, module_id, ordering, type)
VALUES (17, 'While loop assignment', 11, 1, 'assignment');
INSERT INTO lessons (id, name, module_id, ordering, lecture_text)
VALUES (18, 'For loop lecture', 11, 2, 'The for statement provides a compact way to iterate over a range of values. Programmers often refer to it as the "for loop" because of the way in which it repeatedly loops until a particular condition is satisfied. The general form of the for statement can be expressed as follows:

for (initialization; termination; increment) {
    statement(s)
}

When using this version of the for statement, keep in mind that:

    The initialization expression initializes the loop; it''s executed once, as the loop begins.
    When the termination expression evaluates to false, the loop terminates.
    The increment expression is invoked after each iteration through the loop; it is perfectly acceptable for this expression to increment or decrement a value.

The following program, ForDemo, uses the general form of the for statement to print the numbers 1 through 10 to standard output:


class ForDemo {
    public static void main(String[] args){
         for(int i=1; i<11; i++){
              System.out.println("Count is: " + i);
         }
    }
}
');
INSERT INTO lessons (id, name, module_id, ordering, type)
VALUES (19, 'For loop assignment', 11, 3, 'assignment');

-- Data types assignment questions
INSERT INTO questions (id, assignment_id, ordering, question_text, type, answer)
VALUES (20, 13, 0, 'How are the values of class types called?', 'REGEX', '^[rR]eferences?');

INSERT INTO questions (id, assignment_id, ordering, question_text, type, points)
VALUES (21, 13, 1, 'Select all data types which are present in Java', 'MC', 4);
INSERT INTO mc_question_variants (id, question_id, choice_text, is_correct)
VALUES (22, 21, 'byte', TRUE), (23, 21, 'bytessa', FALSE), (24, 21, 'int', TRUE), (25, 21, 'bossyInt', FALSE),
       (26, 21, 'long long', FALSE);

-- Relational operators questions
INSERT INTO questions (id, assignment_id, ordering, question_text, type, answer)
VALUES (27, 15, 0, 'How to check whether two values are equal or not?', 'FIXED', '=='),
       (28, 15, 1, 'Is this a valid relational operator in Java: ''<>''? (Yes/No)', 'FIXED', 'No');

-- While loop questions (id 17)
INSERT INTO questions (id, assignment_id, ordering, question_text, type, answer)
VALUES (29, 17, 0, 'What type must be the expression in the parentheses of a while loop?', 'FIXED', 'boolean');

-- For loop questions (id 19)
INSERT INTO questions (id, assignment_id, ordering, question_text, type, points)
VALUES (30, 19, 0, 'Select the correct for loop syntax', 'MC', 3);
INSERT INTO mc_question_variants (id, question_id, choice_text, is_correct)
VALUES (31, 30, 'for (initialization; termination; increment) {
    statement(s)
}', TRUE), (32, 30, 'for (condition; increment) {
    statement(s)
}', FALSE);

INSERT INTO courses (id, template_id, name, description, avatar_id, end_date)
VALUES (33, 8, 'JAVA COURSE FTW', 'The perfect JAVA course.', NULL, NOW() + INTERVAL '15 DAYS');

INSERT INTO courses (id, template_id, name, description, avatar_id, end_date)
VALUES (34, 8, 'Java course 2', 'THe other Java course', NULL, NOW() + INTERVAL '15 DAYS');

INSERT INTO group_users (user_id, group_id)
VALUES (2, 6),
       (3, 6),
       (4, 7),
       (5, 7);
INSERT INTO course_groups (group_id, course_id)
VALUES (6, 33), (7, 34);

INSERT INTO course_module_dates (course_id, module_id, start_date, end_date)
VALUES (33, 10, NOW(), NOW() + INTERVAL '7 DAYS'), (33, 11, NOW() + INTERVAL '8 DAYS', NOW() + INTERVAL '15 DAYS'),
       (34, 10, NOW(), NOW() + INTERVAL '7 DAYS'), (34, 11, NOW() + INTERVAL '8 DAYS', NOW() + INTERVAL '15 DAYS');

