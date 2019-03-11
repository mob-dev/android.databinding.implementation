public class ExampleViewModel extends ViewModel {
    private ExampleViewModelRepository repository;
    private ExampleFragment exampleFragment;

    public MutableLiveData<String> observableText = new MutableLiveData<String>();
    public MutableLiveData<APIResponseObject> observableAPIResponseObject = new MutableLiveData<APIResponseObject>();
    
    /* Explicit type argument can be replaced with <> */
    public MutableLiveData<List<APIResponseObject>> observableAPIResponseObjectList = new MutableLiveData<>();
    
    /* 0 is default value given to mutable live data */
    public MutableLiveData<String> observableText2 = new MutableLiveData<>("0");

    public TextWatcher textWatcher;
    public TextView.OnEditorActionListener editorActionListener;

    public ExampleViewModel() {
        repository = ExampleViewModelRepository.getInstance();
    }

    public void init(ExampleFragment exampleFragment) {
        this.exampleFragment = exampleFragment;
        setTextWatcher();
        setEditorActionListener();
    }

    private void setEditorActionListener() {
        editorActionListener = (v, actionId, event) -> {
            if (event == null) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    observableText.setValue(observableText2.getValue());
                }
            } else
                return false;
            return true;
        };
    }

    private void setTextWatcher() {
        this.textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence editTextString, int start, int count, int after) {
                // TODO:
            }

            @Override
            public void onTextChanged(CharSequence editTextString, int start, int before, int count) {
                String editTextValue = editTextString.toString();
                observableText2.setValue(editTextValue);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO:
            }
        };
    }

    public void onClickTextView(View v) {
        repository.makeLiveDataQuery(exampleFragment, observableAPIResponseObject);
    }

    public Future<Observable<APIResponseObject>> makeFutureQuery() {
        // Future is used for pendinf tasks and works like Promise.
        return repository.makeFutureQuery();
    }

    public LiveData<APIResponseObject> makeLiveDataQuery() {
        return repository.makeReactiveQuery();
    }
}