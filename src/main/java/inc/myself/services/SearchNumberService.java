package inc.myself.services;

import inc.myself.models.Result;

public interface SearchNumberService {

    /**
     * поиск заданного числа в файлах
     * @param number заданное число
     * @return модель с кодом результата,
     * списком имен файлов, в которых найдено число,
     * и описанием ошибки в случае ее возникновения
     */
    Result findNumber(Integer number);
}
