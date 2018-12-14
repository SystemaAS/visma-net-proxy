--Insert mapping of SYSPED-kode->Visma.net kode
--Also see ViscrossrKoder.javva

select * from viscrossr
delete from viscrossr

--MOMS for Salg, NO og Ikke NO
INSERT INTO VISCROSSR(svtype, svsysp,svvism)
VALUES 
('MS_NO','3','3'),
('MS_NO','4','5'),
('MS_NO','0','7'),
('MS','3','3'),
('MS','4','52'),
('MS','0','7')

--MOMS for Kjop, NO og Ikke NO
INSERT INTO VISCROSSR(svtype, svsysp,svvism)
VALUES 
('MK_NO','0','0'),
('MK_NO','1','1'),
('MK','0','21'),
('MK','1','86'),
('MK','E','E')

