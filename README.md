Этот проект — REST API сервис для сбора данных от сенсоров, который принимает измерения температуры и погодных условий.  
API реализовано с использованием **Spring Boot**, аутентификация выполнена на основе JWT, а данные хранятся в PostgreSQL.

Инструкция по сборке:
1. В терминале перейдите в root directory и пропишите: "mvn clean install"
2. Пропишите: "mvn clean package -DskipTests"
3. Пропишите: "docker compose up -d"
4. Пропишите: "mvn exec:java "-Dexec.mainClass=com.example.devadil.SensorClient" -e" и на этом все. 



