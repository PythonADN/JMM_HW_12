import java.util.concurrent.Callable;

public class Main {

    public static void main(String[] args) {
        // объект task с заданным аргументом Callable (можно было бы через lambda)
        Task<String> task = new Task<>(
                new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        Thread.sleep(1000);
                        return "Отработка метода call";
                    }
                }
        );
//        Task<String> task2 = new Task<>( () -> { Thread.sleep(1000); return "call";} );

        // тред вызывающий метод get у task
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(task.get());
            }
        };

        // создаём множество данных тредов и запускаем их
        for(int i=0; i<10; i++) {
            new Thread(runnable).start();
        }
    }
}
