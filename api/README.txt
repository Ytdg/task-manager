Это что будет всегда (клонируешь с репозитория ТОЛЬКО ПАПКУ "clone" и ничего не в ней не меняешь!
1)импортируешь бд "export.sql" user- postgres; password - 12345678 (может понадобиться)
2)запускашь сервис просто на PhAdmin postgres просто импортируешь бд (export.sql) и проверь что таблица SELECT * FROM users есть (обычный клиент Postgres PhAdmin)
3)Запускаешь сборку docker-compose комманда:  docker-compose up --build -d  (перейди в дирректорию с докер и докер компоуз) комманда сразу соберет и запустить приложение
4)Подожди пару минут и если все гуд docker слушает на localhost:8080 
5)инфа по api ->  http://localhost:8080/api/v1/swagger-ui/index.html#/  (постоянная)

