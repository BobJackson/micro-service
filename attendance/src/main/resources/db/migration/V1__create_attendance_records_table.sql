CREATE TABLE IF NOT EXISTS attendance_records
(
    id              BIGSERIAL PRIMARY KEY,
    employee_id     BIGINT    NOT NULL,
    attendance_date DATE      NOT NULL,
    check_in_time   TIMESTAMP,
    check_out_time  TIMESTAMP,
    status          VARCHAR(20),
    remark          VARCHAR(500),
    create_time     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by      VARCHAR(50),
    update_time     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by      VARCHAR(50)
);

CREATE INDEX idx_attendance_employee ON attendance_records (employee_id);
CREATE INDEX idx_attendance_date ON attendance_records (attendance_date);
CREATE UNIQUE INDEX idx_attendance_employee_date ON attendance_records (employee_id, attendance_date);
