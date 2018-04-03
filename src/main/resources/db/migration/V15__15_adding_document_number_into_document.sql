ALTER TABLE document ADD document_number VARCHAR(255);

ALTER TABLE `cabaredb`.`document`
  ADD CONSTRAINT `FKq13cdsfasmo3k23c32aeq0qnw`
FOREIGN KEY (`employee_id`)
REFERENCES `cabaredb`.`employee` (`id`);

ALTER TABLE `cabaredb`.`document`
DROP FOREIGN KEY `FKq13cdsfasmo3k23c32aeq0qnw`;
ALTER TABLE `cabaredb`.`document`
CHANGE COLUMN `employee_id` `employee_id` BIGINT(20) NULL ;


INSERT INTO `cabaredb`.`stock_action_reason` (`id`, `reason`) VALUES ('1', 'Приходная накладная');
INSERT INTO `cabaredb`.`stock_action_reason` (`id`, `reason`) VALUES ('2', 'Списание по счету');