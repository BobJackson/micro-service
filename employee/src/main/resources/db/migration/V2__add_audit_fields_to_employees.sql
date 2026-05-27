-- 添加审计字段：创建人和更新人
ALTER TABLE employees
    ADD COLUMN created_by VARCHAR(50);
ALTER TABLE employees
    ADD COLUMN updated_by VARCHAR(50);
