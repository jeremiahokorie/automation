-- Insert default permissions for the system
INSERT INTO permission (name, value, description)
VALUES
-- User permissions
('User Read', 'USER_R', 'Permission to view user information'),
('User Write', 'USER_W', 'Permission to create/update user information'),
('User Delete', 'USER_D', 'Permission to delete users'),
('User Management', 'USER_M', 'Permission to manage user roles and permissions'),

-- Role permissions
('Role Read', 'ROLE_R', 'Permission to view roles'),
('Role Write', 'ROLE_W', 'Permission to create/update roles'),
('Role Delete', 'ROLE_D', 'Permission to delete roles'),
('Role Management', 'ROLE_M', 'Permission to manage role permissions'),

-- Business Registration permissions
('Business Registration Read', 'BUS_REG_R', 'Permission to view business registrations'),
('Business Registration Write', 'BUS_REG_W', 'Permission to create/update business registrations'),
('Business Registration Approve', 'BUS_REG_A', 'Permission to approve/reject business registrations'),
('Business Registration Delete', 'BUS_REG_D', 'Permission to delete business registrations'),

-- Certificate of Occupancy permissions
('COO Read', 'COO_R', 'Permission to view certificate of occupancy applications'),
('COO Write', 'COO_W', 'Permission to create/update certificate of occupancy applications'),
('COO Approve', 'COO_A', 'Permission to approve/reject certificate of occupancy applications'),
('COO Delete', 'COO_D', 'Permission to delete certificate of occupancy applications'),

-- Customary Allocation permissions
('Customary Allocation Read', 'CUST_ALLOC_R', 'Permission to view customary allocation applications'),
('Customary Allocation Write', 'CUST_ALLOC_W', 'Permission to create/update customary allocation applications'),
('Customary Allocation Approve', 'CUST_ALLOC_A', 'Permission to approve/reject customary allocation applications'),
('Customary Allocation Delete', 'CUST_ALLOC_D', 'Permission to delete customary allocation applications'),

-- Statutory Allocation permissions
('Statutory Allocation Read', 'STAT_ALLOC_R', 'Permission to view statutory allocation applications'),
('Statutory Allocation Write', 'STAT_ALLOC_W', 'Permission to create/update statutory allocation applications'),
('Statutory Allocation Approve', 'STAT_ALLOC_A', 'Permission to approve/reject statutory allocation applications'),
('Statutory Allocation Delete', 'STAT_ALLOC_D', 'Permission to delete statutory allocation applications'),

-- Environment permissions
('Environment Read', 'ENV_R', 'Permission to view environmental applications'),
('Environment Write', 'ENV_W', 'Permission to create/update environmental applications'),
('Environment Approve', 'ENV_A', 'Permission to approve/reject environmental applications'),
('Environment Delete', 'ENV_D', 'Permission to delete environmental applications'),

-- Ground Rent permissions
('Ground Rent Read', 'GR_R', 'Permission to view ground rent records'),
('Ground Rent Write', 'GR_W', 'Permission to create/update ground rent records'),
('Ground Rent Approve', 'GR_A', 'Permission to approve/reject ground rent records'),
('Ground Rent Delete', 'GR_D', 'Permission to delete ground rent records'),

-- Inspection permissions
('Inspection Read', 'INSP_R', 'Permission to view inspection records'),
('Inspection Write', 'INSP_W', 'Permission to create/update inspection records'),
('Inspection Approve', 'INSP_A', 'Permission to approve/reject inspection records'),
('Inspection Delete', 'INSP_D', 'Permission to delete inspection records'),

-- MDA permissions
('MDA Read', 'MDA_R', 'Permission to view MDAs'),
('MDA Write', 'MDA_W', 'Permission to create/update MDAs'),
('MDA Delete', 'MDA_D', 'Permission to delete MDAs'),

-- Services permissions
('Services Read', 'SVC_R', 'Permission to view services'),
('Services Write', 'SVC_W', 'Permission to create/update services'),
('Services Delete', 'SVC_D', 'Permission to delete services'),

-- System permissions
('System Configuration', 'SYS_CFG', 'Permission to configure system settings'),
('Dashboard Access', 'DASH_ACCESS', 'Permission to access administrative dashboard'),
('Report Generation', 'REPORT_GEN', 'Permission to generate system reports'),
('Audit Logs', 'AUDIT_LOGS', 'Permission to view system audit logs');

-- Create default roles
INSERT INTO roles (name, value, description)
VALUES
    ('Super Administrator', 'SUPERADMIN', 'Has all permissions including user and role management'),
    ('Super User', 'SUPER_USER', 'Has all application permissions but cannot manage users or roles');

-- Map all permissions to SUPERADMIN role
INSERT INTO role_permissions (role_id, permission_id)
SELECT
    (SELECT id FROM roles WHERE value = 'SUPERADMIN') as role_id,
    p.id as permission_id
FROM permission p;

-- Map all application permissions (excluding user/role management) to SUPER USER role
INSERT INTO role_permissions (role_id, permission_id)
SELECT
    (SELECT id FROM roles WHERE value = 'SUPER_USER') as role_id,
    p.id as permission_id
FROM permission p
WHERE p.value NOT LIKE 'USER_%' AND p.value NOT LIKE 'ROLE_%' AND p.value NOT IN ('SYS_CFG', 'AUDIT_LOGS');

-- Add user/role management permissions back to SUPER USER (optional, adjust as needed)
INSERT INTO role_permissions (role_id, permission_id)
VALUES
    ((SELECT id FROM roles WHERE value = 'SUPER_USER'), (SELECT id FROM permission WHERE value = 'USER_R')),
    ((SELECT id FROM roles WHERE value = 'SUPER_USER'), (SELECT id FROM permission WHERE value = 'USER_W')),
    ((SELECT id FROM roles WHERE value = 'SUPER_USER'), (SELECT id FROM permission WHERE value = 'ROLE_R'));

-- Create default admin user (password is 'admin' bcrypt encoded)
INSERT INTO user (first_name, last_name, password, email, create_date, phone_number, address, street, city, state, zip, nin)
VALUES
    ('System', 'Administrator', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 'admin@mda.gov', CURDATE(), '08012345678', 'Government House', 'State Secretariat', 'Capital City', 'State', '100001', '12345678901');

-- Assign SUPERADMIN role to admin user
INSERT INTO user_roles (user_id, role_id)
VALUES
    ((SELECT id FROM user WHERE email = 'admin@mda.gov'), (SELECT id FROM roles WHERE value = 'SUPERADMIN'));