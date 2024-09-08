CREATE TABLE IF NOT EXISTS operatoriregistrati (
    username VARCHAR(100),
    password VARCHAR(100),
    centro VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS parametriclimatici (
    centro VARCHAR(100),
    area VARCHAR(100),
    vento NUMERIC(100),
    umidità NUMERIC(100),
    pressione NUMERIC(100),
    temperatura NUMERIC(100),
    precipitazioni NUMERIC(100),
    altitudine_ghiacciai NUMERIC(100),
    massa_ghiacciai NUMERIC(100),
    note VARCHAR(256),
    data VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS centrimonitoraggio (
    nome VARCHAR(100),
    indirizzo VARCHAR(255),
    area1 VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS coordinatemonitoraggio (
    geoname_id INTEGER,
    name VARCHAR(100),
    ascii_name VARCHAR(100),
    country_code VARCHAR(100),
    country_name VARCHAR(100),
    coordinates VARCHAR(100)
);
