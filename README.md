## QA Mobile (Appium + JUnit 5)

Автоматизированный набор тестов для Android‑приложения **Alfa-Test**. Проект реализован на Java 17, использует Appium + Selenium для управления мобильным клиентом, JUnit 5 для тест-раннера и Allure для построения отчётов.

### Технологии и инструменты

- Java 17, Maven 3.9+
- Appium Java Client 10 + Selenium
- JUnit 5.11
- Allure 2.29

### Структура репозитория

```
src/
└── test/
    ├── java/
    │   ├── annotations/       # Кастомные теги @Smoke/@Regression
    │   ├── config/            # AppiumConfig + device менеджер
    │   ├── listeners/         # TestListener c Allure-аттачами
    │   ├── pages/             # PageObject
    │   ├── steps/             # Allure @Step-обёртки
    │   ├── tests/            
    │   └── utils/             
    └── resources/
        ├── config.properties  # URL Appium + данные приложения
        ├── devices.json       # Каталог доступных девайсов/эмуляторов
        ├── allure.properties  # Пути для Allure
        ├── junit-platform.properties
        └── log4j2.xml
```

### Подготовка окружения

1. Установите Java 17 и Maven 3.9+ (`java -version`, `mvn -version`).
2. Поставьте Android SDK + эмуляторы/реальные устройства и убедитесь, что `adb devices` видит нужные UDID.
3. Установите Appium Server 2.x (`npm i -g appium`) и драйвер `appium driver install uiautomator2`.
4. Запустите Appium на `http://127.0.0.1:4723` или укажите другой адрес в `src/test/resources/config.properties`.

### Конфигурация

- `config.properties`
  - `appiumServerURL` — адрес запущенного Appium.
  - `appPackage`/`appActivity` — тестируемое приложение.
- `devices.json` — список устройств, между которыми `DeviceManager` распределяет потоки. Добавьте новые записи (имя, `platformVersion`, `udid` и т.д.) при необходимости.
- `junit-platform.properties` — управляют параллелизмом.
- Пользовательские аннотации `@Smoke` и `@Regression` добавляют JUnit-теги `smoke/regression` для выборочного запуска.

#### Управление параллельностью через `junit-platform.properties`

Файл `src/test/resources/junit-platform.properties` определяет стратегию параллельного запуска JUnit 5:

```properties
junit.jupiter.execution.parallel.enabled=true
junit.jupiter.execution.parallel.mode.default=concurrent
junit.jupiter.execution.parallel.config.strategy=fixed
junit.jupiter.execution.parallel.config.fixed.parallelism=1
junit.jupiter.execution.parallel.config.fixed.max-pool-size=1
```

- **Включить/выключить параллельный запуск**
  - Полностью отключить параллелизм:
    ```properties
    junit.jupiter.execution.parallel.enabled=false
    ```
  - Включить (как сейчас в проекте):
    ```properties
    junit.jupiter.execution.parallel.enabled=true
    ```

- **Настройка числа одновременно выполняемых тестов**
  - Используется стратегия `fixed`, поэтому степень параллельности задаётся параметрами:
    ```properties
    junit.jupiter.execution.parallel.config.strategy=fixed
    junit.jupiter.execution.parallel.config.fixed.parallelism=4
    junit.jupiter.execution.parallel.config.fixed.max-pool-size=4
    ```
  - Установите оба значения (`parallelism` и `max-pool-size`) равными количеству устройств/эмуляторов, которые вы реально можете использовать одновременно.

### Запуск тестов

Перед первым запуском убедитесь, что:
- Appium сервер запущен и видит устройства из `devices.json`.
- На устройстве установлено тестовое приложение `com.alfabank.qapp`.

Команды Maven:

- Полный прогон:  
  `mvn clean test`

- Только smoke-тесты:  
  `mvn clean test -Djunit.jupiter.tags=smoke`

- Параллельный прогон на N устройствах:

- Прогон с указанием конкретного тестового класса:  
  `mvn -Dtest=tests.login.LoginTests#testSuccessLogin test`

### Allure отчёты

Результаты складываются в `target/allure-results` (пути задаются в `allure.properties`). После прогона:

```
allure serve target/allure-results        # интерактивный просмотр
allure generate target/allure-results \
    --clean -o target/allure-report       # статический отчёт
```

