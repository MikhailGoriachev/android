-- Создание таблиц базы данных

-- удаление существующих таблиц, работает в MS SQL Server 2016+
drop table if exists editions;

-- таблица Издания
create table editions
(
    _id               integer primary key,
    publication_index nvarchar(20)  not null, -- Индекс издания по каталогу (строка из цифр и букв)
    publication_type  nvarchar(60)  not null, -- Вид издания (газета, журнал, альманах, каталог, …)
    name              nvarchar(120) not null, -- Наименование издания (название газеты, журнала, …)
    price             int           not null, -- Цена 1 экземпляра (в руб.)
    subscribe_date    date          not null, -- Дата начала подписки
    duration          int           not null  -- Длительность подписки (количество месяцев)
);