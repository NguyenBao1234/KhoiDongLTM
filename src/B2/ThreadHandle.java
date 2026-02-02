package B2;

public class ThreadHandle
{
    public static void main (String[] args)
    {
        Thread Thread1 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                System.out.println("___Thread1: In so chan___");
                for (int i = 1; i <= 100; i++) if (i % 2 == 0) System.out.println("Thread1 - So chan: " + i);
            }
        });

        Thread Thread2 = new Thread()
        {
            @Override
            public void run()
            {
                System.out.println("___Thread2-In so le___");
                for (int i = 1 ; i <= 100; i++) if(i%2 != 0) System.out.println("Thread2- So le: "+ i);
            }
        };
        Thread2.start();
        Thread1.start();
    }
}
