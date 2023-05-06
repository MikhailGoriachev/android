-- Создание таблиц бвзы данных

-- начало транзакции
-- begin transaction;

-- удаление существующих таблиц, работает в MS SQL Server 2016+
drop table if exists appointments;
drop table if exists doctors;
drop table if exists patients;
drop table if exists persons;
drop table if exists specialities;


-- Таблица персональных данных, одинаковых для докторов 
-- и пациентов - persons
create table persons
(
    _id        integer primary key,
    surname    nvarchar(60) not null, -- Фамилия персоны
    name       nvarchar(50) not null, -- Имя персоны
    patronymic nvarchar(60) not null  -- Отчество персоны
);

-- таблица -  справочник врачебных специальностей докторов specialities
create table specialities
(
    _id  integer primary key,
    name nvarchar(40) not null -- Название врачебной специальности
);

-- Таблица сведений о докторах ВРАЧИ --> doctors
create table doctors
(
    _id           integer primary key,
    id_person     int   not null, -- Внешний ключ, связь с персональными данными
    id_speciality int   not null, -- Внешний ключ, связь со справочником врачебных специальностей
    price         int   not null, -- Стоимость приема
    percent       float not null, -- Процент отчисления от стоимости приема на зарплату врача

    -- ограничения полей таблицы
    constraint ck_doctors_price check (price > 0),
    constraint ck_doctors_percent check (percent > 0),

    -- внешний ключ - связь 1:1 к таблице persons
    constraint fk_doctors_persons foreign key (id_person) references persons (_id),

    -- внешний ключ - связь M:1 к таблице specialities (e.g.: много докторов одной специальности)  
    constraint fk_doctors_specialities foreign key (id_speciality) references specialities (_id)
);

-- Таблица сведений о пациентах ПАЦИЕНТЫ --> patients
create table patients
(
    _id       integer primary key,
    id_person int          not null, -- Внешний ключ, связь с персональными данными
    born_date date         not null, -- Дата рождения пациента
    address   nvarchar(80) not null, -- Адрес проживания пациента
    passport  nvarchar(15) not null, -- Номер и серия паспорта

-- внешний ключ - связь 1:1 к таблице persons
    constraint fk_patients_persons foreign key (id_person) references persons (_id)
);

-- Таблица сведений о приемах пациентов докторами: ПРИЕМЫ --> appointments  
create table appointments
(
    _id              integer primary key,
    appointment_date date not null,
    id_patient       int  not null,
    id_doctor        int  not null,

    -- внешний ключ - связь M:1 к таблице пациентов
    constraint fk_appointments_patients foreign key (id_patient) references patients (_id),

    -- внешний ключ - связь M:1 к таблице докторов
    constraint fk_appointments_doctors foreign key (id_doctor) references doctors (_id)
);