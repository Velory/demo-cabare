CREATE TABLE bill
(
  id                BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  bill_number       INT              NULL,
  open_bill_time    DATETIME         NOT NULL,
  close_bill_time   DATETIME         NULL,
  employee_id       BIGINT           NOT NULL,
  table_number      INT              NULL,
  number_of_persons INT              NULL,
  discount_id       BIGINT           NULL,
  sale_type_id      INT              NOT NULL,
  pay_type_id       INT              NULL,
  pay_status_id     INT              NOT NULL,
  money_paid        BIGINT           NULL,
  is_opened         BIT DEFAULT b'1' NOT NULL,
  is_active_shift   BIT DEFAULT b'1' NOT NULL
)
  ENGINE = InnoDB, DEFAULT CHARSET = utf8;

CREATE INDEX FKdvi8dqm1x4rp0reelkmr97vus
  ON bill (discount_id);

CREATE INDEX FKle28mnhpamru2e2o22d5u78bu
  ON bill (employee_id);

CREATE TABLE calculation
(
  id                BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  is_archived       BIT DEFAULT b'0' NOT NULL,
  quantity_for_dish DOUBLE           NOT NULL,
  begin_date        DATE             NULL,
  end_date          DATE             NULL,
  raw_material_id   BIGINT           NOT NULL,
  dish_id           BIGINT           NOT NULL
)
  ENGINE = InnoDB, DEFAULT CHARSET = utf8;

CREATE INDEX FK3f10ggsnqermo7etph1270dxw
  ON calculation (dish_id);

CREATE INDEX FKb9fairqyaqfef72r7f4pvb2fy
  ON calculation (raw_material_id);

CREATE TABLE calculation_alternative
(
  id                BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  level             INT    NOT NULL,
  quantity_for_dish DOUBLE NOT NULL,
  raw_material_id   BIGINT NOT NULL,
  calculation_id    BIGINT NOT NULL,
  CONSTRAINT FKssa0oq2oshikx3qrbmi4ubkeo
  FOREIGN KEY (calculation_id) REFERENCES calculation (id)
)
  ENGINE = InnoDB, DEFAULT CHARSET = utf8;

CREATE INDEX FKssa0oq2oshikx3qrbmi4ubkeo
  ON calculation_alternative (calculation_id);

CREATE INDEX FK1artex4saln293wos3a47wmab
  ON calculation_alternative (raw_material_id);

CREATE TABLE department
(
  id   BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  name VARCHAR(255) NOT NULL
)
  ENGINE = InnoDB, DEFAULT CHARSET = utf8;

CREATE TABLE discount
(
  id            BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  card_number   VARCHAR(255) UNIQUE   NOT NULL,
  first_name    VARCHAR(255)          NOT NULL,
  last_name     VARCHAR(255)          NOT NULL,
  gender        VARCHAR(255)          NOT NULL,
  birthday      DATE                  NULL,
  emitted       DATE                  NOT NULL,
  discount_size INT                   NOT NULL,
  total_paid    BIGINT                NULL,
  activated     BIT DEFAULT b'1'      NOT NULL
)
  ENGINE = InnoDB, DEFAULT CHARSET = utf8;

ALTER TABLE bill
  ADD CONSTRAINT FKdvi8dqm1x4rp0reelkmr97vus
FOREIGN KEY (discount_id) REFERENCES discount (id);

CREATE TABLE dish
(
  id               BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  name             VARCHAR(255)      NOT NULL,
  photo            VARCHAR(255),
  dish_out         INT               NULL,
  start_day        INT DEFAULT '0'   NOT NULL,
  end_day          INT DEFAULT '366' NOT NULL,
  price            BIGINT            NULL,
  is_archived      BIT DEFAULT b'0'  NOT NULL,
  dish_category_id BIGINT            NOT NULL
)
  ENGINE = InnoDB, DEFAULT CHARSET = utf8;

CREATE INDEX FKdexet01io54i3kyi50oo9gip2
  ON dish (dish_category_id);

ALTER TABLE calculation
  ADD CONSTRAINT FK3f10ggsnqermo7etph1270dxw
FOREIGN KEY (dish_id) REFERENCES dish (id);

CREATE TABLE dish_category
(
  id       BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  name     VARCHAR(255) NOT NULL,
  photo    VARCHAR(255),
  stock_id BIGINT       NOT NULL,
  CONSTRAINT UK_7d2njk36yguowsk4qoj9ew8kt
  UNIQUE (name)
)
  ENGINE = InnoDB, DEFAULT CHARSET = utf8;
ALTER TABLE dish
  ADD CONSTRAINT FKdexet01io54i3kyi50oo9gip2
FOREIGN KEY (dish_category_id) REFERENCES dish_category (id);


CREATE TABLE document
(
  id                     BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  stock_action_date      DATETIME NOT NULL,
  document_date          DATETIME NOT NULL,
  total_sum              BIGINT   NOT NULL,
  employee_id            BIGINT   NOT NULL,
  stock_action_reason_id BIGINT   NOT NULL,
  bill_id                BIGINT   NULL,
  supplier_id            BIGINT   NULL,
  CONSTRAINT FK8fmi09ps19wbx0kbwqtvtalpm
  FOREIGN KEY (bill_id) REFERENCES bill (id)
)
  ENGINE = InnoDB, DEFAULT CHARSET = utf8;

CREATE INDEX FK8fmi09ps19wbx0kbwqtvtalpm
  ON document (bill_id);

CREATE INDEX FKtdas6stqf1txh63ios5o6y0y8
  ON document (stock_action_reason_id);

CREATE INDEX FKomygo8u4y70ktuj9s2v9jr41q
  ON document (supplier_id);

CREATE TABLE document_item
(
  id              BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  price           BIGINT NOT NULL,
  quantity        DOUBLE NOT NULL,
  stock_id        BIGINT NOT NULL,
  document_id     BIGINT NOT NULL,
  raw_material_id BIGINT NOT NULL,
  CONSTRAINT FK3vttw85g9udp7a1qs083ucq26
  FOREIGN KEY (document_id) REFERENCES document (id)
)
  ENGINE = InnoDB, DEFAULT CHARSET = utf8;

CREATE INDEX FK3vttw85g9udp7a1qs083ucq26
  ON document_item (document_id);

CREATE INDEX FKn3hiphihmsqnsuatm0ia1s16a
  ON document_item (raw_material_id);

CREATE INDEX FKnngitkpqxmv5o1tcrjhculx12
  ON document_item (stock_id);

CREATE TABLE employee
(
  id            BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  name          VARCHAR(255)           NOT NULL,
  position_id   BIGINT                 NOT NULL,
  department_id BIGINT                 NOT NULL,
  start_day     DATE                   NULL,
  fired_day     DATE                   NULL,
  salary_id     BIGINT                 NOT NULL,
  password      VARCHAR(255)           NULL,
  email         VARCHAR(255)           NULL,
  birthday      DATE                   NULL,
  phone         VARCHAR(255)           NULL,
  enabled       BIT DEFAULT b'1'       NOT NULL,
  login         VARCHAR(255)           NULL,
  CONSTRAINT FKbejtwvg9bxus2mffsm3swj3u9
  FOREIGN KEY (department_id) REFERENCES department (id)
)
  ENGINE = InnoDB, DEFAULT CHARSET = utf8;

CREATE INDEX FKbejtwvg9bxus2mffsm3swj3u9
  ON employee (department_id);

CREATE INDEX FKbc8rdko9o9n1ri9bpdyxv3x7i
  ON employee (position_id);

CREATE INDEX FKightkvlpv3s9rmg3mid68bw06
  ON employee (salary_id);

ALTER TABLE bill
  ADD CONSTRAINT FKle28mnhpamru2e2o22d5u78bu
FOREIGN KEY (employee_id) REFERENCES employee (id);

CREATE TABLE employee_role
(
  employee_id BIGINT NOT NULL,
  role_id     BIGINT NOT NULL,
  PRIMARY KEY (employee_id, role_id),
  CONSTRAINT FKo7rvk7ejtx29vru9cyhf7o68a
  FOREIGN KEY (employee_id) REFERENCES employee (id)
)
  ENGINE = InnoDB, DEFAULT CHARSET = utf8;

CREATE INDEX FK7jol9jrbtlt6ctiehegh6besp
  ON employee_role (role_id);

CREATE TABLE expenditure
(
  id                BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  expenditure_title VARCHAR(255) NOT NULL
)
  ENGINE = InnoDB, DEFAULT CHARSET = utf8;

CREATE TABLE order_counter
(
  id      BIGINT NOT NULL
    PRIMARY KEY,
  counter BIGINT NOT NULL
)
  ENGINE = InnoDB, DEFAULT CHARSET = utf8;

CREATE TABLE order_item
(
  id           BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  order_number BIGINT       NOT NULL,
  dish_id      BIGINT       NOT NULL,
  quantity     INT          NOT NULL,
  total_price  BIGINT       NOT NULL,
  comments     VARCHAR(255) NULL,
  bill_id      BIGINT       NOT NULL,
  CONSTRAINT FK5253o0lqoo9kh42pilpijg9x4
  FOREIGN KEY (bill_id) REFERENCES bill (id),
  CONSTRAINT FKs7aplknkrukmckr29wijlvcy1
  FOREIGN KEY (dish_id) REFERENCES dish (id)
)
  ENGINE = InnoDB, DEFAULT CHARSET = utf8;

CREATE INDEX FK5253o0lqoo9kh42pilpijg9x4
  ON order_item (bill_id);

CREATE INDEX FKs7aplknkrukmckr29wijlvcy1
  ON order_item (dish_id);

CREATE TABLE outgo
(
  id             BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  outgo_title    VARCHAR(255) NULL,
  outgo_date     DATE         NULL,
  expenditure_id BIGINT       NULL,
  employee_id    BIGINT       NULL,
  outgo_cost     BIGINT       NULL,
  cost_quantity  INT          NULL,
  CONSTRAINT FKckatmsgi2lua2d95njljtuous
  FOREIGN KEY (employee_id) REFERENCES employee (id),
  CONSTRAINT FKoj2ueh1u8d0fr8esx3t9ouw0s
  FOREIGN KEY (expenditure_id) REFERENCES expenditure (id)
)
  ENGINE = InnoDB, DEFAULT CHARSET = utf8;

CREATE INDEX FKckatmsgi2lua2d95njljtuous
  ON outgo (employee_id);

CREATE INDEX FKoj2ueh1u8d0fr8esx3t9ouw0s
  ON outgo (expenditure_id);

CREATE TABLE position
(
  id             BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  position_title VARCHAR(255) NOT NULL
)
  ENGINE = InnoDB, DEFAULT CHARSET = utf8;

ALTER TABLE employee
  ADD CONSTRAINT FKbc8rdko9o9n1ri9bpdyxv3x7i
FOREIGN KEY (position_id) REFERENCES position (id);

CREATE TABLE privilege
(
  id   BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  name VARCHAR(255) NOT NULL
)
  ENGINE = InnoDB, DEFAULT CHARSET = utf8;

CREATE TABLE raw_material
(
  id           BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  measure_unit VARCHAR(255) NOT NULL,
  name         VARCHAR(255) NOT NULL,
  threshold    INT          NULL
)
  ENGINE = InnoDB, DEFAULT CHARSET = utf8;

ALTER TABLE calculation
  ADD CONSTRAINT FKb9fairqyaqfef72r7f4pvb2fy
FOREIGN KEY (raw_material_id) REFERENCES raw_material (id);

ALTER TABLE calculation_alternative
  ADD CONSTRAINT FK1artex4saln293wos3a47wmab
FOREIGN KEY (raw_material_id) REFERENCES raw_material (id);

ALTER TABLE document_item
  ADD CONSTRAINT FKn3hiphihmsqnsuatm0ia1s16a
FOREIGN KEY (raw_material_id) REFERENCES raw_material (id);

CREATE TABLE role
(
  id   BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  name VARCHAR(255) NOT NULL
)
  ENGINE = InnoDB, DEFAULT CHARSET = utf8;

ALTER TABLE employee_role
  ADD CONSTRAINT FK7jol9jrbtlt6ctiehegh6besp
FOREIGN KEY (role_id) REFERENCES role (id);

CREATE TABLE role_privilege
(
  role_id      BIGINT NOT NULL,
  privilege_id BIGINT NOT NULL,
  PRIMARY KEY (role_id, privilege_id),
  CONSTRAINT FK_RP_ROLE_ID
  FOREIGN KEY (role_id) REFERENCES role (id),
  CONSTRAINT FK_RP_PRIVILEGE_ID
  FOREIGN KEY (privilege_id) REFERENCES privilege (id)
)
  ENGINE = InnoDB, DEFAULT CHARSET = utf8;

CREATE INDEX FK_RP_PRIVILEGE_ID
  ON role_privilege (privilege_id);

CREATE TABLE salary
(
  id           BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  total_salary BIGINT NULL
)
  ENGINE = InnoDB, DEFAULT CHARSET = utf8;

ALTER TABLE employee
  ADD CONSTRAINT FKightkvlpv3s9rmg3mid68bw06
FOREIGN KEY (salary_id) REFERENCES salary (id);

CREATE TABLE shift_status
(
  id              BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  is_cash_closed  BIT DEFAULT b'0' NOT NULL,
  is_shift_closed BIT DEFAULT b'0' NOT NULL
)
  ENGINE = InnoDB, DEFAULT CHARSET = utf8;

CREATE TABLE shift_timetable
(
  id          BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  day_of_week INT    NULL,
  employee_id BIGINT NOT NULL,
  CONSTRAINT FKf3a8mf031ja4e8q2fcumowvsc
  FOREIGN KEY (employee_id) REFERENCES employee (id)
)
  ENGINE = InnoDB, DEFAULT CHARSET = utf8;

CREATE INDEX FKf3a8mf031ja4e8q2fcumowvsc
  ON shift_timetable (employee_id);

CREATE TABLE stock
(
  id    BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  photo VARCHAR(255),
  is_visible BIT DEFAULT b'1' NOT NULL
)
  ENGINE = InnoDB, DEFAULT CHARSET = utf8;

ALTER TABLE document_item
  ADD CONSTRAINT FKnngitkpqxmv5o1tcrjhculx12
FOREIGN KEY (stock_id) REFERENCES stock (id);
ALTER TABLE dish_category
  ADD CONSTRAINT fk_dishcat_stock
FOREIGN KEY (stock_id) REFERENCES stock (id);

CREATE TABLE stock_action_reason
(
  id     BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  reason VARCHAR(255) NOT NULL
)
  ENGINE = InnoDB, DEFAULT CHARSET = utf8;

ALTER TABLE document
  ADD CONSTRAINT FKtdas6stqf1txh63ios5o6y0y8
FOREIGN KEY (stock_action_reason_id) REFERENCES stock_action_reason (id);

CREATE TABLE stock_item
(
  id                  BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  stock_item_quantity DOUBLE DEFAULT 0 NULL,
  raw_material_id     BIGINT           NOT NULL,
  stock_id            BIGINT           NOT NULL,
  CONSTRAINT FKtk18xvviy1yrruy69mpwe6yvg
  FOREIGN KEY (raw_material_id) REFERENCES raw_material (id),
  CONSTRAINT FK1p14bepct2q019cw0nj4onbp1
  FOREIGN KEY (stock_id) REFERENCES stock (id)
)
  ENGINE = InnoDB, DEFAULT CHARSET = utf8;

CREATE INDEX FKtk18xvviy1yrruy69mpwe6yvg
  ON stock_item (raw_material_id);

CREATE INDEX FK1p14bepct2q019cw0nj4onbp1
  ON stock_item (stock_id);

CREATE TABLE stock_item_document
(
  stock_item_id BIGINT NOT NULL,
  document_id   BIGINT NOT NULL,
  PRIMARY KEY (stock_item_id, document_id),
  CONSTRAINT FKt3axoc24lujpnp85fglixarr9
  FOREIGN KEY (stock_item_id) REFERENCES stock_item (id),
  CONSTRAINT FKgwrwpvlqh6hqobwdyychstv93
  FOREIGN KEY (document_id) REFERENCES document (id)
)
  ENGINE = InnoDB, DEFAULT CHARSET = utf8;

CREATE INDEX FKgwrwpvlqh6hqobwdyychstv93
  ON stock_item_document (document_id);

CREATE TABLE supplier
(
  id              BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  supplier_name   VARCHAR(255) NOT NULL,
  deferral        INT          NULL,
  start_contract  DATE         NULL,
  end_contract    DATE         NULL,
  contract_type   VARCHAR(255) NULL,
  contract_number VARCHAR(255) NULL
)
  ENGINE = InnoDB, DEFAULT CHARSET = utf8;

ALTER TABLE document
  ADD CONSTRAINT FKomygo8u4y70ktuj9s2v9jr41q
FOREIGN KEY (supplier_id) REFERENCES supplier (id);

CREATE TABLE verification_token
(
  id          BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  token       VARCHAR(255) NULL,
  employee_id BIGINT       NOT NULL,
  expiry_date DATE         NULL,
  CONSTRAINT FK_VERIFY_EMPLOYEE
  FOREIGN KEY (employee_id) REFERENCES employee (id)
)
  ENGINE = InnoDB, DEFAULT CHARSET = utf8;

CREATE INDEX FK_VERIFY_EMPLOYEE
  ON verification_token (employee_id);

