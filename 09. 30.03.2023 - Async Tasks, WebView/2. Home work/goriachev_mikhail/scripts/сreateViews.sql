-- удаление представлений
drop view if exists view_appointments;
drop view if exists view_doctors;
drop view if exists view_patients;


-- представление таблицы doctors			(ВРАЧИ)

-- удаление представления 
-- drop view if exists view_doctors;
-- 

-- создание представления
create view view_doctors
as
select doctors._id
     , doctors.id_person
     , persons.surname    as person_surname    -- Фамилия врача
     , persons.[Name]     as person_name       -- Имя врача
     , persons.patronymic as person_patronymic -- Отчество врача
     , doctors.id_speciality
     , specialities.name as speciality_name                       -- Специальность
     , doctors.price                           -- Цена приёма
     , doctors.[percent]                       -- Процент от цены приёма врачу
from doctors
         inner join persons on doctors.id_person = persons._id
         inner join specialities on doctors.id_speciality = specialities._id;



-- представление таблицы patients			(ПАЦИЕНТЫ)

-- удаление представления 
-- drop view if exists view_patients;
-- 

-- создание представления
create view view_patients
as
select patients._id
     , patients.id_person
     , persons.surname    as person_surname    -- Фамилия пациента
     , persons.[name]     as person_name       -- Имя пациента
     , persons.patronymic as person_patronymic -- Отчество пациента
     , patients.born_date                       -- Дата рождения пациента
     , patients.[address]                       -- Адрес проживания пациента
     , patients.passport                        -- Паспортные данные
from patients
         inner join persons on patients.id_person = persons._id;


-- представление таблицы appointments		(ПРИЕМЫ)

-- удаление представления 
-- drop view if exists view_appointments;
-- 

-- создание представления
create view view_appointments
as
select appointments._id
     , appointments.appointment_date                   -- Дата приёма
     , doc._id            as doctor_person_id          -- id врача
     , doc.surname        as doctor_person_surname     -- Фамилия врача
     , doc.name           as doctor_person_name        -- Имя врача
     , doc.patronymic     as doctor_person_patronymic  -- Отчество врача										
     , specialities._id   as doctor_speciality_id             -- id специальности
     , specialities.name  as doctor_speciality_name           -- Специальность
     , appointments.id_doctor
     , doctors.price                                   -- Цена приёма
     , doctors.[percent]                               -- Процент от цены приёма врачу
     , appointments.id_patient
     , pat._id            as patient_person_id         -- id пациента
     , pat.surname        as patient_person_surname    -- Фамилия пациента
     , pat.name           as patient_person_name       -- Имя пациента
     , pat.patronymic     as patient_person_patronymic -- Отчество пациента
     , patients.born_date as patient_born_date         -- Дата рождения пациента
     , patients.address   as patient_address           -- Адрес проживания пациента
     , patients.passport  as patient_passport          -- Паспортные данные
from appointments
         inner join (doctors inner join persons doc on doctors.id_person = doc._id
    inner join specialities on doctors.id_speciality = specialities._id)
                    on appointments.id_doctor = doctors._id
         inner join (patients inner join persons pat on patients.id_person = pat._id)
                    on appointments.id_patient = patients._id
