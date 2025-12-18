```Markdown
1. Facade (Фасад)
Класс FinanceFacade.
Служит единой точкой входа для работы с данными. Скрывает сложность управления списками и логику пересчета баланса от остальной части системы.

3. Factory Method (Фабричный метод)
Класс FinanceFactory.
Централизованно создает объекты BankAccount, Operation и Category.

3. Command (Команда)
Интерфейс TransactionCommand и классы CreateOperationCommand, EditAccountCommand и др.
Каждое действие пользователя инкапсулировано в отдельный объект.

5. Decorator (Декоратор)
Класс TimingDecorator.
Оборачивает любую команду для замера времени её выполнения.

5. DTO (Data Transfer Object)
Классы BankAccountOdt, OperationOdt.
Перенос данных между слоем UI и слоем бизнес-логики.

6. Singleton (Одиночка)
Все классы с аннотациями @Service и @Component (Например UIService).
Spring по умолчанию создает только один экземпляр этих классов на всё приложение.

7. Dependency Injection (Внедрение зависимостей)
Конструкторы классов, где передаются FinanceFacade или FinanceFactory.

8. Repository (Репозиторий)
Внутри FinanceFacade (твои List<BankAccount>, List<Operation>).

9. Iterator (Итератор)
В методах фильтрации списков (абстракция для обхода).

10. Proxy (Прокси)
Прокси объекты от анотаций Spring
```
Функционал по требованиям.
```Markdown
S - Single Responsibility Principle (Принцип единственной ответственности)
UIService отвечает только за ввод/вывод данных.
FinanceFacade отвечает только за управление коллекциями и целостность.
FinanceFactory отвечает только за инстанцирование объектов.

O - Open/Closed Principle (Принцип открытости/закрытости)
Благодаря паттерну Command, мы можем добавлять новые функции в систему (например, «Перевод между счетами»), 
создав новый класс команды, не меняя существующий код фасада или UI.

L - Liskov Substitution Principle (Принцип подстановки Барбары Лисков)
TimingDecorator реализует интерфейс TransactionCommand. 
В любом месте программы мы можем заменить обычную команду на декорированную, и система продолжит работать корректно.

I - Interface Segregation Principle (Принцип разделения интерфейса)
Команды используют минималистичный интерфейс TransactionCommand только с одним методом execute(), что не заставляет их реализовывать лишние методы.

D - Dependency Inversion Principle (Принцип инверсии зависимостей)
Используется Spring Framework для внедрения зависимостей DI.
```
Запуск:
Сборка командой ```./gradlew clean build``` и запуск исполняемого файла
Или запуск в менеджере gradle с помощью ```./gradlew run```
