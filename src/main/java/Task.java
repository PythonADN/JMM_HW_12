import java.util.concurrent.Callable;

public class Task<T> {
    private final Callable<? extends T> callable;

    private volatile T resultValue;
    private volatile MyException exception;

    public Task(Callable<? extends T> callable) {
        this.callable = callable;
    }

    public T get() {
        // если какой-то поток получил исключение то всем кидаем данное исключение
        if (exception != null) {
            throw exception;
        }

        // вызывать Callable одновременно может только один поток, остальные ждут
        // загрузка Callable происходит только один раз
        if (resultValue == null) {
            synchronized(this) {
                if (resultValue == null) {
                    System.out.println("Вызов метода call у Callable");
                    try {
                        resultValue  = callable.call();
                    } catch (Exception e) {
                        throw new MyException("Исключение при выове call у Callable", e);
                    }
                }
            }
        }
        return resultValue;
    }
}
