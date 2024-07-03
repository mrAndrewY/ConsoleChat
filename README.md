# ConsoleChat
Простой консольный чат. \
Каждый раз при подсоединении клиента на сервере создается свой поток. 

#### Для запуска сервера и клиентов надо:
 - поднять базу postgres на локальном хосте, в [db.properties](SocketServer/src/main/resources/db.properties) настроить подключение к поднятой бд.
 - запустить скрипт   [datasql](SocketServer/src/main/resources/db.properties) для создания схемы и таблиц чата.
 - после запуска бд и успешного прогона скрипта можно приступить к компиляции проекта.
 - сервер имеет свой pom файл и компилируется отдельно, клиент также имеет свой pom файл и компилируется отдельно.
 - в корневой директории проекта ConsoleChat в терминале запустить команды \
    _mvn -f SocketClient/pom.xml  clean install && mv SocketClient/target/*.jar ._ \
    _mvn -f SocketServer/pom.xml  clean install && mv SocketServer/target/*.jar ._ 
 -  запустить ceрвер java -jar SocketServer-1.jar (номер порта 8080 прописан в [application.yaml](SocketServer/src/main/resources/application.yaml) его можно поменять на любой другой и перекомпилировать сервер).
 -  запустить клиент java -jar SocketClient-1.jar -p=8080 (запустить более одного раза чтобы был чат :) )

