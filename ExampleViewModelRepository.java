public class ExampleViewModelRepository {
  private static ExampleViewModelRepository instance;
  
  public static ExampleViewModelRepository getInstance(){
    if(instance == null){
        instance = new ExampleViewModelRepository();
    }
    return instance;
  }

  public Future<Observable<APIResponseObject>> makeFutureQuery() {
    // Executor: an interface which has a execute method. It is designed to decouple task submission from running.
    // Callable: An Interface similar to runnable but allow a result to be returned.
    // Future: Like a promise in JavaScript. It represents the result for an asynchronous task.
    // ExecutorService: an interface which extends Executor interface. It is used to manage threads in the threads pool.
    // ThreadPoolExecutor: a class that implements ExecutorService which gives fine control on the thread pool (Eg, core pool size, max pool size, keep alive time, etc.)
    // Executors: a class that offers factory and utility methods for the aforementioned classes.
    // Reference: https://medium.com/@frank.tan/using-a-thread-pool-in-android-e3c88f59d07f
    final ExecutorService executor = Executors.newSingleThreadExecutor(); // newSingleThreadExecutor creates a thread pool with only one thread.
    final Callable<Observable<APIResponseObject>> myNetworkCallable = new Callable<Observable<APIResponseObject>>() {
        @Override
        public Observable<APIResponseObject> call() throws Exception {
            return ServiceGenerator.getRetrofitAPIInterface().makeObservableQuery();
        }
    };

    final Future<Observable<APIResponseObject>> futureObservable = new Future<Observable<APIResponseObject>>(){
        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            if(mayInterruptIfRunning){
                executor.shutdown();
            }
            return false;
        }

        @Override
        public boolean isCancelled() {
            return executor.isShutdown();
        }

        @Override
        public boolean isDone() {
            return executor.isTerminated();
        }

        @Override
        public Observable<APIResponseObject> get() throws ExecutionException, InterruptedException {
            // To add a task to the thread pool call submit or execute
            return executor.submit(myNetworkCallable).get();
        }

        @Override
        public Observable<APIResponseObject> get(long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
            // To add a task to the thread pool call submit or execute
            return executor.submit(myNetworkCallable).get(timeout, unit);
        }
    };
    return futureObservable;
  }

  public LiveData<APIResponseObject> makeReactiveQuery(){
    return LiveDataReactiveStreams.fromPublisher(ServiceGenerator.getRetrofitAPIInterface()
            .makeQuery()
            .subscribeOn(Schedulers.io()));
  }

  public void makeLiveDataQuery(LifecycleOwner owner, MutableLiveData<APIResponseObject> liveDataObject) {
    makeReactiveQuery().observe(owner, new androidx.lifecycle.Observer<APIResponseObject>() {
        @Override
        public void onChanged(APIResponseObject apiResponseObject) {
            Log.d(TAG, "onChanged: this is a live data response!");
            try {
                Log.d(TAG, "onChanged: " + apiResponseObject.string());
                liveDataObject.setValue(apiResponseObject);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });
  }
}