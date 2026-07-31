
SET @now = NOW();

-- ---------------------------------------------------------------------
-- 1. ABA NORTH
-- ---------------------------------------------------------------------
SET @lga_id = UUID();
INSERT INTO local_governments (id, name, headquarters, created_at, updated_at)
VALUES (@lga_id, 'Aba North', 'Eziama Uratta', @now, @now);

INSERT INTO wards (id, name, local_government_id, created_at, updated_at) VALUES
                                                                              (UUID(), 'Eziama',                     @lga_id, @now, @now),
                                                                              (UUID(), 'Industrial Area',            @lga_id, @now, @now),
                                                                              (UUID(), 'Osusu I',                    @lga_id, @now, @now),
                                                                              (UUID(), 'Osusu II',                   @lga_id, @now, @now),
                                                                              (UUID(), 'St. Eugenes By Okigwe Rd',   @lga_id, @now, @now),
                                                                              (UUID(), 'Uratta',                     @lga_id, @now, @now),
                                                                              (UUID(), 'Old Aba GRA',                @lga_id, @now, @now),
                                                                              (UUID(), 'Umuola',                     @lga_id, @now, @now),
                                                                              (UUID(), 'Ariaria Market',             @lga_id, @now, @now),
                                                                              (UUID(), 'Ogbor I',                    @lga_id, @now, @now),
                                                                              (UUID(), 'Ogbor II',                   @lga_id, @now, @now),
                                                                              (UUID(), 'Umuogor',                    @lga_id, @now, @now);

-- ---------------------------------------------------------------------
-- 2. ABA SOUTH
-- ---------------------------------------------------------------------
SET @lga_id = UUID();
INSERT INTO local_governments (id, name, headquarters, created_at, updated_at)
VALUES (@lga_id, 'Aba South', 'Aba', @now, @now);

INSERT INTO wards (id, name, local_government_id, created_at, updated_at) VALUES
                                                                              (UUID(), 'Eziukwu',       @lga_id, @now, @now),
                                                                              (UUID(), 'Asa',           @lga_id, @now, @now),
                                                                              (UUID(), 'Enyiama',       @lga_id, @now, @now),
                                                                              (UUID(), 'Ngwa',          @lga_id, @now, @now),
                                                                              (UUID(), 'Ohazu I',       @lga_id, @now, @now),
                                                                              (UUID(), 'Ohazu II',      @lga_id, @now, @now),
                                                                              (UUID(), 'Igwebuike',     @lga_id, @now, @now),
                                                                              (UUID(), 'Ekeoha',        @lga_id, @now, @now),
                                                                              (UUID(), 'Glouchester',   @lga_id, @now, @now),
                                                                              (UUID(), 'Mosque',        @lga_id, @now, @now),
                                                                              (UUID(), 'Aba River',     @lga_id, @now, @now),
                                                                              (UUID(), 'Aba Town Hall', @lga_id, @now, @now);

-- ---------------------------------------------------------------------
-- 3. AROCHUKWU
-- ---------------------------------------------------------------------
SET @lga_id = UUID();
INSERT INTO local_governments (id, name, headquarters, created_at, updated_at)
VALUES (@lga_id, 'Arochukwu', 'Arochukwu', @now, @now);

INSERT INTO wards (id, name, local_government_id, created_at, updated_at) VALUES
                                                                              (UUID(), 'Ovukwu',            @lga_id, @now, @now),
                                                                              (UUID(), 'Ohaeke',            @lga_id, @now, @now),
                                                                              (UUID(), 'Ohafor I',          @lga_id, @now, @now),
                                                                              (UUID(), 'Ohafor II',         @lga_id, @now, @now),
                                                                              (UUID(), 'Arochukwu I',       @lga_id, @now, @now),
                                                                              (UUID(), 'Arochukwu II',      @lga_id, @now, @now),
                                                                              (UUID(), 'Ikwun Ihechiowa',   @lga_id, @now, @now),
                                                                              (UUID(), 'Eleoha Ihechiowa',  @lga_id, @now, @now),
                                                                              (UUID(), 'Ututu',             @lga_id, @now, @now),
                                                                              (UUID(), 'Isu',               @lga_id, @now, @now),
                                                                              (UUID(), 'Arochukwu III',     @lga_id, @now, @now);

-- ---------------------------------------------------------------------
-- 4. BENDE
-- ---------------------------------------------------------------------
SET @lga_id = UUID();
INSERT INTO local_governments (id, name, headquarters, created_at, updated_at)
VALUES (@lga_id, 'Bende', 'Bende Town', @now, @now);

INSERT INTO wards (id, name, local_government_id, created_at, updated_at) VALUES
                                                                              (UUID(), 'Aminkalu/Akoliufu',   @lga_id, @now, @now),
                                                                              (UUID(), 'Bende',               @lga_id, @now, @now),
                                                                              (UUID(), 'Ugwueke/Ezeukwu',     @lga_id, @now, @now),
                                                                              (UUID(), 'Igbere A',            @lga_id, @now, @now),
                                                                              (UUID(), 'Igbere B',            @lga_id, @now, @now),
                                                                              (UUID(), 'Item A',              @lga_id, @now, @now),
                                                                              (UUID(), 'Item B',              @lga_id, @now, @now),
                                                                              (UUID(), 'Item C',              @lga_id, @now, @now),
                                                                              (UUID(), 'Itumbauzo',           @lga_id, @now, @now),
                                                                              (UUID(), 'Ozuitem',             @lga_id, @now, @now),
                                                                              (UUID(), 'Umuhu/Ezechi',        @lga_id, @now, @now),
                                                                              (UUID(), 'Umu-Imenyi',          @lga_id, @now, @now),
                                                                              (UUID(), 'Uzuakoli',            @lga_id, @now, @now);

-- ---------------------------------------------------------------------
-- 5. IKWUANO
-- ---------------------------------------------------------------------
SET @lga_id = UUID();
INSERT INTO local_governments (id, name, headquarters, created_at, updated_at)
VALUES (@lga_id, 'Ikwuano', 'Isiala Oboro', @now, @now);

INSERT INTO wards (id, name, local_government_id, created_at, updated_at) VALUES
                                                                              (UUID(), 'Oloko I',    @lga_id, @now, @now),
                                                                              (UUID(), 'Oloko II',   @lga_id, @now, @now),
                                                                              (UUID(), 'Ibere I',    @lga_id, @now, @now),
                                                                              (UUID(), 'Ibere II',   @lga_id, @now, @now),
                                                                              (UUID(), 'Oboro I',    @lga_id, @now, @now),
                                                                              (UUID(), 'Oboro II',   @lga_id, @now, @now),
                                                                              (UUID(), 'Oboro III',  @lga_id, @now, @now),
                                                                              (UUID(), 'Oboro IV',   @lga_id, @now, @now),
                                                                              (UUID(), 'Ariam',      @lga_id, @now, @now),
                                                                              (UUID(), 'Usaka',      @lga_id, @now, @now);

-- ---------------------------------------------------------------------
-- 6. ISIALA NGWA NORTH
-- ---------------------------------------------------------------------
SET @lga_id = UUID();
INSERT INTO local_governments (id, name, headquarters, created_at, updated_at)
VALUES (@lga_id, 'Isiala Ngwa North', 'Okpuala-Ngwa', @now, @now);

INSERT INTO wards (id, name, local_government_id, created_at, updated_at) VALUES
                                                                              (UUID(), 'Amasaa Nsulu',            @lga_id, @now, @now),
                                                                              (UUID(), 'Umunna Nsulu',            @lga_id, @now, @now),
                                                                              (UUID(), 'Isiala Nsulu',            @lga_id, @now, @now),
                                                                              (UUID(), 'Ngwa Ukwu II',            @lga_id, @now, @now),
                                                                              (UUID(), 'Ngwa Ukwu I',             @lga_id, @now, @now),
                                                                              (UUID(), 'Ijie',                    @lga_id, @now, @now),
                                                                              (UUID(), 'Amasaa Ntigha',           @lga_id, @now, @now),
                                                                              (UUID(), 'Amapu Ntigha',            @lga_id, @now, @now),
                                                                              (UUID(), 'Umuoha',                  @lga_id, @now, @now),
                                                                              (UUID(), 'Mbawsi/Umuoma Inta',      @lga_id, @now, @now);

-- ---------------------------------------------------------------------
-- 7. ISIALA NGWA SOUTH
-- ---------------------------------------------------------------------
SET @lga_id = UUID();
INSERT INTO local_governments (id, name, headquarters, created_at, updated_at)
VALUES (@lga_id, 'Isiala Ngwa South', 'Omoba', @now, @now);

INSERT INTO wards (id, name, local_government_id, created_at, updated_at) VALUES
                                                                              (UUID(), 'Amaise/Amaise Aiyaba',    @lga_id, @now, @now),
                                                                              (UUID(), 'Ngwaobi',                 @lga_id, @now, @now),
                                                                              (UUID(), 'Mbutu Ukwu',              @lga_id, @now, @now),
                                                                              (UUID(), 'Mbutu Ngwa',              @lga_id, @now, @now),
                                                                              (UUID(), 'Ehi Na Guru Osokwa',      @lga_id, @now, @now),
                                                                              (UUID(), 'Akunekpu Eziama',         @lga_id, @now, @now),
                                                                              (UUID(), 'Na Obuba',                @lga_id, @now, @now),
                                                                              (UUID(), 'Ovungwu',                 @lga_id, @now, @now),
                                                                              (UUID(), 'Ovuokwu',                 @lga_id, @now, @now),
                                                                              (UUID(), 'Okporo Ahaba',            @lga_id, @now, @now);

-- ---------------------------------------------------------------------
-- 8. ISUIKWUATO
-- ---------------------------------------------------------------------
SET @lga_id = UUID();
INSERT INTO local_governments (id, name, headquarters, created_at, updated_at)
VALUES (@lga_id, 'Isuikwuato', 'Mbalano', @now, @now);

INSERT INTO wards (id, name, local_government_id, created_at, updated_at) VALUES
                                                                              (UUID(), 'Imenyi',            @lga_id, @now, @now),
                                                                              (UUID(), 'Ezere',             @lga_id, @now, @now),
                                                                              (UUID(), 'Isiala-Amawu',      @lga_id, @now, @now),
                                                                              (UUID(), 'Isu-Amawu',         @lga_id, @now, @now),
                                                                              (UUID(), 'Oguduasa',          @lga_id, @now, @now),
                                                                              (UUID(), 'Umunnekwu',         @lga_id, @now, @now),
                                                                              (UUID(), 'Achara/Mbaugwu',    @lga_id, @now, @now),
                                                                              (UUID(), 'Ikeagha I',         @lga_id, @now, @now),
                                                                              (UUID(), 'Ikeagha II',        @lga_id, @now, @now),
                                                                              (UUID(), 'Umuanyi/Absu',      @lga_id, @now, @now);

-- ---------------------------------------------------------------------
-- 9. OBI NGWA
-- ---------------------------------------------------------------------
SET @lga_id = UUID();
INSERT INTO local_governments (id, name, headquarters, created_at, updated_at)
VALUES (@lga_id, 'Obi Ngwa', 'Mgboko', @now, @now);

INSERT INTO wards (id, name, local_government_id, created_at, updated_at) VALUES
                                                                              (UUID(), 'Abayi I',                    @lga_id, @now, @now),
                                                                              (UUID(), 'Abayi II',                   @lga_id, @now, @now),
                                                                              (UUID(), 'Mgboko Umuanunu',            @lga_id, @now, @now),
                                                                              (UUID(), 'Mgboko Itun Gwa',            @lga_id, @now, @now),
                                                                              (UUID(), 'Ahiaba',                     @lga_id, @now, @now),
                                                                              (UUID(), 'Mgboko Amairi',              @lga_id, @now, @now),
                                                                              (UUID(), 'Alaukwu Ohanze',             @lga_id, @now, @now),
                                                                              (UUID(), 'Akumaimo',                   @lga_id, @now, @now),
                                                                              (UUID(), 'Ndiakata/Amairinabua',       @lga_id, @now, @now),
                                                                              (UUID(), 'Ntighauzo Amairi',           @lga_id, @now, @now),
                                                                              (UUID(), 'Ibeme',                      @lga_id, @now, @now);

-- ---------------------------------------------------------------------
-- 10. OHAFIA
-- ---------------------------------------------------------------------
SET @lga_id = UUID();
INSERT INTO local_governments (id, name, headquarters, created_at, updated_at)
VALUES (@lga_id, 'Ohafia', 'Eben Ohafia', @now, @now);

INSERT INTO wards (id, name, local_government_id, created_at, updated_at) VALUES
                                                                              (UUID(), 'Isama Ohafia',        @lga_id, @now, @now),
                                                                              (UUID(), 'Ebem Ohafia',         @lga_id, @now, @now),
                                                                              (UUID(), 'Ndi Elu Nkporo',      @lga_id, @now, @now),
                                                                              (UUID(), 'Ndi Etiti Nkporo',    @lga_id, @now, @now),
                                                                              (UUID(), 'Amaeke Abiriba',      @lga_id, @now, @now),
                                                                              (UUID(), 'Amaogudu Abiriba',    @lga_id, @now, @now),
                                                                              (UUID(), 'Agboji Abiriba',      @lga_id, @now, @now),
                                                                              (UUID(), 'Ohafor Ohafia',       @lga_id, @now, @now),
                                                                              (UUID(), 'Okamu Ohafia',        @lga_id, @now, @now),
                                                                              (UUID(), 'Ania Ohafia',         @lga_id, @now, @now),
                                                                              (UUID(), 'Ndi Agbo Nkporo',     @lga_id, @now, @now);

-- ---------------------------------------------------------------------
-- 11. OSISIOMA NGWA
-- ---------------------------------------------------------------------
SET @lga_id = UUID();
INSERT INTO local_governments (id, name, headquarters, created_at, updated_at)
VALUES (@lga_id, 'Osisioma Ngwa', 'Osisioma', @now, @now);

INSERT INTO wards (id, name, local_government_id, created_at, updated_at) VALUES
                                                                              (UUID(), 'Amavo',                       @lga_id, @now, @now),
                                                                              (UUID(), 'Amaitolu-Mbutu Umuojima',     @lga_id, @now, @now),
                                                                              (UUID(), 'Amasator',                    @lga_id, @now, @now),
                                                                              (UUID(), 'Aro-Ngwa',                    @lga_id, @now, @now),
                                                                              (UUID(), 'Ama-Asaa',                    @lga_id, @now, @now),
                                                                              (UUID(), 'Oso-Okwa',                    @lga_id, @now, @now),
                                                                              (UUID(), 'Uratta',                      @lga_id, @now, @now),
                                                                              (UUID(), 'Amator',                      @lga_id, @now, @now),
                                                                              (UUID(), 'Umunneise',                   @lga_id, @now, @now),
                                                                              (UUID(), 'Okpu-Umuobo',                 @lga_id, @now, @now);

-- ---------------------------------------------------------------------
-- 12. UGWUNAGBO
-- ---------------------------------------------------------------------
SET @lga_id = UUID();
INSERT INTO local_governments (id, name, headquarters, created_at, updated_at)
VALUES (@lga_id, 'Ugwunagbo', 'Ugwunagbo', @now, @now);

INSERT INTO wards (id, name, local_government_id, created_at, updated_at) VALUES
                                                                              (UUID(), 'Ward One',    @lga_id, @now, @now),
                                                                              (UUID(), 'Ward Two',    @lga_id, @now, @now),
                                                                              (UUID(), 'Ward Three',  @lga_id, @now, @now),
                                                                              (UUID(), 'Ward Four',   @lga_id, @now, @now),
                                                                              (UUID(), 'Ward Five',   @lga_id, @now, @now),
                                                                              (UUID(), 'Ward Six',    @lga_id, @now, @now),
                                                                              (UUID(), 'Ward Seven',  @lga_id, @now, @now),
                                                                              (UUID(), 'Ward Eight',  @lga_id, @now, @now),
                                                                              (UUID(), 'Ward Nine',   @lga_id, @now, @now),
                                                                              (UUID(), 'Ward Ten',    @lga_id, @now, @now);

-- ---------------------------------------------------------------------
-- 13. UKWA EAST
-- ---------------------------------------------------------------------
SET @lga_id = UUID();
INSERT INTO local_governments (id, name, headquarters, created_at, updated_at)
VALUES (@lga_id, 'Ukwa East', 'Akwete', @now, @now);

INSERT INTO wards (id, name, local_government_id, created_at, updated_at) VALUES
                                                                              (UUID(), 'Ikwuriator East',     @lga_id, @now, @now),
                                                                              (UUID(), 'Ikuriator West',      @lga_id, @now, @now),
                                                                              (UUID(), 'Azumini',             @lga_id, @now, @now),
                                                                              (UUID(), 'Umuigube Achara',     @lga_id, @now, @now),
                                                                              (UUID(), 'Akwete',              @lga_id, @now, @now),
                                                                              (UUID(), 'Obohia',              @lga_id, @now, @now),
                                                                              (UUID(), 'Ikwueke East',        @lga_id, @now, @now),
                                                                              (UUID(), 'Ikwueke West',        @lga_id, @now, @now),
                                                                              (UUID(), 'Nkporobe/Ohuru',      @lga_id, @now, @now),
                                                                              (UUID(), 'Ikwuorie',            @lga_id, @now, @now);

-- ---------------------------------------------------------------------
-- 14. UKWA WEST
-- ---------------------------------------------------------------------
SET @lga_id = UUID();
INSERT INTO local_governments (id, name, headquarters, created_at, updated_at)
VALUES (@lga_id, 'Ukwa West', 'Obehie', @now, @now);

INSERT INTO wards (id, name, local_government_id, created_at, updated_at) VALUES
                                                                              (UUID(), 'Asa North',    @lga_id, @now, @now),
                                                                              (UUID(), 'Obokwe',       @lga_id, @now, @now),
                                                                              (UUID(), 'Ogwe',         @lga_id, @now, @now),
                                                                              (UUID(), 'Asa South',    @lga_id, @now, @now),
                                                                              (UUID(), 'Obuzor',       @lga_id, @now, @now),
                                                                              (UUID(), 'Ipu West',     @lga_id, @now, @now),
                                                                              (UUID(), 'Ipu East',     @lga_id, @now, @now),
                                                                              (UUID(), 'Ipu South',    @lga_id, @now, @now),
                                                                              (UUID(), 'Ozaa Ukwu',    @lga_id, @now, @now),
                                                                              (UUID(), 'Ozaa West',    @lga_id, @now, @now);

-- ---------------------------------------------------------------------
-- 15. UMUAHIA NORTH
-- ---------------------------------------------------------------------
SET @lga_id = UUID();
INSERT INTO local_governments (id, name, headquarters, created_at, updated_at)
VALUES (@lga_id, 'Umuahia North', 'Umuahia', @now, @now);

INSERT INTO wards (id, name, local_government_id, created_at, updated_at) VALUES
                                                                              (UUID(), 'Ibeku East I',        @lga_id, @now, @now),
                                                                              (UUID(), 'Ibeku East II',       @lga_id, @now, @now),
                                                                              (UUID(), 'Ndume',               @lga_id, @now, @now),
                                                                              (UUID(), 'Ibeku West',          @lga_id, @now, @now),
                                                                              (UUID(), 'Umuahia Urban I',     @lga_id, @now, @now),
                                                                              (UUID(), 'Umuahia Urban II',    @lga_id, @now, @now),
                                                                              (UUID(), 'Umuahia Urban III',   @lga_id, @now, @now),
                                                                              (UUID(), 'Nkwoachara',          @lga_id, @now, @now),
                                                                              (UUID(), 'Nkwoegwu',            @lga_id, @now, @now),
                                                                              (UUID(), 'Afugiri',             @lga_id, @now, @now),
                                                                              (UUID(), 'Umuhu',               @lga_id, @now, @now),
                                                                              (UUID(), 'Isingwu',             @lga_id, @now, @now);

-- ---------------------------------------------------------------------
-- 16. UMUAHIA SOUTH
-- ---------------------------------------------------------------------
SET @lga_id = UUID();
INSERT INTO local_governments (id, name, headquarters, created_at, updated_at)
VALUES (@lga_id, 'Umuahia South', 'Apumiri, Ubakala', @now, @now);

INSERT INTO wards (id, name, local_government_id, created_at, updated_at) VALUES
                                                                              (UUID(), 'Ezeleke/Ogbo Diukwu',   @lga_id, @now, @now),
                                                                              (UUID(), 'Omaegwu',               @lga_id, @now, @now),
                                                                              (UUID(), 'Ohiaocha',              @lga_id, @now, @now),
                                                                              (UUID(), 'Amiaukwu I',            @lga_id, @now, @now),
                                                                              (UUID(), 'Amiaukwu II',           @lga_id, @now, @now),
                                                                              (UUID(), 'Old Umuahia',           @lga_id, @now, @now),
                                                                              (UUID(), 'Amakama',               @lga_id, @now, @now),
                                                                              (UUID(), 'Ubakala A',             @lga_id, @now, @now),
                                                                              (UUID(), 'Nsirimo',               @lga_id, @now, @now),
                                                                              (UUID(), 'Ubakala B',             @lga_id, @now, @now);

-- ---------------------------------------------------------------------
-- 17. UMU-NNEOCHI
-- ---------------------------------------------------------------------
SET @lga_id = UUID();
INSERT INTO local_governments (id, name, headquarters, created_at, updated_at)
VALUES (@lga_id, 'Umu-Nneochi', 'Nkwoegwu', @now, @now);

INSERT INTO wards (id, name, local_government_id, created_at, updated_at) VALUES
                                                                              (UUID(), 'Amuda',                                @lga_id, @now, @now),
                                                                              (UUID(), 'Umuaku',                               @lga_id, @now, @now),
                                                                              (UUID(), 'Mbala/Achara',                         @lga_id, @now, @now),
                                                                              (UUID(), 'Ezin Godo',                            @lga_id, @now, @now),
                                                                              (UUID(), 'Ndiawa/Umuelem/Ihie/Amorie',           @lga_id, @now, @now),
                                                                              (UUID(), 'Eziama-Ugwu',                          @lga_id, @now, @now),
                                                                              (UUID(), 'Eziama-Agbo',                          @lga_id, @now, @now),
                                                                              (UUID(), 'Ubahu/Akawa/Aroikpa',                  @lga_id, @now, @now),
                                                                              (UUID(), 'Umuchieze I',                          @lga_id, @now, @now),
                                                                              (UUID(), 'Umuchieze II',                         @lga_id, @now, @now),
                                                                              (UUID(), 'Umuchieze III',                        @lga_id, @now, @now),
                                                                              (UUID(), 'Obinolu/Obiagu/Lomara',                @lga_id, @now, @now);

-- =====================================================================
-- Sanity check (run manually after migration, not part of the script):
--
-- SELECT lg.name, COUNT(w.id) AS ward_count
-- FROM local_governments lg
-- LEFT JOIN wards w ON w.local_government_id = lg.id
-- GROUP BY lg.name
-- ORDER BY lg.name;
--
-- SELECT COUNT(*) FROM local_governments;   -- expect 17
-- SELECT COUNT(*) FROM wards;                -- expect 267
-- =====================================================================