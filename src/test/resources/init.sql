create table city
(
  id           integer not null,
  name         text    not null,
  country_code char(3) not null,
  district     text    not null,
  population   integer not null
);

create table country
(
  code char(500) not null
    constraint country_pkey
      primary key,
  name           text      not null,
  continent      text      not null,
  region         text      not null,
  surfacearea    real      not null,
  indepyear      smallint,
  population     integer   not null,
  lifeexpectancy real,
  gnp            numeric(10, 2),
  gnpold         numeric(10, 2),
  localname      text      not null,
  governmentform text      not null,
  headofstate    text,
  capital        integer,
  code2          char(2)   not null
);

create table country_language
(
  countrycode char(20) default  not null
      references country,
  language    char(50)  not null,
  isofficial  boolean  default false      not null,
  percentage  real     not null,
    primary key (countrycode, language)
);

INSERT INTO city (id, name, country_code, district, population) VALUES (1, 'Kabul', 'ABW', 'Kabol', 1780000);
INSERT INTO city (id, name, country_code, district, population) VALUES (2, 'Qandahar', 'AFG', 'Qandahar', 237500);
INSERT INTO city (id, name, country_code, district, population) VALUES (3, 'Herat', 'AGO', 'Herat', 186800);
INSERT INTO city (id, name, country_code, district, population) VALUES (4, 'Mazar-e-Sharif', 'AIA', 'Balkh', 127800);
INSERT INTO city (id, name, country_code, district, population) VALUES (34, 'Amsterdam', 'ALB', 'Noord-Holland', 731200);

INSERT INTO country (code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES ('ABW', 'Aruba', 'North America', 'Caribbean', 193, null, 103000, 78.4, 828.00, 793.00, 'Aruba', 'Nonmetropolitan Territory of The Netherlands', 'Beatrix', 129, 'AW');
INSERT INTO country (code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES ('AFG', 'Afghanistan', 'Asia', 'Southern and Central Asia', 652090, 1919, 22720000, 45.9, 5976.00, null, 'Afganistan/Afqanestan', 'Islamic Emirate', 'Mohammad Omar', 1, 'AF');
INSERT INTO country (code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES ('AGO', 'Angola', 'Africa', 'Central Africa', 1246700, 1975, 12878000, 38.3, 6648.00, 7984.00, 'Angola', 'Republic', 'Jos© Eduardo dos Santos', 56, 'AO');
INSERT INTO country (code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES ('AIA', 'Anguilla', 'North America', 'Caribbean', 96, null, 8000, 76.1, 63.20, null, 'Anguilla', 'Dependent Territory of the UK', 'Elisabeth II', 62, 'AI');
INSERT INTO country (code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES ('ALB', 'Albania', 'Europe', 'Southern Europe', 28748, 1912, 3401200, 71.6, 3205.00, 2500.00, 'Shqipria', 'Republic', 'Rexhep Mejdani', 34, 'AL');

INSERT INTO country_language (countrycode, language, isofficial, percentage) VALUES ('ABW', 'French', false, 3.4);
INSERT INTO country_language (countrycode, language, isofficial, percentage) VALUES ('AFG', 'Hindi', false, 1.2);
INSERT INTO country_language (countrycode, language, isofficial, percentage) VALUES ('AGO', 'Marathi', false, 0.7);
INSERT INTO country_language (countrycode, language, isofficial, percentage) VALUES ('AIA', 'Tamil', false, 0.8);
INSERT INTO country_language (countrycode, language, isofficial, percentage) VALUES ('ALB', 'Chichewa', true, 58.3);

commit;

