CREATE TABLE business_registration
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    business_name   VARCHAR(255) NULL,
    owner_name      VARCHAR(255) NULL,
    address         VARCHAR(255) NULL,
    phone           VARCHAR(255) NULL,
    email           VARCHAR(255) NULL,
    status          SMALLINT NULL,
    date_registered date NULL,
    is_renewal      BIT(1) NOT NULL,
    is_expired      BIT(1) NOT NULL,
    business_number VARCHAR(255) NULL,
    comment         VARCHAR(255) NULL,
    renewal_date    date NULL,
    CONSTRAINT pk_businessregistration PRIMARY KEY (id)
);

CREATE TABLE business_type
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255) NULL,
    `description` VARCHAR(255) NULL,
    CONSTRAINT pk_businesstype PRIMARY KEY (id)
);

CREATE TABLE certificate_of_occupancy
(
    id                                   BIGINT AUTO_INCREMENT NOT NULL,
    applicant_name                       VARCHAR(255) NULL,
    district_head_letter                 VARCHAR(255) NULL,
    sales_agreement                      VARCHAR(255) NULL,
    declaration_of_age                   VARCHAR(255) NULL,
    tax_clearance                        VARCHAR(255) NULL,
    survey_data                          VARCHAR(255) NULL,
    status                               VARCHAR(255) NULL,
    applicant_email                      VARCHAR(255) NULL,
    local_government_confirmation_letter VARCHAR(255) NULL,
    created_at                           datetime NULL,
    comment                              VARCHAR(255) NULL,
    approval_date                        date NULL,
    rejection_date                       date NULL,
    CONSTRAINT pk_certificate_of_occupancy PRIMARY KEY (id)
);

CREATE TABLE customary_allocation_applications
(
    id                         BIGINT AUTO_INCREMENT NOT NULL,
    application_date           date NULL,
    applicant_name             VARCHAR(255) NULL,
    applicant_title            VARCHAR(255) NULL,
    nationality                VARCHAR(255) NULL,
    place_of_birth             VARCHAR(255) NULL,
    state_of_origin            VARCHAR(255) NULL,
    date_of_birth              date NULL,
    gender                     VARCHAR(255) NULL,
    marital_status             VARCHAR(255) NULL,
    occupation                 VARCHAR(255) NULL,
    home_address               VARCHAR(255) NULL,
    town_or_area               VARCHAR(255) NULL,
    lga                        VARCHAR(255) NULL,
    phone_number               VARCHAR(255) NULL,
    email                      VARCHAR(255) NULL,
    owns_customary_land        BIT(1) NULL,
    existing_land_location     VARCHAR(255) NULL,
    land_purpose               VARCHAR(255) NULL,
    purpose_detail             VARCHAR(255) NULL,
    proposed_building_type     VARCHAR(255) NULL,
    plot_size                  VARCHAR(255) NULL,
    plot_size_other            VARCHAR(255) NULL,
    application_fee_amount     DECIMAL NULL,
    proposed_development_cost  DECIMAL NULL,
    development_financing_plan VARCHAR(255) NULL,
    community_consent          BIT(1) NULL,
    community_consent_date     date NULL,
    community_leader_name      VARCHAR(255) NULL,
    community_leader_title     VARCHAR(255) NULL,
    signature_or_mark          VARCHAR(255) NULL,
    declaration_date           date NULL,
    passport_photo             VARCHAR(255) NULL,
    tax_clearance              VARCHAR(255) NULL,
    affidavit                  VARCHAR(255) NULL,
    community_consent_letter   VARCHAR(255) NULL,
    development_sketch         VARCHAR(255) NULL,
    latitude DOUBLE NULL,
    longitude DOUBLE NULL,
    altitude DOUBLE NULL,
    gps_accuracy DOUBLE NULL,
    CONSTRAINT pk_customary_allocation_applications PRIMARY KEY (id)
);

CREATE TABLE environment
(
    id                         BIGINT AUTO_INCREMENT NOT NULL,
    applicant_name             VARCHAR(255) NULL,
    contact_person             VARCHAR(255) NULL,
    phone                      VARCHAR(255) NULL,
    email                      VARCHAR(255) NULL,
    address                    VARCHAR(255) NULL,
    is_payed                   BIT(1) NULL,
    permit_type                SMALLINT NULL,
    waste_description          VARCHAR(255) NULL,
    waste_source               SMALLINT NULL,
    waste_quantity DOUBLE NULL,
    disposal_frequency         VARCHAR(255) NULL,
    disposal_method            VARCHAR(255) NULL,
    disposal_location          VARCHAR(255) NULL,
    facility_name              VARCHAR(255) NULL,
    facility_address           VARCHAR(255) NULL,
    industry_type              VARCHAR(255) NULL,
    operational_license_number VARCHAR(255) NULL,
    has_environmental_audit    BIT(1) NULL,
    application_date           date NULL,
    status                     SMALLINT NULL,
    comment                    VARCHAR(255) NULL,
    approval_date              date NULL,
    rejection_date             date NULL,
    CONSTRAINT pk_environment PRIMARY KEY (id)
);

CREATE TABLE ground_rent
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    ba_no          VARCHAR(255) NULL,
    land_no        VARCHAR(255) NULL,
    record         VARCHAR(255) NULL,
    rent DOUBLE NULL,
    status         VARCHAR(255) NULL,
    optional_file  VARCHAR(255) NULL,
    created_at     datetime NULL,
    comment        VARCHAR(255) NULL,
    approval_date  date NULL,
    rejection_date date NULL,
    CONSTRAINT pk_ground_rent PRIMARY KEY (id)
);

CREATE TABLE inspection
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    request_id       BINARY(16)            NULL,
    source_service   VARCHAR(255) NULL,
    applicant_name   VARCHAR(255) NULL,
    application_type VARCHAR(255) NULL,
    status           SMALLINT NULL,
    assigned_to      VARCHAR(255) NULL,
    notes            VARCHAR(255) NULL,
    created_at       datetime NULL,
    updated_at       datetime NULL,
    CONSTRAINT pk_inspection PRIMARY KEY (id)
);

CREATE TABLE lands
(
    id                                   BIGINT AUTO_INCREMENT NOT NULL,
    applicant_name                       VARCHAR(255) NULL,
    email                                VARCHAR(255) NULL,
    application_type                     SMALLINT NULL,
    status                               VARCHAR(255) NULL,
    application_date                     datetime NULL,
    approval_date                        datetime NULL,
    documents                            VARCHAR(255) NULL,
    certificate_url                      VARCHAR(255) NULL,
    district_head_letter                 VARCHAR(255) NULL,
    sales_agreement                      VARCHAR(255) NULL,
    declaration_of_age                   VARCHAR(255) NULL,
    tax_clearance                        VARCHAR(255) NULL,
    survey_data                          VARCHAR(255) NULL,
    applicant_email                      VARCHAR(255) NULL,
    local_government_confirmation_letter VARCHAR(255) NULL,
    created_at                           datetime NULL,
    passport_photos                      VARCHAR(255) NULL,
    tax_clearances                       VARCHAR(255) NULL,
    administrative_charges               VARCHAR(255) NULL,
    processing_fees                      VARCHAR(255) NULL,
    ba_no                                VARCHAR(255) NULL,
    land_no                              VARCHAR(255) NULL,
    record                               VARCHAR(255) NULL,
    rent DOUBLE NULL,
    optional_file                        VARCHAR(255) NULL,
    CONSTRAINT pk_lands PRIMARY KEY (id)
);

CREATE TABLE mda
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255) NULL,
    code VARCHAR(255) NULL,
    CONSTRAINT pk_mda PRIMARY KEY (id)
);

CREATE TABLE permission
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255) NULL,
    value         VARCHAR(255) NULL,
    `description` VARCHAR(255) NULL,
    CONSTRAINT pk_permission PRIMARY KEY (id)
);

CREATE TABLE permit_application
(
    id               BIGINT NOT NULL,
    applicant_name   VARCHAR(255) NULL,
    email            VARCHAR(255) NULL,
    permit_type      VARCHAR(255) NULL,
    document_url     VARCHAR(255) NULL,
    status           VARCHAR(255) NULL,
    license_url      VARCHAR(255) NULL,
    application_date datetime NULL,
    approval_date    datetime NULL,
    CONSTRAINT pk_permitapplication PRIMARY KEY (id)
);

CREATE TABLE role_permissions
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    role_id       BIGINT NULL,
    permission_id BIGINT NULL,
    CONSTRAINT pk_role_permissions PRIMARY KEY (id)
);

CREATE TABLE roles
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255) NULL,
    value         VARCHAR(255) NULL,
    `description` VARCHAR(255) NULL,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

CREATE TABLE roles_permissions
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    permissions_id BIGINT NOT NULL,
    roles_id       BIGINT NOT NULL,
    CONSTRAINT pk_roles_permissions PRIMARY KEY (id)
);

CREATE TABLE services
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255) NULL,
    `description` VARCHAR(255) NULL,
    mda_id        BIGINT NULL,
    CONSTRAINT pk_services PRIMARY KEY (id)
);

CREATE TABLE statutory_allocation_applications
(
    id                             BIGINT AUTO_INCREMENT NOT NULL,
    application_date               date NULL,
    application_no                 VARCHAR(255) NULL,
    luac_no                        VARCHAR(255) NULL,
    title                          VARCHAR(255) NULL,
    title_other                    VARCHAR(255) NULL,
    applicant_name                 VARCHAR(255) NULL,
    nationality                    VARCHAR(255) NULL,
    nationality_other              VARCHAR(255) NULL,
    place_of_birth                 VARCHAR(255) NULL,
    state_of_origin                VARCHAR(255) NULL,
    date_of_birth                  date NULL,
    gender                         VARCHAR(255) NULL,
    marital_status                 VARCHAR(255) NULL,
    occupation                     VARCHAR(255) NULL,
    occupation_other               VARCHAR(255) NULL,
    home_address                   VARCHAR(255) NULL,
    town_or_area                   VARCHAR(255) NULL,
    lga                            VARCHAR(255) NULL,
    phone_number                   VARCHAR(255) NULL,
    email                          VARCHAR(255) NULL,
    owns_state_land                BIT(1) NULL,
    existing_title_no              VARCHAR(255) NULL,
    existing_land_location         VARCHAR(255) NULL,
    is_land_developed              BIT(1) NULL,
    is_assignor_or_assignee        BIT(1) NULL,
    assigned_title_no              VARCHAR(255) NULL,
    assigned_land_location         VARCHAR(255) NULL,
    assigned_date                  date NULL,
    assignee_name_and_address      VARCHAR(255) NULL,
    assigned_land_developed        BIT(1) NULL,
    plot_type                      VARCHAR(255) NULL,
    plot_type_detail               VARCHAR(255) NULL,
    residential_building_type      VARCHAR(255) NULL,
    plot_size                      VARCHAR(255) NULL,
    plot_size_other                VARCHAR(255) NULL,
    application_fee_type           VARCHAR(255) NULL,
    application_fee_amount         DECIMAL NULL,
    other_fees_breakdown           VARCHAR(255) NULL,
    proposed_investment            DECIMAL NULL,
    investment_financing           VARCHAR(255) NULL,
    previous_govt_acquisition      BIT(1) NULL,
    previous_acquisition_location  VARCHAR(255) NULL,
    previous_acquisition_size      VARCHAR(255) NULL,
    previous_acquisition_date      date NULL,
    acquiring_authority            VARCHAR(255) NULL,
    compensation_status            VARCHAR(255) NULL,
    compensation_part_payment      VARCHAR(255) NULL,
    sworn_declaration              LONGTEXT NULL,
    applicant_signature            VARCHAR(255) NULL,
    declaration_date               date NULL,
    illiterate_jurat_attached      BIT(1) NULL,
    passport_photos                VARCHAR(255) NULL,
    tax_clearances                 VARCHAR(255) NULL,
    declaration_of_age             VARCHAR(255) NULL,
    administrative_charges         VARCHAR(255) NULL,
    fee_receipt                    VARCHAR(255) NULL,
    naturalization_doc             VARCHAR(255) NULL,
    oath_declaration               VARCHAR(255) NULL,
    incorporation_certificate_path VARCHAR(255) NULL,
    memorandum_articles_path       VARCHAR(255) NULL,
    director_photo_path            VARCHAR(255) NULL,
    corp_tax_clearance1path        VARCHAR(255) NULL,
    corp_tax_clearance2path        VARCHAR(255) NULL,
    corp_tax_clearance3path        VARCHAR(255) NULL,
    reg_doc1path                   VARCHAR(255) NULL,
    reg_doc2path                   VARCHAR(255) NULL,
    reg_doc3path                   VARCHAR(255) NULL,
    other_doc1path                 VARCHAR(255) NULL,
    other_doc2path                 VARCHAR(255) NULL,
    other_doc3path                 VARCHAR(255) NULL,
    other_doc4path                 VARCHAR(255) NULL,
    latitude DOUBLE NULL,
    longitude DOUBLE NULL,
    altitude DOUBLE NULL,
    gps_accuracy DOUBLE NULL,
    CONSTRAINT pk_statutory_allocation_applications PRIMARY KEY (id)
);

CREATE TABLE statutory_allocations
(
    id                     BIGINT AUTO_INCREMENT NOT NULL,
    applicant_name         VARCHAR(255) NULL,
    applicant_email        VARCHAR(255) NULL,
    passport_photos        VARCHAR(255) NULL,
    tax_clearances         VARCHAR(255) NULL,
    declaration_of_age     VARCHAR(255) NULL,
    administrative_charges VARCHAR(255) NULL,
    processing_fees        VARCHAR(255) NULL,
    status                 VARCHAR(255) NULL,
    created_at             datetime NULL,
    comment                VARCHAR(255) NULL,
    approval_date          date NULL,
    rejection_date         date NULL,
    CONSTRAINT pk_statutory_allocations PRIMARY KEY (id)
);

CREATE TABLE user
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    first_name   VARCHAR(255) NULL,
    last_name    VARCHAR(255) NULL,
    password     VARCHAR(255) NULL,
    email        VARCHAR(255) NULL,
    create_date  date NULL,
    phone_number VARCHAR(255) NULL,
    address      VARCHAR(255) NULL,
    street       VARCHAR(255) NULL,
    city         VARCHAR(255) NULL,
    state        VARCHAR(255) NULL,
    zip          VARCHAR(255) NULL,
    nin          VARCHAR(255) NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE user_roles
(
    id      BIGINT AUTO_INCREMENT NOT NULL,
    user_id BIGINT NULL,
    role_id BIGINT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (id)
);

ALTER TABLE role_permissions
    ADD CONSTRAINT FK_ROLE_PERMISSIONS_ON_PERMISSION FOREIGN KEY (permission_id) REFERENCES permission (id);

ALTER TABLE role_permissions
    ADD CONSTRAINT FK_ROLE_PERMISSIONS_ON_ROLE FOREIGN KEY (role_id) REFERENCES roles (id);

ALTER TABLE services
    ADD CONSTRAINT FK_SERVICES_ON_MDA FOREIGN KEY (mda_id) REFERENCES mda (id);

ALTER TABLE roles_permissions
    ADD CONSTRAINT fk_rolper_on_permission FOREIGN KEY (permissions_id) REFERENCES permission (id);

ALTER TABLE roles_permissions
    ADD CONSTRAINT fk_rolper_on_roles FOREIGN KEY (roles_id) REFERENCES roles (id);