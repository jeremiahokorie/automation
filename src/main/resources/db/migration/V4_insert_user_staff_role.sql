-- ============================================================
-- Migration: Add USER and STAFF roles
-- Run this AFTER the original seed migration (adds to existing
-- roles/permission/role_permissions tables, does not touch them)
-- ============================================================

-- 1. Create the two new roles
INSERT INTO roles (name, value, description)
VALUES
    ('User', 'USER', 'Standard end-user / applicant. Can create and view their own applications only.'),
    ('Staff', 'STAFF', 'MDA staff who process, review, and approve applications submitted by users.');

-- ============================================================
-- 2. USER role permissions
--    Basic applicant access: can create/view applications across
--    modules, but cannot approve, delete, or access admin areas.
-- ============================================================
INSERT INTO role_permissions (role_id, permission_id)
SELECT
    (SELECT id FROM roles WHERE value = 'USER') AS role_id,
    p.id AS permission_id
FROM permission p
WHERE p.value IN (
    'BUS_REG_R', 'BUS_REG_W',
    'COO_R', 'COO_W',
    'CUST_ALLOC_R', 'CUST_ALLOC_W',
    'STAT_ALLOC_R', 'STAT_ALLOC_W',
    'ENV_R', 'ENV_W',
    'GR_R',
    'SVC_R'
);

-- ============================================================
-- 3. STAFF role permissions
--    Operational access: read/write/approve on all case-processing
--    modules, dashboard + report access, but NO delete rights and
--    NO user/role management or system configuration.
-- ============================================================
INSERT INTO role_permissions (role_id, permission_id)
SELECT
    (SELECT id FROM roles WHERE value = 'STAFF') AS role_id,
    p.id AS permission_id
FROM permission p
WHERE p.value IN (
    'BUS_REG_R', 'BUS_REG_W', 'BUS_REG_A',
    'COO_R', 'COO_W', 'COO_A',
    'CUST_ALLOC_R', 'CUST_ALLOC_W', 'CUST_ALLOC_A',
    'STAT_ALLOC_R', 'STAT_ALLOC_W', 'STAT_ALLOC_A',
    'ENV_R', 'ENV_W', 'ENV_A',
    'GR_R', 'GR_W', 'GR_A',
    'INSP_R', 'INSP_W', 'INSP_A',
    'MDA_R',
    'SVC_R', 'SVC_W',
    'DASH_ACCESS',
    'REPORT_GEN'
);
