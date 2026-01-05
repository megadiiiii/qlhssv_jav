CREATE TRIGGER trg_student_insert
AFTER INSERT ON student
FOR EACH ROW
UPDATE class
SET student_current = student_current + 1
WHERE class_id = NEW.class_id;

CREATE TRIGGER trg_student_delete
AFTER DELETE ON student
FOR EACH ROW
UPDATE class
SET student_current = student_current - 1
WHERE class_id = OLD.class_id;

DELIMITER $$

CREATE TRIGGER trg_student_update
AFTER UPDATE ON student
FOR EACH ROW
BEGIN
    IF OLD.class_id <> NEW.class_id THEN
        UPDATE class
        SET student_current = student_current - 1
        WHERE class_id = OLD.class_id;

        UPDATE class
        SET student_current = student_current + 1
        WHERE class_id = NEW.class_id;
    END IF;
END$$

DELIMITER ;

