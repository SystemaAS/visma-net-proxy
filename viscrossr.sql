--Insert mapping of SYSPED-kode->Visma.net kode
--2018-08: MOMSKODE

select * from viscrossr
delete from viscrossr

INSERT INTO VISCROSSR(svtype, svsysp,svvism)
VALUES 
('MOMSK','0','7'),
('MOMSK','5','7'),
('MOMSK','2','20')