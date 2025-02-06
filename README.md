Этот проект — REST API сервис для сбора данных от сенсоров, который принимает измерения температуры и погодных условий.  
API реализовано с использованием **Spring Boot**, аутентификация выполнена на основе JWT, а данные хранятся в PostgreSQL.

Инструкция по сборке:
1. Сначала откройте pgadmin4(это postgresql) -> Register -> Server -> Connection и тут напишите в "Host name/address": ep-mute-smoke-a4ewa1pw-pooler.us-east-1.aws.neon.tech, "port": 5432, "Maintenance database": neondb, "Username": neondb_owner, "Password": npg_SjYRhnmv4gt1 и нажмите на кнопку "Save".
2. В терминале перейдите в root directory и пропишите: "mvn clean install"
3. Пропишите: "mvn clean package -DskipTests"
4. Пропишите: "docker compose up -d"
5. Пропишите: "mvn exec:java "-Dexec.mainClass=com.example.devadil.SensorClient" -e" и на этом все. 
