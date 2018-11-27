--Insert mapping of SYSPED-kode->Visma.net kode
--2018-08: MOMSKODE, kan vara MVA_S for Salg and MVA_K for kjop. Se ViscrossrKoder.javva

select * from viscrossr
delete from viscrossr

--MOMS for Salg
INSERT INTO VISCROSSR(svtype, svsysp,svvism)
VALUES 
('MVA_S','3','3'),
('MVA_S','4','5'),
('MVA_S','0','7')


--MOMS for Kjop, NO og Ikke NO
INSERT INTO VISCROSSR(svtype, svsysp,svvism)
VALUES 
('MK_NO','0','0'),
('MK_NO','1','1'),
('MK','0','21'),
('MK','1','86')