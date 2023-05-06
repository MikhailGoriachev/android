-- Создание таблиц базы данных

-- удаление таблиц
drop table if exists results;
drop table if exists intermediate_sensor_data;
drop table if exists sensors;

-- создание таблицы Датчики
create table sensors
(
    _id               integer primary key,
    identifier_sensor int,         -- идентификатор датчик
    name              nvarchar(50) -- имя датчик
);

-- создание таблицы Промежуточные данные датчика
create table intermediate_sensor_data
(
    _id            integer primary key,
    sensor_id      int,      -- датчик
    value          float,    -- данные
    exec_date_time datetime, -- дата и время обработки

    constraint fk_intermediate_sensor_data_sensors foreign key (sensor_id) references sensors (_id)
);

-- создание таблицы Результаты обработки
create table results
(
    _id                    integer primary key,
    sensor_id              int,      -- датчик
    amount                 int,      -- количество собранных данных
    min_value              float,    -- минимальное значение
    avg_value              float,    -- среднее значение
    max_value              float,    -- максимальное значение
    start_date_time        datetime, -- начало сбора
    process_data_date_time datetime, -- дата и время обработки

    constraint fk_results_sensors foreign key (sensor_id) references sensors (_id)
);

