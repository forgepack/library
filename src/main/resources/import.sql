-- POPULING role
INSERT INTO demo.role(created_at, updated_at, id, name) VALUES(NOW(), NOW(), '7c12004d-e843-4e00-be40-01845ad75834', 'USER') ON CONFLICT DO NOTHING;
INSERT INTO demo.role(created_at, updated_at, id, name) VALUES(NOW(), NOW(), '52c57a80-4e3b-4a41-a864-58d0cea25b14', 'MODERATOR') ON CONFLICT DO NOTHING;
INSERT INTO demo.role(created_at, updated_at, id, name) VALUES(NOW(), NOW(), '8652ec73-0a53-433c-93be-420f1d90c681', 'ADMIN') ON CONFLICT DO NOTHING;
INSERT INTO demo.role(created_at, updated_at, id, name) VALUES(NOW(), NOW(), '55c16ae7-b918-4b31-b920-deb4af049075', 'VIEWER') ON CONFLICT DO NOTHING;
INSERT INTO demo.role(created_at, updated_at, id, name) VALUES(NOW(), NOW(), '83366ed6-b0f2-4ef3-9658-e8bd9a8e3d39', 'OPERATOR') ON CONFLICT DO NOTHING;
INSERT INTO demo.role(created_at, updated_at, id, name) VALUES(NOW(), NOW(), '927c96c5-6884-433a-9479-836efbb1ed87', 'VERIFIER') ON CONFLICT DO NOTHING;
INSERT INTO demo.role(created_at, updated_at, id, name) VALUES(NOW(), NOW(), 'b8b37d04-628d-4939-b200-2a5e48909cd9', 'REVIEWER') ON CONFLICT DO NOTHING;

-- POPULING privilege
INSERT INTO demo.privilege(created_at, updated_at, id, name) VALUES(NOW(), NOW(), 'cc8cf1e3-2c85-4500-b6db-16a1e6af2c8a', 'user:create') ON CONFLICT DO NOTHING;
INSERT INTO demo.privilege(created_at, updated_at, id, name) VALUES(NOW(), NOW(), '82beb7a1-621c-4b9a-83eb-3ca196ed4345', 'user:retrieve') ON CONFLICT DO NOTHING;
INSERT INTO demo.privilege(created_at, updated_at, id, name) VALUES(NOW(), NOW(), '3f2146a9-5d43-448e-a1eb-455766d3a14a', 'user:update') ON CONFLICT DO NOTHING;
INSERT INTO demo.privilege(created_at, updated_at, id, name) VALUES(NOW(), NOW(), 'e5761ebc-d29d-43dc-b1b3-ca013169e2c2', 'user:delete') ON CONFLICT DO NOTHING;

-- POPULING role_privilege
INSERT INTO demo.role_privileges(role_id, privilege_id) VALUES ('7c12004d-e843-4e00-be40-01845ad75834', '82beb7a1-621c-4b9a-83eb-3ca196ed4345') ON CONFLICT DO NOTHING;
INSERT INTO demo.role_privileges(role_id, privilege_id) VALUES ('52c57a80-4e3b-4a41-a864-58d0cea25b14', 'cc8cf1e3-2c85-4500-b6db-16a1e6af2c8a') ON CONFLICT DO NOTHING;
INSERT INTO demo.role_privileges(role_id, privilege_id) VALUES ('52c57a80-4e3b-4a41-a864-58d0cea25b14', '82beb7a1-621c-4b9a-83eb-3ca196ed4345') ON CONFLICT DO NOTHING;
INSERT INTO demo.role_privileges(role_id, privilege_id) VALUES ('52c57a80-4e3b-4a41-a864-58d0cea25b14', '3f2146a9-5d43-448e-a1eb-455766d3a14a') ON CONFLICT DO NOTHING;
INSERT INTO demo.role_privileges(role_id, privilege_id) VALUES ('8652ec73-0a53-433c-93be-420f1d90c681', 'cc8cf1e3-2c85-4500-b6db-16a1e6af2c8a') ON CONFLICT DO NOTHING;
INSERT INTO demo.role_privileges(role_id, privilege_id) VALUES ('8652ec73-0a53-433c-93be-420f1d90c681', '82beb7a1-621c-4b9a-83eb-3ca196ed4345') ON CONFLICT DO NOTHING;
INSERT INTO demo.role_privileges(role_id, privilege_id) VALUES ('8652ec73-0a53-433c-93be-420f1d90c681', '3f2146a9-5d43-448e-a1eb-455766d3a14a') ON CONFLICT DO NOTHING;
INSERT INTO demo.role_privileges(role_id, privilege_id) VALUES ('8652ec73-0a53-433c-93be-420f1d90c681', 'e5761ebc-d29d-43dc-b1b3-ca013169e2c2') ON CONFLICT DO NOTHING;
INSERT INTO demo.role_privileges(role_id, privilege_id) VALUES ('55c16ae7-b918-4b31-b920-deb4af049075', '82beb7a1-621c-4b9a-83eb-3ca196ed4345') ON CONFLICT DO NOTHING;
INSERT INTO demo.role_privileges(role_id, privilege_id) VALUES ('83366ed6-b0f2-4ef3-9658-e8bd9a8e3d39', 'cc8cf1e3-2c85-4500-b6db-16a1e6af2c8a') ON CONFLICT DO NOTHING;
INSERT INTO demo.role_privileges(role_id, privilege_id) VALUES ('83366ed6-b0f2-4ef3-9658-e8bd9a8e3d39', '82beb7a1-621c-4b9a-83eb-3ca196ed4345') ON CONFLICT DO NOTHING;
INSERT INTO demo.role_privileges(role_id, privilege_id) VALUES ('83366ed6-b0f2-4ef3-9658-e8bd9a8e3d39', '3f2146a9-5d43-448e-a1eb-455766d3a14a') ON CONFLICT DO NOTHING;
INSERT INTO demo.role_privileges(role_id, privilege_id) VALUES ('83366ed6-b0f2-4ef3-9658-e8bd9a8e3d39', 'e5761ebc-d29d-43dc-b1b3-ca013169e2c2') ON CONFLICT DO NOTHING;
INSERT INTO demo.role_privileges(role_id, privilege_id) VALUES ('927c96c5-6884-433a-9479-836efbb1ed87', 'cc8cf1e3-2c85-4500-b6db-16a1e6af2c8a') ON CONFLICT DO NOTHING;
INSERT INTO demo.role_privileges(role_id, privilege_id) VALUES ('927c96c5-6884-433a-9479-836efbb1ed87', '82beb7a1-621c-4b9a-83eb-3ca196ed4345') ON CONFLICT DO NOTHING;
INSERT INTO demo.role_privileges(role_id, privilege_id) VALUES ('927c96c5-6884-433a-9479-836efbb1ed87', '3f2146a9-5d43-448e-a1eb-455766d3a14a') ON CONFLICT DO NOTHING;
INSERT INTO demo.role_privileges(role_id, privilege_id) VALUES ('927c96c5-6884-433a-9479-836efbb1ed87', 'e5761ebc-d29d-43dc-b1b3-ca013169e2c2') ON CONFLICT DO NOTHING;
INSERT INTO demo.role_privileges(role_id, privilege_id) VALUES ('b8b37d04-628d-4939-b200-2a5e48909cd9', 'cc8cf1e3-2c85-4500-b6db-16a1e6af2c8a') ON CONFLICT DO NOTHING;
INSERT INTO demo.role_privileges(role_id, privilege_id) VALUES ('b8b37d04-628d-4939-b200-2a5e48909cd9', '82beb7a1-621c-4b9a-83eb-3ca196ed4345') ON CONFLICT DO NOTHING;
INSERT INTO demo.role_privileges(role_id, privilege_id) VALUES ('b8b37d04-628d-4939-b200-2a5e48909cd9', '3f2146a9-5d43-448e-a1eb-455766d3a14a') ON CONFLICT DO NOTHING;
INSERT INTO demo.role_privileges(role_id, privilege_id) VALUES ('b8b37d04-628d-4939-b200-2a5e48909cd9', 'e5761ebc-d29d-43dc-b1b3-ca013169e2c2') ON CONFLICT DO NOTHING;
