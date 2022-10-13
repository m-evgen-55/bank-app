package com.demo.bankapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankAppApplication.class, args);
    }
}

// TODO вынести переменные в property
// TODO сделать ролевую модель пользователям через Spring Security
// TODO упаковать приложение в Docker

// TODO сделать фронт:
//  - общая страница где видно список клиентов.
//  на ней можно создать клиента (впоследствии сделать удаление клиента из под роли админа)
//  (впоследствии сделать создание пользователя из под поли админа)
//  - возможно провалиться в карточку клиента:
//  в карточке клиента можно обновить информацию о клиенте, открыть счет, взять кредит
//  - провалившись в карточку счета можно снять и положить деньги на счет
//  - страница логина

// TODO сделать второй сервис и вызывать его через клиента
// TODO отредактировать README
