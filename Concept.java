 private boolean isStarted;
    private final int LIMIT = 20;
    private void test() {
       Log.d("TEST12345", "ddd");
       Object monitor = new Object();
       Counter counter = new Counter(monitor);
       Thread t1 = new Thread(counter,"T1");
       Thread t2 = new Thread(counter,"T2");
       t1.start();
       t2.start();
    }


    private class Counter implements  Runnable{

        private Object monitor = null;
        private boolean firstThread = true;
        private boolean secThread = false;
        private int index = 0;
        public Counter(Object pMonitor){
            monitor = this;
        }

        @Override
        public void run() {
            try{
                while (index <= 50){
                    synchronized (monitor)
                    {
                        String name = Thread.currentThread().getName();
                        if(firstThread && name.equalsIgnoreCase("T1")){
                            firstThread = false;
                            secThread = true;
                            Log.d(TAG,"T1+++ "+((index)));
                            index++;
                            monitor.notifyAll();
                            monitor.wait();
                        }else if(secThread && name.equalsIgnoreCase("T2")){
                            firstThread = true;
                            secThread = false;
                            Log.d(TAG,"T2+++ "+((index)));
                            index++;
                            monitor.notifyAll();
                            monitor.wait();
                        }else{
                            monitor.wait();
                        }
                        Thread.sleep(1000);
                    }
                }
            }catch (Exception e){

            }
        }
    }
