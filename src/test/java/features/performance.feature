@performance
Feature: performance test 1, collecting metric to excel file

  Scenario: go through all auction activities and open some other parts of site
    Given Создается файл для метрик

    When Заказчик входит в систему со снятием метрик
    And Заказчик снимает метрики
    And Заказчик создает три аукциона
    And Заказчик получает информацию о созданных аукционах
    Then Заказчик выходит из системы

    When Поставщик "1" входит в систему со снятием метрик
    And Поставщик снимает метрики о заявках
    Then Поставщик подает заявку на участие в торгах со снятием метрик
    Then Поставщик подает заявку на участие в торгах
    And Поставщик подает запрос на разъяснение документации
    And Поставщик снимает метрики о торгах и контрактах
    And Поставщик выходит из системы

    When Поставщик "2" входит в систему
    Then Поставщик подает заявку на участие в торгах
    And Поставщик выходит из системы

    When Администратор входит в систему
    And Администратор открывает страницу ускорения процедур
    Then Администратор ускоряет закупки до этапа рассмотрения заявок
    And Администратор выходит из системы

    When Заказчик входит в систему
    And Заказчик открывает форму протокола рассмотрения заявок со снятием метрик
    Then Заказчик создает протокол рассмотрения двух заявок
    #  And Заказчик открывает форму протокола рассмотрения заявок по закупке с номером
    Then Заказчик создает протокол рассмотрения одной заявки
    #  And Заказчик открывает форму протокола рассмотрения заявок по закупке с номером
    Then Заказчик создает протокол о признании закупки несостоявшейся
    And Заказчик выходит из системы

    When Администратор входит в систему
    And Администратор открывает страницу ускорения процедур
    Then Администратор ускоряет закупку до этапа начала торгов
    And Администратор выходит из системы

    When Поставщик "1" входит в систему
    And Поставщик открывает страницу подачи ценовых предложений
    Then Поставщик подает ценовое предложение на аукционе со снятием метрик
    And Поставщик выходит из системы

    When Поставщик "2" входит в систему
    And Поставщик открывает страницу подачи ценовых предложений
    Then Поставщик подает ценовое предложение на аукционе
    And Поставщик выходит из системы

    When Администратор входит в систему
    And Администратор открывает страницу ускорения процедур
    Then Администратор ускоряет закупку до этапа подведения итогов торгов
    And Администратор выходит из системы

    When Заказчик входит в систему
    And Заказчик открывает форму протокола подведения итогов торгов
    Then Заказчик создает протокол подведения итогов торгов
    And Заказчик отправляет контракт на подпись поставщику
    And Заказчик выходит из системы

    When Поставщик "2" входит в систему
    And Поставщик открывает контракт через треугольник 'Подпишите контракт' со снятием метрик
    Then Поставщик создает протокол разногласий
    And Поставщик выходит из системы

    When Заказчик входит в систему
    And Заказчик открывает контракт через треугольник 'Переотправьте контракт'
    Then Заказчик вносит изменения в контракт
    And Заказчик выходит из системы

    When Поставщик "2" входит в систему
    And Поставщик открывает контракт через треугольник 'Подпишите контракт'
    Then Поставщик подписывает контракт
    And Поставщик выходит из системы

    When Администратор входит в систему
    And Администратор открывает страницу сроков подписания контракта
    Then Администратор сдвигает регламентный срок подписания контракта
    And Администратор выходит из системы

    When Заказчик входит в систему
    And Заказчик открывает контракт через треугольник 'Подпишите контракт'
    Then Заказчик подписывает контракт
    And Заказчик выходит из системы


    #When Поставщик безденежных выходит в систему
    #And Поставщик делает поиск закупок
    #Then Поставщик подает отложенные заявки



    Then Закрытие файла отчета