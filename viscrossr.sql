--Insert mapping of SYSPED-kode->Visma.net kode
--2018-08: MOMSKODE, kan vara MVA_S for Salg and MVA_K for kjop. Se ViscrossrKoder.javva

select * from viscrossr
delete from viscrossr

--MOMS_S for Salg
INSERT INTO VISCROSSR(svtype, svsysp,svvism)
VALUES 
('MVA_S','3','3'),
('MVA_S','4','5'),
('MVA_S','0','7')


--MOMS_K for Kjop
INSERT INTO VISCROSSR(svtype, svsysp,svvism)
VALUES 
('MVA_K','0','0'),
('MVA_K','1','1')