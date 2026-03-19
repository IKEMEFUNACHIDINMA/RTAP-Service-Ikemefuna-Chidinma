-- Insert an Instructor
INSERT INTO users (username, role) VALUES ('prof_smith', 'INSTRUCTOR');

-- Insert a few Students
INSERT INTO users (username, role) VALUES ('student_alice', 'STUDENT');
INSERT INTO users (username, role) VALUES ('student_bob', 'STUDENT');

-- Insert a test class session to play with
INSERT INTO class_sessions (title, start_time, is_active)
VALUES ('Intro to Spring Boot', CURRENT_TIMESTAMP, true);