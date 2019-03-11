public class MainActivity extends AppCompatActivity {
  CompositeDisposable compositeDisposable = new CompositeDisposable();
  ExampleViewModel viewModel;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    viewModel = ViewModelProviders.of(this).get(ExampleViewModel.class);
    useOfFutureKeyword();
    makeLiveDataQuery();
  }

  public void makeLiveDataQuery() {
    viewModel.makeQuery().observe(this, new androidx.lifecycle.Observer<APIResponseObject>() {
        @Override
        public void onChanged(APIResponseObject apiResponseObject) {
            Log.d(TAG, "onChanged: this is a live data response!");
            try {
                Log.d(TAG, "onChanged: " + apiResponseObject.string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });
  }

  public void useOfFutureKeyword() {
    try {
        viewModel.makeFutureQuery().get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<APIResponseObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: called.");
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(APIResponseObject apiResponseObject) {
                        Log.d(TAG, "onNext: got the response from server!");
                        try {
                            Log.d(TAG, "onNext: " + apiResponseObject.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: called.");
                    }
                });
    } catch (ExecutionException e) {
        e.printStackTrace();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
  }

  @Override
  public void onDestroy() {
      super.onDestroy();
      compositeDisposable.clear();
  }
}
